package com.r3944realms.whimsy.utils;


import com.r3944realms.whimsy.gamerule.Gamerules;

public class Util {
    public static String getGameruleName(Class<?> clazz) {
        return Gamerules.GAMERULE_PREFIX + clazz.getSimpleName();
    }

}
