package com.r3944realms.whimsy.datagen.LanguageAndOtherData;

import com.r3944realms.dg_lab.APILanguageKey;
import com.r3944realms.whimsy.content.blocks.ModBlocksRegister;
import com.r3944realms.whimsy.content.commands.MiscCommand.LeashCommand;
import com.r3944realms.whimsy.content.commands.MiscCommand.MotionCommand;
import com.r3944realms.whimsy.content.commands.PlayerProperty.ChatCommand;
import com.r3944realms.whimsy.content.commands.PlayerProperty.NameTagCommand;
import com.r3944realms.whimsy.content.commands.TestClientCommand;
import com.r3944realms.whimsy.content.commands.Websocket.WebSocketClientCommand;
import com.r3944realms.whimsy.content.commands.Websocket.WebSocketServerCommand;
import com.r3944realms.whimsy.content.effects.ModEffectRegister;
import com.r3944realms.whimsy.content.effects.ModPotionRegister;
import com.r3944realms.whimsy.content.gamerules.ClientRender.MustOthersHiddenNameTag;
import com.r3944realms.whimsy.content.gamerules.ServerChat.DefaultTalkArea;
import com.r3944realms.whimsy.content.items.CreativeModeTab.ModCreativeTab;
import com.r3944realms.whimsy.content.items.ModItemsRegister;
import com.r3944realms.whimsy.content.sounds.ModSoundRegister;
import com.r3944realms.whimsy.datagen.provider.attributes.ModEnchantments;
import com.r3944realms.whimsy.datagen.provider.attributes.ModPaintingVariants;
import com.r3944realms.whimsy.network.NetworkHandler;
import com.r3944realms.whimsy.utils.Enum.LanguageEnum;
import com.r3944realms.whimsy.utils.Enum.ModPartEnum;
import com.r3944realms.whimsy.utils.ModAnnotation.NeedCompletedInFuture;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public enum ModLangKeyValue {
    //ITEM
    TEST_ITEM(ModItemsRegister.TEST_ITEM, ModPartEnum.ITEM,"Test Item", "测试物品", "測試物品", true),
    DYNAMIC_ITEM(ModItemsRegister.DYNAMIC_TEXTURE_ITEM, ModPartEnum.ITEM, "Dynamic Texture Item", "动态材质物品", "動態材質物品", false),
    TEST_TEXTURE_ITEM(ModItemsRegister.TEST_TEXTURE_ITEM, ModPartEnum.ITEM, "Test Texture Item", "测试材质物品", "測試材質物品", false),
    FABRIC(ModItemsRegister.FABRIC, ModPartEnum.ITEM, "§lFabric", "§l帆布", "§l帆布", true),
    DISC_SANDS_OF_TIME(ModItemsRegister.MUSIC_DISC_SANDS_OF_TIME, ModPartEnum.ITEM, "Mcc Game Music Disc", "Mcc小游戏音乐唱片", "Mcc小游戲音樂唱片", true),
    DISC_HUB_MUSIC(ModItemsRegister.MUSIC_DISC_HUB_MUSIC, ModPartEnum.ITEM, "Mcc Game Music Disc", "Mcc小游戏音乐唱片", "Mcc小游戲音樂唱片", true),
    DISC_ACE_RACE(ModItemsRegister.MUSIC_DISC_ACE_RACE, ModPartEnum.ITEM, "Mcc Game Music Disc", "Mcc小游戏音乐唱片", "Mcc小游戲音樂唱片", true),
    DISC_GRID_RUNNERS(ModItemsRegister.MUSIC_DISC_GRID_RUNNERS, ModPartEnum.ITEM, "Mcc Game Music Disc", "Mcc小游戏音乐唱片", "Mcc小游戲音樂唱片", true),
    DISC_MELTDOWN(ModItemsRegister.MUSIC_DISC_MELTDOWN, ModPartEnum.ITEM, "Mcc Game Music Disc", "Mcc小游戏音乐唱片", "Mcc小游戲音樂唱片", true),
    //BLOCK
    TEST_BLOCK(ModBlocksRegister.TEST_BLOCK, ModPartEnum.BLOCK, "Test Block", "测试方块", "測試方塊", false),
    //CREATIVE_TAB
    TEST_CREATIVE_TAB(ModCreativeTab.getCreativeMod(ModCreativeTab.TEST), ModPartEnum.CREATIVE_TAB, "Test Creative Tab", "测试创造物品栏", "測試創造物品欄",false),
    //PAINTING
    RANK_TITLE(ModPaintingVariants.getPaintingVariantTitleKey(ModPaintingVariants.RANK), ModPartEnum.TITLE, "Rank", "然可", "然可", false),
    RANK_AUTHOR(ModPaintingVariants.getPaintingVariantAuthorKey(ModPaintingVariants.RANK), ModPartEnum.AUTHOR, "Who know?", "佚名", "佚名", false),
    GROUP_PHOTO_TITLE(ModPaintingVariants.getPaintingVariantTitleKey(ModPaintingVariants.GROUP_PHOTO),ModPartEnum.TITLE, "§dGroup Photo §7[§6memorable§7]§r", "§d集体照  §7[§6纪念§7]§r", "§d集體照 §7[§6紀念§7]§r", false),
    GROUP_PHOTO_AUTHOR(ModPaintingVariants.getPaintingVariantAuthorKey(ModPaintingVariants.GROUP_PHOTO),ModPartEnum.AUTHOR, "§9Leisure §4Time §eDock§r","§9闲趣§4时§e坞§r","§9閑趣§4時§e塢§r",false),
    //SOUND_SUBTITLE
    ST_SANDS_OF_TIME(ModSoundRegister.getSubTitleTranslateKey("sands_of_time"), ModPartEnum.TITLE, "Sands Of Time", "肝疼小曲", "時之沙", false),
    ST_HUB_MUSIC(ModSoundRegister.getSubTitleTranslateKey("hub_music"), ModPartEnum.TITLE, "Main Hub Music", "主大厅音乐", "主大厅音乐", false),
    ST_GRID_RUNNER(ModSoundRegister.getSubTitleTranslateKey("grid_runners"), ModPartEnum.TITLE, "Grid Runners", "网格跑者", "網格跑者", false),
    ST_ACE_RACE(ModSoundRegister.getSubTitleTranslateKey("ace_race"), ModPartEnum.TITLE, "Ace Race", "王牌竞速", "王牌盡速", false),
    ST_MELTDOWN(ModSoundRegister.getSubTitleTranslateKey("meltdown"), ModPartEnum.TITLE, "Meltdown", "熔毁", "熔毀", false),
    //JUKEBOX_SONG
    JB_SANDS_OF_TIME(ModSoundRegister.getJukeboxSongTranslateKey("sands_of_time"), ModPartEnum.DESCRIPTION, "Sands of time", "时之沙", "時之沙", false),
    JB_HUB_MUSIC(ModSoundRegister.getJukeboxSongTranslateKey("hub_music"), ModPartEnum.DESCRIPTION, "Main Hub's music", "主大厅音乐", "主大廳音樂", false),
    JB_ACE_RACE(ModSoundRegister.getJukeboxSongTranslateKey("ace_race"), ModPartEnum.DESCRIPTION, "Ace Race", "王牌竞速", "王牌盡速", false),
    JB_GRID_RUNNER(ModSoundRegister.getJukeboxSongTranslateKey("grid_runner"), ModPartEnum.DESCRIPTION, "Grid Runners", "网格跑者", "網格跑者", false),
    JB_MELTDOWN(ModSoundRegister.getJukeboxSongTranslateKey("meltdown"), ModPartEnum.DESCRIPTION, "Meltdown", "熔毁", "熔毀", false),
    //MOB_EFFECT
    DRUNK_EFFECT(ModEffectRegister.getModEffectKey(ModEffectRegister.DRUNK_EFFECT), ModPartEnum.NAME, "Drunk", "醉", "醉",false),
    //POTION
    DRUNK_POTION(ModPotionRegister.getPotionNameKey("drunk", (char) 0), ModPartEnum.ITEM, "Wine", "酒", "酒",false),
    DRUNK_POTION_SPLASH(ModPotionRegister.getPotionNameKey("drunk", (char) 2), ModPartEnum.ITEM, "Splash Wine", "喷溅型酒", "噴濺型酒",false),
    DRUNK_POTION_LINGERING(ModPotionRegister.getPotionNameKey("drunk", (char) 1), ModPartEnum.ITEM, "Lingering Wine", "滞留型酒", "滯留型酒",false),
    L_DRUNK_POTION(ModPotionRegister.getPotionNameKey("long_drunk", (char) 0), ModPartEnum.ITEM, "Wine", "酒", "酒",false),
    L_DRUNK_POTION_SPLASH(ModPotionRegister.getPotionNameKey("long_drunk", (char) 2), ModPartEnum.ITEM, "Splash Wine", "喷溅型酒", "噴濺型酒",false),
    L_DRUNK_POTION_LINGERING(ModPotionRegister.getPotionNameKey("long_drunk", (char) 1), ModPartEnum.ITEM, "Lingering Wine", "滞留型酒", "滯留型酒",false),
    S_DRUNK_POTION(ModPotionRegister.getPotionNameKey("strong_drunk", (char) 0), ModPartEnum.ITEM, "Strong Wine", "烈酒", "烈酒",false),
    S_DRUNK_POTION_SPLASH(ModPotionRegister.getPotionNameKey(")strong_drunk", (char) 2), ModPartEnum.ITEM, "Splash Strong Wine", "喷溅型烈酒", "噴濺型烈酒",false),
    S_DRUNK_POTION_LINGERING(ModPotionRegister.getPotionNameKey("strong_drunk", (char) 1), ModPartEnum.ITEM, "Lingering Strong Wine", "滞留型烈酒", "滯留型烈酒",false),
    //ARROW
    DRUNK_ARROW(ModPotionRegister.getTippedArrowNameKey("drunk"), ModPartEnum.ITEM, "Arrow of Drunk", "醉酒之箭", "醉酒之箭", false),
    L_DRUNK_ARROW(ModPotionRegister.getTippedArrowNameKey("long_drunk"), ModPartEnum.ITEM, "Arrow of Drunk", "醉酒之箭", "醉酒之箭", false),
    S_DRUNK_ARROW(ModPotionRegister.getTippedArrowNameKey("strong_drunk"), ModPartEnum.ITEM, "Arrow of Strong Drunk", "烈酒之箭", "烈酒之箭", false),
    //ENCHANTMENT
    DEAD_EYES(ModEnchantments.getEnchantmentKey(ModEnchantments.DEATH_EYES), ModPartEnum.ENCHANTMENT, "Death Eyes", "死亡之眼", "死亡之眼", false),
    CHANGE_ITEM(ModEnchantments.getEnchantmentKey(ModEnchantments.CHANGE_ITEM), ModPartEnum.ENCHANTMENT, "Change item", "易物", "易物", "易", false),
    RANDOM_ENCHANTMENT(ModEnchantments.getEnchantmentKey(ModEnchantments.RANDOM_ENCHANTMENT), ModPartEnum.ENCHANTMENT, "§k#§rRandom§k#§r", "§k#§r随机§k#§r", "§k#§r隨機§k#§r", false),
    //ADVANCEMENT
    NAME_START_ADV(ModAdvancementKey.RWN_WELCOME.getNameKey(), ModPartEnum.ADVANCEMENT, "Whimsy", "奇思妙想", "奇思妙想", false),
    DES_START_ADV(ModAdvancementKey.RWN_WELCOME.getDescKey(), ModPartEnum.DESCRIPTION, "Welcome Join this world.Have Fun !", "欢迎加入，快乐至上", "感謝游玩，快樂至上", false),
    NAME_CHANGE_ITEM_ADV(ModAdvancementKey.RWN_CHANGE_ITEM.getNameKey(), ModPartEnum.ADVANCEMENT, "Fight your own shield with your spear", "以子之矛，攻子之盾", "以己之矛，攻子之盾",false),
    DES_CHANGE_ITEM_ADV(ModAdvancementKey.RWN_CHANGE_ITEM.getDescKey(), ModPartEnum.DESCRIPTION, "Change each other item by enchantment", "通过附魔来交换双方的物品", "通過附魔來交換雙方物品", false),
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

    MESSAGE_NAME_TAG_SELF_VISIBILITY_STATUS(NameTagCommand.SELF_NAME_TAG_VISIBILITY, ModPartEnum.COMMAND, "%s SelfNameTagVisible:%s","%s可见自身名牌:%s","%s可見自身名牌:%s",false),
    MESSAGE_NAME_TAG_OTHERS_HIDDEN_STATUS(NameTagCommand.OTHERS_NAME_TAG_HIDDEN, ModPartEnum.COMMAND, "%s OthersNameTagHidden:%s","对于%s隐藏其它玩家名牌:%s","對於%s隱藏他人名牌:%s",false),
    MESSAGE_NAME_TAG_FORBIDDEN_OPERATION(NameTagCommand.FORBIDDEN_MODIFIED_GAMERULE_LOCK, ModPartEnum.COMMAND, "Modifications to content locked by the rules are prohibited. To make changes, please disable the setting that forcefully hides labels above other players' heads.", "禁止修改规则已锁定的内容，如要修改请先关闭强制隐藏他人NameTag规则", "禁止修改規則已鎖定内容，如需修改請先關閉强制隱藏他人頭部NameTag規則",false),

    MESSAGE_MOTION_SETTER_SUCCESSFUL(MotionCommand.MOTION_SETTER_SUCCESSFUL,ModPartEnum.COMMAND,"§bSet Successfully.§a%s§7:§f[§eCurrent Vec§7: §a(§f%f§7, §f%f§7, §f%f§a)§f]§r","§b设置成功.§a%s§7:§f[§e目前速度§7: §a(§f%f§7, §f%f§7, §f%f§a)§f]§r","§b設置成功.§a%s§7:§f[§e目前速度§7: §a(§f%f§7, §f%f§7, §f%f§a)§f]§r",false),
    MESSAGE_MOTION_ADDER_SUCCESSFUL(MotionCommand.MOTION_ADDER_SUCCESSFUL,ModPartEnum.COMMAND, "§bAdd Successfully.§a%s§7:§f[§eCurrent Vec§7: §a(§f%f§7, §f%f§7, §f%f§a)§f]§r","§b添加成功.§a%s§7:§f[§e目前速度§7: §a(§f%f§7, §f%f§7, §f%f§a)§f]§r","§b添加成功.§a%s§7:§f[§e目前速度§7: §a(§f%f§7, §f%f§7, §f%f§a)§f]§r", false),
    MESSAGE_LEASH_LENGTH_FAIL(LeashCommand.LEASH_LENGTH_FAIL, ModPartEnum.COMMAND, "Failed (Internal Error)", "失败（内部错误）", "失敗（内部錯誤）", false),
    MESSAGE_LEASH_LENGTH_SHOW(LeashCommand.LEASH_LENGTH_SHOW, ModPartEnum.COMMAND, "The Leash Length of %s is %f blocks", "%s的拴绳长度为%f格", "%s的栓繩長度為%f格" , false),
    MESSAGE_LEASH_LENGTH_SET(LeashCommand.LEASH_LENGTH_SET, ModPartEnum.COMMAND, "The Leash length of %s is set to %f blocks", "%s的拴绳长度被设置为%f格", "%s的栓繩長度被設置為%f格" , false),
    //INFO_MESSAGE
    CHAT_NONE_HEARD_YOU(DefaultTalkArea.CHAT_NONE_HEARD_YOU, ModPartEnum.MESSAGE, "Nobody heard your message", "沒有人接收到你的消息", "無人接收到你的訊息", "无人受子问",false),
    //TEST MESSAGE
    TEST_OPEN_FILE_LINK(TestClientCommand.TEST_KEY_OPEN_FILE_LINK, ModPartEnum.COMMAND, ">> %s",">> %s",">> %s",false),
    //API WEBSOCKET APPLICATION
    PB_LINK_SHOW(APILanguageKey.PB_LINK_OF_QRCODE, ModPartEnum.MESSAGE, "Here is Bind Qrcode Link for DG_LAB PowerBox -> %s","这里是主机绑定二维码链接 -> %s","這裏是主機綁定二維碼鏈接 -> %s",false),
    PB_BIND_SUC(APILanguageKey.PB_BIND_SUCCESSFUL, ModPartEnum.MESSAGE, "bind successful", "绑定成功" , "綁定成功", false),
    //GAME_RULE_NAME
    DEFAULT_AREA_TALK_NAME(DefaultTalkArea.NAME_KEY, ModPartEnum.NAME, "Default Area Talk", "默认聊天区域可见半径", "默認聊天區域可見半徑",false),
    MUST_OTHERS_HIDDEN_NAME_TAG_NAME(MustOthersHiddenNameTag.NAME_KEY, ModPartEnum.NAME, "Force Hide Others' NameTag", "强制隐藏他人名字标签", "强制隱藏他人名字標簽", false),
    //GAME_RULE_DESCRIPTION
    DEFAULT_AREA_TALK_DESCRIPTION(DefaultTalkArea.DESCRIPTION_KEY, ModPartEnum.DESCRIPTION, "When the global setting is non-negative, it limits the chat range for all players. If a player's configured chat range is smaller than this value, this rule applies. Otherwise, the player's custom value is used.", "全局区域设置为非负数时，则限制全体玩家的聊天区域。若玩家设置的聊天区域小于该值则采用该规则，反之则采用玩家自定义值", "儅全局區域為非負時，則限制全服玩家聊天區域。若玩家自定義值小於該規則則采用，反之則用玩家自定義值","阖局初置非负数，则限一顾之日下，若立聊城小在直则用其常，若用玩义直上跻之地", false),
    MUST_OTHERS_HIDDEN_NAME_DESCRIPTION(MustOthersHiddenNameTag.DESCRIPTION_KEY, ModPartEnum.DESCRIPTION, "Forcefully turn off the display of others' NameTags, but it does not affect the display of NameTags through team settings. (After enabling, NameTag cannot be used to control others' head NameTag hidden control)", "强制关闭他人NameTag显示，但不影响通过队伍设置来显示NameTag的情况。（开启后无法使用NameTag来控制他人头部NameTag隐藏控制）", "強制關閉他人NameTag顯示，但不影響通過隊伍設定來顯示NameTag的情况。 （開啟後無法使用NameTag來控制他人頭部NameTag隱藏控制）", false),

    ;

    private final Supplier<?> supplier;
    private String key;
    private final String US_EN;
    private final String SIM_CN;
    private final String TRA_CN;
    private final String LZH;
    private final Boolean Default;
    private final ModPartEnum MPE;
    ModLangKeyValue(Supplier<?> Supplier, ModPartEnum MPE, String US_EN, String SIM_CN, String TRA_CN, String LZH, Boolean isDefault) {
        this.supplier = Supplier;
        this.MPE = MPE;
        this.US_EN = US_EN;
        this.SIM_CN = SIM_CN;
        this.TRA_CN = TRA_CN;
        this.LZH = LZH;
        this.Default = isDefault;
    }
    ModLangKeyValue(@NotNull String ResourceKey, ModPartEnum MPE, String US_EN, String SIM_CN, String TRA_CN, String LZH, Boolean isDefault) {
        this.supplier = null;
        this.key = ResourceKey;
        this.MPE = MPE;
        this.US_EN = US_EN;
        this.SIM_CN = SIM_CN;
        this.TRA_CN = TRA_CN;
        this.LZH = LZH;
        this.Default = isDefault;
    }
    ModLangKeyValue(Supplier<?> Supplier, ModPartEnum MPE, String US_EN, String SIM_CN, String TRA_CN, Boolean isDefault) {
        this(Supplier, MPE, US_EN, SIM_CN, TRA_CN, null, isDefault);
    }
    ModLangKeyValue(@NotNull String ResourceKey, ModPartEnum MPE, String US_EN, String SIM_CN, String TRA_CN, Boolean isDefault) {
        this(ResourceKey, MPE, US_EN, SIM_CN, TRA_CN, null, isDefault);
    }
    public static String getLan(LanguageEnum lan, ModLangKeyValue key) {
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
    private static String getEnglish(ModLangKeyValue key) {
        return key.US_EN;
    }
    private static String getSimpleChinese(ModLangKeyValue key) {
        return key.SIM_CN;
    }
    private static String getTraditionalChinese(ModLangKeyValue key) {
        return key.TRA_CN;
    }
    @Nullable public static String getLiteraryChinese(ModLangKeyValue key) {
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
        assert supplier != null;
        return (Item)supplier.get();
    }
    @SuppressWarnings("null")
    public Block getBlock() {
        assert supplier != null;
        return (Block)supplier.get();
    }
    public boolean isDefaultItem(){
        return MPE == ModPartEnum.ITEM && Default;
    }
    public boolean isDefaultBlock() {
        return MPE == ModPartEnum.BLOCK && Default;
    }
}
