package com.iber.portal.model.longRent;

import java.util.Date;

public class LongRentOrderLog {
    private Integer id;

    private String orderId;

    private Date actionStartTime;

    private Date actionEndTime;

    private Integer actionParkId;

    private String actionType;

    private Integer actionMny;

    private String termDate;

    private Integer discountMny;

    private Integer payMny;

    private String payType;

    private String traneNo;

    private Integer depositMny;

    private Integer vehicleMny;

    private Integer premiumMny;

    private Integer nonDeductibleMny;

    private Integer counterMny;

    private String timeOutTime;

    private Integer timeOutMny;

    private Integer differentPlacesMny;

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

    public Date getActionStartTime() {
        return actionStartTime;
    }

    public void setActionStartTime(Date actionStartTime) {
        this.actionStartTime = actionStartTime;
    }

    public Date getActionEndTime() {
        return actionEndTime;
    }

    public void setActionEndTime(Date actionEndTime) {
        this.actionEndTime = actionEndTime;
    }

    public Integer getActionParkId() {
        return actionParkId;
    }

    public void setActionParkId(Integer actionParkId) {
        this.actionParkId = actionParkId;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType == null ? null : actionType.trim();
    }

    public Integer getActionMny() {
        return actionMny;
    }

    public void setActionMny(Integer actionMny) {
        this.actionMny = actionMny;
    }

    public String getTermDate() {
        return termDate;
    }

    public void setTermDate(String termDate) {
        this.termDate = termDate == null ? null : termDate.trim();
    }

    public Integer getDiscountMny() {
        return discountMny;
    }

    public void setDiscountMny(Integer discountMny) {
        this.discountMny = discountMny;
    }

    public Integer getPayMny() {
        return payMny;
    }

    public void setPayMny(Integer payMny) {
        this.payMny = payMny;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType == null ? null : payType.trim();
    }

    public String getTraneNo() {
        return traneNo;
    }

    public void setTraneNo(String traneNo) {
        this.traneNo = traneNo == null ? null : traneNo.trim();
    }

    public Integer getDepositMny() {
        return depositMny;
    }

    public void setDepositMny(Integer depositMny) {
        this.depositMny = depositMny;
    }

    public Integer getVehicleMny() {
        return vehicleMny;
    }

    public void setVehicleMny(Integer vehicleMny) {
        this.vehicleMny = vehicleMny;
    }

    public Integer getPremiumMny() {
        return premiumMny;
    }

    public void setPremiumMny(Integer premiumMny) {
        this.premiumMny = premiumMny;
    }

    public Integer getNonDeductibleMny() {
        return nonDeductibleMny;
    }

    public void setNonDeductibleMny(Integer nonDeductibleMny) {
        this.nonDeductibleMny = nonDeductibleMny;
    }

    public Integer getCounterMny() {
        return counterMny;
    }

    public void setCounterMny(Integer counterMny) {
        this.counterMny = counterMny;
    }

    public String getTimeOutTime() {
        return timeOutTime;
    }

    public void setTimeOutTime(String timeOutTime) {
        this.timeOutTime = timeOutTime == null ? null : timeOutTime.trim();
    }

    public Integer getTimeOutMny() {
        return timeOutMny;
    }

    public void setTimeOutMny(Integer timeOutMny) {
        this.timeOutMny = timeOutMny;
    }

    public Integer getDifferentPlacesMny() {
        return differentPlacesMny;
    }

    public void setDifferentPlacesMny(Integer differentPlacesMny) {
        this.differentPlacesMny = differentPlacesMny;
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