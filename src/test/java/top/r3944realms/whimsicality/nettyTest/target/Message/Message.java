package top.r3944realms.whimsicality.nettyTest.target.Message;

import java.io.Serializable;

public abstract class Message implements Serializable {
    public static Class<?> getMessageClass(int messageType) {
        return null;
    }

    private int sequenceId;

    private int messageType;

    public abstract int getMessageType();

    public static final int LoginRequestMessage = 0;
    public static final int LoginResponseMessage = 1;

    public int getSequenceId() {
        return sequenceId;
    }
}
