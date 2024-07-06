package com.r3944realms.whimsy.api.SpringBoot.DG_LAB.Payload.Utils;

import com.r3944realms.whimsy.api.SpringBoot.DG_LAB.Payload.DataContext.PowerBoxData;
import com.r3944realms.whimsy.api.SpringBoot.DG_LAB.Payload.DataPayload;
import com.r3944realms.whimsy.api.SpringBoot.DG_LAB.Payload.HeartBeatPayload;

public class MyPayloadFitClassTransformer {
     static public class result {
        String type;
        String ClientId;
        String targetId;
        String message;
        public result(String type, String ClientId, String targetId, String message) {
            this.type = type;
            this.ClientId = ClientId;
            this.targetId = targetId;
            this.message = message;
        }
    }
    static public result transform(DataPayload payload) {
        return new result(payload.type(), payload.clientID(), payload.targetID(), ((PowerBoxData) payload.data()).getMessage_A());
    }
    static public result transform(HeartBeatPayload payload) {
        return new result(payload.type(), payload.clientID(), payload.targetID(), payload.message());
    }
}
