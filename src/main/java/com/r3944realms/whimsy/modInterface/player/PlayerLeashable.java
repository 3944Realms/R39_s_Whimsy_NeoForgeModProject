package com.r3944realms.whimsy.modInterface.player;

import net.minecraft.network.protocol.game.ClientboundSetEntityLinkPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Leashable;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public interface PlayerLeashable extends Leashable {
    @Nullable
    Entity getLeashHolder();

    Leashable.LeashData getLeashDataFromEntityData();

    boolean canBeLeashedInstantly(Player player);

    default void setLeashedTo(@NotNull Entity pLeashHolder, boolean pBroadcastPacket) {
        setLeashedTo((Entity & Leashable)this, pLeashHolder, pBroadcastPacket);
    }

    static <E extends Entity & Leashable> void setLeashedTo(E pEntity, Entity pLeashHolder, boolean pBroadcastPacket) {
        Leashable.LeashData leashable$leashdata = pEntity.getLeashData();
        if (leashable$leashdata == null) {
            leashable$leashdata = new Leashable.LeashData(pLeashHolder);
            pEntity.setLeashData(leashable$leashdata);
        } else {
            leashable$leashdata.setLeashHolder(pLeashHolder);
        }

        if (pBroadcastPacket && pEntity.level() instanceof ServerLevel serverlevel) {
            serverlevel.getChunkSource().broadcast(pEntity, new ClientboundSetEntityLinkPacket(pEntity, pLeashHolder));
        }

    }
}
