package com.r3944realms.whimsy;


import com.r3944realms.whimsy.utils.logger.logger;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

@Mod(WhimsyMod.MOD_ID)
public class WhimsyMod {
    public static final String MOD_ID = "whimsicality";

    public WhimsyMod () { /*loading*/
        logger.info();
    }
    /**
     *CommandRegister
     */
    @SubscribeEvent
    public void onRegisterCommander(RegisterCommandsEvent event) {

    }
    private void commonSetup(final FMLCommonSetupEvent event) {

    }
    /**
     * SeverStartingEvent
     */
    public void onServerStarting(ServerStartingEvent event) {

    }
    @Mod.EventBusSubscriber(modid = WhimsyMod.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModEvent {
        @SubscribeEvent
        public static void ClientSetup(FMLClientSetupEvent event) {

        }
    }
}

