package com.r3944realms.whimsy.items;

import net.minecraft.world.item.Item;

import java.util.function.Supplier;

public class ModItems {
    public static final Supplier<Item> TEST_ITEM = ModItemsRegister.register("test_item", () -> new Item(new Item.Properties().stacksTo(1)));
}
