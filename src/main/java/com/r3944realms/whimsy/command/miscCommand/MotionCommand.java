package com.r3944realms.whimsy.command.miscCommand;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.r3944realms.whimsy.command.WhimsyCommand;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

public class MotionCommand {
    private final static String WHIMSICALITY_MOTION_MESSAGE_ = "whimsy.command.motion.message.";
    public final static String MOTION_SETTER_SUCCESSFUL = WHIMSICALITY_MOTION_MESSAGE_ + "setter.successful",
                                MOTION_ADDER_SUCCESSFUL = WHIMSICALITY_MOTION_MESSAGE_ + "adder.successful";
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        LiteralArgumentBuilder<CommandSourceStack> literalArgumentBuilder = Commands.literal(WhimsyCommand.WHIMSICALITY_COMMAND);
        Command<CommandSourceStack> motionVecAdder = context -> {
            CommandSourceStack source = context.getSource();
            for(Entity entity : EntityArgument.getEntities(context, "targets")){
                Vec3 motionVec = new Vec3(
                        DoubleArgumentType.getDouble(context, "vecX"),
                        DoubleArgumentType.getDouble(context, "vecY"),
                        DoubleArgumentType.getDouble(context, "vecZ")
                );
                entity.addDeltaMovement(motionVec);
                entity.hurtMarked = true;
                double vecX = entity.getDeltaMovement().x, vecY = entity.getDeltaMovement().y, vecZ = entity.getDeltaMovement().z;
                source.sendSuccess(() -> Component.translatable(MOTION_ADDER_SUCCESSFUL, entity.getName().copy().withStyle(), vecX, vecY, vecZ), true);
            }
            return 0;
        };
        Command<CommandSourceStack> motionVecSetter = context -> {
            CommandSourceStack source = context.getSource();
            for(Entity entity : EntityArgument.getEntities(context, "targets")){
                Vec3 motionVec = new Vec3(
                        DoubleArgumentType.getDouble(context, "vecX"),
                        DoubleArgumentType.getDouble(context, "vecY"),
                        DoubleArgumentType.getDouble(context, "vecZ")
                );
                entity.setDeltaMovement(motionVec);
                entity.hurtMarked = true;
                double vecX = entity.getDeltaMovement().x, vecY = entity.getDeltaMovement().y, vecZ = entity.getDeltaMovement().z;
                source.sendSuccess(() -> Component.translatable(MOTION_SETTER_SUCCESSFUL, entity.getName().copy(), vecX, vecY, vecZ), true);
            }
            return 0;
        };
        literalArgumentBuilder
                .then(Commands.literal("motion").requires(cs -> cs.hasPermission(2))
                        .then(Commands.argument("targets", EntityArgument.entities())
                            .then(Commands.literal("add")
                                    .then(Commands.argument("vecX", DoubleArgumentType.doubleArg())
                                        .then(Commands.argument("vecY", DoubleArgumentType.doubleArg())
                                            .then(Commands.argument("vecZ", DoubleArgumentType.doubleArg())
                                                    .executes(motionVecAdder)
                                            )
                                        )
                                    )
                            )
                            .then(Commands.literal("set")
                                    .then(Commands.argument("vecX", DoubleArgumentType.doubleArg())
                                        .then(Commands.argument("vecY", DoubleArgumentType.doubleArg())
                                            .then(Commands.argument("vecZ", DoubleArgumentType.doubleArg())
                                                    .executes(motionVecSetter)
                                            )
                                        )
                                    )
                            )
                        )
                );
        dispatcher.register(literalArgumentBuilder);
    }
}
