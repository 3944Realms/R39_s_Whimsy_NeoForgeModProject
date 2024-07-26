package com.r3944realms.whimsy.mixin.server;

import com.mojang.authlib.GameProfile;
import com.r3944realms.whimsy.player.ServerPlayerCapacity;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayer.class)
public abstract class MixinServerPlayer extends Player implements ServerPlayerCapacity {
    @Shadow protected abstract void completeUsingItem();

    @Unique
    int Whimsy$TalkArea;
    @Unique
    int Whimsy$TalkAreaPreference;

    public MixinServerPlayer(Level pLevel, BlockPos pPos, float pYRot, GameProfile pGameProfile) {

        super(pLevel, pPos, pYRot, pGameProfile);
    }

    @Override
    public void Whimsy$SetTalkArea(int talkArea) {
        Whimsy$TalkArea = talkArea;
    }

    @Override
    public int Whimsy$GetTalkArea() {
        return Whimsy$TalkArea;
    }

    @Override
    public void Whimsy$SetTalkAreaPreference(int talkAreaPreference) {
        Whimsy$TalkAreaPreference = talkAreaPreference;
    }

    @Override
    public int Whimsy$GetTalkAreaPreference() {
        return Whimsy$TalkAreaPreference;
    }
    @Inject(method = {"tick()V"}, at = {@At("TAIL")})
    private void tick(CallbackInfo callbackInfo) {

    }
    @Inject(method = {"<init>"}, at = {@At("TAIL")})
    private void init(CallbackInfo callbackInfo) {
        this.Whimsy$TalkArea = -1;
        this.Whimsy$TalkAreaPreference = -1;
    }

    /**
     * 在玩家死亡后重生时，将旧玩家的数据恢复到当前玩家对象上
     */
    @Inject(method = {"restoreFrom"}, at = {@At("TAIL")})
    private void restoreFrom(ServerPlayer serverPlayer, boolean pKeepEverything, CallbackInfo callbackInfo) {
        Whimsy$TalkArea = ((ServerPlayerCapacity)serverPlayer).Whimsy$GetTalkArea();
        Whimsy$TalkAreaPreference = ((ServerPlayerCapacity)serverPlayer).Whimsy$GetTalkAreaPreference();
    }


    @Inject(method = {"readAdditionalSaveData"}, at = {@At("RETURN")})
    private void readSaveData(CompoundTag compoundTag, CallbackInfo callbackInfo) {
        if(compoundTag.contains("Whimsy$TalkArea")) {
            Whimsy$SetTalkArea(compoundTag.getInt("Whimsy$TalkArea"));
        }
        if(compoundTag.contains("Whimsy$TalkAreaPreference")) {
            Whimsy$SetTalkAreaPreference(compoundTag.getInt("Whimsy$TalkAreaPreference"));
        }
    }
    @Inject(method = {"addAdditionalSaveData"}, at = {@At("RETURN")})
    private void addSaveData(CompoundTag compoundTag, CallbackInfo callbackInfo) {
        compoundTag.putInt("Whimsy$TalkArea", Whimsy$TalkArea);
        compoundTag.putInt("Whimsy$TalkAreaPreference", Whimsy$TalkAreaPreference);
    }
}
