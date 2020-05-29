package com.iber.portal.model.timeShare;

import java.util.Date;

public class TimeShareRate {
    private Integer id;

    private String name;

    private String description;

    private Integer timeUnit;

    private Integer timeRate;

    private Integer milesRate;

    private Integer otherCost;

    private Integer discount;

    private Integer timeDiscount;

    private Integer milesDiscount;

    private String status;

    private Date createdTime;

    private String createdUser;

    private Date updatedTime;

    private String updatedUser;

    private Integer minConsump;

    private Integer maxConsump;

    private String cityCode;
    
    private String cityName;
    
    private String brandName;//品牌

	private Integer carTypeId;

    private String carTypeName;
    
    private Double freeCompensationPrice ;
    
    private Double maxFreeCompensationPrice ;
    
    private Integer nightTimeRate ;
    
    private Integer nightMilesRate ;
    
    private Date discountStartTime;
    
    private Date discountEndTime;
    
    private Integer timeDiscountRate;
    
    public Integer getTimeDiscountRate() {
		return timeDiscountRate;
	}

	public void setTimeDiscountRate(Integer timeDiscountRate) {
		this.timeDiscountRate = timeDiscountRate;
	}

	public Date getDiscountStartTime() {
		return discountStartTime;
	}

	public void setDiscountStartTime(Date discountStartTime) {
		this.discountStartTime = discountStartTime;
	}

	public Date getDiscountEndTime() {
		return discountEndTime;
	}

	public void setDiscountEndTime(Date discountEndTime) {
		this.discountEndTime = discountEndTime;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Integer getTimeUnit() {
        return timeUnit;
    }

    public void setTimeUnit(Integer timeUnit) {
        this.timeUnit = timeUnit;
    }

    public Integer getTimeRate() {
        return timeRate;
    }

    public void setTimeRate(Integer timeRate) {
        this.timeRate = timeRate;
    }

    public Integer getMilesRate() {
        return milesRate;
    }

    public void setMilesRate(Integer milesRate) {
        this.milesRate = milesRate;
    }

    public Integer getOtherCost() {
        return otherCost;
    }

    public void setOtherCost(Integer otherCost) {
        this.otherCost = otherCost;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public Integer getTimeDiscount() {
        return timeDiscount;
    }

    public void setTimeDiscount(Integer timeDiscount) {
        this.timeDiscount = timeDiscount;
    }

    public Integer getMilesDiscount() {
        return milesDiscount;
    }

    public void setMilesDiscount(Integer milesDiscount) {
        this.milesDiscount = milesDiscount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getCreatedUser() {
        return createdUser;
    }

    public void setCreatedUser(String createdUser) {
        this.createdUser = createdUser == null ? null : createdUser.trim();
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    public String getUpdatedUser() {
        return updatedUser;
    }

    public void setUpdatedUser(String updatedUser) {
        this.updatedUser = updatedUser == null ? null : updatedUser.trim();
    }

    public Integer getMinConsump() {
        return minConsump;
    }

    public void setMinConsump(Integer minConsump) {
        this.minConsump = minConsump;
    }

    public Integer getMaxConsump() {
        return maxConsump;
    }

    public void setMaxConsump(Integer maxConsump) {
        this.maxConsump = maxConsump;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode == null ? null : cityCode.trim();
    }

    public Integer getCarTypeId() {
        return carTypeId;
    }

    public void setCarTypeId(Integer carTypeId) {
        this.carTypeId = carTypeId;
    }

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCarTypeName() {
		return carTypeName;
	}

	public void setCarTypeName(String carTypeName) {
		this.carTypeName = carTypeName;
	}

	public Double getFreeCompensationPrice() {
		return freeCompensationPrice;
	}

	public void setFreeCompensationPrice(Double freeCompensationPrice) {
		this.freeCompensationPrice = freeCompensationPrice;
	}

	public Double getMaxFreeCompensationPrice() {
		return maxFreeCompensationPrice;
	}

	public void setMaxFreeCompensationPrice(Double maxFreeCompensationPrice) {
		this.maxFreeCompensationPrice = maxFreeCompensationPrice;
	}

	public Integer getNightTimeRate() {
		return nightTimeRate;
	}

	public void setNightTimeRate(Integer nightTimeRate) {
		this.nightTimeRate = nightTimeRate;
	}

	public Integer getNightMilesRate() {
		return nightMilesRate;
	}

	public void setNightMilesRate(Integer nightMilesRate) {
		this.nightMilesRate = nightMilesRate;
	}

	/**
     * 品牌
     * @return
     * @author ouxx
     * @date 2016-9-28 上午11:13:46
     */
    public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
}