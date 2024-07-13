package com.r3944realms.whimsy.mixin.client.renderer;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerRenderer.class)
public class MixinPlayerRenderer {
    @Inject(at = @At("TAIL")
            , method = "<init>(Lnet/minecraft/client/renderer/entity/EntityRendererProvider$Context;Z)V")
    public void extraLayers(EntityRendererProvider.Context pContext, boolean pUseSlimModel, CallbackInfo ci) {
//        ((PlayerRenderer)(Object)this).addLayer();
    }

    @Unique
    PlayerRenderer Whimsy$Self() {
        return ((PlayerRenderer)(Object)this);
    }

}
