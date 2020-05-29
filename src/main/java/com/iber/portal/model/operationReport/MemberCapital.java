package com.iber.portal.model.operationReport;

import java.util.Date;

public class MemberCapital {

    private Integer memberId;

    private String money;

	private Date lastDateTime;

	private Integer lastMoney;

    private Date createTime;

    private Date lastChargeTime;

    private String totalChargeMoney;  
    
    private String totalConsumeMoney;
    
    private Date lastComsumeTime;

    private String totalRefundMoney;

    private Date lastRefundTime;
    
    private String deposit;

    private Date depositDateTime;

    private Integer quota;

    private Integer quotaMonth;

    private Date quotaDateTime;

    private Integer quotaUseMoney;

    private Integer integral;
    
    private Float totalMoney;
    
    private String name;
    
	private String cityName;
    
	private String phone;
	
	private String memberLevel;
	
	private String totalCouponMoney;
	
	private Double depositSum;//会员押金总合计
	private Double moneySum;//会员余额总合计
	private Double totalChargeMoneySum;//总充值金额总合计
	private Double totalConsumeMoneySum;//总消费金额-支付总合计
	private Double totalCouponMoneySum;//总消费金额-优惠券总合计
	private Double totalRefundMoneySum;//总退款金额总合计
	private Integer contributedVal;//贡献值
	private Integer levelCode; //等级编码
	
	public Integer getLevelCode() {
		return levelCode;
	}

	public void setLevelCode(Integer levelCode) {
		this.levelCode = levelCode;
	}

	public Integer getContributedVal() {
		return contributedVal;
	}

	public void setContributedVal(Integer contributedVal) {
		this.contributedVal = contributedVal;
	}

	public Double getDepositSum() {
		return depositSum;
	}

	public void setDepositSum(Double depositSum) {
		this.depositSum = depositSum;
	}

	public Double getMoneySum() {
		return moneySum;
	}

	public void setMoneySum(Double moneySum) {
		this.moneySum = moneySum;
	}

	public Double getTotalChargeMoneySum() {
		return totalChargeMoneySum;
	}

	public void setTotalChargeMoneySum(Double totalChargeMoneySum) {
		this.totalChargeMoneySum = totalChargeMoneySum;
	}

	public Double getTotalConsumeMoneySum() {
		return totalConsumeMoneySum;
	}

	public void setTotalConsumeMoneySum(Double totalConsumeMoneySum) {
		this.totalConsumeMoneySum = totalConsumeMoneySum;
	}

	public Double getTotalCouponMoneySum() {
		return totalCouponMoneySum;
	}

	public void setTotalCouponMoneySum(Double totalCouponMoneySum) {
		this.totalCouponMoneySum = totalCouponMoneySum;
	}

	public Double getTotalRefundMoneySum() {
		return totalRefundMoneySum;
	}

	public void setTotalRefundMoneySum(Double totalRefundMoneySum) {
		this.totalRefundMoneySum = totalRefundMoneySum;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}
	
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}


    public Float getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(Float totalMoney) {
		this.totalMoney = totalMoney;
	}


    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
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



    public Date getLastComsumeTime() {
        return lastComsumeTime;
    }

    public void setLastComsumeTime(Date lastComsumeTime) {
        this.lastComsumeTime = lastComsumeTime;
    }



    public Date getLastRefundTime() {
        return lastRefundTime;
    }

    public void setLastRefundTime(Date lastRefundTime) {
        this.lastRefundTime = lastRefundTime;
    }


	public String getTotalChargeMoney() {
		return totalChargeMoney;
	}

	public void setTotalChargeMoney(String totalChargeMoney) {
		this.totalChargeMoney = totalChargeMoney;
	}

	public String getTotalConsumeMoney() {
		return totalConsumeMoney;
	}

	public void setTotalConsumeMoney(String totalConsumeMoney) {
		this.totalConsumeMoney = totalConsumeMoney;
	}

	public String getTotalRefundMoney() {
		return totalRefundMoney;
	}

	public void setTotalRefundMoney(String totalRefundMoney) {
		this.totalRefundMoney = totalRefundMoney;
	}

	public String getDeposit() {
		return deposit;
	}

	public void setDeposit(String deposit) {
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

	public String getMemberLevel() {
		return memberLevel;
	}

	public void setMemberLevel(String memberLevel) {
		this.memberLevel = memberLevel;
	}

	public String getTotalCouponMoney() {
		return totalCouponMoney;
	}

	public void setTotalCouponMoney(String totalCouponMoney) {
		this.totalCouponMoney = totalCouponMoney;
	}
	
	
}