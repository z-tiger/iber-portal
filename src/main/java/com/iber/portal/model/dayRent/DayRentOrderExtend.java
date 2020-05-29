package com.iber.portal.model.dayRent;

import java.util.Date;

public class DayRentOrderExtend {
    private Integer id;

    private Integer xDId;

    private String orderId;

    private String type;

    private Integer orderMoney;

    private String coupon;

    private Integer couponBalance;

    private Integer freeMoney;

    private Integer payMoney;

    private String payType;

    private Integer rentCarMoney;

    private Integer insuranceMoney;

    private Integer procedureMoney;

    private Integer freeCompensateMoney;

    private Integer remoteMoney;

    private Integer rentTimeout;

    private String payStatus;

    private Integer refundMoney;

    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getxDId() {
        return xDId;
    }

    public void setxDId(Integer xDId) {
        this.xDId = xDId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public Integer getOrderMoney() {
        return orderMoney;
    }

    public void setOrderMoney(Integer orderMoney) {
        this.orderMoney = orderMoney;
    }

    public String getCoupon() {
        return coupon;
    }

    public void setCoupon(String coupon) {
        this.coupon = coupon == null ? null : coupon.trim();
    }

    public Integer getCouponBalance() {
        return couponBalance;
    }

    public void setCouponBalance(Integer couponBalance) {
        this.couponBalance = couponBalance;
    }

    public Integer getFreeMoney() {
        return freeMoney;
    }

    public void setFreeMoney(Integer freeMoney) {
        this.freeMoney = freeMoney;
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

    public Integer getRentCarMoney() {
        return rentCarMoney;
    }

    public void setRentCarMoney(Integer rentCarMoney) {
        this.rentCarMoney = rentCarMoney;
    }

    public Integer getInsuranceMoney() {
        return insuranceMoney;
    }

    public void setInsuranceMoney(Integer insuranceMoney) {
        this.insuranceMoney = insuranceMoney;
    }

    public Integer getProcedureMoney() {
        return procedureMoney;
    }

    public void setProcedureMoney(Integer procedureMoney) {
        this.procedureMoney = procedureMoney;
    }

    public Integer getFreeCompensateMoney() {
        return freeCompensateMoney;
    }

    public void setFreeCompensateMoney(Integer freeCompensateMoney) {
        this.freeCompensateMoney = freeCompensateMoney;
    }

    public Integer getRemoteMoney() {
        return remoteMoney;
    }

    public void setRemoteMoney(Integer remoteMoney) {
        this.remoteMoney = remoteMoney;
    }

    public Integer getRentTimeout() {
        return rentTimeout;
    }

    public void setRentTimeout(Integer rentTimeout) {
        this.rentTimeout = rentTimeout;
    }

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus == null ? null : payStatus.trim();
    }

    public Integer getRefundMoney() {
        return refundMoney;
    }

    public void setRefundMoney(Integer refundMoney) {
        this.refundMoney = refundMoney;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}