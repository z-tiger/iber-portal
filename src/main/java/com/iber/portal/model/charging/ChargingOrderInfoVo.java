package com.iber.portal.model.charging;

public class ChargingOrderInfoVo {
	private Double chargingAmountSum;//电量总合计
	private Integer chargingTimeSum;//充电时长总合计
	private Integer orderMoneySum;//订单金额总合计
	private Integer payMoneySum;//支付金额总合计
	private Integer couponValueSum;//优惠券面值总合计
	
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
}
