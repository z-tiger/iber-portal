package com.iber.portal.enums;

import com.iber.portal.enums.handler.IntEnum;

public enum DetailItemEnum implements IntEnum<PayStatusEnum> {

    DELAY(1,"误工费"),
    RESCUE(2,"救援费"),
    REPAIR(3,"维修费"),
    VIOLATION(4,"违章处理费"),
    INSURANCE(5,"保险上涨费")
    ;


    private Integer value;
    private String displayName;

    private DetailItemEnum(Integer value, String displayName) {
        this.value=value;
        this.displayName=displayName;
    }
    public static String fromIndex(int index) {
        for (DetailItemEnum p : DetailItemEnum.values()) {
            if (index == p.getValue())
                return p.displayName;
        }
        return null;
    }
    public Integer getValue() {
        return value;
    }
    public void setValue(Integer value) {
        this.value = value;
    }
    public String getDisplayName() {
        return displayName;
    }
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
    @Override
    public int getIntValue() {
        return this.value;
    }
}
