package com.r3944realms.whimsy.datagen.LanguageData;

import com.r3944realms.whimsy.blocks.ModBlocksRegister;
import com.r3944realms.whimsy.command.WebSocketClientCommand;
import com.r3944realms.whimsy.command.WebSocketServerCommand;
import com.r3944realms.whimsy.items.CreativeModeTab.ModCreativeTab;
import com.r3944realms.whimsy.items.ModItemsRegister;
import com.r3944realms.whimsy.network.NetworkHandler;
import com.r3944realms.whimsy.utils.Enum.ModPartEnum;
import com.r3944realms.whimsy.utils.ModAnnotation.NeedCompletedInFuture;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public enum ModKeyValue {
    //ITEM
    TEST_ITEM(ModItemsRegister.TEST_ITEM, ModPartEnum.ITEMS,"Test Item", "测试物品", "測試物品", true),
    //BLOCK
    TEST_BLOCK(ModBlocksRegister.TEST_BLOCK, ModPartEnum.BLOCKS, "Test Block", "测试方块", "測試方塊", false),
    //CREATIVE_TAB
    TEST_CREATIVE_TAB(ModCreativeTab.getCreativeMod(ModCreativeTab.TEST), ModPartEnum.CREATIVE_TAB, "Test Creative Tab", "测试创造物品栏", "測試創造物品欄",false ),
    //NETWORK_MESSAGE
    MESSAGE_NET_ACK_FAILED(NetworkHandler.ACK_FAILED, ModPartEnum.MESSAGE, "ACK FAILED", "确认失败", "確認失敗",false),
    MESSAGE_NET_WB_CLIENT_SYNC_FAILED(NetworkHandler.WS_CLIENT_SYNC_FAILED, ModPartEnum.MESSAGE,"Sync the address of Websocket Server Failed", "同步服务器Websocket地址失败", "同步伺服器Websocket地址失敗",false),
    //PLAIN_MESSAGE
    MESSAGE_WEBSOCKET_SERVER__START_SUCCESSFUL(WebSocketServerCommand.SERVER_START_SUCCESSFUL, ModPartEnum.MESSAGE, "Start WebSocket Server.", "Websocket服务器线程启动成功","Websocket伺服器綫程啓動成功", false),
    MESSAGE_WEBSOCKET_SERVER__START_FAILED_REPEAT(WebSocketServerCommand.SERVER_START_FAILED_REPEAT_START, ModPartEnum.MESSAGE, "WebSocket Server is already running.", "Websocket服务器线程已运行，切勿重复启动","Websocket伺服器綫程在運行中，切勿重複啓動", false),
    MESSAGE_WEBSOCKET_SERVER__START_FAILED_CLOSING(WebSocketServerCommand.SERVER_START_FAILED_CLOSING, ModPartEnum.MESSAGE, "WebSocket Server is closing.", "Websocket服务器线程在关闭中，请等完全关闭后再启动","Websocket伺服器綫程在關閉中，請等待關閉之後再開啓", false),
    MESSAGE_WEBSOCKET_SERVER__STOP_SUCCESS(WebSocketServerCommand.SERVER_STOP_SUCCESSFUL, ModPartEnum.MESSAGE, "Stop WebSocket Server.","Websocket服务器线程关闭中","關閉Websocket伺服器綫程關閉中",false),
    MESSAGE_WEBSOCKET_SERVER__STOP_FAILED(WebSocketServerCommand.SERVER_STOP_FAILED_HAD_CLOSED, ModPartEnum.MESSAGE, "WebSocket Server had closed","Websocket服务线程已关闭","Websocket伺服器綫程已關閉",false),

    MESSAGE_WEBSOCKET_NOT_CLIENT(WebSocketClientCommand.NOT_CLIENT, ModPartEnum.MESSAGE, "This command is Only can be running in client environment","该命令只可以在客户端上运行","該命令僅可在客戶端上運行",false),
    MESSAGE_WEBSOCKET_CLIENT__START_SUCCESSFUL(WebSocketClientCommand.CLIENT_START_SUCCESSFUL, ModPartEnum.MESSAGE, "Start WebSocket Client.", "Websocket客户端线程启动成功","Websocket客戶端綫程啓動成功", false),
    MESSAGE_WEBSOCKET_CLIENT__START_FAILED_REPEAT(WebSocketClientCommand.CLIENT_START_FAILED_REPEAT_START, ModPartEnum.MESSAGE, "WebSocket Client is already running.", "Websocket客户端线程已运行，切勿重复启动","Websocket客戶端綫程在運行中，切勿重複啓動", false),
    MESSAGE_WEBSOCKET_CLIENT__START_FAILED_CLOSING(WebSocketClientCommand.CLIENT_START_FAILED_CLOSING, ModPartEnum.MESSAGE, "WebSocket Client is closing.", "Websocket客户端线程在关闭中，请等完全关闭后再启动","Websocket客戶端綫程在關閉中，請等待關閉之後再開啓", false),
    MESSAGE_WEBSOCKET_CLIENT__START_FAILED_NOT_SYNC(WebSocketClientCommand.CLIENT_START_FAILED_NOT_SYNC, ModPartEnum.MESSAGE, "Not sync WebSocket Server address,please use \"sync\" command first", "未同步,请使用sync指令同步后再启动","未同步,請使用sync指令同步后再啓動",false),
    MESSAGE_WEBSOCKET_CLIENT__STOP_SUCCESS(WebSocketClientCommand.CLIENT_STOP_SUCCESSFUL, ModPartEnum.MESSAGE, "Stop WebSocket Client.","Websocket客户端线程关闭中","關閉Websocket客戶端綫程關閉中",false),
    MESSAGE_WEBSOCKET_CLIENT__STOP_FAILED(WebSocketClientCommand.CLIENT_STOP_FAILED_HAD_CLOSED, ModPartEnum.MESSAGE, "WebSocket Client had closed","Websocket客户端线程已关闭","Websocket客戶端綫程已關閉",false),

    ;

    private final Supplier<?> supplier;
    private String key;
    private final String US_EN;
    private final String SIM_CN;
    private final String TRA_CN;
    private final Boolean Default;
    private final ModPartEnum MPE;

    ModKeyValue(Supplier<?> Supplier, ModPartEnum MPE, String US_EN, String SIM_CN, String TRA_CN, Boolean isDefault) {
        this.supplier = Supplier;
        this.MPE = MPE;
        this.US_EN = US_EN;
        this.SIM_CN = SIM_CN;
        this.TRA_CN = TRA_CN;
        this.Default = isDefault;
    }
    ModKeyValue(@NotNull String ResourceKey, ModPartEnum MPE, String US_EN, String SIM_CN, String TRA_CN, Boolean isDefault) {
        this.supplier = null;
        this.key = ResourceKey;
        this.MPE = MPE;
        this.US_EN = US_EN;
        this.SIM_CN = SIM_CN;
        this.TRA_CN = TRA_CN;
        this.Default = isDefault;
    }
    public static String getEnglish(ModKeyValue key) {
        return key.US_EN;
    }
    public static String getSimpleChinese(ModKeyValue key) {
        return key.SIM_CN;
    }
    public static String getTraditionalChinese(ModKeyValue key) {
        return key.TRA_CN;
    }
    @NeedCompletedInFuture
    public String getKey() {
        if(key == null){
            switch (MPE) {//Don't need to use "break;";
                case CREATIVE_TAB -> throw new UnsupportedOperationException("The Key value is NULL! Please use the correct constructor and write the parameters correctly");
                case ITEMS -> key = (getItem()).getDescriptionId();
                case BLOCKS -> key =(getBlock()).getDescriptionId();
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
        return MPE == ModPartEnum.ITEMS && Default;
    }
    public boolean isDefaultBlock() {
        return MPE == ModPartEnum.BLOCKS && Default;
    }
}
