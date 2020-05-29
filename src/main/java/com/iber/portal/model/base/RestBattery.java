package com.iber.portal.model.base;

import java.io.Serializable;

/**
 * 电量
 * lf
 * 2018年2月7日
 */
public class RestBattery implements Serializable{

    private static final long serialVersionUID = -3203238702188830942L;
    private Double startRestBattery; // 开始电量
    private Double endRestBattery; // 结束电量

    public Double getStartRestBattery() {
        return startRestBattery;
    }

    public void setStartRestBattery(Double startRestBattery) {
        this.startRestBattery = startRestBattery;
    }

    public Double getEndRestBattery() {
        return endRestBattery;
    }

    public void setEndRestBattery(Double endRestBattery) {
        this.endRestBattery = endRestBattery;
    }

    @Override
    public String toString() {
        return "RestBattery{" +
                "startRestBattery=" + startRestBattery +
                ", endRestBattery=" + endRestBattery +
                '}';
    }
}
