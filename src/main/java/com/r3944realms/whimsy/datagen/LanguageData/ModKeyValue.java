package com.r3944realms.whimsy.datagen.LanguageData;

import com.r3944realms.whimsy.blocks.ModBlocksRegister;
import com.r3944realms.whimsy.command.WebSocketServerCommand;
import com.r3944realms.whimsy.items.CreativeModeTab.ModCreativeTab;
import com.r3944realms.whimsy.items.ModItemsRegister;
import com.r3944realms.whimsy.network.payload.ackpayload.AckPayload;
import com.r3944realms.whimsy.utils.Enum.ModPartEnum;
import com.r3944realms.whimsy.utils.ModAnnotation.NeedCompletedInFuture;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public enum ModKeyValue {
    TEST_ITEM(ModItemsRegister.TEST_ITEM, ModPartEnum.ITEMS,"Test Item", "测试物品", "測試物品", true),
    TEST_BLOCK(ModBlocksRegister.TEST_BLOCK, ModPartEnum.BLOCKS, "Test Block", "测试方块", "測試方塊", false),
    TEST_CREATIVE_TAB(ModCreativeTab.getCreativeMod(ModCreativeTab.TEST), ModPartEnum.CREATIVE_TAB, "Test Creative Tab", "测试创造物品栏", "測試創造物品欄",false ),
    MESSAGE_NET_ACK_FAILED(AckPayload.NET_PAYLOAD_ACK_FAILED_KEY, ModPartEnum.MESSAGE, "ACK FAILED", "确认失败", "確認失敗",false),
    MESSAGE_WEBSOCKET_SERVER__START_SUCCESSFUL(WebSocketServerCommand.START_SUCCESSFUL, ModPartEnum.MESSAGE, "Start WebSocket Server.", "Websocket服务器线程启动成功","Websocket伺服器綫程啓動成功", false),
    MESSAGE_WEBSOCKET_SERVER__START_FAILED_REPEAT(WebSocketServerCommand.START_FAILED_REPEAT_START, ModPartEnum.MESSAGE, "WebSocket Server is already running.", "Websocket服务器线程已运行，切勿重复启动","Websocket伺服器綫程在運行中，切勿重複啓動", false),
    MESSAGE_WEBSOCKET_SERVER__START_FAILED_CLOSING(WebSocketServerCommand.START_FAILED_CLOSING, ModPartEnum.MESSAGE, "WebSocket Server is closing.", "Websocket服务器线程在关闭中，请等完全关闭后再启动","Websocket伺服器綫程在關閉中，請等待關閉之後再開啓", false),
    MESSAGE_WEBSOCKET_SERVER__STOP_SUCCESS(WebSocketServerCommand.STOP_SUCCESSFUL, ModPartEnum.MESSAGE, "Stop WebSocket Server.","Websocket服务器线程关闭中","關閉Websocket伺服器綫程關閉中",false),
    MESSAGE_WEBSOCKET_SERVER__STOP_FAILED(WebSocketServerCommand.STOP_FAILED_HAD_CLOSE, ModPartEnum.MESSAGE, "WebSocket Server had closed","Websocket服务线程已关闭","Websocket伺服器綫程已關閉",false),
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
