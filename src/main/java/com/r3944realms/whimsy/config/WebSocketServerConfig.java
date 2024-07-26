package com.r3944realms.whimsy.config;


import net.neoforged.neoforge.common.ModConfigSpec;

public class WebSocketServerConfig {
    public static ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
    public static final ModConfigSpec SPEC;
    public static ModConfigSpec.ConfigValue<String> WebSocketServerAddress;
    public static ModConfigSpec.IntValue WebSocketServerPort;
    public static ModConfigSpec.BooleanValue WebSocketServerAutoManager,
    isEnableWebSocketTextMessageMode;

    static {
        BUILDER.push("The Basic Config about the WebSocket Server");
        WebSocketServerAutoManager = BUILDER.comment("When the server is turned on, the Websocket server is automatically turned on.\nSimilarly, when the server is turned off, the Websocket server is automatically turned off.\n[Default:true]")
                .define("WebSocketServerAutoManager", true);
        WebSocketServerAddress = BUILDER.comment("The address of Websocket server [PS.Currently, it must be the address of Game Server] \n(In the future, it will support Run on independent Websocket Server)\n[Default:127.0.0.1]")
                                        .define("websocket-server-address(gameServerAddress)","127.0.0.1");
        WebSocketServerPort = BUILDER.comment("The port which Websocket Server starts on.(The port range is between 1 ~ 65535)\n[Default: 9000]")
                                        .defineInRange("websocket-port",9000,1,65535);
        BUILDER.push("The Advance Config about the Websocket Server");
        isEnableWebSocketTextMessageMode = BUILDER.comment("When enable this Mode ,Server will send the MessageJson instead of DataJson to Client side to provide more information for client.\n[Default:false]")
                .define("isEnableWebSocketTextMessageMode", false);
        SPEC = BUILDER.build();
    }
}
