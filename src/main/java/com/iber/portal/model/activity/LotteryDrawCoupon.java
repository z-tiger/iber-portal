package com.iber.portal.model.activity;

import java.util.Date;

public class LotteryDrawCoupon {
    private Integer id;

    private Integer couponAccount;

    private Integer lotteryDrawItemId;

    private Integer balance;

    private Double minUseValue;

    private Integer deadline;

    private Integer useType;

    private Date createTime;

    private Double minBalance;

    private Double maxBalance;

    private Double maxDeductionValue;

    private String editor;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCouponAccount() {
        return couponAccount;
    }

    public void setCouponAccount(Integer couponAccount) {
        this.couponAccount = couponAccount;
    }

    public Integer getLotteryDrawItemId() {
        return lotteryDrawItemId;
    }

    public void setLotteryDrawItemId(Integer lotteryDrawItemId) {
        this.lotteryDrawItemId = lotteryDrawItemId;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public Double getMinUseValue() {
        return minUseValue;
    }

    public void setMinUseValue(Double minUseValue) {
        this.minUseValue = minUseValue;
    }

    public Integer getDeadline() {
        return deadline;
    }

    public void setDeadline(Integer deadline) {
        this.deadline = deadline;
    }

    public Integer getUseType() {
        return useType;
    }

    public void setUseType(Integer useType) {
        this.useType = useType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Double getMinBalance() {
        return minBalance;
    }

    public void setMinBalance(Double minBalance) {
        this.minBalance = minBalance;
    }

    public Double getMaxBalance() {
        return maxBalance;
    }

    public void setMaxBalance(Double maxBalance) {
        this.maxBalance = maxBalance;
    }

    public Double getMaxDeductionValue() {
        return maxDeductionValue;
    }

    public void setMaxDeductionValue(Double maxDeductionValue) {
        this.maxDeductionValue = maxDeductionValue;
    }

    public String getEditor() {
        return editor;
    }

    public void setEditor(String editor) {
        this.editor = editor;
    }
}