package com.r3944realms.whimsy.api.websocket.protocol;

import com.r3944realms.whimsy.api.APILanguageKey;
import com.r3944realms.whimsy.api.websocket.WebSocketClient;
import com.r3944realms.whimsy.api.websocket.message.PowerBoxMessage;
import com.r3944realms.whimsy.api.websocket.message.data.PowerBoxData;
import com.r3944realms.whimsy.api.websocket.message.role.WebSocketClientRole;
import com.r3944realms.whimsy.api.websocket.message.role.WebSocketServerRole;
import com.r3944realms.whimsy.api.websocket.message.role.type.RoleType;
import com.r3944realms.whimsy.content.items.ModItemsRegister;
import com.r3944realms.whimsy.init.FilePathHelper;
import com.r3944realms.whimsy.utils.Notice.NoticePlayer;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.ItemStack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.SocketException;
import java.util.Objects;
import java.util.Timer;

public class ClientMessageWebsocketHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    public static String connectionId = "",
                            targetWSId = "";
    private static final int delay = 500; //防抖
    private static Timer delayTimer;
    private static final boolean followAStrength = false;//跟随AB的软上限
    private static final boolean followBStrength = false;
    private static final Logger logger = LoggerFactory.getLogger(ClientMessageWebsocketHandler.class);
    /**
     * 接收和发送的消息都是Message而不是Data 开关
     */
    private static final boolean SRMsg = false;

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        logger.info("连接已建立");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        PowerBoxMessage dataMsg;
        PowerBoxData data = null;
        String json = msg.text();
        if(SRMsg) {
            dataMsg = PowerBoxMessage.getNullMessage().getMessage(json);
            if(dataMsg.direction.sender().type != RoleType.T_SERVER && !Objects.equals(dataMsg.direction.sender().name, "IWebsocketServer")) {
                logger.info("消息验证者错误：{}", dataMsg.direction.sender().name);
                data = PowerBoxMessage.getNullMessage().getPayload(dataMsg.getInvalidMessageJson());
            }
        } else {
            data = PowerBoxMessage.getNullMessage().getPayload(json);
        }
        assert data != null;
        switch (data.getCommandType()) {
            case _NC_BIND_ -> {
                if(data.getTargetId().isEmpty()) {
                    //初次连接客户端获取服务器指定id
                    connectionId = data.getClientId();
                    logger.info("收到clientId: {}",connectionId);
                    String qrCodeContext = "https://www.dungeon-lab.com/app-download.php#DGLAB-SOCKET#" + WebSocketClient.getUrl()  + connectionId;
                    FilePathHelper.ReCreateHCJFile(qrCodeContext);
                    try {
                        File file = FilePathHelper.get_HCJ_HTML_Path().toFile().getAbsoluteFile();
                        MutableComponent fileComponent = Component.literal(file.getName()).withStyle(ChatFormatting.UNDERLINE);
                        fileComponent.withStyle((style) -> {
                                    return style.withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_FILE, file.getAbsolutePath()))
                                            .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_ITEM, new HoverEvent.ItemStackInfo(new ItemStack(ModItemsRegister.TEST_ITEM.get()))));
                                }
                        );
                        Component link_display = Component.translatable(APILanguageKey.PB_LINK_OF_QRCODE, fileComponent);
                        NoticePlayer.showMessageToLocalPlayer(Minecraft.getInstance().player, link_display);
                    } catch (Exception e) {
                        logger.debug("Notice File Link Failed");
                    }
                    logger.debug("重新生成QrCodeContext: {}",qrCodeContext);
                } else {
                    if(!Objects.equals(data.getClientId(), connectionId)) {
                        logger.debug("接收到不正确的target消息,消息中目标Id{}",data.getClientId());
                        return;
                    }
                    targetWSId = data.getTargetId();
                    logger.info("已建立绑定连接，TargetId:{}, msg:{}",targetWSId,data.getMessage());
                    try {
                        Component bind_suc = Component.translatable(APILanguageKey.PB_BIND_SUCCESSFUL);
                        NoticePlayer.showMessageToLocalPlayer(Minecraft.getInstance().player, bind_suc);
                    } catch (Exception e) {
                        logger.debug("Notice Failed");
                    }
                }
            }
            case _NC_BREAK_ -> {
                //对方断开
                if (!Objects.equals(data.getTargetId(), targetWSId)) {
                    return;
                }
                logger.info("对方已断开，targetId:{}, msg:{}", targetWSId, data.getMessage());
            }
            case _NC_ERROR_ -> {
                if (!Objects.equals(data.getTargetId(), targetWSId)) {
                    return;
                }
                logger.info("接收到错误码:{}",data.getMessage());
            }
            case _NC_HEARTBEAT_ -> {
                logger.info("收到心跳");
            }
            default -> {
                logger.info("收到其它信息{}",data.getMessage());
            }
        }

    }
    private void sendMsgOrData(ChannelHandlerContext target, PowerBoxMessage dataMsg) {
        String json = SRMsg ? dataMsg.getMsgJson() : dataMsg.getDataJson();
        target.channel().writeAndFlush(new TextWebSocketFrame(json));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error("连接出现错误：{}",cause.getMessage());
        //错误处理逻辑待加入
        if(cause instanceof SocketException) {
            logger.info("远程服务器关闭");
        }
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        PowerBoxMessage breakMsg = PowerBoxMessage.createPowerBoxMessage("break", connectionId, targetWSId, "200", new WebSocketClientRole("Cl" + connectionId), new WebSocketServerRole("WebSocketServer"));
        if(ctx != null && ctx.channel().isActive() && ctx.channel().isOpen()) sendMsgOrData(ctx, breakMsg);
        logger.info("连接已断开");

    }
}
