package com.r3944realms.whimsy.client.tooltip;

import com.r3944realms.whimsy.content.items.tooltip.TestItemTooltip;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

public class TestItemClientTooltipComponent implements ClientTooltipComponent {
    private static final ResourceLocation BACKGROUND_SPRITE = ResourceLocation.withDefaultNamespace("container/bundle/background");
    private final ItemStack stack;
    private int width;
    public TestItemClientTooltipComponent(TestItemTooltip tooltip) {
        this.stack = tooltip.stack();
   }

    @Override
    public void renderImage(@NotNull Font pFont, int pX, int pY, GuiGraphics pGuiGraphics) {
        pGuiGraphics.blitSprite(BACKGROUND_SPRITE, pX, pY, 20, 22);
        this.blit(pGuiGraphics, pX + 1, pY + 1, TestItemClientTooltipComponent.Texture.SLOT);
        pGuiGraphics.renderItem(stack, pX + 1, pY + 1);
    }
    private void blit(GuiGraphics pGuiGraphics, int pX, int pY, TestItemClientTooltipComponent.Texture pTexture) {
        pGuiGraphics.blitSprite(pTexture.sprite, pX, pY, 0, pTexture.w, pTexture.h);
    }
    @Override
    public int getHeight() {
        return 26;
    }

    @Override
    public int getWidth(@NotNull Font pFont) {
        return width;
    }
    @OnlyIn(Dist.CLIENT)
    enum Texture {
        BLOCKED_SLOT(ResourceLocation.withDefaultNamespace("container/bundle/blocked_slot"), 18, 20),
        SLOT(ResourceLocation.withDefaultNamespace("container/bundle/slot"), 18, 20);

        public final ResourceLocation sprite;
        public final int w;
        public final int h;

        private Texture(ResourceLocation pSprite, int pW, int pH) {
            this.sprite = pSprite;
            this.w = pW;
            this.h = pH;
        }
    }
}
