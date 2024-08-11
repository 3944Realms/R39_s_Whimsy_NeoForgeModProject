package com.r3944realms.whimsy.mixin.enchantmentItem;

import com.r3944realms.whimsy.config.ModMiscConfig;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Enchantment.class)
public class MixinEnchantment {
    @Shadow
    @Final
    private Enchantment.EnchantmentDefinition definition;
    /**
     * @author
     * Mafuyu33
     * @reason
     * Always can be enchanted.
     */
    @Overwrite
    public boolean canEnchant(ItemStack stack) {
        if(ModMiscConfig.enableAntyItemEnchant.get()) {
            return true;
        } else {
            return this.definition.supportedItems().contains(stack.getItemHolder());
        }
    }
}
