package com.r3944realms.whimsy.network.payload.ackpayload;

import com.r3944realms.whimsy.WhimsyMod;
import com.r3944realms.whimsy.network.NetworkHandler;
import com.r3944realms.whimsy.network.configuration.TestConfigurationTask;
import com.r3944realms.whimsy.network.payload.TestModData;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

@Deprecated
public record AckPayload() implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<AckPayload> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(WhimsyMod.MOD_ID,"DefaultAck"));

    /**
        <h>#unit</h><br/>
        A stream codec which supplies an in-code value and encodes to nothing can be represented using StreamCodec#unit.
        This is useful if no information should be synced across the network.
    */
    public static final StreamCodec<ByteBuf, AckPayload> STREAM_CODEC = StreamCodec.unit(new AckPayload());

//    public static final String NET_PAYLOAD_ACK_FAILED_KEY = "whimsy.network.payload.ack.failed";
    public void onAck(AckPayload payload, IPayloadContext context) {
        context.finishCurrentTask(TestConfigurationTask.TYPE);
    }

    public void onMyTestModData(TestModData date, IPayloadContext context) {
        context.enqueueWork( () -> {

        }).exceptionally(e -> {
            context.disconnect(Component.translatable(NetworkHandler.ACK_FAILED + ": %s", e.getMessage()));
            return null;
        })
        .thenAccept(v -> {
           context.reply(new AckPayload());
        });
    }

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

}
