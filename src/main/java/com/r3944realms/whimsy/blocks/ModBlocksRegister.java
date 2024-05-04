package com.r3944realms.whimsy.blocks;

import com.r3944realms.whimsy.WhimsyMod;
import com.r3944realms.whimsy.blocks.custom.TestBlock;
import com.r3944realms.whimsy.items.ModItemsRegister;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlocksRegister {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Registries.BLOCK, WhimsyMod.MOD_ID);

    public static final Supplier<Block> TEST_BLOCK = registerBlock("test_block", () -> new TestBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS)));
    public static Supplier<Block> registerBlock(String name, Supplier<Block> block) {
        Supplier<Block> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }
    public static void registerBlockItem(String name, Supplier<Block> block) {
        registerBlockItem(name, block,new Item.Properties());
    }
    public static void registerBlockItem(String name, Supplier<Block> block, Item.Properties properties) {
        ModItemsRegister.register(name, () -> new BlockItem(block.get(), properties));
    }
    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
