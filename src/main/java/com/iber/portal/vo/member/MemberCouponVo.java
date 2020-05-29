package com.iber.portal.vo.member;

import java.io.Serializable;

public class MemberCouponVo implements Serializable{

	private Integer memberId;
	//优惠券面值(元)
	private Integer value;
	//优惠券数量
	private Integer number;
	//等级名称
	private String levelName;
	//会员电话号码
	private String phone;
	
	public Integer getMemberId() {
		return memberId;
	}
	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}
	public Integer getValue() {
		return value;
	}
	public void setValue(Integer value) {
		this.value = value;
	}
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	public String getLevelName() {
		return levelName;
	}
	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
}
