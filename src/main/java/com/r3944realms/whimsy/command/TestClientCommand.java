package com.r3944realms.whimsy.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.r3944realms.whimsy.init.FilePathHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

import java.io.File;
import java.nio.file.Files;

import static com.r3944realms.whimsy.command.WhimsyCommand.WHIMSICALITY_COMMAND;

public class TestClientCommand {
    public static String TEST_KEY_OPEN_FILE_LINK = "whimsy.command.test.open_file.link";
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        LiteralArgumentBuilder<CommandSourceStack> literalArgumentBuilder = Commands.literal(WHIMSICALITY_COMMAND);
        Command<CommandSourceStack> CreateFileLink = (context -> {
            MutableComponent fileComponent;
            if(Files.exists(FilePathHelper.get_HCJ_HTML_Path())) {
                File file = FilePathHelper.get_HCJ_HTML_Path().toFile().getAbsoluteFile();
                fileComponent = Component.literal(file.getName()).withStyle(ChatFormatting.UNDERLINE);
                fileComponent.withStyle((style) -> style.withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_FILE, file.getAbsolutePath())));
                context.getSource().sendSuccess(()-> Component.translatable(TEST_KEY_OPEN_FILE_LINK,fileComponent), true);
            } else context.getSource().sendFailure(Component.literal("Not Found Html file"));
            return 0;
        });

        literalArgumentBuilder
                .then(Commands.literal("test").requires(commandSourceStack -> commandSourceStack.hasPermission(4))
                        .then(Commands.literal("CreateFileLink").executes(CreateFileLink))

                );

        dispatcher.register(literalArgumentBuilder);
    }
}
