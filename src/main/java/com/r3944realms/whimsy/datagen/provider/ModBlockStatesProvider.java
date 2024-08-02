package com.r3944realms.whimsy.datagen.provider;

import com.r3944realms.whimsy.WhimsyMod;
import com.r3944realms.whimsy.blocks.ModBlocksRegister;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModBlockStatesProvider extends BlockStateProvider {
    public ModBlockStatesProvider(PackOutput output, String modId, ExistingFileHelper exFileHelper) {
        super(output, modId, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        addWithHaveModel(ModBlocksRegister.TEST_BLOCK.get(), "test_block");
//        this.simpleBlockItem(ModBlocksRegister.TEST_BLOCK.get(), cubeAll(ModBlocksRegister.TEST_BLOCK.get()));
    }
    public void addWithHaveModel(Block block, String name) {
        var model_path = models().getExistingFile(ResourceLocation.fromNamespaceAndPath(WhimsyMod.MOD_ID, name));
        var model = new ConfiguredModel(model_path);
        getVariantBuilder(block).partialState().setModels(model);
        simpleBlockItem(block, model_path);
    }
}
