package com.iber.portal.model.deposit;

import java.util.Date;

public class Deposit {
   
	private Integer id;
	
	private String memberLevel;
	
	private Integer depositValue;
	
	private String driverAge;
	
	private String sesameCredit;
	
	private String detail;
	
	private Date createTime;
	
	private Date updateTime;
	
	private Integer createId;
	
	private Integer updateId;
	
	private String createName;
	
	private String updateName;
	
	private Integer sesameGrade;
	
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

	public Integer getCreateId() {
		return createId;
	}

	public void setCreateId(Integer createId) {
		this.createId = createId;
	}

	public Integer getUpdateId() {
		return updateId;
	}

	public void setUpdateId(Integer updateId) {
		this.updateId = updateId;
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

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMemberLevel() {
		return memberLevel;
	}

	public void setMemberLevel(String memberLevel) {
		this.memberLevel = memberLevel;
	}

	public Integer getDepositValue() {
		return depositValue;
	}

	public void setDepositValue(Integer depositValue) {
		this.depositValue = depositValue;
	}

	public String getDriverAge() {
		return driverAge;
	}

	public void setDriverAge(String driverAge) {
		this.driverAge = driverAge;
	}

	public String getSesameCredit() {
		return sesameCredit;
	}

	public void setSesameCredit(String sesameCredit) {
		this.sesameCredit = sesameCredit;
	}

	public Integer getSesameGrade() {
		return sesameGrade;
	}

	public void setSesameGrade(Integer sesameGrade) {
		this.sesameGrade = sesameGrade;
	}
}
