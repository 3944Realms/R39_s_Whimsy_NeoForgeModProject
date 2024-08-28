package com.r3944realms.whimsy.content.entities;

import com.r3944realms.whimsy.WhimsyMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModEntitiesRegister {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPE = DeferredRegister.create(Registries.ENTITY_TYPE, WhimsyMod.MOD_ID);

    public static void register(IEventBus eventBus) {
        ENTITY_TYPE.register(eventBus);
    }
}
