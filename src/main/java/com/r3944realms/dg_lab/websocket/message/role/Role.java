package com.r3944realms.dg_lab.websocket.message.role;

import com.r3944realms.dg_lab.websocket.message.role.type.RoleType;

import java.io.Serializable;

public abstract class Role implements Serializable {
    public final String name;
    public final RoleType type;
    Role(final String name, final RoleType type) {
        this.name = name;
        this.type = type;
    }
}

