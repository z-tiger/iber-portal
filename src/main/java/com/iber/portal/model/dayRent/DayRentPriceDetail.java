package com.iber.portal.model.dayRent;

import java.util.Date;

public class DayRentPriceDetail {
    private Integer id;

    private String cityCode;

    private Integer modelId;

    private Date currDate;

    private Integer actualPrice;

    private Integer basePrice;

    private Integer insurancePrice;

    private Integer procedurePrice;

    private Integer remotePrice;

    private Integer freeCompensationPrice;

    private Integer timeoutPrice;

    private Date updateTime;

    private String updateUser;
    
    private String brandName;//品牌
    
    public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	private String modelName;//车型

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode == null ? null : cityCode.trim();
    }

    public Integer getModelId() {
        return modelId;
    }

    public void setModelId(Integer modelId) {
        this.modelId = modelId;
    }

    public Date getCurrDate() {
        return currDate;
    }

    public void setCurrDate(Date currDate) {
        this.currDate = currDate;
    }

    public Integer getActualPrice() {
        return actualPrice;
    }

    public void setActualPrice(Integer actualPrice) {
        this.actualPrice = actualPrice;
    }

    public Integer getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(Integer basePrice) {
        this.basePrice = basePrice;
    }

    public Integer getInsurancePrice() {
        return insurancePrice;
    }

    public void setInsurancePrice(Integer insurancePrice) {
        this.insurancePrice = insurancePrice;
    }

    public Integer getProcedurePrice() {
        return procedurePrice;
    }

    public void setProcedurePrice(Integer procedurePrice) {
        this.procedurePrice = procedurePrice;
    }

    public Integer getRemotePrice() {
        return remotePrice;
    }

    public void setRemotePrice(Integer remotePrice) {
        this.remotePrice = remotePrice;
    }

    public Integer getFreeCompensationPrice() {
        return freeCompensationPrice;
    }

    public void setFreeCompensationPrice(Integer freeCompensationPrice) {
        this.freeCompensationPrice = freeCompensationPrice;
    }

    public Integer getTimeoutPrice() {
        return timeoutPrice;
    }

    public void setTimeoutPrice(Integer timeoutPrice) {
        this.timeoutPrice = timeoutPrice;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser == null ? null : updateUser.trim();
    }
}