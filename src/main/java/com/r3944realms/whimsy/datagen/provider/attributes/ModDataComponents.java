package com.r3944realms.whimsy.datagen.provider.attributes;

import com.r3944realms.whimsy.WhimsyMod;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.util.ExtraCodecs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;
import java.util.function.UnaryOperator;

public class ModDataComponents {
    public static DeferredRegister<DataComponentType<?>> DATA_COMPONENTS = DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, WhimsyMod.MOD_ID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> RANDOM_LEVEL = register(
            "random_level", pBuilder -> pBuilder.persistent(ExtraCodecs.NON_NEGATIVE_INT).networkSynchronized(ByteBufCodecs.VAR_INT)
    );

    private static <T> DeferredHolder<DataComponentType<?>,DataComponentType<T>> register(String name, UnaryOperator<DataComponentType.Builder<T>> pBuilder) {
        return DATA_COMPONENTS.register(name, () ->  pBuilder.apply(new DataComponentType.Builder<>()).build());
    }

    public static void register(IEventBus eventBus){
        DATA_COMPONENTS.register(eventBus);
    }
}
