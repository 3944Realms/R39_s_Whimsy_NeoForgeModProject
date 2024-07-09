package com.r3944realms.whimsy.network;

import com.r3944realms.whimsy.WhimsyMod;
import com.r3944realms.whimsy.network.payload.TestModData;
import com.r3944realms.whimsy.network.payload.WebSocketServerAddressData;
import com.r3944realms.whimsy.network.payload.ackpayload.SyncWebsocketRequestPayload;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.DirectionalPayloadHandler;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber(modid = WhimsyMod.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class NetworkHandler {
    protected static final String WHIMSICALITY_PAYLOAD_MESSAGE_ = "whimsy.network.payload.";
    protected static final String WHIMSICALITY_PA_ME_WEBSOCKET_ = WHIMSICALITY_PAYLOAD_MESSAGE_ + "websocket.";


    public static final String ACK_FAILED = NetworkHandler.WHIMSICALITY_PAYLOAD_MESSAGE_ + "ack.failed";


    public static final String WS_CLIENT_SYNC_FAILED = NetworkHandler.WHIMSICALITY_PA_ME_WEBSOCKET_ + "client.sync.failed";

    @SubscribeEvent
    public static void register(final RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar(WhimsyMod.MOD_ID);
        registrar.playBidirectional(
                TestModData.TYPE,
                TestModData.STREAM_CODEC,
                new DirectionalPayloadHandler<>(
                    ClientPayloadHandler::handleData,
                    ServerPayloadHandler::handleData
                )
        );
        registrar.playToServer(
                SyncWebsocketRequestPayload.TYPE,
                SyncWebsocketRequestPayload.STREAM_CODEC,
                ServerPayloadHandler::handleSyncWebsocketAddressAck
        );
        registrar.playToClient(
                WebSocketServerAddressData.TYPE,
                WebSocketServerAddressData.STREAM_CODEC,
                ClientPayloadHandler::handleSyncWebsocketServerAddressData
        );
    }
}
