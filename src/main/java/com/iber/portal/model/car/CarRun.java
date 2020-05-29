package com.iber.portal.model.car;

import java.util.Date;

public class CarRun {
    private Integer id;

    private String orderId;

    private String lpn;

    private Integer parkId;

    private Integer pileId;

    private String status;

    private String latitude;

    private String longitude;

    private String speed;

    private String batStatus;

    private String doorStatus;

    private Double enduranceMileage;

    private Double restBattery;

    private String cityCode;

    private Integer memberId;

    private String isOnline;
    
    private String smallBatteryVoltage;
    private String smallBatteryChargeStatus;
    private String carSignal;
    private String safeBeltStatus;
    private String readyStatus;
    private String gear;
    private String handBreakStatus;
    private String lightStatus;
    private String windowStatus;
    private String trunkStatus;
    private String cpuTemperature;
    private String address;
    private Date updateTime;
    private String preOffline;
    private Integer tfCardExist; //tf卡是否在线
    
    private Double latestOrderMileage;
    private Date latestOrderTime;

    private String getuiStatus;//个推状态

    private String brandName;
    private String cid; // tbox imei
    private Integer longRentStatus;// 开启日租标志 0 未开启 1 开启

    private Double totalKg;

    private Integer version;
    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getGetuiStatus() {
		return getuiStatus;
	}

	public void setGetuiStatus(String getuiStatus) {
		this.getuiStatus = getuiStatus;
	}

	public String getPreOffline() {
		return preOffline;
	}

	public void setPreOffline(String preOffline) {
		this.preOffline = preOffline;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	private Double mileage;//车辆行驶总里程
    
    public Double getMileage() {
		return mileage;
	}

	public void setMileage(Double mileage) {
		this.mileage = mileage;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    public String getLpn() {
        return lpn;
    }

    public void setLpn(String lpn) {
        this.lpn = lpn == null ? null : lpn.trim();
    }

    public Integer getParkId() {
        return parkId;
    }

    public void setParkId(Integer parkId) {
        this.parkId = parkId;
    }

    public Integer getPileId() {
        return pileId;
    }

    public void setPileId(Integer pileId) {
        this.pileId = pileId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude == null ? null : latitude.trim();
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude == null ? null : longitude.trim();
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed == null ? null : speed.trim();
    }

    public String getBatStatus() {
        return batStatus;
    }

    public void setBatStatus(String batStatus) {
        this.batStatus = batStatus == null ? null : batStatus.trim();
    }

    public String getDoorStatus() {
        return doorStatus;
    }

    public void setDoorStatus(String doorStatus) {
        this.doorStatus = doorStatus == null ? null : doorStatus.trim();
    }

    public Double getEnduranceMileage() {
        return enduranceMileage;
    }

    public void setEnduranceMileage(Double enduranceMileage) {
        this.enduranceMileage = enduranceMileage;
    }

    public Double getRestBattery() {
        return restBattery;
    }

    public void setRestBattery(Double restBattery) {
        this.restBattery = restBattery;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode == null ? null : cityCode.trim();
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public String getIsOnline() {
        return isOnline;
    }

    public void setIsOnline(String isOnline) {
        this.isOnline = isOnline == null ? null : isOnline.trim();
    }

	public String getSmallBatteryVoltage() {
		return smallBatteryVoltage;
	}

	public void setSmallBatteryVoltage(String smallBatteryVoltage) {
		this.smallBatteryVoltage = smallBatteryVoltage;
	}

	public String getSmallBatteryChargeStatus() {
		return smallBatteryChargeStatus;
	}

	public void setSmallBatteryChargeStatus(String smallBatteryChargeStatus) {
		this.smallBatteryChargeStatus = smallBatteryChargeStatus;
	}

	public String getCarSignal() {
		return carSignal;
	}

	public void setCarSignal(String carSignal) {
		this.carSignal = carSignal;
	}

	public String getSafeBeltStatus() {
		return safeBeltStatus;
	}

	public void setSafeBeltStatus(String safeBeltStatus) {
		this.safeBeltStatus = safeBeltStatus;
	}

	public String getReadyStatus() {
		return readyStatus;
	}

	public void setReadyStatus(String readyStatus) {
		this.readyStatus = readyStatus;
	}

	public String getGear() {
		return gear;
	}

	public void setGear(String gear) {
		this.gear = gear;
	}

	public String getHandBreakStatus() {
		return handBreakStatus;
	}

	public void setHandBreakStatus(String handBreakStatus) {
		this.handBreakStatus = handBreakStatus;
	}

	public String getLightStatus() {
		return lightStatus;
	}

	public void setLightStatus(String lightStatus) {
		this.lightStatus = lightStatus;
	}

	public String getWindowStatus() {
		return windowStatus;
	}

	public void setWindowStatus(String windowStatus) {
		this.windowStatus = windowStatus;
	}

	public String getTrunkStatus() {
		return trunkStatus;
	}

	public void setTrunkStatus(String trunkStatus) {
		this.trunkStatus = trunkStatus;
	}

	public String getCpuTemperature() {
		return cpuTemperature;
	}

	public void setCpuTemperature(String cpuTemperature) {
		this.cpuTemperature = cpuTemperature;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

    public Integer getTfCardExist() {
        return tfCardExist;
    }

    public void setTfCardExist(Integer tfCardExist) {
        this.tfCardExist = tfCardExist;
    }

	public Double getLatestOrderMileage() {
		return latestOrderMileage;
	}

	public void setLatestOrderMileage(Double latestOrderMileage) {
		this.latestOrderMileage = latestOrderMileage;
	}

	public Date getLatestOrderTime() {
		return latestOrderTime;
	}

	public void setLatestOrderTime(Date latestOrderTime) {
		this.latestOrderTime = latestOrderTime;
	}

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public Integer getLongRentStatus() {
        return longRentStatus;
    }

    public void setLongRentStatus(Integer longRentStatus) {
        this.longRentStatus = longRentStatus;
    }

    public Double getTotalKg() {
        return totalKg;
    }

    public void setTotalKg(Double totalKg) {
        this.totalKg = totalKg;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}