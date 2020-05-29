package com.iber.portal.model.car;

import java.util.Date;

public class CarAccident {
	
	
	private Integer id;//   
	private Integer carId;//   x_car主键id
	private String lpn;//   车牌号码
	private Date startTime;//   事故处理开始时间
	private Date endTime;//   事故处理结束时间
	private String reason;//   事故原因
	private String responsiblePerson;//   负责人
	private String responsiblePersonPhone;//   负责人号码
	private String createUser;//   
	private Date createTime;//   
	private String updateUser;//   
	private Date updateTime;//   
	private String status;//   事故处理状态，1处理中，0恢复运营
	private String isInsurance;//   是否需报保险
	private String insuranceCode;//   保险单号
	private String result;//   处理结果
	private String orderId;
	private Integer money;
	private Integer handleByCustomer;
	private String memberPhone;
	private String predictTime;
	private String responsibility;
	private String assessmentTime;
	private String assessmentMoney;
	private String memberName;

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
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public Integer getMoney() {
		return money;
	}
	public void setMoney(Integer money) {
		this.money = money;
	}
	public Integer getHandleByCustomer() {
		return handleByCustomer;
	}
	public void setHandleByCustomer(Integer handleByCustomer) {
		this.handleByCustomer = handleByCustomer;
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
	public String getIsInsurance() {
		return isInsurance;
	}
	public void setIsInsurance(String isInsurance) {
		this.isInsurance = isInsurance;
	}
	public String getInsuranceCode() {
		return insuranceCode;
	}
	public void setInsuranceCode(String insuranceCode) {
		this.insuranceCode = insuranceCode;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}

	public String getPredictTime() {
		return predictTime;
	}

	public void setPredictTime(String predictTime) {
		this.predictTime = predictTime;
	}

	public String getResponsibility() {
		return responsibility;
	}

	public void setResponsibility(String responsibility) {
		this.responsibility = responsibility;
	}

	public String getAssessmentTime() {
		return assessmentTime;
	}

	public void setAssessmentTime(String assessmentTime) {
		this.assessmentTime = assessmentTime;
	}

	public String getAssessmentMoney() {
		return assessmentMoney;
	}

	public void setAssessmentMoney(String assessmentMoney) {
		this.assessmentMoney = assessmentMoney;
	}
}

