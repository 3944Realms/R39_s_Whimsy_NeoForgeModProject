package com.r3944realms.whimsy.datagen.LanguageData;

import com.r3944realms.whimsy.api.APILanguageKey;
import com.r3944realms.whimsy.blocks.ModBlocksRegister;
import com.r3944realms.whimsy.command.PlayerProperty.ChatCommand;
import com.r3944realms.whimsy.command.TestClientCommand;
import com.r3944realms.whimsy.command.Websocket.WebSocketClientCommand;
import com.r3944realms.whimsy.command.Websocket.WebSocketServerCommand;
import com.r3944realms.whimsy.command.miscCommand.MotionCommand;
import com.r3944realms.whimsy.gamerule.ServerChat.DefaultTalkArea;
import com.r3944realms.whimsy.items.CreativeModeTab.ModCreativeTab;
import com.r3944realms.whimsy.items.ModItemsRegister;
import com.r3944realms.whimsy.network.NetworkHandler;
import com.r3944realms.whimsy.utils.Enum.LanguageEnum;
import com.r3944realms.whimsy.utils.Enum.ModPartEnum;
import com.r3944realms.whimsy.utils.ModAnnotation.NeedCompletedInFuture;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public enum ModKeyValue {
    //ITEM
    TEST_ITEM(ModItemsRegister.TEST_ITEM, ModPartEnum.ITEM,"Test Item", "测试物品", "測試物品", true),
    //BLOCK
    TEST_BLOCK(ModBlocksRegister.TEST_BLOCK, ModPartEnum.BLOCK, "Test Block", "测试方块", "測試方塊", false),
    //CREATIVE_TAB
    TEST_CREATIVE_TAB(ModCreativeTab.getCreativeMod(ModCreativeTab.TEST), ModPartEnum.CREATIVE_TAB, "Test Creative Tab", "测试创造物品栏", "測試創造物品欄",false),
    //NETWORK_MESSAGE
    MESSAGE_NET_ACK_FAILED(NetworkHandler.ACK_FAILED, ModPartEnum.MESSAGE, "ACK FAILED", "确认失败", "確認失敗","确认，不成",false),
    MESSAGE_NET_WB_CLIENT_SYNC_FAILED(NetworkHandler.WS_CLIENT_SYNC_FAILED, ModPartEnum.MESSAGE,"Sync the address of Websocket Server Failed", "同步服务器Websocket地址失败", "同步伺服器Websocket地址失敗","同步服务器Websocket地址，不果",false),
    //COMMAND__MESSAGE
    MESSAGE_WEBSOCKET_SERVER__START_SUCCESSFUL(WebSocketServerCommand.SERVER_START_SUCCESSFUL, ModPartEnum.COMMAND, "Start WebSocket Server.", "Websocket服务器线程启动成功","Websocket伺服器綫程啓動成功", "Websocket服务器线程，启之，功成",false),
    MESSAGE_WEBSOCKET_SERVER__START_FAILED_REPEAT(WebSocketServerCommand.SERVER_START_FAILED_REPEAT_START, ModPartEnum.COMMAND, "WebSocket Server is already running.", "Websocket服务器线程已运行，切勿重复启动","Websocket伺服器綫程在運行中，切勿重複啓動", "Websocket客端线程，已行矣，勿复再启",false),
    MESSAGE_WEBSOCKET_SERVER__START_FAILED_CLOSING(WebSocketServerCommand.SERVER_START_FAILED_CLOSING, ModPartEnum.COMMAND, "WebSocket Server is closing.", "Websocket服务器线程在关闭中，请等完全关闭后再启动","Websocket伺服器綫程在關閉中，請等待關閉之後再開啓", "Websocket客端线程，其闭也，正当中途",false),
    MESSAGE_WEBSOCKET_SERVER__STOP_SUCCESS(WebSocketServerCommand.SERVER_STOP_SUCCESSFUL, ModPartEnum.COMMAND, "Stop WebSocket Server.","Websocket服务器线程关闭中","Websocket伺服器綫程關閉中", "Websocket服务器线程，其闭也，正当中途",false),
    MESSAGE_WEBSOCKET_SERVER__STOP_FAILED(WebSocketServerCommand.SERVER_STOP_FAILED_HAD_CLOSED, ModPartEnum.COMMAND, "WebSocket Server had closed","Websocket服务线程已关闭","Websocket伺服器綫程已關閉", "Websocket服务线程，已阖矣",false),

    MESSAGE_WEBSOCKET_NOT_CLIENT(WebSocketClientCommand.NOT_CLIENT, ModPartEnum.COMMAND, "This command is Only can be running in client environment","该命令只可以在客户端上运行","該命令僅可在客戶端上運行","此令，唯可在客端行也",false),
    MESSAGE_WEBSOCKET_SYNC_ACK_SEND(WebSocketClientCommand.CLIENT_SYNC_ACK_SEND, ModPartEnum.COMMAND, "SYNC Request is send successful","同步请求已发送","同步請求已發送","同步之请，已发矣",false),
    MESSAGE_WEBSOCKET_CLIENT__START_SUCCESSFUL(WebSocketClientCommand.CLIENT_START_SUCCESSFUL, ModPartEnum.COMMAND, "Start WebSocket Client.", "Websocket客户端线程启动成功","Websocket客戶端綫程啓動成功", "Websocket客端线程，启之，功成", false),
    MESSAGE_WEBSOCKET_CLIENT__START_FAILED_REPEAT(WebSocketClientCommand.CLIENT_START_FAILED_REPEAT_START, ModPartEnum.COMMAND, "WebSocket Client is already running.", "Websocket客户端线程已运行，切勿重复启动","Websocket客戶端綫程在運行中，切勿重複啓動","Websocket客端线程，已行矣，勿复再启", false),
    MESSAGE_WEBSOCKET_CLIENT__START_FAILED_CLOSING(WebSocketClientCommand.CLIENT_START_FAILED_CLOSING, ModPartEnum.COMMAND, "WebSocket Client is closing.", "Websocket客户端线程在关闭中，请等完全关闭后再启动","Websocket客戶端綫程在關閉中，請等待關閉之後再開啓", "Websocket客端线程，方闭之际，宜俟其全而后再启",false),
    MESSAGE_WEBSOCKET_CLIENT__START_FAILED_NOT_SYNC(WebSocketClientCommand.CLIENT_START_FAILED_NOT_SYNC, ModPartEnum.COMMAND, "Not sync WebSocket Server address,please use \"sync\" command first", "未同步,请使用sync指令同步后再启动","未同步,請使用sync指令同步后再啓動","未同步，宜用sync之令，同步而后启",false),
    MESSAGE_WEBSOCKET_CLIENT__STOP_SUCCESS(WebSocketClientCommand.CLIENT_STOP_SUCCESSFUL, ModPartEnum.COMMAND, "Stop WebSocket Client.","Websocket客户端线程关闭中","關閉Websocket客戶端綫程關閉中","Websocket客端线程，其闭也，正当中途",false),
    MESSAGE_WEBSOCKET_CLIENT__STOP_FAILED(WebSocketClientCommand.CLIENT_STOP_FAILED_HAD_CLOSED, ModPartEnum.COMMAND, "WebSocket Client had closed","Websocket客户端线程已关闭","Websocket客戶端綫程已關閉","Websocket客端线程，已阖矣",false),

    MESSAGE_CHAT_TALKAREA_SET(ChatCommand.TALK_AREA_SET, ModPartEnum.COMMAND,"§aTalk area set to §e%d§a blocks!§r", "§a聊天区域可见消息半径设置为 §e%d §a格!§r", "§a聊天區域可見半徑為 §e%d §a格!§r", "§6今聊城可知半径置§e%d §6格§r",false),
    MESSAGE_CHAT_TALKAREA_PREFERENCE_SET(ChatCommand.TALK_AREA_PREFERENCE_SET, ModPartEnum.COMMAND, "§aTalk area preference set to §e%d§a blocks!§r","§a默认聊天区域可见消息半径设置为 §e%d §a格!§r","§a默認聊天區域可見訊息半徑設置為 §e%d §a格!§r", "§6默置§e %d§6格§r", false),
    MESSAGE_CHAT_TALKAREA_UNLIMITED(ChatCommand.TALK_AREA_UNLIMITED, ModPartEnum.COMMAND, "§aTalk area unlimited!§r", "§a聊天区域半径无限制§r", "§a聊天區域半徑無限制§r","§a语矢无限§r" ,false),
    MESSAGE_CHAT_TALKAREA_PREFERENCE_NOT_SET(ChatCommand.TALK_AREA_PREFERENCE_NOT_SET, ModPartEnum.COMMAND, "Your talk area preference is not set!", "未设置默认可见消息聊天区域半径", "未設置可見訊息聊天半徑", "未置默视之",false),
    MESSAGE_CHAT_TALKAREA_CURRENT_CONFIG(ChatCommand.TALK_AREA_CURRENT_CONFIG, ModPartEnum.COMMAND, "§6TalkArea Current§7:§e %d §6blocks §f(§aPrefence§7:§e %d §ablocks§f)§r", "§6目前聊天区域可见半径设置§7:§e %d §6格 §f(§a默认值§7:§e %d §a格§f)§r", "§6目前可見訊息聊天半徑設置§7:§e%d §6格 §f(§a默認值§7:§e%d §a格§f)§r", "§6今聊城可知半径置§e %d§6格§f(§a默§7：§e%d格§f)§r",false),

    MESSAGE_MOTION_SETTER_SUCCESSFUL(MotionCommand.MOTION_SETTER_SUCCESSFUL,ModPartEnum.COMMAND,"§bSet Successfully.§a%s§7:§f[§eCurrent Vec§7: §a(§f%f§7, §f%f§7, §f%f§a)§f]§r","§b设置成功.§a%s§7:§f[§e目前速度§7: §a(§f %f §7, §f %f §7, §f %f §a)§f]§r","§b設置成功.§a%s§7:§f[§e目前速度§7: §a(§f%f§7, §f%f§7, §f%f§a)§f]§r",false),
    MESSAGE_MOTION_ADDER_SUCCESSFUL(MotionCommand.MOTION_ADDER_SUCCESSFUL,ModPartEnum.COMMAND, "§bAdd Successfully.§a%s§7:§f[§eCurrent Vec§7: §a(§f%f§7, §f%f§7, §f%f§a)§f]§r","§b添加成功.§a%s§7:§f[§e目前速度§7: §a(§f %f §7, §f %f §7, §f %f §a)§f]§r","§b添加成功.§a%s§7:§f[§e目前速度§7: §a(§f%f§7, §f%f§7, §f%f§a)§f]§r", false),
    //INFO_MESSAGE
    CHAT_NONE_HEARD_YOU(DefaultTalkArea.CHAT_NONE_HEARD_YOU, ModPartEnum.MESSAGE, "Nobody heard your message", "沒有人接收到你的消息", "無人接收到你的訊息", "无人受子问",false),
    //TEST MESSAGE
    TEST_OPEN_FILE_LINK(TestClientCommand.TEST_KEY_OPEN_FILE_LINK, ModPartEnum.COMMAND, ">> %s",">> %s",">> %s",false),
    //API WEBSOCKET APPLICATION
    PB_LINK_SHOW(APILanguageKey.PB_LINK_OF_QRCODE, ModPartEnum.MESSAGE, "Here is Bind Qrcode Link for DG_LAB PowerBox -> %s","这里是主机绑定二维码链接 -> %s","這裏是主機綁定二維碼鏈接 -> %s",false),
    PB_BIND_SUC(APILanguageKey.PB_BIND_SUCCESSFUL, ModPartEnum.MESSAGE, "bind successful", "绑定成功" , "綁定成功", false)
    ;

    private final Supplier<?> supplier;
    private String key;
    private final String US_EN;
    private final String SIM_CN;
    private final String TRA_CN;
    private final String LZH;
    private final Boolean Default;
    private final ModPartEnum MPE;
    ModKeyValue(Supplier<?> Supplier, ModPartEnum MPE, String US_EN, String SIM_CN, String TRA_CN, String LZH, Boolean isDefault) {
        this.supplier = Supplier;
        this.MPE = MPE;
        this.US_EN = US_EN;
        this.SIM_CN = SIM_CN;
        this.TRA_CN = TRA_CN;
        this.LZH = LZH;
        this.Default = isDefault;
    }
    ModKeyValue(@NotNull String ResourceKey, ModPartEnum MPE, String US_EN, String SIM_CN, String TRA_CN, String LZH, Boolean isDefault) {
        this.supplier = null;
        this.key = ResourceKey;
        this.MPE = MPE;
        this.US_EN = US_EN;
        this.SIM_CN = SIM_CN;
        this.TRA_CN = TRA_CN;
        this.LZH = LZH;
        this.Default = isDefault;
    }
    ModKeyValue(Supplier<?> Supplier, ModPartEnum MPE, String US_EN, String SIM_CN, String TRA_CN, Boolean isDefault) {
        this(Supplier, MPE, US_EN, SIM_CN, TRA_CN, null, isDefault);
    }
    ModKeyValue(@NotNull String ResourceKey, ModPartEnum MPE, String US_EN, String SIM_CN, String TRA_CN, Boolean isDefault) {
        this(ResourceKey, MPE, US_EN, SIM_CN, TRA_CN, null, isDefault);
    }
    public static String getLan(LanguageEnum lan, ModKeyValue key) {
        if (lan == null || lan == LanguageEnum.English) return getEnglish(key);
        else {
          switch (lan) {
              case SimpleChinese -> {
                  return getSimpleChinese(key);
              }
              case TraditionalChinese -> {
                  return getTraditionalChinese(key);
              }
              case LiteraryChinese -> {
                  return getLiteraryChinese(key);
              }
              default -> {
                  return getEnglish(key);
              }
          }
        }
    }
    private static String getEnglish(ModKeyValue key) {
        return key.US_EN;
    }
    private static String getSimpleChinese(ModKeyValue key) {
        return key.SIM_CN;
    }
    private static String getTraditionalChinese(ModKeyValue key) {
        return key.TRA_CN;
    }
    @Nullable public static String getLiteraryChinese(ModKeyValue key) {
        return key.LZH;
    }
    @NeedCompletedInFuture
    public String getKey() {
        if(key == null){
            switch (MPE) {//Don't need to use "break;"[Java feature];
                case CREATIVE_TAB, MESSAGE, INFO, DEFAULT, COMMAND, CONFIG -> throw new UnsupportedOperationException("The Key value is NULL! Please use the correct constructor and write the parameters correctly");
                case ITEM -> key = (getItem()).getDescriptionId();
                case BLOCK -> key =(getBlock()).getDescriptionId();

            }
            //需要完善
        }
        return key;
    }
    @SuppressWarnings("null")
    public Item getItem() {
        return (Item)supplier.get();
    }
    @SuppressWarnings("null")
    public Block getBlock() {
        return (Block)supplier.get();
    }
    public boolean isDefaultItem(){
        return MPE == ModPartEnum.ITEM && Default;
    }
    public boolean isDefaultBlock() {
        return MPE == ModPartEnum.BLOCK && Default;
    }
}
