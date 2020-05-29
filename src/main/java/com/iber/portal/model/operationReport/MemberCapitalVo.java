package com.iber.portal.model.operationReport;

public class MemberCapitalVo {
	private Double depositSum;//会员押金总合计
	private Double moneySum;//会员余额总合计
	private Double totalChargeMoneySum;//总充值金额总合计
	private Double totalConsumeMoneySum;//总消费金额-支付总合计
	private Double totalCouponMoneySum;//总消费金额-优惠券总合计
	private Double totalRefundMoneySum;//总退款金额总合计
	
	public Double getDepositSum() {
		return depositSum;
	}

	public void setDepositSum(Double depositSum) {
		this.depositSum = depositSum;
	}

	public Double getMoneySum() {
		return moneySum;
	}

	public void setMoneySum(Double moneySum) {
		this.moneySum = moneySum;
	}

	public Double getTotalChargeMoneySum() {
		return totalChargeMoneySum;
	}

	public void setTotalChargeMoneySum(Double totalChargeMoneySum) {
		this.totalChargeMoneySum = totalChargeMoneySum;
	}

	public Double getTotalConsumeMoneySum() {
		return totalConsumeMoneySum;
	}

	public void setTotalConsumeMoneySum(Double totalConsumeMoneySum) {
		this.totalConsumeMoneySum = totalConsumeMoneySum;
	}

	public Double getTotalCouponMoneySum() {
		return totalCouponMoneySum;
	}

	public void setTotalCouponMoneySum(Double totalCouponMoneySum) {
		this.totalCouponMoneySum = totalCouponMoneySum;
	}

	public Double getTotalRefundMoneySum() {
		return totalRefundMoneySum;
	}

	public void setTotalRefundMoneySum(Double totalRefundMoneySum) {
		this.totalRefundMoneySum = totalRefundMoneySum;
	}
}
