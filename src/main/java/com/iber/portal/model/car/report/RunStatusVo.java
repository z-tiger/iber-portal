package com.iber.portal.model.car.report;

import java.io.Serializable;

/**
 * 车辆运营状态vo
 * @author zengfeiyue
 */
public class RunStatusVo implements Serializable{

    /**
     * 用车中
     */
    private String useCar;
    /**
     * 维修中
     */
    private String repair;
    /**
     * 维护中
     */
    private String maintain;
    /**
     * 补电中
     */
    private String batStatus;

    public String getUseCar() {
        return useCar;
    }

    public void setUseCar(String useCar) {
        this.useCar = useCar;
    }

    public String getRepair() {
        return repair;
    }

    public void setRepair(String repair) {
        this.repair = repair;
    }

    public String getMaintain() {
        return maintain;
    }

    public void setMaintain(String maintain) {
        this.maintain = maintain;
    }

    public String getBatStatus() {
        return batStatus;
    }

    public void setBatStatus(String batStatus) {
        this.batStatus = batStatus;
    }
}
