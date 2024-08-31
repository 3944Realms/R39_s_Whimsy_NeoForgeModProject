package com.r3944realms.whimsy;

import com.mojang.brigadier.CommandDispatcher;
import com.r3944realms.dg_lab.manager.WebsocketServerManager;
import com.r3944realms.dg_lab.websocket.WebSocketServer;
import com.r3944realms.dg_lab.websocket.utils.enums.SendMode;
import com.r3944realms.whimsy.config.WebSocketServerConfig;
import com.r3944realms.whimsy.content.commands.MiscCommand.LeashCommand;
import com.r3944realms.whimsy.content.commands.MiscCommand.MotionCommand;
import com.r3944realms.whimsy.content.commands.PlayerProperty.ChatCommand;
import com.r3944realms.whimsy.content.commands.PlayerProperty.NameTagCommand;
import com.r3944realms.whimsy.content.commands.TestServerCommand;
import com.r3944realms.whimsy.content.commands.Websocket.WebSocketServerCommand;
import com.r3944realms.whimsy.content.effects.ModPotionRegister;
import com.r3944realms.whimsy.content.gamerules.ClientRender.MustOthersHiddenNameTag;
import com.r3944realms.whimsy.content.gamerules.Gamerules;
import com.r3944realms.whimsy.network.payload.BooleanGameRuleValueChangeData;
import com.r3944realms.whimsy.utils.logger.logger;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.Potions;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.brewing.RegisterBrewingRecipesEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.server.ServerStartedEvent;
import net.neoforged.neoforge.event.server.ServerStoppedEvent;
import net.neoforged.neoforge.network.PacketDistributor;


public class CommonEventHandler {

    @EventBusSubscriber(modid = WhimsyMod.MOD_ID)
    public static class CommonGameBusEventHandler extends CommonEventHandler {
        public static MinecraftServer serverInstance;
        @SubscribeEvent
        static void onRegisterPotionBrewing(RegisterBrewingRecipesEvent event){
            PotionBrewing.Builder builder = event.getBuilder();
            builder.addMix(Potions.WATER, Items.APPLE, ModPotionRegister.DRUNK);
            builder.addMix(Potions.WATER, Items.WHEAT, ModPotionRegister.DRUNK);
            builder.addMix(ModPotionRegister.DRUNK, Items.FERMENTED_SPIDER_EYE, ModPotionRegister.STRONG_DRUNK);
            builder.addMix(ModPotionRegister.DRUNK, Items.SUGAR, ModPotionRegister.LONG_DRUNK);
        }
        @SubscribeEvent
        static void onServerStarted(ServerStartedEvent event) {
            serverInstance = event.getServer();
            Boolean enableMsgMode = WebSocketServerConfig.isEnableWebSocketTextMessageMode.get();
            WebSocketServer.setMode(enableMsgMode ? SendMode.ClientMessage : SendMode.OnlyText);
            WebSocketServer.BindingPort(WebSocketServerConfig.WebSocketServerPort.get());
            if(WebSocketServerConfig.WebSocketServerAutoManager.get()) {
                WebsocketServerManager.getManager().StartServer();
            }
        }
        @SubscribeEvent
        static void onServerStopped(ServerStoppedEvent event) {
            if(WebSocketServerConfig.WebSocketServerAutoManager.get()) {
                WebsocketServerManager.getManager().StopServer();
            }
        }
        @SubscribeEvent
        static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
            final BooleanGameRuleValueChangeData syncPayload = new BooleanGameRuleValueChangeData(MustOthersHiddenNameTag.ID, Gamerules.gamerulesBooleanValuesClient.get(MustOthersHiddenNameTag.ID));
            logger.info("==-=-=-=-=-=-=-=-=-=-=-=-=--=");
            PacketDistributor.sendToPlayer((ServerPlayer) event.getEntity(), syncPayload);
        }
        /**
         *CommandRegister In BOTH SIDE
         */
        @SubscribeEvent
        static void onRegisterCommander(RegisterCommandsEvent event) {
            CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();
            WebSocketServerCommand.register(dispatcher);
            ChatCommand.register(dispatcher);
            MotionCommand.register(dispatcher);
            NameTagCommand.register(dispatcher);
            LeashCommand.register(dispatcher);

            TestServerCommand.register(dispatcher);
        }

    }

    @EventBusSubscriber(modid = WhimsyMod.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
    public static class CommonModBusEventHandler extends CommonEventHandler {
        @SubscribeEvent
        public static void CommonSetUp(FMLCommonSetupEvent event) {

        }
    }

    public static void setServerInstance(MinecraftServer server){
        CommonEventHandler.CommonGameBusEventHandler.serverInstance = server;
    }

    public static MinecraftServer getServerInstance() {
        return CommonEventHandler.CommonGameBusEventHandler.serverInstance;
    }

}
