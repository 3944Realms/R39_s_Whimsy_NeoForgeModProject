package com.r3944realms.whimsy;

import com.mojang.brigadier.CommandDispatcher;
import com.r3944realms.whimsy.api.manager.WebsocketClientManager;
import com.r3944realms.whimsy.command.TestClientCommand;
import com.r3944realms.whimsy.command.Websocket.WebSocketClientCommand;
import com.r3944realms.whimsy.config.WebSocketClientConfig;
import com.r3944realms.whimsy.init.FilePathHelper;
import com.r3944realms.whimsy.utils.logger.logger;
import net.minecraft.commands.CommandSourceStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;
import net.neoforged.neoforge.client.event.RegisterClientCommandsEvent;
import net.neoforged.neoforge.common.NeoForge;

@EventBusSubscriber(modid = WhimsyMod.MOD_ID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public class ClientEventHandler {
    @SubscribeEvent
    public static void ClientSetup(FMLClientSetupEvent event) {
        NeoForge.EVENT_BUS.addListener(ClientEventHandler::onClientCommandsRegister);//客户端指令注册器
        NeoForge.EVENT_BUS.addListener(ClientEventHandler::onLoggingOn);
        NeoForge.EVENT_BUS.addListener(ClientEventHandler::onLoggingOut);
        FilePathHelper.HCJFileCreator();
        try {
            Class.forName("io.netty.handler.codec.http.HttpHeaders");
            logger.info("HttpHeaders class is available");
        } catch (ClassNotFoundException e) {
            logger.info("HttpHeaders class is not available");
            logger.info(e.getMessage());
        }
    }
    /**
     *CommandRegister In CLIENT SIDE
     */
    private static void onClientCommandsRegister(RegisterClientCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();
        WebSocketClientCommand.register(dispatcher);
        TestClientCommand.register(dispatcher);
    }
    public static void onLoggingOn(ClientPlayerNetworkEvent.LoggingIn event) {
        if(WebSocketClientConfig.WebSocketClientAutoManager.get()){
            WebsocketClientManager.INSTANCE.StartClient();
        }
    }
    public static void onLoggingOut(ClientPlayerNetworkEvent.LoggingOut event){
        if(WebSocketClientConfig.WebSocketClientAutoManager.get()){
            WebsocketClientManager.INSTANCE.StopClient();
        }
    }
}
