package com.r3944realms.whimsy.network;

import com.r3944realms.whimsy.network.payload.TestModData;
import com.r3944realms.whimsy.utils.logger.logger;
import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class ServerPayloadHandler {

    public static void handleData(final TestModData data, final IPayloadContext context) {
        context.enqueueWork(() -> {
            logger.info(data.message());
        })
        .exceptionally(e -> {
           context.disconnect(Component.translatable("whimsy.network.payload.ack.failed" + ": %s",e.getMessage()));
            return null;
        });
    }
}
