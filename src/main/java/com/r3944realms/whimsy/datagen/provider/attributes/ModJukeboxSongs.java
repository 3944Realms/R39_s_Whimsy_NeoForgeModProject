package com.r3944realms.whimsy.datagen.provider.attributes;

import com.r3944realms.whimsy.WhimsyMod;
import com.r3944realms.whimsy.content.sounds.ModSoundRegister;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.JukeboxSong;

public class ModJukeboxSongs {
    public static ResourceKey<JukeboxSong> SANDS_OF_TIME = create("sands_of_time");
    public static ResourceKey<JukeboxSong> HUB_MUSIC = create("hub_music");
    public static ResourceKey<JukeboxSong> ACE_RACE = create("ace_race");
    public static ResourceKey<JukeboxSong> GRID_RUNNER = create("grid_runner");
    public static ResourceKey<JukeboxSong> MELTDOWN = create("meltdown");

    public static void bootstrap(BootstrapContext<JukeboxSong> pContext) {
        JukeboxSongBootstrap(pContext);
    }
    public static void JukeboxSongBootstrap(BootstrapContext<JukeboxSong> pContext) {
        register(pContext, SANDS_OF_TIME, ModSoundRegister.SANDS_OF_TIME, 252, 0);
        register(pContext, HUB_MUSIC, ModSoundRegister.HUB_MUSIC, 136, 0);
        register(pContext, ACE_RACE, ModSoundRegister.ACR_RACE, 157, 0);
        register(pContext, MELTDOWN, ModSoundRegister.MELTDOWN, 300, 0);
        register(pContext, GRID_RUNNER, ModSoundRegister.GRID_RUNNERS, 139, 0);

    }

    private static void register(
            BootstrapContext<JukeboxSong> pContext, ResourceKey<JukeboxSong> pKey, Holder<SoundEvent> pSoundEvent, int pLengthInSeconds, int pComparatorOutput
    ) {
        pContext.register(
                pKey,
                new JukeboxSong(pSoundEvent, Component.translatable(Util.makeDescriptionId("jukebox_song", pKey.location())), (float)pLengthInSeconds, pComparatorOutput)
        );
    }
    private static ResourceKey<JukeboxSong> create(String pName) {
        return ResourceKey.create(Registries.JUKEBOX_SONG, ResourceLocation.fromNamespaceAndPath(WhimsyMod.MOD_ID, pName));
    }

}
