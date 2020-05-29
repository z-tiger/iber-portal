/*
 * Copyright 2015 guobang zaixian science technology CO., LTD. All rights reserved.
 * distributed with this file and available online at
 * http://www.gob123.com/
 */
package com.iber.portal.vo.charging;

import java.util.Date;

/**
 * 充电桩收入报表  【网点名】【充电桩编码】【桩类型】（如：快充-单枪、慢充-双枪）【充电电量】【价格】（两个小项：【物业电价】、【会员电价】）【计费金额（元）】【充电收入】
 * @author ouxx
 * @since 2016-11-24 下午2:35:15
 *
 */
public class ChargingIncomeVo extends ChargingReportVo {

	//充电费（元/度）
	private Integer chargingPrice;
	//服务费（元/度）
	private Integer servicePrice;
	//价格 = chargingPrice + servicePrice
	private Integer totalPrice;
	//充电收入
	private Integer income;
	
	private Double chargingAmountSum;//充电量总合计
	private Integer totalPriceSum;//价格总合计
	private Integer feeSum;//订单金额总合计
	private Integer incomeSum;//收入总合计
	private String carport;
	private String lpn;
	private String isFreeOrder;//是否免单
	private String freeReason; //免单原因
	private String memberStatus;
	private String userType;
	private String chargingStatus;
	
	private Integer invoiceStatus;
	private Integer invoiceMoney;
	private Integer invoiceMoneySum;
	private Date startTime ;
	private Double payMoney ;

	private Date PayTime;

    public Date getPayTime() {
        return PayTime;
    }

    public void setPayTime(Date payTime) {
        PayTime = payTime;
    }

    public Double getPayMoney() {
		return payMoney;
	}
	public void setPayMoney(Double payMoney) {
		this.payMoney = payMoney;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Integer getInvoiceStatus() {
		return invoiceStatus;
	}
	public void setInvoiceStatus(Integer invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}
	public Integer getInvoiceMoney() {
		return invoiceMoney;
	}
	public void setInvoiceMoney(Integer invoiceMoney) {
		this.invoiceMoney = invoiceMoney;
	}
	public Integer getInvoiceMoneySum() {
		return invoiceMoneySum;
	}
	public void setInvoiceMoneySum(Integer invoiceMoneySum) {
		this.invoiceMoneySum = invoiceMoneySum;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getChargingStatus() {
		return chargingStatus;
	}
	public void setChargingStatus(String chargingStatus) {
		this.chargingStatus = chargingStatus;
	}
	public String getMemberStatus() {
		return memberStatus;
	}
	public void setMemberStatus(String memberStatus) {
		this.memberStatus = memberStatus;
	}
	public String getLpn() {
		return lpn;
	}
	public void setLpn(String lpn) {
		this.lpn = lpn;
	}
	public String getCarport() {
		return carport;
	}
	public void setCarport(String carport) {
		this.carport = carport;
	}
	public String getIsFreeOrder() {
		return isFreeOrder;
	}
	public void setIsFreeOrder(String isFreeOrder) {
		this.isFreeOrder = isFreeOrder;
	}
	public String getFreeReason() {
		return freeReason;
	}
	public void setFreeReason(String freeReason) {
		this.freeReason = freeReason;
	}
	public Double getChargingAmountSum() {
		return chargingAmountSum;
	}
	public void setChargingAmountSum(Double chargingAmountSum) {
		this.chargingAmountSum = chargingAmountSum;
	}
	public Integer getTotalPriceSum() {
		return totalPriceSum;
	}
	public void setTotalPriceSum(Integer totalPriceSum) {
		this.totalPriceSum = totalPriceSum;
	}
	public Integer getFeeSum() {
		return feeSum;
	}
	public void setFeeSum(Integer feeSum) {
		this.feeSum = feeSum;
	}
	public Integer getIncomeSum() {
		return incomeSum;
	}
	public void setIncomeSum(Integer incomeSum) {
		this.incomeSum = incomeSum;
	}
	public Integer getChargingPrice() {
		return chargingPrice;
	}
	public void setChargingPrice(Integer chargingPrice) {
		this.chargingPrice = chargingPrice;
	}
	public Integer getServicePrice() {
		return servicePrice;
	}
	public void setServicePrice(Integer servicePrice) {
		this.servicePrice = servicePrice;
	}
	public Integer getIncome() {
		return income;
	}
	public void setIncome(Integer income) {
		this.income = income;
	}
	public Integer getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(Integer totalPrice) {
		this.totalPrice = totalPrice;
	}

    @Override
    public String toString() {
        return "ChargingIncomeVo{" +
                "chargingPrice=" + chargingPrice +
                ", servicePrice=" + servicePrice +
                ", totalPrice=" + totalPrice +
                ", income=" + income +
                ", chargingAmountSum=" + chargingAmountSum +
                ", totalPriceSum=" + totalPriceSum +
                ", feeSum=" + feeSum +
                ", incomeSum=" + incomeSum +
                ", carport='" + carport + '\'' +
                ", lpn='" + lpn + '\'' +
                ", isFreeOrder='" + isFreeOrder + '\'' +
                ", freeReason='" + freeReason + '\'' +
                ", memberStatus='" + memberStatus + '\'' +
                ", userType='" + userType + '\'' +
                ", chargingStatus='" + chargingStatus + '\'' +
                ", invoiceStatus=" + invoiceStatus +
                ", invoiceMoney=" + invoiceMoney +
                ", invoiceMoneySum=" + invoiceMoneySum +
                ", startTime=" + startTime +
                ", payMoney=" + payMoney +
                ", PayTime=" + PayTime +
                '}';
    }
}
