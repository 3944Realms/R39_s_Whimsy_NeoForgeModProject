package com.r3944realms.whimsy.network;

import com.r3944realms.whimsy.network.payload.TestModData;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class ClientPayloadHandler {
    private static final ClientPayloadHandler INSTANCE = new ClientPayloadHandler();

    public static ClientPayloadHandler getInstance() {
        return INSTANCE;
    }
    public void handleTestData(final TestModData data, final IPayloadContext context) {

    }
}
