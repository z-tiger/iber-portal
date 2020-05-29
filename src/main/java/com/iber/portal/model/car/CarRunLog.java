package com.iber.portal.model.car;

import java.util.Date;

public class CarRunLog {
    private Integer id;

    private String lpn;

    private Integer pileId;

    private Integer memberId;

    private String latitude;

    private String longitude;

    private String speed;

    private String batStatus;

    private String doorStatus;

    private String carSignal;

    private Double totalKg;

    private Double totalMinute;

    private Double enduranceMileage;

    private Double restBattery;

    private String orderId;
    
    private Date createTime ;
    
    private String address ;
    
    private Double mileage ;

    private String smallBatteryVoltage ;
    private String smallBatteryChargeStatus ;

    private String gear ;// 档位
    private String handBreakStatus ;// 手刹状态 0 松开1拉紧
    private String lightStatus ;// 车灯是否开启 0 关闭 1 开启
    private String safeBeltStatus ;// 安全带状态 0 没系1已系上
    private String windowStatus ;// 车窗状态 0 关闭 1 开启
    private String readyStatus ;// Ready状态 0 熄火1启动
    private String trunkStatus ; //后备箱状态 0 关闭 1打开

    private String cpuTemperature ; //后视镜CPU温度

    private Integer netCount;//后视镜与网络交互统计次数
    private Double networkFlow;//流量统计字段   单位：M
    private Integer isWifiOn;//WIFI:0关闭 1开启
    private Integer isGpsOn;//GPS:0关闭 1开启
    private Integer weightNum;//重力感应取得值
    private Integer isCarOnline;//车是否在线：1为在线 0为下线
    private Integer tfCardExist; //tf卡是否在线

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLpn() {
        return lpn;
    }

    public void setLpn(String lpn) {
        this.lpn = lpn == null ? null : lpn.trim();
    }

    public Integer getPileId() {
        return pileId;
    }

    public void setPileId(Integer pileId) {
        this.pileId = pileId;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
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

    public String getCarSignal() {
        return carSignal;
    }

    public void setCarSignal(String carSignal) {
        this.carSignal = carSignal == null ? null : carSignal.trim();
    }

    public Double getTotalKg() {
        return totalKg;
    }

    public void setTotalKg(Double totalKg) {
        this.totalKg = totalKg;
    }

    public Double getTotalMinute() {
        return totalMinute;
    }

    public void setTotalMinute(Double totalMinute) {
        this.totalMinute = totalMinute;
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

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Double getMileage() {
		return mileage;
	}

	public void setMileage(Double mileage) {
		this.mileage = mileage;
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

    public String getSafeBeltStatus() {
        return safeBeltStatus;
    }

    public void setSafeBeltStatus(String safeBeltStatus) {
        this.safeBeltStatus = safeBeltStatus;
    }

    public String getWindowStatus() {
        return windowStatus;
    }

    public void setWindowStatus(String windowStatus) {
        this.windowStatus = windowStatus;
    }

    public String getReadyStatus() {
        return readyStatus;
    }

    public void setReadyStatus(String readyStatus) {
        this.readyStatus = readyStatus;
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

    public Integer getNetCount() {
        return netCount;
    }

    public void setNetCount(Integer netCount) {
        this.netCount = netCount;
    }

    public Double getNetworkFlow() {
        return networkFlow;
    }

    public void setNetworkFlow(Double networkFlow) {
        this.networkFlow = networkFlow;
    }

    public Integer getIsWifiOn() {
        return isWifiOn;
    }

    public void setIsWifiOn(Integer isWifiOn) {
        this.isWifiOn = isWifiOn;
    }

    public Integer getIsGpsOn() {
        return isGpsOn;
    }

    public void setIsGpsOn(Integer isGpsOn) {
        this.isGpsOn = isGpsOn;
    }

    public Integer getWeightNum() {
        return weightNum;
    }

    public void setWeightNum(Integer weightNum) {
        this.weightNum = weightNum;
    }

    public Integer getIsCarOnline() {
        return isCarOnline;
    }

    public void setIsCarOnline(Integer isCarOnline) {
        this.isCarOnline = isCarOnline;
    }

    public Integer getTfCardExist() {
        return tfCardExist;
    }

    public void setTfCardExist(Integer tfCardExist) {
        this.tfCardExist = tfCardExist;
    }
}