package com.r3944realms.whimsy.datagen;

import com.r3944realms.whimsy.blocks.ModBlocksRegister;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModBlockStatesProvider extends BlockStateProvider {
    public ModBlockStatesProvider(PackOutput output, String modId, ExistingFileHelper exFileHelper) {
        super(output, modId, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
//        this.simpleBlockItem(ModBlocksRegister.TEST_BLOCK.get(), cubeAll(ModBlocksRegister.TEST_BLOCK.get()));
    }
}
