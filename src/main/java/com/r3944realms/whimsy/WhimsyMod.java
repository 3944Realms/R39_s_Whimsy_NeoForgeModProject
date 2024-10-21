package com.r3944realms.whimsy;


import com.r3944realms.dg_lab.Dg_Lab;
import com.r3944realms.whimsy.config.ModMiscConfig;
import com.r3944realms.whimsy.config.WebSocketClientConfig;
import com.r3944realms.whimsy.config.WebSocketServerConfig;
import com.r3944realms.whimsy.content.advancements.ModCriteriaTriggers;
import com.r3944realms.whimsy.content.blocks.ModBlocksRegister;
import com.r3944realms.whimsy.content.components.ModDataComponents;
import com.r3944realms.whimsy.content.effects.ModEffectRegister;
import com.r3944realms.whimsy.content.effects.ModPotionRegister;
import com.r3944realms.whimsy.content.items.CreativeModeTab.ModCreativeTab;
import com.r3944realms.whimsy.content.items.ModItemsRegister;
import com.r3944realms.whimsy.content.items.enchantment.ModEnchantmentEffectComponents;
import com.r3944realms.whimsy.content.paintings.ModPaintingsRegister;
import com.r3944realms.whimsy.content.sounds.ModSoundRegister;
import com.r3944realms.whimsy.init.FilePathHelper;
import com.r3944realms.whimsy.utils.logger.logger;
import io.netty.handler.logging.LogLevel;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;


//2024-05-18 ACC
@Mod(WhimsyMod.MOD_ID)
public class WhimsyMod {
    public static final String MOD_ID = "whimsicality";
    public static final Dg_Lab  DG_LAB = new Dg_Lab(LogLevel.DEBUG);
//TODO: 青光眼罩
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

        logger.info();//Be careful about its loading order
        ModItemsRegister.register(modEventBus);//ItemsRegister
        ModBlocksRegister.register(modEventBus);//BlockRegister
        ModPaintingsRegister.register(modEventBus);//Painting
        ModCreativeTab.register(modEventBus);//CreativeTabRegister
        ModEnchantmentEffectComponents.register(modEventBus);//ModEnchantmentEffectComponents
        ModCriteriaTriggers.register(modEventBus);//CriteriaTriggers
        ModDataComponents.register(modEventBus);//DataComponents
        ModEffectRegister.register(modEventBus);//Mob_Effect
        ModPotionRegister.register(modEventBus);//Potion
        ModSoundRegister.register(modEventBus);//SoundEvent
        }
        //GitHub
    private void initialize() {
        String Websocket = "Websocket";
        FilePathHelper.configWhimsyFile(new String[]{Websocket});//初始化配置文件目录
        ModLoadingContext.get().getActiveContainer().registerConfig(ModConfig.Type.COMMON, ModMiscConfig.SPEC, MOD_ID + "/whimsicality_config.toml");
        ModLoadingContext.get().getActiveContainer().registerConfig(ModConfig.Type.COMMON, WebSocketServerConfig.SPEC, MOD_ID + "/"+ Websocket +"/whimsicality_config_websocketServer.toml");
        ModLoadingContext.get().getActiveContainer().registerConfig(ModConfig.Type.CLIENT, WebSocketClientConfig.SPEC, MOD_ID + "/"+ Websocket +"/whimsicality_config_websocketClient.toml");
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

