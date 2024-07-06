package com.r3944realms.whimsy.api.SpringBoot.DG_LAB;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class DG_LAB_WebSocket_SpringBootApplication {
    private static ConfigurableApplicationContext context;

    public static void start() {
        if (context == null) {
            try {
                context = SpringApplication.run(DG_LAB_WebSocket_SpringBootApplication.class);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

        }
    }

    public static void stop() {
        if (context != null) {
            SpringApplication.exit(context, () -> 0);
            context = null;
        }
    }

    /**
    * 测试
    */
    public static void main(String[] args) {
        ConfigurableApplicationContext contextMain = SpringApplication.run(DG_LAB_WebSocket_SpringBootApplication.class, args);
    }
}