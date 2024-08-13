package com.r3944realms.whimsy.mixin.bothSide.player;

import com.r3944realms.whimsy.content.gamerules.ClientRender.MustOthersHiddenNameTag;
import com.r3944realms.whimsy.content.gamerules.GameruleRegistry;
import com.r3944realms.whimsy.modInterface.entity.IEntityExtension;
import com.r3944realms.whimsy.modInterface.player.PlayerCapacity;
import com.r3944realms.whimsy.modInterface.player.PlayerLeashable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Leashable;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.LeashFenceKnotEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

@Mixin(Player.class)
public abstract class MixinPlayer extends LivingEntity implements PlayerCapacity, PlayerLeashable {

    @Shadow public float bob;

    @Shadow public abstract void tick();

    @Unique
    @Nullable
    private Leashable.LeashData Whimsy$LeashData;

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
    @Inject(method = {"tick"}, at = {@At("HEAD")})
    private void tickForLeash(CallbackInfo ci) {
        if(!this.level().isClientSide) {
            Whimsy$tickLeash();
        }
        PlayerLeashable playerLeashable = this;
        Entity leashHolder = playerLeashable.getLeashHolder();
        if(leashHolder != null && Whimsy$LeashData != null) {
            Whimsy$UpdateLeash(Whimsy$LeashData.leashHolder, leashHolder);
            this.bob = 1.25f;
        } else if(leashHolder instanceof ServerPlayer) {
            this.bob = 1.0f;
        } else {
            this.bob = 0.6f;
        }
        //TODO:
    }
    @Unique
    protected void Whimsy$tickLeash() {
        if(this.Whimsy$LeashData == null) return;
        if(this.Whimsy$LeashData.delayedLeashInfo != null) {
            Whimsy$RestoreLeashFormSave();
        }
        float leashLength = 5.0f;
        assert this.Whimsy$LeashData.delayedLeashInfo != null;
        Entity entity = this.Whimsy$LeashData.delayedLeashInfo.left().isPresent() ? ((ServerLevel) this.level()).getEntity(this.Whimsy$LeashData.delayedLeashInfo.left().get()) : null;
        if(entity instanceof IEntityExtension iEntityExtension) {
            leashLength = iEntityExtension.getLeashLength();
        }
        if (entity != null) {
            if(!isAlive() || entity.isAlive() || distanceTo(entity) > Math.max(leashLength * 2.0f, 10.0f)){
                dropLeash(true, true);
            } else if(distanceTo(entity) > leashLength * 1.3f) {
                tick();
            }
        }
    }
    @Unique
    private static void Whimsy$UpdateLeash(Entity entity, Entity entity2) {
        if(entity == null || entity.level() != entity2.level())
            return;
        float leashLength = 5.0f;
        if(entity instanceof IEntityExtension iEntity) {
            leashLength = iEntity.getLeashLength();
        }
        float distance = entity.distanceTo(entity2);
        if(distance > leashLength) {
            double dX = (entity.getX() - entity2.getX()) / distance ;
            double dY = (entity.getY() - entity2.getY()) / distance ;
            double dZ = (entity.getZ() - entity2.getZ()) / distance ;
            entity2.moveTo(
                    entity2.position().add(
                            Math.copySign(dX * dX * 0.4d, dX),
                            Math.copySign(dY * dY * 0.4d, dY),
                            Math.copySign(dZ * dZ * 0.4d, dZ)
                    )
            );
        }

    }
    @Unique
    private void Whimsy$RestoreLeashFormSave() {
        assert this.Whimsy$LeashData != null;
        if(!(this.level() instanceof ServerLevel)) {
           return;
        }
        assert this.Whimsy$LeashData.delayedLeashInfo != null;
        if(this.Whimsy$LeashData.delayedLeashInfo.left().isPresent()) {
            Entity entity = ((ServerLevel) this.level()).getEntity(this.Whimsy$LeashData.delayedLeashInfo.left().get());
            if(entity != null) {
                setLeashedTo(entity, true);
                return;
            }
        } else if(this.Whimsy$LeashData.delayedLeashInfo.right().isPresent()) {
            setLeashedTo(LeashFenceKnotEntity.getOrCreateKnot(this.level(), this.Whimsy$LeashData.delayedLeashInfo.right().get()), true);
            return;
        }

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

    @SuppressWarnings("AddedMixinMembersNamePattern")
    @Override
    public boolean canBeLeashedInstantly(Player player) {
        return !isLeashed();
    }
    @Override
    public Entity getLeashHolder() {
        if (Whimsy$LeashData == null) return null;
        if (Whimsy$LeashData.leashHolder == null && Whimsy$LeashData.delayedLeashHolderId != 0 ) {
            Whimsy$LeashData.leashHolder = this.level().getEntity(Whimsy$LeashData.delayedLeashHolderId);
        }
        return Whimsy$LeashData.leashHolder;
    }

    @Override
    public Leashable.LeashData getLeashData(){
        return Whimsy$LeashData;
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
        CompoundTag pLeashTag = new CompoundTag();
        writeLeashData(pLeashTag, Whimsy$LeashData);
        pCompound.put("Whimsy$Leash", pLeashTag);
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
        if(pCompound.contains("Whimsy$Leash")) {
            Whimsy$LeashData = readLeashData(pCompound.getCompound("Whimsy$Leash"));
        }
    }


}
