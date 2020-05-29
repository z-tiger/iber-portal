package com.iber.portal.vo.enterprise;

import com.iber.portal.model.enterprise.EnterpriseUseCarApply;

/**
 * @author liubiao
 */
public class EnterpriseUseCarApplyVo extends EnterpriseUseCarApply {
    private String enterpriseName;
    private String memberName;
    private String applyCityName;
    private String actualCityName;
    private Long useTime;
    private String checkMemberName;
    private String memberPhone;
    private String orderType ;

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public Long getUseTime() {
        return useTime;
    }

    public void setUseTime(Long useTime) {
        this.useTime = useTime;
    }

    public String getMemberPhone() {
        return memberPhone;
    }

    public void setMemberPhone(String memberPhone) {
        this.memberPhone = memberPhone;
    }

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getApplyCityName() {
        return applyCityName;
    }

    public void setApplyCityName(String applyCityName) {
        this.applyCityName = applyCityName;
    }

    public String getActualCityName() {
        return actualCityName;
    }

    public void setActualCityName(String actualCityName) {
        this.actualCityName = actualCityName;
    }

    public String getCheckMemberName() {
        return checkMemberName;
    }

    public void setCheckMemberName(String checkMemberName) {
        this.checkMemberName = checkMemberName;
    }

    @Override
    public String toString() {
        return "EnterpriseUseCarApplyVo{" +
                "enterpriseName='" + enterpriseName + '\'' +
                ", memberName='" + memberName + '\'' +
                ", applyCityName='" + applyCityName + '\'' +
                ", actualCityName='" + actualCityName + '\'' +
                ", userTime=" + useTime +
                ", checkMemberName='" + checkMemberName + '\'' +
                ", memberPhone='" + memberPhone + '\'' +
                '}';
    }

}
