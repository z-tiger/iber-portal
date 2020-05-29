package com.iber.portal.model.activity;

import java.util.Date;

public class LotteryDrawItem {
    private Integer id;

    private Integer lotteryDrawId;

    private String prizeName;

    private Integer prizeAmount;

    private Integer prizeRestAmount;

    private String prizeWeight;

    private Integer version;

    private String remark;

    private Date createTime;

    private Integer sort;

    private Byte isCoupon;
    
    private String prizeWeightSum ;
    

    public String getPrizeWeightSum() {
		return prizeWeightSum;
	}

	public void setPrizeWeightSum(String prizeWeightSum) {
		this.prizeWeightSum = prizeWeightSum;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLotteryDrawId() {
        return lotteryDrawId;
    }

    public void setLotteryDrawId(Integer lotteryDrawId) {
        this.lotteryDrawId = lotteryDrawId;
    }

    public String getPrizeName() {
        return prizeName;
    }

    public void setPrizeName(String prizeName) {
        this.prizeName = prizeName == null ? null : prizeName.trim();
    }

    public Integer getPrizeAmount() {
        return prizeAmount;
    }

    public void setPrizeAmount(Integer prizeAmount) {
        this.prizeAmount = prizeAmount;
    }

    public Integer getPrizeRestAmount() {
        return prizeRestAmount;
    }

    public void setPrizeRestAmount(Integer prizeRestAmount) {
        this.prizeRestAmount = prizeRestAmount;
    }

    public String getPrizeWeight() {
        return prizeWeight;
    }

    public void setPrizeWeight(String prizeWeight) {
        this.prizeWeight = prizeWeight == null ? null : prizeWeight.trim();
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Byte getIsCoupon() {
        return isCoupon;
    }

    public void setIsCoupon(Byte isCoupon) {
        this.isCoupon = isCoupon;
    }
}