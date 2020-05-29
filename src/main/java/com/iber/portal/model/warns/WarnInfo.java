package com.iber.portal.model.warns;

import java.util.Date;

public class WarnInfo {
     private Integer id;

    private String warnItemCode;

    private String warnContent;

    private Integer toDispatch;

    private Integer memberId;

    private String lpn;

    private Integer parkId;

    private String orderId;

    private String thresholdValue;

    private String actualValue;

    private Date createTime;

    private Date beginTime;

    private Date endTime;
    
    private Integer status;
    
	private String itemName;
    
    private Integer userId;
    
    private String createTimeStr;
    
    public String getCreateTimeStr() {
		return createTimeStr;
	}

	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
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
	
	
	
	
	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}


	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getWarnItemCode() {
        return warnItemCode;
    }

    public void setWarnItemCode(String warnItemCode) {
        this.warnItemCode = warnItemCode == null ? null : warnItemCode.trim();
    }

    public String getWarnContent() {
        return warnContent;
    }

    public void setWarnContent(String warnContent) {
        this.warnContent = warnContent == null ? null : warnContent.trim();
    }

    public Integer getToDispatch() {
        return toDispatch;
    }

    public void setToDispatch(Integer toDispatch) {
        this.toDispatch = toDispatch;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public String getLpn() {
        return lpn;
    }

    public void setLpn(String lpn) {
        this.lpn = lpn == null ? null : lpn.trim();
    }

    public Integer getParkId() {
        return parkId;
    }

    public void setParkId(Integer parkId) {
        this.parkId = parkId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    public String getThresholdValue() {
        return thresholdValue;
    }

    public void setThresholdValue(String thresholdValue) {
        this.thresholdValue = thresholdValue == null ? null : thresholdValue.trim();
    }

    public String getActualValue() {
        return actualValue;
    }

    public void setActualValue(String actualValue) {
        this.actualValue = actualValue == null ? null : actualValue.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}