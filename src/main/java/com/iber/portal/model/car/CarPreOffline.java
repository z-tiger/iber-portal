package com.iber.portal.model.car;

import java.util.Date;

public class CarPreOffline {
	private Integer id;
	private String lpn;
	private Integer principalId;  // 该车辆预下线记录负责人id，同是默认是该记录的创建人
	private String principalName; // 平台操作人姓名
	private String principalPhone;// 平台操作人电话
	private Date createTime;     // 创建时间
	private Date updateTime;     // 最近更新时间
	private String createUser;   // 该记录的创建人姓名
	private String updateUser;
	private Integer updateUserId;//记录修改人
	private Date endTime;        // 结束时间
	private Integer offlineType;  // 车辆的下线类型,1维修，2维护，3 补电
	private String reason;        // 下线原因
	private Date predictTime;   // 车辆预计上线时间
	private Integer status;    // 该记录的状态
	private String cityCode;

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public Integer getUpdateUserId() {
		return updateUserId;
	}

	public void setUpdateUserId(Integer updateUserId) {
		this.updateUserId = updateUserId;
	}

	public Integer getPrincipalId() {
		return principalId;
	}

	public void setPrincipalId(Integer principalId) {
		this.principalId = principalId;
	}

	public String getPrincipalName() {
		return principalName;
	}

	public void setPrincipalName(String principalName) {
		this.principalName = principalName;
	}

	public String getPrincipalPhone() {
		return principalPhone;
	}

	public void setPrincipalPhone(String principalPhone) {
		this.principalPhone = principalPhone;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLpn() {
		return lpn;
	}

	public void setLpn(String lpn) {
		this.lpn = lpn;
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

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Integer getOfflineType() {
		return offlineType;
	}

	public void setOfflineType(Integer offlineType) {
		this.offlineType = offlineType;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Date getPredictTime() {
		return predictTime;
	}

	public void setPredictTime(Date predictTime) {
		this.predictTime = predictTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
