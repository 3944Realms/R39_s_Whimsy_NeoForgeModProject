package com.r3944realms.whimsy.datagen.LanguageData;

import com.r3944realms.whimsy.items.ModItemsRegister;
import com.r3944realms.whimsy.utils.Enum.ModPartEnum;
import com.r3944realms.whimsy.utils.ModAnnotation.NeedCompletedInFuture;
import net.minecraft.world.item.Item;

import java.util.function.Supplier;

public enum ModKeyValue {
    TEST_ITEM(ModItemsRegister.TEST_ITEM, ModPartEnum.ITEMS,"Test item", "测试物品", "測試物品", true);
    private final Supplier<?> supplier;
    private String key;
    private final String US_EN;
    private final String SIM_CN;
    private final String TRA_CN;
    private final Boolean Default;
    private final ModPartEnum MPE;

    ModKeyValue(Supplier<Item> itemSupplier,ModPartEnum MPE, String US_EN, String SIM_CN, String TRA_CN, Boolean isDefault) {
        this.supplier = itemSupplier;
        this.MPE = MPE;
        this.US_EN = US_EN;
        this.SIM_CN = SIM_CN;
        this.TRA_CN = TRA_CN;
        this.Default = isDefault;
    }
    public static String getEnglish(ModKeyValue key) {
        return key.US_EN;
    }
    public static String getSimpleChinese(ModKeyValue key) {
        return key.SIM_CN;
    }
    public static String getTraditionalChinese(ModKeyValue key) {
        return key.TRA_CN;
    }
    @NeedCompletedInFuture
    public String getKey() {
        if(key == null){
            if(MPE == ModPartEnum.ITEMS )key = ((Item)supplier.get()).getDescriptionId();
        //需要完善
        }
        return key;
    }

    public Item getItem() {
        return (Item)supplier.get();
    }
    public boolean isDefaultItem(){
        return MPE == ModPartEnum.ITEMS && Default;
    }
}
