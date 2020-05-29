package com.iber.portal.zhima.vo;

import java.util.Date;

public class ZhimaRequestVo {
	
	private String idcard;
	private String userName;
	private String orderId;
	private Integer timeRate;
	private String lpn;
	private Integer deposit;
	private Date startTime;
	private Integer payMoney;
	private Date endTime;
	private Integer wzMoney;//如果有违章，要罚款的金额
	
	public Integer getWzMoney() {
		return wzMoney;
	}
	public void setWzMoney(Integer wzMoney) {
		this.wzMoney = wzMoney;
	}
	public String getIdcard() {
		return idcard;
	}
	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public Integer getTimeRate() {
		return timeRate;
	}
	public void setTimeRate(Integer timeRate) {
		this.timeRate = timeRate;
	}
	public String getLpn() {
		return lpn;
	}
	public void setLpn(String lpn) {
		this.lpn = lpn;
	}
	public Integer getDeposit() {
		return deposit;
	}
	public void setDeposit(Integer deposit) {
		this.deposit = deposit;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Integer getPayMoney() {
		return payMoney;
	}
	public void setPayMoney(Integer payMoney) {
		this.payMoney = payMoney;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	
}
