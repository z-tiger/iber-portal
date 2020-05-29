package com.iber.portal.model.longRent;

import java.util.Date;

public class LongRentOrder {
    private Integer id;

    private String orderId;

    private Integer memberId;

    private String cityCode;

    private Date appointmenTakeCarTime;

    private Integer appointmenTakeParkId;

    private Date appointmenReturnCarTime;

    private Integer appointmenReturnCarParkId;

    private String appointmenReturnDate;

    private String orderStatus;

    private String memberName;

    private String memberPhone;

    private Integer appointmenCarNum;

    private Integer totalMny;

    private Date createTime;

    private String createBy;

    private Date lastModifyTime;

    private String lastModifyBy;

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

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode == null ? null : cityCode.trim();
    }

    public Date getAppointmenTakeCarTime() {
        return appointmenTakeCarTime;
    }

    public void setAppointmenTakeCarTime(Date appointmenTakeCarTime) {
        this.appointmenTakeCarTime = appointmenTakeCarTime;
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

    public String getAppointmenReturnDate() {
        return appointmenReturnDate;
    }

    public void setAppointmenReturnDate(String appointmenReturnDate) {
        this.appointmenReturnDate = appointmenReturnDate == null ? null : appointmenReturnDate.trim();
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus == null ? null : orderStatus.trim();
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName == null ? null : memberName.trim();
    }

    public String getMemberPhone() {
        return memberPhone;
    }

    public void setMemberPhone(String memberPhone) {
        this.memberPhone = memberPhone == null ? null : memberPhone.trim();
    }

    public Integer getAppointmenCarNum() {
        return appointmenCarNum;
    }

    public void setAppointmenCarNum(Integer appointmenCarNum) {
        this.appointmenCarNum = appointmenCarNum;
    }

    public Integer getTotalMny() {
        return totalMny;
    }

    public void setTotalMny(Integer totalMny) {
        this.totalMny = totalMny;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy == null ? null : createBy.trim();
    }

    public Date getLastModifyTime() {
        return lastModifyTime;
    }

    public void setLastModifyTime(Date lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }

    public String getLastModifyBy() {
        return lastModifyBy;
    }

    public void setLastModifyBy(String lastModifyBy) {
        this.lastModifyBy = lastModifyBy == null ? null : lastModifyBy.trim();
    }
}