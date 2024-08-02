package com.r3944realms.whimsy.items.enchantment.effect;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.r3944realms.whimsy.WhimsyMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public record EnchantmentDeathEyesEffect (float sacrifice)implements EnchantmentEntityEffect {
    public static final MapCodec<EnchantmentDeathEyesEffect> CODEC = RecordCodecBuilder.mapCodec(
            (instance) -> instance.group(
                    Codec.FLOAT.fieldOf("sacrifice").forGetter(EnchantmentDeathEyesEffect::sacrifice)
            ).apply(instance, EnchantmentDeathEyesEffect::new));
    @Override
    public void apply(@NotNull ServerLevel pLevel, int pEnchantmentLevel, @NotNull EnchantedItemInUse pItem, @NotNull Entity pEntity, @NotNull Vec3 pOrigin) {
        if(pEntity instanceof LivingEntity livingEntity) {
            Objects.requireNonNull(livingEntity.getAttribute(Attributes.MAX_HEALTH)).addPermanentModifier(
                    new AttributeModifier(ResourceLocation.fromNamespaceAndPath(WhimsyMod.MOD_ID, "sacrifice"), - sacrifice * 5, AttributeModifier.Operation.ADD_VALUE)
            );
        }
    }

    @Override
    public @NotNull MapCodec<? extends EnchantmentEntityEffect> codec() {
        return CODEC;
    }
}
