package com.iber.portal.model.base;

import java.util.Date;

public class MemberRefundWorderDetail {
    private Integer id;

    private Integer rid;

    private String auditor;

    private String auditorRoleId;

    private Date auditorDatetime;
    
    private String auditorDatetimeStr;

    private String auditorResult;

    private String auditorRemark;

    private String auditorAccessoryFile;

    private String auditorAccessoryFilename;
    
    private Date applyTime;

    public Date getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRid() {
        return rid;
    }

    public void setRid(Integer rid) {
        this.rid = rid;
    }

    public String getAuditor() {
        return auditor;
    }

    public void setAuditor(String auditor) {
        this.auditor = auditor == null ? null : auditor.trim();
    }

    public String getAuditorRoleId() {
        return auditorRoleId;
    }

    public void setAuditorRoleId(String auditorRoleId) {
        this.auditorRoleId = auditorRoleId == null ? null : auditorRoleId.trim();
    }

    public Date getAuditorDatetime() {
        return auditorDatetime;
    }

    public void setAuditorDatetime(Date auditorDatetime) {
        this.auditorDatetime = auditorDatetime;
    }
    
    public void setAuditorDatetimeStr(String auditorDatetimeStr) {
		this.auditorDatetimeStr = auditorDatetimeStr;
	}
    
    public String getAuditorDatetimeStr() {
		return auditorDatetimeStr;
	}

    public String getAuditorResult() {
        return auditorResult;
    }

    public void setAuditorResult(String auditorResult) {
        this.auditorResult = auditorResult == null ? null : auditorResult.trim();
    }

    public String getAuditorRemark() {
        return auditorRemark;
    }

    public void setAuditorRemark(String auditorRemark) {
        this.auditorRemark = auditorRemark == null ? null : auditorRemark.trim();
    }

    public String getAuditorAccessoryFile() {
        return auditorAccessoryFile;
    }

    public void setAuditorAccessoryFile(String auditorAccessoryFile) {
        this.auditorAccessoryFile = auditorAccessoryFile == null ? null : auditorAccessoryFile.trim();
    }

    public String getAuditorAccessoryFilename() {
        return auditorAccessoryFilename;
    }

    public void setAuditorAccessoryFilename(String auditorAccessoryFilename) {
        this.auditorAccessoryFilename = auditorAccessoryFilename == null ? null : auditorAccessoryFilename.trim();
    }
}