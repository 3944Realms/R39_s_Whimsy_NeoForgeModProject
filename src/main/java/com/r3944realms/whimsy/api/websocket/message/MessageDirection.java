package com.r3944realms.whimsy.api.websocket.message;

import com.r3944realms.whimsy.api.websocket.message.role.Role;

import java.io.Serializable;

public record MessageDirection<T extends Role, K extends Role>(T sender, K receiver) implements Serializable {

    @Override
    public String toString() {
        return
                "MessageDirection:[ " + sender.type + " -> " + receiver.type + " ] {" + sender.name + " -> " + receiver.name + "}";
    }
}
