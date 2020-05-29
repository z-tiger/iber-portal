package com.iber.portal.model.coupon;

import java.util.Date;

public class Coupon {
    private Integer id;

    private String couponNo;

    private String title;

    private String description;

    private Date startTime;

    private Date endTime;

    private String status;

    private Integer createId;
    
	private Date createTime;

    private Integer memberId;

    private String useStatus;

    private Double balance;

    private String batchNo;

    private Date useTime;

    private String cityCode;
    
    private String cityName;
    
    private String memberName ;
    
    private String memberPhone;
    
    private String type ; //  优惠券类型 0 手段 1 自动
    private String itemCode ; //优惠券配置项CODE
    
    private String status1;
    
    private String sysUserName;
    
    private Integer useType;//优惠使用性类型，null和0均代表无限制使用；1代表有限制使用,即该券为抵扣券
     
    private Integer minUseValue;
    
    private Integer issueAuthority;
    
    private Integer maxDeductionValue;

    //优惠劵的类别 1：日租优惠券 0 时组优惠券
    private Integer category ;
    
    private Integer strategyId;
    
    private String applyDep;
    
    private String applyUser;
    
    public String getApplyDep() {
		return applyDep;
	}

	public void setApplyDep(String applyDep) {
		this.applyDep = applyDep;
	}

	public String getApplyUser() {
		return applyUser;
	}

	public void setApplyUser(String applyUser) {
		this.applyUser = applyUser;
	}

	public Integer getStrategyId() {
		return strategyId;
	}

	public void setStrategyId(Integer strategyId) {
		this.strategyId = strategyId;
	}

	public Integer getMaxDeductionValue() {
		return maxDeductionValue;
	}

	public void setMaxDeductionValue(Integer maxDeductionValue) {
		this.maxDeductionValue = maxDeductionValue;
	}

	public Integer getMinUseValue() {
		return minUseValue;
	}

	public void setMinUseValue(Integer minUseValue) {
		this.minUseValue = minUseValue;
	}

	public Integer getIssueAuthority() {
		return issueAuthority;
	}

	public void setIssueAuthority(Integer issueAuthority) {
		this.issueAuthority = issueAuthority;
	}

	public String getSysUserName() {
		return sysUserName;
	}

	public void setSysUserName(String sysUserName) {
		this.sysUserName = sysUserName;
	}

	public String getStatus1() {
		return status1;
	}

	public void setStatus1(String status1) {
		this.status1 = status1;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCouponNo() {
        return couponNo;
    }

    public void setCouponNo(String couponNo) {
        this.couponNo = couponNo == null ? null : couponNo.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
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

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public String getUseStatus() {
        return useStatus;
    }

    public void setUseStatus(String useStatus) {
        this.useStatus = useStatus == null ? null : useStatus.trim();
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo == null ? null : batchNo.trim();
    }

    public Date getUseTime() {
        return useTime;
    }

    public void setUseTime(Date useTime) {
        this.useTime = useTime;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode == null ? null : cityCode.trim();
    }

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getMemberPhone() {
		return memberPhone;
	}

	public void setMemberPhone(String memberPhone) {
		this.memberPhone = memberPhone;
	}

	public Integer getUseType() {
		return useType;
	}

	public void setUseType(Integer useType) {
		this.useType = useType;
	}

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }
}