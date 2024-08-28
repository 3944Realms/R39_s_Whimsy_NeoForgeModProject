package com.r3944realms.whimsy.content.sounds;

import com.r3944realms.whimsy.WhimsyMod;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModSoundRegister {
    public static DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, WhimsyMod.MOD_ID);
    public static ResourceLocation RL_SANDS_OF_TIME = ResourceLocation.fromNamespaceAndPath(WhimsyMod.MOD_ID, "music/sands_of_time/main_music");
    public static ResourceLocation RL_HUB_MUSIC = ResourceLocation.fromNamespaceAndPath(WhimsyMod.MOD_ID, "music/hub/main_music");
    public static ResourceLocation RL_ACE_RACE = ResourceLocation.fromNamespaceAndPath(WhimsyMod.MOD_ID, "music/ace_race/main_music");
    public static ResourceLocation RL_GRID_RUNNERS = ResourceLocation.fromNamespaceAndPath(WhimsyMod.MOD_ID, "music/grid_runners/main_music");
    public static ResourceLocation RL_MELTDOWN = ResourceLocation.fromNamespaceAndPath(WhimsyMod.MOD_ID, "music/meltdown/main_music");
    public static final DeferredHolder<SoundEvent, SoundEvent> SANDS_OF_TIME =
            ModSoundRegister.register("sands_of_time", () -> SoundEvent.createFixedRangeEvent(
                    RL_SANDS_OF_TIME,
                    128
            ));
    public static final DeferredHolder<SoundEvent, SoundEvent> HUB_MUSIC =
            ModSoundRegister.register("hub_music", () -> SoundEvent.createFixedRangeEvent(
                    RL_HUB_MUSIC,
                    128
            ));
    public static final DeferredHolder<SoundEvent, SoundEvent> ACR_RACE =
            ModSoundRegister.register("acr_race", () -> SoundEvent.createFixedRangeEvent(
                    RL_ACE_RACE,
                    128
            ));
    public static final DeferredHolder<SoundEvent, SoundEvent> GRID_RUNNERS =
            ModSoundRegister.register("grid_runners", () -> SoundEvent.createFixedRangeEvent(
                    RL_GRID_RUNNERS,
                    128
            ));
    public static final DeferredHolder<SoundEvent, SoundEvent> MELTDOWN =
            ModSoundRegister.register("meltdown", () -> SoundEvent.createFixedRangeEvent(
                    RL_MELTDOWN,
                    128
            ));

    public static DeferredHolder<SoundEvent, SoundEvent> register(String name, Supplier<SoundEvent> supplier){
        return SOUNDS.register(name, supplier);

    }
    public static void register(IEventBus modBus){
        SOUNDS.register(modBus);
    }
    public static String getJukeboxSongTranslateKey(String name) {
        return "jukebox_song." + WhimsyMod.MOD_ID + "." + name;
    }
    public static String getSubTitleTranslateKey(String name) {
        return "sound." + WhimsyMod.MOD_ID + ".subtitle." + name;
    }
}
