package com.r3944realms.whimsy.utils.Notice;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.neoforged.fml.loading.FMLEnvironment;

public class NoticePlayer {
    public static void sendComponentToPlayer(Player player, Component component) {
        player.sendSystemMessage(component);
    }
    public static void showMessageToLocalPlayer(Player player, Component component) {
        if(!FMLEnvironment.dist.isClient()) return;
        player.displayClientMessage(component, false);
    }
}
