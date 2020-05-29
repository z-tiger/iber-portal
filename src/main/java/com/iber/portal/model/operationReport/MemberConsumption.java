package com.iber.portal.model.operationReport;

import com.iber.portal.model.base.City;
import com.iber.portal.model.car.Car;

import java.util.Date;

public class MemberConsumption {
    private Integer id;

    private String orderId;

    private Integer memberId;

    private String lpn;

    private Date beginTime;

    private Date endTime;

    private String totalMileage;

    private Integer totalMinute;

    private String payMoney;

	private String couponNo;

    private String couponBalance;

    private String cityCode;

    private Double totalPayMoney;

    private Integer reductionPayMoney;

    private Integer rateId;

    private Integer integralNum;

    private Integer integralMny;

    private Integer totalMileageCost;

    private Integer totalMinuteCost;
    
    private Date useDate;
    
    private String name;
    
    private String sex;
    
    private String idcard;
    
    private Date lastChargeTime;
    
    private Float money;
    
    private Integer integral;
    
    private String memberLevel;
    
    private String phone;
    
    private String payType;
    
    private Double freeCompensateMoney;
    
    private Date startTime;//充电开始时间
    
    private Integer chargingAmount;//充电量
    
    private Integer chargingTime;//充电时长
    
    private String  orderMoney;//充电订单金额
    
    private String couponValue;//充电优惠券金额
    
    private Integer chargingAmountSum;//充电量总合计
    private Integer chargingTimeSum;//充电时长总合计
    private Integer orderMoneySum;//订单金额总合计
    private Double payMoneySum;//支付金额总合计
    private Integer couponMoneySum;//优惠券金额总合计
    
    private Integer totalMinuteSum;//使用时长总合计
    private Integer totalMileageSum;//使用里程总合计
    private Double totalPayMoneySum;//订单金额总合计
    private Double couponBalanceSum;//优惠券金额总合计
    private Double freeCompensateMoneySum;//不计免赔总合计

    private String cityName; // 城市
    private String brandName; //汽车品牌名

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public Double getPayMoneySum() {
		return payMoneySum;
	}

	public void setPayMoneySum(Double payMoneySum) {
		this.payMoneySum = payMoneySum;
	}

	public Integer getTotalMinuteSum() {
		return totalMinuteSum;
	}

	public void setTotalMinuteSum(Integer totalMinuteSum) {
		this.totalMinuteSum = totalMinuteSum;
	}

	public Integer getTotalMileageSum() {
		return totalMileageSum;
	}

	public void setTotalMileageSum(Integer totalMileageSum) {
		this.totalMileageSum = totalMileageSum;
	}

	public Double getTotalPayMoneySum() {
		return totalPayMoneySum;
	}

	public void setTotalPayMoneySum(Double totalPayMoneySum) {
		this.totalPayMoneySum = totalPayMoneySum;
	}

	public Double getCouponBalanceSum() {
		return couponBalanceSum;
	}

	public void setCouponBalanceSum(Double couponBalanceSum) {
		this.couponBalanceSum = couponBalanceSum;
	}

	public Double getFreeCompensateMoneySum() {
		return freeCompensateMoneySum;
	}

	public void setFreeCompensateMoneySum(Double freeCompensateMoneySum) {
		this.freeCompensateMoneySum = freeCompensateMoneySum;
	}

	public Integer getChargingAmountSum() {
		return chargingAmountSum;
	}

	public void setChargingAmountSum(Integer chargingAmountSum) {
		this.chargingAmountSum = chargingAmountSum;
	}

	public Integer getChargingTimeSum() {
		return chargingTimeSum;
	}

	public void setChargingTimeSum(Integer chargingTimeSum) {
		this.chargingTimeSum = chargingTimeSum;
	}

	public Integer getOrderMoneySum() {
		return orderMoneySum;
	}

	public void setOrderMoneySum(Integer orderMoneySum) {
		this.orderMoneySum = orderMoneySum;
	}

	
	public Integer getCouponMoneySum() {
		return couponMoneySum;
	}

