package com.r3944realms.whimsy.datagen.provider;

import com.r3944realms.whimsy.content.items.ModItemsRegister;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider {
    public ModRecipeProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> future) {
        super(pOutput, future);
    }

    @Override
    protected void buildRecipes(@NotNull RecipeOutput pRecipeOutput) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItemsRegister.TEST_ITEM.get())
                .define('$', Items.COMMAND_BLOCK)
                .define('#', Items.IRON_BLOCK)
                .pattern("###")
                .pattern("#$#")
                .pattern("###")
                .unlockedBy("has_commandBlock",has(Items.COMMAND_BLOCK))
                .save(pRecipeOutput);
//        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItemsRegister.TEST_ITEM.get())
//                .requires(Items.COMMAND_BLOCK)
//                .unlockedBy("has_commandBlock",has(Items.COMMAND_BLOCK))
//                .save(pRecipeOutput);
    }


}
