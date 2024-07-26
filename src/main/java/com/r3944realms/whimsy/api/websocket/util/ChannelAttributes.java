package com.r3944realms.whimsy.api.websocket.util;

import io.netty.util.AttributeKey;

public class ChannelAttributes {
    public static final AttributeKey<String> DYNAMIC_PATH = AttributeKey.newInstance("dynamicPath");
    private ChannelAttributes() {} //Private constructor to prevent instantiation
}
