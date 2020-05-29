package com.iber.portal.model.enterprise;

import java.io.Serializable;
import java.util.Date;

public class EnterpriseUseCarApply implements Serializable {
    private Integer id;

    private Integer enterpriseId;

    private Integer memberId;

    private String lpn;

    private Byte status;

    private String cityCode;

    private Date startTime;

    private Date endTime;

    private String interviewee;

    private String intervieweePhone;

    private String reason;

    private Date createTime;

    private Date updateTime;

    private Integer updateUserId;

    private String updateUser;

    private String orderId;

    private String remark;

    private Integer nextHandlerId;

    private Integer highestEstimatedCost;

    private Integer lowestEstimatedCost;

    private String rejectReason;

    private Byte payStatus;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(Integer enterpriseId) {
        this.enterpriseId = enterpriseId;
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

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode == null ? null : cityCode.trim();
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getInterviewee() {
        return interviewee;
    }

    public void setInterviewee(String interviewee) {
        this.interviewee = interviewee == null ? null : interviewee.trim();
    }

    public String getIntervieweePhone() {
        return intervieweePhone;
    }

    public void setIntervieweePhone(String intervieweePhone) {
        this.intervieweePhone = intervieweePhone == null ? null : intervieweePhone.trim();
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason == null ? null : reason.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(Integer updateUserId) {
        this.updateUserId = updateUserId;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser == null ? null : updateUser.trim();
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getNextHandlerId() {
        return nextHandlerId;
    }

    public void setNextHandlerId(Integer nextHandlerId) {
        this.nextHandlerId = nextHandlerId;
    }

    public Integer getHighestEstimatedCost() {
        return highestEstimatedCost;
    }

    public void setHighestEstimatedCost(Integer highestEstimatedCost) {
        this.highestEstimatedCost = highestEstimatedCost;
    }

    public Integer getLowestEstimatedCost() {
        return lowestEstimatedCost;
    }

    public void setLowestEstimatedCost(Integer lowestEstimatedCost) {
        this.lowestEstimatedCost = lowestEstimatedCost;
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason == null ? null : rejectReason.trim();
    }

    public Byte getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Byte payStatus) {
        this.payStatus = payStatus;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        EnterpriseUseCarApply other = (EnterpriseUseCarApply) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getEnterpriseId() == null ? other.getEnterpriseId() == null : this.getEnterpriseId().equals(other.getEnterpriseId()))
            && (this.getMemberId() == null ? other.getMemberId() == null : this.getMemberId().equals(other.getMemberId()))
            && (this.getLpn() == null ? other.getLpn() == null : this.getLpn().equals(other.getLpn()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getCityCode() == null ? other.getCityCode() == null : this.getCityCode().equals(other.getCityCode()))
            && (this.getStartTime() == null ? other.getStartTime() == null : this.getStartTime().equals(other.getStartTime()))
            && (this.getEndTime() == null ? other.getEndTime() == null : this.getEndTime().equals(other.getEndTime()))
            && (this.getInterviewee() == null ? other.getInterviewee() == null : this.getInterviewee().equals(other.getInterviewee()))
            && (this.getIntervieweePhone() == null ? other.getIntervieweePhone() == null : this.getIntervieweePhone().equals(other.getIntervieweePhone()))
            && (this.getReason() == null ? other.getReason() == null : this.getReason().equals(other.getReason()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getUpdateUserId() == null ? other.getUpdateUserId() == null : this.getUpdateUserId().equals(other.getUpdateUserId()))
            && (this.getUpdateUser() == null ? other.getUpdateUser() == null : this.getUpdateUser().equals(other.getUpdateUser()))
            && (this.getOrderId() == null ? other.getOrderId() == null : this.getOrderId().equals(other.getOrderId()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getNextHandlerId() == null ? other.getNextHandlerId() == null : this.getNextHandlerId().equals(other.getNextHandlerId()))
            && (this.getHighestEstimatedCost() == null ? other.getHighestEstimatedCost() == null : this.getHighestEstimatedCost().equals(other.getHighestEstimatedCost()))
            && (this.getLowestEstimatedCost() == null ? other.getLowestEstimatedCost() == null : this.getLowestEstimatedCost().equals(other.getLowestEstimatedCost()))
            && (this.getRejectReason() == null ? other.getRejectReason() == null : this.getRejectReason().equals(other.getRejectReason()))
            && (this.getPayStatus() == null ? other.getPayStatus() == null : this.getPayStatus().equals(other.getPayStatus()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getEnterpriseId() == null) ? 0 : getEnterpriseId().hashCode());
        result = prime * result + ((getMemberId() == null) ? 0 : getMemberId().hashCode());
        result = prime * result + ((getLpn() == null) ? 0 : getLpn().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getCityCode() == null) ? 0 : getCityCode().hashCode());
        result = prime * result + ((getStartTime() == null) ? 0 : getStartTime().hashCode());
        result = prime * result + ((getEndTime() == null) ? 0 : getEndTime().hashCode());
        result = prime * result + ((getInterviewee() == null) ? 0 : getInterviewee().hashCode());
        result = prime * result + ((getIntervieweePhone() == null) ? 0 : getIntervieweePhone().hashCode());
        result = prime * result + ((getReason() == null) ? 0 : getReason().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getUpdateUserId() == null) ? 0 : getUpdateUserId().hashCode());
        result = prime * result + ((getUpdateUser() == null) ? 0 : getUpdateUser().hashCode());
        result = prime * result + ((getOrderId() == null) ? 0 : getOrderId().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getNextHandlerId() == null) ? 0 : getNextHandlerId().hashCode());
        result = prime * result + ((getHighestEstimatedCost() == null) ? 0 : getHighestEstimatedCost().hashCode());
        result = prime * result + ((getLowestEstimatedCost() == null) ? 0 : getLowestEstimatedCost().hashCode());
        result = prime * result + ((getRejectReason() == null) ? 0 : getRejectReason().hashCode());
        result = prime * result + ((getPayStatus() == null) ? 0 : getPayStatus().hashCode());
        return result;
    }

    @Override
    public String toString() {
        return "EnterpriseUseCarApply{" +
                "id=" + id +
                ", enterpriseId=" + enterpriseId +
                ", memberId=" + memberId +
                ", lpn='" + lpn + '\'' +
                ", status=" + status +
                ", cityCode='" + cityCode + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", interviewee='" + interviewee + '\'' +
                ", intervieweePhone='" + intervieweePhone + '\'' +
                ", reason='" + reason + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", updateUserId=" + updateUserId +
                ", updateUser='" + updateUser + '\'' +
                ", orderId='" + orderId + '\'' +
                ", remark='" + remark + '\'' +
                ", nextHandlerId=" + nextHandlerId +
                ", highestEstimatedCost=" + highestEstimatedCost +
                ", lowestEstimatedCost=" + lowestEstimatedCost +
                ", rejectReason='" + rejectReason + '\'' +
                ", payStatus=" + payStatus +
                '}';
    }
}