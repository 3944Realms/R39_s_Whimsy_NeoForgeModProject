package com.r3944realms.whimsy.mixin.client.renderer;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityRenderer.class)
public abstract class MixinEntityRenderer<T extends Entity> {

    @Inject(method = {"shouldShowName"}, at = {@At("HEAD")}, cancellable = true)
    protected void shouldShowName(T pEntity, CallbackInfoReturnable<Boolean> cir) {
        if (pEntity instanceof Player) {
            cir.setReturnValue(pEntity.shouldShowName());
        }
    }
}
