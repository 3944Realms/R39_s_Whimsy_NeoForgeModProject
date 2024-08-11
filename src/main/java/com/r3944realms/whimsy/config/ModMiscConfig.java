package com.r3944realms.whimsy.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public class ModMiscConfig {
    public static ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
    public static final ModConfigSpec SPEC;
    public static ModConfigSpec.ConfigValue<String> testConfig;
    public static ModConfigSpec.BooleanValue enableAntyItemEnchant;

    static {
        BUILDER.push("R39's Whimsicality Mod Set");
        testConfig = BUILDER.comment("Test config here").define("testConfig", "test,debug,info,fatal,warn,error");
        enableAntyItemEnchant = BUILDER.comment("Enable Any item can be enchanted").define("enableAnyThingEnchant", true);
        SPEC = BUILDER.build();
    }
}
//