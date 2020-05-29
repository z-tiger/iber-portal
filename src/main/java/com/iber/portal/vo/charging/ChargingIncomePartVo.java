package com.iber.portal.vo.charging;

public class ChargingIncomePartVo {
	private Double chargingAmountSum;//充电量总合计
	private Integer totalPriceSum;//价格总合计
	private Integer feeSum;//订单金额总合计
	private Integer incomeSum;//收入总合计
	private Integer invoiceMoneySum;
	

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
	public Integer getInvoiceMoneySum() {
		return invoiceMoneySum;
	}
	public void setInvoiceMoneySum(Integer invoiceMoneySum) {
		this.invoiceMoneySum = invoiceMoneySum;
	}
	
}
