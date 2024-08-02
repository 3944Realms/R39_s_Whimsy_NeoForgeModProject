package com.r3944realms.whimsy.client.renderer.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.r3944realms.whimsy.client.mdoel.NewKidPlayerModel;
import net.minecraft.client.model.ParrotModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ParrotRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Parrot;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class NewParrotOnShoulderLayer<T extends Player> extends RenderLayer<T, NewKidPlayerModel<T>> {
    private final ParrotModel model;

    public NewParrotOnShoulderLayer(RenderLayerParent<T, NewKidPlayerModel<T>> pRenderer, EntityModelSet pModelSet) {
        super(pRenderer);
        this.model = new ParrotModel(pModelSet.bakeLayer(ModelLayers.PARROT));
    }

    public void render(
            @NotNull PoseStack pPoseStack,
            @NotNull MultiBufferSource pBuffer,
            int pPackedLight,
            @NotNull T pLivingEntity,
            float pLimbSwing,
            float pLimbSwingAmount,
            float pPartialTicks,
            float pAgeInTicks,
            float pNetHeadYaw,
            float pHeadPitch
    ) {
        this.render(pPoseStack, pBuffer, pPackedLight, pLivingEntity, pLimbSwing, pLimbSwingAmount, pNetHeadYaw, pHeadPitch, true);
        this.render(pPoseStack, pBuffer, pPackedLight, pLivingEntity, pLimbSwing, pLimbSwingAmount, pNetHeadYaw, pHeadPitch, false);
    }

    private void render(
            PoseStack pPoseStack,
            MultiBufferSource pBuffer,
            int pPackedLight,
            T pLivingEntity,
            float pLimbSwing,
            float pLimbSwingAmount,
            float pNetHeadYaw,
            float pHeadPitch,
            boolean pLeftShoulder
    ) {
        CompoundTag compoundtag = pLeftShoulder ? pLivingEntity.getShoulderEntityLeft() : pLivingEntity.getShoulderEntityRight();
        EntityType.byString(compoundtag.getString("id"))
                .filter(p_117294_ -> p_117294_ == EntityType.PARROT)
                .ifPresent(
                        p_262538_ -> {
                            pPoseStack.pushPose();
                            pPoseStack.translate(pLeftShoulder ? 0.4F : -0.4F, pLivingEntity.isCrouching() ? -1.3F : -1.5F, 0.0F);
                            Parrot.Variant parrot$variant = Parrot.Variant.byId(compoundtag.getInt("Variant"));
                            VertexConsumer vertexconsumer = pBuffer.getBuffer(this.model.renderType(ParrotRenderer.getVariantTexture(parrot$variant)));
                            this.model
                                    .renderOnShoulder(
                                            pPoseStack, vertexconsumer, pPackedLight, OverlayTexture.NO_OVERLAY, pLimbSwing, pLimbSwingAmount, pNetHeadYaw, pHeadPitch, pLivingEntity.tickCount
                                    );
                            pPoseStack.popPose();
                        }
                );
    }
}
