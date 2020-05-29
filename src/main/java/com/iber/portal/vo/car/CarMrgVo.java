package com.iber.portal.vo.car;

import com.iber.portal.model.car.Car;

public class CarMrgVo extends Car {

	private Integer carRunId;

	private String orderId;

	private Integer parkId;

	private String parkName;

	private String parkAddress;

	private Integer pileId;

	private String carRunStatus;

	private String carRunLatitude;

	private String carRunLongitude;

	private String carRunSpeed;

	private String batStatus;

	private String doorStatus;

	private Double enduranceMileage;

	private Double restBattery;

	private Integer memberId;

	private String isOnline;

	private String carTypeType;

	private String carTypeTypeName;

	private String carTypeBrance;

	private Integer carTypeCarriage;

	private Integer carTypeSeatNumber;

	private Integer carTypeEndurance;

	private String cityName;
	
    private String smallBatteryChargeStatus;
    
    private String smallBatteryVoltage;
    // sys_param 小电瓶电压阀值
    private String sysSmallBatteryVoltage;
    
    private String updateTime ;
    
    private String carImgUri ; //车辆图片
    
    private String cpuTemperature;//车载cpu温度
    
    private String brandUrl;//车牌图片URL
    
    private Integer boxType;
    
    private String insuranceFileUri;//行驶证图片
    private String drivingLicenseFileUri;//行驶证图片

    private String insuranceStrongUri; //保险单(交强险)
    private String insuranceBusUri;//保险单(商业险)
    
	private String tfcard; //tf卡状态

    private String preOffline;  // 预下线标记
    
    private String getuiStatus;//个推状态

	private String brandName;

	private String name;

	private String phone;

	private Integer levelCode;

	private String orderStatus;

	private String giName ; //所属片区
	
	private String carAddress;
	
	private String iccId;

	private String areaName;

	private String branceType;
	
	
	public String getSysSmallBatteryVoltage() {
		return sysSmallBatteryVoltage;
	}
	public void setSysSmallBatteryVoltage(String sysSmallBatteryVoltage) {
		this.sysSmallBatteryVoltage = sysSmallBatteryVoltage;
	}
	/** 车上线下线电量阀值 **/
	private Double onlineLowerLimit;//补电时，能自动上线的电量下限
	private Double offlineThreshold;//自动下线的电量阈值

    private Integer enterpriseRelationCarId;//x_enterprise_relationo_car 的主键id

    private Integer enterpriseId;

    public Integer getEnterpriseRelationCarId() {
        return enterpriseRelationCarId;
    }

    public void setEnterpriseRelationCarId(Integer enterpriseRelationCarId) {
        this.enterpriseRelationCarId = enterpriseRelationCarId;
    }

