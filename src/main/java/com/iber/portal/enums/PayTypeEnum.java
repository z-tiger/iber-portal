package com.iber.portal.enums;
import com.iber.portal.enums.handler.IntEnum;
public enum PayTypeEnum implements IntEnum<PayStatusEnum> {

    BALANCE(0,"余额"),
    ALIPAY(1,"支付宝"),
    WECHATPAY(3,"微信")
    ;

    private Integer value;
    private String displayName;

    private PayTypeEnum(Integer value,String displayName) {
        this.value=value;
        this.displayName=displayName;
    }
    public static String fromIndex(int index) {
        for (PayTypeEnum p : PayTypeEnum.values()) {
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
