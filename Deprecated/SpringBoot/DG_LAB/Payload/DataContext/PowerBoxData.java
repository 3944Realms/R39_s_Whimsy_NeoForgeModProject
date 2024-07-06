package com.r3944realms.whimsy.api.SpringBoot.DG_LAB.Payload.DataContext;

import org.jetbrains.annotations.NotNull;

public class PowerBoxData implements IData {
    final String negativeError = "错误的参数大小：不得为负数";
    private String Message_A = "int_default";
    private String Message_B = "int_default";
    private Integer Timer_A = 0;
    private Integer Timer_B = 0;
    private Integer Channel = 0;
    private Integer Strength = 0;

    public PowerBoxData() {
        Message_A = "default";
        Message_B = "default";
        Timer_A = 0;
        Timer_B = 0;
        Channel = 0;
        Strength = 0;
    }

    private PowerBoxData(String Message_A, String Message_B, Integer Timer_A, Integer Timer_B, Integer Channel, Integer Strength) {
        this.Message_A = Message_A;
        this.Message_B = Message_B;
        this.Timer_A = Timer_A;
        this.Timer_B = Timer_B;
        this.Channel = Channel;
        this.Strength = Strength;
    }

    public PowerBoxData getPowerBoxData(String Message_A, String Message_B, Integer Timer_A, Integer Timer_B, Integer Channel, Integer Strength) {
       if(!(Message_A != null && Message_B != null && Timer_A != null && Timer_B != null && Channel != null && Strength != null)) {
           throw new NullPointerException("空指针异常");
       }
        if(Timer_A < 0 || Timer_B < 0 || Channel < 0 || Strength < 0) {
            throw new IllegalArgumentException(negativeError);
        }
        return new PowerBoxData(Message_A, Message_B, Timer_A, Timer_B, Channel, Strength);
    }


    //==========Getter/Setter Beginning ==========
    public String getMessage_A() {
        return Message_A;
    }
    public void setMessage_A(@NotNull String message_A) {
        Message_A = message_A;
    }
    public String getMessage_B() {
        return Message_B;
    }
    public void setMessage_B(@NotNull String message_B) {
        this.Message_B = message_B;
    }

    public Integer getChannel() {
        return Channel;
    }
    public void setChannel(@NotNull Integer channel) {
        if(channel >= 0) Channel = channel;
        else throw new IllegalArgumentException(negativeError);
    }
    public Integer getStrength() {
        return Strength;
    }
    public void setStrength(@NotNull Integer strength) {
        if(strength >= 0) Strength = strength;
        else throw new IllegalArgumentException(negativeError);
    }
    public Integer getTimer_A() {
        return Timer_A;
    }
    public void setTimer_A(@NotNull Integer timer_A) {
        if(timer_A >= 0) Timer_A = timer_A;
        else throw new IllegalArgumentException(negativeError);
    }
    public Integer getTimer_B() {
        return Timer_B;
    }
    public void setTimer_B(@NotNull Integer timer_B) {
        if(timer_B >= 0) Timer_B = timer_B;
        else throw  new IllegalArgumentException(negativeError);
    }
    //==========EndPoint Here ============

    @Override
    public DataType type() {
        return DataType.POWER_BOX;
    }
}
