package com.iber.portal.vo.car;

public class CarMonthlyOperationVo {
	private Integer rentTimeSum;//租赁次数总合计
	private Double totalMileageSum;//里程总合计
	private Double payMoneySum;//支付金额总合计
	private Double freeCompensationMoneySum;//不计免赔总合计
	private Double totalPayMoneySum;//订单金额总合计
	private Double totalMinuteSum;//行驶时间总合计
	public Integer getRentTimeSum() {
		return rentTimeSum;
	}
	public void setRentTimeSum(Integer rentTimeSum) {
		this.rentTimeSum = rentTimeSum;
	}
	public Double getTotalMileageSum() {
		return totalMileageSum;
	}
	public void setTotalMileageSum(Double totalMileageSum) {
		this.totalMileageSum = totalMileageSum;
	}
	public Double getPayMoneySum() {
		return payMoneySum;
	}
	public void setPayMoneySum(Double payMoneySum) {
		this.payMoneySum = payMoneySum;
	}
	public Double getFreeCompensationMoneySum() {
		return freeCompensationMoneySum;
	}
	public void setFreeCompensationMoneySum(Double freeCompensationMoneySum) {
		this.freeCompensationMoneySum = freeCompensationMoneySum;
	}
	public Double getTotalPayMoneySum() {
		return totalPayMoneySum;
	}
	public void setTotalPayMoneySum(Double totalPayMoneySum) {
		this.totalPayMoneySum = totalPayMoneySum;
	}
	public Double getTotalMinuteSum() {
		return totalMinuteSum;
	}
	public void setTotalMinuteSum(Double totalMinuteSum) {
		this.totalMinuteSum = totalMinuteSum;
	}
}
