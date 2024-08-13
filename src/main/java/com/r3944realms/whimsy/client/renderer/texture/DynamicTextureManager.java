package com.r3944realms.whimsy.client.renderer.texture;

import com.google.common.hash.Hashing;
import com.r3944realms.whimsy.WhimsyMod;
import com.r3944realms.whimsy.content.components.ModDataComponents;
import com.r3944realms.whimsy.content.items.ModItemsRegister;
import com.r3944realms.whimsy.content.items.custom.DynamicTextureItem;
import com.r3944realms.whimsy.utils.logger.logger;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.HttpTexture;
import net.minecraft.client.renderer.texture.MissingTextureAtlasSprite;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;
import java.io.File;
import java.util.HashMap;

public class DynamicTextureManager {
    public static final ResourceLocation NO_SKIN = ResourceLocation.fromNamespaceAndPath(WhimsyMod.MOD_ID, "textures/skins/invalid.png");
    private static final HashMap<String, ResourceLocation> textures = new HashMap<>();
    private static final HashMap<String, BakedModel> models = new HashMap<>();
    public static ResourceLocation downloadTexture(String texture) {
        textures.put(texture, null);
        @SuppressWarnings("deprecation")
        String hashCode =  Hashing.sha1().hashUnencodedChars(texture).toString();
        ResourceLocation resourceLocation = ResourceLocation.fromNamespaceAndPath(WhimsyMod.MOD_ID ,"textures/" + hashCode);
        if (Minecraft.getInstance().getTextureManager().getTexture(resourceLocation, MissingTextureAtlasSprite.getTexture()) == MissingTextureAtlasSprite.getTexture()){
            Minecraft.getInstance().getTextureManager().getTexture(resourceLocation, new HttpTexture(new File(new File(Minecraft.getInstance().gameDirectory, hashCode.length() > 2 ? hashCode.substring(0, 2) : "xx"),hashCode),texture, DefaultPlayerSkin.getDefaultTexture(), false, () ->{
                addTexture(texture ,resourceLocation);
            }));
        } else {
            addTexture(texture, resourceLocation);
        }
        return resourceLocation;
    }
    private static void addTexture(String str, ResourceLocation texture) {
        textures.put(str , texture);
    }

    public static HashMap<String, BakedModel> getModels() {
        return new HashMap<>(models);
    }
    public static HashMap<String, ResourceLocation> getTextures() {
        return new HashMap<>(textures);
    }
    public static ResourceLocation getTextureLocationFromTextureTag(ItemStack itemStack, String texture) {
        String customTag = itemStack.getComponents().get(ModDataComponents.DYNAMIC_TEXTURE_URL.get());
        if (customTag == null) {
            return null;
        }
        if(textures.containsKey(customTag)) {
            ResourceLocation resourceLocation = textures.get(customTag);
            return resourceLocation == null ? NO_SKIN : resourceLocation;
        }
        return downloadTexture(texture);
    }
    @Nullable
    public static ItemStack getDynamicIconStack(ItemStack itemStack) {
        try {
            if(!(itemStack.getItem() instanceof DynamicTextureItem) || itemStack.getComponents().get(ModDataComponents.DYNAMIC_TEXTURE_URL.get()) == null) {
                return null;
            }
            ItemStack defaultInstance = ModItemsRegister.DYNAMIC_TEXTURE_ITEM.get().getDefaultInstance();
            defaultInstance.applyComponents(itemStack.getComponents());
            return defaultInstance;
        } catch (Exception e) {
            logger.error("Error from com.r3944realms.whimsy.client.renderer.texture.DynamicTextureManager.getDynamicIconStack " + e.getCause());
            return null;
        }
    }
}
