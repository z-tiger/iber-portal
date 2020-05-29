package com.iber.portal.model.member;

import java.util.Date;

public class MemberContributeItemVo {
	private Integer orderAmount;
	private Integer contributedValDelta;//贡献值增量
	private Date createTime;
	private String type;
	private String memberName;
	public Integer getOrderAmount() {
		return orderAmount;
	}
	public void setOrderAmount(Integer orderAmount) {
		this.orderAmount = orderAmount;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public Integer getContributedValDelta() {
		return contributedValDelta;
	}
	public void setContributedValDelta(Integer contributedValDelta) {
		this.contributedValDelta = contributedValDelta;
	}
	
}
