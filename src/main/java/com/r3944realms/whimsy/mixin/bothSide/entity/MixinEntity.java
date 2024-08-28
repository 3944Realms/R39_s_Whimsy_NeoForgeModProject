package com.r3944realms.whimsy.mixin.bothSide.entity;

import com.r3944realms.whimsy.modInterface.player.PlayerLeashable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Leashable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Entity.class)
public class MixinEntity {
    @Redirect(
            method = "baseTick",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Leashable;tickLeash(Lnet/minecraft/world/entity/Entity;)V")
    )
    <E extends Entity & Leashable> void checkAndCancelIfTure(E entity) {
        if(!(entity instanceof PlayerLeashable)) {
            Leashable.tickLeash(entity);
        }
    }
}
