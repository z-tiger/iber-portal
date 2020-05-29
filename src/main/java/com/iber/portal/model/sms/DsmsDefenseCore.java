package com.iber.portal.model.sms;

import java.util.Date;

public class DsmsDefenseCore {
    private Integer smsDefenseCoreSeq;

    private String telephoneNo;

    private String ipAddress;

    private Date sendTime;

    private String templateId;

    private String contentParam;

    public Integer getSmsDefenseCoreSeq() {
        return smsDefenseCoreSeq;
    }

    public void setSmsDefenseCoreSeq(Integer smsDefenseCoreSeq) {
        this.smsDefenseCoreSeq = smsDefenseCoreSeq;
    }

    public String getTelephoneNo() {
        return telephoneNo;
    }

    public void setTelephoneNo(String telephoneNo) {
        this.telephoneNo = telephoneNo == null ? null : telephoneNo.trim();
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress == null ? null : ipAddress.trim();
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId == null ? null : templateId.trim();
    }

    public String getContentParam() {
        return contentParam;
    }

    public void setContentParam(String contentParam) {
        this.contentParam = contentParam == null ? null : contentParam.trim();
    }
}