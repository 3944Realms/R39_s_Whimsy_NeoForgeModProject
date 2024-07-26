package com.r3944realms.whimsy.api.websocket.message.role;

import com.r3944realms.whimsy.api.websocket.message.role.type.RoleType;

public class PlaceholderRole extends Role {
    public PlaceholderRole(String name) {
        super(name, RoleType.PLACEHOLDER);
    }
}
