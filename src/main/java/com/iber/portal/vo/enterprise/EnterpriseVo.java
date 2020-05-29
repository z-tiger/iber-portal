package com.iber.portal.vo.enterprise;

import com.iber.portal.model.enterprise.Enterprise;

public class EnterpriseVo extends Enterprise{
    private int enterpriseMemberNum;
    private int enterpriseRechargeSum;
    private int enterpriseCostSum;
    private int enterpriseRefundSum;
    private int money;
    private int deposit;
    private int enterpriseParkNum;
    private int enterpriseCarNum;

   /* private boolean isContactPark;//是否关联网点 默认否

    public boolean isContactPark() {
        return isContactPark;
    }

    public void setContactPark(boolean contactPark) {
        isContactPark = contactPark;
    }*/

    public int getEnterpriseCarNum() {
        return enterpriseCarNum;
    }

    public void setEnterpriseCarNum(int enterpriseCarNum) {
        this.enterpriseCarNum = enterpriseCarNum;
    }

    public EnterpriseVo() {
		super();
	}
	public int getEnterpriseMemberNum() {
		return enterpriseMemberNum;
	}
	public void setEnterpriseMemberNum(int enterpriseMemberNum) {
		this.enterpriseMemberNum = enterpriseMemberNum;
	}
	public int getEnterpriseRechargeSum() {
		return enterpriseRechargeSum;
	}
	public void setEnterpriseRechargeSum(int enterpriseRechargeSum) {
		this.enterpriseRechargeSum = enterpriseRechargeSum;
	}
	public int getEnterpriseCostSum() {
		return enterpriseCostSum;
	}
	public void setEnterpriseCostSum(int enterpriseCostSum) {
		this.enterpriseCostSum = enterpriseCostSum;
	}
	public int getMoney() {
		return money;
	}
	public void setMoney(int money) {
		this.money = money;
	}
	public int getDeposit() {
		return deposit;
	}
	public void setDeposit(int deposit) {
		this.deposit = deposit;
	}
	public int getEnterpriseRefundSum() {
		return enterpriseRefundSum;
	}
	public void setEnterpriseRefundSum(int enterpriseRefundSum) {
		this.enterpriseRefundSum = enterpriseRefundSum;
	}
	public int getEnterpriseParkNum() {
		return enterpriseParkNum;
	}
	public void setEnterpriseParkNum(int enterpriseParkNum) {
		this.enterpriseParkNum = enterpriseParkNum;
	}
	
}
