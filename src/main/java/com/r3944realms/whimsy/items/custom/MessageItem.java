package com.r3944realms.whimsy.items.custom;

import com.r3944realms.whimsy.network.payload.TestModData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;

public class MessageItem extends Item {
    public MessageItem(Properties pProperties) {
        super(pProperties);
    }
    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level world, @NotNull Player player, @NotNull InteractionHand hand) {
        if(world.isClientSide) {
            PacketDistributor.sendToServer(new TestModData("Form Client",2));
        }
        if(!world.isClientSide) {
            PacketDistributor.sendToPlayer((ServerPlayer) player, new TestModData("Form Server",2));
        }
        return super.use(world, player, hand);
    }
}
