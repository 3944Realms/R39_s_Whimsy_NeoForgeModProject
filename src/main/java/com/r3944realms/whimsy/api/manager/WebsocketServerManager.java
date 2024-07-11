package com.r3944realms.whimsy.api.manager;

import com.r3944realms.whimsy.api.websocket.WebSocketServer;
import com.r3944realms.whimsy.utils.Enum.ManagerResultEnum;

public enum WebsocketServerManager {
    INSTANCE;
    public ManagerResultEnum StartServer() {
        WebSocketServer.refresh();
        WebSocketServer.Start();
        return ManagerResultEnum.SUCCESSFUL;
    }
    public ManagerResultEnum StopServer() {
        WebSocketServer.refresh();
        WebSocketServer.Stop();
        return ManagerResultEnum.SUCCESSFUL;
    }
}
