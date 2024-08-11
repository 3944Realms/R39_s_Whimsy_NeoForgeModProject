package com.r3944realms.whimsy.mixin.registry;


import com.r3944realms.whimsy.datagen.provider.attributes.ModEnchantments;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
//原版的
@Mixin(Enchantments.class)
public class MixinEnchantments {
    //仅runData注入，为了让进度正确能拿到模组的附魔
    @Inject(method = {"bootstrap"}, at = @At("TAIL"))
    private static void bootstrap(BootstrapContext<Enchantment> pContext, CallbackInfo ci) {
        ModEnchantments.EnchantmentBootstrap(pContext);
    }
}
