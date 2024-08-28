package com.r3944realms.whimsy.client.renderer.texture;

import com.google.common.hash.Hashing;
import com.r3944realms.whimsy.WhimsyMod;
import com.r3944realms.whimsy.content.components.ModDataComponents;
import com.r3944realms.whimsy.content.items.ModItemsRegister;
import com.r3944realms.whimsy.content.items.custom.capcity.IDynamicTexture;
import com.r3944realms.whimsy.utils.NetworkUtils.UrlValidator;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.client.renderer.texture.HttpTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@OnlyIn(Dist.CLIENT)
public class DynamicTextureManager {
    static final Logger logger = LoggerFactory.getLogger(DynamicTextureManager.class);
    public static final ResourceLocation NO_SKIN = ResourceLocation.fromNamespaceAndPath(WhimsyMod.MOD_ID, "textures/skins/invalid.png");
    private final DynamicTextureManager.DynamicTextureCache cache;
    public DynamicTextureManager(TextureManager textureManager, Path path) {
        boolean isCreated = path.toFile().mkdirs();
        if (!isCreated) {
            logger.info("Dynamic_texture File is existed!");
        }
        this.cache = new DynamicTextureManager.DynamicTextureCache(textureManager, path);
    }

    public Map<String, ResourceLocation> immutableMap() {
        Map<String, ResourceLocation> result = new HashMap<>();
        this.cache.textures.forEach(
                (key, value) -> result.put(key, value.getNow(NO_SKIN))
        );
        return result;
    }


    public Optional<ResourceLocation> getTextureLocationFromTextureTag(ItemStack itemStack){
        String customTag = itemStack.getComponents().get(ModDataComponents.DYNAMIC_TEXTURE_URL.get());
        if (customTag == null) {
            return Optional.empty();
        }
        ResourceLocation resourceLocation;
        resourceLocation = cache.getOrLoad(customTag).getNow(NO_SKIN);
        return Optional.of(resourceLocation == null ? NO_SKIN : resourceLocation);
    }
    @Nullable
    public static ItemStack getDynamicIconStack(ItemStack itemStack) {
        Item item = itemStack.getItem();
        if (!(item instanceof IDynamicTexture)) {
            return null;
        }

        String dynamicTextureUrl = itemStack.getComponents().get(ModDataComponents.DYNAMIC_TEXTURE_URL.get());
        if (dynamicTextureUrl == null) {
            return null;
        }

        try {
            ItemStack defaultInstance = ModItemsRegister.DYNAMIC_TEXTURE_ITEM.get().getDefaultInstance();
            defaultInstance.applyComponents(itemStack.getComponents());
            return defaultInstance;
        } catch (Exception e) {
            logger.error("Error in DynamicTextureManager.getDynamicIconStack: {}", e.getMessage());
            return null;
        }
    }
    public static class DynamicTextureCache {
        private final TextureManager textureManager;
        private final Path root;
//        private final Map<ResourceLocation, CompletableFuture<BakedModel>> models = new Object2ObjectOpenHashMap<>();
        private final Map<String, CompletableFuture<ResourceLocation>> textures = new Object2ObjectOpenHashMap<>();

        public DynamicTextureCache(TextureManager textureManager, Path root) {
            this.textureManager = textureManager;
            this.root = root;
        }

        public CompletableFuture<ResourceLocation> getOrLoad(String textureUrl) {
            String hashCode = Hashing.sha256().hashUnencodedChars(textureUrl).toString();
            CompletableFuture<ResourceLocation> result = this.textures.get(hashCode);
            if(result == null) {
                if(!UrlValidator.isValidImageUri(textureUrl)) {
                    logger.error("Not Valid Image URL: {}", textureUrl);
                    return CompletableFuture.completedFuture(null);
                }
                result = this.registerTexture(textureUrl);
                this.textures.put(hashCode, result);
                logger.debug("Texture added: {} -> {}", textureUrl, hashCode);
            }
            return result;
        }
        public CompletableFuture<ResourceLocation> registerTexture(String textureUrl) {
            String hashCode = Hashing.sha256().hashUnencodedChars(textureUrl).toString();
            ResourceLocation resourceLocation = getTextureLocation(hashCode);
            Path path = this.root.resolve(hashCode.length() > 2 ? hashCode.substring(0,2) : "xx").resolve(hashCode);
            // Check if the texture is already loaded, if not, download and load it.
            CompletableFuture<ResourceLocation> result = new CompletableFuture<>();
            // TODO NaiveImage充当了其中的读取缓存的角色
            HttpTexture httpTexture = new HttpTexture(
                    path.toFile(),
                    textureUrl,
                    DefaultPlayerSkin.getDefaultTexture(),
                    false,
                    () -> result.complete(resourceLocation));
            this.textureManager.register(resourceLocation, httpTexture);
            return result;
        }
        //For Extension [More Type]
        public ResourceLocation getTextureLocation(String pName) {
            return ResourceLocation.withDefaultNamespace("dynamic_images/" +  pName);
        }

    }

}
