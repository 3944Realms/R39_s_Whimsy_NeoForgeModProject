package com.r3944realms.whimsy.network.payload;

import com.r3944realms.whimsy.WhimsyMod;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public record WebSocketServerAddressData(String address, int port) implements CustomPacketPayload {
    public static final Type<WebSocketServerAddressData> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(WhimsyMod.MOD_ID,"websocket_data"));
    public static final StreamCodec<FriendlyByteBuf, WebSocketServerAddressData> STREAM_CODEC =
            CustomPacketPayload.codec(WebSocketServerAddressData::write, WebSocketServerAddressData::new);

    public WebSocketServerAddressData(final FriendlyByteBuf buf) {
        this(buf.readUtf(), buf.readVarInt());
    }
    public void write(final FriendlyByteBuf buf) {
        buf.writeUtf(this.address);
        buf.writeVarInt(this.port);
    }
//    WebSocketServerAddressData()
    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
