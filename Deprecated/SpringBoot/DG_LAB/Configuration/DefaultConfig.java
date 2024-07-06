package com.r3944realms.whimsy.api.SpringBoot.DG_LAB.Configuration;

import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Deprecated
//@Configuration
//@EnableWebSocket
public class DefaultConfig implements WebSocketConfigurer {
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(null);
    }
}
