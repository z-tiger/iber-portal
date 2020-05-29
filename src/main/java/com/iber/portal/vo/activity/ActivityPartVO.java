package com.iber.portal.vo.activity;

public class ActivityPartVO {
	private String itemCode;//活动名称
	private Integer number;//优惠券数量
	private Integer balance;//优惠券面值
	private Integer deadline;//优惠券期限
	private String statu;//活动状态
	
	@Override
	public String toString() {
		return "ActivityPartVO [itemCode=" + itemCode + ", number="
				+ number + ", balance=" + balance + ", deadline=" + deadline
				+ ", statu=" + statu + "]";
	}

	public String getStatu() {
		return statu;
	}

	public void setStatu(String statu) {
		this.statu = statu;
	}

	public String getItemCode() {
		return itemCode;
	}
	
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	public Integer getBalance() {
		return balance;
	}
	public void setBalance(Integer balance) {
		this.balance = balance;
	}
	public Integer getDeadline() {
		return deadline;
	}
	public void setDeadline(Integer deadline) {
		this.deadline = deadline;
	}
	
	
	
}
