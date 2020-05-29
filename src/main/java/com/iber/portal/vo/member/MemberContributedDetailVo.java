package com.iber.portal.vo.member;

import java.util.Date;

public class MemberContributedDetailVo {
	private Integer id;
	private Integer contributeVal;
	private Integer isIncrease;
	private String childrenName;
	private String parentName;
	private Date createTime;
	private String createName;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getContributeVal() {
		return contributeVal;
	}
	public void setContributeVal(Integer contributeVal) {
		this.contributeVal = contributeVal;
	}
	public Integer getIsIncrease() {
		return isIncrease;
	}
	public void setIsIncrease(Integer isIncrease) {
		this.isIncrease = isIncrease;
	}
	public String getChildrenName() {
		return childrenName;
	}
	public void setChildrenName(String childrenName) {
		this.childrenName = childrenName;
	}
	public String getParentName() {
		return parentName;
	}
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getCreateName() {
		return createName;
	}
	public void setCreateName(String createName) {
		this.createName = createName;
	}
	
}
