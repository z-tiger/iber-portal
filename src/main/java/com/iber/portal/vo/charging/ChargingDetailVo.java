/*
 * Copyright 2015 guobang zaixian science technology CO., LTD. All rights reserved.
 * distributed with this file and available online at
 * http://www.gob123.com/
 */
package com.iber.portal.vo.charging;

/**
 * 充电桩充电明细表 【网点名】【充电桩编码】【桩类型】（如：快充-单枪、慢充-双枪）【使用者】【使用者身份证】【手机号码】【充电电量】【充电时长】【计费金额（元）】、【消费金额（元）】，两个小项：【支付金额（元）】、【优惠券（元）】
 * @author ouxx
 * @since 2016-11-19 下午4:22:23
 * 
 */
public class ChargingDetailVo extends ChargingReportVo {

	private String memberName;
	private String memberIdcard;
	private String memberPhone;
	private Double chargingAmountSum;//电量总合计
	private Integer chargingTimeSum;//充电时长总合计
	private Integer orderMoneySum;//订单金额总合计
	private Integer payMoneySum;//支付金额总合计
	private Integer couponValueSum;//优惠券面值总合计
	private String isFreeOrder; //是否免单
	private String freeReason;  //免单原因
	private String userType;
	private String memberStatus;
	private String carport;
	private String chargingStatus;
	public String getChargingStatus() {
		return chargingStatus;
	}
	public void setChargingStatus(String chargingStatus) {
		this.chargingStatus = chargingStatus;
	}
	public String getCarport() {
		return carport;
	}
	public void setCarport(String carport) {
		this.carport = carport;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getMemberStatus() {
		return memberStatus;
	}
	public void setMemberStatus(String memberStatus) {
		this.memberStatus = memberStatus;
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
	public Integer getChargingTimeSum() {
		return chargingTimeSum;
	}
	public void setChargingTimeSum(Integer chargingTimeSum) {
		this.chargingTimeSum = chargingTimeSum;
	}
	public Integer getOrderMoneySum() {
		return orderMoneySum;
	}
	public void setOrderMoneySum(Integer orderMoneySum) {
		this.orderMoneySum = orderMoneySum;
	}
	public Integer getPayMoneySum() {
		return payMoneySum;
	}
	public void setPayMoneySum(Integer payMoneySum) {
		this.payMoneySum = payMoneySum;
	}
	public Integer getCouponValueSum() {
		return couponValueSum;
	}
	public void setCouponValueSum(Integer couponValueSum) {
		this.couponValueSum = couponValueSum;
	}

	// 【消费金额（元）】，两个小项：【支付金额（元）】、【优惠券（元）】
	private Integer consumption;
	private Integer payMoney;
	private Integer couponAmount;
	
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public String getMemberIdcard() {
		return memberIdcard;
	}
	public void setMemberIdcard(String memberIdcard) {
		this.memberIdcard = memberIdcard;
	}
	public String getMemberPhone() {
		return memberPhone;
	}
	public void setMemberPhone(String memberPhone) {
		this.memberPhone = memberPhone;
	}
	public Integer getConsumption() {
		return consumption;
	}
	public void setConsumption(Integer consumption) {
		this.consumption = consumption;
	}
	public Integer getPayMoney() {
		return payMoney;
	}
	public void setPayMoney(Integer payMoney) {
		this.payMoney = payMoney;
	}
	public Integer getCouponAmount() {
		return couponAmount;
	}
	public void setCouponAmount(Integer couponAmount) {
		this.couponAmount = couponAmount;
	}

}
