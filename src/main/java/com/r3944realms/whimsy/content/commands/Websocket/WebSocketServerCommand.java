package com.r3944realms.whimsy.content.commands.Websocket;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;

import com.r3944realms.dg_lab.websocket.WebSocketServer;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

import static com.r3944realms.whimsy.content.commands.WhimsyCommand.WHIMSICALITY_COMMAND;

public class WebSocketServerCommand {
    private static final String WHIMSICALITY_WEBSOCKET_SERVER_MESSAGE_ = "whimsy.command.websocket.server.message.";
    public static final String SERVER_START_SUCCESSFUL = WHIMSICALITY_WEBSOCKET_SERVER_MESSAGE_ + "start.successful",
                                SERVER_START_FAILED_CLOSING = WHIMSICALITY_WEBSOCKET_SERVER_MESSAGE_ + "start.failed.closing",
                                SERVER_START_FAILED_REPEAT_START = WHIMSICALITY_WEBSOCKET_SERVER_MESSAGE_ + "start.failed.repeat_start",
                                SERVER_STOP_SUCCESSFUL = WHIMSICALITY_WEBSOCKET_SERVER_MESSAGE_ + "stop.successful",
                                SERVER_STOP_FAILED_HAD_CLOSED = WHIMSICALITY_WEBSOCKET_SERVER_MESSAGE_ + "stop.failed.had_closed";
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        LiteralArgumentBuilder<CommandSourceStack> literalArgumentBuilder = Commands.literal(WHIMSICALITY_COMMAND);

        Command<CommandSourceStack> WebsocketServerStart = (ctx) -> {
            CommandSourceStack source = ctx.getSource();
            WebSocketServer.Start();
            source.sendSuccess(() ->
                            (!WebSocketServer.isRunning() ?
                                    (!WebSocketServer.isStopping() ?
                                            Component.translatable(SERVER_START_SUCCESSFUL).withStyle(ChatFormatting.GREEN) :
                                            Component.translatable(SERVER_START_FAILED_CLOSING).withStyle(ChatFormatting.RED)) :
                                    Component.translatable(SERVER_START_FAILED_REPEAT_START).withStyle(ChatFormatting.RED)),
                    false);

            return 0;
        };

        Command<CommandSourceStack> WebsocketServerStop = (ctx) -> {
            CommandSourceStack source = ctx.getSource();
            WebSocketServer.Stop();
            source.sendSuccess(() -> WebSocketServer.isRunning() ?
                            Component.translatable(SERVER_STOP_SUCCESSFUL).withStyle(ChatFormatting.GREEN) :
                            Component.translatable(SERVER_STOP_FAILED_HAD_CLOSED).withStyle(ChatFormatting.RED),
                    false);

            return 0;
        };

        literalArgumentBuilder.then(Commands.literal("websocket").requires(player -> player.hasPermission(4))
                        .then(Commands.literal("Server")
                                .then(Commands.literal("start").executes(WebsocketServerStart))
                                .then(Commands.literal("stop").executes(WebsocketServerStop))
                        )
        );
        dispatcher.register(literalArgumentBuilder);
    }
}
