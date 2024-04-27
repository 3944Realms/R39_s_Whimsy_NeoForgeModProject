package com.r3944realms.whimsy;

import com.mojang.logging.LogUtils;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import org.slf4j.Logger;

@Mod(WhimsyMod.MOD_ID)
public class WhimsyMod {
    public static final String MOD_ID = "whimsicality";
    private static final Logger LOGGER = LogUtils.getLogger();
    public WhimsyMod () { /*加载对象->*/

    }
    /**
     * 命令注册事务
     */
    @SubscribeEvent
    public void onRegisterCommander(RegisterCommandsEvent event) {//命令注册

    }
    private void commonSetup(final FMLCommonSetupEvent event) {

    }
    /**
     * 服务端启动事务
     */
    public void onServerStarting(ServerStartingEvent event) {

    }
    @Mod.EventBusSubscriber(modid = WhimsyMod.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModEvent {
        @SubscribeEvent
        public static void ClientSetup(FMLClientSetupEvent event) {

        }
    }
}

