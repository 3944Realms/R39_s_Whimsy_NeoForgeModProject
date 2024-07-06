package com.r3944realms.whimsy.api.SpringBoot.DG_LAB.Payload;

import com.r3944realms.whimsy.api.SpringBoot.DG_LAB.Payload.DataContext.IData;
import com.r3944realms.whimsy.api.SpringBoot.DG_LAB.Payload.DataContext.PowerBoxData;

public record DataPayload(String type,
                          String clientID,
                          String targetID,
                          IData data) {
    public DataPayload(String type, String clientID, String targetID) {
         this(type, clientID, targetID, new PowerBoxData());
    }


}
