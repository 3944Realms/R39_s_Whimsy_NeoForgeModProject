package com.r3944realms.whimsy.modInterface.player;

import net.minecraft.world.entity.player.Player;

import java.util.HashMap;

public interface PlayerCapacity {
    void Whimsy$SetSelfNameTagVisible(boolean visible);
    void Whimsy$SetOtherPlayerNameTagHidden(boolean isEnable);
    boolean Whimsy$GetOtherPlayerNameTagHidden();
    boolean Whimsy$GetSelfNameTagVisible();
    void Whimsy$SaveSetting(String Setting,  boolean value);
    void Whimsy$RemoveSetting(String Setting);
    String[] Whimsy$GetAllSettings();
    void Whimsy$CopySetting(Player player);
    HashMap<String, Boolean> Whimsy$GetSettingMap();
}
