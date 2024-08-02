package com.r3944realms.whimsy.advancements;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.r3944realms.whimsy.items.enchantment.ModEnchantmentHelper;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Tuple;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.ConditionalEffect;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.effects.EnchantmentValueEffect;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

public class UseItemWithEnchantmentTrigger extends SimpleCriterionTrigger<UseItemWithEnchantmentTrigger.TriggerInstance> {
    @Override
    public @NotNull Codec<UseItemWithEnchantmentTrigger.TriggerInstance> codec() {
        return UseItemWithEnchantmentTrigger.TriggerInstance.CODEC;
    }
    public void trigger(ServerPlayer pPlayer, ItemStack pItem, DeferredHolder<DataComponentType<?>, DataComponentType<List<ConditionalEffect<EnchantmentValueEffect>>>> pEnchantmentComponent) {
        this.trigger(pPlayer, p_163870_ -> p_163870_.matches(pItem, pEnchantmentComponent));
    }
    public record TriggerInstance(Optional<ContextAwarePredicate> player, Optional<ItemPredicate> itemStack) implements SimpleInstance {
        public static final Codec<UseItemWithEnchantmentTrigger.TriggerInstance> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(UseItemWithEnchantmentTrigger.TriggerInstance::player),
                ItemPredicate.CODEC.optionalFieldOf("item").forGetter(UseItemWithEnchantmentTrigger.TriggerInstance::itemStack)
        ).apply(instance, UseItemWithEnchantmentTrigger.TriggerInstance::new));
//        public static CriterionTrigger<UseItemWithEnchantmentTrigger.TriggerInstance> usingItemThat(EntityPredicate.Builder pPlayer, ItemPredicate.Builder pItem) {
//            return;
//            //TODO: 寫事件觸發器
//        }

        public boolean matches(ItemStack pItem, DeferredHolder<DataComponentType<?>, DataComponentType<List<ConditionalEffect<EnchantmentValueEffect>>>> pEnchantmentComponent) {
            return itemStack.isEmpty() || (this.itemStack.get().test(pItem) &&  ModEnchantmentHelper.hasReferToEnchantment(null, pItem, pEnchantmentComponent));
        }
    }

}
