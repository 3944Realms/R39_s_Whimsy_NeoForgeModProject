package com.r3944realms.whimsy.demo;

import com.r3944realms.whimsy.api.websocket.WebSocketServer;

public class DemoServer {
    public static void main(String[] args) {
        WebSocketServer.enableDemo();
        WebSocketServer.Start();

    }
}
