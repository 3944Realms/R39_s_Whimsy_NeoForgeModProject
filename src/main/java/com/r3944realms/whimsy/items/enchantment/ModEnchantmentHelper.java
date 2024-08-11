package com.r3944realms.whimsy.items.enchantment;

import com.r3944realms.whimsy.datagen.provider.attributes.ModEnchantments;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.ConditionalEffect;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.item.enchantment.effects.EnchantmentValueEffect;
import net.neoforged.neoforge.common.CommonHooks;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import java.util.*;

public class ModEnchantmentHelper {
    private static final Logger logger = LoggerFactory.getLogger(ModEnchantmentHelper.class);
    private static final Map<ResourceKey<Enchantment>, Integer> enchantmentsMap = new HashMap<>();
    private static List<ResourceKey<Enchantment>> enchantmentKeys = null;
    public static final int MapSize;
    static {
        init();
        MapSize = enchantmentsMap.size();
    }
    private static void init() {
        HolderLookup.RegistryLookup<Enchantment> lookup = CommonHooks.resolveLookup(Registries.ENCHANTMENT);
        List<Holder.Reference<Enchantment>> referencesList;
        if(lookup == null) {
            logger.error("No enchantment registry found.");
            return;
        }
        referencesList = lookup.listElements().toList();
        if(referencesList.isEmpty()) {
            logger.error("No enchantments found");
            return;
        }
        referencesList.forEach(i -> {
            if(i.key() == ModEnchantments.RANDOM_ENCHANTMENT) return;//防止再次随机
            enchantmentsMap.put(i.key(), i.value().getMaxLevel());
        });
        enchantmentKeys = enchantmentsMap.keySet().stream().toList();

    }
    public static @Nullable Holder.Reference<Enchantment> getEnchantmentByResourceKey(ResourceKey<Enchantment> key) {
        var lookup = CommonHooks.resolveLookup(Registries.ENCHANTMENT);
        if (lookup != null) {
            Optional<Holder.Reference<Enchantment>> enchantmentReference = lookup.get(key);
            return enchantmentReference.orElse(null);
        }
        return null;
    }
    public static boolean hasDesignatedEnchantment(ServerLevel level, ItemStack stack, DeferredHolder<DataComponentType<?>, DataComponentType<List<ConditionalEffect<EnchantmentValueEffect>>>> enchantmentComponent) {
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

    public static @Nullable Object2IntMap.Entry<Holder<Enchantment>> getDesignatedEnchantment(ServerLevel level, ItemStack stack, DeferredHolder<DataComponentType<?>, DataComponentType<List<ConditionalEffect<EnchantmentValueEffect>>>> enchantmentComponent) {
        ItemEnchantments itemEnchantments = stack.getOrDefault(DataComponents.ENCHANTMENTS, ItemEnchantments.EMPTY);
        Object2IntMap.Entry<Holder<Enchantment>> ret = null;
        for (Object2IntMap.Entry<Holder<Enchantment>> entry : itemEnchantments.entrySet()) {
            Holder<Enchantment> enchantmentHolder = entry.getKey();
            Enchantment value = enchantmentHolder.value();
            List<ConditionalEffect<EnchantmentValueEffect>> effects = value.getEffects(enchantmentComponent.value());
            if(!effects.isEmpty()) {
                ret = entry;
                break;
            }
        }
        return ret;
    }
    @Nullable
    public static ResourceKey<Enchantment> getRandomEnchantment(int index) {
        return (index >= enchantmentsMap.size() || index < 0) ? null : enchantmentKeys.get(index);
    }
    public static ItemEnchantments setDesignatedItemEnchantmentsLevelAndGet(ItemStack stack, ResourceKey<Enchantment> key, int level) {
        Holder.Reference<Enchantment> enchantmentHolderRef = getEnchantmentByResourceKey(key);
        if(enchantmentHolderRef == null)
            throw new NoSuchElementException("No enchantment found for resource key " + key);
        ItemEnchantments tagEnchantments1 = stack.getTagEnchantments();
        ItemEnchantments.Mutable mutable = new ItemEnchantments.Mutable(tagEnchantments1);
        mutable.set(enchantmentHolderRef.getDelegate(), level);
        return mutable.toImmutable();
    }
    public static void randomEnchantments(int times, ItemStack itemStack) {
        List<TagKey<Item>> list = itemStack.getTags().toList();
        int count = Math.min(times, MapSize);
        int[] excludeValues = new int[count];
        Arrays.fill(excludeValues, -1);
        int index;
        for(int i = 0; i < count; i++) {
            RandomSource randomSource = RandomSource.create();
            do {
                index = randomSource.nextIntBetweenInclusive(0, ModEnchantmentHelper.MapSize - 1);
            } while (hasExclusiveIndex(excludeValues, index));
            excludeValues[i] = index;
            Holder.Reference<Enchantment> enchantmentByResourceKey = ModEnchantmentHelper.getEnchantmentByResourceKey(ModEnchantmentHelper.getRandomEnchantment(index));
            if(enchantmentByResourceKey != null) {
                itemStack.enchant(enchantmentByResourceKey, randomSource.nextIntBetweenInclusive(1, enchantmentsMap.get(enchantmentByResourceKey.key())));
            }
        }
    }

    private static boolean hasExclusiveIndex(int[] excludeValues, int value) {
        for (int excludeValue : excludeValues) {
            if (excludeValue == value) {
                return true;
            }
        }
        return false;
    }

}
