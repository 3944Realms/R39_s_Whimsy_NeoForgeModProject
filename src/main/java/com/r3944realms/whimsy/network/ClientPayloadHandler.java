package com.r3944realms.whimsy.network;

import com.r3944realms.whimsy.network.payload.TestModData;
import com.r3944realms.whimsy.utils.logger.logger;
import net.minecraft.network.chat.Component;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public class ClientPayloadHandler {
    public static void handleData(final TestModData data, final IPayloadContext context) {
        context.enqueueWork(()->{
            logger.info(data.message());
            })
            .exceptionally(e-> {
                context.disconnect(Component.translatable("whimsy.network.payload.ack.failed" + ": %s", e.getMessage()));
                return null;
            });
    }
    @SubscribeEvent
    public static void register(final RegisterPayloadHandlersEvent event) {
        // Sets the current network version
        final PayloadRegistrar registrar = event.registrar("1");
    }
}
