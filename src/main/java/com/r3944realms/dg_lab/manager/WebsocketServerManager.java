package com.r3944realms.dg_lab.manager;

import com.r3944realms.dg_lab.websocket.WebSocketServer;
import com.r3944realms.dg_lab.websocket.utils.enums.ManagerResultEnum;

public enum WebsocketServerManager {
    INSTANCE;

    public static WebsocketServerManager getManager() {
        return INSTANCE;
    }
    public void StartServer() {
        ManagerResultEnum result = StartServer0();
    }
    private ManagerResultEnum StartServer0() {
        WebSocketServer.Start();
        return ManagerResultEnum.SUCCESSFUL;
    }
    public void StopServer() {
        ManagerResultEnum result = StopServer0();
    }
    private ManagerResultEnum StopServer0() {
        WebSocketServer.Stop();
        return ManagerResultEnum.SUCCESSFUL;
    }
}
