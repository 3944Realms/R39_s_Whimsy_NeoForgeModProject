package com.r3944realms.whimsy.api.SpringBoot.DG_LAB.Websocket;

import com.r3944realms.whimsy.api.SpringBoot.DG_LAB.Payload.DataPayload;
import com.r3944realms.whimsy.api.SpringBoot.DG_LAB.Payload.HeartBeatPayload;
import com.r3944realms.whimsy.api.SpringBoot.DG_LAB.Payload.Utils.MyPayloadFitClassTransformer;
import jakarta.websocket.Session;

import java.util.TimerTask;
import java.util.function.Consumer;

public class DG_LAB_TimerTask extends TimerTask {

    Session clientSession, targetSession;
    Integer RemainedSenderTimer_A, RemainedSenderTimer_B;
    DataPayload SenderDataPayload_A, SenderDataPayload_B;
    Consumer<Object> timeConsumer;
    public DG_LAB_TimerTask(Session clientSession, Session targetSession,
                            DataPayload SenderDataPayload_A, DataPayload SenderDataPayload_B,
                            Integer RemainedSenderTimer_A, Integer RemainedSenderTimer_B,
                            Consumer<Object> timeConsumer) {
        this.clientSession = clientSession;
        this.targetSession = targetSession;
        this.RemainedSenderTimer_A = RemainedSenderTimer_A;
        this.RemainedSenderTimer_B = RemainedSenderTimer_B;
        this.SenderDataPayload_A = SenderDataPayload_A;
        this.SenderDataPayload_B = SenderDataPayload_B;
        this.timeConsumer = timeConsumer;
    }

    private void send(Session session, DataPayload payload) {
        session.getAsyncRemote().sendText(WebsocketServer.gsonCodec.toJson(payload));
    }

    private void send(Session session, HeartBeatPayload payload) {
        session.getAsyncRemote().sendText(WebsocketServer.gsonCodec.toJson(MyPayloadFitClassTransformer.transform(payload)));
    }

    private void send(Session session, String message) {
        session.getAsyncRemote().sendText(message);
    }

    @Override
    public void run() {
        if(RemainedSenderTimer_A > 0) {
            send(clientSession, SenderDataPayload_A);
            RemainedSenderTimer_A--;
        }
        else if(RemainedSenderTimer_B > 0) {
            send(clientSession, SenderDataPayload_B);
            RemainedSenderTimer_B--;
        }
        if(RemainedSenderTimer_A <= 0 && RemainedSenderTimer_B <= 0) {
            send(clientSession, "Send All Successfully!");
            timeConsumer.accept(null);
        }
    }
}
