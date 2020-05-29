package com.iber.portal.model.member;

import java.util.Date;

/**
 * 
 * <br>
 * <b>功能：</b>MemberBlacklistEntity<br>
 * <b>作者：</b>www.jeecg.org<br>
 * <b>日期：</b> Feb 2, 2013 <br>
 * <b>版权所有：<b>版权所有(C) 2013，www.jeecg.org<br>
 */
public class MemberBlacklist {
	
		private Integer id;//   	private Integer memberId;//   	private Integer createId;//   	private Date createTime;//   	private Integer updateId;//   	private Date updateTime;//   	private Integer isAuto;
	private String reason;
	private String cityName;
	private String name;
	private String phone;
	private String sex;
	private String idCard;
	private String accoutStatus;
	private String memberLevel;
	private Integer latestContributedVal;
	private String creator;//操作人员
	private Integer levelCode;
	private Integer contributedVal;
	
	public Integer getContributedVal() {
		return contributedVal;
	}
	public void setContributedVal(Integer contributedVal) {
		this.contributedVal = contributedVal;
	}
	public Integer getLevelCode() {
		return levelCode;
	}
	public void setLevelCode(Integer levelCode) {
		this.levelCode = levelCode;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	public String getAccoutStatus() {
		return accoutStatus;
	}
	public void setAccoutStatus(String accoutStatus) {
		this.accoutStatus = accoutStatus;
	}
	public String getMemberLevel() {
		return memberLevel;
	}
	public void setMemberLevel(String memberLevel) {
		this.memberLevel = memberLevel;
	}
	public Integer getLatestContributedVal() {
		return latestContributedVal;
	}
	public void setLatestContributedVal(Integer latestContributedVal) {
		this.latestContributedVal = latestContributedVal;
	}
	public Integer getIsAuto() {
		return isAuto;
	}
	public void setIsAuto(Integer isAuto) {
		this.isAuto = isAuto;
	}
	public java.lang.Integer getId() {
		return id;
	}
	public void setId(java.lang.Integer id) {
		this.id = id;
	}
	public java.lang.Integer getMemberId() {
		return memberId;
	}
	public void setMemberId(java.lang.Integer memberId) {
		this.memberId = memberId;
	}
	public Integer getCreateId() {
		return createId;
	}
	public void setCreateId(Integer createId) {
		this.createId = createId;
	}
	public java.util.Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}
	public java.lang.Integer getUpdateId() {
		return updateId;
	}
	public void setUpdateId(java.lang.Integer updateId) {
		this.updateId = updateId;
	}
	public java.util.Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(java.util.Date updateTime) {
		this.updateTime = updateTime;
	}
	
		
}

