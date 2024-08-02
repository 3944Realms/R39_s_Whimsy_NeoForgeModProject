package com.r3944realms.whimsy.server.event;

import com.r3944realms.whimsy.WhimsyMod;
import com.r3944realms.whimsy.items.enchantment.ModEnchantmentEffectComponents;
import com.r3944realms.whimsy.items.enchantment.ModEnchantmentHelper;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;

@EventBusSubscriber(modid = WhimsyMod.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public class EnchantmentEvent {
//    @SubscribeEvent
//    public static void onHitEntityEvent(AttackEntityEvent event) {
//        LivingEntity attackSourceEntity = event.getEntity();
//        Entity target = event.getTarget();
//        if(attackSourceEntity.level() instanceof ServerLevel serverLevel && target instanceof LivingEntity attacktargetEntity) {
//            ItemStack mainHandItem = attackSourceEntity.getMainHandItem();
//            if (ModEnchantmentHelper.hasChangeItemEnchantment(serverLevel, mainHandItem)) {
//                attacktargetEntity.setItemInHand(InteractionHand.MAIN_HAND,mainHandItem);
//                attackSourceEntity.setItemInHand(InteractionHand.MAIN_HAND,ItemStack.EMPTY);
//            }
//        }
//    }
    @SubscribeEvent
    public static void onDamageEntityEvent(LivingDamageEvent.Pre event) {
        LivingEntity damageTargetLivingEntity = event.getEntity();
        Entity damageSourceEntity = event.getSource().getEntity();
        if (damageTargetLivingEntity.level() instanceof ServerLevel serverLevel && damageSourceEntity instanceof LivingEntity damageSourceLivingEntity) {
            ItemStack weaponItem = damageSourceEntity.getWeaponItem();
            if (ModEnchantmentHelper.hasReferToEnchantment(serverLevel, weaponItem, ModEnchantmentEffectComponents.CHANGE_ITEM)) {
                ItemStack damageTargetEntityMainHandItem = damageTargetLivingEntity.getMainHandItem();
                damageSourceLivingEntity.setItemInHand(InteractionHand.MAIN_HAND, damageTargetEntityMainHandItem);
                damageTargetLivingEntity.setItemInHand(InteractionHand.MAIN_HAND, weaponItem);
            }
        }
    }
}
