package com.iber.portal.model.base;

import java.util.Date;

public class MemberRefundLog {
    private Integer id;

    private Integer memberId;

    private Integer money;

    private Date createTime;

    private Date lastHandleTime;

    private String lastHandleUser;

    private String status;

    private String refundUserMoblie;

    private String bankName;

    private String bankCard;

    private String remark;

    private String bankCardUserName;

    private String reason;

    private String failReason; 

    private String isIllegal;

    private String refundId;

    private String tradeNo;

    private String chargeCategory;
    
    private Integer nextHandleUserRoleId;
    
    private String nextHandleUserRoleIdStr;
    
    private String name;
    private String phone;
    private String cityCode;
    private String cityName;
    private Integer isHandleReturn;
    private Integer refundSuccessMoney;
    private Integer chargedNum;
    private Integer refundFailNum;
    private Date userCarTime;
    private Integer days;
    private Integer levelCode;
    
	public Integer getLevelCode() {
		return levelCode;
	}

	public void setLevelCode(Integer levelCode) {
		this.levelCode = levelCode;
	}

	public Integer getDays(){
    	if(createTime == null){
    		days = 0;
    		return 0;
    	}
    	long s2 = System.currentTimeMillis();;
    	long s1=createTime.getTime();
    	days=(int)((s2-s1)/1000/60/60/24);
    	return days;
    }
	
    public Date getUserCarTime() {
		return userCarTime;
	}

	public void setUserCarTime(Date userCarTime) {
		this.userCarTime = userCarTime;
	}

	public Integer getRefundFailNum() {
		return refundFailNum;
	}

	public void setRefundFailNum(Integer refundFailNum) {
		this.refundFailNum = refundFailNum;
	}

	public Integer getChargedNum() {
		return chargedNum;
	}

	public void setChargedNum(Integer chargedNum) {
		this.chargedNum = chargedNum;
	}

	public Integer getRefundSuccessMoney() {
		return refundSuccessMoney;
	}

	public void setRefundSuccessMoney(Integer refundSuccessMoney) {
		this.refundSuccessMoney = refundSuccessMoney;
	}

	public Integer getIsHandleReturn() {
		return isHandleReturn;
	}

	public void setIsHandleReturn(Integer isHandleReturn) {
		this.isHandleReturn = isHandleReturn;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastHandleTime() {
        return lastHandleTime;
    }

    public void setLastHandleTime(Date lastHandleTime) {
        this.lastHandleTime = lastHandleTime;
    }

    public String getLastHandleUser() {
        return lastHandleUser;
    }

    public void setLastHandleUser(String lastHandleUser) {
        this.lastHandleUser = lastHandleUser == null ? null : lastHandleUser.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getRefundUserMoblie() {
        return refundUserMoblie;
    }

    public void setRefundUserMoblie(String refundUserMoblie) {
        this.refundUserMoblie = refundUserMoblie == null ? null : refundUserMoblie.trim();
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName == null ? null : bankName.trim();
    }

    public String getBankCard() {
        return bankCard;
    }

    public void setBankCard(String bankCard) {
        this.bankCard = bankCard == null ? null : bankCard.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getBankCardUserName() {
        return bankCardUserName;
    }

    public void setBankCardUserName(String bankCardUserName) {
        this.bankCardUserName = bankCardUserName == null ? null : bankCardUserName.trim();
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason == null ? null : reason.trim();
    }

    public String getFailReason() {
        return failReason;
    }

    public void setFailReason(String failReason) {
        this.failReason = failReason == null ? null : failReason.trim();
    }

    public String getIsIllegal() {
        return isIllegal;
    }

    public void setIsIllegal(String isIllegal) {
        this.isIllegal = isIllegal == null ? null : isIllegal.trim();
    }

    public String getRefundId() {
        return refundId;
    }

    public void setRefundId(String refundId) {
        this.refundId = refundId == null ? null : refundId.trim();
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo == null ? null : tradeNo.trim();
    }

    public String getChargeCategory() {
        return chargeCategory;
    }

    public void setChargeCategory(String chargeCategory) {
        this.chargeCategory = chargeCategory == null ? null : chargeCategory.trim();
    }
    
    public void setNextHandleUserRoleId(Integer nextHandleUserRoleId) {
		this.nextHandleUserRoleId = nextHandleUserRoleId;
	}
    
    public Integer getNextHandleUserRoleId() {
		return nextHandleUserRoleId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
    
    public void setNextHandleUserRoleIdStr(String nextHandleUserRoleIdStr) {
		this.nextHandleUserRoleIdStr = nextHandleUserRoleIdStr;
	}
    
    public String getNextHandleUserRoleIdStr() {
		return nextHandleUserRoleIdStr;
	}
}