package com.iber.portal.vo.member;

public class MemberRefundVo {
	private Integer chargingId;//	充值记录id
	private String chargingTime;//	充值时间
	private String chargingType;//	A表示支付宝支付，WX表示微信支付
	private Integer money;//会员充值押金金额
	private Integer refundId;
	
	public Integer getRefundId() {
		return refundId;
	}
	public void setRefundId(Integer refundId) {
		this.refundId = refundId;
	}
	public Integer getChargingId() {
		return chargingId;
	}
	public void setChargingId(Integer chargingId) {
		this.chargingId = chargingId;
	}
	public String getChargingTime() {
		return chargingTime;
	}
	public void setChargingTime(String chargingTime) {
		this.chargingTime = chargingTime;
	}
	public String getChargingType() {
		return chargingType;
	}
	public void setChargingType(String chargingType) {
		this.chargingType = chargingType;
	}
	public Integer getMoney() {
		return money;
	}
	public void setMoney(Integer money) {
		this.money = money;
	}
	
}
