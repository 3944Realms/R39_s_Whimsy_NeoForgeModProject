package com.r3944realms.whimsy.datagen.provider.enchantment;

import com.r3944realms.whimsy.WhimsyMod;
import com.r3944realms.whimsy.items.enchantment.ModEnchantmentEffectComponents;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.item.enchantment.effects.AddValue;
import net.minecraft.world.item.enchantment.effects.EnchantmentAttributeEffect;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;


public class ModEnchantments {

    public static final ResourceKey<Enchantment> DEATH_EYES = Key("death_eyes");
    public static final ResourceKey<Enchantment> CHANGE_ITEM = Key("change_item");

    public static void bootstrap(BootstrapContext<Enchantment> pContext) {
        EnchantmentBootstrap(pContext);
    }

    public static void EnchantmentBootstrap(BootstrapContext<Enchantment> pContext) {
        HolderGetter<DamageType> damageTypeHolderGetter = pContext.lookup(Registries.DAMAGE_TYPE);
        HolderGetter<Enchantment> enchantmentHolderGetter = pContext.lookup(Registries.ENCHANTMENT);
        HolderGetter<Item> itemHolderGetter = pContext.lookup(Registries.ITEM);
        HolderGetter<Block> blockHolderGetter = pContext.lookup(Registries.BLOCK);
        ModEnchantments.register(
                pContext,
                ModEnchantments.DEATH_EYES,
                Enchantment.enchantment(
                        Enchantment.definition(
                                itemHolderGetter.getOrThrow(ItemTags.AXES),
                                2,
                                1,
                                Enchantment.constantCost(25),
                                Enchantment.constantCost(20),
                                8, EquipmentSlotGroup.MAINHAND
                        )
                ).withEffect(
                        EnchantmentEffectComponents.ATTRIBUTES,
                        new EnchantmentAttributeEffect(
                                ResourceLocation.fromNamespaceAndPath(WhimsyMod.MOD_ID, "death_eyes"),
                                Attributes.MAX_HEALTH,
                                new LevelBasedValue.LevelsSquared(-11.0F),
                                AttributeModifier.Operation.ADD_VALUE
                        )
                ).withEffect(
                        ModEnchantmentEffectComponents.DEATH_EYES.get(),
                        new AddValue(LevelBasedValue.constant(1))
                )
        );
        ModEnchantments.register(
                pContext,
                ModEnchantments.CHANGE_ITEM,
                Enchantment.enchantment(
                        Enchantment.definition(
                                itemHolderGetter.getOrThrow(Tags.Items.ENCHANTABLES),
                                2,
                                1,
                                Enchantment.constantCost(25),
                                Enchantment.constantCost(20),
                                8, EquipmentSlotGroup.MAINHAND
                        )
                ).withEffect(
                        ModEnchantmentEffectComponents.CHANGE_ITEM.get(),
                        new AddValue(LevelBasedValue.constant(1))
                )
        );
    }

    public static void register(BootstrapContext<Enchantment> context, ResourceKey<Enchantment> key, Enchantment.Builder builder) {
        context.register(key, builder.build(key.location()));
    }
    public static ResourceKey<Enchantment> Key(String name) {
        return ResourceKey.create(Registries.ENCHANTMENT, ResourceLocation.fromNamespaceAndPath(WhimsyMod.MOD_ID, name));
    }
    public static String getEnchantmentKey(String enchantmentName) {

        return "enchantment." + WhimsyMod.MOD_ID + "." + enchantmentName;
    }

}
