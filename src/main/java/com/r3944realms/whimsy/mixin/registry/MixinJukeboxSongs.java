package com.r3944realms.whimsy.mixin.registry;

import com.r3944realms.whimsy.datagen.provider.attributes.ModJukeboxSongs;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.item.JukeboxSong;
import net.minecraft.world.item.JukeboxSongs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(JukeboxSongs.class)
public interface MixinJukeboxSongs {
    @Inject(method = {"bootstrap"}, at = @At("TAIL"))
    private static void bootstrap(BootstrapContext<JukeboxSong> pContext, CallbackInfo ci) {
        ModJukeboxSongs.JukeboxSongBootstrap(pContext);
    }
}
