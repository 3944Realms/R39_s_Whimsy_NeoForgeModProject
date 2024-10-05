package com.r3944realms.dg_lab.future.websocket.sharedData;

import java.util.Timer;

public class ClientPowerBoxSharedData implements ISharedData {
    public String connectionId = "",
            targetWSId = "";
    public String ServerAddress;
    public int delay = 500; //防抖
    public Timer delayTimer;
    public boolean SRMsg;
    //跟随AB的软上限
    public volatile boolean followAStrength = false;
    public volatile boolean followBStrength = false;
    public ClientPowerBoxSharedData(int delay) {
        this.delay = delay > 0 ? delay : 500;
    }
    public ClientPowerBoxSharedData() {}

}
