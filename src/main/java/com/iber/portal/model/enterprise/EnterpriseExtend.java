package com.iber.portal.model.enterprise;

public class EnterpriseExtend {
    private Integer id;

    private String checkStatus;

    private String checkDescription;

    private String useCarType;

    private String useCarDescription;

    private String upCarAddress;

    private String downCarAddress;

    private String orderId;

    private String upCarGps;

    private String downCarGps;

    private String memberName;

    private String startTime;

    private String endTime;

    private Integer enterpriseId;

    private Integer amount;

    private String phone;

    private Integer enterpriseLevelId;

    private String payType;

    private String lpn;

    private Integer memberId;

    private Double mileage;
    
    private String cityCode;
    
    private String cityName;
    
    private String enterpriseName;
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(String checkStatus) {
        this.checkStatus = checkStatus == null ? null : checkStatus.trim();
    }

    public String getCheckDescription() {
        return checkDescription;
    }

    public void setCheckDescription(String checkDescription) {
        this.checkDescription = checkDescription == null ? null : checkDescription.trim();
    }

    public String getUseCarType() {
        return useCarType;
    }

    public void setUseCarType(String useCarType) {
        this.useCarType = useCarType == null ? null : useCarType.trim();
    }

    public String getUseCarDescription() {
        return useCarDescription;
    }

    public void setUseCarDescription(String useCarDescription) {
        this.useCarDescription = useCarDescription == null ? null : useCarDescription.trim();
    }

    public String getUpCarAddress() {
        return upCarAddress;
    }

    public void setUpCarAddress(String upCarAddress) {
        this.upCarAddress = upCarAddress == null ? null : upCarAddress.trim();
    }

    public String getDownCarAddress() {
        return downCarAddress;
    }

    public void setDownCarAddress(String downCarAddress) {
        this.downCarAddress = downCarAddress == null ? null : downCarAddress.trim();
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    public String getUpCarGps() {
        return upCarGps;
    }

    public void setUpCarGps(String upCarGps) {
        this.upCarGps = upCarGps == null ? null : upCarGps.trim();
    }

    public String getDownCarGps() {
        return downCarGps;
    }

    public void setDownCarGps(String downCarGps) {
        this.downCarGps = downCarGps == null ? null : downCarGps.trim();
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName == null ? null : memberName.trim();
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime == null ? null : startTime.trim();
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime == null ? null : endTime.trim();
    }

    public Integer getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(Integer enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public Integer getEnterpriseLevelId() {
        return enterpriseLevelId;
    }

    public void setEnterpriseLevelId(Integer enterpriseLevelId) {
        this.enterpriseLevelId = enterpriseLevelId;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType == null ? null : payType.trim();
    }

    public String getLpn() {
        return lpn;
    }

    public void setLpn(String lpn) {
        this.lpn = lpn == null ? null : lpn.trim();
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public Double getMileage() {
        return mileage;
    }

    public void setMileage(Double mileage) {
        this.mileage = mileage;
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

	public String getEnterpriseName() {
		return enterpriseName;
	}

	public void setEnterpriseName(String enterpriseName) {
		this.enterpriseName = enterpriseName;
	}
    
}