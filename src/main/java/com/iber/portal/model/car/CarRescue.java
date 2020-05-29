package com.iber.portal.model.car;

import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

public class CarRescue {
	private Integer id;//   
	private Integer carId;//   x_car主键id
	private String lpn;//   车牌号码
	private Date startTime;//   救援开始时间
	private Date endTime;//   救援结束时间
	private String reason;//   救援原因
	private String responsiblePerson;//   负责人
	private String responsiblePersonPhone;//   负责人号码
	private String createUser;//   
	private Date createTime;//   
	private String updateUser;//   
	private Date updateTime;//   
	private String status;//   救援状态，1救援中，0回复运营
	private String result;//   救援结果
    private String responsibleType;
	private String ifRepair;
	private MultipartFile picture;
	private String pictureUrl;
	private Integer picCount;
	private String behaviorTypeName; // 责任类型项名称
	private String responsibilityJudgeAdvice;//责任判定建议
	private String responsibleDescription;//责任类型说明
	private String matterProcess;//事件经过,也算是原因了
	private String rescueAddress;//救援地址
	private String carBranceType;
	private Integer taskId;
	private String orderId;
	private Integer memberLevel;
	private String memberName;
	private String memberPhone;
	private Integer memberId;
	
	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getMemberPhone() {
		return memberPhone;
	}

	public void setMemberPhone(String memberPhone) {
		this.memberPhone = memberPhone;
	}

	public Integer getMemberLevel() {
		return memberLevel;
	}

	public void setMemberLevel(Integer memberLevel) {
		this.memberLevel = memberLevel;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Integer getTaskId() {
		return taskId;
	}

	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}

	public String getCarBranceType() {
		return carBranceType;
	}

	public void setCarBranceType(String carBranceType) {
		this.carBranceType = carBranceType;
	}

	public String getResponsibilityJudgeAdvice() {
		return responsibilityJudgeAdvice;
	}

	public void setResponsibilityJudgeAdvice(String responsibilityJudgeAdvice) {
		this.responsibilityJudgeAdvice = responsibilityJudgeAdvice;
	}

	public String getResponsibleDescription() {
		return responsibleDescription;
	}

	public void setResponsibleDescription(String responsibleDescription) {
		this.responsibleDescription = responsibleDescription;
	}

	public String getMatterProcess() {
		return matterProcess;
	}

	public void setMatterProcess(String matterProcess) {
		this.matterProcess = matterProcess;
	}

	public String getRescueAddress() {
		return rescueAddress;
	}

	public void setRescueAddress(String rescueAddress) {
		this.rescueAddress = rescueAddress;
	}

	public String getBehaviorTypeName() {
		return behaviorTypeName;
	}
	public void setBehaviorTypeName(String behaviorTypeName) {
		this.behaviorTypeName = behaviorTypeName;
	}
	public Integer getPicCount() {
		return picCount;
	}
	public void setPicCount(Integer picCount) {
		this.picCount = picCount;
	}
	public MultipartFile getPicture() {
		return picture;
	}
	public void setPicture(MultipartFile picture) {
		this.picture = picture;
	}
	public String getPictureUrl() {
		return pictureUrl;
	}
	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}
	public String getResponsibleType() {
		return responsibleType;
	}
	public void setResponsibleType(String responsibleType) {
		this.responsibleType = responsibleType;
	}
	public String getIfRepair() {
		return ifRepair;
	}
	public void setIfRepair(String ifRepair) {
		this.ifRepair = ifRepair;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getCarId() {
		return carId;
	}
	public void setCarId(Integer carId) {
		this.carId = carId;
	}
	public String getLpn() {
		return lpn;
	}
	public void setLpn(String lpn) {
		this.lpn = lpn;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getResponsiblePerson() {
		return responsiblePerson;
	}
	public void setResponsiblePerson(String responsiblePerson) {
		this.responsiblePerson = responsiblePerson;
	}
	public String getResponsiblePersonPhone() {
		return responsiblePersonPhone;
	}
	public void setResponsiblePersonPhone(String responsiblePersonPhone) {
		this.responsiblePersonPhone = responsiblePersonPhone;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getUpdateUser() {
		return updateUser;
	}
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
}

