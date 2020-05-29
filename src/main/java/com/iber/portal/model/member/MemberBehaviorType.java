package com.iber.portal.model.member;

import java.util.Date;

public class MemberBehaviorType {
	private Integer id;

	private String behaviorName;

	private Integer createId;

	private Date createTime;

	private Integer updateId;

	private Date updateTime;

	private String behaviorDetail;

	private String createName;

	private String updateName;

	private String complain;

	private String complainType;

	private String behaviorType;
	
	private String canAdd;

	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	public String getUpdateName() {
		return updateName;
	}

	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getBehaviorName() {
		return behaviorName;
	}

	public void setBehaviorName(String behaviorName) {
		this.behaviorName = behaviorName == null ? null : behaviorName.trim();
	}

	public Integer getCreateId() {
		return createId;
	}

	public void setCreateId(Integer createId) {
		this.createId = createId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getUpdateId() {
		return updateId;
	}

	public void setUpdateId(Integer updateId) {
		this.updateId = updateId;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getBehaviorDetail() {
		return behaviorDetail;
	}

	public void setBehaviorDetail(String behaviorDetail) {
		this.behaviorDetail = behaviorDetail == null ? null : behaviorDetail
				.trim();
	}

	public String getComplain() {
		return complain;
	}

	public void setComplain(String complain) {
		this.complain = complain;
	}

	public String getBehaviorType() {
		return behaviorType;
	}

	public void setBehaviorType(String behaviorType) {
		this.behaviorType = behaviorType;
	}

	public String getComplainType() {
		return complainType;
	}

	public void setComplainType(String complainType) {
		this.complainType = complainType;
	}

	public String getCanAdd() {
		return canAdd;
	}

	public void setCanAdd(String canAdd) {
		this.canAdd = canAdd;
	}

	@Override
	public String toString() {
		return "MemberBehaviorType [id=" + id + ", behaviorName="
				+ behaviorName + ", createId=" + createId + ", createTime="
				+ createTime + ", updateId=" + updateId + ", updateTime="
				+ updateTime + ", behaviorDetail=" + behaviorDetail
				+ ", createName=" + createName + ", updateName=" + updateName
				+ ", complain=" + complain + ", complainType=" + complainType
				+ ", behaviorType=" + behaviorType + ", canAdd=" + canAdd + "]";
	}

}