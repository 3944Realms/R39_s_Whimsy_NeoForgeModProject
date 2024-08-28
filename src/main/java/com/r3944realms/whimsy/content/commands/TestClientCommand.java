package com.r3944realms.whimsy.content.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.r3944realms.whimsy.init.FilePathHelper;
import com.r3944realms.whimsy.utils.logger.logger;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.HttpTexture;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

import java.io.File;
import java.nio.file.Files;

import static com.r3944realms.whimsy.content.commands.WhimsyCommand.WHIMSICALITY_COMMAND;

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
        Command<CommandSourceStack> modifyBob = context -> {
            CommandSourceStack source = context.getSource();
            try {
                Player player = (Player)source.getEntity();
                if (player != null) {
                    player.bob = context.getArgument("bob", Float.class);
                }
            } catch (NullPointerException e) {
                logger.error();
            }
            return 0;
        };
        Command<CommandSourceStack> DownloadFileInPathByResourcelocation = context -> {
            CommandSourceStack source = context.getSource();
            Minecraft minecraft = Minecraft.getInstance();
            String namespace = context.getArgument("namespace", String.class),
                    location = context.getArgument("location", String.class),
                    path = context.getArgument("path", String.class),
                    url = context.getArgument("url", String.class);
            try {
                ResourceLocation resourceLocation1 = ResourceLocation.fromNamespaceAndPath(namespace, location);
                minecraft.getTextureManager().register(resourceLocation1, new HttpTexture(
                        null,
                        url,
                        resourceLocation1,
                        false,
                        null
                ));
            } catch (Exception e) {
                logger.debug("Failure", e);
            }
            return 0;
        };

        LiteralArgumentBuilder<CommandSourceStack> $$ClientTest = Commands.literal("ClientTest");
        literalArgumentBuilder
                .then($$ClientTest.requires(commandSourceStack -> commandSourceStack.hasPermission(4))
                        .then(Commands.literal("CreateFileLink").executes(CreateFileLink))

                );
        literalArgumentBuilder
                .then($$ClientTest.then(Commands.literal("bob")
                            .then(Commands.argument("bob", FloatArgumentType.floatArg()).executes(modifyBob)
                )
            )
        );
        literalArgumentBuilder
                .then($$ClientTest.then(Commands.literal("resourceLocation")
                        .then(Commands.argument("namespace", StringArgumentType.string())
                                .then(Commands.argument("location", StringArgumentType.string())
                                        .then(Commands.literal("pathAndUrl")
                                                .then(Commands.argument("path", StringArgumentType.string())
                                                        .then(Commands.argument("url", StringArgumentType.string())
                                                                .executes(DownloadFileInPathByResourcelocation)
                                                        )
                                                )
                                        )
                                        .then(Commands.literal("file"))
                                )
                        )
                )
        );

        dispatcher.register(literalArgumentBuilder);
    }
}
