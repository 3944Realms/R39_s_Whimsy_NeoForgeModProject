package com.r3944realms.whimsy;

import net.minecraft.server.MinecraftServer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.server.ServerStartedEvent;

@EventBusSubscriber
public class ServerEventHandler {
    public static MinecraftServer serverInstance;

    public static void setServerInstance(MinecraftServer server){
        serverInstance = server;
    }

    public static MinecraftServer getServerInstance(){
        return serverInstance;
    }

    @SubscribeEvent
    public static void onServerStarted(ServerStartedEvent event){
        serverInstance = event.getServer();
    }
}
