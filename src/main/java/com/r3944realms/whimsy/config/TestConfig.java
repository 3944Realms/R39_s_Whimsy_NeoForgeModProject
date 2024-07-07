package com.r3944realms.whimsy.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public class TestConfig {
    public static ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
    public static final ModConfigSpec SPEC;
    public static ModConfigSpec.ConfigValue<String> testConfig;

    static {
        BUILDER.push("R39's Whimsicality Mod Set");
        testConfig = BUILDER.comment("Test config here").define("testConfig", "test,debug,info,fatal,warn,error");
        SPEC = BUILDER.build();
    }
}
//