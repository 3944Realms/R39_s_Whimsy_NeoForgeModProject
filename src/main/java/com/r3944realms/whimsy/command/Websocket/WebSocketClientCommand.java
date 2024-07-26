package com.r3944realms.whimsy.command.Websocket;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.r3944realms.whimsy.api.websocket.WebSocketClient;
import com.r3944realms.whimsy.config.WebSocketServerConfig;
import com.r3944realms.whimsy.network.payload.ackpayload.SyncWebsocketRequestPayload;
import com.r3944realms.whimsy.utils.logger.logger;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.network.PacketDistributor;

import static com.r3944realms.whimsy.command.WhimsyCommand.WHIMSICALITY_COMMAND;

public class WebSocketClientCommand {
    public static final String WHIMSICALITY_WEBSOCKET_CLIENT_MESSAGE_= "whimsy.websocket.client.message.",
            NOT_CLIENT = WHIMSICALITY_WEBSOCKET_CLIENT_MESSAGE_ + "not_client",
            CLIENT_SYNC_ACK_SEND = WHIMSICALITY_WEBSOCKET_CLIENT_MESSAGE_ + "client_sync_send",
            CLIENT_START_SUCCESSFUL = WHIMSICALITY_WEBSOCKET_CLIENT_MESSAGE_ + "start.successful",
            CLIENT_START_FAILED_CLOSING =WHIMSICALITY_WEBSOCKET_CLIENT_MESSAGE_ + "start.failed.closing",
            CLIENT_START_FAILED_REPEAT_START = WHIMSICALITY_WEBSOCKET_CLIENT_MESSAGE_ + "start.failed.repeat_start",
            CLIENT_START_FAILED_NOT_SYNC = WHIMSICALITY_WEBSOCKET_CLIENT_MESSAGE_ + "start.failed.not_sync",
            CLIENT_STOP_SUCCESSFUL = WHIMSICALITY_WEBSOCKET_CLIENT_MESSAGE_ + "stop.successful",
            CLIENT_STOP_FAILED_HAD_CLOSED = WHIMSICALITY_WEBSOCKET_CLIENT_MESSAGE_ + "stop.failed.had_closed";
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        LiteralArgumentBuilder<CommandSourceStack> literalArgumentBuilder = Commands.literal(WHIMSICALITY_COMMAND);
        Command<CommandSourceStack> WebsocketClientSync = context -> {
            CommandSourceStack source = context.getSource();
            if(!FMLEnvironment.dist.isClient()) { //非客户端退出
                source.sendFailure(Component.translatable(NOT_CLIENT));
                return 1;
            }
            try {
                if(Minecraft.getInstance().isSingleplayer()) {
                    WebSocketClient.syncServerData(WebSocketServerConfig.WebSocketServerAddress.get(), WebSocketServerConfig.WebSocketServerPort.get());
                    source.sendSuccess(() -> Component.translatable(CLIENT_SYNC_ACK_SEND).withStyle(ChatFormatting.GREEN),true);
                    return 0;
                }
                if (Minecraft.getInstance().player != null) {
                    PacketDistributor.sendToServer(new SyncWebsocketRequestPayload(Minecraft.getInstance().player.getUUID()));
                    source.sendSuccess(() -> Component.translatable(CLIENT_SYNC_ACK_SEND).withStyle(ChatFormatting.GREEN),true);
                }
                else throw new NullPointerException();
            } catch (NullPointerException e) {
                logger.error("Null pointer exception, player is null :{}", e);
            }
            return 0;
        };
        Command<CommandSourceStack> WebsocketClientStart = context -> {
            CommandSourceStack source = context.getSource();
            if(!FMLEnvironment.dist.isClient()) { //非客户端退出
                source.sendFailure(Component.translatable(NOT_CLIENT));
                return 1;
            }
            if (!WebSocketClient.hasSync()) { //非同步退出
                source.sendFailure(Component.translatable(CLIENT_START_FAILED_NOT_SYNC));
                return -1;
            }
            WebSocketClient.refresh();
            source.sendSuccess(() ->
                            (!WebSocketClient.isRunning() ?
                                    (!WebSocketClient.isStopping() ?
                                            Component.translatable(CLIENT_START_SUCCESSFUL).withStyle(ChatFormatting.GREEN) :
                                            Component.translatable(CLIENT_START_FAILED_CLOSING).withStyle(ChatFormatting.RED)) :
                                    Component.translatable(CLIENT_START_FAILED_REPEAT_START).withStyle(ChatFormatting.RED)),
                    true);
            if (!WebSocketClient.isStopping()) WebSocketClient.Start();
            return 0;
        };
        Command<CommandSourceStack> WebsocketClientStop = context -> {
            CommandSourceStack source = context.getSource();
            if(!FMLEnvironment.dist.isClient()) { //非客户端退出
                source.sendFailure(Component.translatable(NOT_CLIENT));
                return 1;
            }
            source.sendSuccess(() -> WebSocketClient.isRunning() ?
                            Component.translatable(CLIENT_STOP_SUCCESSFUL).withStyle(ChatFormatting.GREEN) :
                            Component.translatable(CLIENT_STOP_FAILED_HAD_CLOSED).withStyle(ChatFormatting.RED),
                    true);
            WebSocketClient.Stop();
            return 0;
        };
        literalArgumentBuilder.then(Commands.literal("websocket")
                .then(Commands.literal("Client")
                        .then(Commands.literal("sync").executes(WebsocketClientSync))
                        .then(Commands.literal("start").executes(WebsocketClientStart))
                        .then(Commands.literal("stop").executes(WebsocketClientStop))
                )
        );
        dispatcher.register(literalArgumentBuilder);
    }

}

