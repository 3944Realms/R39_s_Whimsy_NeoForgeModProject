package com.r3944realms.whimsy.api.websocket.message.role;

import com.r3944realms.whimsy.api.websocket.message.role.type.RoleType;

public class WebSocketClientRole extends Role {
    public WebSocketClientRole(String name) {
        super(name, RoleType.T_CLIENT);
    }
}
