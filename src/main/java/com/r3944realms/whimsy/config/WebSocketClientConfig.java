package com.r3944realms.whimsy.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public class WebSocketClientConfig {
    public static ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
    public static final ModConfigSpec SPEC;
    public static ModConfigSpec.BooleanValue WebSocketClientAutoManager;
    static {
        BUILDER.push("The Config about the WebSocket Client");
        WebSocketClientAutoManager = BUILDER.comment("When connecting to the server, it will automatically synchronize server information and start the Websocket client to connect to the server. \nSimilarly, it will automatically disconnect when the connection with the server is disconnected\n[Default:true]")
                .define("WebSocketClientAutoManager", true);
        SPEC = BUILDER.build();
    }
}
