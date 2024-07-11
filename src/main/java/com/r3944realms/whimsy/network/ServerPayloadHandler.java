package com.r3944realms.whimsy.network;

import com.electronwill.nightconfig.core.conversion.InvalidValueException;
import com.r3944realms.whimsy.config.WebSocketServerConfig;
import com.r3944realms.whimsy.network.payload.TestModData;
import com.r3944realms.whimsy.network.payload.WebSocketServerAddressData;
import com.r3944realms.whimsy.network.payload.ackpayload.SyncWebsocketRequestPayload;
import com.r3944realms.whimsy.utils.NetworkUtils.AddressValidator;
import com.r3944realms.whimsy.utils.logger.logger;

import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.server.ServerLifecycleHooks;

public class ServerPayloadHandler {

    public static void handleData(final TestModData data, final IPayloadContext context) {
        context.enqueueWork(() -> {
            logger.info(data.message());
        })
        .exceptionally(e -> {
           context.disconnect(Component.translatable("whimsy.network.payload.ack.failed" + ": %s",e.getMessage()));
            return null;
        });
    }
    public static void handleSyncWebsocketAddressAck(final SyncWebsocketRequestPayload ackPayload,final IPayloadContext context) {
        context.enqueueWork(() -> {
            String address = WebSocketServerConfig.WebSocketServerAddress.get();
            MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
            try {
                ServerPlayer player = null;
                if (server != null) {
                    player = server.getPlayerList().getPlayer(ackPayload.player_uuid());
                }
                if (player == null) throw new NullPointerException();
                if(!AddressValidator.isValidAddress(address)) throw new InvalidValueException(address);
                PacketDistributor.sendToPlayer(player,new WebSocketServerAddressData(address, WebSocketServerConfig.WebSocketServerPort.get()));
            } catch (NullPointerException e) {
                logger.error("player(uuid:{}) is not existed in game.", ackPayload.player_uuid());
            } catch (InvalidValueException e) {
                logger.error("Server Address is invalid:{}",address);
            }
        }).exceptionally(throwable -> {
            context.disconnect(Component.translatable(throwable.getMessage()));
            return null;
        });
    }
}