	public void setCouponMoneySum(Integer couponMoneySum) {
		this.couponMoneySum = couponMoneySum;
	}

	public String getPayMoney() {
		return payMoney;
	}

	public void setPayMoney(String payMoney) {
		this.payMoney = payMoney;
	}
       
    public Float getMoney() {
		return money;
	}

	public void setMoney(Float money) {
		this.money = money;
	}

	public Date getLastChargeTime() {
		return lastChargeTime;
	}

	public void setLastChargeTime(Date lastChargeTime) {
		this.lastChargeTime = lastChargeTime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public Date getUseDate() {
		return useDate;
	}

	public void setUseDate(Date useDate) {
		this.useDate = useDate;
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
        this.orderId = orderId == null ? null : orderId.trim();
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

	public String getTotalMileage() {
		return totalMileage;
	}

	public void setTotalMileage(String totalMileage) {
		this.totalMileage = totalMileage;
	}


	public Integer getTotalMinute() {
		return totalMinute;
	}

	public void setTotalMinute(Integer totalMinute) {
		this.totalMinute = totalMinute;
	}

	public String getCouponNo() {
        return couponNo;
    }

    public void setCouponNo(String couponNo) {
        this.couponNo = couponNo == null ? null : couponNo.trim();
    }

    public String getCouponBalance() {
		return couponBalance;
	}

	public void setCouponBalance(String couponBalance) {
		this.couponBalance = couponBalance;
	}

	public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode == null ? null : cityCode.trim();
    }
    public Double getTotalPayMoney() {
		return totalPayMoney;
	}

	public void setTotalPayMoney(Double totalPayMoney) {
		this.totalPayMoney = totalPayMoney;
	}

	public Integer getReductionPayMoney() {
        return reductionPayMoney;
    }

    public void setReductionPayMoney(Integer reductionPayMoney) {
        this.reductionPayMoney = reductionPayMoney;
    }

    public Integer getRateId() {
        return rateId;
    }

    public void setRateId(Integer rateId) {
        this.rateId = rateId;
    }

    public Integer getIntegralNum() {
        return integralNum;
    }

    public void setIntegralNum(Integer integralNum) {
        this.integralNum = integralNum;
    }

    public Integer getIntegralMny() {
        return integralMny;
    }

    public void setIntegralMny(Integer integralMny) {
        this.integralMny = integralMny;
    }

    public Integer getTotalMileageCost() {
        return totalMileageCost;
    }

    public void setTotalMileageCost(Integer totalMileageCost) {
        this.totalMileageCost = totalMileageCost;
    }

    public Integer getTotalMinuteCost() {
        return totalMinuteCost;
    }

    public void setTotalMinuteCost(Integer totalMinuteCost) {
        this.totalMinuteCost = totalMinuteCost;
    }

	public Integer getIntegral() {
		return integral;
	}

	public void setIntegral(Integer integral) {
		this.integral = integral;
	}

	public String getMemberLevel() {
		return memberLevel;
	}

	public void setMemberLevel(String memberLevel) {
		this.memberLevel = memberLevel;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public Double getFreeCompensateMoney() {
		return freeCompensateMoney;
	}

	public void setFreeCompensateMoney(Double freeCompensateMoney) {
		this.freeCompensateMoney = freeCompensateMoney;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Integer getChargingAmount() {
		return chargingAmount;
	}

	public void setChargingAmount(Integer chargingAmount) {
		this.chargingAmount = chargingAmount;
	}

	public Integer getChargingTime() {
		return chargingTime;
	}

	public void setChargingTime(Integer chargingTime) {
		this.chargingTime = chargingTime;
	}

	

	public String getOrderMoney() {
		return orderMoney;
	}

	public void setOrderMoney(String orderMoney) {
		this.orderMoney = orderMoney;
	}

	public String getCouponValue() {
		return couponValue;
	}

	public void setCouponValue(String couponValue) {
		this.couponValue = couponValue;
	}
}