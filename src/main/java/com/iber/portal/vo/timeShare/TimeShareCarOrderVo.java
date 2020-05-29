package com.iber.portal.vo.timeShare;

import java.util.Date;

public class TimeShareCarOrderVo {
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

    private String name ;
    
    private String freeCompensationMoney ;
    
    private String userType;
    
    public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFreeCompensationMoney() {
		return freeCompensationMoney;
	}

	public void setFreeCompensationMoney(String freeCompensationMoney) {
		this.freeCompensationMoney = freeCompensationMoney;
	}
    
}