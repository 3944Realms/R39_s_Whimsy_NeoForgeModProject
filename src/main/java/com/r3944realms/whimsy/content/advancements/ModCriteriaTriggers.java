package com.r3944realms.whimsy.content.advancements;

import com.r3944realms.whimsy.WhimsyMod;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModCriteriaTriggers {
    public static final DeferredRegister<CriterionTrigger<?>> TRIGGERS = DeferredRegister.create(Registries.TRIGGER_TYPE, WhimsyMod.MOD_ID);
    public static final DeferredHolder<CriterionTrigger<?>, EnchantmentItemUsedTrigger> ENCHANTMENT_ITEM_USED_TRIGGER =
            TRIGGERS.register("enchantment_item_used", EnchantmentItemUsedTrigger::new);
    public static void register(IEventBus eventBus) {
        TRIGGERS.register(eventBus);
    }
}
