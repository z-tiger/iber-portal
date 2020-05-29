package com.iber.portal.model.operationReport;

public class MemberTimeShareConsumptionVo {
	private Integer totalMinuteSum;//使用时长总合计
    private Integer totalMileageSum;//使用里程总合计
    private Double payMoneySum;//支付金额总合计
    private Double totalPayMoneySum;//订单金额总合计
    private Double couponBalanceSum;//优惠券金额总合计
    private Double freeCompensateMoneySum;//不计免赔总合计
    
	public Double getPayMoneySum() {
		return payMoneySum;
	}

	public void setPayMoneySum(Double payMoneySum) {
		this.payMoneySum = payMoneySum;
	}

	public Integer getTotalMinuteSum() {
		return totalMinuteSum;
	}

	public void setTotalMinuteSum(Integer totalMinuteSum) {
		this.totalMinuteSum = totalMinuteSum;
	}

	public Integer getTotalMileageSum() {
		return totalMileageSum;
	}

	public void setTotalMileageSum(Integer totalMileageSum) {
		this.totalMileageSum = totalMileageSum;
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

	public Double getFreeCompensateMoneySum() {
		return freeCompensateMoneySum;
	}

	public void setFreeCompensateMoneySum(Double freeCompensateMoneySum) {
		this.freeCompensateMoneySum = freeCompensateMoneySum;
	}
}
