package com.r3944realms.whimsy.content.gamerules.ClientRender;

import com.r3944realms.whimsy.WhimsyMod;
import com.r3944realms.whimsy.content.gamerules.Gamerules;
import com.r3944realms.whimsy.network.payload.BooleanGameRuleValueChangeData;
import com.r3944realms.whimsy.utils.Util;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.GameRules;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.function.BiConsumer;

@EventBusSubscriber(modid = WhimsyMod.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class MustOthersHiddenNameTag {
    public static final boolean DEFAULT_VALUE = false;
    public static final String ID = Util.getGameruleName(MustOthersHiddenNameTag.class);
    public static final String DESCRIPTION_KEY = Gamerules.getDescriptionKey(MustOthersHiddenNameTag.class);
    public static final String NAME_KEY = Gamerules.getNameKey(MustOthersHiddenNameTag.class);
    public static final GameRules.Category CATEGORY = GameRules.Category.PLAYER;
    public static final BiConsumer<MinecraftServer, GameRules.BooleanValue> NOTICE = (MinecraftServer server, GameRules.BooleanValue value) -> {
        server.getPlayerList().getPlayers().forEach(player -> {
            Gamerules.gamerulesBooleanValuesClient.put(ID, value.get());
            PacketDistributor.sendToPlayer(player, new BooleanGameRuleValueChangeData(ID, value.get()));//通知客户端
        });
    };
    @SubscribeEvent
    public static void commonSetup(final FMLCommonSetupEvent event) {
        Gamerules.gamerulesBooleanValuesClient.put(ID, DEFAULT_VALUE);
        Gamerules.GAMERULE_REGISTRY.registerGamerule(ID, CATEGORY, DEFAULT_VALUE, NOTICE);
    }

}
