package com.iber.portal.model.dispatcher;

import java.util.Date;

public class Employee {
	private String name;
	private Integer id;
	private String photoUrl;
	private String status;//是否上班，1表示上班，2表示下班
	private Double longitude;
	private Double latitude;
	private Date createTime;
	private Integer createId;
	private Integer updateId;
	private Date updateTime;
	private String cityCode;
	private String phone;
	private String password;
	private String fingerUrl;
	private String type;//类型（1.调度员、2.救援员、3.维保员）
	private String cityName;
	private String gridName;
	private Integer isManager;//是否是网格管理员
	private String sex;//0表示男，1表示女
	private String profession;//0表示临时工，1表示正式工
	private Integer currentTask ;
	private Integer todayCompleteTask;
	private Integer processTask;
	private Integer totalCompleteTask;
    private String remark;
    private Integer identifyLabel;
    private String tboxFinger; // 三代指纹
	private String position;
	private String email;

	private String todayScore;
	private String monthScore;
	private String totalScore;

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getIdentifyLabel() {
		return identifyLabel;
	}
	public void setIdentifyLabel(Integer identifyLabel) {
		this.identifyLabel = identifyLabel;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getProfession() {
		return profession;
	}
	public void setProfession(String profession) {
		this.profession = profession;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getGridName() {
		return gridName;
	}
	public void setGridName(String gridName) {
		this.gridName = gridName;
	}
	public Integer getIsManager() {
		return isManager;
	}
	public void setIsManager(Integer isManager) {
		this.isManager = isManager;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getPhotoUrl() {
		return photoUrl;
	}
	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getCityCode() {
		return cityCode;
	}
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFingerUrl() {
		return fingerUrl;
	}
	public void setFingerUrl(String fingerUrl) {
		this.fingerUrl = fingerUrl;
	}
	public Integer getCurrentTask() {
		return currentTask;
	}
	public void setCurrentTask(Integer currentTask) {
		this.currentTask = currentTask;
	}
	public Integer getTodayCompleteTask() {
		return todayCompleteTask;
	}
	public void setTodayCompleteTask(Integer todayCompleteTask) {
		this.todayCompleteTask = todayCompleteTask;
	}
	public Integer getProcessTask() {
		return processTask;
	}
	public void setProcessTask(Integer processTask) {
		this.processTask = processTask;
	}
	public Integer getTotalCompleteTask() {
		return totalCompleteTask;
	}
	public void setTotalCompleteTask(Integer totalCompleteTask) {
		this.totalCompleteTask = totalCompleteTask;
	}

	public String getTboxFinger() {
		return tboxFinger;
	}

	public void setTboxFinger(String tboxFinger) {
		this.tboxFinger = tboxFinger;
	}

	public String getTodayScore() {
		return todayScore;
	}

	public void setTodayScore(String todayScore) {
		this.todayScore = todayScore;
	}

	public String getMonthScore() {
		return monthScore;
	}

	public void setMonthScore(String monthScore) {
		this.monthScore = monthScore;
	}

	public String getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(String totalScore) {
		this.totalScore = totalScore;
	}
}
