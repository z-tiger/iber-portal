package com.iber.portal.model.car;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class Car {
    private Integer id;

    private String lpn;

    private String brandName;

    private String cityCode;

    private String bluetoothNo;

    private String eLpn;

    private String type;

    private String color;

    private String seats;

    private String speed;

    private String mileage;

    private String iosUuid;

    private String phone;

    private String owner;

    private Integer modelId;
    
    private String deviceStatus;
    private Date lastTimeDeviceReporting;
    
    private String bluetoothVestionCode;
    private String boxSoftType;
    private String boxVersionCode;
    private String softType;
    private String versionCode;
    private String versionName;
    
    private String isNav;
    private String isCall;
    private String isTripRecord;
    
    private String engineno; //发动机号
    private String classno;//车架号
    
    private String carImgUri ; //车辆图片
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date annualInspectionTime;//车辆年检时间
    private String smallCarImgUri;
    
    private String insuranceFileUri;//车辆保险单图片
    private String drivingLicenseFileUri;//行驶证图片
    
    private String insuranceStrongUri;//保险单(交强险)
    private String insuranceBusUri;//商业险

    private String tboxImei;
    private Integer tboxVersion;
   
    private Boolean status;
    private String remark;
    private Integer updateId;
    private String userName ;

    private Boolean isEnterpriseBind;

    public Boolean getIsEnterpriseBind() {
        return isEnterpriseBind;
    }

    public void setIsEnterpriseBind(Boolean enterpriseBind) {
        isEnterpriseBind = enterpriseBind;
    }

    public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getSmallCarImgUri() {
		return smallCarImgUri;
	}

	public void setSmallCarImgUri(String smallCarImgUri) {
		this.smallCarImgUri = smallCarImgUri;
	}

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

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName == null ? null : brandName.trim();
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode == null ? null : cityCode.trim();
    }

    public String getBluetoothNo() {
        return bluetoothNo;
    }

    public void setBluetoothNo(String bluetoothNo) {
        this.bluetoothNo = bluetoothNo == null ? null : bluetoothNo.trim();
    }

    public String geteLpn() {
        return eLpn;
    }

    public void seteLpn(String eLpn) {
        this.eLpn = eLpn == null ? null : eLpn.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color == null ? null : color.trim();
    }

    public String getSeats() {
        return seats;
    }

    public void setSeats(String seats) {
        this.seats = seats == null ? null : seats.trim();
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed == null ? null : speed.trim();
    }

    public String getMileage() {
        return mileage;
    }

    public void setMileage(String mileage) {
        this.mileage = mileage == null ? null : mileage.trim();
    }

    public String getIosUuid() {
        return iosUuid;
    }

    public void setIosUuid(String iosUuid) {
        this.iosUuid = iosUuid == null ? null : iosUuid.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner == null ? null : owner.trim();
    }

    public Integer getModelId() {
        return modelId;
    }

    public void setModelId(Integer modelId) {
        this.modelId = modelId;
    }

	public String getDeviceStatus() {
		return deviceStatus;
	}

	public void setDeviceStatus(String deviceStatus) {
		this.deviceStatus = deviceStatus;
	}
	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getUpdateId() {
		return updateId;
	}

	public void setUpdateId(Integer updateId) {
		this.updateId = updateId;
	}

	public Date getLastTimeDeviceReporting() {
		return lastTimeDeviceReporting;
	}

	public void setLastTimeDeviceReporting(Date lastTimeDeviceReporting) {
		this.lastTimeDeviceReporting = lastTimeDeviceReporting;
	}

	public String getBluetoothVestionCode() {
		return bluetoothVestionCode;
	}

	public void setBluetoothVestionCode(String bluetoothVestionCode) {
		this.bluetoothVestionCode = bluetoothVestionCode;
	}

	public String getBoxSoftType() {
		return boxSoftType;
	}

	public void setBoxSoftType(String boxSoftType) {
		this.boxSoftType = boxSoftType;
	}

	public String getBoxVersionCode() {
		return boxVersionCode;
	}

	public void setBoxVersionCode(String boxVersionCode) {
		this.boxVersionCode = boxVersionCode;
	}

	public String getSoftType() {
		return softType;
	}

	public void setSoftType(String softType) {
		this.softType = softType;
	}

	public String getVersionCode() {
		return versionCode;
	}

	public void setVersionCode(String versionCode) {
		this.versionCode = versionCode;
	}

	public String getVersionName() {
		return versionName;
	}

	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}

	public String getIsNav() {
		return isNav;
	}

	public void setIsNav(String isNav) {
		this.isNav = isNav;
	}

	public String getIsCall() {
		return isCall;
	}

	public void setIsCall(String isCall) {
		this.isCall = isCall;
	}

	public String getIsTripRecord() {
		return isTripRecord;
	}

	public void setIsTripRecord(String isTripRecord) {
		this.isTripRecord = isTripRecord;
	}

	public String getEngineno() {
		return engineno;
	}

	public void setEngineno(String engineno) {
		this.engineno = engineno;
	}

	public String getClassno() {
		return classno;
	}

	public void setClassno(String classno) {
		this.classno = classno;
	}

	public String getCarImgUri() {
		return carImgUri;
	}

	public void setCarImgUri(String carImgUri) {
		this.carImgUri = carImgUri;
	}

	public Date getAnnualInspectionTime() {
		return annualInspectionTime;
	}

	public void setAnnualInspectionTime(Date annualInspectionTime) {
		this.annualInspectionTime = annualInspectionTime;
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

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

    public String getTboxImei() {
        return tboxImei;
    }

    public void setTboxImei(String tboxImei) {
        this.tboxImei = tboxImei;
    }

    public Integer getTboxVersion() {
        return tboxVersion;
    }

    public void setTboxVersion(Integer tboxVersion) {
        this.tboxVersion = tboxVersion;
    }
}