package com.r3944realms.whimsy.mixin.enchantmentBlock.main;

import com.r3944realms.whimsy.content.blocks.enchantmentBlock.BlockEnchantmentStorage;
import com.r3944realms.whimsy.utils.mixinHelper.InjectHelper;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.BrushItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;

@Mixin(BrushItem.class)
public abstract class MixinBrushItem extends Item {
    public MixinBrushItem(Properties properties) {
        super(properties);
    }

    @Override
    public boolean hurtEnemy(@NotNull ItemStack stack, @NotNull LivingEntity target, @NotNull LivingEntity attacker) {
        return super.hurtEnemy(stack, target, attacker);
    }

    @Inject(at = @At("HEAD"), method = "useOn")
    private void init(UseOnContext context, CallbackInfoReturnable<InteractionResult> cir) {
        if (!context.getLevel().isClientSide) {//只在服务端运行
            Level level = context.getLevel();
            if (context.getItemInHand().isEnchanted()) {//如果刷子有附魔
                if(Objects.equals(BlockEnchantmentStorage.getEnchantmentsAtPosition(level.dimension(),context.getClickedPos()), new ListTag())){//如果Pos位置方块没有附魔
//				ListTag enchantments = context.getItemInHand().getEnchantments();//获取刷子上的附魔信息列表
                    InjectHelper.addToList(context.getItemInHand(), context.getLevel(), context.getClickedPos());
//				BlockEnchantmentStorage.addBlockEnchantment(context.getClickedPos().immutable(), enchantments);//储存信息
                    EquipmentSlot equipmentSlot = context.getItemInHand().equals(Objects.requireNonNull(context.getPlayer()).getItemBySlot(EquipmentSlot.OFFHAND)) ? EquipmentSlot.OFFHAND : EquipmentSlot.MAINHAND;
//				context.getItemInHand().hurtAndBreak(1, context.getPlayer(), (userx) -> {
//					userx.broadcastBreakEvent(equipmentSlot);
//				});
                    context.getItemInHand().hurtAndBreak(1,context.getPlayer(),equipmentSlot);
                } else {//位置方块有附魔
                    ListTag oldEnchantments = BlockEnchantmentStorage.getEnchantmentsAtPosition(level.dimension(), context.getClickedPos());
                    BlockEnchantmentStorage.removeBlockEnchantment(level.dimension() ,context.getClickedPos().immutable());//先删除信息
//					ListTag enchantments = context.getItemInHand().getEnchantments();//获取刷子上的附魔信息列表
                    ListTag enchantments = InjectHelper.enchantmentsToNbtList(context.getItemInHand(), context.getClickedPos());
                    ListTag newEnchantments = Whimsy$MergeNbtLists(oldEnchantments, enchantments); // 合并附魔列表
                    BlockEnchantmentStorage.addBlockEnchantment(level.dimension(), context.getClickedPos().immutable(), newEnchantments);//储存信息
                }
            } else {//如果刷子没有附魔
                BlockEnchantmentStorage.removeBlockEnchantment(level.dimension(), context.getClickedPos().immutable());//删除信息
            }
        }
    }
    // 合并两个 NBT 列表
    @Unique
    private static ListTag Whimsy$MergeNbtLists(ListTag oldTagList, ListTag newTagList) {
        ListTag mergedList = new ListTag();
        for(int i = 0; i < oldTagList.size(); ++i) {
            for(int j = 0; j < newTagList.size(); ++j){
                if (oldTagList.getCompound(i).getString("id").equals(newTagList.getCompound(j).getString("id"))) {
                    oldTagList.remove(i);
                    mergedList.add(newTagList.getCompound(j));
                    newTagList.remove(j);
                    break;
                }
            }
            //1. old:[ %1 ^2 &3 *4 ] new:[ %3 &2 ]
            //2. old:[ ^2 &3 *4 ] new:[ &2 ]
        }
        mergedList.addAll(oldTagList);
        mergedList.addAll(newTagList);
        return mergedList;
    }
}
