package com.iber.portal.model.car;
//com.iber.portal.model.car.CarOfflineApply

import java.util.Date;

/**
 * @Author:cuichongc
 * @Version:1.0
 */
public class CarOfflineApply {
	
	private Integer id;
	
	private Integer carId;
	
	private String lpn;
	
	private Date endTime;
	
	private Integer parkId;
	
	private String reason;
	
	private String applicant;
	
	private String applicantPhone;
	
	private Date createTime;
	
	private String createUser;
	
	private String offLineType;
	
	private String status;
	
	private String updateUser;
	
	private Date updateTime;
	
	private String cityCode;
	
	private String auditHuman;//审核人
	
	private String auditResult;//审核结果 1 通过  2 拒绝
	
	private String feedback;//审核反馈
	
	private String cname;
	
	private String pname;
	
	private Integer employeeId;
	
	private String cstatus;
	
	private Double restBattery;
	
	private Date cupdateTime;
	
	private String smallBatteryVoltage;
	
	private Integer offlineCategory;
	
	private String preoffline;
	
	public String getPreoffline() {
		return preoffline;
	}

	public void setPreoffline(String preoffline) {
		this.preoffline = preoffline;
	}

	public Integer getOfflineCategory() {
		return offlineCategory;
	}

	public void setOfflineCategory(Integer offlineCategory) {
		this.offlineCategory = offlineCategory;
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

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Integer getParkId() {
		return parkId;
	}

	public void setParkId(Integer parkId) {
		this.parkId = parkId;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getApplicant() {
		return applicant;
	}

	public void setApplicant(String applicant) {
		this.applicant = applicant;
	}

	public String getApplicantPhone() {
		return applicantPhone;
	}

	public void setApplicantPhone(String applicantPhone) {
		this.applicantPhone = applicantPhone;
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
	
	public String getOffLineType() {
		return offLineType;
	}

	public void setOffLineType(String offLineType) {
		this.offLineType = offLineType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	
	public String getAuditHuman() {
		return auditHuman;
	}

	public void setAuditHuman(String auditHuman) {
		this.auditHuman = auditHuman;
	}

	public String getAuditResult() {
		return auditResult;
	}

	public void setAuditResult(String auditResult) {
		this.auditResult = auditResult;
	}

	public String getFeedback() {
		return feedback;
	}

	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}

	public Integer getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}

	public String getCstatus() {
		return cstatus;
	}

	public void setCstatus(String cstatus) {
		this.cstatus = cstatus;
	}

	public Double getRestBattery() {
		return restBattery;
	}

	public void setRestBattery(Double restBattery) {
		this.restBattery = restBattery;
	}

	public Date getCupdateTime() {
		return cupdateTime;
	}

	public void setCupdateTime(Date cupdateTime) {
		this.cupdateTime = cupdateTime;
	}

	public String getSmallBatteryVoltage() {
		return smallBatteryVoltage;
	}

	public void setSmallBatteryVoltage(String smallBatteryVoltage) {
		this.smallBatteryVoltage = smallBatteryVoltage;
	}
	
	
}
