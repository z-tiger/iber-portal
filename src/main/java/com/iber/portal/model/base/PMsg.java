package com.iber.portal.model.base;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

public class PMsg {
    private Integer id;

    private String msgTitle;

    private String msgContent;

    private Date createTime;
    
    private String createTimeStr;

    private String createUser;

    private Date updateTime;
    
    private String updateTimeStr;

    private String updateUser;

    private String msgFirstP;

    private String msgUrl;
    
    private String msgFirstPS;
    
    private String msgUrlS;

    private Integer clickRecords;

    private String msgStatus;

    private Date auditTime;
    
    private String auditTimeStr;

    private String auditUser;
    
    private MultipartFile uploadFile;
    
    private MultipartFile msgPicture;
    
    public MultipartFile getMsgPicture() {
		return msgPicture;
	}

	public void setMsgPicture(MultipartFile msgPicture) {
		this.msgPicture = msgPicture;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMsgTitle() {
        return msgTitle;
    }

    public void setMsgTitle(String msgTitle) {
        this.msgTitle = msgTitle == null ? null : msgTitle.trim();
    }

    public String getMsgContent() {
        return msgContent;
    }

    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent == null ? null : msgContent.trim();
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

    public String getMsgFirstP() {
        return msgFirstP;
    }

    public void setMsgFirstP(String msgFirstP) {
        this.msgFirstP = msgFirstP == null ? null : msgFirstP.trim();
    }

    public String getMsgUrl() {
        return msgUrl;
    }

    public void setMsgUrl(String msgUrl) {
        this.msgUrl = msgUrl == null ? null : msgUrl.trim();
    }

    public Integer getClickRecords() {
        return clickRecords;
    }

    public void setClickRecords(Integer clickRecords) {
        this.clickRecords = clickRecords;
    }

    public String getMsgStatus() {
        return msgStatus;
    }

    public void setMsgStatus(String msgStatus) {
        this.msgStatus = msgStatus == null ? null : msgStatus.trim();
    }

    public Date getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
    }

    public String getAuditUser() {
        return auditUser;
    }

    public void setAuditUser(String auditUser) {
        this.auditUser = auditUser == null ? null : auditUser.trim();
    }

	public String getCreateTimeStr() {
		return createTimeStr;
	}

	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}

	public String getUpdateTimeStr() {
		return updateTimeStr;
	}

	public void setUpdateTimeStr(String updateTimeStr) {
		this.updateTimeStr = updateTimeStr;
	}

	public String getAuditTimeStr() {
		return auditTimeStr;
	}

	public void setAuditTimeStr(String auditTimeStr) {
		this.auditTimeStr = auditTimeStr;
	}
    
	public void setUploadFile(MultipartFile uploadFile) {
		this.uploadFile = uploadFile;
	}
	
	public MultipartFile getUploadFile() {
		return uploadFile;
	}

	public String getMsgFirstPS() {
		return msgFirstPS;
	}

	public void setMsgFirstPS(String msgFirstPS) {
		this.msgFirstPS = msgFirstPS;
	}

	public String getMsgUrlS() {
		return msgUrlS;
	}

	public void setMsgUrlS(String msgUrlS) {
		this.msgUrlS = msgUrlS;
	}
	
}