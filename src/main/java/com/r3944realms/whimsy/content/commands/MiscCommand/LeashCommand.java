package com.r3944realms.whimsy.content.commands.MiscCommand;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.r3944realms.whimsy.content.commands.WhimsyCommand;
import com.r3944realms.whimsy.modInterface.entity.IEntityExtension;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

public class LeashCommand {
    private final static String WHIMSICALITY_LEASH_MESSAGE_ = "whimsy.command.leash.message.";
    public final static String LEASH_LENGTH_SHOW = WHIMSICALITY_LEASH_MESSAGE_ + "leash.length.show",
                            LEASH_LENGTH_FAIL = WHIMSICALITY_LEASH_MESSAGE_ + "leash.length.fail",
                            LEASH_LENGTH_SET = WHIMSICALITY_LEASH_MESSAGE_ + "leash.length.set";
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        LiteralArgumentBuilder<CommandSourceStack> literalArgumentBuilder = Commands.literal(WhimsyCommand.WHIMSICALITY_COMMAND);
        Command<CommandSourceStack> getSelfLeashLength = context -> {
            CommandSourceStack source = context.getSource();
            try {
                ServerPlayer player = source.getPlayerOrException();
                float leashLength = ((IEntityExtension)player).getLeashLength();
                source.sendSuccess(() -> Component.translatable(LEASH_LENGTH_SHOW, player.getName(), leashLength), true);
            } catch (Exception e) {
                source.sendFailure(Component.translatable(LEASH_LENGTH_FAIL));
                return -1;
            }
            return 0;
        };
        Command<CommandSourceStack> getRefPlayerLeashLength = context -> {
            CommandSourceStack source = context.getSource();
            try {
                Player player = context.getArgument("player", Player.class);
                float leashLength = ((IEntityExtension)player).getLeashLength();
                source.sendSuccess(() -> Component.translatable(LEASH_LENGTH_SHOW, player.getName(), leashLength), true);
            } catch (Exception e) {
                source.sendFailure(Component.translatable(LEASH_LENGTH_FAIL));
                return -1;
            }
            return 0;
        };
        Command<CommandSourceStack> setSelfLengthLeashLength = context -> {
            CommandSourceStack source = context.getSource();
            try {
                ServerPlayer player = source.getPlayerOrException();
                float leashLength = context.getArgument("leashLength", Float.class);
                ((IEntityExtension)player).setLeashLength(leashLength);
                source.sendSuccess(() -> Component.translatable(LEASH_LENGTH_SET, player.getName(), leashLength), true);
            } catch (Exception e) {
                source.sendFailure(Component.translatable(LEASH_LENGTH_FAIL));
                return -1;
            }
            return 0;
        };
        Command<CommandSourceStack> setLengthLeashLength = context -> {
            CommandSourceStack source = context.getSource();
            try {
                Player player = context.getArgument("player", Player.class);
                float leashLength = context.getArgument("leashLength", Float.class);
                ((IEntityExtension)player).setLeashLength(leashLength);
                source.sendSuccess(() -> Component.translatable(LEASH_LENGTH_SET, player.getName(), leashLength), true);
            } catch (Exception e) {
                source.sendFailure(Component.translatable(LEASH_LENGTH_FAIL));
                return -1;
            }
            return 0;
        };
        LiteralArgumentBuilder<CommandSourceStack> $$leashRoot = Commands.literal("leash").requires(cs -> cs.hasPermission(2));
        literalArgumentBuilder.then(
                $$leashRoot.then(Commands.literal("length").executes(getSelfLeashLength)
                        .then(Commands.literal("getLength").executes(getSelfLeashLength))
                        .then(Commands.literal("setLength")
                                .then(Commands.argument("leashLength", FloatArgumentType.floatArg(5, 1024)).executes(setSelfLengthLeashLength)
                                )
                        )
                )
        );
        literalArgumentBuilder.then(
                $$leashRoot.then(
                            Commands.literal("length")
                                    .then(Commands.argument("player", EntityArgument.player()).executes(getRefPlayerLeashLength)
                                            .then(Commands.literal("getLength").executes(getRefPlayerLeashLength))
                                            .then(Commands.literal("setLength")
                                                    .then(
                                                            Commands.argument("leashLength", FloatArgumentType.floatArg(5, 1024)).executes(setLengthLeashLength)
                                                    )
                                            )
                                    )
                )


        );
        dispatcher.register(literalArgumentBuilder);
    }
}
