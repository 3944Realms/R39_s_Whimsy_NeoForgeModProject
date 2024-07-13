package com.r3944realms.whimsy.network;

import com.r3944realms.whimsy.api.manager.WebsocketClientManager;
import com.r3944realms.whimsy.api.websocket.WebSocketClient;
import com.r3944realms.whimsy.network.payload.TestModData;
import com.r3944realms.whimsy.network.payload.WebSocketServerAddressData;
import com.r3944realms.whimsy.utils.logger.logger;
import net.minecraft.network.chat.Component;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

import static com.r3944realms.whimsy.network.NetworkHandler.WS_CLIENT_SYNC_FAILED;

public class ClientPayloadHandler {

    public static void handleData(final TestModData data, final IPayloadContext context) {
        context.enqueueWork(() -> {
            logger.info(data.message());
            })
            .exceptionally(e-> {
                context.disconnect(Component.translatable(NetworkHandler.ACK_FAILED + ": %s", e.getMessage()));
                return null;
            });
    }
    public static void handleSyncWebsocketServerAddressData(final WebSocketServerAddressData data, final IPayloadContext context) {
        context.enqueueWork(() -> {
            WebSocketClient.syncServerData(data.address(), data.port());
            logger.info("sync WebsocketServer Address Data successful");
            if(WebsocketClientManager.INSTANCE.getShouldStart() && WebsocketClientManager.INSTANCE.getWaitingForSynchronization()) {
                WebSocketClient.Start();
                WebsocketClientManager.INSTANCE.setShouldStart(false);
                WebsocketClientManager.INSTANCE.setWaitingForSynchronization(false);
            }
        }).exceptionally(throwable -> {
            context.disconnect(Component.translatable(WS_CLIENT_SYNC_FAILED));
            return null;
        });
    }
    @SubscribeEvent
    public static void register(final RegisterPayloadHandlersEvent event) {
        // Sets the current network version
        final PayloadRegistrar registrar = event.registrar("1");
    }
}
