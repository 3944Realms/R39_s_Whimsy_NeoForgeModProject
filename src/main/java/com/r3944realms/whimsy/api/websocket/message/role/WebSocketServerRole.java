package com.r3944realms.whimsy.api.websocket.message.role;

import com.r3944realms.whimsy.api.websocket.message.role.type.RoleType;

public class WebSocketServerRole extends Role {
    public WebSocketServerRole(String name) {
        super(name, RoleType.T_SERVER);
    }
}
