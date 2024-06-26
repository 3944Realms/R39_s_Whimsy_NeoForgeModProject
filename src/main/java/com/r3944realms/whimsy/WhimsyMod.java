package com.r3944realms.whimsy;


import com.r3944realms.whimsy.blocks.ModBlocksRegister;
import com.r3944realms.whimsy.config.TestConfig;
import com.r3944realms.whimsy.items.CreativeModeTab.ModCreativeTab;
import com.r3944realms.whimsy.items.ModItemsRegister;
import com.r3944realms.whimsy.network.NetworkHandler;
import com.r3944realms.whimsy.network.configuration.TestConfigurationTask;
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
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

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
        modEventBus.addListener(this::commonSetup);
        logger.info();//Be careful about its loading order
        ModItemsRegister.register(modEventBus);//ItemsRegister
        ModBlocksRegister.register(modEventBus);//BlockRegister
        ModCreativeTab.register(modEventBus);//CreativeTabRegister
        ModLoadingContext.get().getActiveContainer().registerConfig(ModConfig.Type.COMMON, TestConfig.SPEC, "whimsicality_config.toml");
    }
    /**
     *CommandRegister
     */
    @SubscribeEvent
    public static  void registerPackets(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar(WhimsyMod.MOD_ID);

    }
    @SubscribeEvent
    public void onRegisterCommander(RegisterCommandsEvent event) {

    }
    private void commonSetup(final FMLCommonSetupEvent event) {
    }
    /**
     * SeverStartingEvent
     */
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

    }
    @EventBusSubscriber(modid = WhimsyMod.MOD_ID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
    public static class ClientModEvent {
        @SubscribeEvent
        public static void ClientSetup(FMLClientSetupEvent event) {

        }
    }
}

