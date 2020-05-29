package com.iber.portal.model.overall;

import java.util.Date;

public class OverallMember {
    private Integer id;

    private Integer userNum;

    private Integer registerNum;

    private Integer auditNum;

    private Integer chargeMemberNum;

    private Integer deposit;

    private Integer balance;

    private Integer actualIncome;
    
    private Integer income;

    private Integer onlineMemberNum;

    private Integer onlineMoney;

    private Integer refundMemberNum;

    private Integer refundMoney;

    private Integer owingMemberNum;

    private Integer owingMoney;

    private Integer owing30MemberNum;

    private Integer owing30Money;

    private String channel;

    private String memberType;

    private String cityCode;

    private Date dateTime;

    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserNum() {
        return userNum;
    }

    public void setUserNum(Integer userNum) {
        this.userNum = userNum;
    }

    public Integer getRegisterNum() {
        return registerNum;
    }

    public void setRegisterNum(Integer registerNum) {
        this.registerNum = registerNum;
    }

    public Integer getAuditNum() {
        return auditNum;
    }

    public void setAuditNum(Integer auditNum) {
        this.auditNum = auditNum;
    }

    public Integer getChargeMemberNum() {
        return chargeMemberNum;
    }

    public void setChargeMemberNum(Integer chargeMemberNum) {
        this.chargeMemberNum = chargeMemberNum;
    }

    public Integer getDeposit() {
        return deposit;
    }

    public void setDeposit(Integer deposit) {
        this.deposit = deposit;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public Integer getActualIncome() {
		return actualIncome;
	}

	public void setActualIncome(Integer actualIncome) {
		this.actualIncome = actualIncome;
	}

	public Integer getIncome() {
        return income;
    }

    public void setIncome(Integer income) {
        this.income = income;
    }

    public Integer getOnlineMemberNum() {
        return onlineMemberNum;
    }

    public void setOnlineMemberNum(Integer onlineMemberNum) {
        this.onlineMemberNum = onlineMemberNum;
    }

    public Integer getOnlineMoney() {
        return onlineMoney;
    }

    public void setOnlineMoney(Integer onlineMoney) {
        this.onlineMoney = onlineMoney;
    }

    public Integer getRefundMemberNum() {
        return refundMemberNum;
    }

    public void setRefundMemberNum(Integer refundMemberNum) {
        this.refundMemberNum = refundMemberNum;
    }

    public Integer getRefundMoney() {
        return refundMoney;
    }

    public void setRefundMoney(Integer refundMoney) {
        this.refundMoney = refundMoney;
    }

    public Integer getOwingMemberNum() {
        return owingMemberNum;
    }

    public void setOwingMemberNum(Integer owingMemberNum) {
        this.owingMemberNum = owingMemberNum;
    }

    public Integer getOwingMoney() {
        return owingMoney;
    }

    public void setOwingMoney(Integer owingMoney) {
        this.owingMoney = owingMoney;
    }

    public Integer getOwing30MemberNum() {
        return owing30MemberNum;
    }

    public void setOwing30MemberNum(Integer owing30MemberNum) {
        this.owing30MemberNum = owing30MemberNum;
    }

    public Integer getOwing30Money() {
        return owing30Money;
    }

    public void setOwing30Money(Integer owing30Money) {
        this.owing30Money = owing30Money;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel == null ? null : channel.trim();
    }

    public String getMemberType() {
        return memberType;
    }

    public void setMemberType(String memberType) {
        this.memberType = memberType == null ? null : memberType.trim();
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode == null ? null : cityCode.trim();
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}