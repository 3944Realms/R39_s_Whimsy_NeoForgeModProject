package com.r3944realms.whimsy.network.payload.ackpayload;

import com.r3944realms.whimsy.WhimsyMod;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public record SyncWebsocketRequestPayload(UUID player_uuid) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<SyncWebsocketRequestPayload> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(WhimsyMod.MOD_ID,"request"));
    public static final StreamCodec<FriendlyByteBuf, SyncWebsocketRequestPayload> STREAM_CODEC =
            CustomPacketPayload.codec(SyncWebsocketRequestPayload::write, SyncWebsocketRequestPayload::new);

    public SyncWebsocketRequestPayload(final FriendlyByteBuf buf) {
        this(buf.readUUID());
    }

    public void write(final FriendlyByteBuf buf) {
        buf.writeUUID(this.player_uuid);
    }

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
