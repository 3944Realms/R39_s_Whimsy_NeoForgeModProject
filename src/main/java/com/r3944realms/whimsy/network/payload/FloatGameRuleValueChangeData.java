package com.r3944realms.whimsy.network.payload;

import com.r3944realms.whimsy.WhimsyMod;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public record FloatGameRuleValueChangeData(String GameRuleName, float value) implements IGameRuleValueChangeData{
    public static CustomPacketPayload.Type<FloatGameRuleValueChangeData> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(WhimsyMod.MOD_ID,"float_gamerule_value_change"));
    public static final StreamCodec<FriendlyByteBuf, FloatGameRuleValueChangeData> STREAM_CODEC =
            CustomPacketPayload.codec(FloatGameRuleValueChangeData::write, FloatGameRuleValueChangeData::new);
    public FloatGameRuleValueChangeData(final FriendlyByteBuf buf) {
        this(buf.readUtf(), buf.readFloat());
    }
    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeUtf(this.GameRuleName);
        buf.writeFloat(this.value);
    }

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
