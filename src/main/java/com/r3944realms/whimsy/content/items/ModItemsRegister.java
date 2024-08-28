package com.r3944realms.whimsy.content.items;

import com.r3944realms.whimsy.WhimsyMod;
import com.r3944realms.whimsy.content.items.custom.DynamicTextureItem;
import com.r3944realms.whimsy.content.items.custom.MessageItem;
import com.r3944realms.whimsy.content.items.custom.TestTextureItem;
import com.r3944realms.whimsy.datagen.provider.attributes.ModJukeboxSongs;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.JukeboxSong;
import net.minecraft.world.item.Rarity;
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
    public static final Supplier<Item> TEST_ITEM =
            ModItemsRegister.register("test_item", () -> new MessageItem(new Item.Properties().stacksTo(1)));

    public static final Supplier<Item> DYNAMIC_TEXTURE_ITEM =
            ModItemsRegister.register("dynamic_texture_item", () -> new DynamicTextureItem(new Item.Properties().stacksTo(1)), false);

    public static final Supplier<Item> TEST_TEXTURE_ITEM =
            ModItemsRegister.register("test_texture_item", () -> new TestTextureItem(new Item.Properties().stacksTo(1)), false);

    public static final Supplier<Item> MUSIC_DISC_SANDS_OF_TIME =
            ModItemsRegister.register("music_disc_sands_of_time", () -> new Item(DistProperties(ModJukeboxSongs.SANDS_OF_TIME)), true);

    public static final Supplier<Item> MUSIC_DISC_HUB_MUSIC =
            ModItemsRegister.register("music_disc_hub_music", () -> new Item(DistProperties(ModJukeboxSongs.HUB_MUSIC)), true);

    public static final Supplier<Item> MUSIC_DISC_ACE_RACE =
            ModItemsRegister.register("music_disc_ace_race", () -> new Item(DistProperties(ModJukeboxSongs.ACE_RACE)), true);

    public static final Supplier<Item> MUSIC_DISC_GRID_RUNNERS =
            ModItemsRegister.register("music_disc_grid_runners", () -> new Item(DistProperties(ModJukeboxSongs.GRID_RUNNER)), true);

    public static final Supplier<Item> MUSIC_DISC_MELTDOWN =
            ModItemsRegister.register("music_disc_meltdown", () -> new Item(DistProperties(ModJukeboxSongs.MELTDOWN)), true);


    public static Supplier<Item> register(String name, Supplier<Item> supplier) {
        return register(name, supplier, true);
    }
    public static Item.Properties DistProperties(ResourceKey<JukeboxSong> song) {
        return new Item.Properties().stacksTo(1).rarity(Rarity.RARE).jukeboxPlayable(song);
    }
    public static Supplier<Item> register(String name, Supplier<Item> supplier, boolean shouldJoinSupplierLists) {
        Supplier<Item> supplierItem = ITEMS.register(name, supplier);
        if(shouldJoinSupplierLists) ITEM_SUPPLIER.add(supplierItem);
        return supplierItem;
    }

    @Deprecated(since = "1.0.0.3", forRemoval = true)
    public static String getName(Item items){
        return items.getDescriptionId();
    }
    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
