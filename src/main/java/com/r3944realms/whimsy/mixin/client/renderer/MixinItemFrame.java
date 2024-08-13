package com.r3944realms.whimsy.mixin.client.renderer;

import com.r3944realms.whimsy.client.renderer.texture.DynamicTextureManager;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.HangingEntity;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemFrame.class)
public abstract class MixinItemFrame extends HangingEntity {
    @Shadow public abstract ItemStack getItem();

    protected MixinItemFrame(EntityType<? extends HangingEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }
    @Inject(method = {"getItem"}, at = {@At("RETURN")}, cancellable = true)
    private void getItem(CallbackInfoReturnable<ItemStack> cir) {
        ItemStack dynamicIconStack;
        if((dynamicIconStack = DynamicTextureManager.getDynamicIconStack(cir.getReturnValue())) == null) {
            return;
        }
        cir.setReturnValue(dynamicIconStack);
    }
}
