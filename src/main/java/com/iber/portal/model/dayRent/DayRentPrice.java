package com.iber.portal.model.dayRent;

import java.util.Date;

public class DayRentPrice {
    private Integer id;

    private String cityCode;

    private Integer modelId;

    private Double ordinaryCoefficient;

    private Double festivalCoefficient;

    private Double weekedCoefficient;

    private Integer basePrice;

    private Integer insurancePrice;

    private Integer procedurePrice;

    private Integer freeCompensationPrice;

    private Integer timeoutPrice;
    
    private Integer remotePrice;

    private Date createTime;

    private Integer createId;

    private Date updateCreate;

    private Integer updateId;
    
    private String modelName;
    
    private String cityName;
    
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

    public Double getOrdinaryCoefficient() {
        return ordinaryCoefficient;
    }

    public void setOrdinaryCoefficient(Double ordinaryCoefficient) {
        this.ordinaryCoefficient = ordinaryCoefficient;
    }

    public Double getFestivalCoefficient() {
        return festivalCoefficient;
    }

    public void setFestivalCoefficient(Double festivalCoefficient) {
        this.festivalCoefficient = festivalCoefficient;
    }

    public Double getWeekedCoefficient() {
        return weekedCoefficient;
    }

    public void setWeekedCoefficient(Double weekedCoefficient) {
        this.weekedCoefficient = weekedCoefficient;
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
    
    public Integer getRemotePrice() {
		return remotePrice;
	}

	public void setRemotePrice(Integer remotePrice) {
		this.remotePrice = remotePrice;
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

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
    
}