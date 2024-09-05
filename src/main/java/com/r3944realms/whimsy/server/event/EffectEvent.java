package com.r3944realms.whimsy.server.event;

import com.r3944realms.whimsy.WhimsyMod;
import com.r3944realms.whimsy.content.advancements.ModCriteriaTriggers;
import com.r3944realms.whimsy.content.blocks.enchantmentBlock.BlockEnchantmentStorage;
import com.r3944realms.whimsy.content.components.ModDataComponents;
import com.r3944realms.whimsy.content.items.enchantment.ModEnchantmentEffectComponents;
import com.r3944realms.whimsy.content.items.enchantment.ModEnchantmentHelper;
import com.r3944realms.whimsy.datagen.provider.attributes.ModEnchantments;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.component.PatchedDataComponentMap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.player.AttackEntityEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

import java.util.Set;


@EventBusSubscriber(modid = WhimsyMod.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public class EffectEvent {
    @SubscribeEvent
    public static void onHitEntityEvent(AttackEntityEvent event) {
        Player attackSourceEntity = event.getEntity();
        Entity target = event.getTarget();
        if(attackSourceEntity.level() instanceof ServerLevel && target instanceof LivingEntity) {
            ItemStack mainHandItem = attackSourceEntity.getMainHandItem();
            ItemEnchantments enchantments = EnchantmentHelper.getEnchantmentsForCrafting(mainHandItem);
            ModCriteriaTriggers.ENCHANTMENT_ITEM_USED_TRIGGER.get().trigger((ServerPlayer) attackSourceEntity, mainHandItem, enchantments);
        }
    }
    @SubscribeEvent
    public static void onDamageEntityEvent(LivingDamageEvent.Pre event) {
        LivingEntity damageTargetLivingEntity = event.getEntity();
        Entity damageSourceEntity = event.getSource().getEntity();
        if (damageTargetLivingEntity.level() instanceof ServerLevel serverLevel && damageSourceEntity instanceof LivingEntity damageSourceLivingEntity) {
            ItemStack weaponItem = damageSourceEntity.getWeaponItem();
            if((damageSourceLivingEntity instanceof Player || damageSourceLivingEntity instanceof Monster) && (damageTargetLivingEntity instanceof Player || damageTargetLivingEntity instanceof Monster)) {
                if(((LivingEntity) damageSourceEntity).isBaby())
                    if (ModEnchantmentHelper.hasDesignatedEnchantment(serverLevel, weaponItem, ModEnchantmentEffectComponents.CHANGE_ITEM)) {
                        ItemStack damageTargetEntityMainHandItem = damageTargetLivingEntity.getMainHandItem();
                        damageSourceLivingEntity.setItemInHand(InteractionHand.MAIN_HAND, damageTargetEntityMainHandItem);
                        damageTargetLivingEntity.setItemInHand(InteractionHand.MAIN_HAND, weaponItem);
                    }
            }
        }
    }
    @SubscribeEvent
    public static void onRightItemUseEvent(PlayerInteractEvent.RightClickItem event) {
        if(event.getLevel() instanceof ServerLevel serverLevel && event.getItemStack().isEnchanted()) {
            ItemStack itemStack = event.getItemStack();
            if(ModEnchantmentHelper.hasDesignatedEnchantment(serverLevel, itemStack, ModEnchantmentEffectComponents.RANDOM_ENCHANTMENT)) {
                Object2IntMap.Entry<Holder<Enchantment>> random = ModEnchantmentHelper.getDesignatedEnchantment(serverLevel, itemStack, ModEnchantmentEffectComponents.RANDOM_ENCHANTMENT);
                assert random != null;
                ModEnchantmentHelper.randomEnchantments(random.getIntValue(), itemStack);
                EnchantmentHelper.setEnchantments(itemStack, ModEnchantmentHelper.setDesignatedItemEnchantmentsLevelAndGet(itemStack, ModEnchantments.RANDOM_ENCHANTMENT, 0));
                DataComponentPatch patch = DataComponentPatch.builder()
                        .set(ModDataComponents.RANDOM_LEVEL.get(), random.getIntValue()).build();
                ((PatchedDataComponentMap) itemStack.getComponents()).applyPatch(patch);
            }

        }
    }
    //ITEM store StartPos
    @SubscribeEvent
    public static void onPlayerAttack(PlayerInteractEvent.LeftClickBlock event){
        if(event.getLevel() instanceof ServerLevel) {
            BlockPos startPos = null;
            Player player = event.getEntity();
            Level world  = event.getLevel();
            InteractionHand hand = event.getHand();
            BlockPos pos = event.getPos();
            Iterable<ItemStack> handItemStacks = player.getAllSlots();
            for (ItemStack itemstack : handItemStacks) {
                if (itemstack.is(Items.BRUSH)) {
                    if (itemstack.isEnchanted()) {//有附魔，全图获取
                        if (startPos == null) {
                            startPos = pos ;
                        } else {
                            brushAllBlocks(world,startPos, pos, itemstack);
                            startPos = null;
                        }
                    } else {//没附魔，清除附魔方块
                        if (startPos == null) {
                            startPos = pos;
                        } else {
                            clearAllBlocks(world,startPos, pos);
                            startPos = null;
                        }
                    }
                }
            }
        }

    }
    private static void brushAllBlocks(Level world, BlockPos startPos, BlockPos pos, ItemStack itemStack) {
        // 获取立方体对角方块的坐标
        int minX = Math.min(startPos.getX(), pos.getX());
        int minY = Math.min(startPos.getY(), pos.getY());
        int minZ = Math.min(startPos.getZ(), pos.getZ());
        int maxX = Math.max(startPos.getX(), pos.getX());
        int maxY = Math.max(startPos.getY(), pos.getY());
        int maxZ = Math.max(startPos.getZ(), pos.getZ());

        // 遍历立方体内的所有方块
        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                for (int z = minZ; z <= maxZ; z++) {
                    BlockPos currentPos = new BlockPos(x, y, z);
                    BlockState blockState = world.getBlockState(currentPos);

                    // 排除空气、水、岩浆等特定方块
                    if (blockState.is(Blocks.AIR) ||
                            blockState.is(Blocks.WATER) ||
                            blockState.is(Blocks.LAVA)) {
                        continue;
                    }

                    // 在这里对满足条件的方块进行处理
                    ItemEnchantments itemEnchantments = itemStack.getOrDefault(DataComponents.ENCHANTMENTS, ItemEnchantments.EMPTY);

                    ListTag enchantmentNbtList = new ListTag();
                    Set<Object2IntMap.Entry<Holder<Enchantment>>> entries = itemEnchantments.entrySet();
                    for (Object2IntMap.Entry<Holder<Enchantment>> entry : entries) {
                        Holder<Enchantment> key = entry.getKey();
                        int intValue = entry.getIntValue();


                        CompoundTag enchantmentNbt = new CompoundTag();
                        enchantmentNbt.putString("id",String.valueOf(key.getKey()));
                        enchantmentNbt.putInt("lvl",intValue);
                        enchantmentNbtList.add(enchantmentNbt);

                    }
                    BlockEnchantmentStorage.addBlockEnchantment(world.dimension(), currentPos, enchantmentNbtList);
                }
            }
        }
    }

    private static void clearAllBlocks(Level world,BlockPos startPos, BlockPos pos) {
        // 获取立方体对角方块的坐标
        int minX = Math.min(startPos.getX(), pos.getX());
        int minY = Math.min(startPos.getY(), pos.getY());
        int minZ = Math.min(startPos.getZ(), pos.getZ());
        int maxX = Math.max(startPos.getX(), pos.getX());
        int maxY = Math.max(startPos.getY(), pos.getY());
        int maxZ = Math.max(startPos.getZ(), pos.getZ());

        // 遍历立方体内的所有方块
        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                for (int z = minZ; z <= maxZ; z++) {
                    BlockPos currentPos = new BlockPos(x, y, z);
                    BlockState blockState = world.getBlockState(currentPos);

                    // 在这里对满足条件的方块进行处理
                    BlockEnchantmentStorage.removeBlockEnchantment(world.dimension(), currentPos);
//                    ExampleMod.LOGGER.info("Found block: " + blockState.getBlock().getTranslationKey() + " at " + currentPos);
                }
            }
        }
    }



}
