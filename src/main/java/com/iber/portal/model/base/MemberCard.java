package com.iber.portal.model.base;

import java.util.Date;

public class MemberCard {
    private Integer id;

    private Integer memberId;

    private Integer money;

    private Date lastDateTime;

    private Integer lastMoney;

    private Date createTime;

    private Date lastChargeTime;

    private Integer totalChargeMoney;

    private Integer totalConsumeMoney;

    private Date lastComsumeTime;

    private Integer totalRefundMoney;

    private Date lastRefundTime;

    private Integer deposit;

    private Date depositDateTime;

    private Integer quota;

    private Integer quotaMonth;

    private Date quotaDateTime;

    private Integer quotaUseMoney;

    private Integer integral;
    
    private String blockingReason;//会员资金冻结原因
    
    private Integer contributedVal;//贡献值
    
    private String remark;

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public Date getLastDateTime() {
        return lastDateTime;
    }

    public void setLastDateTime(Date lastDateTime) {
        this.lastDateTime = lastDateTime;
    }

    public Integer getLastMoney() {
        return lastMoney;
    }

    public void setLastMoney(Integer lastMoney) {
        this.lastMoney = lastMoney;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastChargeTime() {
        return lastChargeTime;
    }

    public void setLastChargeTime(Date lastChargeTime) {
        this.lastChargeTime = lastChargeTime;
    }

    public Integer getTotalChargeMoney() {
        return totalChargeMoney;
    }

    public void setTotalChargeMoney(Integer totalChargeMoney) {
        this.totalChargeMoney = totalChargeMoney;
    }

    public Integer getTotalConsumeMoney() {
        return totalConsumeMoney;
    }

    public void setTotalConsumeMoney(Integer totalConsumeMoney) {
        this.totalConsumeMoney = totalConsumeMoney;
    }

    public Date getLastComsumeTime() {
        return lastComsumeTime;
    }

    public void setLastComsumeTime(Date lastComsumeTime) {
        this.lastComsumeTime = lastComsumeTime;
    }

    public Integer getTotalRefundMoney() {
        return totalRefundMoney;
    }

    public void setTotalRefundMoney(Integer totalRefundMoney) {
        this.totalRefundMoney = totalRefundMoney;
    }

    public Date getLastRefundTime() {
        return lastRefundTime;
    }

    public void setLastRefundTime(Date lastRefundTime) {
        this.lastRefundTime = lastRefundTime;
    }

    public Integer getDeposit() {
        return deposit;
    }

    public void setDeposit(Integer deposit) {
        this.deposit = deposit;
    }

    public Date getDepositDateTime() {
        return depositDateTime;
    }

    public void setDepositDateTime(Date depositDateTime) {
        this.depositDateTime = depositDateTime;
    }

    public Integer getQuota() {
        return quota;
    }

    public void setQuota(Integer quota) {
        this.quota = quota;
    }

    public Integer getQuotaMonth() {
        return quotaMonth;
    }

    public void setQuotaMonth(Integer quotaMonth) {
        this.quotaMonth = quotaMonth;
    }

    public Date getQuotaDateTime() {
        return quotaDateTime;
    }

    public void setQuotaDateTime(Date quotaDateTime) {
        this.quotaDateTime = quotaDateTime;
    }

    public Integer getQuotaUseMoney() {
        return quotaUseMoney;
    }

    public void setQuotaUseMoney(Integer quotaUseMoney) {
        this.quotaUseMoney = quotaUseMoney;
    }

    public Integer getIntegral() {
        return integral;
    }

    public void setIntegral(Integer integral) {
        this.integral = integral;
    }

	public String getBlockingReason() {
		return blockingReason;
	}

	public void setBlockingReason(String blockingReason) {
		this.blockingReason = blockingReason;
	}

	public Integer getContributedVal() {
		return contributedVal;
	}

	public void setContributedVal(Integer contributedVal) {
		this.contributedVal = contributedVal;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}