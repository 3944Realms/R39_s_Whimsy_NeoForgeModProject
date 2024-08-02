package com.r3944realms.whimsy.client.renderer;

import com.google.common.collect.ImmutableMap;
import com.mojang.logging.LogUtils;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.slf4j.Logger;

import java.util.Map;


@OnlyIn(Dist.CLIENT)
public class ModEntityRenderers {
    private static final Logger LOGGER = LogUtils.getLogger();
    public static final Map<EntityType<?>, EntityRendererProvider<?>> PROVIDERS = new Object2ObjectOpenHashMap<>();
    public static final Map<PlayerSkin.Model, EntityRendererProvider<AbstractClientPlayer>> KID_PLAYER_PROVIDES =
            Map.of(
                    PlayerSkin.Model.WIDE, pContext -> new NewKidPlayerRenderer(pContext, false),
                    PlayerSkin.Model.SLIM, pContext -> new NewKidPlayerRenderer(pContext, true)
            );
    public static <T extends Entity> void register(EntityType<? extends T> pEntityType, EntityRendererProvider<T> pProvider) {
        PROVIDERS.put(pEntityType, pProvider);
    }
    public static Map<PlayerSkin.Model, EntityRenderer<? extends Player>> createPlayerRenderers(EntityRendererProvider.Context context) {
        ImmutableMap.Builder<PlayerSkin.Model, EntityRenderer<? extends Player>> builder = ImmutableMap.builder();
        KID_PLAYER_PROVIDES.forEach((model, provider) -> {
            try {
                builder.put(model, provider.create(context));
            } catch (Exception exception) {
                throw new IllegalArgumentException("Failed to create player model for " + model, exception);
            }
        });
        return builder.build();
    }
    public static boolean validateRegistrations() {
        boolean flag = true;

        for (EntityType<?> entityType : BuiltInRegistries.ENTITY_TYPE) {
            if (entityType != EntityType.PLAYER && !PROVIDERS.containsKey(entityType)) {
                LOGGER.warn("No renderer registered for {}", BuiltInRegistries.ENTITY_TYPE.getKey(entityType));
                flag = false;
            }
        }
        return !flag;
    }


}
