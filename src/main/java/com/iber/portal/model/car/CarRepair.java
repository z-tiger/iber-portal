package com.iber.portal.model.car;

import com.iber.portal.model.base.RestBattery;

import java.util.Date;

public class CarRepair extends RestBattery {
    private Integer id;

    private Integer carId;

    private String lpn;
    
    private String status;

    private Date startTime;

    private Date endTime;

    private String reason;
    
    private Integer responsiblePersonId;

	private String responsiblePerson;

    private String responsiblePersonPhone;

    private String createUser;

    private Date createTime;

    private String updateUser;

    private Date updateTime;
    
    private Date predictTime;
    
    private String cityName;
    
    private String cityCode;
    
    private String parkName;
    
    private Integer parkId;
    
    private Integer taskId;

    private double battery;

    private double voltage;

    private Integer batStatus;

    private String statusCache;
    
    private Date latestUpdateTime;// 设备最近上传时间
    
    private String bluetoothNo;  // 蓝牙地址
    
    private Integer tfCardStatus; // tf卡状态
    
    private String category ; //网点类型
    private String giName ; //所属片区

    private String orderId;

    private String recoverReason;

    private String recoverUser;

    private String recoverUserPhone;
    
    public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getGiName() {
		return giName;
	}

	public void setGiName(String giName) {
		this.giName = giName;
	}

	public String getBluetoothNo() {
		return bluetoothNo;
	}

	public void setBluetoothNo(String bluetoothNo) {
		this.bluetoothNo = bluetoothNo;
	}

	public Integer getTfCardStatus() {
		return tfCardStatus;
	}

	public void setTfCardStatus(Integer tfCardStatus) {
		this.tfCardStatus = tfCardStatus;
	}

	public Date getLatestUpdateTime() {
		return latestUpdateTime;
	}

	public void setLatestUpdateTime(Date latestUpdateTime) {
		this.latestUpdateTime = latestUpdateTime;
	}

	public Integer getTaskId() {
		return taskId;
	}

	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}

	public Integer getParkId() {
		return parkId;
	}

	public void setParkId(Integer parkId) {
		this.parkId = parkId;
	}

	public String getParkName() {
		return parkName;
	}

	public void setParkName(String parkName) {
		this.parkName = parkName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public Date getPredictTime() {
		return predictTime;
	}

	public void setPredictTime(Date predictTime) {
		this.predictTime = predictTime;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCarId() {
        return carId;
    }

    public void setCarId(Integer carId) {
        this.carId = carId;
    }

    public String getLpn() {
        return lpn;
    }

    public void setLpn(String lpn) {
        this.lpn = lpn == null ? null : lpn.trim();
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason == null ? null : reason.trim();
    }

    public Integer getResponsiblePersonId() {
		return responsiblePersonId;
	}

	public void setResponsiblePersonId(Integer responsiblePersonId) {
		this.responsiblePersonId = responsiblePersonId;
	}
	
    public String getResponsiblePerson() {
        return responsiblePerson;
    }

    public void setResponsiblePerson(String responsiblePerson) {
        this.responsiblePerson = responsiblePerson == null ? null : responsiblePerson.trim();
    }

    public String getResponsiblePersonPhone() {
        return responsiblePersonPhone;
    }

    public void setResponsiblePersonPhone(String responsiblePersonPhone) {
        this.responsiblePersonPhone = responsiblePersonPhone == null ? null : responsiblePersonPhone.trim();
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser == null ? null : updateUser.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
    
    public void setStatus(String status) {
		this.status = status;
	}
    
    public String getStatus() {
		return status;
	}

    public double getBattery() {
        return battery;
    }

    public void setBattery(double battery) {
        this.battery = battery;
    }

    public double getVoltage() {
        return voltage;
    }

    public void setVoltage(double voltage) {
        this.voltage = voltage;
    }

    public Integer getBatStatus() {
        return batStatus;
    }

    public void setBatStatus(Integer batStatus) {
        this.batStatus = batStatus;
    }

    public String getStatusCache() {
        return statusCache;
    }

    public void setStatusCache(String statusCache) {
        this.statusCache = statusCache;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getRecoverReason() {
        return recoverReason;
    }

    public void setRecoverReason(String recoverReason) {
        this.recoverReason = recoverReason;
    }

    public String getRecoverUser() {
        return recoverUser;
    }

    public void setRecoverUser(String recoverUser) {
        this.recoverUser = recoverUser;
    }

    public String getRecoverUserPhone() {
        return recoverUserPhone;
    }

    public void setRecoverUserPhone(String recoverUserPhone) {
        this.recoverUserPhone = recoverUserPhone;
    }
}