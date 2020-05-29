package com.iber.portal.model.versions;

import java.util.Date;

public class VersionsUpgradeLog {
    private Integer id;

    private String lpn;

    private String upgradetype;

    private String currentVersionNo;

    private String upgradeVersionNo;

    private Integer status;

    private String remark;

    private Integer createId;

    private Date createTime;

    private Integer updateId;

    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLpn() {
        return lpn;
    }

    public void setLpn(String lpn) {
        this.lpn = lpn == null ? null : lpn.trim();
    }

    public String getUpgradetype() {
        return upgradetype;
    }

    public void setUpgradetype(String upgradetype) {
        this.upgradetype = upgradetype == null ? null : upgradetype.trim();
    }

    public String getCurrentVersionNo() {
        return currentVersionNo;
    }

    public void setCurrentVersionNo(String currentVersionNo) {
        this.currentVersionNo = currentVersionNo == null ? null : currentVersionNo.trim();
    }

    public String getUpgradeVersionNo() {
        return upgradeVersionNo;
    }

    public void setUpgradeVersionNo(String upgradeVersionNo) {
        this.upgradeVersionNo = upgradeVersionNo;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getCreateId() {
        return createId;
    }

    public void setCreateId(Integer createId) {
        this.createId = createId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getUpdateId() {
        return updateId;
    }

    public void setUpdateId(Integer updateId) {
        this.updateId = updateId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}