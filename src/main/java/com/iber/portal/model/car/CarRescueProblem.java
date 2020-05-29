package com.iber.portal.model.car;

import java.util.Date;

public class CarRescueProblem {
	private Integer id;
	private Date createTime;
	private Date updateTime;
	private String remark;
	private Integer carRescueId;
	private int dicId;
	private String problemDescription;
	private String createUser;
	private String updateUser;
	private String dicCode;

	public String getDicCode() {
		return dicCode;
	}

	public void setDicCode(String dicCode) {
		this.dicCode = dicCode;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getCarRescueId() {
		return carRescueId;
	}

	public void setCarRescueId(Integer carRescueId) {
		this.carRescueId = carRescueId;
	}

	public int getDicId() {
		return dicId;
	}

	public void setDicId(int dicId) {
		this.dicId = dicId;
	}

	public String getProblemDescription() {
		return problemDescription;
	}

	public void setProblemDescription(String problemDescription) {
		this.problemDescription = problemDescription;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}