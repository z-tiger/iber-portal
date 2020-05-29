package com.iber.portal.vo.report;

public class TimeShareIncomeReportVo {
	private Double totalMinuteSum;
	private Double payMoneySum;
	private Double totalMileageSum;
	private Double reductionPayMoneySum;
	private Double invoiceMoneySum;
	private Double totalPayMoneySum ;
	private Double totalCompensationSum;

    public Double getTotalCompensationSum() {
        return totalCompensationSum;
    }

    public void setTotalCompensationSum(Double totalCompensationSum) {
        this.totalCompensationSum = totalCompensationSum;
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
	public Double getPayMoneySum() {
		return payMoneySum;
	}
	public void setPayMoneySum(Double payMoneySum) {
		this.payMoneySum = payMoneySum;
	}
	public Double getTotalMileageSum() {
		return totalMileageSum;
	}
	public void setTotalMileageSum(Double totalMileageSum) {
		this.totalMileageSum = totalMileageSum;
	}
	public Double getReductionPayMoneySum() {
		return reductionPayMoneySum;
	}
	public void setReductionPayMoneySum(Double reductionPayMoneySum) {
		this.reductionPayMoneySum = reductionPayMoneySum;
	}
	public Double getInvoiceMoneySum() {
		return invoiceMoneySum;
	}
	public void setInvoiceMoneySum(Double invoiceMoneySum) {
		this.invoiceMoneySum = invoiceMoneySum;
	}
}	
