package com.r3944realms.whimsy.demo;

import com.r3944realms.whimsy.api.websocket.WebSocketClient;

public class DemoClient {
    public static void main(String[] args) {
        WebSocketClient.enableDemo();
        WebSocketClient.Start();
    }

}
