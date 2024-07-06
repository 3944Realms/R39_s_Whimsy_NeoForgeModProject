package com.r3944realms.whimsy.api.SpringBoot.DG_LAB.Payload.DataContext;

public interface IData {
    /**
     * 谁的数据类型
     * @return （String）谁的数据类型
     */
    DataType type();
    enum DataType {
        POWER_BOX,
        UNKNOWN
    }
}

