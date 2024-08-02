package com.r3944realms.whimsy.network.payload;

import com.r3944realms.whimsy.WhimsyMod;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public record BooleanGameRuleValueChangeData(String GameRuleName, boolean value) implements IGameRuleValueChangeData {
    public static CustomPacketPayload.Type<BooleanGameRuleValueChangeData> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(WhimsyMod.MOD_ID,"bool_gamerule_value_change"));
    public static final StreamCodec<FriendlyByteBuf, BooleanGameRuleValueChangeData> STREAM_CODEC =
            CustomPacketPayload.codec(BooleanGameRuleValueChangeData::write, BooleanGameRuleValueChangeData::new);
    public BooleanGameRuleValueChangeData(final FriendlyByteBuf buf) {
        this(buf.readUtf(), buf.readBoolean());
    }
    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeUtf(this.GameRuleName);
        buf.writeBoolean(this.value);
    }

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
