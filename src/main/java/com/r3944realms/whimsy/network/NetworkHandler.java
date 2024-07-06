package com.r3944realms.whimsy.network;

import com.r3944realms.whimsy.WhimsyMod;
import com.r3944realms.whimsy.network.payload.TestModData;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.DirectionalPayloadHandler;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber(modid = WhimsyMod.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class NetworkHandler {
    @SubscribeEvent
    public static void register(final RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar(WhimsyMod.MOD_ID);
        registrar.playBidirectional(
                TestModData.TYPE,
                TestModData.STREAM_CODEC,
                new DirectionalPayloadHandler<>(
                    ClientPayloadHandler::handleData,
                    ServerPayloadHandler::handleData
                )
        );
    }
}
