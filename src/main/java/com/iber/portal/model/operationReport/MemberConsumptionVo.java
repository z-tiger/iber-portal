package com.iber.portal.model.operationReport;

public class MemberConsumptionVo {
	private Integer chargingAmountSum;//充电量总合计
    private Integer chargingTimeSum;//充电时长总合计
    private Double orderMoneySum;//订单金额总合计
    private Double payMoneySum;//支付金额总合计
    private Integer couponMoneySum;//优惠券金额总合计
    
     public Integer getChargingAmountSum() {
		return chargingAmountSum;
	}

	public void setChargingAmountSum(Integer chargingAmountSum) {
		this.chargingAmountSum = chargingAmountSum;
	}

	public Integer getChargingTimeSum() {
		return chargingTimeSum;
	}

	public void setChargingTimeSum(Integer chargingTimeSum) {
		this.chargingTimeSum = chargingTimeSum;
	}

	public Double getOrderMoneySum() {
		return orderMoneySum;
	}

	public void setOrderMoneySum(Double orderMoneySum) {
		this.orderMoneySum = orderMoneySum;
	}

	public Double getPayMoneySum() {
		return payMoneySum;
	}

	public void setPayMoneySum(Double payMoneySum) {
		this.payMoneySum = payMoneySum;
	}

	public Integer getCouponMoneySum() {
		return couponMoneySum;
	}

	public void setCouponMoneySum(Integer couponMoneySum) {
		this.couponMoneySum = couponMoneySum;
	}
}
