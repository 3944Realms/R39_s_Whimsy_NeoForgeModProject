package com.r3944realms.whimsy.advancements;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.r3944realms.whimsy.items.enchantment.ModEnchantmentHelper;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.ConditionalEffect;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.item.enchantment.effects.EnchantmentValueEffect;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

public class EnchantmentItemUsedTrigger extends SimpleCriterionTrigger<EnchantmentItemUsedTrigger.TriggerInstance> {
    @Override
    public @NotNull Codec<EnchantmentItemUsedTrigger.TriggerInstance> codec() {
        return EnchantmentItemUsedTrigger.TriggerInstance.CODEC;
    }
    public void trigger(ServerPlayer pPlayer, ItemStack pItem, ItemEnchantments enchantment) {
        this.trigger(pPlayer, p_163870_ -> p_163870_.matches(pItem, enchantment));
    }
    public record TriggerInstance(
            Optional<ContextAwarePredicate> player, Optional<ItemPredicate> itemStack, Optional<EnchantmentPredicate> enchantment
    ) implements SimpleInstance {
        public static final Codec<EnchantmentItemUsedTrigger.TriggerInstance> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(EnchantmentItemUsedTrigger.TriggerInstance::player),
                ItemPredicate.CODEC.optionalFieldOf("item").forGetter(EnchantmentItemUsedTrigger.TriggerInstance::itemStack),
                EnchantmentPredicate.CODEC.optionalFieldOf("enchantments").forGetter(EnchantmentItemUsedTrigger.TriggerInstance::enchantment)
        ).apply(instance, EnchantmentItemUsedTrigger.TriggerInstance::new));
        public static Criterion<TriggerInstance> usingItemEnchantment(PlayerPredicate.Builder pPlayer, ItemPredicate.Builder pItem, EnchantmentPredicate pEnchantment) {
            return ModCriteriaTriggers.ENCHANTMENT_ITEM_USED_TRIGGER.get()
                    .createCriterion(new EnchantmentItemUsedTrigger.TriggerInstance( Optional.empty() ,Optional.of(pItem.build()), Optional.of(pEnchantment)));
        }

        public boolean matches(ItemStack pItem, ItemEnchantments pEnchantment) {
            return this.itemStack.isPresent() && this.itemStack.get().test(pItem) && this.enchantment.isPresent() && this.enchantment.get().containedIn(pEnchantment);
        }
    }

}
