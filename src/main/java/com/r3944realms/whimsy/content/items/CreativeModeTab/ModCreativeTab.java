package com.r3944realms.whimsy.content.items.CreativeModeTab;

import com.r3944realms.whimsy.WhimsyMod;
import com.r3944realms.whimsy.content.items.ModItemsRegister;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class ModCreativeTab {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, WhimsyMod.MOD_ID);
    public static final String WHIMSY_TAB_STRING = "creativetab.whimsicality";
    public static final String TEST = "test_tab";
    public static final Supplier<CreativeModeTab> TEST_TAB = CREATIVE_MODE_TABS.register(TEST,() -> CreativeModeTab.builder()
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .title(Component.translatable(getCreativeMod(TEST)))
            .icon(() -> ModItemsRegister.TEST_ITEM.get().getDefaultInstance())
            .displayItems(((pParameters, pOutput) -> {
                ModItemsRegister.ITEM_SUPPLIER.forEach(item -> pOutput.accept(item.get()));
            })).build());
    public static String getCreativeMod(@NotNull String tabs) {
        return WHIMSY_TAB_STRING + "." + tabs;
    }
    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }

}
