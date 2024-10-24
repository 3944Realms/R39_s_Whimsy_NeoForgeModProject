package com.r3944realms.whimsy.mixin.enchantmentBlock.main;

import com.r3944realms.whimsy.content.blocks.enchantmentBlock.BlockEnchantmentStorage;
import com.r3944realms.whimsy.utils.mixinHelper.InjectHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;
@Mixin(Block.class)
public abstract class MixinBlock extends BlockBehaviour implements ItemLike, net.neoforged.neoforge.common.extensions.IBlockExtension {
    @Unique
    private final static ListTag WHIMSY$EMPTY_LIST = new ListTag();
    public MixinBlock(Properties properties) {
        super(properties);
    }
    @Inject(at = @At("HEAD"), method = "setPlacedBy")//存储方块的附魔
    private void init1(Level level, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack, CallbackInfo ci) {
        InjectHelper.onPlacedInject(level,stack,pos);
    }

    @Inject(at = @At("HEAD"), method = "destroy")//删除方块的附魔
    private void init2(LevelAccessor level, BlockPos pos, BlockState state, CallbackInfo ci){
        if (!level.isClientSide()) {
            Level world = (Level) level;
            if (!Objects.equals(BlockEnchantmentStorage.getEnchantmentsAtPosition(world.dimension(), pos), WHIMSY$EMPTY_LIST)) {
                BlockEnchantmentStorage.removeBlockEnchantment(world.dimension(), pos.immutable());//删除信息

                //给掉落的方块附魔
//                BlockEntity blockEntity = state.hasBlockEntity() ? world.getBlockEntity(pos) : null;
//                Block.dropStacks(state, (World) world, pos, blockEntity, null, ItemStack.EMPTY);
            }
        }
    }
    @Inject(at = @At("TAIL"), method = "wasExploded")//删除方块的附魔
    private void breakBFExploded(Level level, BlockPos pos, Explosion explosion, CallbackInfo ci){
        if (!level.isClientSide) {
            if (!Objects.equals(BlockEnchantmentStorage.getEnchantmentsAtPosition(level.dimension(), pos), WHIMSY$EMPTY_LIST)) {
                BlockEnchantmentStorage.removeBlockEnchantment(level.dimension(), pos.immutable());//删除信息
            }
        }
    }
    @Inject(at = @At("TAIL"), method = "dropResources(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;)V")//删除方块的附魔
    private static void init5(BlockState state, Level level, BlockPos pos, CallbackInfo ci){
        if (!level.isClientSide()) {
            if (!Objects.equals(BlockEnchantmentStorage.getEnchantmentsAtPosition(level.dimension(), pos), WHIMSY$EMPTY_LIST)) {
                BlockEnchantmentStorage.removeBlockEnchantment(level.dimension(), pos.immutable());//删除信息
            }
        }
    }

}
