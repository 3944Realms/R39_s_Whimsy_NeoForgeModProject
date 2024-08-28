package com.r3944realms.whimsy;

import com.r3944realms.whimsy.utils.logger.logger;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLDedicatedServerSetupEvent;
import net.neoforged.neoforge.event.server.ServerStartedEvent;


public class DedicatedServerEventHandler {
    @EventBusSubscriber(modid = WhimsyMod.MOD_ID, value = Dist.DEDICATED_SERVER, bus = EventBusSubscriber.Bus.GAME)
    public static class DedicatedGameBusEventHandler extends DedicatedServerEventHandler {
        @SubscribeEvent
        public static void onDedicatedServerSetup(ServerStartedEvent event) {
            logger.debug("DedicatedGameBusEventHandler has been registered");
        }
    }
    @EventBusSubscriber(modid = WhimsyMod.MOD_ID, value = Dist.DEDICATED_SERVER, bus = EventBusSubscriber.Bus.MOD)
    public static class DedicatedModBusEventHandler extends DedicatedServerEventHandler {
        @SubscribeEvent
        public static void ServerSetup(FMLDedicatedServerSetupEvent event) {
            logger.debug("DedicatedModBusEventHandler has been registered");
        }
    }
}
