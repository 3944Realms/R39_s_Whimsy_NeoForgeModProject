package com.r3944realms.whimsy.mixin.client.renderer;

import com.google.common.collect.Lists;
import com.r3944realms.whimsy.modInterface.player.PlayerCapacity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(LivingEntityRenderer.class)
public abstract class MixinLivingEntityRenderer<T extends LivingEntity, M extends EntityModel<T>> extends EntityRenderer<T> implements RenderLayerParent<T, M> {

    @Shadow
    public M model;
    @Shadow
    protected final List<RenderLayer<T, M>> layers = Lists.newArrayList();

    protected MixinLivingEntityRenderer(EntityRendererProvider.Context pContext, M pModel, float pShadowRadius) {
        super(pContext);
        this.model = pModel;
        this.shadowRadius = pShadowRadius;
    }

    @Override
    public @NotNull M getModel() {
        return this.model;
    }

    @Inject(
            method = {"shouldShowName(Lnet/minecraft/world/entity/LivingEntity;)Z"},
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;getInstance()Lnet/minecraft/client/Minecraft;", shift = At.Shift.AFTER),
            cancellable = true)
    protected void shouldShowSelfName(T pEntity, CallbackInfoReturnable<Boolean> cir) {
        LocalPlayer Self = Minecraft.getInstance().player;
        assert Self != null;
        if (pEntity == Self) {
            PlayerCapacity LSelf = (PlayerCapacity) Self;
            cir.setReturnValue(LSelf.Whimsy$GetSelfNameTagVisible() && Minecraft.renderNames());
        }
    }
    @Inject(
            method = {"shouldShowName(Lnet/minecraft/world/entity/LivingEntity;)Z"},
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;getTeam()Lnet/minecraft/world/scores/PlayerTeam;", shift = At.Shift.AFTER),
            cancellable = true)
    protected void setOtherPlayerNameTagHidden(T pEntity, CallbackInfoReturnable<Boolean> cir) {
        if (pEntity.getTeam() == null && pEntity instanceof Player) { //实体非Player类，不加pEntity instanceof Player判断对于其它实体会报转型报错【M】
            PlayerCapacity LSelf = (PlayerCapacity) Minecraft.getInstance().player;
            assert LSelf != null;
            cir.setReturnValue(!LSelf.Whimsy$GetOtherPlayerNameTagHidden());
        }
    }

}
