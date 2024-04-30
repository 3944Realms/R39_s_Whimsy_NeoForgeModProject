package com.r3944realms.whimsy.datagen.LanguageData;

import com.r3944realms.whimsy.items.ModItemsRegister;
import net.minecraft.world.item.Item;

import java.util.function.Supplier;

public enum KeyLanguage {
    TEST_ITEM(ModItemsRegister.TEST_ITEM,"Test item", "测试物品", "測試物品");
    private final Supplier<Item> itemSupplier;
    private String key;
    private final String US_EN;
    private final String SIM_CN;
    private final String TRA_CN;
    KeyLanguage(Supplier<Item> itemSupplier,String US_EN, String SIM_CN, String TRA_CN) {
        this.itemSupplier = itemSupplier;
        this.US_EN = US_EN;
        this.SIM_CN = SIM_CN;
        this.TRA_CN = TRA_CN;
    }
    public static String getEnglish(KeyLanguage key) {
        return key.US_EN;
    }
    public static String getSimpleChinese(KeyLanguage key) {
        return key.SIM_CN;
    }
    public static String getTraditionalChinese(KeyLanguage key) {
        return key.TRA_CN;
    }
    public String getKey() {
        if(key == null){
            key = itemSupplier.get().getDescriptionId();
        }
        return key;
    }
}
