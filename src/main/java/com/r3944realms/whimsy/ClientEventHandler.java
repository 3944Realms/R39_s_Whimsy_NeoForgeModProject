package com.r3944realms.whimsy;

import com.mojang.brigadier.CommandDispatcher;

import com.r3944realms.dg_lab.manager.WebsocketClientManager;
import com.r3944realms.whimsy.api.dg_lab.DG_Lab;
import com.r3944realms.whimsy.client.mdoel.CustomTextureBakedModel;
import com.r3944realms.whimsy.client.mdoel.DynamicTextureItemBakedModel;
import com.r3944realms.whimsy.client.renderer.DynamicTextureItemRenderer;
import com.r3944realms.whimsy.client.renderer.texture.DynamicTextureManager;
import com.r3944realms.whimsy.client.tooltip.TestItemClientTooltipComponent;
import com.r3944realms.whimsy.config.WebSocketClientConfig;
import com.r3944realms.whimsy.content.commands.TestClientCommand;
import com.r3944realms.whimsy.content.commands.Websocket.WebSocketClientCommand;
import com.r3944realms.whimsy.content.items.ModItemsRegister;
import com.r3944realms.whimsy.content.items.custom.DynamicTextureItem;
import com.r3944realms.whimsy.content.items.tooltip.TestItemTooltip;
import com.r3944realms.whimsy.init.FilePathHelper;
import com.r3944realms.whimsy.modInterface.IMinecraftExtension;
import com.r3944realms.whimsy.utils.logger.logger;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.*;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Optional;


public class ClientEventHandler {
    @EventBusSubscriber(modid = WhimsyMod.MOD_ID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.GAME)
    public static class ClientGameBusEventHandler extends ClientEventHandler {
        /**
         *CommandRegister In CLIENT SIDE
         */
        @SubscribeEvent
        static void onClientCommandsRegister(RegisterClientCommandsEvent event) {
            CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();
            WebSocketClientCommand.register(dispatcher);
            TestClientCommand.register(dispatcher);
        }
        @SubscribeEvent
        static void onLoggingOn(ClientPlayerNetworkEvent.LoggingIn event) {
            if(WebSocketClientConfig.WebSocketClientAutoManager.get()) {
                WebsocketClientManager.init(
                        DG_Lab.ClientManagerStart,
                        DG_Lab.ClientManagerStop,
                        DG_Lab.informSupplier,
                        DG_Lab.noticeSupplier,
                        DG_Lab.qrCodeProducer
                );
                WebsocketClientManager.getManager().StartClient();
            }
        }
        @SubscribeEvent
        static void onLoggingOut(ClientPlayerNetworkEvent.LoggingOut event){
            if(WebSocketClientConfig.WebSocketClientAutoManager.get()){
                WebsocketClientManager.getManager().StopClient();
            }
        }
        @SubscribeEvent
        static void onClientTick(ClientTickEvent.Pre event) {
            Minecraft mc = Minecraft.getInstance();
            DynamicTextureManager dynamicTextureManager = ((IMinecraftExtension) mc).getDynamicTextureManager();
            if(mc.level == null || mc.player == null) return;
            for(ItemStack stack : mc.player.getInventory().items) {
                Item item = stack.getItem();
                if(item instanceof DynamicTextureItem) {
                    Optional<ResourceLocation> textureLocation = dynamicTextureManager.getTextureLocationFromTextureTag(stack);
                    textureLocation.ifPresent(texture -> {

                    });
                }
            }
        }
    }
    @EventBusSubscriber(modid = WhimsyMod.MOD_ID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
    public static class ClientModBusEventHandler extends ClientEventHandler {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            FilePathHelper.HCJFileCreator();
            try {
                Class.forName("io.netty.handler.codec.http.HttpHeaders");
                logger.info("HttpHeaders class is available");
            } catch (ClassNotFoundException e) {
                logger.info("HttpHeaders class is not available");
                logger.info(e.getMessage());
            }
        }

        @SubscribeEvent
        public static void RegisterClientTooltipComponents(RegisterClientTooltipComponentFactoriesEvent event) {
            // 注册文本提示
            event.register(TestItemTooltip.class, TestItemClientTooltipComponent::new);
        }

        @SubscribeEvent
        public static void onRegisterClientExtension(RegisterClientExtensionsEvent event) {
            event.registerItem(new IClientItemExtensions() {
                @Override
                public @NotNull BlockEntityWithoutLevelRenderer getCustomRenderer() {
                    return new DynamicTextureItemRenderer();
                }
            },ModItemsRegister.DYNAMIC_TEXTURE_ITEM.get());
        }

        @SubscribeEvent
        public static void onModelBakeRegistry(ModelEvent.ModifyBakingResult event) {
            IMinecraftExtension minecraftExtension = (IMinecraftExtension) Minecraft.getInstance();
            DynamicTextureManager dynamicTextureManager = minecraftExtension.getDynamicTextureManager();
            Map<String, ResourceLocation> stringCompletableFutureMap = dynamicTextureManager.immutableMap();
            for (String textureUrl : stringCompletableFutureMap.keySet()) {
                ModelResourceLocation modelResourceLocation = new ModelResourceLocation(BuiltInRegistries.ITEM.getKey(ModItemsRegister.DYNAMIC_TEXTURE_ITEM.get()),"inventory");
                Map<ModelResourceLocation, BakedModel> models = event.getModels();
                BakedModel originalModel = models.get(modelResourceLocation);
                ResourceLocation customTexture = stringCompletableFutureMap.get(textureUrl);
                if (customTexture != null){
                    CustomTextureBakedModel customModel = new CustomTextureBakedModel(originalModel, customTexture);
                    models.put(modelResourceLocation, customModel);
                }
            }
        }
        @SubscribeEvent
        public static void onModelBaked(ModelEvent.ModifyBakingResult event) {
            Map<ModelResourceLocation, BakedModel> modelResourceLocationBakedModelMap = event.getModels();
            ModelResourceLocation location = new ModelResourceLocation(BuiltInRegistries.ITEM.getKey(ModItemsRegister.DYNAMIC_TEXTURE_ITEM.get()),"inventory");
            BakedModel existingModel = modelResourceLocationBakedModelMap.get(location);
            if (existingModel == null) {
                throw new RuntimeException("Did not find Obsidian Hidden in registry");
            } else if (existingModel instanceof DynamicTextureItemBakedModel) {
                throw new RuntimeException("Tried to replaceObsidian Hidden twice");
            } else {
                DynamicTextureItemBakedModel dynamicTextureItemBakedModel = new DynamicTextureItemBakedModel(existingModel);
                event.getModels().put(location, dynamicTextureItemBakedModel);
            }
        }
    }






}
