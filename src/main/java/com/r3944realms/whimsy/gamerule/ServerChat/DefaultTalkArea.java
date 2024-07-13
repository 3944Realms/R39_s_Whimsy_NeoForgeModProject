package com.r3944realms.whimsy.gamerule.ServerChat;


import com.r3944realms.whimsy.WhimsyMod;
import com.r3944realms.whimsy.gamerule.GameruleRegistry;
import com.r3944realms.whimsy.utils.Util;
import net.minecraft.world.level.GameRules;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;


@EventBusSubscriber(modid = WhimsyMod.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class DefaultTalkArea {
    public static String CHAT_NONE_HEARD_YOU = "whimsy.chat.none_heard_you";
    public static final int DEFAULT_VALUE = -1;
    public static final String ID = Util.getGameruleName(DefaultTalkArea.class);
    public static final GameRules.Category CATEGORY = GameRules.Category.PLAYER;

    @SubscribeEvent
    public static void commonSetup(final FMLCommonSetupEvent event) {
        GameruleRegistry.INSTANCE.registerGamerule(ID, CATEGORY, DEFAULT_VALUE);
    }
}
