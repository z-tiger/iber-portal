package com.iber.portal.model.sms;

import java.util.Date;

public class Blacklist {
    private Integer smsBlacklistSeq;

    private String ipAddress;

    private Date createTime;

    public Integer getSmsBlacklistSeq() {
        return smsBlacklistSeq;
    }

    public void setSmsBlacklistSeq(Integer smsBlacklistSeq) {
        this.smsBlacklistSeq = smsBlacklistSeq;
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