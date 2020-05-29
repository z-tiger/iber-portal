package com.iber.portal.model.insurance;

import java.util.Date;

public class Insurance {
    private Integer id;

    private String insuranceNo;

    private Date startTime;

    private Date endTime;

    private String insuranceRange;

    private String remark;

    private String insuranceCompany;

    private String lpn;

    private String insuranceHolder;

    private String insuranceHolderIdcard;

    private Integer insuranceMoney;

    private String insuranceType;

    private Date createTime;

    private String createUser;

    private Date updateTime;

    private String updateUser;

    private Integer isAttachment;
    
    private Integer status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getInsuranceNo() {
        return insuranceNo;
    }

    public void setInsuranceNo(String insuranceNo) {
        this.insuranceNo = insuranceNo == null ? null : insuranceNo.trim();
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

    public String getInsuranceRange() {
        return insuranceRange;
    }

    public void setInsuranceRange(String insuranceRange) {
        this.insuranceRange = insuranceRange == null ? null : insuranceRange.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getInsuranceCompany() {
        return insuranceCompany;
    }

    public void setInsuranceCompany(String insuranceCompany) {
        this.insuranceCompany = insuranceCompany == null ? null : insuranceCompany.trim();
    }

    public String getLpn() {
        return lpn;
    }

    public void setLpn(String lpn) {
        this.lpn = lpn == null ? null : lpn.trim();
    }

    public String getInsuranceHolder() {
        return insuranceHolder;
    }

    public void setInsuranceHolder(String insuranceHolder) {
        this.insuranceHolder = insuranceHolder == null ? null : insuranceHolder.trim();
    }

    public String getInsuranceHolderIdcard() {
        return insuranceHolderIdcard;
    }

    public void setInsuranceHolderIdcard(String insuranceHolderIdcard) {
        this.insuranceHolderIdcard = insuranceHolderIdcard == null ? null : insuranceHolderIdcard.trim();
    }

    public Integer getInsuranceMoney() {
        return insuranceMoney;
    }

    public void setInsuranceMoney(Integer insuranceMoney) {
        this.insuranceMoney = insuranceMoney;
    }

    public String getInsuranceType() {
        return insuranceType;
    }

    public void setInsuranceType(String insuranceType) {
        this.insuranceType = insuranceType == null ? null : insuranceType.trim();
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
        this.createUser = createUser == null ? null : createUser.trim();
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
        this.updateUser = updateUser == null ? null : updateUser.trim();
    }

    public Integer getIsAttachment() {
        return isAttachment;
    }

    public void setIsAttachment(Integer isAttachment) {
        this.isAttachment = isAttachment;
    }

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
    
}