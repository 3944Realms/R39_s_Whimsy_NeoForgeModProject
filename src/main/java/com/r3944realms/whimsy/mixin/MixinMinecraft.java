package com.r3944realms.whimsy.mixin;

import com.r3944realms.whimsy.client.renderer.texture.DynamicTextureManager;
import com.r3944realms.whimsy.modInterface.IMinecraftExtension;
import net.minecraft.client.Minecraft;
import net.minecraft.client.main.GameConfig;
import net.minecraft.client.renderer.texture.TextureManager;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.File;

@Mixin(Minecraft.class)
public class MixinMinecraft implements IMinecraftExtension {
    @Final
    @Shadow
    private TextureManager textureManager;
    @Unique
    private DynamicTextureManager Whimsy$DynamicTextureManager;
    @Inject(method = {"<init>"}, at = @At("TAIL"))
    private void init(GameConfig pGameConfig, CallbackInfo ci) {
        File asssetFile = pGameConfig.location.assetDirectory;
        this.Whimsy$DynamicTextureManager = new DynamicTextureManager(textureManager, asssetFile.toPath().resolve("dynamic_images"));
    }

    @SuppressWarnings("AddedMixinMembersNamePattern")
    @Override
    public DynamicTextureManager getDynamicTextureManager() {
        return Whimsy$DynamicTextureManager;
    }
}
