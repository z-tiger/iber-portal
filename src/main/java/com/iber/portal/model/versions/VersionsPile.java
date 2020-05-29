package com.iber.portal.model.versions;

import java.util.Date;

/**
 * 
 * @author zixiaobang
 * @date 2017.03.22
 *
 */
public class VersionsPile {
	private Integer id;

	private String categoryCode;

	private String versionName;

	private String versionUrl;

	private String versionNo;

	private Integer versionRecord;

	private Integer createId;

	private Date createTime;

	private Integer updateId;

	private Date updateTime;

	private String remark;

	private String isIncrement;

	private String createName;

	private String updateName;
	
	private String categoryName;

	public VersionsPile() {
	}

	public VersionsPile(String categoryCode, String versionName,
			String versionUrl, String versionNo, Integer versionRecord,
			Integer createId, Date createTime, Integer updateId,
			Date updateTime, String remark, String isIncrement) {
		super();
		this.categoryCode = categoryCode;
		this.versionName = versionName;
		this.versionUrl = versionUrl;
		this.versionNo = versionNo;
		this.versionRecord = versionRecord;
		this.createId = createId;
		this.createTime = createTime;
		this.updateId = updateId;
		this.updateTime = updateTime;
		this.remark = remark;
		this.isIncrement = isIncrement;
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

	public String getVersionUrl() {
		return versionUrl;
	}

	public void setVersionUrl(String versionUrl) {
		this.versionUrl = versionUrl == null ? null : versionUrl.trim();
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}

	public String getIsIncrement() {
		return isIncrement;
	}

	public void setIsIncrement(String isIncrement) {
		this.isIncrement = isIncrement == null ? null : isIncrement.trim();
	}

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

	@Override
	public String toString() {
		return "VersionsPile [id=" + id + ", categoryCode=" + categoryCode
				+ ", versionName=" + versionName + ", versionUrl=" + versionUrl
				+ ", versionNo=" + versionNo + ", versionRecord="
				+ versionRecord + ", createId=" + createId + ", createTime="
				+ createTime + ", updateId=" + updateId + ", updateTime="
				+ updateTime + ", remark=" + remark + ", isIncrement="
				+ isIncrement + ", createName=" + createName + ", updateName="
				+ updateName + ", categoryName=" + categoryName + "]";
	}
}