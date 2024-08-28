package com.r3944realms.whimsy.content.effects;

import com.r3944realms.whimsy.WhimsyMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModEffectRegister {
    public static DeferredRegister<MobEffect> MOB_EFFECT = DeferredRegister.create(Registries.MOB_EFFECT, WhimsyMod.MOD_ID);
    public static DeferredHolder<MobEffect, ? extends MobEffect> DRUNK_EFFECT = register(
        "drunk_effect",
        () -> new MobEffect(MobEffectCategory.NEUTRAL, 13421568)
    );
    public static <T extends MobEffect>DeferredHolder<MobEffect, T> register(String name, Supplier<T> effect) {
       return MOB_EFFECT.register(name, effect);
    }

    public static String getEffectKey(MobEffect effect) {
        return effect.getDescriptionId();
    }
    public static String getModEffectKey(DeferredHolder<MobEffect, ? extends MobEffect> effect) {
        return getEffectKey(effect.get());
    }
    public static void register(IEventBus eventBus) {
        MOB_EFFECT.register(eventBus);
    }
}
