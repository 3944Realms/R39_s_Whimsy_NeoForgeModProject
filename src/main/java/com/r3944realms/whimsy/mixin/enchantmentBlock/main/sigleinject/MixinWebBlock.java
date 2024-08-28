package com.r3944realms.whimsy.mixin.enchantmentBlock.main.sigleinject;

import com.r3944realms.whimsy.content.blocks.enchantmentBlock.BlockEnchantmentStorage;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.WebBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WebBlock.class)
public abstract class MixinWebBlock  extends Block {
    public MixinWebBlock(Properties properties) {
        super(properties);
    }
    @Inject(at = @At("HEAD"), method = "entityInside")
    private void init1(BlockState pState, Level pLevel, BlockPos pPos, Entity pEntity, CallbackInfo ci) {
        int k = BlockEnchantmentStorage.getEnchantmentLevel(Enchantments.THORNS, pLevel.dimension(), pPos);
        if (!pLevel.isClientSide() && k > 0) {//如果有荆棘附魔
            pEntity.hurt(pEntity.damageSources().cactus(),(float) k);
        }
    }
}
