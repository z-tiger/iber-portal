package com.iber.portal.model.task;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * <br>
 * <b>功能：</b>TaskInfoEntity<br>
 * <b>作者：</b>www.jeecg.org<br>
 * <b>日期：</b> Feb 2, 2013 <br>
 * <b>版权所有：<b>版权所有(C) 2013，www.jeecg.org<br>
 */
public class TaskInfo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private java.lang.Integer id;//   
	private java.lang.Integer employeeId;//   员工id
	private java.lang.Integer beginParkId;//   开始网点
	private java.lang.Integer endParkId;//   结束网点
	private java.lang.String lpn;//   车牌号
	private java.util.Date taskBeginTime;//   任务开始时间
	private java.util.Date predictDoneTime;//   预计完成时间
	private java.lang.String taskExplain;//   任务说明
	private java.lang.String taskLevel;//   任务等级
	private java.lang.String taskType;//   任务类型
	private java.lang.String doneRemark;//   完成反馈
	private java.lang.String status;//   状态
	private java.util.Date doneTime;//   完成时间
	private java.lang.Integer createId;//   创建人
	private java.util.Date createTime;//   创建时间
	private java.lang.Integer updateId;//   更新人
	private java.util.Date updateTime;//   更新时间
	private java.lang.String cityCode;//   城市编码
	private String taskName;//任务名称
	private String employeeLeader;//执行人上级
	private String employeeName;//执行人
	private String gridName;
	private String taskLevelName;//任务等级名称
	private String cityName;
	private String timeLimit;//时间限制
	private String taskTypeName;//任务类型
	private String beginParkName;//所在网点
	private String endParkName;//目标网点
	private Integer lpnId;//车牌对应的车辆id
	private Integer gridId;
	private String address;//救援车辆地址
	private Double latitude;//救援车辆纬度
	private Double longitude;//救援车辆经度
	private Integer memberId;
	private Date deadline;
	private String score;
	private String photoUrl;
	private Double planMileage;
	
	public Date getDeadline() {
		return deadline;
	}
	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	public Integer getMemberId() {
		return memberId;
	}
	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}
	public Integer getGridId() {
		return gridId;
	}
	public void setGridId(Integer gridId) {
		this.gridId = gridId;
	}
	public Integer getLpnId() {
		return lpnId;
	}
	public void setLpnId(Integer lpnId) {
		this.lpnId = lpnId;
	}
	public String getTimeLimit() {
		return timeLimit;
	}
	public void setTimeLimit(String timeLimit) {
		this.timeLimit = timeLimit;
	}
	public String getTaskTypeName() {
		return taskTypeName;
	}
	public void setTaskTypeName(String taskTypeName) {
		this.taskTypeName = taskTypeName;
	}
	public String getBeginParkName() {
		return beginParkName;
	}
	public void setBeginParkName(String beginParkName) {
		this.beginParkName = beginParkName;
	}
	public String getEndParkName() {
		return endParkName;
	}
	public void setEndParkName(String endParkName) {
		this.endParkName = endParkName;
	}
	public java.lang.Integer getId() {
		return id;
	}
	public void setId(java.lang.Integer id) {
		this.id = id;
	}
	public java.lang.Integer getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(java.lang.Integer employeeId) {
		this.employeeId = employeeId;
	}
	public java.lang.Integer getBeginParkId() {
		return beginParkId;
	}
	public void setBeginParkId(java.lang.Integer beginParkId) {
		this.beginParkId = beginParkId;
	}
	public java.lang.Integer getEndParkId() {
		return endParkId;
	}
	public void setEndParkId(java.lang.Integer endParkId) {
		this.endParkId = endParkId;
	}
	public java.lang.String getLpn() {
		return lpn;
	}
	public void setLpn(java.lang.String lpn) {
		this.lpn = lpn;
	}
	public java.util.Date getTaskBeginTime() {
		return taskBeginTime;
	}
	public void setTaskBeginTime(java.util.Date taskBeginTime) {
		this.taskBeginTime = taskBeginTime;
	}
	public java.util.Date getPredictDoneTime() {
		return predictDoneTime;
	}
	public void setPredictDoneTime(java.util.Date predictDoneTime) {
		this.predictDoneTime = predictDoneTime;
	}
	public java.lang.String getTaskExplain() {
		return taskExplain;
	}
	public void setTaskExplain(java.lang.String taskExplain) {
		this.taskExplain = taskExplain;
	}
	public java.lang.String getTaskLevel() {
		return taskLevel;
	}
	public void setTaskLevel(java.lang.String taskLevel) {
		this.taskLevel = taskLevel;
	}
	public java.lang.String getTaskType() {
		return taskType;
	}
	public void setTaskType(java.lang.String taskType) {
		this.taskType = taskType;
	}
	public java.lang.String getDoneRemark() {
		return doneRemark;
	}
	public void setDoneRemark(java.lang.String doneRemark) {
		this.doneRemark = doneRemark;
	}
	public java.lang.String getStatus() {
		return status;
	}
	public void setStatus(java.lang.String status) {
		this.status = status;
	}
	public java.util.Date getDoneTime() {
		return doneTime;
	}
	public void setDoneTime(java.util.Date doneTime) {
		this.doneTime = doneTime;
	}
	public java.lang.Integer getCreateId() {
		return createId;
	}
	public void setCreateId(java.lang.Integer createId) {
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
	public java.lang.String getCityCode() {
		return cityCode;
	}
	public void setCityCode(java.lang.String cityCode) {
		this.cityCode = cityCode;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public String getEmployeeLeader() {
		return employeeLeader;
	}
	public void setEmployeeLeader(String employeeLeader) {
		this.employeeLeader = employeeLeader;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public String getGridName() {
		return gridName;
	}
	public void setGridName(String gridName) {
		this.gridName = gridName;
	}
	public String getTaskLevelName() {
		return taskLevelName;
	}
	public void setTaskLevelName(String taskLevelName) {
		this.taskLevelName = taskLevelName;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

	public Double getPlanMileage() {
		return planMileage;
	}

	public void setPlanMileage(Double planMileage) {
		this.planMileage = planMileage;
	}
}

