package com.r3944realms.whimsy.utils;


import com.r3944realms.whimsy.content.gamerules.Gamerules;

public class Util {
    public static String getGameruleName(Class<?> clazz) {
        return Gamerules.GAMERULE_PREFIX + clazz.getSimpleName();
    }

}
