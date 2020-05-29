package com.iber.portal.model.base;

import java.util.Date;

public class MemberLevel {
    private Integer id;

    private String name;

    private Integer chargeMoneyLimit;

    private Integer consumeMoneyLimit;

    private Integer refundMoneyLimit;

    private Integer integralNumLimit;

    private Integer discountLimit;

    private Integer otherCost;

    private Date createTime;

    private String createBy;

    private Date updateTime;

    private String updateBy;

    private String isAuto;

    private String isShow;

    private String isEnterpriseUserCar;

    private String orderType;

    private Integer depositLimit;

    private Integer overdraft;

    private String levelType;

    private String isDefault;

    private Integer levelCode;
    
    private Integer integralDownlimit;
    
    private Integer integralUpLimit;
    
    private String createName;
    
    private String updateName;

    private String rightsName;

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getChargeMoneyLimit() {
        return chargeMoneyLimit;
    }

    public void setChargeMoneyLimit(Integer chargeMoneyLimit) {
        this.chargeMoneyLimit = chargeMoneyLimit;
    }

    public Integer getConsumeMoneyLimit() {
        return consumeMoneyLimit;
    }

    public void setConsumeMoneyLimit(Integer consumeMoneyLimit) {
        this.consumeMoneyLimit = consumeMoneyLimit;
    }

    public Integer getRefundMoneyLimit() {
        return refundMoneyLimit;
    }

    public void setRefundMoneyLimit(Integer refundMoneyLimit) {
        this.refundMoneyLimit = refundMoneyLimit;
    }

    public Integer getIntegralNumLimit() {
        return integralNumLimit;
    }

    public void setIntegralNumLimit(Integer integralNumLimit) {
        this.integralNumLimit = integralNumLimit;
    }

    public Integer getDiscountLimit() {
        return discountLimit;
    }

    public void setDiscountLimit(Integer discountLimit) {
        this.discountLimit = discountLimit;
    }

    public Integer getOtherCost() {
        return otherCost;
    }

    public void setOtherCost(Integer otherCost) {
        this.otherCost = otherCost;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy == null ? null : createBy.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy == null ? null : updateBy.trim();
    }

    public String getIsAuto() {
        return isAuto;
    }

    public void setIsAuto(String isAuto) {
        this.isAuto = isAuto == null ? null : isAuto.trim();
    }

    public String getIsShow() {
        return isShow;
    }

    public void setIsShow(String isShow) {
        this.isShow = isShow == null ? null : isShow.trim();
    }

    public String getIsEnterpriseUserCar() {
        return isEnterpriseUserCar;
    }

    public void setIsEnterpriseUserCar(String isEnterpriseUserCar) {
        this.isEnterpriseUserCar = isEnterpriseUserCar == null ? null : isEnterpriseUserCar.trim();
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType == null ? null : orderType.trim();
    }

    public Integer getDepositLimit() {
        return depositLimit;
    }

    public void setDepositLimit(Integer depositLimit) {
        this.depositLimit = depositLimit;
    }

    public Integer getOverdraft() {
        return overdraft;
    }

    public void setOverdraft(Integer overdraft) {
        this.overdraft = overdraft;
    }

    public String getLevelType() {
        return levelType;
    }

    public void setLevelType(String levelType) {
        this.levelType = levelType == null ? null : levelType.trim();
    }

    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault == null ? null : isDefault.trim();
    }

    

	public Integer getLevelCode() {
		return levelCode;
	}

	public void setLevelCode(Integer levelCode) {
		this.levelCode = levelCode;
	}

	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	public String getUpdateName() {
		return updateName;
	}

	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}

	public Integer getIntegralDownlimit() {
		return integralDownlimit;
	}

	public void setIntegralDownlimit(Integer integralDownlimit) {
		this.integralDownlimit = integralDownlimit;
	}

	public Integer getIntegralUpLimit() {
		return integralUpLimit;
	}

	public void setIntegralUpLimit(Integer integralUpLimit) {
		this.integralUpLimit = integralUpLimit;
	}

	public String getRightsName() {
		return rightsName;
	}

	public void setRightsName(String rightsName) {
		this.rightsName = rightsName;
	}
	
	
    
}