package com.iber.portal.model.car.report;

import java.io.Serializable;

/**
 * 小电瓶及电量和用户详情
 */
public class CarRunSmallBatteryDetail implements Serializable {
    /**
     * 会员名称
     */
    private String memberName;
    /**
     * 会员手机号
     */
    private String memberPhone;
    /**
     * 车辆状态
     */
    private String status;
    /**
     * 车牌
     */
    private String lpn;
    /**
     * 车辆品牌名称
     */
    private String brandName;
    /**
     * 小电瓶电压
     */
    private String smallBattery;
    /**
     * 大电瓶电量
     */
    private String restBattery;

    private String orderId;

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getMemberPhone() {
        return memberPhone;
    }

    public void setMemberPhone(String memberPhone) {
        this.memberPhone = memberPhone;
    }

    public String getLpn() {
        return lpn;
    }

    public void setLpn(String lpn) {
        this.lpn = lpn;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getSmallBattery() {
        return smallBattery;
    }

    public void setSmallBattery(String smallBattery) {
        this.smallBattery = smallBattery;
    }

    public String getRestBattery() {
        return restBattery;
    }

    public void setRestBattery(String restBattery) {
        this.restBattery = restBattery;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

}