    public Integer getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(Integer enterpriseId) {
        this.enterpriseId = enterpriseId;
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

	public String getIccId() {
		return iccId;
	}

	public void setIccId(String iccId) {
		this.iccId = iccId;
	}

	public String getCarAddress() {
		return carAddress;
	}

	public void setCarAddress(String carAddress) {
		this.carAddress = carAddress;
	}
	
	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getPhone() {
		return phone;
	}

	@Override
	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getLevelCode() {
		return levelCode;
	}

	public void setLevelCode(Integer levelCode) {
		this.levelCode = levelCode;
	}

	@Override
	public String getBrandName() {
		return brandName;
	}

	@Override
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

    
	public String getGiName() {
		return giName;
	}

	public void setGiName(String giName) {
		this.giName = giName;
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

	public Integer getBoxType() {
		return boxType;
	}

	public void setBoxType(Integer boxType) {
		this.boxType = boxType;
	}

	public String getBrandUrl() {
		return brandUrl;
	}

	public void setBrandUrl(String brandUrl) {
		this.brandUrl = brandUrl;
	}

	public CarMrgVo() {
	}

	public Integer getCarRunId() {
		return carRunId;
	}

	public void setCarRunId(Integer carRunId) {
		this.carRunId = carRunId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
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

	public String getParkAddress() {
		return parkAddress;
	}

	public void setParkAddress(String parkAddress) {
		this.parkAddress = parkAddress;
	}

	public Integer getPileId() {
		return pileId;
	}

	public void setPileId(Integer pileId) {
		this.pileId = pileId;
	}

	public String getCarRunStatus() {
		return carRunStatus;
	}

	public void setCarRunStatus(String carRunStatus) {
		this.carRunStatus = carRunStatus;
	}

	public String getCarRunLatitude() {
		return carRunLatitude;
	}

	public void setCarRunLatitude(String carRunLatitude) {
		this.carRunLatitude = carRunLatitude;
	}

	public String getCarRunLongitude() {
		return carRunLongitude;
	}

	public void setCarRunLongitude(String carRunLongitude) {
		this.carRunLongitude = carRunLongitude;
	}

	public String getCarRunSpeed() {
		return carRunSpeed;
	}

	public void setCarRunSpeed(String carRunSpeed) {
		this.carRunSpeed = carRunSpeed;
	}

	public String getBatStatus() {
		return batStatus;
	}

	public void setBatStatus(String batStatus) {
		this.batStatus = batStatus;
	}

	public String getDoorStatus() {
		return doorStatus;
	}

	public void setDoorStatus(String doorStatus) {
		this.doorStatus = doorStatus;
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
		this.isOnline = isOnline;
	}

	public String getCarTypeType() {
		return carTypeType;
	}

	public void setCarTypeType(String carTypeType) {
		this.carTypeType = carTypeType;
	}

	public String getCarTypeTypeName() {
		return carTypeTypeName;
	}

	public void setCarTypeTypeName(String carTypeTypeName) {
		this.carTypeTypeName = carTypeTypeName;
	}

	public String getCarTypeBrance() {
		return carTypeBrance;
	}

	public void setCarTypeBrance(String carTypeBrance) {
		this.carTypeBrance = carTypeBrance;
	}

	public Integer getCarTypeCarriage() {
		return carTypeCarriage;
	}

	public void setCarTypeCarriage(Integer carTypeCarriage) {
		this.carTypeCarriage = carTypeCarriage;
	}

	public Integer getCarTypeSeatNumber() {
		return carTypeSeatNumber;
	}

	public void setCarTypeSeatNumber(Integer carTypeSeatNumber) {
		this.carTypeSeatNumber = carTypeSeatNumber;
	}

	public Integer getCarTypeEndurance() {
		return carTypeEndurance;
	}

	public void setCarTypeEndurance(Integer carTypeEndurance) {
		this.carTypeEndurance = carTypeEndurance;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCityName() {
		return cityName;
	}
	
	public String getSmallBatteryChargeStatus() {
		return smallBatteryChargeStatus;
	}

	public void setSmallBatteryChargeStatus(String smallBatteryChargeStatus) {
		this.smallBatteryChargeStatus = smallBatteryChargeStatus;
	}

	public String getSmallBatteryVoltage() {
		return smallBatteryVoltage;
	}

	public void setSmallBatteryVoltage(String smallBatteryVoltage) {
		this.smallBatteryVoltage = smallBatteryVoltage;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getCarImgUri() {
		return carImgUri;
	}

	public void setCarImgUri(String carImgUri) {
		this.carImgUri = carImgUri;
	}

	public String getCpuTemperature() {
		return cpuTemperature;
	}

	public void setCpuTemperature(String cpuTemperature) {
		this.cpuTemperature = cpuTemperature;
	}

	public String getInsuranceFileUri() {
		return insuranceFileUri;
	}

	public void setInsuranceFileUri(String insuranceFileUri) {
		this.insuranceFileUri = insuranceFileUri;
	}

	public String getDrivingLicenseFileUri() {
		return drivingLicenseFileUri;
	}

	public void setDrivingLicenseFileUri(String drivingLicenseFileUri) {
		this.drivingLicenseFileUri = drivingLicenseFileUri;
	}

	public String getTfcard() {
		return tfcard;
	}

	public void setTfcard(String tfcard) {
		this.tfcard = tfcard;
	}

	public String getInsuranceStrongUri() {
		return insuranceStrongUri;
	}

	public void setInsuranceStrongUri(String insuranceStrongUri) {
		this.insuranceStrongUri = insuranceStrongUri;
	}

	public String getInsuranceBusUri() {
		return insuranceBusUri;
	}

	public void setInsuranceBusUri(String insuranceBusUri) {
		this.insuranceBusUri = insuranceBusUri;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getBranceType() {
		return branceType;
	}

	public void setBranceType(String branceType) {
		this.branceType = branceType;
	}
}
