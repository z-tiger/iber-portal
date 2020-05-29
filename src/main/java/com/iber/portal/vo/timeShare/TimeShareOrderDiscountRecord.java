package com.iber.portal.vo.timeShare;

import java.util.Date;

public class TimeShareOrderDiscountRecord {
	private Integer id;

	private String orderId;

	private Date createTime;

	private String createUser;

	private Date updateTime;

	private String updateUser;

	private String category;

	private String remark;

	private String cashReturnStatus;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCashReturnStatus() {
		return cashReturnStatus;
	}

	public void setCashReturnStatus(String cashReturnStatus) {
		this.cashReturnStatus = cashReturnStatus;
	}

}
