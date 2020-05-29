package com.iber.portal.enums;

import com.iber.portal.enums.handler.IntEnum;

public enum PayStatusEnum implements IntEnum<PayStatusEnum> {
    UNPAY(0,"未支付"),

    PAYED(1,"支付完成"),
    ;
    private Integer value;
    private String displayName;

    private PayStatusEnum(Integer value,String displayName) {
        this.value=value;
        this.displayName=displayName;
    }
    public static String fromIndex(int index) {
        for (PayStatusEnum p : PayStatusEnum.values()) {
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
