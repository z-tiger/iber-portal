package com.iber.portal.vo.enterprise;

import com.iber.portal.model.enterprise.EnterpriseExcessApply;

/**
 * @author liubiao
 */
public class EnterpriseExcessApplyVo extends EnterpriseExcessApply {
    private String enterpriseName;
    private String memberName;
    private String applyCityName;
    private String actualCityName;
    private Long useTime;
    private String checkMemberName;
    private String memberPhone;
    private String orderType ;
    private Long planUseTime;

    private String reason;
    private Integer lowestEstimatedCost;
    private Integer highestEstimatedCost;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Integer getLowestEstimatedCost() {
        return lowestEstimatedCost;
    }

    public void setLowestEstimatedCost(Integer lowestEstimatedCost) {
        this.lowestEstimatedCost = lowestEstimatedCost;
    }

    public Integer getHighestEstimatedCost() {
        return highestEstimatedCost;
    }

    public void setHighestEstimatedCost(Integer highestEstimatedCost) {
        this.highestEstimatedCost = highestEstimatedCost;
    }

    public Long getPlanUseTime() {
        return planUseTime;
    }

    public void setPlanUseTime(Long planUseTime) {
        this.planUseTime = planUseTime;
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

    public Long getUseTime() {
        return useTime;
    }

    public void setUseTime(Long useTime) {
        this.useTime = useTime;
    }

    public String getCheckMemberName() {
        return checkMemberName;
    }

    public void setCheckMemberName(String checkMemberName) {
        this.checkMemberName = checkMemberName;
    }

    public String getMemberPhone() {
        return memberPhone;
    }

    public void setMemberPhone(String memberPhone) {
        this.memberPhone = memberPhone;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    @Override
    public String toString() {
        return "EnterpriseExcessApplyVo{" +
                "enterpriseName='" + enterpriseName + '\'' +
                ", memberName='" + memberName + '\'' +
                ", applyCityName='" + applyCityName + '\'' +
                ", actualCityName='" + actualCityName + '\'' +
                ", useTime=" + useTime +
                ", checkMemberName='" + checkMemberName + '\'' +
                ", memberPhone='" + memberPhone + '\'' +
                ", orderType='" + orderType + '\'' +
                '}';
    }
}
