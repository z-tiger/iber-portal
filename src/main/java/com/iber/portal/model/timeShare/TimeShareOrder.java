package com.iber.portal.model.timeShare;

import java.util.Date;

public class TimeShareOrder {
    private Integer id;

    private String orderId;

    private String memberId;

    private Integer parkId;

    private String lpn;

    private Double totalRunMileage;

    private Date orderTime;

    private Date beginTime;

    private Date endTime;

    private String status;

    private String cityCode;

    private String isEnterpriseUseCar;

    private String orderType;

    private Integer enterpriseId;

    private Integer returnParkId;

    private String isFreeCompensate;

    private Date lockCarTime; // 锁车时间

    private Integer isLockCar; // 是否锁车 0 没有 1 锁车
    
    private Integer isLockTwoWarn; // 是否发生了锁车二次提醒  0 没有 1 锁车

    private Date oneWarnTime; // 一级预警时间

    private Integer isOneWarn; // 是否发送一级预警 0 没有 1 发送

    private Date twoWarnTime; // 二级预警时间

    private Integer isTwoWarn; // 是否发送二级预警 0 没有 1 发送
    
    private Integer timeRate;

    public Integer getTimeRate() {
		return timeRate;
	}

	public void setTimeRate(Integer timeRate) {
		this.timeRate = timeRate;
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

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId == null ? null : memberId.trim();
    }

    public Integer getParkId() {
        return parkId;
    }

    public void setParkId(Integer parkId) {
        this.parkId = parkId;
    }

    public String getLpn() {
        return lpn;
    }

    public void setLpn(String lpn) {
        this.lpn = lpn == null ? null : lpn.trim();
    }

    public Double getTotalRunMileage() {
        return totalRunMileage;
    }

    public void setTotalRunMileage(Double totalRunMileage) {
        this.totalRunMileage = totalRunMileage;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode == null ? null : cityCode.trim();
    }

    public String getIsEnterpriseUseCar() {
        return isEnterpriseUseCar;
    }

    public void setIsEnterpriseUseCar(String isEnterpriseUseCar) {
        this.isEnterpriseUseCar = isEnterpriseUseCar == null ? null : isEnterpriseUseCar.trim();
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType == null ? null : orderType.trim();
    }

    public Integer getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(Integer enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public Integer getReturnParkId() {
        return returnParkId;
    }

    public void setReturnParkId(Integer returnParkId) {
        this.returnParkId = returnParkId;
    }

    public String getIsFreeCompensate() {
        return isFreeCompensate;
    }

    public void setIsFreeCompensate(String isFreeCompensate) {
        this.isFreeCompensate = isFreeCompensate;
    }

    public Date getLockCarTime() {
        return lockCarTime;
    }

    public void setLockCarTime(Date lockCarTime) {
        this.lockCarTime = lockCarTime;
    }

    public Integer getIsLockCar() {
        return isLockCar;
    }

    public void setIsLockCar(Integer isLockCar) {
        this.isLockCar = isLockCar;
    }

    public Date getOneWarnTime() {
        return oneWarnTime;
    }

    public void setOneWarnTime(Date oneWarnTime) {
        this.oneWarnTime = oneWarnTime;
    }

    public Integer getIsOneWarn() {
        return isOneWarn;
    }

    public void setIsOneWarn(Integer isOneWarn) {
        this.isOneWarn = isOneWarn;
    }

    public Date getTwoWarnTime() {
        return twoWarnTime;
    }

    public void setTwoWarnTime(Date twoWarnTime) {
        this.twoWarnTime = twoWarnTime;
    }

    public Integer getIsTwoWarn() {
        return isTwoWarn;
    }

    public void setIsTwoWarn(Integer isTwoWarn) {
        this.isTwoWarn = isTwoWarn;
    }

	public Integer getIsLockTwoWarn() {
		return isLockTwoWarn;
	}

	public void setIsLockTwoWarn(Integer isLockTwoWarn) {
		this.isLockTwoWarn = isLockTwoWarn;
	}
}