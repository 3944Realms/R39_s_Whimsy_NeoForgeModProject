package com.r3944realms.whimsy.mixin.enchantmentBlock.main;

import com.r3944realms.whimsy.modInterface.block.IBlockBehaviour$PropertiesExtension;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@SuppressWarnings("AddedMixinMembersNamePattern")
@Mixin(BlockBehaviour.Properties.class)
public class MixinBlock$Properties implements IBlockBehaviour$PropertiesExtension {
    @Unique
    private ListTag Whimsy$EnchantmentList;

    @Override
    public ListTag getEnchantmentList() {
        return Whimsy$EnchantmentList;
    }

    @Override
    public void setEnchantmentList(ListTag enchantment) {
        this.Whimsy$EnchantmentList = enchantment;
    }
}
