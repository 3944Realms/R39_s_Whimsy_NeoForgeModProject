package com.r3944realms.whimsy.api.SpringBoot.DG_LAB.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * 本机的websocket的配置
 * @link <a href="https://github.com/Vpn33/DG-LAB-OPENSOURCE/blob/main/socket/BackEnd(java%2BSpringboot)/src/main/java/com/vpn33/config/WebSocketConfig.java">原链接</a>
 */
@Configuration
public class WebsocketConfig {
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
}
