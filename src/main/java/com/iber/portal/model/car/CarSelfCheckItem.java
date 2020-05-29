package com.iber.portal.model.car;

import java.util.Date;

public class CarSelfCheckItem {
    private Integer id;

    private Integer itemType;

    private String itemName;

    private Integer status;

    private Date createTime;

    private Date updateTime;

    private Integer createUser;

    private String uname;
    
    private String appType ;
    
    private Integer exceptionUploadStatus;
    
    private Integer uploadStatus;

    private String samplePhotoUri;
    
    public String getAppType() {
		return appType;
	}

	public void setAppType(String appType) {
		this.appType = appType;
	}

	public Integer getExceptionUploadStatus() {
		return exceptionUploadStatus;
	}

	public void setExceptionUploadStatus(Integer exceptionUploadStatus) {
		this.exceptionUploadStatus = exceptionUploadStatus;
	}

	public Integer getUploadStatus() {
		return uploadStatus;
	}

	public void setUploadStatus(Integer uploadStatus) {
		this.uploadStatus = uploadStatus;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getItemType() {
        return itemType;
    }

    public void setItemType(Integer itemType) {
        this.itemType = itemType;
    }

    public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

    public String getSamplePhotoUri() {
        return samplePhotoUri;
    }

    public void setSamplePhotoUri(String samplePhotoUri) {
        this.samplePhotoUri = samplePhotoUri;
    }
}