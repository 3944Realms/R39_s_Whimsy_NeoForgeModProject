package com.r3944realms.whimsy.mixin.client.renderer;


import net.minecraft.client.renderer.entity.EntityRenderers;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(EntityRenderers.class)
public class MixinEntityRenderers {
//    @Inject(method = "createPlayerRenderers", at = @At("RETURN"), cancellable = true)
//    private static void injectCustomPlayerRenderer(EntityRendererProvider.Context pContext, CallbackInfoReturnable<Map<PlayerSkin.Model, EntityRenderer<? extends Player>>> cir) {
//        ImmutableMap.Builder<PlayerSkin.Model, EntityRenderer<? extends Player>> builder = ImmutableMap.builder();
//        ModEntityRenderers.KID_PLAYER_PROVIDES.forEach(((model, abstractClientPlayerEntityRendererProvider) -> {
//            try {
//                builder.put(model, abstractClientPlayerEntityRendererProvider.create(pContext));
//            } catch (Exception e) {
//                throw new IllegalArgumentException("Failed to create player model for " + model, e);
//            }
//        }));
//        cir.setReturnValue(builder.build());
//    }
}
