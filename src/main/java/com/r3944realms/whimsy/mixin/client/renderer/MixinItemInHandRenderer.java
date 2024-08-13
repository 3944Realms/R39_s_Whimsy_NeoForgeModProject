package com.r3944realms.whimsy.mixin.client.renderer;

import com.r3944realms.whimsy.client.renderer.texture.DynamicTextureManager;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ItemInHandRenderer.class)
public class MixinItemInHandRenderer {
    @ModifyVariable(method = {"renderArmWithItem"}, at = @At ("HEAD"),argsOnly = true, ordinal = 0)
    private ItemStack injected(ItemStack stack) {
        ItemStack dynamicIconStack = DynamicTextureManager.getDynamicIconStack(stack);
        return dynamicIconStack != null ? dynamicIconStack : stack;
    }
}
