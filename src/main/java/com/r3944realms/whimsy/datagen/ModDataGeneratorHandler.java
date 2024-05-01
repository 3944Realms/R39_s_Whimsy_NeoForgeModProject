package com.r3944realms.whimsy.datagen;

import com.r3944realms.whimsy.WhimsyMod;
import com.r3944realms.whimsy.utils.Enum.LanguageEnum;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataProvider;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = WhimsyMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModDataGeneratorHandler {
    @SubscribeEvent
    public static void genData(GatherDataEvent event) {
        CompletableFuture<HolderLookup.Provider> HolderFolder = event.getLookupProvider();

        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        /*Language Provider ENGLISH CHINESE(SIM/TRA)*/
        addLanguage(event, LanguageEnum.English, "en_us");
        addLanguage(event, LanguageEnum.SimpleChinese, "zh_cn");
        addLanguage(event, LanguageEnum.TraditionalChinese, "zh_tw");
        /*Item Model*/
        ItemModelGenerator(event, existingFileHelper);
        /*Recipe*/
        RecipeGenerator(event);
        //Forge Part
    }
    private static void addLanguage(GatherDataEvent event, LanguageEnum language, String lan_regex){
        event.getGenerator().addProvider(
                event.includeClient(),
                (DataProvider.Factory<ModLanguageProvider>) pOutput -> new ModLanguageProvider(pOutput, WhimsyMod.MOD_ID, lan_regex, language)
        );
    }
    private static void ItemModelGenerator(GatherDataEvent event, ExistingFileHelper helper) {
        event.getGenerator().addProvider(
                event.includeClient(),
                (DataProvider.Factory<ModItemModelProvider>) pOutput -> new ModItemModelProvider(pOutput, WhimsyMod.MOD_ID, helper)
        );
    }
    private static void RecipeGenerator(GatherDataEvent event) {
        event.getGenerator().addProvider(
                event.includeServer(),
                (DataProvider.Factory<ModRecipeProvider>) ModRecipeProvider::new
        );
    }
}
