package com.r3944realms.whimsy.datagen;

import com.r3944realms.whimsy.WhimsyMod;
import com.r3944realms.whimsy.datagen.provider.*;
import com.r3944realms.whimsy.utils.Enum.LanguageEnum;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataProvider;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = WhimsyMod.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class ModDataGeneratorHandler {
    @SubscribeEvent
    public static void genData(GatherDataEvent event) {
        CompletableFuture<HolderLookup.Provider> HolderFolder = event.getLookupProvider();

        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        /*Language Provider ENGLISH CHINESE(SIM/TRA)*/
        addLanguage(event, LanguageEnum.English, "en_us");
        addLanguage(event, LanguageEnum.SimpleChinese, "zh_cn");
        addLanguage(event, LanguageEnum.TraditionalChinese, "zh_tw");
        addLanguage(event, LanguageEnum.LiteraryChinese, "lzh");
        /*Item Model*/
        ItemModelGenerator(event, existingFileHelper);
        /*DataPack*/
        ModDataPackBuiltinEntriesProvider(event, HolderFolder);
        /*Block States Register*/
        BlockStateGenerator(event, existingFileHelper);
        /*Recipe*/
        RecipeGenerator(event, HolderFolder);
        /*PaintingTag*/
        ModPaintVariantTagsProvider(event, HolderFolder, existingFileHelper);
        /*Advancement*/
        ModAdvancementProvider(event, HolderFolder, existingFileHelper);
        //Forge Part
    }
    private static void addLanguage(GatherDataEvent event, LanguageEnum language, String lan_regex){
        event.getGenerator().addProvider(
                event.includeClient(),
                (DataProvider.Factory<ModLanguageProvider>) pOutput -> new ModLanguageProvider(pOutput, WhimsyMod.MOD_ID, language)
        );
    }
    private static void ItemModelGenerator(GatherDataEvent event, ExistingFileHelper helper) {
        event.getGenerator().addProvider(
                event.includeClient(),
                (DataProvider.Factory<ModItemModelProvider>) pOutput -> new ModItemModelProvider(pOutput, WhimsyMod.MOD_ID, helper)
        );
    }
    private static void RecipeGenerator(GatherDataEvent event, CompletableFuture<HolderLookup.Provider> future) {
        event.getGenerator().addProvider(
                event.includeServer(),
                (DataProvider.Factory<ModRecipeProvider>) pOutput -> new ModRecipeProvider(pOutput, future)
        );
    }
    private static void BlockStateGenerator(GatherDataEvent event, ExistingFileHelper helper) {
        event.getGenerator().addProvider(
                event.includeClient(),
                (DataProvider.Factory<ModBlockStatesProvider>) pOutput -> new ModBlockStatesProvider(pOutput, WhimsyMod.MOD_ID, helper)
        );
    }
    private static void ModDataPackBuiltinEntriesProvider(GatherDataEvent event, CompletableFuture<HolderLookup.Provider> pLookUpProvider) {
        event.getGenerator().addProvider(
                event.includeServer(),
                (DataProvider.Factory<ModDataPackBuiltInEntriesProvider>) pOutput -> new ModDataPackBuiltInEntriesProvider(pOutput, pLookUpProvider)
        );
    }
    private static void ModPaintVariantTagsProvider(GatherDataEvent event, CompletableFuture<HolderLookup.Provider> completableFuture, ExistingFileHelper helper) {
        event.getGenerator().addProvider(
                event.includeServer(),
                (DataProvider.Factory<ModPaintingVariantTagsProvider>) pOutput -> {
                  return new ModPaintingVariantTagsProvider(pOutput, completableFuture, WhimsyMod.MOD_ID, helper);
                }
        );
    }
    private static void ModAdvancementProvider(GatherDataEvent event, CompletableFuture<HolderLookup.Provider> pLookUpProvider, ExistingFileHelper helper) {
        event.getGenerator().addProvider(
                event.includeServer(),
                (DataProvider.Factory<ModAdvancementProvider>) pOutput -> {
                    try {
                        Thread.sleep(1000);//最Low的方法，目前沒想到其他方法來更好的控制，只能拉延遲
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    return new ModAdvancementProvider(pOutput, pLookUpProvider, helper);
                }
        );
    }
}
