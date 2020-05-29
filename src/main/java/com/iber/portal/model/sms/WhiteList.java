package com.iber.portal.model.sms;

import java.util.Date;

public class WhiteList {
    private Integer smsWhiteListSeq;

    private String ipAddress;

    private Date createTime;

    public Integer getSmsWhiteListSeq() {
        return smsWhiteListSeq;
    }

    public void setSmsWhiteListSeq(Integer smsWhiteListSeq) {
        this.smsWhiteListSeq = smsWhiteListSeq;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress == null ? null : ipAddress.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}