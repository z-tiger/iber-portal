package com.iber.portal.model.car;

import java.util.Date;

public class CarType {
    private Integer id;

    private String type;

    private Date createTime;

    private Date updateTime;

    private String createUser;

    private String updateUser;

    private String typeName;

    private String brance;

    private Integer carriage;

    private Integer seatNumber;

    private Integer endurance;
    
    private Integer maxSpeed ;
    
    private String cityCode;
    
    private String cityName;

    private Integer budgetAmount; // 预设金额
    
    private Double onlineLowerLimit;//补电时，能自动上线的电量下限
    private Double offlineThreshold;//自动下线的电量阈值

    private String carImage;
    
    
    public Integer getMaxSpeed() {
		return maxSpeed;
	}

	public void setMaxSpeed(Integer maxSpeed) {
		this.maxSpeed = maxSpeed;
	}

	public String getCarImage() {
		return carImage;
	}

	public void setCarImage(String carImage) {
		this.carImage = carImage;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser == null ? null : updateUser.trim();
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName == null ? null : typeName.trim();
    }

    public String getBrance() {
        return brance;
    }

    public void setBrance(String brance) {
        this.brance = brance == null ? null : brance.trim();
    }

    public Integer getCarriage() {
        return carriage;
    }

    public void setCarriage(Integer carriage) {
        this.carriage = carriage;
    }

    public Integer getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(Integer seatNumber) {
        this.seatNumber = seatNumber;
    }

    public Integer getEndurance() {
        return endurance;
    }

    public void setEndurance(Integer endurance) {
        this.endurance = endurance;
    }

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

    public Integer getBudgetAmount() {
        return budgetAmount;
    }

    public void setBudgetAmount(Integer budgetAmount) {
        this.budgetAmount = budgetAmount;
    }

	public Double getOnlineLowerLimit() {
		return onlineLowerLimit;
	}

	public void setOnlineLowerLimit(Double onlineLowerLimit) {
		this.onlineLowerLimit = onlineLowerLimit;
	}

    public Double getOfflineThreshold() {
        return offlineThreshold;
    }

    public void setOfflineThreshold(Double offlineThreshold) {
        this.offlineThreshold = offlineThreshold;
    }
}