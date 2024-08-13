package com.r3944realms.whimsy.datagen.provider;

import com.r3944realms.whimsy.WhimsyMod;
import com.r3944realms.whimsy.datagen.provider.attributes.ModEnchantments;
import com.r3944realms.whimsy.datagen.provider.attributes.ModPaintingVariants;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class ModDataPackBuiltInEntriesProvider extends DatapackBuiltinEntriesProvider {

    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.PAINTING_VARIANT, ModPaintingVariants::bootstrap)
            .add(Registries.ENCHANTMENT, ModEnchantments::bootstrap);

    public ModDataPackBuiltInEntriesProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of(WhimsyMod.MOD_ID));
        CompletableFuture<HolderLookup.Provider> registryProvider = getRegistryProvider();
    }
}
