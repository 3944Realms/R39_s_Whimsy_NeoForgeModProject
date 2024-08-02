package com.r3944realms.whimsy.client.renderer.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.r3944realms.whimsy.client.mdoel.NewKidPlayerModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class NewArrowLayer<T extends LivingEntity, M extends NewKidPlayerModel<T>> extends NewStuckInBodyLayer<T, M> {
    private final EntityRenderDispatcher dispatcher;

    public NewArrowLayer(EntityRendererProvider.Context pContext, LivingEntityRenderer<T, M> pRenderer) {
        super(pRenderer);
        this.dispatcher = pContext.getEntityRenderDispatcher();
    }

    @Override
    protected int numStuck(T pEntity) {
        return pEntity.getArrowCount();
    }

    @Override
    protected void renderStuckItem(
            PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight, Entity pEntity, float pX, float pY, float pZ, float pPartialTick
    ) {
        float f = Mth.sqrt(pX * pX + pZ * pZ);
        Arrow arrow = new Arrow(pEntity.level(), pEntity.getX(), pEntity.getY(), pEntity.getZ(), ItemStack.EMPTY, null);
        arrow.setYRot((float)(Math.atan2(pX, pZ) * 180.0F / (float)Math.PI));
        arrow.setXRot((float)(Math.atan2(pY, f) * 180.0F / (float)Math.PI));
        arrow.yRotO = arrow.getYRot();
        arrow.xRotO = arrow.getXRot();
        this.dispatcher.render(arrow, 0.0, 0.0, 0.0, 0.0F, pPartialTick, pPoseStack, pBuffer, pPackedLight);
    }
}
