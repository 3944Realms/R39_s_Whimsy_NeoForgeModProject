package com.r3944realms.whimsy.content.gamerules.ServerChat;

import com.r3944realms.whimsy.WhimsyMod;
import com.r3944realms.whimsy.content.gamerules.Gamerules;
import com.r3944realms.whimsy.utils.Util;
import net.minecraft.world.level.GameRules;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;

import static com.r3944realms.whimsy.content.gamerules.Gamerules.GAMERULE_REGISTRY;

//It had so Long Name at first.(
@EventBusSubscriber(modid = WhimsyMod.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class TeleportWithLeashedPlayers {
    public static final boolean DEFAULT_VALUE = true;
    public static final String ID = Util.getGameruleName(TeleportWithLeashedPlayers.class);
    public static final String DESCRIPTION_KEY = Gamerules.getDescriptionKey(TeleportWithLeashedPlayers.class);
    public static final String NAME_KEY = Gamerules.getNameKey(TeleportWithLeashedPlayers.class);
    public static final GameRules.Category CATEGORY = GameRules.Category.PLAYER;

    @SubscribeEvent
    public static void onCommonSetup(final FMLCommonSetupEvent event) {
        GAMERULE_REGISTRY.registerGamerule(ID, CATEGORY, DEFAULT_VALUE);
    }
}
