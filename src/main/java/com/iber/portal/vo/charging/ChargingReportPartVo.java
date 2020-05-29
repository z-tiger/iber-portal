package com.iber.portal.vo.charging;

public class ChargingReportPartVo {
	private Integer chargingTimeSum;//充电时间总合计
	private Double chargingAmountSum;//充电金额总合计
	private Integer orderMoneySum;//订单金额总合计
	private Integer chargedTimesSum;//充电次数合计
	private Integer useTimeSum;//电桩使用时长合计
	
	public Integer getChargingTimeSum() {
		return chargingTimeSum;
	}
	public void setChargingTimeSum(Integer chargingTimeSum) {
		this.chargingTimeSum = chargingTimeSum;
	}
	public Double getChargingAmountSum() {
		return chargingAmountSum;
	}
	public void setChargingAmountSum(Double chargingAmountSum) {
		this.chargingAmountSum = chargingAmountSum;
	}
	public Integer getOrderMoneySum() {
		return orderMoneySum;
	}
	public void setOrderMoneySum(Integer orderMoneySum) {
		this.orderMoneySum = orderMoneySum;
	}
	public Integer getChargedTimesSum() {
		return chargedTimesSum;
	}
	public void setChargedTimesSum(Integer chargedTimesSum) {
		this.chargedTimesSum = chargedTimesSum;
	}
	public Integer getUseTimeSum() {
		return useTimeSum;
	}
	public void setUseTimeSum(Integer useTimeSum) {
		this.useTimeSum = useTimeSum;
	}
	
}
