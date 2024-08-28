package com.r3944realms.whimsy.client.mdoel;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public class CustomTextureBakedModel implements BakedModel {
    private final BakedModel originalModel;
    private final ResourceLocation customTexture;

    public CustomTextureBakedModel(BakedModel originalModel, ResourceLocation customTexture) {
        this.originalModel = originalModel;
        this.customTexture = customTexture;
    }

    /**
     * <code>getQuads</code> 方法用于获取模型的面片
     */
    @Override
    public @NotNull List<BakedQuad> getQuads(@Nullable BlockState pState, @Nullable Direction pDirection, @NotNull RandomSource pRandom) {
        List<BakedQuad> originalQuads = originalModel.getQuads(pState, pDirection, pRandom);
        List<BakedQuad> newQuads = new ArrayList<>();
        for (BakedQuad quad : originalQuads) {
            // 使用自定义材质的纹理图标替换原始材质
            BakedQuad newQuad = new BakedQuad(quad.getVertices(), quad.getTintIndex(), quad.getDirection(),
                    Minecraft.getInstance().getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(customTexture),
                    quad.isShade());
            newQuads.add(newQuad);
        }
        return newQuads;
    }

    /**
     * <code>useAmbientOcclusion</code> 方法用于判断是否使用环境遮蔽
     */
    @Override
    public boolean useAmbientOcclusion() {
        return originalModel.useAmbientOcclusion();
    }

    @Override
    public boolean isGui3d() {
        return originalModel.isGui3d();
    }

    @Override
    public boolean usesBlockLight() {
        return originalModel.usesBlockLight();
    }

    @Override
    public boolean isCustomRenderer() {
        return originalModel.isCustomRenderer();
    }

    @Override
    public @NotNull TextureAtlasSprite getParticleIcon() {
        return Minecraft.getInstance().getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(customTexture);
    }

    @Override
    public @NotNull ItemOverrides getOverrides() {
        return originalModel.getOverrides();
    }
}
