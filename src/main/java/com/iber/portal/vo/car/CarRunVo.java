package com.iber.portal.vo.car;

public class CarRunVo {
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
    
    private String address ;
    
    private String cpuTemperature;//车载cpu温度
    
    private String preOffline;

    private Integer tfCardExist; //tf卡是否在线
    
    private String preofflineReason;

    public String getPreofflineReason() {
		return preofflineReason;
	}

	public void setPreofflineReason(String preofflineReason) {
		this.preofflineReason = preofflineReason;
	}

	public String getPreOffline() {
		return preOffline;
	}

	public void setPreOffline(String preOffline) {
		this.preOffline = preOffline;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCpuTemperature() {
		return cpuTemperature;
	}

	public void setCpuTemperature(String cpuTemperature) {
		this.cpuTemperature = cpuTemperature;
	}

    public Integer getTfCardExist() {
        return tfCardExist;
    }

    public void setTfCardExist(Integer tfCardExist) {
        this.tfCardExist = tfCardExist;
    }
}