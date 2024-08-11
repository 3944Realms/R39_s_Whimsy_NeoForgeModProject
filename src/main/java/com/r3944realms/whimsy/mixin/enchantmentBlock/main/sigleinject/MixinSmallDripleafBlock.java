package com.r3944realms.whimsy.mixin.enchantmentBlock.main.sigleinject;

import com.r3944realms.whimsy.utils.mixinHelper.InjectHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SmallDripleafBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SmallDripleafBlock.class)
public class MixinSmallDripleafBlock {
    @Inject(at = @At("HEAD"), method = "setPlacedBy")//存储方块的附魔
    private void init1(Level level, BlockPos pos, BlockState state, LivingEntity entity, ItemStack stack, CallbackInfo ci) {
        InjectHelper.onPlacedInject(level, stack, pos);
    }
}
