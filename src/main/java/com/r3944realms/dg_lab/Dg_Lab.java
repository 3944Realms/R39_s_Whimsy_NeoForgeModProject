package com.r3944realms.dg_lab;

import com.r3944realms.dg_lab.manager.WebsocketClientManager;
import com.r3944realms.dg_lab.manager.WebsocketServerManager;
import com.r3944realms.dg_lab.websocket.WebSocketClient;
import com.r3944realms.dg_lab.websocket.WebSocketServer;
import com.r3944realms.dg_lab.websocket.utils.enums.ManagerResultEnum;
import com.r3944realms.dg_lab.websocket.utils.enums.SendMode;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class Dg_Lab {
    public static LoggingHandler LOGGING_HANDLER = new LoggingHandler(LogLevel.DEBUG);
    public Dg_Lab(LogLevel level) {
        LOGGING_HANDLER = new LoggingHandler(level);
    }

    public void initClientManager (
                            @NotNull Supplier<ManagerResultEnum> clientStartSupplier,
                            @NotNull Supplier<ManagerResultEnum> clientStopSupplier,
                            @NotNull Supplier<Void> informSupplier,
                            @NotNull Supplier<Void> noticeSupplier,
                            @NotNull Consumer<String> qrCodeProducer
    ) {
        WebsocketClientManager.init(clientStartSupplier, clientStopSupplier, informSupplier, noticeSupplier, qrCodeProducer);
    }

    public void initClientWS(
            @NotNull Supplier<Void> informSupplier,
            @NotNull Supplier<Void> noticeSupplier,
            @NotNull Consumer<String> qrCodeProducer
    ) {
        WebSocketClient.init(informSupplier, noticeSupplier, qrCodeProducer);
    }

    public WebsocketServerManager getWebsocketServerManager() {
        return WebsocketServerManager.getManager();
    }
    public WebsocketClientManager getWebSocketClientManager() {
        return WebsocketClientManager.getManager();
    }

    public void initClientConfig(String address, int port) {
        setConnectTarget(address, port);
    }

    public void setConnectTarget(String address, int port) {
        WebSocketClient.syncServerData(address, port);
    }

    public void initServerConfig(int port, SendMode sendMode) {
        BindServerPort(port);
        SetServerSendMode(sendMode);
    }

    public void BindServerPort(int port) {
        WebSocketServer.BindingPort(port);
    }
    public void SetServerSendMode(SendMode sendMode) {
        WebSocketServer.setMode(sendMode);
    }

}
