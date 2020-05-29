package com.iber.portal.vo.order;

public class RentReportSumVo {
	private Double totalMinuteSum;//租赁时长总合计
	private Double totalPayMoneySum;//订单金额总合计
	private Double couponBalanceSum;//优惠券金额总合计
	private Double freeCompsationMoneySum;//保险总合计
	private Double payMoneySum;//收入总合计
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
	
	
}
