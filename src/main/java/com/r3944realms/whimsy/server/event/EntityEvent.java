package com.r3944realms.whimsy.server.event;

import com.r3944realms.whimsy.WhimsyMod;
import com.r3944realms.whimsy.items.enchantment.ModEnchantmentEffectComponents;
import com.r3944realms.whimsy.items.enchantment.ModEnchantmentHelper;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;

@EventBusSubscriber(modid = WhimsyMod.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public class EntityEvent {
    @SubscribeEvent
    public static void onEntityDropEvent(LivingDropsEvent event) {
        LivingEntity entity = event.getEntity();
        Level level = entity.level();
        if(entity instanceof LivingEntity && level instanceof ServerLevel serverLevel) {
            entity.getAllSlots().forEach(slot -> {
                if (ModEnchantmentHelper.hasReferToEnchantment(serverLevel, slot, ModEnchantmentEffectComponents.CHANGE_ITEM)) {
                    ItemEntity drop = new ItemEntity(serverLevel, entity.getX(), entity.getY(), entity.getZ(), slot);
                    event.getDrops().add(drop);
                }
            });
        }
    }
}
