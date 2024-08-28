package com.r3944realms.whimsy.content.effects;

import com.r3944realms.whimsy.WhimsyMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModPotionRegister {
    public static DeferredRegister<Potion> POTIONS = DeferredRegister.create(Registries.POTION, WhimsyMod.MOD_ID);
    public static final DeferredHolder<Potion, Potion> DRUNK = register("drunk",
            () -> new Potion("drunk", new MobEffectInstance(ModEffectRegister.DRUNK_EFFECT, 2400, 0))
    );
    public static final DeferredHolder<Potion, Potion> LONG_DRUNK = register("long_drunk",
            () -> new Potion("long_drunk", new MobEffectInstance(ModEffectRegister.DRUNK_EFFECT, 7200, 0))
    );
    public static final DeferredHolder<Potion, Potion> STRONG_DRUNK = register("strong_drunk",
            () -> new Potion("strong_drunk", new MobEffectInstance(ModEffectRegister.DRUNK_EFFECT, 2400, 1))
    );
    public static <T extends Potion>DeferredHolder<Potion, Potion> register(String Name, Supplier<T> supplier) {
        return POTIONS.register(Name, supplier);
    }
    /**
     *
     * @param name the Name of Potion
     * @param type (char)<br/> [ <br/> 0 & 3 ~ 255 : potion <br/>1 : lingering_potion <br/>2 : splash_potion<br/>]
     * @return Language Key
     */
    public static String getPotionNameKey(String name, char type) {
        return "item.minecraft." +
                (type == 1 ? "lingering_potion" :
                    (type == 2 ? "splash_potion" : "potion")
                )
                + ".effect." + name;
    }
    public static String getTippedArrowNameKey(String Name) {
        return "item.minecraft.tipped_arrow.effect." + Name;
    }

    public static void register(IEventBus eventBus) {
        POTIONS.register(eventBus);
    }
}
