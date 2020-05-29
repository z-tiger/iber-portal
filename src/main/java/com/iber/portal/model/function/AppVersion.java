package com.iber.portal.model.function;

import java.util.Date;

public class AppVersion {
    private Integer id;

    private String appDesc;

    private String appName;

    private Integer currentRecord;

    private String versionNo;

    private Date publishDate;

    private String downloadUrl;

    private String isForceUpdate;

    private String appCategory;
    
    private Double appSize ;
    
    private String appType;//app类型

    public String getAppType() {
		return appType;
	}

	public void setAppType(String appType) {
		this.appType = appType;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAppDesc() {
        return appDesc;
    }

    public void setAppDesc(String appDesc) {
        this.appDesc = appDesc == null ? null : appDesc.trim();
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName == null ? null : appName.trim();
    }

    public Integer getCurrentRecord() {
        return currentRecord;
    }

    public void setCurrentRecord(Integer currentRecord) {
        this.currentRecord = currentRecord;
    }

    public String getVersionNo() {
        return versionNo;
    }

    public void setVersionNo(String versionNo) {
        this.versionNo = versionNo == null ? null : versionNo.trim();
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl == null ? null : downloadUrl.trim();
    }

    public String getIsForceUpdate() {
        return isForceUpdate;
    }

    public void setIsForceUpdate(String isForceUpdate) {
        this.isForceUpdate = isForceUpdate == null ? null : isForceUpdate.trim();
    }

    public String getAppCategory() {
        return appCategory;
    }

    public void setAppCategory(String appCategory) {
        this.appCategory = appCategory == null ? null : appCategory.trim();
    }

	public Double getAppSize() {
		return appSize;
	}

	public void setAppSize(Double appSize) {
		this.appSize = appSize;
	}
}