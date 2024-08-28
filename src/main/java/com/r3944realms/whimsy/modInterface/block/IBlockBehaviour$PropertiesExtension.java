package com.r3944realms.whimsy.modInterface.block;

import net.minecraft.nbt.ListTag;

public interface IBlockBehaviour$PropertiesExtension {

    ListTag getEnchantmentList();

    void setEnchantmentList(ListTag enchantment);

    default boolean hasEnchantment() {
        return getEnchantmentList() != null;
    }
}
