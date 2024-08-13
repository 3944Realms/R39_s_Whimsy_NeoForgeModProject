package com.r3944realms.whimsy.content.items.tooltip;

import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;

public record TestItemTooltip(ItemStack stack) implements TooltipComponent {
    public TestItemTooltip(ItemStack stack) {
        this.stack = stack;
    }
}
