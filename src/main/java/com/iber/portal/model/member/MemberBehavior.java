package com.iber.portal.model.member;

import java.util.Date;

public class MemberBehavior {
	private Integer id;

	private String name;

	private String contriValue;

	private Integer sort;

	private String contriDetal;

	private Integer createId;

	private Date createTime;

	private Integer updateId;

	private Date updateTime;

	private Integer behaviorId;

	private Integer isIncrease;

	private Integer isRatio;

	private String memberComplain;

	private String employeeComplain;

	private String conditionVal;

	private String conditionType;

	private String behaviorType;

	private String createName;

	private String updateName;
	
	private Integer status;
	
	private String type;
	
	private String canAdd;

	public String getBehaviorType() {
		return behaviorType;
	}

	public void setBehaviorType(String behaviorType) {
		this.behaviorType = behaviorType;
	}

	public Integer getIsIncrease() {
		return isIncrease;
	}

	public void setIsIncrease(Integer isIncrease) {
		this.isIncrease = isIncrease;
	}

	public Integer getIsRatio() {
		return isRatio;
	}

	public void setIsRatio(Integer isRatio) {
		this.isRatio = isRatio;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public String getContriValue() {
		return contriValue;
	}

	public void setContriValue(String contriValue) {
		this.contriValue = contriValue == null ? null : contriValue.trim();
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getContriDetal() {
		return contriDetal;
	}

	public void setContriDetal(String contriDetal) {
		this.contriDetal = contriDetal == null ? null : contriDetal.trim();
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

	public Integer getBehaviorId() {
		return behaviorId;
	}

	public void setBehaviorId(Integer behaviorId) {
		this.behaviorId = behaviorId;
	}

	public String getConditionVal() {
		return conditionVal;
	}

	public void setConditionVal(String conditionVal) {
		this.conditionVal = conditionVal;
	}

	public String getConditionType() {
		return conditionType;
	}

	public void setConditionType(String conditionType) {
		this.conditionType = conditionType;
	}

	public String getMemberComplain() {
		return memberComplain;
	}

	public void setMemberComplain(String memberComplain) {
		this.memberComplain = memberComplain;
	}

	public String getEmployeeComplain() {
		return employeeComplain;
	}

	public void setEmployeeComplain(String employeeComplain) {
		this.employeeComplain = employeeComplain;
	}

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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCanAdd() {
		return canAdd;
	}

	public void setCanAdd(String canAdd) {
		this.canAdd = canAdd;
	}
	
}