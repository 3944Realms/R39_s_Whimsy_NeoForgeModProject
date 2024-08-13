package com.r3944realms.whimsy.content.commands.PlayerProperty;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.r3944realms.whimsy.content.commands.WhimsyCommand;
import com.r3944realms.whimsy.modInterface.player.ServerPlayerCapacity;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;


public class ChatCommand {
    private static final String WHIMSICALITY_CHAT_MESSAGE_ = "whimsy.command.chat.message.";
    public static final String TALK_AREA_SET = WHIMSICALITY_CHAT_MESSAGE_ + "talkarea.set",
                            TALK_AREA_PREFERENCE_SET = WHIMSICALITY_CHAT_MESSAGE_ + "talkarea.preference.set",
                            TALK_AREA_PREFERENCE_NOT_SET = WHIMSICALITY_CHAT_MESSAGE_ + "talkarea.preference_not_set",
                            TALK_AREA_UNLIMITED = WHIMSICALITY_CHAT_MESSAGE_ + "talkarea.unlimited",
                            TALK_AREA_CURRENT_CONFIG = WHIMSICALITY_CHAT_MESSAGE_ + "talkarea.current_config";
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        LiteralArgumentBuilder<CommandSourceStack> literalArgumentBuilder = Commands.literal(WhimsyCommand.WHIMSICALITY_COMMAND);
        Command<CommandSourceStack> chat$talkArea_set = (context -> {
            CommandSourceStack source = context.getSource();
            ServerPlayerCapacity serverPlayer = (ServerPlayerCapacity) source.getPlayerOrException();
            int integer = IntegerArgumentType.getInteger(context, "talkAreaRadius");
            serverPlayer.Whimsy$SetTalkArea(integer);
            source.sendSuccess(() -> Component.translatable(TALK_AREA_SET, integer),true);
            return 0;
        });
        Command<CommandSourceStack> chat$talkArea_preference_set = (context -> {
            CommandSourceStack source = context.getSource();
            ServerPlayerCapacity serverPlayer = (ServerPlayerCapacity) source.getPlayerOrException();
            int integer = IntegerArgumentType.getInteger(context, "talkAreaPreferenceRadius");
            serverPlayer.Whimsy$SetTalkAreaPreference(integer);
            source.sendSuccess(() -> Component.translatable(TALK_AREA_PREFERENCE_SET, integer),true);
            return 0;
        });
        Command<CommandSourceStack> chat$talkArea_usePreference = (context -> {
            CommandSourceStack source = context.getSource();
            ServerPlayerCapacity serverPlayer = (ServerPlayerCapacity) source.getPlayerOrException();
            if(serverPlayer.Whimsy$GetTalkAreaPreference() == -1) {
                source.sendFailure(Component.translatable(TALK_AREA_PREFERENCE_NOT_SET));
                return 1;
            }
            int currentPreference = serverPlayer.Whimsy$GetTalkAreaPreference();
            serverPlayer.Whimsy$SetTalkArea(currentPreference);
            source.sendSuccess(() -> Component.translatable(TALK_AREA_PREFERENCE_SET, currentPreference),true);
            return 0;
        });
        Command<CommandSourceStack> chat$talkArea_unlimited = (context -> {
            CommandSourceStack source = context.getSource();
            ServerPlayerCapacity serverPlayer = (ServerPlayerCapacity) source.getPlayerOrException();
            serverPlayer.Whimsy$SetTalkArea(-1);
            source.sendSuccess(() -> Component.translatable(TALK_AREA_UNLIMITED),true);
            return 0;
        });
        Command<CommandSourceStack> chat$talkArea_getCurrentConfig = (context -> {
            CommandSourceStack source = context.getSource();
            ServerPlayerCapacity serverPlayer = (ServerPlayerCapacity) source.getPlayerOrException();
            source.sendSuccess(() -> Component.translatable(TALK_AREA_CURRENT_CONFIG,serverPlayer.Whimsy$GetTalkArea(),serverPlayer.Whimsy$GetTalkAreaPreference()),true);
            return 0;
        });
        literalArgumentBuilder.then(Commands.literal("chat")
                .then(Commands.literal("talkArea")
                        .then(Commands.literal("set")
                                .then(Commands.argument("talkAreaRadius", IntegerArgumentType.integer(0))
                                        .executes(chat$talkArea_set)))
                        .then(Commands.literal("setPreference")
                                .then(Commands.argument("talkAreaPreferenceRadius", IntegerArgumentType.integer(0))
                                        .executes(chat$talkArea_preference_set)))
                        .then(Commands.literal("usePreference")
                                .executes(chat$talkArea_usePreference))
                        .then(Commands.literal("unlimited")
                                .executes(chat$talkArea_unlimited))
                        .then(Commands.literal("currentConfig")
                                .executes(chat$talkArea_getCurrentConfig))
                )
        );
        dispatcher.register(literalArgumentBuilder);
    }
}
