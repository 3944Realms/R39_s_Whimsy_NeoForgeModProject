package com.r3944realms.whimsy.datagen.LanguageData;

import com.r3944realms.whimsy.items.ModItems;

public enum KeyLanguage {
    TEST_ITEM(ModItems.TEST_ITEM.get().getDescriptionId(),"Test item", "测试物品", "測試物品");
    public final String Key;
    public final String US_EN;
    public final String SIM_CN;
    public final String TRA_CN;
    KeyLanguage(String key, String US_EN, String SIM_CN, String TRA_CN) {
        this.Key = key;
        this.US_EN = US_EN;
        this.SIM_CN = SIM_CN;
        this.TRA_CN = TRA_CN;
    }
    public static String getKey(KeyLanguage key) {
        return key.Key;
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
}
