package com.iber.portal.model.versions;

import java.util.Date;

public class VersionsEmployee {
	private Integer id;

	private String categoryCode;

	private String versionName;

	private String versionNo;

	private Integer versionRecord;

	private Integer createId;

	private Date createTime;

	private Integer updateId;

	private Date updateTime;

	private String versionUrl;

	private String versionDetail;
	
	private String categoryName;
	
	private String createName;
	
	private String updateName;
    
	
	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	public String getUpdateName() {
		return updateName;
	}

	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCategoryCode() {
		return categoryCode;
	}

	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode == null ? null : categoryCode.trim();
	}

	public String getVersionName() {
		return versionName;
	}

	public void setVersionName(String versionName) {
		this.versionName = versionName == null ? null : versionName.trim();
	}

	public String getVersionNo() {
		return versionNo;
	}

	public void setVersionNo(String versionNo) {
		this.versionNo = versionNo == null ? null : versionNo.trim();
	}

	public Integer getVersionRecord() {
		return versionRecord;
	}

	public void setVersionRecord(Integer versionRecord) {
		this.versionRecord = versionRecord;
	}

	public Integer getCreateId() {
		return createId;
	}

	public void setCreateId(Integer createId) {
		this.createId = createId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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

	public String getVersionUrl() {
		return versionUrl;
	}

	public void setVersionUrl(String versionUrl) {
		this.versionUrl = versionUrl == null ? null : versionUrl.trim();
	}

	public String getVersionDetail() {
		return versionDetail;
	}

	public void setVersionDetail(String versionDetail) {
		this.versionDetail = versionDetail == null ? null : versionDetail
				.trim();
	}

}
