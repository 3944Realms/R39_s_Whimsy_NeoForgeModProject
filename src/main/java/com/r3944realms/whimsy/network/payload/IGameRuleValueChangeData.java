package com.r3944realms.whimsy.network.payload;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import org.jetbrains.annotations.NotNull;

public interface IGameRuleValueChangeData extends CustomPacketPayload {
    void write(final FriendlyByteBuf buf);

    @Override
    @NotNull Type<? extends CustomPacketPayload> type();
}
