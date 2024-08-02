package com.r3944realms.whimsy.mixin.client.player;

import com.r3944realms.whimsy.gamerule.ClientRender.MustOthersHiddenNameTag;
import com.r3944realms.whimsy.gamerule.GameruleRegistry;
import com.r3944realms.whimsy.player.PlayerCapacity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;
import java.util.Map;

@Mixin(Player.class)
public abstract class MixinPlayer extends LivingEntity implements PlayerCapacity {

    @SuppressWarnings({"WrongEntityDataParameterClass", "RedundantSuppression"})
    @Unique
    private static final EntityDataAccessor<Boolean> Whimsy$DATA_OTHERS_HIDDEN = SynchedEntityData.defineId(Player.class, EntityDataSerializers.BOOLEAN),
                        Whimsy$DATA_SELF_VISIBILITY = SynchedEntityData.defineId(Player.class, EntityDataSerializers.BOOLEAN);
    @Unique
    private HashMap<String, Boolean> Whimsy$SyncSetting;
    protected MixinPlayer(EntityType<? extends LivingEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.Whimsy$SyncSetting = null;//懒加载
    }
    @Inject(
            method = {"defineSynchedData"}, at = {@At("TAIL")}
    )
    private void defineSyncData (SynchedEntityData.Builder pBuilder, CallbackInfo ci) {
        pBuilder.define(Whimsy$DATA_SELF_VISIBILITY, false);
        pBuilder.define(Whimsy$DATA_OTHERS_HIDDEN, false);
    }

    @Override
    public String[] Whimsy$GetAllSettings() {
        return Whimsy$SyncSetting.keySet().toArray(new String[0]);
    }

    @Override
    public HashMap<String, Boolean> Whimsy$GetSettingMap() {
        if(this.Whimsy$SyncSetting == null) {
            this.Whimsy$SyncSetting = new HashMap<>();//TODO: 线程安全待分析
        }
        return Whimsy$SyncSetting;
    }

    @Override
    public void Whimsy$CopySetting(Player player) {
        this.Whimsy$SyncSetting = ((PlayerCapacity) player).Whimsy$GetSettingMap();
    }

    @Override
    public void Whimsy$SetOtherPlayerNameTagHidden(boolean isEnable) {
        Whimsy$GetSettingMap().put("Whimsy$ShouldHiddenOtherPlayerNameTag", isEnable);
        this.entityData.set(Whimsy$DATA_OTHERS_HIDDEN, isEnable);
    }

    @Override
    public boolean Whimsy$GetOtherPlayerNameTagHidden() {
        return this.entityData.get(Whimsy$DATA_OTHERS_HIDDEN) || GameruleRegistry.getGameruleBoolValue(this.level(), MustOthersHiddenNameTag.ID);
    }

    @Override
    public void Whimsy$SetSelfNameTagVisible(boolean visible) {
        Whimsy$GetSettingMap().put("Whimsy$ShouldShowSelfNameTag", visible);
        this.entityData.set(Whimsy$DATA_SELF_VISIBILITY, visible);
    }

    @Override
    public boolean Whimsy$GetSelfNameTagVisible() {
        return this.entityData.get(Whimsy$DATA_SELF_VISIBILITY);
    }

    @Override
    public void Whimsy$RemoveSetting(String Setting) {
        Whimsy$GetSettingMap().remove(Setting);
    }

    @Inject(
            method = {"addAdditionalSaveData"}, at = {@At("RETURN")}
    )
    private void addSaveData(CompoundTag pCompound, CallbackInfo ci) {
        CompoundTag pSettingTag = new CompoundTag();
        Map<String, Boolean> stringBooleanHashMap = Whimsy$GetSettingMap();
        for(String key : stringBooleanHashMap.keySet()) {
            pSettingTag.putBoolean(key, stringBooleanHashMap.get(key));
        }
        pCompound.put("Whimsy$Settings", pSettingTag);
    }
    @Inject(
            method = {"readAdditionalSaveData"}, at = {@At("RETURN")}
    )
    private void readSaveData(CompoundTag pCompound, CallbackInfo ci) {
        if(pCompound.contains("Whimsy$Settings")) {
            Map<String, Boolean> stringBooleanHashMap = Whimsy$GetSettingMap();
            for(String key : pCompound.getAllKeys()) {
                stringBooleanHashMap.put(key, pCompound.getBoolean(key));
            }
        }
    }


}
