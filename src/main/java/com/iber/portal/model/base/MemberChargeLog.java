package com.iber.portal.model.base;

import java.util.Date;

public class MemberChargeLog {
    private Integer id;

    private Integer memberId;

    private Integer money;

    private String bankCategory;

    private Date createTime;

    private String tradeStatus;

    private String remark;

    private String tradeNo;

    private Date tradeTime;

    private String chargeId;

    private String chargeCategory;
    
    private Integer mid;

    private String name;
    
    private Integer refundMoney;
    
    private Date applyCreateTime;
    
    private String phone;
    
    private Date lastHandleTime;
    
    private String lastHandleUser;
    
    private String refundUserMoblie;
    
    private Integer refundMemberId;
    
    private Integer isHandleReturn;
    
    private String bankName;
    
    private String bankCard;
    
    private Integer isRefund;
    
    private Integer refundId;
    
    private Integer isHandle;
    
    public Integer getIsHandle() {
		return isHandle;
	}

	public void setIsHandle(Integer isHandle) {
		this.isHandle = isHandle;
	}
   
    public Integer getIsRefund() {
		return isRefund;
	}

	public void setIsRefund(Integer isRefund) {
		this.isRefund = isRefund;
	}

	public Integer getRefundId() {
		return refundId;
	}

	public void setRefundId(Integer refundId) {
		this.refundId = refundId;
	}

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

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public String getBankCategory() {
        return bankCategory;
    }

    public void setBankCategory(String bankCategory) {
        this.bankCategory = bankCategory == null ? null : bankCategory.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getTradeStatus() {
        return tradeStatus;
    }

    public void setTradeStatus(String tradeStatus) {
        this.tradeStatus = tradeStatus == null ? null : tradeStatus.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo == null ? null : tradeNo.trim();
    }

    public Date getTradeTime() {
        return tradeTime;
    }

    public void setTradeTime(Date tradeTime) {
        this.tradeTime = tradeTime;
    }

    public String getChargeId() {
        return chargeId;
    }

    public void setChargeId(String chargeId) {
        this.chargeId = chargeId == null ? null : chargeId.trim();
    }

    public String getChargeCategory() {
        return chargeCategory;
    }

    public void setChargeCategory(String chargeCategory) {
        this.chargeCategory = chargeCategory == null ? null : chargeCategory.trim();
    }

	/**
	 * @return the mid
	 */
	public Integer getMid() {
		return mid;
	}

	/**
	 * @param mid the mid to set
	 */
	public void setMid(Integer mid) {
		this.mid = mid;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the refundMoney
	 */
	public Integer getRefundMoney() {
		return refundMoney;
	}

	/**
	 * @param refundMoney the refundMoney to set
	 */
	public void setRefundMoney(Integer refundMoney) {
		this.refundMoney = refundMoney;
	}

	/**
	 * @return the applyCreateTime
	 */
	public Date getApplyCreateTime() {
		return applyCreateTime;
	}

	/**
	 * @param applyCreateTime the applyCreateTime to set
	 */
	public void setApplyCreateTime(Date applyCreateTime) {
		this.applyCreateTime = applyCreateTime;
	}

	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * @return the lastHandleTime
	 */
	public Date getLastHandleTime() {
		return lastHandleTime;
	}

	/**
	 * @param lastHandleTime the lastHandleTime to set
	 */
	public void setLastHandleTime(Date lastHandleTime) {
		this.lastHandleTime = lastHandleTime;
	}

	/**
	 * @return the lastHandleUser
	 */
	public String getLastHandleUser() {
		return lastHandleUser;
	}

	/**
	 * @param lastHandleUser the lastHandleUser to set
	 */
	public void setLastHandleUser(String lastHandleUser) {
		this.lastHandleUser = lastHandleUser;
	}

	/**
	 * @return the refundUserMoblie
	 */
	public String getRefundUserMoblie() {
		return refundUserMoblie;
	}

	/**
	 * @param refundUserMoblie the refundUserMoblie to set
	 */
	public void setRefundUserMoblie(String refundUserMoblie) {
		this.refundUserMoblie = refundUserMoblie;
	}

	/**
	 * @return the refundMemberId
	 */
	public Integer getRefundMemberId() {
		return refundMemberId;
	}

	/**
	 * @param refundMemberId the refundMemberId to set
	 */
	public void setRefundMemberId(Integer refundMemberId) {
		this.refundMemberId = refundMemberId;
	}

	/**
	 * @return the isHandleReturn
	 */
	public Integer getIsHandleReturn() {
		return isHandleReturn;
	}

	/**
	 * @param isHandleReturn the isHandleReturn to set
	 */
	public void setIsHandleReturn(Integer isHandleReturn) {
		this.isHandleReturn = isHandleReturn;
	}

	/**
	 * @return the bankName
	 */
	public String getBankName() {
		return bankName;
	}

	/**
	 * @param bankName the bankName to set
	 */
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	/**
	 * @return the bankCard
	 */
	public String getBankCard() {
		return bankCard;
	}

	/**
	 * @param bankCard the bankCard to set
	 */
	public void setBankCard(String bankCard) {
		this.bankCard = bankCard;
	}
    
    
}