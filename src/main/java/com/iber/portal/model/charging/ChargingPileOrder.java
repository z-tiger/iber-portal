package com.iber.portal.model.charging;

import java.util.Date;

public class ChargingPileOrder {
    private Integer id;

    private String orderId;

    private String cityCode;

    private Integer memberId;

    private Integer pileId;

    private Integer parkId;

    private String chargingType;

    private String chargingMode;

    private Double electricAmount;

    private Integer chargingTime;

    private Date beginTime;

    private Date endTime;

    private Integer orderMoney;

    private String couponNo;

    private Integer couponBalance;

    private Integer privilegeMoney;

    private Integer payMoney;

    private String payType;

    private String dimensionInfo;

    private String status;

    private Double beforeElectric;

    private Double endElectric;

    private Date createTime;
    
    private String thresholdValue ;

    private String memberName;
    
    private String cityName;
    
    private String parkName;
    
    private String pileType;

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

    public Integer getPileId() {
        return pileId;
    }

    public void setPileId(Integer pileId) {
        this.pileId = pileId;
    }

    public Integer getParkId() {
        return parkId;
    }

    public void setParkId(Integer parkId) {
        this.parkId = parkId;
    }

    public String getChargingType() {
        return chargingType;
    }

    public void setChargingType(String chargingType) {
        this.chargingType = chargingType == null ? null : chargingType.trim();
    }

    public String getChargingMode() {
        return chargingMode;
    }

    public void setChargingMode(String chargingMode) {
        this.chargingMode = chargingMode == null ? null : chargingMode.trim();
    }

    public Double getElectricAmount() {
        return electricAmount;
    }

    public void setElectricAmount(Double electricAmount) {
        this.electricAmount = electricAmount;
    }

    public Integer getChargingTime() {
        return chargingTime;
    }

    public void setChargingTime(Integer chargingTime) {
        this.chargingTime = chargingTime;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getOrderMoney() {
        return orderMoney;
    }

    public void setOrderMoney(Integer orderMoney) {
        this.orderMoney = orderMoney;
    }

    public String getCouponNo() {
        return couponNo;
    }

    public void setCouponNo(String couponNo) {
        this.couponNo = couponNo == null ? null : couponNo.trim();
    }

    public Integer getCouponBalance() {
        return couponBalance;
    }

    public void setCouponBalance(Integer couponBalance) {
        this.couponBalance = couponBalance;
    }

    public Integer getPrivilegeMoney() {
        return privilegeMoney;
    }

    public void setPrivilegeMoney(Integer privilegeMoney) {
        this.privilegeMoney = privilegeMoney;
    }

    public Integer getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(Integer payMoney) {
        this.payMoney = payMoney;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType == null ? null : payType.trim();
    }

    public String getDimensionInfo() {
        return dimensionInfo;
    }

    public void setDimensionInfo(String dimensionInfo) {
        this.dimensionInfo = dimensionInfo == null ? null : dimensionInfo.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Double getBeforeElectric() {
        return beforeElectric;
    }

    public void setBeforeElectric(Double beforeElectric) {
        this.beforeElectric = beforeElectric;
    }

    public Double getEndElectric() {
        return endElectric;
    }

    public void setEndElectric(Double endElectric) {
        this.endElectric = endElectric;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

	public String getThresholdValue() {
		return thresholdValue;
	}

	public void setThresholdValue(String thresholdValue) {
		this.thresholdValue = thresholdValue;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getParkName() {
		return parkName;
	}

	public void setParkName(String parkName) {
		this.parkName = parkName;
	}

	public String getPileType() {
		return pileType;
	}

	public void setPileType(String pileType) {
		this.pileType = pileType;
	}
    
}