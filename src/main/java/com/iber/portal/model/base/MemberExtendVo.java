package com.iber.portal.model.base;

public class MemberExtendVo extends Member {
	private Integer deposit;
	private Integer refundMoney;
	private Integer requireDeposit;
	public Integer getDeposit() {
		return deposit;
	}
	public void setDeposit(Integer deposit) {
		this.deposit = deposit;
	}
	public Integer getRefundMoney() {
		return refundMoney;
	}
	public void setRefundMoney(Integer refundMoney) {
		this.refundMoney = refundMoney;
	}
	public Integer getRequireDeposit() {
		return requireDeposit;
	}
	public void setRequireDeposit(Integer requireDeposit) {
		this.requireDeposit = requireDeposit;
	}
	

}
