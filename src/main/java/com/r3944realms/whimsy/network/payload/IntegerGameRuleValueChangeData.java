package com.r3944realms.whimsy.network.payload;

import com.r3944realms.whimsy.WhimsyMod;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public record IntegerGameRuleValueChangeData(String GameRuleName, int value) implements IGameRuleValueChangeData {
    public static CustomPacketPayload.Type<IntegerGameRuleValueChangeData> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(WhimsyMod.MOD_ID,"int_gamerule_value_change"));

    public static final StreamCodec<FriendlyByteBuf, IntegerGameRuleValueChangeData> STREAM_CODEC =
            CustomPacketPayload.codec(IntegerGameRuleValueChangeData::write, IntegerGameRuleValueChangeData::new);
    public IntegerGameRuleValueChangeData(final FriendlyByteBuf buf) {
        this(buf.readUtf(), buf.readInt());
    }
    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeUtf(this.GameRuleName);
        buf.writeInt(this.value);
    }

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

}
