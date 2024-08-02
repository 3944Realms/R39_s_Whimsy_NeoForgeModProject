package com.r3944realms.whimsy.items.enchantment;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.ConditionalEffect;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.item.enchantment.effects.EnchantmentValueEffect;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.List;

public class ModEnchantmentHelper {
    public static boolean hasDeathEyesEnchantment(ServerLevel level, ItemStack stack) {
        ItemEnchantments itemEnchantments = stack.getOrDefault(DataComponents.ENCHANTMENTS, ItemEnchantments.EMPTY);
        boolean isHaveEnchantment = false;
        for (Object2IntMap.Entry<Holder<Enchantment>> entry : itemEnchantments.entrySet()) {
            Holder<Enchantment> enchantmentHolder = entry.getKey();
            Enchantment value = enchantmentHolder.value();
            List<ConditionalEffect<EnchantmentValueEffect>> effects = value.getEffects(ModEnchantmentEffectComponents.DEATH_EYES.value());
            if(!effects.isEmpty()) {
                isHaveEnchantment = true;
                break;
            }

        }
        return isHaveEnchantment;
    }
    public static boolean hasChangeItemEnchantment(ServerLevel level, ItemStack stack) {
        ItemEnchantments itemEnchantments = stack.getOrDefault(DataComponents.ENCHANTMENTS, ItemEnchantments.EMPTY);
        boolean isHaveEnchantment = false;
        for (Object2IntMap.Entry<Holder<Enchantment>> entry : itemEnchantments.entrySet()) {
            Holder<Enchantment> enchantmentHolder = entry.getKey();
            Enchantment value = enchantmentHolder.value();
            List<ConditionalEffect<EnchantmentValueEffect>> effects = value.getEffects(ModEnchantmentEffectComponents.CHANGE_ITEM.value());
            if(!effects.isEmpty()) {
                isHaveEnchantment = true;
                break;
            }
        }
        return isHaveEnchantment;
    }
    public static boolean hasReferToEnchantment(ServerLevel level, ItemStack stack, DeferredHolder<DataComponentType<?>, DataComponentType<List<ConditionalEffect<EnchantmentValueEffect>>>> enchantmentComponent) {
        ItemEnchantments itemEnchantments = stack.getOrDefault(DataComponents.ENCHANTMENTS, ItemEnchantments.EMPTY);
        boolean isHaveEnchantment = false;
        for (Object2IntMap.Entry<Holder<Enchantment>> entry : itemEnchantments.entrySet()) {
            Holder<Enchantment> enchantmentHolder = entry.getKey();
            Enchantment value = enchantmentHolder.value();
            List<ConditionalEffect<EnchantmentValueEffect>> effects = value.getEffects(enchantmentComponent.value());
            if(!effects.isEmpty()) {
                isHaveEnchantment = true;
                break;
            }
        }
        return isHaveEnchantment;
    }
}
