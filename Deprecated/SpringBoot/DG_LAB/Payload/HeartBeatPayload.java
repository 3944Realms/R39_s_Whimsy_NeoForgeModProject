package com.r3944realms.whimsy.api.SpringBoot.DG_LAB.Payload;


public record HeartBeatPayload(String clientID,
                               String targetID) {
    public String type() {
        return "heartbeat";
    }

    public String message() {
        return "200";
    }
}
