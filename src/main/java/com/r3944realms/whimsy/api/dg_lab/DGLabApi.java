package com.r3944realms.whimsy.api.dg_lab;

import com.r3944realms.dg_lab.APILanguageKey;
import com.r3944realms.dg_lab.websocket.utils.enums.ManagerResultEnum;
import com.r3944realms.whimsy.WhimsyMod;
import com.r3944realms.whimsy.config.WebSocketServerConfig;
import com.r3944realms.whimsy.content.items.ModItemsRegister;
import com.r3944realms.whimsy.init.FilePathHelper;
import com.r3944realms.whimsy.network.payload.ackpayload.SyncWebsocketRequestPayload;
import com.r3944realms.whimsy.utils.Notice.NoticePlayer;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.ItemStack;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.network.PacketDistributor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class DGLabApi {
    private static final Logger logger = LoggerFactory.getLogger(DGLabApi.class);
    public final static Supplier<ManagerResultEnum> ClientManagerStart = () -> {
               if(!FMLEnvironment.dist.isClient()) { //非客户端退出
                  logger.error("Distributor is not client");
                  return ManagerResultEnum.MISMATCHED_ENVIRONMENT_FAILURE;
              }
              try {
                  if(Minecraft.getInstance().isSingleplayer()) {//在单人模式下
                      WhimsyMod.DG_LAB.initClientConfig(WebSocketServerConfig.WebSocketServerAddress.get(), WebSocketServerConfig.WebSocketServerPort.get());
                      return ManagerResultEnum.SUCCESSFUL;//因为是单人模式,直接从配置中读取Websocket服务器地址和端口
                  }
                  if (Minecraft.getInstance().player != null) {//非单人模式
                      PacketDistributor.sendToServer(new SyncWebsocketRequestPayload(Minecraft.getInstance().player.getUUID()));//向服务器发送获取同步Websocket服务器地址和端口请求
                      return ManagerResultEnum.WAITING_FOR_SYNCHRONIZATION;
                  }
                  else throw new NullPointerException();
              } catch (NullPointerException e) {
                  logger.error("Null pointer exception, player is null :", e);
                  return ManagerResultEnum.FAILURE_FROM_CATCH;
              }
      };
    public final static Supplier<ManagerResultEnum> ClientManagerStop = () -> {
          if(!FMLEnvironment.dist.isClient()) { //非客户端退出
           logger.error("Distributor is not client");
           return ManagerResultEnum.MISMATCHED_ENVIRONMENT_FAILURE;
      }

      return ManagerResultEnum.SUCCESSFUL;
    };
    public final static Supplier<Void> informSupplier = () -> {
        File file = FilePathHelper.get_HCJ_HTML_Path().toFile().getAbsoluteFile();
                MutableComponent fileComponent = Component.literal(file.getName()).withStyle(ChatFormatting.UNDERLINE);
                fileComponent.withStyle((style) -> {
                            return style.withClickEvent(new ClickEvent(ClickEvent.Action.OPEN_FILE, file.getAbsolutePath()))
                                    .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_ITEM, new HoverEvent.ItemStackInfo(new ItemStack(ModItemsRegister.TEST_ITEM.get()))));
                        }
                );
                Component link_display = Component.translatable(APILanguageKey.PB_LINK_OF_QRCODE, fileComponent);
                NoticePlayer.showMessageToLocalPlayer(Minecraft.getInstance().player, link_display);
        return null;
    };
    public final static Supplier<Void> noticeSupplier = () -> {
        Component bind_suc = Component.translatable(APILanguageKey.PB_BIND_SUCCESSFUL);
        NoticePlayer.showMessageToLocalPlayer(Minecraft.getInstance().player, bind_suc);
        return null;
    };
    public final static Consumer<String> qrCodeProducer = FilePathHelper::ReCreateHCJFile;
}
