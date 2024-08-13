package com.r3944realms.whimsy.content.components;

import com.mojang.serialization.Codec;
import com.r3944realms.whimsy.WhimsyMod;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.util.ExtraCodecs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.UnaryOperator;

public class ModDataComponents {
    public static DeferredRegister<DataComponentType<?>> DATA_COMPONENTS = DeferredRegister.create(Registries.DATA_COMPONENT_TYPE, WhimsyMod.MOD_ID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Integer>> RANDOM_LEVEL = register(
            "random_level", pBuilder -> pBuilder.persistent(ExtraCodecs.NON_NEGATIVE_INT).networkSynchronized(ByteBufCodecs.VAR_INT)
    );
    public static final DeferredHolder<DataComponentType<?>, DataComponentType<String>> DYNAMIC_TEXTURE_URL = register(
      "dynamic_texture_url", pBuilder -> pBuilder.persistent(Codec.STRING).networkSynchronized(ByteBufCodecs.STRING_UTF8)
    );

    private static <T> DeferredHolder<DataComponentType<?>,DataComponentType<T>> register(String name, UnaryOperator<DataComponentType.Builder<T>> pBuilder) {
        return DATA_COMPONENTS.register(name, () ->  pBuilder.apply(new DataComponentType.Builder<>()).build());
    }


    public static void register(IEventBus eventBus){
        DATA_COMPONENTS.register(eventBus);
    }
}
