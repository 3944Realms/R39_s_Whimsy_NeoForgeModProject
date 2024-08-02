package com.r3944realms.whimsy.datagen.LanguageData;

import com.r3944realms.whimsy.WhimsyMod;

import javax.annotation.Nullable;

public enum ModAdvancementKey {
    RWN_WELCOME("root", null)
    ;
    private final String Name;
    @Nullable
    private final ModAdvancementKey Parent;
    ModAdvancementKey(String name, @Nullable ModAdvancementKey parent) {
        this.Name = name;
        this.Parent = parent;
    }

    public @Nullable ModAdvancementKey getParent() {
        return Parent;
    }
    public String getNameKey() {
        return "advancement." + WhimsyMod.MOD_ID + "." + Name;
    }

    public String getDescKey() {
        return this.getNameKey() + ".desc";
    }
    public String getNameWithNameSpace() {
        return WhimsyMod.MOD_ID + ":" + this.Name;
    }
}
