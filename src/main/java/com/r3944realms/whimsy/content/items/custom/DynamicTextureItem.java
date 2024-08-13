package com.r3944realms.whimsy.content.items.custom;

import com.r3944realms.whimsy.content.components.ModDataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class DynamicTextureItem extends Item {
    public DynamicTextureItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @NotNull TooltipContext pContext, @NotNull List<Component> pTooltipComponents, @NotNull TooltipFlag pTooltipFlag) {
        String url = pStack.getComponents().get(ModDataComponents.DYNAMIC_TEXTURE_URL.get());
        if (url != null) {
            pTooltipComponents.add(Component.literal("URL: " + url));
        }
    }
}
