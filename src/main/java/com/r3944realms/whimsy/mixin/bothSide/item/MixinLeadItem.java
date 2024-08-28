package com.r3944realms.whimsy.mixin.bothSide.item;

import com.r3944realms.whimsy.modInterface.player.PlayerLeashable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Leashable;
import net.minecraft.world.entity.decoration.LeashFenceKnotEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.LeadItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(LeadItem.class)
public abstract class MixinLeadItem extends Item {
    public MixinLeadItem(Properties pProperties) {
        super(pProperties);
    }
    @Inject(
            method = {"bindPlayerMobs"},
            at = @At("HEAD"),
            cancellable = true)
    private static void selfLeash(Player pPlayer, Level pLevel, BlockPos pPos, CallbackInfoReturnable<InteractionResult> cir) {
        List<Leashable> list = LeadItem.leashableInArea(pLevel, pPos, p_353025_ -> p_353025_.getLeashHolder() == pPlayer);
        if (list.isEmpty()) {
            ItemStack mainHandItem = pPlayer.getMainHandItem();
            if (!(mainHandItem.getItem() instanceof LeadItem )) {
                return;
            }
            if(!pPlayer.isCreative()) mainHandItem.shrink(1);
            LeashFenceKnotEntity leashfenceknotentity;
            PlayerLeashable self = (PlayerLeashable) pPlayer;
            leashfenceknotentity = LeashFenceKnotEntity.getOrCreateKnot(pLevel, pPos);
            leashfenceknotentity.playPlacementSound();
            self.setLeashedTo(leashfenceknotentity, true);
            pLevel.gameEvent(GameEvent.BLOCK_ATTACH, pPos, GameEvent.Context.of(pPlayer));
            cir.setReturnValue(InteractionResult.SUCCESS);
        }
    }

}
