package com.r3944realms.whimsy.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

import static com.r3944realms.whimsy.command.WhimsyCommand.WHIMSICALITY_COMMAND;

public class TestServerCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        LiteralArgumentBuilder<CommandSourceStack> literalArgumentBuilder = Commands.literal(WHIMSICALITY_COMMAND);
        Command<CommandSourceStack> createEntity = (context -> {
            return 0;
        });
        literalArgumentBuilder.then(Commands.literal("CreateEntity").executes(createEntity));
        dispatcher.register(literalArgumentBuilder);
    }
}
