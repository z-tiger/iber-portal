package com.iber.portal.vo.car;

/**
 * 车辆运营状态报告运营车辆电量较低查询条件
 * @author zengfeiyue
 */
public class CarReportQuery {
    private String brandName;
    private String minBattery;

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getMinBattery() {
        return minBattery;
    }

    public void setMinBattery(String minBattery) {
        this.minBattery = minBattery;
    }
}
