package com.iber.portal.model.dayRent;

import java.util.Date;

public class DayRentOrder {
    private Integer id;

    private String orderId;

    private String memberId;

    private String cityCode;

    private Integer modelId;

    private String lpn;

    private Date carBindTime;

    private Date appointmenTakeCarTimet;

    private Integer appointmenTakeParkId;

    private Date appointmenReturnCarTime;

    private Integer appointmenReturnCarParkId;

    private Date delayReturnCarTime;

    private Date actualTakenCarTime;

    private Integer actualTakenCarParkId;

    private Date actualReturnCarTime;

    private Integer actualReturnCarParkId;

    private String orderStatus;

    private Integer rentTimeout;

    private Date createTime;
    
    private String payStatus;
    
    private String memberName;
    
    private String cityName;

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

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode == null ? null : cityCode.trim();
    }

    public Integer getModelId() {
        return modelId;
    }

    public void setModelId(Integer modelId) {
        this.modelId = modelId;
    }

    public String getLpn() {
        return lpn;
    }

    public void setLpn(String lpn) {
        this.lpn = lpn == null ? null : lpn.trim();
    }

    public Date getCarBindTime() {
        return carBindTime;
    }

    public void setCarBindTime(Date carBindTime) {
        this.carBindTime = carBindTime;
    }

    public Date getAppointmenTakeCarTimet() {
        return appointmenTakeCarTimet;
    }

    public void setAppointmenTakeCarTimet(Date appointmenTakeCarTimet) {
        this.appointmenTakeCarTimet = appointmenTakeCarTimet;
    }

    public Integer getAppointmenTakeParkId() {
        return appointmenTakeParkId;
    }

    public void setAppointmenTakeParkId(Integer appointmenTakeParkId) {
        this.appointmenTakeParkId = appointmenTakeParkId;
    }

    public Date getAppointmenReturnCarTime() {
        return appointmenReturnCarTime;
    }

    public void setAppointmenReturnCarTime(Date appointmenReturnCarTime) {
        this.appointmenReturnCarTime = appointmenReturnCarTime;
    }

    public Integer getAppointmenReturnCarParkId() {
        return appointmenReturnCarParkId;
    }

    public void setAppointmenReturnCarParkId(Integer appointmenReturnCarParkId) {
        this.appointmenReturnCarParkId = appointmenReturnCarParkId;
    }

    public Date getDelayReturnCarTime() {
        return delayReturnCarTime;
    }

    public void setDelayReturnCarTime(Date delayReturnCarTime) {
        this.delayReturnCarTime = delayReturnCarTime;
    }

    public Date getActualTakenCarTime() {
        return actualTakenCarTime;
    }

    public void setActualTakenCarTime(Date actualTakenCarTime) {
        this.actualTakenCarTime = actualTakenCarTime;
    }

    public Integer getActualTakenCarParkId() {
        return actualTakenCarParkId;
    }

    public void setActualTakenCarParkId(Integer actualTakenCarParkId) {
        this.actualTakenCarParkId = actualTakenCarParkId;
    }

    public Date getActualReturnCarTime() {
        return actualReturnCarTime;
    }

    public void setActualReturnCarTime(Date actualReturnCarTime) {
        this.actualReturnCarTime = actualReturnCarTime;
    }

    public Integer getActualReturnCarParkId() {
        return actualReturnCarParkId;
    }

    public void setActualReturnCarParkId(Integer actualReturnCarParkId) {
        this.actualReturnCarParkId = actualReturnCarParkId;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus == null ? null : orderStatus.trim();
    }

    public Integer getRentTimeout() {
        return rentTimeout;
    }

    public void setRentTimeout(Integer rentTimeout) {
        this.rentTimeout = rentTimeout;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

	public String getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
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
	
}