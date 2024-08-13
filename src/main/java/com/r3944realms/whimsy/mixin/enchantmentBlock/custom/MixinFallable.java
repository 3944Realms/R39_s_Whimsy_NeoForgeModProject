package com.r3944realms.whimsy.mixin.enchantmentBlock.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Fallable;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Fallable.class)
public interface MixinFallable {
    @Inject(method = {"onLand"}, at = {@At("TAIL")})
    private void onLand(Level pLevel, BlockPos pPos, BlockState pState, BlockState pReplaceableState, FallingBlockEntity pFallingBlock, CallbackInfo ci) {
        //Todo:待解决下落方块落地，附魔同步问题[看有无实现的必要]
    }

}
