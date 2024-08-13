package com.r3944realms.whimsy.mixin.enchantmentBlock.custom;

import com.r3944realms.whimsy.content.blocks.enchantmentBlock.BlockEnchantmentStorage;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.ListTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.PointedDripstoneBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(PointedDripstoneBlock.class)
public class MixinPointedDripstoneBlock {
    @Shadow @Final
    public static DirectionProperty TIP_DIRECTION;
    @Unique
    private final static ListTag WHIMSY$EMPTY_LIST = new ListTag();
    @Inject(method = {"spawnFallingStalactite"}, at = {
            @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/item/FallingBlockEntity;fall(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)Lnet/minecraft/world/entity/item/FallingBlockEntity;",
                    shift = At.Shift.AFTER
            )

    })
    private static void spawnFallingStalactite(BlockState pState, ServerLevel pLevel, BlockPos pPos, CallbackInfo ci) {
        String pName = pState.getBlock().toString();
        BlockPos blockPos = pPos;
        BlockState blockState;
        do {
            if(!Objects.equals(BlockEnchantmentStorage.getEnchantmentsAtPosition(pLevel.dimension(), blockPos), WHIMSY$EMPTY_LIST))
                BlockEnchantmentStorage.removeBlockEnchantment(pLevel.dimension(), blockPos);//对于下落方块，在下落前移除其附魔
            blockPos = blockPos.below();
            blockState =  pLevel.getBlockState(blockPos);
        } while(blockState.getBlock().toString().equals(pName) && blockState.getValue(TIP_DIRECTION) == Direction.DOWN);
    }
}
