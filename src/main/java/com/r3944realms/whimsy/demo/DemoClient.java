package com.r3944realms.whimsy.demo;

import com.r3944realms.whimsy.api.websocket.WebSocketClient;

public class DemoClient {
    public static void main(String[] args) {
        try {
            Class.forName("io.netty.handler.codec.http.HttpHeaders");
            System.out.println("HttpHeaders class is available");
        } catch (ClassNotFoundException e) {
            System.out.println("HttpHeaders class is not available");
            e.printStackTrace();
        }

        WebSocketClient.enableDemo();
        WebSocketClient.Start();
    }

}
