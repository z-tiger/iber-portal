package com.iber.portal.enums;

import com.iber.portal.enums.handler.IntEnum;

public enum OrderAttachedTypeEnum implements IntEnum<OrderAttachedTypeEnum> {

    VIOLATION(0,"违章"),

    REPAIR(1,"维修"),

    RESCUE(2,"救援")
    ;

    private Integer value;
    private String displayName;

    private OrderAttachedTypeEnum(Integer value,String displayName) {
        this.value=value;
        this.displayName=displayName;
    }

    public static String fromIndex(int index) {
        for (OrderAttachedTypeEnum p : OrderAttachedTypeEnum.values()) {
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

    @Override
    public int getIntValue() {
        return this.value;
    }


    @Override
    public String toString(){
        return this.displayName;
    }
}
