package com.iber.portal.vo.enterprise;

import com.iber.portal.model.base.Member;

import java.util.Date;

public class EnterpriseMemberVo extends Member{
	private int money; 
//	private int deposit;
	private int integral; 
	private int quota; 
	private int quotaUseMoney; 
	private int quotaMonth; 
	private Date quotaDateTime;

    private Integer totalRecharge;
    private Integer accountMoney;

    private Integer memberAllAvail;
    private Integer memberAvail;

    public Integer getMemberAllAvail() {
        return memberAllAvail;
    }

    public void setMemberAllAvail(Integer memberAllAvail) {
        this.memberAllAvail = memberAllAvail;
    }

    public Integer getMemberAvail() {
        return memberAvail;
    }

    public void setMemberAvail(Integer memberAvail) {
        this.memberAvail = memberAvail;
    }

    public Integer getTotalRecharge() {
        return totalRecharge;
    }

    public void setTotalRecharge(Integer totalRecharge) {
        this.totalRecharge = totalRecharge;
    }

    public Integer getAccountMoney() {
        return accountMoney;
    }

    public void setAccountMoney(Integer accountMoney) {
        this.accountMoney = accountMoney;
    }

    public EnterpriseMemberVo() {
		super();
	}
	public int getMoney() {
		return money;
	}
	public void setMoney(int money) {
		this.money = money;
	}
//	public Integer getDeposit() {
//		return deposit;
//	}
//	public void setDeposit(int deposit) {
//		this.deposit = deposit;
//	}
	public int getIntegral() {
		return integral;
	}
	public void setIntegral(int integral) {
		this.integral = integral;
	}
	public int getQuota() {
		return quota;
	}
	public void setQuota(int quota) {
		this.quota = quota;
	}
	public int getQuotaMonth() {
		return quotaMonth;
	}
	public void setQuotaMonth(int quotaMonth) {
		this.quotaMonth = quotaMonth;
	}
	public Date getQuotaDateTime() {
		return quotaDateTime;
	}
	public void setQuotaDateTime(Date quotaDateTime) {
		this.quotaDateTime = quotaDateTime;
	}
	public int getQuotaUseMoney() {
		return quotaUseMoney;
	}
	public void setQuotaUseMoney(int quotaUseMoney) {
		this.quotaUseMoney = quotaUseMoney;
	} 
}
