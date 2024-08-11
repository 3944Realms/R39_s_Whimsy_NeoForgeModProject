package com.r3944realms.whimsy.server.event;

import com.r3944realms.whimsy.WhimsyMod;
import com.r3944realms.whimsy.datagen.provider.attributes.ModDataComponents;
import com.r3944realms.whimsy.datagen.provider.attributes.ModEnchantments;
import com.r3944realms.whimsy.items.enchantment.ModEnchantmentEffectComponents;
import com.r3944realms.whimsy.items.enchantment.ModEnchantmentHelper;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.item.ItemTossEvent;
import net.neoforged.neoforge.event.entity.living.LivingDropsEvent;

@EventBusSubscriber(modid = WhimsyMod.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public class EntityEvent {
    @SubscribeEvent
    public static void onEntityDropEvent(LivingDropsEvent event) {
        LivingEntity entity = event.getEntity();
        Level level = entity.level();
        if(entity instanceof LivingEntity && level instanceof ServerLevel serverLevel) {
            entity.getAllSlots().forEach(slot -> {
                if (ModEnchantmentHelper.hasDesignatedEnchantment(serverLevel, slot, ModEnchantmentEffectComponents.CHANGE_ITEM)) {
                    ItemEntity drop = new ItemEntity(serverLevel, entity.getX(), entity.getY(), entity.getZ(), slot);
                    event.getDrops().add(drop);
                }
            });
        }
    }
    @SubscribeEvent
    public static void onItemDropEvent(ItemTossEvent event) {
        if(event.getPlayer().level() instanceof ServerLevel serverLevel && event.getEntity().getItem().isEnchanted()) {
            ItemStack item = event.getEntity().getItem();
            Integer i = item.get(ModDataComponents.RANDOM_LEVEL.get());
            if(i != null && i > 0) {
                EnchantmentHelper.setEnchantments(item, ItemEnchantments.EMPTY);
                EnchantmentHelper.setEnchantments(item, ModEnchantmentHelper.setDesignatedItemEnchantmentsLevelAndGet(item, ModEnchantments.RANDOM_ENCHANTMENT, i));

            }
        }
    }
}
