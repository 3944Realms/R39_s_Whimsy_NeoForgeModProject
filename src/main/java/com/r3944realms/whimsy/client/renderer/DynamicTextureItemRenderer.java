package com.r3944realms.whimsy.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class DynamicTextureItemRenderer extends BlockEntityWithoutLevelRenderer {
    private static int degree = 0;
    public DynamicTextureItemRenderer() {
        super(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());
    }

    @Override
    public void renderByItem(@NotNull ItemStack pStack, @NotNull ItemDisplayContext pDisplayContext, @NotNull PoseStack pPoseStack, @NotNull MultiBufferSource pBuffer, int pPackedLight, int pPackedOverlay) {
        if(degree == 360) {
            degree = 0;
        }
        degree ++;
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        BakedModel bakedModel = itemRenderer.getModel(pStack, null, null, 1);
        pPoseStack.pushPose();
        pPoseStack.translate(0.5, 0.5, 0.5);
        float xOffset = -1 / 32F;
        float zOffset = 0;
        pPoseStack.translate(-xOffset, 0, - zOffset);
        pPoseStack.mulPose(Axis.YP.rotationDegrees(degree));
        pPoseStack.translate(xOffset, 0, zOffset);
        itemRenderer.render(pStack, ItemDisplayContext.NONE, false, pPoseStack, pBuffer, pPackedLight, pPackedOverlay, bakedModel);
        pPoseStack.popPose();
    }
}
