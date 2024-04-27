package com.r3944realms.whimsy.datagen;

import com.r3944realms.whimsy.WhimsyMod;
import net.minecraft.data.DataGenerator;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.data.event.GatherDataEvent;

@Mod.EventBusSubscriber(modid = WhimsyMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class dataGenerations {
    @SubscribeEvent
    public static void genData(GatherDataEvent event) {
        DataGenerator dataGenerator = event.getGenerator();
    }
}
