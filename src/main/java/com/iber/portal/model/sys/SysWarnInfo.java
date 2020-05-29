package com.iber.portal.model.sys;

import java.io.Serializable;
import java.util.Date;

public class SysWarnInfo implements Serializable{
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

    private Integer isRead = 0; // 默认未读

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

    public Integer getIsRead() {
        return isRead;
    }

    public void setIsRead(Integer isRead) {
        this.isRead = isRead;
    }
}