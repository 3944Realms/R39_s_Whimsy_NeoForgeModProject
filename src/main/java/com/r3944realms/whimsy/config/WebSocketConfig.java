package com.r3944realms.whimsy.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public class WebSocketConfig {
    public static ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
    public static final ModConfigSpec SPEC;
    public static ModConfigSpec.ConfigValue<String> WebSocketConfig_String;
    public static ModConfigSpec.IntValue WebSocketServerPort;

    static {
        BUILDER.push("The Config about the WebSocket Server");
        WebSocketServerPort = BUILDER.comment("The port which Websocket Server starts on.(The port range is between 1 ~ 65535)[Default: 9000]")
                                        .defineInRange("websocket-port",9000,1,65535);
        SPEC = BUILDER.build();
    }
}
