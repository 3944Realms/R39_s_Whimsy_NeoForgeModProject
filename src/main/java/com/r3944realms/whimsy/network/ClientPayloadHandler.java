package com.r3944realms.whimsy.network;


import com.r3944realms.dg_lab.manager.WebsocketClientManager;
import com.r3944realms.dg_lab.websocket.WebSocketClient;
import com.r3944realms.whimsy.WhimsyMod;
import com.r3944realms.whimsy.content.gamerules.GameruleRegistry;
import com.r3944realms.whimsy.content.gamerules.Gamerules;
import com.r3944realms.whimsy.network.payload.*;
import com.r3944realms.whimsy.utils.logger.logger;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.GameRules;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import static com.r3944realms.whimsy.network.NetworkHandler.WS_CLIENT_SYNC_FAILED;

public class ClientPayloadHandler {

    public static void handleData(final TestModData data, final IPayloadContext context) {
        context.enqueueWork(() -> logger.info(data.message()))
            .exceptionally(e-> {
                context.disconnect(Component.translatable(NetworkHandler.ACK_FAILED + ": %s", e.getMessage()));
                return null;
            });
    }
    public static void handleSyncWebsocketServerAddressData(final WebSocketServerAddressData data, final IPayloadContext context) {
        context.enqueueWork(() -> {
            WhimsyMod.DG_LAB.initClientConfig(data.address(), data.port());
            logger.info("sync WebsocketServer Address Data successful");
            WhimsyMod.DG_LAB.getWebSocketClientManager().SyncedAndStart();
        }).exceptionally(throwable -> {
            context.disconnect(Component.translatable(WS_CLIENT_SYNC_FAILED));
            return null;
        });
    }
    @SuppressWarnings("unchecked")
    public static void handleSyncBooleanGameRuleData(final BooleanGameRuleValueChangeData data, final IPayloadContext context) {
        context.enqueueWork(() -> {
            ClientLevel level = Minecraft.getInstance().level;
            assert level != null;
            GameRules gameRules = level.getGameRules();
            gameRules.getRule(
                    (GameRules.Key<GameRules.BooleanValue>)
                            GameruleRegistry.gamerules.get(data.GameRuleName())
            ).set(data.value(), null);
        }).exceptionally(throwable -> {
            context.disconnect(Component.translatable(WS_CLIENT_SYNC_FAILED));
            return null;
        });
    }
    @SuppressWarnings("unchecked")
    public static void handleSyncIntegerGameRuleData(final IntegerGameRuleValueChangeData data, final IPayloadContext context) {
        context.enqueueWork(() -> {
            ClientLevel level = Minecraft.getInstance().level;
            assert level != null;
            GameRules gameRules = level.getGameRules();
            gameRules.getRule(
                    (GameRules.Key<GameRules.IntegerValue>)
                            GameruleRegistry.gamerules.get(data.GameRuleName())
            ).set(data.value(), null);
        }).exceptionally(throwable -> {
            context.disconnect(Component.translatable(WS_CLIENT_SYNC_FAILED));
            return null;
        });
    }
    @SuppressWarnings("unchecked")
    public static void handleSyncFloatGameRuleData(final FloatGameRuleValueChangeData data, final IPayloadContext context) {
        context.enqueueWork(() -> {
            ClientLevel level = Minecraft.getInstance().level;
            assert level != null;
            GameRules gameRules = level.getGameRules();
            gameRules.getRule(
                    (GameRules.Key<Gamerules.FloatValue>)
                            GameruleRegistry.gamerules.get(data.GameRuleName())
            ).set(data.value(), null);
        }).exceptionally(throwable -> {
            context.disconnect(Component.translatable(WS_CLIENT_SYNC_FAILED));
            return null;
        });
    }



}
