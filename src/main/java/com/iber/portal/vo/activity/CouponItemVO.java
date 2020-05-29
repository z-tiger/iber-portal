package com.iber.portal.vo.activity;

import java.math.BigDecimal;

/**
 * 包含数量，面值，活动类型，期限的vo对象
 * @author Administrator
 *
 */
public class CouponItemVO {
	private String itemCode;
	private int number;
	private Integer balance;
	private int deadline;
	public String getItemCode() {
		return itemCode;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public Integer getBalance() {
		return balance;
	}
	public void setBalance(Integer balance) {
		this.balance = balance;
	}
	public int getDeadline() {
		return deadline;
	}
	public void setDeadline(int deadline) {
		this.deadline = deadline;
	}
}
