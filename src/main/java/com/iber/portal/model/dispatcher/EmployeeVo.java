package com.iber.portal.model.dispatcher;

public class EmployeeVo {
	private String name;
	private String type;//员工类型
	private String status;//是否是上班状态
	private Integer currentTask;//当前任务数
	private Integer processTask;//正在执行任务数
	private Integer todayCompleteTask;//今日完成数
	private Integer totalCompleteTask;//累计完成任务数
	private Double longitude;//经度
	private Double latitude;//纬度
	private String photoUrl;
	private Integer id;
	private String statusImg;
	private String address;
	private String phone;
	private String lpn; //正在用车车牌
	private String usingCarStatus;
	private String identifyLabel;
	public String getIdentifyLabel() {
		return identifyLabel;
	}
	public void setIdentifyLabel(String identifyLabel) {
		this.identifyLabel = identifyLabel;
	}
	public String getUsingCarStatus() {
		return usingCarStatus;
	}
	public void setUsingCarStatus(String usingCarStatus) {
		this.usingCarStatus = usingCarStatus;
	}
	public String getLpn() {
		return lpn;
	}
	public void setLpn(String lpn) {
		this.lpn = lpn;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getStatusImg() {
		return statusImg;
	}
	public void setStatusImg(String statusImg) {
		this.statusImg = statusImg;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Integer getCurrentTask() {
		return currentTask;
	}
	public void setCurrentTask(Integer currentTask) {
		this.currentTask = currentTask;
	}
	public Integer getProcessTask() {
		return processTask;
	}
	public void setProcessTask(Integer processTask) {
		this.processTask = processTask;
	}
	public Integer getTodayCompleteTask() {
		return todayCompleteTask;
	}
	public void setTodayCompleteTask(Integer todayCompleteTask) {
		this.todayCompleteTask = todayCompleteTask;
	}
	public Integer getTotalCompleteTask() {
		return totalCompleteTask;
	}
	public void setTotalCompleteTask(Integer totalCompleteTask) {
		this.totalCompleteTask = totalCompleteTask;
	}
	
}
