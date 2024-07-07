package com.r3944realms.whimsy.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.r3944realms.whimsy.WhimsyMod;
import com.r3944realms.whimsy.api.websocket.WebSocketServer;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

public class WebSocketServerCommand {
    public static final String WHIMSICALITY_COMMAND = WhimsyMod.MOD_ID;
    private static final String WHIMSICALITY_WEBSOCKET_SERVER_MESSAGE_ = "whimsy.websocket.server.message.";
    public static final String  START_SUCCESSFUL = WHIMSICALITY_WEBSOCKET_SERVER_MESSAGE_ + "start.successful",
                                START_FAILED_CLOSING = WHIMSICALITY_WEBSOCKET_SERVER_MESSAGE_ + "start.failed.closing",
                                START_FAILED_REPEAT_START = WHIMSICALITY_WEBSOCKET_SERVER_MESSAGE_ + "start.failed.repeat_start",
                                STOP_SUCCESSFUL = WHIMSICALITY_WEBSOCKET_SERVER_MESSAGE_ + ".stop.successful",
                                STOP_FAILED_HAD_CLOSE = WHIMSICALITY_WEBSOCKET_SERVER_MESSAGE_ + "stop.failed.had_close"
                                ;

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        LiteralArgumentBuilder<CommandSourceStack> literalArgumentBuilder = Commands.literal(WHIMSICALITY_COMMAND)
                .then(Commands.literal("websocket").requires(player -> player.hasPermission(4))
                        .then(Commands.literal("Server")
                                .then(Commands.literal("start").executes(context -> {
                                    CommandSourceStack source = context.getSource();
                                    source.sendSuccess(() ->
                                           ( !WebSocketServer.isRunning() ?
                                                   (!WebSocketServer.isStopping() ?
                                                           Component.translatable(START_SUCCESSFUL).withStyle(ChatFormatting.GREEN) :
                                                           Component.translatable(START_FAILED_CLOSING).withStyle(ChatFormatting.RED)) :
                                                   Component.translatable(START_FAILED_REPEAT_START).withStyle(ChatFormatting.RED)),
                                            false);
                                    if(!WebSocketServer.isStopping()) WebSocketServer.Start();
                                    return 0;
                                }))
                                .then(Commands.literal("stop").executes(context -> {
                                    CommandSourceStack source = context.getSource();
                                    source.sendSuccess(() -> WebSocketServer.isRunning() ?
                                            Component.translatable(STOP_SUCCESSFUL).withStyle(ChatFormatting.GREEN) :
                                            Component.translatable(STOP_FAILED_HAD_CLOSE).withStyle(ChatFormatting.RED),
                                            false);
                                    WebSocketServer.Stop();
                                    return 0;
                                }))));
        dispatcher.register(literalArgumentBuilder);
    }
}
