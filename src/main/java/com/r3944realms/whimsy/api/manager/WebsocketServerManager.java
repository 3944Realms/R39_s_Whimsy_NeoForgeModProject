package com.r3944realms.whimsy.api.manager;

import com.r3944realms.whimsy.api.websocket.WebSocketServer;
import com.r3944realms.whimsy.utils.Enum.ManagerResultEnum;

public enum WebsocketServerManager {
    INSTANCE;
    public void StartServer() {
        ManagerResultEnum result = StartServer0();
    }
    public ManagerResultEnum StartServer0() {
        WebSocketServer.refresh();
        WebSocketServer.Start();
        return ManagerResultEnum.SUCCESSFUL;
    }
    public void StopServer() {
        ManagerResultEnum result = StopServer0();
    }
    public ManagerResultEnum StopServer0() {
        WebSocketServer.refresh();
        WebSocketServer.Stop();
        return ManagerResultEnum.SUCCESSFUL;
    }
}
