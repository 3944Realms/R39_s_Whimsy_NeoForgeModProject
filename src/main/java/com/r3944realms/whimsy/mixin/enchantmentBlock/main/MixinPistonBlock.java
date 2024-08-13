package com.r3944realms.whimsy.mixin.enchantmentBlock.main;

import com.llamalad7.mixinextras.sugar.Local;
import com.r3944realms.whimsy.content.blocks.enchantmentBlock.BlockEnchantmentStorage;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.piston.PistonBaseBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;

@Mixin(PistonBaseBlock.class)
public abstract class MixinPistonBlock {

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/core/BlockPos;relative(Lnet/minecraft/core/Direction;)Lnet/minecraft/core/BlockPos;"
            ,ordinal = 1), method = "moveBlocks")//活塞推拉方块的部分
    private void init(Level world, BlockPos pos, Direction dir
            , boolean retract, CallbackInfoReturnable<Boolean> cir, @Local(ordinal = 2) BlockPos blockPos3) {
        if (!world.isClientSide) {//只在服务端运行,获取信息
//            System.out.println(blockPos3);
            Direction direction = retract ? dir : dir.getOpposite();
            if (!Objects.equals(BlockEnchantmentStorage.getEnchantmentsAtPosition(world.dimension(), blockPos3), new ListTag())) {//如果原位置方块有附魔
                ListTag enchantments = BlockEnchantmentStorage.getEnchantmentsAtPosition(world.dimension(), blockPos3); //获取附魔信息列表
                BlockEnchantmentStorage.addBlockEnchantment(world.dimension(), blockPos3.relative(direction).immutable(), enchantments);//在新位置储存信息
            }
            if (!Objects.equals(BlockEnchantmentStorage.getEnchantmentsAtPosition(world.dimension(), blockPos3), new ListTag())) {
                BlockEnchantmentStorage.removeBlockEnchantment(world.dimension(), blockPos3.immutable());//删除信息
            }
        }
    }
}
