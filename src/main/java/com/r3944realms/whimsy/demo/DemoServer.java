package com.r3944realms.whimsy.demo;


import com.r3944realms.dg_lab.future.manager.DGPBClientManager;
import com.r3944realms.dg_lab.future.manager.DGPBServerManager;
import com.r3944realms.dg_lab.future.websocket.PowerBoxWSClient;
import com.r3944realms.dg_lab.future.websocket.PowerBoxWSServer;
import com.r3944realms.dg_lab.future.websocket.sharedData.ClientPowerBoxSharedData;
import com.r3944realms.dg_lab.future.websocket.sharedData.ServerPowerBoxSharedData;
import com.r3944realms.dg_lab.websocket.WebSocketServer;
import com.r3944realms.dg_lab.websocket.message.role.WebSocketClientRole;
import com.r3944realms.dg_lab.websocket.message.role.WebSocketServerRole;

public class DemoServer {
    public static void main(String[] args) {
        ServerPowerBoxSharedData sharedData = new ServerPowerBoxSharedData();
        PowerBoxWSServer powerBoxWSServer = new PowerBoxWSServer(sharedData, new WebSocketServerRole("IWebsocketServer"));
        powerBoxWSServer.start();
    }
}
