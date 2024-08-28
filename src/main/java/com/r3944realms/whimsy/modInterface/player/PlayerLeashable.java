package com.r3944realms.whimsy.modInterface.player;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Leashable;
import net.minecraft.world.entity.player.Player;

import javax.annotation.Nullable;

public interface PlayerLeashable extends Leashable {
    @Nullable
    Entity getLeashHolder();

    public Leashable.LeashData getLeashDataFromEntityData();

    boolean canBeLeashedInstantly(Player player);

}
