package com.r3944realms.whimsy.mixin.client.renderer;

import com.r3944realms.whimsy.client.renderer.texture.DynamicTextureManager;
import net.minecraft.client.renderer.entity.ItemEntityRenderer;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ItemEntityRenderer.class)
public class MixinItemEntityRenderer {
    @Redirect(
        method = {"render(Lnet/minecraft/world/entity/item/ItemEntity;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V"},
        at = @At(
                value = "INVOKE",
                target = "Lnet/minecraft/world/entity/item/ItemEntity;getItem()Lnet/minecraft/world/item/ItemStack;"
        )
    )
    private ItemStack injected(ItemEntity itemEntity) {
        ItemStack itemStack = itemEntity.getItem();
        ItemStack dynamicIconStack = DynamicTextureManager.getDynamicIconStack(itemStack);
        return dynamicIconStack != null ? dynamicIconStack : itemStack;
    }
}
