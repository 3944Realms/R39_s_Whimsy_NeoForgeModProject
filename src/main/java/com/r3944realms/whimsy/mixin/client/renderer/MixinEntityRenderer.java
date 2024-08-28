package com.r3944realms.whimsy.mixin.client.renderer;

import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityRenderer.class)
public abstract class MixinEntityRenderer<T extends Entity> {

    @Inject(method = {"shouldShowName"}, at = {@At("HEAD")}, cancellable = true)
    protected void shouldShowName(T pEntity, CallbackInfoReturnable<Boolean> cir) {
        if (pEntity instanceof Player) {
            cir.setReturnValue(pEntity.shouldShowName());
        }
    }
    @Redirect(
            method = {"renderLeash"},
                at = @At(
                        value = "INVOKE",
                        target = "Lnet/minecraft/world/entity/Entity;getLeashOffset(F)Lnet/minecraft/world/phys/Vec3;"
                )
    )
    private @NotNull Vec3 ret(Entity instance, float pPartialTick) {
        if(instance instanceof AbstractClientPlayer) {
            return  instance.getLeashOffset(pPartialTick).add(0, -0.2, -0.2);
        }
        return instance.getLeashOffset(pPartialTick);
    }
}
