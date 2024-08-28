package com.r3944realms.whimsy.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.r3944realms.whimsy.client.event.RenderKidPlayerEvent;
import com.r3944realms.whimsy.client.mdoel.NewKidPlayerModel;
import net.minecraft.client.model.HumanoidArmorModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.numbers.StyledFormat;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.PlayerModelPart;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.scores.DisplaySlot;
import net.minecraft.world.scores.Objective;
import net.minecraft.world.scores.ReadOnlyScoreInfo;
import net.minecraft.world.scores.Scoreboard;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class NewKidPlayerRenderer extends PlayerRenderer {
    public NewKidPlayerRenderer(EntityRendererProvider.Context pContext, boolean pUSeSlimModel) {
        super(pContext,  pUSeSlimModel);
        this.addLayer(new HumanoidArmorLayer<>(
                this,
                new HumanoidArmorModel<>(pContext.bakeLayer(pUSeSlimModel ? ModelLayers.PLAYER_SLIM_INNER_ARMOR : ModelLayers.PLAYER_INNER_ARMOR)),
                new HumanoidArmorModel<>(pContext.bakeLayer(pUSeSlimModel ? ModelLayers.PLAYER_SLIM_OUTER_ARMOR : ModelLayers.PLAYER_OUTER_ARMOR)),
                pContext.getModelManager()
        ));
    }
    @Override
    public void render(@NotNull AbstractClientPlayer pEntity, float pEntityYaw, float pPartialTicks, @NotNull PoseStack pPoseStack, @NotNull MultiBufferSource pBuffer, int pPackedLight) {
        this.setModelProperties(pEntity);
        if (net.neoforged.neoforge.common.NeoForge.EVENT_BUS.post(new RenderKidPlayerEvent.Pre(pEntity, this, pPartialTicks, pPoseStack, pBuffer, pPackedLight)).isCanceled()) return;
        super.render(pEntity, pEntityYaw, pPartialTicks, pPoseStack, pBuffer, pPackedLight);
        net.neoforged.neoforge.common.NeoForge.EVENT_BUS.post(new RenderKidPlayerEvent.Post(pEntity, this, pPartialTicks, pPoseStack, pBuffer, pPackedLight));
    }
    private static HumanoidModel.ArmPose getArmPose(AbstractClientPlayer pPlayer, InteractionHand pHand) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        if (itemstack.isEmpty()) {
            return HumanoidModel.ArmPose.EMPTY;
        } else {
            if (pPlayer.getUsedItemHand() == pHand && pPlayer.getUseItemRemainingTicks() > 0) {
                UseAnim useanim = itemstack.getUseAnimation();
                if (useanim == UseAnim.BLOCK) {
                    return HumanoidModel.ArmPose.BLOCK;
                }

                if (useanim == UseAnim.BOW) {
                    return HumanoidModel.ArmPose.BOW_AND_ARROW;
                }

                if (useanim == UseAnim.SPEAR) {
                    return HumanoidModel.ArmPose.THROW_SPEAR;
                }

                if (useanim == UseAnim.CROSSBOW && pHand == pPlayer.getUsedItemHand()) {
                    return HumanoidModel.ArmPose.CROSSBOW_CHARGE;
                }

                if (useanim == UseAnim.SPYGLASS) {
                    return HumanoidModel.ArmPose.SPYGLASS;
                }

                if (useanim == UseAnim.TOOT_HORN) {
                    return HumanoidModel.ArmPose.TOOT_HORN;
                }

                if (useanim == UseAnim.BRUSH) {
                    return HumanoidModel.ArmPose.BRUSH;
                }
            } else if (!pPlayer.swinging && itemstack.getItem() instanceof CrossbowItem && CrossbowItem.isCharged(itemstack)) {
                return HumanoidModel.ArmPose.CROSSBOW_HOLD;
            }
            HumanoidModel.ArmPose forgeArmPose = net.neoforged.neoforge.client.extensions.common.IClientItemExtensions.of(itemstack).getArmPose(pPlayer, pHand, itemstack);
            if (forgeArmPose != null) return forgeArmPose;

            return HumanoidModel.ArmPose.ITEM;
        }
    }
    private void setModelProperties(AbstractClientPlayer pClientPlayer) {
        NewKidPlayerModel<AbstractClientPlayer> playerModel = (NewKidPlayerModel<AbstractClientPlayer>) this.getModel();
        if (pClientPlayer.isSpectator()) {
            playerModel.setAllVisible(false);
            playerModel.head.visible = true;
            playerModel.hat.visible = true;
        } else {
            playerModel.setAllVisible(true);
            playerModel.hat.visible = pClientPlayer.isModelPartShown(PlayerModelPart.HAT);
            playerModel.jacket.visible = pClientPlayer.isModelPartShown(PlayerModelPart.JACKET);
            playerModel.leftPants.visible = pClientPlayer.isModelPartShown(PlayerModelPart.LEFT_PANTS_LEG);
            playerModel.rightPants.visible = pClientPlayer.isModelPartShown(PlayerModelPart.RIGHT_PANTS_LEG);
            playerModel.leftSleeve.visible = pClientPlayer.isModelPartShown(PlayerModelPart.LEFT_SLEEVE);
            playerModel.rightSleeve.visible = pClientPlayer.isModelPartShown(PlayerModelPart.RIGHT_SLEEVE);
            playerModel.crouching = pClientPlayer.isCrouching();
            HumanoidModel.ArmPose humanoidmodel$armpose = getArmPose(pClientPlayer, InteractionHand.MAIN_HAND);
            HumanoidModel.ArmPose humanoidmodel$armpose1 = getArmPose(pClientPlayer, InteractionHand.OFF_HAND);
            if (humanoidmodel$armpose.isTwoHanded()) {
                humanoidmodel$armpose1 = pClientPlayer.getOffhandItem().isEmpty() ? HumanoidModel.ArmPose.EMPTY : HumanoidModel.ArmPose.ITEM;
            }

            if (pClientPlayer.getMainArm() == HumanoidArm.RIGHT) {
                playerModel.rightArmPose = humanoidmodel$armpose;
                playerModel.leftArmPose = humanoidmodel$armpose1;
            } else {
                playerModel.rightArmPose = humanoidmodel$armpose1;
                playerModel.leftArmPose = humanoidmodel$armpose;
            }
        }
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(AbstractClientPlayer pEntity) {
        return pEntity.getSkin().texture();
    }
    @Override
    protected void scale(@NotNull AbstractClientPlayer pClientPlayer, @NotNull PoseStack pPoseStack, float pScaleFactor) {
        float f = 0.5F;
        pPoseStack.scale(f, f, f);
    }
    @Override
    protected void renderNameTag(
            @NotNull AbstractClientPlayer pEntity, @NotNull Component pDisplayName, PoseStack pPoseStack, @NotNull MultiBufferSource pBufferSource, int pPackedLight, float pPartialTick
    ) {
        double d0 = this.entityRenderDispatcher.distanceToSqr(pEntity);
        pPoseStack.pushPose();
        if (d0 < 100.0) {
            Scoreboard scoreboard = pEntity.getScoreboard();
            Objective objective = scoreboard.getDisplayObjective(DisplaySlot.BELOW_NAME);
            if (objective != null) {
                ReadOnlyScoreInfo readOnlyScoreInfo = scoreboard.getPlayerScoreInfo(pEntity, objective);
                Component component = ReadOnlyScoreInfo.safeFormatValue(readOnlyScoreInfo, objective.numberFormatOrDefault(StyledFormat.NO_STYLE));
                super.renderNameTag(
                        pEntity,
                        Component.empty().append(component).append(CommonComponents.SPACE).append(objective.getDisplayName()),
                        pPoseStack,
                        pBufferSource,
                        pPackedLight,
                        pPartialTick
                );
                pPoseStack.translate(0.0, 9.0F * 1.15F * 0.025F, 0.0F);
            }
        }
        super.renderNameTag(pEntity, pDisplayName, pPoseStack, pBufferSource, pPackedLight, pPartialTick);
        pPoseStack.popPose();
    }
    public void renderRightHand(PoseStack pPoseStack, MultiBufferSource pBuffer, int pCombinedLight, AbstractClientPlayer pPlayer) {
        if(!net.neoforged.neoforge.client.ClientHooks.renderSpecificFirstPersonArm(pPoseStack, pBuffer, pCombinedLight, pPlayer, HumanoidArm.RIGHT))
            this.renderHand(pPoseStack, pBuffer, pCombinedLight, pPlayer, this.model.rightArm, this.model.rightSleeve);
    }

    public void renderLeftHand(PoseStack pPoseStack, MultiBufferSource pBuffer, int pCombinedLight, AbstractClientPlayer pPlayer) {
        if(!net.neoforged.neoforge.client.ClientHooks.renderSpecificFirstPersonArm(pPoseStack, pBuffer, pCombinedLight, pPlayer, HumanoidArm.LEFT))
            this.renderHand(pPoseStack, pBuffer, pCombinedLight, pPlayer, this.model.leftArm, this.model.leftSleeve);
    }

    private void renderHand(
            PoseStack pPoseStack, MultiBufferSource pBuffer, int pCombinedLight, AbstractClientPlayer pPlayer, ModelPart pRendererArm, ModelPart pRendererArmwear
    ) {
        NewKidPlayerModel<AbstractClientPlayer> playerModel = (NewKidPlayerModel<AbstractClientPlayer>) this.getModel();
        this.setModelProperties(pPlayer);
        playerModel.attackTime = 0.0F;
        playerModel.crouching = false;
        playerModel.swimAmount = 0.0F;
        playerModel.setupAnim(pPlayer, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        pRendererArm.xRot = 0.0F;
        ResourceLocation resourcelocation = pPlayer.getSkin().texture();
        pRendererArm.render(pPoseStack, pBuffer.getBuffer(RenderType.entitySolid(resourcelocation)), pCombinedLight, OverlayTexture.NO_OVERLAY);
        pRendererArmwear.xRot = 0.0F;
        pRendererArmwear.render(pPoseStack, pBuffer.getBuffer(RenderType.entityTranslucent(resourcelocation)), pCombinedLight, OverlayTexture.NO_OVERLAY);
    }
    protected void setupRotations(AbstractClientPlayer pEntity, @NotNull PoseStack pPoseStack, float pBob, float pYBodyRot, float pPartialTick, float pScale) {
        float f = pEntity.getSwimAmount(pPartialTick);
        float f1 = pEntity.getViewXRot(pPartialTick);
        if (pEntity.isFallFlying()) {
            super.setupRotations(pEntity, pPoseStack, pBob, pYBodyRot, pPartialTick, pScale);
            float f2 = (float)pEntity.getFallFlyingTicks() + pPartialTick;
            float f3 = Mth.clamp(f2 * f2 / 100.0F, 0.0F, 1.0F);
            if (!pEntity.isAutoSpinAttack()) {
                pPoseStack.mulPose(Axis.XP.rotationDegrees(f3 * (-90.0F - f1)));
            }

            Vec3 vec3 = pEntity.getViewVector(pPartialTick);
            Vec3 vec31 = pEntity.getDeltaMovementLerped(pPartialTick);
            double d0 = vec31.horizontalDistanceSqr();
            double d1 = vec3.horizontalDistanceSqr();
            if (d0 > 0.0 && d1 > 0.0) {
                double d2 = (vec31.x * vec3.x + vec31.z * vec3.z) / Math.sqrt(d0 * d1);
                double d3 = vec31.x * vec3.z - vec31.z * vec3.x;
                pPoseStack.mulPose(Axis.YP.rotation((float)(Math.signum(d3) * Math.acos(d2))));
            }
        } else if (f > 0.0F) {
            super.setupRotations(pEntity, pPoseStack, pBob, pYBodyRot, pPartialTick, pScale);
            float f4 = pEntity.isInWater() || pEntity.isInFluidType((fluidType, height) -> pEntity.canSwimInFluidType(fluidType)) ? -90.0F - pEntity.getXRot() : -90.0F;
            float f5 = Mth.lerp(f, 0.0F, f4);
            pPoseStack.mulPose(Axis.XP.rotationDegrees(f5));
            if (pEntity.isVisuallySwimming()) {
                pPoseStack.translate(0.0F, -1.0F, 0.3F);
            }
        } else {
            super.setupRotations(pEntity, pPoseStack, pBob, pYBodyRot, pPartialTick, pScale);
        }
    }
}
