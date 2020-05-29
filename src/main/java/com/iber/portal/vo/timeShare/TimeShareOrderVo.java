package com.iber.portal.vo.timeShare;

import java.util.Date;

public class TimeShareOrderVo {
    private Integer id;

    private String orderId;

    private String memberName;

    private String parkName;

    private String lpn;

    private Date orderTime;

    private Date beginTime;

    private Date endTime;

    private String status;

    private String cityCode;

    private String returnParkName;
    
    private Double totalMileage ;
    
    private Double totalMinute ;
    
    private Double payMoney ;
    
    private String couponNo ;
    private Double couponBalance ;
    private Double reductionPayMoney ;
    private Double totalMileageCost ;
    private String totalMinuteCost ;
    private Double totalPayMoney ;
    
    private String cityName ;
    
    private Double freeCompensationMoney ;
    private Double nightTotalMinute ;
    private Double nightMinuteReductionMoney ;
    
    private String payStatus ;
    
    private Double discountMoney;//优惠折扣金额
    
    private String memberLevelDiscount;//打折,9折，98折，95折
    
    private Double consumpVal;//封顶价格
    
    private String memberPhone;
    private Integer memberId;
	private String isFreeCompensate;
	private Integer isLockCar; // 是否锁车 0 没有 1 锁车
	private Integer isLongOrder; // 是否超长订单
	
	private Integer balance;//会员余额
	private Integer deposit;//会员押金
	private String score;//会员芝麻信用分

    private Integer lastMoney;
    private String isEnterpriseUseCar;
    private Integer entprisePayMoney;
    public Integer getEntprisePayMoney() {
		return entprisePayMoney;
	}

	public void setEntprisePayMoney(Integer entprisePayMoney) {
		this.entprisePayMoney = entprisePayMoney;
	}

	public String getIsEnterpriseUseCar() {
        return isEnterpriseUseCar;
    }

    public void setIsEnterpriseUseCar(String isEnterpriseUseCar) {
        this.isEnterpriseUseCar = isEnterpriseUseCar;
    }

    public Integer getLastMoney() {
        return lastMoney;
    }

    public void setLastMoney(Integer lastMoney) {
        this.lastMoney = lastMoney;
    }

    public Integer getMemberId() {
   		return memberId;
   	}

   	public void setMemberId(Integer memberId) {
   		this.memberId = memberId;
   	}
    

	public Double getConsumpVal() {
		return consumpVal;
	}

	public void setConsumpVal(Double consumpVal) {
		this.consumpVal = consumpVal;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getParkName() {
		return parkName;
	}

	public void setParkName(String parkName) {
		this.parkName = parkName;
	}

	public String getLpn() {
		return lpn;
	}

	public void setLpn(String lpn) {
		this.lpn = lpn;
	}

	public Date getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
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
		this.status = status;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getReturnParkName() {
		return returnParkName;
	}

	public void setReturnParkName(String returnParkName) {
		this.returnParkName = returnParkName;
	}

	public Double getTotalMileage() {
		return totalMileage;
	}

	public void setTotalMileage(Double totalMileage) {
		this.totalMileage = totalMileage;
	}

	public Double getTotalMinute() {
		return totalMinute;
	}

	public void setTotalMinute(Double totalMinute) {
		this.totalMinute = totalMinute;
	}

	public Double getPayMoney() {
		return payMoney;
	}

	public void setPayMoney(Double payMoney) {
		this.payMoney = payMoney;
	}

	public String getCouponNo() {
		return couponNo;
	}

	public void setCouponNo(String couponNo) {
		this.couponNo = couponNo;
	}

	public Double getCouponBalance() {
		return couponBalance;
	}

	public void setCouponBalance(Double couponBalance) {
		this.couponBalance = couponBalance;
	}

	public Double getReductionPayMoney() {
		return reductionPayMoney;
	}

	public void setReductionPayMoney(Double reductionPayMoney) {
		this.reductionPayMoney = reductionPayMoney;
	}

	public Double getTotalMileageCost() {
		return totalMileageCost;
	}

	public void setTotalMileageCost(Double totalMileageCost) {
		this.totalMileageCost = totalMileageCost;
	}

	public String getTotalMinuteCost() {
		return totalMinuteCost;
	}

	public void setTotalMinuteCost(String totalMinuteCost) {
		this.totalMinuteCost = totalMinuteCost;
	}

	public Double getTotalPayMoney() {
		return totalPayMoney;
	}

	public void setTotalPayMoney(Double totalPayMoney) {
		this.totalPayMoney = totalPayMoney;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public Double getFreeCompensationMoney() {
		return freeCompensationMoney;
	}

	public void setFreeCompensationMoney(Double freeCompensationMoney) {
		this.freeCompensationMoney = freeCompensationMoney;
	}

	public Double getNightTotalMinute() {
		return nightTotalMinute;
	}

	public void setNightTotalMinute(Double nightTotalMinute) {
		this.nightTotalMinute = nightTotalMinute;
	}

	public Double getNightMinuteReductionMoney() {
		return nightMinuteReductionMoney;
	}

	public void setNightMinuteReductionMoney(Double nightMinuteReductionMoney) {
		this.nightMinuteReductionMoney = nightMinuteReductionMoney;
	}

	public String getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}

	public Double getDiscountMoney() {
		return discountMoney;
	}

	public void setDiscountMoney(Double discountMoney) {
		this.discountMoney = discountMoney;
	}

	public String getMemberLevelDiscount() {
		return memberLevelDiscount;
	}

	public void setMemberLevelDiscount(String memberLevelDiscount) {
		this.memberLevelDiscount = memberLevelDiscount;
	}

	public String getMemberPhone() {
		return memberPhone;
	}

	public void setMemberPhone(String memberPhone) {
		this.memberPhone = memberPhone;
	}

	public Integer getIsLockCar() {
		return isLockCar;
	}

	public void setIsLockCar(Integer isLockCar) {
		this.isLockCar = isLockCar;
	}

	public String getIsFreeCompensate() {
		return isFreeCompensate;
	}

	public void setIsFreeCompensate(String isFreeCompensate) {
		this.isFreeCompensate = isFreeCompensate;
	}

	public Integer getBalance() {
		return balance;
	}

	public void setBalance(Integer balance) {
		this.balance = balance;
	}

	public Integer getDeposit() {
		return deposit;
	}

	public void setDeposit(Integer deposit) {
		this.deposit = deposit;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public Integer getIsLongOrder() {
		return isLongOrder;
	}

	public void setIsLongOrder(Integer isLongOrder) {
		this.isLongOrder = isLongOrder;
	}
}