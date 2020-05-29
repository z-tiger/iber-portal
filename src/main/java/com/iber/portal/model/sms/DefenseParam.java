package com.iber.portal.model.sms;

public class DefenseParam {
    private Integer smsDefenseParamSeq;

    private String paramCode;

    private String paramValue;

    public Integer getSmsDefenseParamSeq() {
        return smsDefenseParamSeq;
    }

    public void setSmsDefenseParamSeq(Integer smsDefenseParamSeq) {
        this.smsDefenseParamSeq = smsDefenseParamSeq;
    }

    public String getParamCode() {
        return paramCode;
    }

    public void setParamCode(String paramCode) {
        this.paramCode = paramCode == null ? null : paramCode.trim();
    }

    public String getParamValue() {
        return paramValue;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue == null ? null : paramValue.trim();
    }
}