/*
 * Copyright 2015 guobang zaixian science technology CO., LTD. All rights reserved.
 * distributed with this file and available online at
 * http://www.gob123.com/
 */
package com.iber.portal.vo.order;

import java.util.Date;

/**
 * 租赁收入报表
 * @author ouxx
 * @since 2016-12-19 下午3:23:49
 *
 */
public class RentReportVo {

	private String orderId;
	private Date endTime;
	private String lpn;
	private String brandName;
	private String cityName;// 运营城市
	private String type;//车型
	private Integer memberId;
	private String name;//会员名
	private String phone;//会员手机号
	private Double totalMinute;
	private Double rate;//单价
	private Double payMoney;//实际收入
	private Double couponBalance;//优惠券面值（元）
	private Double reductionMoney;//优惠金额
	private Double freeCompensationMoney;//保险（元）
	private Double totalPayMoney;//收入（元），扣减优惠之前的
	private Double totalMinuteSum;//租赁时长总合计
	private Double totalPayMoneySum;//订单金额总合计
	private Double couponBalanceSum;//优惠券金额总合计
	private Double nightReductionMoney;//夜间优惠
	private Double freeCompsationMoneySum;//保险总合计
	private Double payMoneySum;//收入总合计
	private Double invoiceMoney;//收入总合计
	private Integer invoiceStatus;
	private Double totalInvoiceMoney;
	
	
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public Double getTotalInvoiceMoney() {
		return totalInvoiceMoney;
	}
	public void setTotalInvoiceMoney(Double totalInvoiceMoney) {
		this.totalInvoiceMoney = totalInvoiceMoney;
	}
	public Double getInvoiceMoney() {
		return invoiceMoney;
	}
	public void setInvoiceMoney(Double invoiceMoney) {
		this.invoiceMoney = invoiceMoney;
	}
	public Integer getInvoiceStatus() {
		return invoiceStatus;
	}
	public void setInvoiceStatus(Integer invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
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
	public Double getTotalMinute() {
		return totalMinute;
	}
	public void setTotalMinute(Double totalMinute) {
		this.totalMinute = totalMinute;
	}
	public Double getRate() {
		return rate;
	}
	public void setRate(Double rate) {
		this.rate = rate;
	}
	public Double getPayMoney() {
		return payMoney;
	}
	public void setPayMoney(Double payMoney) {
		this.payMoney = payMoney;
	}
	public Double getCouponBalance() {
		return couponBalance;
	}
	public void setCouponBalance(Double couponBalance) {
		this.couponBalance = couponBalance;
	}
	public Double getFreeCompensationMoney() {
		return freeCompensationMoney;
	}
	public void setFreeCompensationMoney(Double freeCompensationMoney) {
		this.freeCompensationMoney = freeCompensationMoney;
	}
	public Double getTotalPayMoney() {
		return totalPayMoney;
	}
	public void setTotalPayMoney(Double totalPayMoney) {
		this.totalPayMoney = totalPayMoney;
	}
	public Double getTotalMinuteSum() {
		return totalMinuteSum;
	}
	public void setTotalMinuteSum(Double totalMinuteSum) {
		this.totalMinuteSum = totalMinuteSum;
	}
	public Double getTotalPayMoneySum() {
		return totalPayMoneySum;
	}
	public void setTotalPayMoneySum(Double totalPayMoneySum) {
		this.totalPayMoneySum = totalPayMoneySum;
	}
	public Double getCouponBalanceSum() {
		return couponBalanceSum;
	}
	public void setCouponBalanceSum(Double couponBalanceSum) {
		this.couponBalanceSum = couponBalanceSum;
	}
	public Double getFreeCompsationMoneySum() {
		return freeCompsationMoneySum;
	}
	public void setFreeCompsationMoneySum(Double freeCompsationMoneySum) {
		this.freeCompsationMoneySum = freeCompsationMoneySum;
	}
	public Double getPayMoneySum() {
		return payMoneySum;
	}
	public void setPayMoneySum(Double payMoneySum) {
		this.payMoneySum = payMoneySum;
	}
	public Double getNightReductionMoney() {
		return nightReductionMoney;
	}
	public void setNightReductionMoney(Double nightReductionMoney) {
		this.nightReductionMoney = nightReductionMoney;
	}
	public Double getReductionMoney() {
		return reductionMoney;
	}
	public void setReductionMoney(Double reductionMoney) {
		this.reductionMoney = reductionMoney;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	
}
