package com.r3944realms.whimsy.api.manager;

import com.r3944realms.whimsy.api.websocket.WebSocketClient;
import com.r3944realms.whimsy.api.websocket.WebSocketServer;
import com.r3944realms.whimsy.config.WebSocketServerConfig;
import com.r3944realms.whimsy.network.payload.ackpayload.SyncWebsocketRequestPayload;
import com.r3944realms.whimsy.utils.Enum.ManagerResultEnum;
import com.r3944realms.whimsy.utils.logger.logger;
import net.minecraft.client.Minecraft;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.concurrent.atomic.AtomicBoolean;

public enum WebsocketClientManager {
    INSTANCE;
    private final AtomicBoolean shouldStart = new AtomicBoolean(false),
                                waitingForSynchronization = new AtomicBoolean(false);

    public boolean getShouldStart() {
        return shouldStart.get();
    }
    public void setShouldStart(boolean shouldStart) {
        this.shouldStart.set(shouldStart);
    }
    public boolean getWaitingForSynchronization() {
        return waitingForSynchronization.get();
    }
    public void setWaitingForSynchronization(boolean waitingForSynchronization) {
        this.waitingForSynchronization.set(waitingForSynchronization);
    }

    public void StartClient() {
        ManagerResultEnum result = StartClient0();
        switch (result) {
            case SUCCESSFUL -> logger.info("Client successfully started");
            case WAITING_FOR_SYNCHRONIZATION -> {
                logger.info("Waiting for synchronization");
                waitingForSynchronization.set(true);
            }
            case MISMATCHED_ENVIRONMENT_FAILURE -> logger.info("Mismatched environment failure");
            case FAILURE_FROM_CATCH -> logger.info("Failure from catch");
        }
    }

    private ManagerResultEnum StartClient0() {
        if(!FMLEnvironment.dist.isClient()) { //非客户端退出
            logger.error("Distributor is not client");
            return ManagerResultEnum.MISMATCHED_ENVIRONMENT_FAILURE;
        }
        try {
            WebSocketServer.refresh();
            if(Minecraft.getInstance().isSingleplayer()) {
                WebSocketClient.syncServerData(WebSocketServerConfig.WebSocketServerAddress.get(), WebSocketServerConfig.WebSocketServerPort.get());
                WebSocketClient.Start();
                return ManagerResultEnum.SUCCESSFUL;
            }
            if (Minecraft.getInstance().player != null) {
                PacketDistributor.sendToServer(new SyncWebsocketRequestPayload(Minecraft.getInstance().player.getUUID()));
                shouldStart.set(true);
                return ManagerResultEnum.WAITING_FOR_SYNCHRONIZATION;
            }
            else throw new NullPointerException();
        } catch (NullPointerException e) {
            logger.error("Null pointer exception, player is null :{}", e);
            return ManagerResultEnum.FAILURE_FROM_CATCH;
        }
    }

    public void StopClient() {
        ManagerResultEnum result = StopClient0();
        switch (result) {
            case SUCCESSFUL -> logger.info("Client successfully started");
            case MISMATCHED_ENVIRONMENT_FAILURE -> logger.info("Mismatched environment failure");
        }
    }

    private ManagerResultEnum StopClient0() {
        if(!FMLEnvironment.dist.isClient()) { //非客户端退出
            logger.error("Distributor is not client");
            return ManagerResultEnum.MISMATCHED_ENVIRONMENT_FAILURE;
        }
        WebSocketServer.refresh();
        WebSocketClient.Stop();
        return ManagerResultEnum.SUCCESSFUL;
    }
}
