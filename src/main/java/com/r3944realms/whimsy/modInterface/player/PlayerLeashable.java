package com.r3944realms.whimsy.modInterface.player;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Leashable;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public interface PlayerLeashable extends Leashable {
    @Nullable
    Entity getLeashHolder();

    void setLeashedTo(@NotNull Entity entity, boolean leashed);

    boolean canBeLeashedInstantly(Player player);

    void dropLeash(boolean pBroadcastPacket, boolean pDropItem);

    void setDelayedLeashHolderId(int id);
}
