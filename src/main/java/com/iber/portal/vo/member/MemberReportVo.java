package com.iber.portal.vo.member;

import java.util.Date;

public class MemberReportVo {

	private Integer id;

	private Integer createId;

	private Integer reportedMemberId;

	private Integer behaviorParentId;

	private Integer behaviorChildrenId;

	private Date createTime;

	private String lpn;

	private Integer status;

	private Integer isMemberComplain;

	private Integer updateId;

	private Date updateTime;

	private String parkId;

	private String parkNo;

	private String cityCode;

	private String lastOrderId;

	private String lastCarType;

	private Integer auditId;
	
	private Date auditTime;

	private String auditExplain;

	private String remark;

	private String cityName;

	private String reportedMemberName;

	private String reportedPhone;

	private String parkName;

	private String behaviorType;

	private String behaviorName;

	private String createName;
	
	private String typeCode;
	
	private String auditName;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCreateId() {
		return createId;
	}

	public void setCreateId(Integer createId) {
		this.createId = createId;
	}

	public Integer getReportedMemberId() {
		return reportedMemberId;
	}

	public void setReportedMemberId(Integer reportedMemberId) {
		this.reportedMemberId = reportedMemberId;
	}

	public Integer getBehaviorParentId() {
		return behaviorParentId;
	}

	public void setBehaviorParentId(Integer behaviorParentId) {
		this.behaviorParentId = behaviorParentId;
	}

	public Integer getBehaviorChildrenId() {
		return behaviorChildrenId;
	}

	public void setBehaviorChildrenId(Integer behaviorChildrenId) {
		this.behaviorChildrenId = behaviorChildrenId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getLpn() {
		return lpn;
	}

	public void setLpn(String lpn) {
		this.lpn = lpn == null ? null : lpn.trim();
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getIsMemberComplain() {
		return isMemberComplain;
	}

	public void setIsMemberComplain(Integer isMemberComplain) {
		this.isMemberComplain = isMemberComplain;
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

	public String getParkId() {
		return parkId;
	}

	public void setParkId(String parkId) {
		this.parkId = parkId == null ? null : parkId.trim();
	}

	public String getParkNo() {
		return parkNo;
	}

	public void setParkNo(String parkNo) {
		this.parkNo = parkNo == null ? null : parkNo.trim();
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode == null ? null : cityCode.trim();
	}

	public String getLastOrderId() {
		return lastOrderId;
	}

	public void setLastOrderId(String lastOrderId) {
		this.lastOrderId = lastOrderId == null ? null : lastOrderId.trim();
	}

	public String getLastCarType() {
		return lastCarType;
	}

	public void setLastCarType(String lastCarType) {
		this.lastCarType = lastCarType == null ? null : lastCarType.trim();
	}

	public Integer getAuditId() {
		return auditId;
	}

	public void setAuditId(Integer auditId) {
		this.auditId = auditId;
	}

	public String getAuditExplain() {
		return auditExplain;
	}

	public void setAuditExplain(String auditExplain) {
		this.auditExplain = auditExplain == null ? null : auditExplain.trim();
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getReportedMemberName() {
		return reportedMemberName;
	}

	public void setReportedMemberName(String reportedMemberName) {
		this.reportedMemberName = reportedMemberName;
	}

	public String getReportedPhone() {
		return reportedPhone;
	}

	public void setReportedPhone(String reportedPhone) {
		this.reportedPhone = reportedPhone;
	}

	public String getParkName() {
		return parkName;
	}

	public void setParkName(String parkName) {
		this.parkName = parkName;
	}

	public String getBehaviorType() {
		return behaviorType;
	}

	public void setBehaviorType(String behaviorType) {
		this.behaviorType = behaviorType;
	}

	public String getBehaviorName() {
		return behaviorName;
	}

	public void setBehaviorName(String behaviorName) {
		this.behaviorName = behaviorName;
	}

	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	public String getAuditName() {
		return auditName;
	}

	public void setAuditName(String auditName) {
		this.auditName = auditName;
	}

	public Date getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}

}
