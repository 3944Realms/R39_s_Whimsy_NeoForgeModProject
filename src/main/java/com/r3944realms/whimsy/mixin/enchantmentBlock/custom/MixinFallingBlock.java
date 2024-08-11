package com.r3944realms.whimsy.mixin.enchantmentBlock.custom;

import com.r3944realms.whimsy.blocks.enchantmentBlock.BlockEnchantmentStorage;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.ListTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(FallingBlock.class)
public abstract class MixinFallingBlock {
    @Unique
    private final static ListTag WHIMSY$EMPTY_LIST = new ListTag();
    @Inject(method = {"tick"}, at = {
            @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/item/FallingBlockEntity;fall(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;)Lnet/minecraft/world/entity/item/FallingBlockEntity;",
                    shift = At.Shift.AFTER
            )

    })
    protected void tick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom, CallbackInfo ci) {
        if(!Objects.equals(BlockEnchantmentStorage.getEnchantmentsAtPosition(pLevel.dimension(), pPos), WHIMSY$EMPTY_LIST))
            BlockEnchantmentStorage.removeBlockEnchantment(pLevel.dimension(), pPos);//对于下落方块，在下落前移除其附魔

    }

}
