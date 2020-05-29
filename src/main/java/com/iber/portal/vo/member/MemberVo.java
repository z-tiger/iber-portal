package com.iber.portal.vo.member;

public class MemberVo {
	private Integer memberId;
	private String cityName;
	private String memberName;
	private String phone;
	private Integer levelCode;
	private Integer memberContributedVal;
	public Integer getMemberId() {
		return memberId;
	}
	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public Integer getLevelCode() {
		return levelCode;
	}
	public void setLevelCode(Integer levelCode) {
		this.levelCode = levelCode;
	}
	public Integer getMemberContributedVal() {
		return memberContributedVal;
	}
	public void setMemberContributedVal(Integer memberContributedVal) {
		this.memberContributedVal = memberContributedVal;
	}
	
}
