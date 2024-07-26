package com.r3944realms.whimsy.gamerule;

import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;

import java.util.HashMap;
import java.util.Map;

public enum GameruleRegistry {
    INSTANCE;
    public static final Map<String, GameRules.Key<?>> gamerules = new HashMap<>();;
    public static final Map<String, RuleDataType> gameruleDataTypes = new HashMap<>();
    public enum RuleDataType {
        BOOLEAN,
        INTEGER,
    }

    @SuppressWarnings("unchecked")
    public static boolean isGameruleEnable(Level level, String gameruleName) {
        if(level.isClientSide && Gamerules.gameruleIntegerValuesClient.containsKey(gameruleName)) {
            return Gamerules.gameruleIntegerValuesClient.get(gameruleName) != 0;
        }
        if(gameruleDataTypes.get(gameruleName) != RuleDataType.BOOLEAN) {
            return false;
        }
        return level.getGameRules().getBoolean((GameRules.Key<GameRules.BooleanValue>) gamerules.get(gameruleName));
    }
    @SuppressWarnings("unchecked")
    public static Integer getGameruleIntValue(Level level, String gameruleName) {
        if (level.isClientSide && Gamerules.gameruleIntegerValuesClient.containsKey(gameruleName)) {
            return Gamerules.gameruleIntegerValuesClient.get(gameruleName);
        }
        if (gameruleDataTypes.get(gameruleName) != RuleDataType.INTEGER) {
            return 0;
        }
        return level.getGameRules().getInt((GameRules.Key<GameRules.IntegerValue>)gamerules.get(gameruleName));
    }
    public void registerGamerule(String gameruleName, GameRules.Category category, boolean value) {
        gamerules.put(gameruleName, GameRules.register(gameruleName, category, GameRules.BooleanValue.create(value)));
        gameruleDataTypes.put(gameruleName, RuleDataType.BOOLEAN);
    }
    public void registerGamerule(String gameruleName, GameRules.Category category, int value) {
        gamerules.put(gameruleName, GameRules.register(gameruleName, category, GameRules.IntegerValue.create(value)));
        gameruleDataTypes.put(gameruleName, RuleDataType.INTEGER);
    }
}
