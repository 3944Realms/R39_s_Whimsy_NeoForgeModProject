package com.r3944realms.whimsy;


import com.r3944realms.whimsy.api.manager.WebsocketClientManager;
import com.r3944realms.whimsy.api.manager.WebsocketServerManager;
import com.r3944realms.whimsy.blocks.ModBlocksRegister;
import com.r3944realms.whimsy.command.WebSocketClientCommand;
import com.r3944realms.whimsy.command.WebSocketServerCommand;
import com.r3944realms.whimsy.config.TestConfig;
import com.r3944realms.whimsy.config.WebSocketClientConfig;
import com.r3944realms.whimsy.config.WebSocketServerConfig;
import com.r3944realms.whimsy.init.FilePathInit;
import com.r3944realms.whimsy.items.CreativeModeTab.ModCreativeTab;
import com.r3944realms.whimsy.items.ModItemsRegister;
import com.r3944realms.whimsy.utils.logger.logger;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;
import net.neoforged.neoforge.client.event.RegisterClientCommandsEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.server.ServerStartedEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.event.server.ServerStoppedEvent;
import net.neoforged.neoforge.event.server.ServerStoppingEvent;
import org.jetbrains.annotations.NotNull;

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
        logger.info();//Be careful about its loading order
        ModItemsRegister.register(modEventBus);//ItemsRegister
        ModBlocksRegister.register(modEventBus);//BlockRegister
        ModCreativeTab.register(modEventBus);//CreativeTabRegister
        }
    private void initialize() {
        String Websocket = "Websocket";
        FilePathInit.configWhimsyFile(new String[]{Websocket});//初始化配置文件目录
        ModLoadingContext.get().getActiveContainer().registerConfig(ModConfig.Type.COMMON, TestConfig.SPEC, MOD_ID + "/whimsicality_config.toml");
        ModLoadingContext.get().getActiveContainer().registerConfig(ModConfig.Type.COMMON, WebSocketServerConfig.SPEC, MOD_ID + "/"+ Websocket +"/whimsicality_config_websocketServer.toml");
        ModLoadingContext.get().getActiveContainer().registerConfig(ModConfig.Type.CLIENT, WebSocketClientConfig.SPEC, MOD_ID + "/"+ Websocket +"/whimsicality_config_websocketClient.toml");
    }

    /**
     *CommandRegister In BOTH SIDE
     */
    private void onRegisterCommander(RegisterCommandsEvent event) {
        WebSocketServerCommand.register(event.getDispatcher());
    }

    private void commonSetup(final FMLCommonSetupEvent event) {

    }
    /**
     * SeverStartingEvent
     */
    public void onServerStarted(ServerStartedEvent event) {
        if(WebSocketServerConfig.WebSocketServerAutoManager.get()) {
            WebsocketServerManager.INSTANCE.StartServer();
        }
    }
    public void onServerStopped(ServerStoppedEvent event) {
        if(WebSocketServerConfig.WebSocketServerAutoManager.get()) {
            WebsocketServerManager.INSTANCE.StopServer();
        }
    }
    @EventBusSubscriber(modid = WhimsyMod.MOD_ID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
    public static class ClientModEvent {

        @SubscribeEvent
        public static void ClientSetup(FMLClientSetupEvent event) {
            NeoForge.EVENT_BUS.addListener(ClientModEvent::onClientCommandsRegister);//客户端指令注册器
            NeoForge.EVENT_BUS.addListener(ClientModEvent::onLoggingOn);
            NeoForge.EVENT_BUS.addListener(ClientModEvent::onLoggingOut);
        }
        /**
         *CommandRegister In CLIENT SIDE
         */
        private static void onClientCommandsRegister(RegisterClientCommandsEvent event) {
            WebSocketClientCommand.register(event.getDispatcher());
        }
        public static void onLoggingOn(ClientPlayerNetworkEvent.LoggingIn event) {
            WebsocketClientManager.INSTANCE.StartClient();
        }
        public static void onLoggingOut(ClientPlayerNetworkEvent.LoggingOut event){
            WebsocketClientManager.INSTANCE.StopClient();
        }
    }
}

