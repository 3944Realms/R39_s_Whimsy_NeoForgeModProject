package com.r3944realms.dg_lab.future.websocket;

import com.r3944realms.dg_lab.future.websocket.hanlder.ServerDLPBHandler;
import com.r3944realms.dg_lab.future.websocket.sharedData.ServerPowerBoxSharedData;
import com.r3944realms.dg_lab.websocket.message.Message;
import com.r3944realms.dg_lab.websocket.message.PowerBoxMessage;
import com.r3944realms.dg_lab.websocket.message.role.WebSocketServerRole;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

public class PowerBoxWSServer extends AbstractWebSocketServer {
    protected ServerPowerBoxSharedData SharedData;
    protected WebSocketServerRole role;
    public PowerBoxWSServer(ServerPowerBoxSharedData sharedData, WebSocketServerRole role) {
        this.SharedData = sharedData;
        this.role = role;
    }

    @Override
    protected void MessagePipeLineHandler(ChannelPipeline pipeline) {
        pipeline.addLast(new ServerDLPBHandler(SharedData, role));
    }

    @Override
    public void send(String clientId, Message message) {
        if(message instanceof PowerBoxMessage PBMessage) {
            ChannelHandlerContext context = SharedData.connections.get(clientId);
            if(context != null) {
                String targetId = SharedData.relations.get(clientId);
                if(targetId != null) {
                    context.writeAndFlush(new TextWebSocketFrame(PBMessage.getMsgJson()));
                } else {
                    //TODO:待完善相关的 else 逻辑
                }

            } else {

            }
        } else {
            logger.error("Message is not a PowerBoxMessage");
        }
    }
}
