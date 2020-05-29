package com.iber.portal.model.member;

import java.util.Date;


public class EvidenceRelation {
	
		private java.lang.Integer id;//   	private java.lang.Integer reportId;//   举报id	private java.lang.String pictureEvidenceUrl;//   图片凭证url	private java.lang.Integer isMemberComplain;//   是否是会员举报，0表示是，1表示否
	private Date createTime;
	
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public java.lang.Integer getId() {
		return id;
	}
	public void setId(java.lang.Integer id) {
		this.id = id;
	}
	public java.lang.Integer getReportId() {
		return reportId;
	}
	public void setReportId(java.lang.Integer reportId) {
		this.reportId = reportId;
	}
	public java.lang.String getPictureEvidenceUrl() {
		return pictureEvidenceUrl;
	}
	public void setPictureEvidenceUrl(java.lang.String pictureEvidenceUrl) {
		this.pictureEvidenceUrl = pictureEvidenceUrl;
	}
	public java.lang.Integer getIsMemberComplain() {
		return isMemberComplain;
	}
	public void setIsMemberComplain(java.lang.Integer isMemberComplain) {
		this.isMemberComplain = isMemberComplain;
	}	
}

