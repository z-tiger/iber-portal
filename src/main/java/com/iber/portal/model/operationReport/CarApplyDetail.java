package com.iber.portal.model.operationReport;

import java.util.Date;

public class CarApplyDetail {
    private Integer id;

    private String orderId;

    private Integer memberId;

    private String lpn;

    private Date useDate;
    
    private Date beginTime;

    private Date endTime;

    private Double totalMileage;

    private Integer totalMinute;

    private Integer payMoney;

    private String couponNo;

    private Integer couponBalance;

    private String cityCode;

    private Double totalPayMoney;

    private Integer reductionPayMoney;

    private Integer rateId;

    private Integer integralNum;

    private Integer integralMny;

    private Integer totalMileageCost;

    private Integer totalMinuteCost;
 
    private String name;

    private String phone;
    
    private String idCard;
    
    private String parkName;
    
	private String returnParkName;
	
	private String carType;

	private Double totalMileageVal;
	
	private Integer totalMinuteVal;
	
	private Double totalPayMoneyVal;
	
	public Double getTotalMileageVal() {
		return totalMileageVal;
	}

	public void setTotalMileageVal(Double totalMileageVal) {
		this.totalMileageVal = totalMileageVal;
	}

	public Integer getTotalMinuteVal() {
		return totalMinuteVal;
	}

	public void setTotalMinuteVal(Integer totalMinuteVal) {
		this.totalMinuteVal = totalMinuteVal;
	}

	public Double getTotalPayMoneyVal() {
		return totalPayMoneyVal;
	}

	public void setTotalPayMoneyVal(Double totalPayMoneyVal) {
		this.totalPayMoneyVal = totalPayMoneyVal;
	}
	public Double getTotalMileage() {
		return totalMileage;
	}

	public void setTotalMileage(Double totalMileage) {
		this.totalMileage = totalMileage;
	}

	public Integer getTotalMinute() {
		return totalMinute;
	}

	public void setTotalMinute(Integer totalMinute) {
		this.totalMinute = totalMinute;
	}

	public Double getTotalPayMoney() {
		return totalPayMoney;
	}

	public void setTotalPayMoney(Double totalPayMoney) {
		this.totalPayMoney = totalPayMoney;
	}

	public String getParkName() {
		return parkName;
	}

	public void setParkName(String parkName) {
		this.parkName = parkName;
	}

	public String getReturnParkName() {
		return returnParkName;
	}

	public void setReturnParkName(String returnParkName) {
		this.returnParkName = returnParkName;
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

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
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


    public Date getUseDate() {
        return useDate;
    }

    public void setUseDate(Date useDate) {
        this.useDate = useDate;
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


    public Integer getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(Integer payMoney) {
        this.payMoney = payMoney;
    }

    public String getCouponNo() {
        return couponNo;
    }

    public void setCouponNo(String couponNo) {
        this.couponNo = couponNo == null ? null : couponNo.trim();
    }

    public Integer getCouponBalance() {
        return couponBalance;
    }

    public void setCouponBalance(Integer couponBalance) {
        this.couponBalance = couponBalance;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode == null ? null : cityCode.trim();
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

	public String getCarType() {
		return carType;
	}

	public void setCarType(String carType) {
		this.carType = carType;
	}
}