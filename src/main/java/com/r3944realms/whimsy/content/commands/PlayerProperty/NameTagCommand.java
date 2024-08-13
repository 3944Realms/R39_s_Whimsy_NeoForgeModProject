package com.r3944realms.whimsy.content.commands.PlayerProperty;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.r3944realms.whimsy.content.gamerules.ClientRender.MustOthersHiddenNameTag;
import com.r3944realms.whimsy.content.gamerules.GameruleRegistry;
import com.r3944realms.whimsy.modInterface.player.PlayerCapacity;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;

import static com.r3944realms.whimsy.content.commands.WhimsyCommand.WHIMSICALITY_COMMAND;

public class NameTagCommand {
    private static final String WHIMSICALITY_NAME_TAG_ = "whimsy.command.name_tag.";
    public static final String SELF_NAME_TAG_VISIBILITY = WHIMSICALITY_NAME_TAG_ + "status.self_name_tag.visibility",
                                OTHERS_NAME_TAG_HIDDEN = WHIMSICALITY_NAME_TAG_ + "status.others_name_tag.visibility",
                                FORBIDDEN_MODIFIED_GAMERULE_LOCK = WHIMSICALITY_NAME_TAG_ + "failed.forbidden_by_gamerule";

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        Command<CommandSourceStack> nameTag$selfNameTagVisibility = (context -> {
            CommandSourceStack source = context.getSource();
            boolean visibility = BoolArgumentType.getBool(context, "Visibility");
            EntityArgument.getPlayers(context, "targets").forEach(i -> {
                ((PlayerCapacity) i).Whimsy$SetSelfNameTagVisible(visibility);
                source.sendSuccess(() -> Component.translatable(SELF_NAME_TAG_VISIBILITY, i.getName(), visibility), false);
            });

            return 0;
        });
        Command<CommandSourceStack> nameTag$OtherPlayerTagHidden = (context -> {
            CommandSourceStack source = context.getSource();
            if(GameruleRegistry.getGameruleBoolValue(source.getLevel(), MustOthersHiddenNameTag.ID)) {
                source.sendFailure(Component.translatable(FORBIDDEN_MODIFIED_GAMERULE_LOCK));
                return -1;
            }
            boolean hidden = BoolArgumentType.getBool(context, "Hidden");
            EntityArgument.getPlayers(context, "targets").forEach(i -> {
                ((PlayerCapacity) i).Whimsy$SetOtherPlayerNameTagHidden(hidden);
                source.sendSuccess(() -> Component.translatable(OTHERS_NAME_TAG_HIDDEN, i.getName(), hidden), false);
            });
            return 0;
        });

        LiteralArgumentBuilder<CommandSourceStack> literalArgumentBuilder = Commands.literal(WHIMSICALITY_COMMAND);
        literalArgumentBuilder.then(Commands.literal("nameTag").requires(cs -> cs.hasPermission(2))
                .then(Commands.argument("targets", EntityArgument.players())
                    .then(Commands.literal("selfNameTagVisibility")
                        .then(Commands.argument("Visibility", BoolArgumentType.bool())
                                .executes(nameTag$selfNameTagVisibility)
                            )
                        )
                    .then(Commands.literal("OtherPlayerHidden")
                        .then(Commands.argument("Hidden", BoolArgumentType.bool())
                                .executes(nameTag$OtherPlayerTagHidden)
                        )
                    )
                )
        );
        dispatcher.register(literalArgumentBuilder);
    }
}
