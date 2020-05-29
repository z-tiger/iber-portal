package com.iber.portal.model.charging;

import java.util.Date;

public class ChargingPileType {
    private Integer id;

    private String brandName;

    private String pileType;

    private Date createTime;

    private Integer createId;

    private Date updateCreate;

    private Integer updateId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName == null ? null : brandName.trim();
    }

    public String getPileType() {
        return pileType;
    }

    public void setPileType(String pileType) {
        this.pileType = pileType == null ? null : pileType.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getCreateId() {
        return createId;
    }

    public void setCreateId(Integer createId) {
        this.createId = createId;
    }

    public Date getUpdateCreate() {
        return updateCreate;
    }

    public void setUpdateCreate(Date updateCreate) {
        this.updateCreate = updateCreate;
    }

    public Integer getUpdateId() {
        return updateId;
    }

    public void setUpdateId(Integer updateId) {
        this.updateId = updateId;
    }
}