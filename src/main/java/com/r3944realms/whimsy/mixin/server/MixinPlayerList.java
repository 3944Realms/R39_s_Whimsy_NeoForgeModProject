package com.r3944realms.whimsy.mixin.server;

import com.r3944realms.whimsy.content.gamerules.GameruleRegistry;
import com.r3944realms.whimsy.content.gamerules.Server.DefaultTalkArea;
import com.r3944realms.whimsy.modInterface.player.ServerPlayerCapacity;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.OutgoingChatMessage;
import net.minecraft.network.chat.PlayerChatMessage;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;
import java.util.function.Predicate;

@Mixin(PlayerList.class)
public abstract class MixinPlayerList {

    @Unique
    private boolean Whimsy$HasAnyoneReceivedMessage;
    @Unique
    private ServerPlayer Whimsy$PSender;

    @Shadow
    public abstract MinecraftServer getServer();

    @Inject(method = {"broadcastChatMessage(Lnet/minecraft/network/chat/PlayerChatMessage;Ljava/util/function/Predicate;Lnet/minecraft/server/level/ServerPlayer;Lnet/minecraft/network/chat/ChatType$Bound;)V"}, at = {@At("HEAD")})
    private void broadcastChatMessageHead(PlayerChatMessage pMessage, Predicate<ServerPlayer> pShouldFilterMessageTo, @Nullable ServerPlayer pSender, ChatType.Bound pBoundChatType, CallbackInfo CallbackInfo){
        Whimsy$HasAnyoneReceivedMessage = false;
        Whimsy$PSender = pSender;
    }

    @Inject(method = {"broadcastChatMessage(Lnet/minecraft/network/chat/PlayerChatMessage;Ljava/util/function/Predicate;Lnet/minecraft/server/level/ServerPlayer;Lnet/minecraft/network/chat/ChatType$Bound;)V"}, at = {@At("TAIL")})
    private void broadcastChatMessageTail(PlayerChatMessage pMessage, Predicate<ServerPlayer> pShouldFilterMessageTo, @Nullable ServerPlayer pSender, ChatType.Bound pBoundChatType, CallbackInfo CallbackInfo){
        ServerPlayerCapacity sSenderPlayer = (ServerPlayerCapacity) pSender;
        if (sSenderPlayer != null && (this.Whimsy$HasAnyoneReceivedMessage || sSenderPlayer.Whimsy$GetTalkArea() == -1)) {
            return;
        }
        if (pSender != null) {
            pSender.sendSystemMessage(Component.translatable(DefaultTalkArea.CHAT_NONE_HEARD_YOU).withStyle(ChatFormatting.RED), true);
        }
    }
    @Redirect(
            method = {"broadcastChatMessage(Lnet/minecraft/network/chat/PlayerChatMessage;Ljava/util/function/Predicate;Lnet/minecraft/server/level/ServerPlayer;Lnet/minecraft/network/chat/ChatType$Bound;)V"},
                at = @At(
                        value = "INVOKE",
                        target = "Lnet/minecraft/server/level/ServerPlayer;sendChatMessage(Lnet/minecraft/network/chat/OutgoingChatMessage;ZLnet/minecraft/network/chat/ChatType$Bound;)V"
                )
    )
    public void MixinSendChatMessage(ServerPlayer pReceiver, OutgoingChatMessage pMessage, boolean pFiltered, ChatType.Bound pBoundType) {
        boolean InRangeHasReceiver = false;
        if(Whimsy$PSender == null) {
            pReceiver.sendChatMessage(pMessage, true, pBoundType);
            return;
        }
        int SenderEffectiveTalkArea = Whimsy$GetEffectiveTalkArea(Whimsy$PSender),
            ReceiverEffectiveTalkArea = Whimsy$GetEffectiveTalkArea(pReceiver);
        if(pReceiver.level() != Whimsy$PSender.level()) {
            // Players are in different worlds
            // 接收者与发送者不在同一个维度
            if(SenderEffectiveTalkArea > 0 || ReceiverEffectiveTalkArea > 0) {
                //But at least one has a non-zero effective talk area
                //但有一方有聊天距离设置
                return;
            }
        }
        float distance = pReceiver.distanceTo(Whimsy$PSender);
        if(distance > ReceiverEffectiveTalkArea && ReceiverEffectiveTalkArea > 0) {
            //Receiver's EffectiveTalkArea is smaller than the distance of both
            //接收者设置距离比发送者距离小
           return;
        }
        if(SenderEffectiveTalkArea > 0) {
            //Sender's EffectiveTalkArea is non-zero
            //发送者距离非零
            InRangeHasReceiver = true;
            if(distance > SenderEffectiveTalkArea) {
                //But too far
                //但距离大于它
                return;
            }
        }
        if(pReceiver != Whimsy$PSender) {
            //不是同一人，能执行到这里就说明有玩家可以被接收到消息
            this.Whimsy$HasAnyoneReceivedMessage = true;
        }
        if(InRangeHasReceiver) {
            PlayerChatMessage message = ((OutgoingChatMessage.Player) pMessage).message();
            String content = "§c*§r" + pMessage.content().getString(256 - "§c*§r".length());
            PlayerChatMessage playerChatMessage = new PlayerChatMessage(message.link(), message.signature(), message.signedBody(), Component.literal(content), message.filterMask());
            pMessage = OutgoingChatMessage.create(playerChatMessage);
        }
        pReceiver.sendChatMessage(pMessage, pFiltered, pBoundType);
    }

    @Unique
    private static int Whimsy$GetEffectiveTalkArea(ServerPlayer pPlayer) {
        return Math.max(((ServerPlayerCapacity)pPlayer).Whimsy$GetTalkArea(), GameruleRegistry.getGameruleIntValue(pPlayer.level(), DefaultTalkArea.ID));
    }

}
