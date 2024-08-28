package com.r3944realms.whimsy.client.mdoel;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.client.model.data.ModelData;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@OnlyIn(Dist.CLIENT)
public class DynamicTextureItemBakedModel implements BakedModel {
    private BakedModel existingModel;

    public DynamicTextureItemBakedModel(BakedModel existingModel) {
        this.existingModel = existingModel;
    }
    @Override
    public @NotNull List<BakedQuad> getQuads(@Nullable BlockState pState, @Nullable Direction pDirection, @NotNull RandomSource pRandom) {
        return this.existingModel.getQuads(pState, pDirection, pRandom);
    }

    @Override
    public boolean useAmbientOcclusion() {
        return this.existingModel.useAmbientOcclusion();
    }

    @Override
    public boolean isGui3d() {
        return this.existingModel.isGui3d();
    }

    @Override
    public boolean usesBlockLight() {
        return this.existingModel.usesBlockLight();
    }

    @Override
    public boolean isCustomRenderer() {
        return true;
    }

    @Override
    public @NotNull TextureAtlasSprite getParticleIcon() {
        return this.existingModel.getParticleIcon();
    }


    @Override
    public @NotNull ItemOverrides getOverrides() {
        return this.existingModel.getOverrides();
    }

    @Override
    public @NotNull List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @NotNull RandomSource rand, @NotNull ModelData data, @Nullable RenderType renderType) {
        throw new AssertionError("This method should not be called");
    }

    @Override
    public @NotNull BakedModel applyTransform(@NotNull ItemDisplayContext transformType, @NotNull PoseStack poseStack, boolean applyLeftHandTransform) {
        if(transformType == ItemDisplayContext.FIRST_PERSON_LEFT_HAND || transformType == ItemDisplayContext.FIRST_PERSON_RIGHT_HAND ){
            return this;
        }
        else return this.existingModel.applyTransform(transformType, poseStack, applyLeftHandTransform);
    }
}
