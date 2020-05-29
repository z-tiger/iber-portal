package com.iber.portal.model.member;

import java.io.Serializable;
import java.util.Date;

public class LostPhonenoApplication implements Serializable {

    private Integer id;

    private Integer memberId;

    private String oldPhone;

    private String newPhone;

    private String memberName;

    private String idcard;

    private String driverIdcard;

    private String idcardPhotoUrl;

    private String driverIdcardPhotoUrl;

    private String preMemberDetail;

    private String nextMemberDetail;

    private String preMemberCardDetail;

    private String nextMemberCardDetail;

    private String verifiedStatus;

    private String refuseReason;

    private Integer userId;

    private Date createTime;

    private Date verifiedTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public String getOldPhone() {
        return oldPhone;
    }

    public void setOldPhone(String oldPhone) {
        this.oldPhone = oldPhone == null ? null : oldPhone.trim();
    }

    public String getNewPhone() {
        return newPhone;
    }

    public void setNewPhone(String newPhone) {
        this.newPhone = newPhone == null ? null : newPhone.trim();
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName == null ? null : memberName.trim();
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard == null ? null : idcard.trim();
    }

    public String getDriverIdcard() {
        return driverIdcard;
    }

    public void setDriverIdcard(String driverIdcard) {
        this.driverIdcard = driverIdcard == null ? null : driverIdcard.trim();
    }

    public String getIdcardPhotoUrl() {
        return idcardPhotoUrl;
    }

    public void setIdcardPhotoUrl(String idcardPhotoUrl) {
        this.idcardPhotoUrl = idcardPhotoUrl == null ? null : idcardPhotoUrl.trim();
    }

    public String getDriverIdcardPhotoUrl() {
        return driverIdcardPhotoUrl;
    }

    public void setDriverIdcardPhotoUrl(String driverIdcardPhotoUrl) {
        this.driverIdcardPhotoUrl = driverIdcardPhotoUrl == null ? null : driverIdcardPhotoUrl.trim();
    }

    public String getPreMemberDetail() {
        return preMemberDetail;
    }

    public void setPreMemberDetail(String preMemberDetail) {
        this.preMemberDetail = preMemberDetail == null ? null : preMemberDetail.trim();
    }

    public String getNextMemberDetail() {
        return nextMemberDetail;
    }

    public void setNextMemberDetail(String nextMemberDetail) {
        this.nextMemberDetail = nextMemberDetail == null ? null : nextMemberDetail.trim();
    }

    public String getPreMemberCardDetail() {
        return preMemberCardDetail;
    }

    public void setPreMemberCardDetail(String preMemberCardDetail) {
        this.preMemberCardDetail = preMemberCardDetail == null ? null : preMemberCardDetail.trim();
    }

    public String getNextMemberCardDetail() {
        return nextMemberCardDetail;
    }

    public void setNextMemberCardDetail(String nextMemberCardDetail) {
        this.nextMemberCardDetail = nextMemberCardDetail == null ? null : nextMemberCardDetail.trim();
    }

    public String getVerifiedStatus() {
        return verifiedStatus;
    }

    public void setVerifiedStatus(String verifiedStatus) {
        this.verifiedStatus = verifiedStatus == null ? null : verifiedStatus.trim();
    }

    public String getRefuseReason() {
        return refuseReason;
    }

