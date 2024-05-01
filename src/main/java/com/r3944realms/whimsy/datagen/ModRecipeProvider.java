package com.r3944realms.whimsy.datagen;

import com.r3944realms.whimsy.items.ModItemsRegister;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;

public class ModRecipeProvider extends RecipeProvider {
    public ModRecipeProvider(PackOutput pOutput) {
        super(pOutput);
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
    }

}
