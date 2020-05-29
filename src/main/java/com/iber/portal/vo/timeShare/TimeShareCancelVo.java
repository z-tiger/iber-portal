package com.iber.portal.vo.timeShare;

import java.util.Date;

public class TimeShareCancelVo {
	private Integer id;

	private String memberName;

	private String cityName;

	private Integer cancelNum;

	private Date createTime;

	private String phone;
	/** 预约取消充电订单数（系统取消数+会员取消数） */
	private Integer chargingCancelNum;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public Integer getCancelNum() {
		return cancelNum;
	}

	public void setCancelNum(Integer cancelNum) {
		this.cancelNum = cancelNum;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getChargingCancelNum() {
		return chargingCancelNum;
	}

	public void setChargingCancelNum(Integer chargingCancelNum) {
		this.chargingCancelNum = chargingCancelNum;
	}

}