    public void setRefuseReason(String refuseReason) {
        this.refuseReason = refuseReason == null ? null : refuseReason.trim();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getVerifiedTime() {
        return verifiedTime;
    }

    public void setVerifiedTime(Date verifiedTime) {
        this.verifiedTime = verifiedTime;
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
        LostPhonenoApplication other = (LostPhonenoApplication) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getMemberId() == null ? other.getMemberId() == null : this.getMemberId().equals(other.getMemberId()))
            && (this.getOldPhone() == null ? other.getOldPhone() == null : this.getOldPhone().equals(other.getOldPhone()))
            && (this.getNewPhone() == null ? other.getNewPhone() == null : this.getNewPhone().equals(other.getNewPhone()))
            && (this.getMemberName() == null ? other.getMemberName() == null : this.getMemberName().equals(other.getMemberName()))
            && (this.getIdcard() == null ? other.getIdcard() == null : this.getIdcard().equals(other.getIdcard()))
            && (this.getDriverIdcard() == null ? other.getDriverIdcard() == null : this.getDriverIdcard().equals(other.getDriverIdcard()))
            && (this.getIdcardPhotoUrl() == null ? other.getIdcardPhotoUrl() == null : this.getIdcardPhotoUrl().equals(other.getIdcardPhotoUrl()))
            && (this.getDriverIdcardPhotoUrl() == null ? other.getDriverIdcardPhotoUrl() == null : this.getDriverIdcardPhotoUrl().equals(other.getDriverIdcardPhotoUrl()))
            && (this.getPreMemberDetail() == null ? other.getPreMemberDetail() == null : this.getPreMemberDetail().equals(other.getPreMemberDetail()))
            && (this.getNextMemberDetail() == null ? other.getNextMemberDetail() == null : this.getNextMemberDetail().equals(other.getNextMemberDetail()))
            && (this.getPreMemberCardDetail() == null ? other.getPreMemberCardDetail() == null : this.getPreMemberCardDetail().equals(other.getPreMemberCardDetail()))
            && (this.getNextMemberCardDetail() == null ? other.getNextMemberCardDetail() == null : this.getNextMemberCardDetail().equals(other.getNextMemberCardDetail()))
            && (this.getVerifiedStatus() == null ? other.getVerifiedStatus() == null : this.getVerifiedStatus().equals(other.getVerifiedStatus()))
            && (this.getRefuseReason() == null ? other.getRefuseReason() == null : this.getRefuseReason().equals(other.getRefuseReason()))
            && (this.getUserId() == null ? other.getUserId() == null : this.getUserId().equals(other.getUserId()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getVerifiedTime() == null ? other.getVerifiedTime() == null : this.getVerifiedTime().equals(other.getVerifiedTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getMemberId() == null) ? 0 : getMemberId().hashCode());
        result = prime * result + ((getOldPhone() == null) ? 0 : getOldPhone().hashCode());
        result = prime * result + ((getNewPhone() == null) ? 0 : getNewPhone().hashCode());
        result = prime * result + ((getMemberName() == null) ? 0 : getMemberName().hashCode());
        result = prime * result + ((getIdcard() == null) ? 0 : getIdcard().hashCode());
        result = prime * result + ((getDriverIdcard() == null) ? 0 : getDriverIdcard().hashCode());
        result = prime * result + ((getIdcardPhotoUrl() == null) ? 0 : getIdcardPhotoUrl().hashCode());
        result = prime * result + ((getDriverIdcardPhotoUrl() == null) ? 0 : getDriverIdcardPhotoUrl().hashCode());
        result = prime * result + ((getPreMemberDetail() == null) ? 0 : getPreMemberDetail().hashCode());
        result = prime * result + ((getNextMemberDetail() == null) ? 0 : getNextMemberDetail().hashCode());
        result = prime * result + ((getPreMemberCardDetail() == null) ? 0 : getPreMemberCardDetail().hashCode());
        result = prime * result + ((getNextMemberCardDetail() == null) ? 0 : getNextMemberCardDetail().hashCode());
        result = prime * result + ((getVerifiedStatus() == null) ? 0 : getVerifiedStatus().hashCode());
        result = prime * result + ((getRefuseReason() == null) ? 0 : getRefuseReason().hashCode());
        result = prime * result + ((getUserId() == null) ? 0 : getUserId().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getVerifiedTime() == null) ? 0 : getVerifiedTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", memberId=").append(memberId);
        sb.append(", oldPhone=").append(oldPhone);
        sb.append(", newPhone=").append(newPhone);
        sb.append(", memberName=").append(memberName);
        sb.append(", idcard=").append(idcard);
        sb.append(", driverIdcard=").append(driverIdcard);
        sb.append(", idcardPhotoUrl=").append(idcardPhotoUrl);
        sb.append(", driverIdcardPhotoUrl=").append(driverIdcardPhotoUrl);
        sb.append(", preMemberDetail=").append(preMemberDetail);
        sb.append(", nextMemberDetail=").append(nextMemberDetail);
        sb.append(", preMemberCardDetail=").append(preMemberCardDetail);
        sb.append(", nextMemberCardDetail=").append(nextMemberCardDetail);
        sb.append(", verifiedStatus=").append(verifiedStatus);
        sb.append(", refuseReason=").append(refuseReason);
        sb.append(", userId=").append(userId);
        sb.append(", createTime=").append(createTime);
        sb.append(", verifiedTime=").append(verifiedTime);
        sb.append("]");
        return sb.toString();
    }
}