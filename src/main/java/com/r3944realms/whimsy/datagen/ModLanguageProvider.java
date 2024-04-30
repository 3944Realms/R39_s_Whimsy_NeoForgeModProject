package com.r3944realms.whimsy.datagen;

import com.r3944realms.whimsy.datagen.LanguageData.KeyLanguage;
import com.r3944realms.whimsy.utils.Enum.LanguageEnum;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.common.data.LanguageProvider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.r3944realms.whimsy.datagen.LanguageData.KeyLanguage.*;

public class ModLanguageProvider extends LanguageProvider {
    private final LanguageEnum Language;
    private static Map<String, Map<LanguageEnum, String>> LanguageMAP;
    private static List<String> objects;
    public ModLanguageProvider(PackOutput output, String modId, String locale, LanguageEnum Lan) {
        super(output, modId, locale);
        this.Language = Lan;
        LanguageMAP = new HashMap<>();
        objects = new ArrayList<>();
        init();
    }
    private void init(){
       for (KeyLanguage key : KeyLanguage.values()) {
           addLanguage(key.getKey(), getEnglish(key), getSimpleChinese(key), getTraditionalChinese(key));
       }
    }

    private void addItemLanguage(Item item, String English, String SimpleChinese, String TraditionChinese) {
        addLanguage(item.getDescriptionId(), English, SimpleChinese, TraditionChinese);
    }
    private void addLanguage(String Key, String English, String SimpleChinese, String TraditionalChinese) {
        objects.add(Key);
        Map<LanguageEnum, String> LanMap = new HashMap<>();
        LanMap.put(LanguageEnum.English, English);
        LanMap.put(LanguageEnum.SimpleChinese, SimpleChinese);
        LanMap.put(LanguageEnum.TraditionalChinese, TraditionalChinese);
        LanguageMAP.put(Key, LanMap);
    }

    @Override
    protected void addTranslations() {
        objects.forEach(key -> {
            if(!LanguageMAP.containsKey(key)) return;
            add(key, LanguageMAP.get(key).get(Language));
        });
    }
}
