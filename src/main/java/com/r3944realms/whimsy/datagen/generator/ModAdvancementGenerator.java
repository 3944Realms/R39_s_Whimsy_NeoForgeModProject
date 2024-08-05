package com.r3944realms.whimsy.datagen.generator;

import com.r3944realms.whimsy.WhimsyMod;
import com.r3944realms.whimsy.advancements.EnchantmentItemUsedTrigger;
import com.r3944realms.whimsy.datagen.LanguageData.ModAdvancementKey;
import com.r3944realms.whimsy.datagen.provider.ModAdvancementProvider;
import com.r3944realms.whimsy.datagen.provider.enchantment.ModEnchantments;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.AdvancementProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import javax.xml.stream.Location;
import java.util.function.Consumer;

public class ModAdvancementGenerator implements AdvancementProvider.AdvancementGenerator {
    @Override
    public void generate(HolderLookup.@NotNull Provider registries, @NotNull Consumer<AdvancementHolder> saver, @NotNull ExistingFileHelper existingFileHelper) {
        HolderLookup.RegistryLookup<Enchantment> registryLookup = registries.lookupOrThrow(Registries.ENCHANTMENT);
        AdvancementHolder root = Advancement.Builder.advancement().display(
                Blocks.TNT,
                Component.translatable(ModAdvancementKey.RWN_WELCOME.getNameKey()),
                Component.translatable(ModAdvancementKey.RWN_WELCOME.getDescKey()),
                ResourceLocation.fromNamespaceAndPath(WhimsyMod.MOD_ID, "textures/gui/advancements/backgrounds/whimsy.png"),
                AdvancementType.TASK,
                true,
                false,
                false
        ).addCriterion("join_this_game", PlayerTrigger.TriggerInstance.located(
                LocationPredicate.Builder.inDimension(Level.OVERWORLD)
        )).save(saver, ModAdvancementKey.RWN_WELCOME.getNameWithNameSpace());
        AdvancementHolder change_item = Advancement.Builder.advancement().display(
                Items.FLINT_AND_STEEL,
                Component.translatable(ModAdvancementKey.RWN_CHANGE_ITEM.getNameKey()),
                Component.translatable(ModAdvancementKey.RWN_CHANGE_ITEM.getDescKey()),
                null,
                AdvancementType.GOAL,
                true,
                false,
                false
        ).addCriterion("change_item", EnchantmentItemUsedTrigger.TriggerInstance.usingItemEnchantment(
                PlayerPredicate.Builder.player(),
                ItemPredicate.Builder.item().of(Tags.Items.ENCHANTABLES),
                new EnchantmentPredicate(registryLookup.getOrThrow(ModEnchantments.CHANGE_ITEM), MinMaxBounds.Ints.atLeast(1))
        )).parent(root).save(saver, ModAdvancementKey.RWN_CHANGE_ITEM.getNameWithNameSpace());
    }
}
