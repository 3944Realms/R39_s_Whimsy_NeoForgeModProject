package com.r3944realms.whimsy.utils.LevelHelper;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.CommonHooks;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

public class LevelHelper {
    private static final Logger logger = LoggerFactory.getLogger(LevelHelper.class);
    private static final Map<String, ResourceKey<Level>> levelsMap = new HashMap<>();
    private static List<ResourceKey<Level>> allLevels = null;
    public static final int MapSize;
    static {
        init();
        MapSize = levelsMap.size();
    }
    private static void init() {
        HolderLookup.RegistryLookup<Level> lookup = CommonHooks.resolveLookup(Registries.DIMENSION);
        List<Holder.Reference<Level>> referenceList;
        if(lookup == null) {
            logger.error("No dimension registry found");
            return;
        }
        referenceList = lookup.listElements().toList();
        if(referenceList.isEmpty()) {
            logger.error("No dimension found");
            return;
        }
        referenceList.forEach(ref -> {
            ResourceLocation location = Objects.requireNonNull(ref.getKey()).location();
            levelsMap.put(location.getNamespace() + "@" + location.getPath(), ref.getKey());
        });
        allLevels = levelsMap.values().stream().toList();
    }

    public static ResourceKey<Level> getDesignatedLevelResKey(String key) {
        return levelsMap.get(key);
    }
    @Nullable
    public static ResourceKey<Level> getLevelResKeyByLevel(Level level) {
        var lookup = CommonHooks.resolveLookup(Registries.DIMENSION);
        AtomicReference<ResourceKey<Level>> levelRes = new AtomicReference<>();
        if(lookup != null) {
            lookup.listElements().forEach(ref -> {
                if(ref.value() == level) {
                    levelRes.set(ref.getKey());
                }
            });
        }
        return levelRes.get() != null ? levelRes.get() : null;
    }

    public static Holder.Reference<Level> getDesignatedLevelRef(String key) {
        return getDesignatedLevelRef(getDesignatedLevelResKey(key));
    }

    public static Holder.Reference<Level> getDesignatedLevelRef(ResourceKey<Level> key) {
        var lookup = CommonHooks.resolveLookup(Registries.DIMENSION);
        if(lookup != null) {
            Optional<Holder.Reference<Level>> levelReference = lookup.get(key);
            return levelReference.orElse(null);
        }
        return null;
    }

    @Nullable
    public static ResourceKey<Level> getRandomLevel(int index) {
        return (index >= levelsMap.size() || index < 0) ? null : allLevels.get(index);
    }
}
