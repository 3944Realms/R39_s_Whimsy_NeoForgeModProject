package com.r3944realms.whimsy.mixin.enchantmentBlock.main;

import com.r3944realms.whimsy.content.blocks.enchantmentBlock.BlockEnchantmentStorage;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;

@Mixin(Level.class)
public abstract class MixinWorld {
    @Shadow
    public abstract boolean isClientSide();

    @Shadow
    public abstract ResourceKey<Level> dimension();

    @Inject(at = @At("HEAD"), method = "destroyBlock")
    private void init(BlockPos pos, boolean dropBlock, Entity entity, int recursionLeft, CallbackInfoReturnable<Boolean> cir) {
        if (!this.isClientSide()) {
            if (!Objects.equals(BlockEnchantmentStorage.getEnchantmentsAtPosition(this.dimension(), pos), new ListTag())) {
                BlockEnchantmentStorage.removeBlockEnchantment(this.dimension(), pos.immutable());//删除信息
            }
        }
    }
}