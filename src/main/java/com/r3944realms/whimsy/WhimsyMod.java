package com.r3944realms.whimsy;


import com.mojang.brigadier.CommandDispatcher;
import com.r3944realms.whimsy.advancements.ModCriteriaTriggers;
import com.r3944realms.whimsy.api.manager.WebsocketServerManager;
import com.r3944realms.whimsy.api.websocket.WebSocketServer;
import com.r3944realms.whimsy.blocks.ModBlocksRegister;
import com.r3944realms.whimsy.command.PlayerProperty.ChatCommand;
import com.r3944realms.whimsy.command.PlayerProperty.NameTagCommand;
import com.r3944realms.whimsy.command.TestServerCommand;
import com.r3944realms.whimsy.command.Websocket.WebSocketServerCommand;
import com.r3944realms.whimsy.command.miscCommand.MotionCommand;
import com.r3944realms.whimsy.config.TestConfig;
import com.r3944realms.whimsy.config.WebSocketClientConfig;
import com.r3944realms.whimsy.config.WebSocketServerConfig;
import com.r3944realms.whimsy.gamerule.ClientRender.MustOthersHiddenNameTag;
import com.r3944realms.whimsy.gamerule.Gamerules;
import com.r3944realms.whimsy.init.FilePathHelper;
import com.r3944realms.whimsy.items.CreativeModeTab.ModCreativeTab;
import com.r3944realms.whimsy.items.ModItemsRegister;
import com.r3944realms.whimsy.items.enchantment.ModEnchantmentEffectComponents;
import com.r3944realms.whimsy.network.payload.BooleanGameRuleValueChangeData;
import com.r3944realms.whimsy.utils.logger.logger;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.enchantment.Enchantment;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.server.ServerStartedEvent;
import net.neoforged.neoforge.event.server.ServerStoppedEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;


//2024-05-18 ACC
@Mod(WhimsyMod.MOD_ID)
public class WhimsyMod {
    public static final String MOD_ID = "whimsicality";

    /**
    * We can directly receive the event bus as a mod constructor argument.<br/><br/><br/>
     * <code>
     *   Old method:<br/>
     *   IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
     * </code><br/><br/><b> Method above on had been Deprecated.</b>
     * <br/><br/>
     * @since neoForge 1.20.4
    **/
    public WhimsyMod (IEventBus modEventBus) { /*loading*/
        initialize();
        modEventBus.addListener(this::commonSetup);
        NeoForge.EVENT_BUS.addListener(this::onRegisterCommander);//指令注册器
        NeoForge.EVENT_BUS.addListener(this::onServerStarted);
        NeoForge.EVENT_BUS.addListener(this::onServerStopped);
        NeoForge.EVENT_BUS.addListener(this::onPlayerLogin);
        logger.info();//Be careful about its loading order
        ModItemsRegister.register(modEventBus);//ItemsRegister
        ModBlocksRegister.register(modEventBus);//BlockRegister
        ModCreativeTab.register(modEventBus);//CreativeTabRegister
        ModEnchantmentEffectComponents.register(modEventBus);//ModEnchantmentEffectComponents
        ModCriteriaTriggers.register(modEventBus);//CriteriaTriggers
        }
    private void initialize() {
        String Websocket = "Websocket";
        FilePathHelper.configWhimsyFile(new String[]{Websocket});//初始化配置文件目录
        ModLoadingContext.get().getActiveContainer().registerConfig(ModConfig.Type.COMMON, TestConfig.SPEC, MOD_ID + "/whimsicality_config.toml");
        ModLoadingContext.get().getActiveContainer().registerConfig(ModConfig.Type.COMMON, WebSocketServerConfig.SPEC, MOD_ID + "/"+ Websocket +"/whimsicality_config_websocketServer.toml");
        ModLoadingContext.get().getActiveContainer().registerConfig(ModConfig.Type.CLIENT, WebSocketClientConfig.SPEC, MOD_ID + "/"+ Websocket +"/whimsicality_config_websocketClient.toml");

    }

    /**
     *CommandRegister In BOTH SIDE
     */
    private void onRegisterCommander(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();
        WebSocketServerCommand.register(dispatcher);
        ChatCommand.register(dispatcher);
        MotionCommand.register(dispatcher);
        NameTagCommand.register(dispatcher);

        TestServerCommand.register(dispatcher);
    }


    private void commonSetup(final FMLCommonSetupEvent event) {

    }
    /**
     * SeverStartingEvent
     */
    public void onServerStarted(ServerStartedEvent event) {
        WebSocketServer.isTextMessageMode.set(WebSocketServerConfig.isEnableWebSocketTextMessageMode.get());
        if(WebSocketServerConfig.WebSocketServerAutoManager.get()) {
            WebsocketServerManager.INSTANCE.StartServer();
        }
    }
    public void onServerStopped(ServerStoppedEvent event) {
        if(WebSocketServerConfig.WebSocketServerAutoManager.get()) {
            WebsocketServerManager.INSTANCE.StopServer();
        }
    }
    public void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        final BooleanGameRuleValueChangeData syncPayload = new BooleanGameRuleValueChangeData(MustOthersHiddenNameTag.ID, Gamerules.gamerulesBooleanValuesClient.get(MustOthersHiddenNameTag.ID));
        logger.info("==-=-=-=-=-=-=-=-=-=-=-=-=--=");
        PacketDistributor.sendToPlayer((ServerPlayer) event.getEntity(), syncPayload);
    }
//   @EventBusSubscriber(modid = WhimsyMod.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
//    public static class ServerModEvent {
//
//       @SubscribeEvent
//       public static void ConfigurationTasks(final RegisterConfigurationTasksEvent event) {
//           event.register(new ClientGameRulesSyncConfigurationTask(event.getListener()));
//       }
//   }

}

