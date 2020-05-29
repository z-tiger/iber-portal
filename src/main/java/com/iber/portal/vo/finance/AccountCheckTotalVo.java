/*
 * Copyright 2015 guobang zaixian science technology CO., LTD. All rights reserved.
 * distributed with this file and available online at
 * http://www.gob123.com/
 */
package com.iber.portal.vo.finance;


/**
 * 消费对账单
 * @author lf
 * @since 2017-2-17 09:32:46
 *
 */
public class AccountCheckTotalVo {

	private Double depositChargeTotal;//押金充值合计
	private Double balanceChargeTotal;//余额充值合计
	private Double consumeTotal;//消费合计
	private Double balanceConsumeTotal;//余额消费合计
	private Double refundTotal;//退款合计
	private Long balanceTotal;//余额合计
	private Long depositTotal;//押金合计
	private Double returnedMoneyTotal;//返现合计,仅仅针对众泰车订单类型
	
	public Double getReturnedMoneyTotal() {
		return returnedMoneyTotal;
	}
	public void setReturnedMoneyTotal(Double returnedMoneyTotal) {
		this.returnedMoneyTotal = returnedMoneyTotal;
	}
	public Double getDepositChargeTotal() {
		return depositChargeTotal;
	}
	public void setDepositChargeTotal(Double depositChargeTotal) {
		this.depositChargeTotal = depositChargeTotal;
	}
	public Double getBalanceChargeTotal() {
		return balanceChargeTotal;
	}
	public void setBalanceChargeTotal(Double balanceChargeTotal) {
		this.balanceChargeTotal = balanceChargeTotal;
	}
	public Double getConsumeTotal() {
		return consumeTotal;
	}
	public void setConsumeTotal(Double consumeTotal) {
		this.consumeTotal = consumeTotal;
	}
	public Double getRefundTotal() {
		return refundTotal;
	}
	public void setRefundTotal(Double refundTotal) {
		this.refundTotal = refundTotal;
	}
	public Long getBalanceTotal() {
		return balanceTotal;
	}
	public void setBalanceTotal(Long balanceTotal) {
		this.balanceTotal = balanceTotal;
	}
	public Long getDepositTotal() {
		return depositTotal;
	}
	public void setDepositTotal(Long depositTotal) {
		this.depositTotal = depositTotal;
	}
	public Double getBalanceConsumeTotal() {
		return balanceConsumeTotal;
	}
	public void setBalanceConsumeTotal(Double balanceConsumeTotal) {
		this.balanceConsumeTotal = balanceConsumeTotal;
	}
	
	
}
