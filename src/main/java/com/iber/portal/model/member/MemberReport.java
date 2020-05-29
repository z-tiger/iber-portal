package com.iber.portal.model.member;

import java.util.Date;

public class MemberReport {
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

    private String auditExplain;

    private String remark;
    
    private Date auditTime;

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

	public Date getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}

	@Override
	public String toString() {
		return "MemberReport [id=" + id + ", createId=" + createId
				+ ", reportedMemberId=" + reportedMemberId
				+ ", behaviorParentId=" + behaviorParentId
				+ ", behaviorChildrenId=" + behaviorChildrenId
				+ ", createTime=" + createTime + ", lpn=" + lpn + ", status="
				+ status + ", isMemberComplain=" + isMemberComplain
				+ ", updateId=" + updateId + ", updateTime=" + updateTime
				+ ", parkId=" + parkId + ", parkNo=" + parkNo + ", cityCode="
				+ cityCode + ", lastOrderId=" + lastOrderId + ", lastCarType="
				+ lastCarType + ", auditId=" + auditId + ", auditExplain="
				+ auditExplain + ", remark=" + remark + ", auditTime="
				+ auditTime + "]";
	}
	
}