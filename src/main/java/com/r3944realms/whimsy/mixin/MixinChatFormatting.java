//package com.r3944realms.whimsy.mixin;
//
//import com.google.common.collect.Lists;
//import com.r3944realms.whimsy.WhimsyMod;
//import net.minecraft.ChatFormatting;
//import net.minecraft.util.StringRepresentable;
//import net.neoforged.api.distmarker.Dist;
//import net.neoforged.bus.api.SubscribeEvent;
//import net.neoforged.fml.common.EventBusSubscriber;
//import net.neoforged.fml.event.lifecycle.FMLLoadCompleteEvent;
//import org.spongepowered.asm.mixin.*;
//import org.spongepowered.asm.mixin.injection.At;
//import org.spongepowered.asm.mixin.injection.Inject;
//import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
//
//import java.lang.reflect.Field;
//import java.lang.reflect.Modifier;
//import java.util.*;
////import java.util.stream.Collectors;
//
//@EventBusSubscriber(modid = WhimsyMod.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
//@Mixin(ChatFormatting.class)
//public class MixinChatFormatting {
//    @Mutable
//    @Final
//    @Shadow
//    private static Map<String, ChatFormatting> FORMATTING_BY_NAME;
//
//    @Unique
//    private static ChatFormatting[] ENUM_VALUES;
//    @Unique
//    @SubscribeEvent
//    private static void Whimsy$OnLoadComplete(FMLLoadCompleteEvent event) {
//        try {
//            ENUM_VALUES = Whimsy$AddEnum(ChatFormatting.class, "NEW_FORMATTING", 'x', 16, 12345678);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//
//    /**
//     * @author 3944Realms
//     * @reason Add New Value
//     */
//    @Overwrite
//    public static ChatFormatting[] values() {
//        return MixinChatFormatting.ENUM_VALUES;
//    }
//
//    @Unique
//    private static ChatFormatting[] Whimsy$AddEnum(Class<ChatFormatting> enumClass, String name, char code, int id, Integer color) throws Exception {
//        Field valuesField = ChatFormatting.class.getDeclaredField("$VALUES");
//        valuesField.setAccessible(true);
//
//        ChatFormatting[] currentValues = (ChatFormatting[]) valuesField.get(null);
//        ChatFormatting[] newValues = Arrays.copyOf(currentValues, currentValues.length + 1);
//
//        ChatFormatting newEnum = Enum.valueOf(enumClass, name);
//        Field nameField = Enum.class.getDeclaredField("name");
//        nameField.setAccessible(true);
//        nameField.set(newEnum, name);
//
//        Field ordinalField = Enum.class.getDeclaredField("ordinal");
//        ordinalField.setAccessible(true);
//        ordinalField.setInt(newEnum, currentValues.length);
//
//        newValues[currentValues.length] = newEnum;
//
//        Whimsy$SetStaticFinalField(enumClass, "$VALUES", newValues);
//        FORMATTING_BY_NAME = Arrays.stream(newValues)
//                .collect(Collectors.toMap(p -> Whimsy$CleanName(p.getName()), p -> p));
//
//        return newValues;
//    }
//
//    @Unique
//    private static void Whimsy$SetStaticFinalField(Class<?> clazz, String fieldName, Object value) throws Exception {
//        Field field = clazz.getDeclaredField(fieldName);
//        field.setAccessible(true);
//
//        Field modifiersField = Field.class.getDeclaredField("modifiers");
//        modifiersField.setAccessible(true);
//        modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
//
//        field.set(null, value);
//    }
//
//    @Unique
//    private static String Whimsy$CleanName(String name) {
//        return name.toLowerCase().replaceAll("[^a-z]", "");
//    }
//}
