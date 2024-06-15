package com.r3944realms.whimsy.network.payload;

import com.r3944realms.whimsy.WhimsyMod;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public record TestModData(String message, int age) implements CustomPacketPayload {
    public static final StreamCodec<FriendlyByteBuf, TestModData> STREAM_CODEC = CustomPacketPayload.codec(TestModData::write, TestModData::new);
    public static final Type<TestModData> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(WhimsyMod.MOD_ID,"test_mod_data"));
    public TestModData(final FriendlyByteBuf buf) {
        this(buf.readUtf(), buf.readInt());
    }

    public void write(final FriendlyByteBuf buf) {
        buf.writeUtf(message());
        buf.writeInt(age());
    }
    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
