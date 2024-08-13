package com.r3944realms.whimsy.mixin.client.renderer;

import com.r3944realms.whimsy.client.renderer.texture.DynamicTextureManager;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ItemRenderer.class)
public class MixinItemRenderer {
    @ModifyVariable(method = {"renderStatic(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemDisplayContext;ZLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;Lnet/minecraft/world/level/Level;III)V"}, at =@At("HEAD"), argsOnly = true, ordinal = 0)
    private ItemStack rendererStatic(ItemStack stack) {
        ItemStack dynamicIconStack = DynamicTextureManager.getDynamicIconStack(stack);
        return dynamicIconStack != null ? dynamicIconStack : stack;
    }
}
