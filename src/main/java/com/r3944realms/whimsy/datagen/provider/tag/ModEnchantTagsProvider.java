package com.r3944realms.whimsy.datagen.provider.tag;

import com.r3944realms.whimsy.datagen.provider.attributes.ModEnchantments;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EnchantmentTagsProvider;
import net.minecraft.tags.EnchantmentTags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModEnchantTagsProvider extends EnchantmentTagsProvider {
    public ModEnchantTagsProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pLookupProvider, String modId, @Nullable ExistingFileHelper existingFileHelper) {
        super(pOutput, pLookupProvider, modId, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider pProvider) {
        this.tag(EnchantmentTags.IN_ENCHANTING_TABLE)
                .add(ModEnchantments.CHANGE_ITEM)
                .add(ModEnchantments.RANDOM_ENCHANTMENT);
        this.tag(EnchantmentTags.TREASURE)
                .add(ModEnchantments.RANDOM_ENCHANTMENT);
    }
}
