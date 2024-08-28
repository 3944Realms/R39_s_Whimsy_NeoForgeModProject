package com.r3944realms.whimsy.content.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.r3944realms.whimsy.content.components.ModDataComponents;
import com.r3944realms.whimsy.content.items.custom.DynamicTextureItem;
import com.r3944realms.whimsy.content.items.custom.TestTextureItem;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.Objects;

import static com.r3944realms.whimsy.content.commands.WhimsyCommand.WHIMSICALITY_COMMAND;

public class TestServerCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        LiteralArgumentBuilder<CommandSourceStack> literalArgumentBuilder = Commands.literal(WHIMSICALITY_COMMAND);
        Command<CommandSourceStack> createEntity = (context -> {
            return 0;
        });
        Command<CommandSourceStack> setDynamic = context -> {
            CommandSourceStack source = context.getSource();
            try {
                ItemStack itemInHand = Objects.requireNonNull(source.getPlayer()).getItemInHand(InteractionHand.MAIN_HAND);
                Item item = itemInHand.getItem();
                if(item instanceof DynamicTextureItem || item instanceof TestTextureItem) {
                    String url = context.getArgument("url", String.class);
                    String set = itemInHand.set(ModDataComponents.DYNAMIC_TEXTURE_URL, url);
                    source.sendSuccess(() -> Component.literal("Success"), false);
                }
                else source.sendFailure(Component.literal("Failure"));
            } catch (Exception e) {
                source.sendFailure(Component.literal("Failure"));
                return -1;
            }
            return 0;
        };
        LiteralArgumentBuilder<CommandSourceStack> $$ServerTest = Commands.literal("ServerTest");
        literalArgumentBuilder.then($$ServerTest
                    .then(Commands.literal("CreateEntity")
                            .executes(createEntity)
                    )
        );
        literalArgumentBuilder.then($$ServerTest
                .then(Commands.literal("dynamic")
                        .then(Commands.argument("url", StringArgumentType.string())
                                .executes(setDynamic)
                        )
                )
        );

        dispatcher.register(literalArgumentBuilder);
    }
}
