package com.r3944realms.whimsy.items;

import com.r3944realms.whimsy.WhimsyMod;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class ModItemsRegister {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(BuiltInRegistries.ITEM, WhimsyMod.MOD_ID);
    public static final List<Supplier<Item>> ITEM_SUPPLIER = new ArrayList<>();

    /*Register Item*/
    public static final Supplier<Item> TEST_ITEM = ModItemsRegister.register("test_item", () -> new Item(new Item.Properties().stacksTo(1)));

    public static Supplier<Item> register(String name, Supplier<Item> supplier) {
        Supplier<Item> supplierItem = ITEMS.register(name, supplier);
        ITEM_SUPPLIER.add(supplierItem);
        return supplierItem;
    }
    public static String getName(Item items){
        return items.getDescriptionId();
    }
    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}