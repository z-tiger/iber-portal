/*
 * Copyright 2015 guobang zaixian science technology CO., LTD. All rights reserved.
 * distributed with this file and available online at
 * http://www.gob123.com/
 */
package com.iber.portal.vo.finance;

import java.util.Date;

/**
 * 消费对账单
 * @author ouxx
 * @since 2016-12-21 上午8:55:18
 *
 */
public class AccountCheckVo {
    private Integer id;
	private Date endTime;
	private Integer memberId;
	private String name;//会员名
	private String phone;//会员手机号
	
	private Double chargeMoney;//普通充值金额
	
	private Double depositChargeMoney;//押金充值金额
	private String chargeCategory;//充值类别（押金D，余额B）
	private String payType;//支付类型 B余额
	
	private Date chargeTime;//充值日期
	private Double consumptionMoney;//消费金额
	private Double balanceConsumptionMoney;//余额消费金额
	private String consumptionType;//消费类型，时租、日租、充电等
	private Date consumptionTime;//消费日期
	
	private Double refundMoney;//退款
	private Long balance;//余额
	
	private Long deposit;//押金余额
	
	private Double returnedMoneyTotal;//众泰订单返还余额,返现记录在x_money_log
	private String lpn ;
	private String brandName ;
	private String cityCode;
	private String cityName;

	private Double payMoney;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(Double payMoney) {
        this.payMoney = payMoney;
    }

    public String getCityCode() {
		return cityCode;
	}
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getLpn() {
		return lpn;
	}
	public void setLpn(String lpn) {
		this.lpn = lpn;
	}
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	public Double getReturnedMoneyTotal() {
		return returnedMoneyTotal;
	}

	public void setReturnedMoneyTotal(Double returnedMoneyTotal) {
		this.returnedMoneyTotal = returnedMoneyTotal;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public Integer getMemberId() {
		return memberId;
	}
	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Double getChargeMoney() {
		return chargeMoney;
	}
	public void setChargeMoney(Double chargeMoney) {
		this.chargeMoney = chargeMoney;
	}
	public Double getConsumptionMoney() {
		return consumptionMoney;
	}
	public void setConsumptionMoney(Double consumptionMoney) {
		this.consumptionMoney = consumptionMoney;
	}
	public String getConsumptionType() {
		return consumptionType;
	}
	public void setConsumptionType(String consumptionType) {
		this.consumptionType = consumptionType;
	}
	public Double getRefundMoney() {
		return refundMoney;
	}
	public void setRefundMoney(Double refundMoney) {
		this.refundMoney = refundMoney;
	}
	public Long getBalance() {
		return balance;
	}
	public void setBalance(Long balance) {
		this.balance = balance;
	}
	public Date getChargeTime() {
		return chargeTime;
	}
	public void setChargeTime(Date chargeTime) {
		this.chargeTime = chargeTime;
	}
	public Date getConsumptionTime() {
		return consumptionTime;
	}
	public void setConsumptionTime(Date consumptionTime) {
		this.consumptionTime = consumptionTime;
	}
	public Double getDepositChargeMoney() {
		return depositChargeMoney;
	}
	public void setDepositChargeMoney(Double depositChargeMoney) {
		this.depositChargeMoney = depositChargeMoney;
	}
	public Long getDeposit() {
		return deposit;
	}
	public void setDeposit(Long deposit) {
		this.deposit = deposit;
	}
	public String getChargeCategory() {
		return chargeCategory;
	}
	public void setChargeCategory(String chargeCategory) {
		this.chargeCategory = chargeCategory;
	}
	public Double getBalanceConsumptionMoney() {
		return balanceConsumptionMoney;
	}
	public void setBalanceConsumptionMoney(Double balanceConsumptionMoney) {
		this.balanceConsumptionMoney = balanceConsumptionMoney;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}

    @Override
    public String toString() {
        return "AccountCheckVo{" +
                "id=" + id +
                ", endTime=" + endTime +
                ", memberId=" + memberId +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", chargeMoney=" + chargeMoney +
                ", depositChargeMoney=" + depositChargeMoney +
                ", chargeCategory='" + chargeCategory + '\'' +
                ", payType='" + payType + '\'' +
                ", chargeTime=" + chargeTime +
                ", consumptionMoney=" + consumptionMoney +
                ", balanceConsumptionMoney=" + balanceConsumptionMoney +
                ", consumptionType='" + consumptionType + '\'' +
                ", consumptionTime=" + consumptionTime +
                ", refundMoney=" + refundMoney +
                ", balance=" + balance +
                ", deposit=" + deposit +
                ", returnedMoneyTotal=" + returnedMoneyTotal +
                ", lpn='" + lpn + '\'' +
                ", brandName='" + brandName + '\'' +
                ", cityCode='" + cityCode + '\'' +
                ", cityName='" + cityName + '\'' +
                ", payMoney=" + payMoney +
                '}';
    }


}
