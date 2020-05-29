package com.iber.portal.model.operationReport;

import java.util.Date;

public class CarMonthlyOperation {
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

    private Integer couponBalance;

    private String cityCode;

    private String totalPayMoney;

    private Integer reductionPayMoney;

    private Integer rateId;

    private Integer integralNum;

    private Integer integralMny;

    private Integer totalMileageCost;

    private Integer totalMinuteCost;
    
    private String name;
    
    private String idcard;
    
    private Integer totalMoney;
    
    private Integer rentTime;
    
    private String sumCompensation;
    
    private String carType;
    
    private String rentType;
    
    private Integer rentTotal;
    
    private String carUseRate;
    
    
    
    @Override
	public String toString() {
		return "CarMonthlyOperation [id=" + id + ", orderId=" + orderId
				+ ", memberId=" + memberId + ", lpn=" + lpn + ", beginTime="
				+ beginTime + ", endTime=" + endTime + ", totalMileage="
				+ totalMileage + ", totalMinute=" + totalMinute + ", payMoney="
				+ payMoney + ", couponNo=" + couponNo + ", couponBalance="
				+ couponBalance + ", cityCode=" + cityCode + ", totalPayMoney="
				+ totalPayMoney + ", reductionPayMoney=" + reductionPayMoney
				+ ", rateId=" + rateId + ", integralNum=" + integralNum
				+ ", integralMny=" + integralMny + ", totalMileageCost="
				+ totalMileageCost + ", totalMinuteCost=" + totalMinuteCost
				+ ", name=" + name + ", idcard=" + idcard + ", totalMoney="
				+ totalMoney + ", rentTime=" + rentTime + ", sumCompensation="
				+ sumCompensation + ", carType=" + carType + ", rentType="
				+ rentType + ", rentTotal=" + rentTotal + ", carUseRate="
				+ carUseRate + ", rentTimeSum=" + rentTimeSum
				+ ", totalMileageSum=" + totalMileageSum + ", payMoneySum="
				+ payMoneySum + ", freeCompensationMoneySum="
				+ freeCompensationMoneySum + ", totalPayMoneySum="
				+ totalPayMoneySum + ", totalMinuteSum=" + totalMinuteSum + "]";
	}

    private Integer rentTimeSum;//租赁次数总合计
	private Double totalMileageSum;//里程总合计
	private Double payMoneySum;//支付金额总合计
	private Double freeCompensationMoneySum;//不计免赔总合计
	private Double totalPayMoneySum;//订单金额总合计
	private Double totalMinuteSum;//行驶时间总合计
	public Integer getRentTimeSum() {
		return rentTimeSum;
	}
	public void setRentTimeSum(Integer rentTimeSum) {
		this.rentTimeSum = rentTimeSum;
	}
	public Double getTotalMileageSum() {
		return totalMileageSum;
	}
	public void setTotalMileageSum(Double totalMileageSum) {
		this.totalMileageSum = totalMileageSum;
	}
	public Double getPayMoneySum() {
		return payMoneySum;
	}
	public void setPayMoneySum(Double payMoneySum) {
		this.payMoneySum = payMoneySum;
	}
	public Double getFreeCompensationMoneySum() {
		return freeCompensationMoneySum;
	}
	public void setFreeCompensationMoneySum(Double freeCompensationMoneySum) {
		this.freeCompensationMoneySum = freeCompensationMoneySum;
	}
	public Double getTotalPayMoneySum() {
		return totalPayMoneySum;
	}
	public void setTotalPayMoneySum(Double totalPayMoneySum) {
		this.totalPayMoneySum = totalPayMoneySum;
	}
	public Double getTotalMinuteSum() {
		return totalMinuteSum;
	}
	public void setTotalMinuteSum(Double totalMinuteSum) {
		this.totalMinuteSum = totalMinuteSum;
	}
    public String getName() {
		return name;
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

	public String getTotalPayMoney() {
		return totalPayMoney;
	}

	public void setTotalPayMoney(String totalPayMoney) {
		this.totalPayMoney = totalPayMoney;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
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

    public String getPayMoney() {
		return payMoney;
	}

	public void setPayMoney(String payMoney) {
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

	public Integer getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(Integer totalMoney) {
		this.totalMoney = totalMoney;
	}

	public Integer getRentTime() {
		return rentTime;
	}

	public void setRentTime(Integer rentTime) {
		this.rentTime = rentTime;
	}
	
	public String getSumCompensation() {
		return sumCompensation;
	}

	public void setSumCompensation(String sumCompensation) {
		this.sumCompensation = sumCompensation;
	}

	public String getCarType() {
		return carType;
	}

	public void setCarType(String carType) {
		this.carType = carType;
	}

	public String getRentType() {
		return rentType;
	}

	public void setRentType(String rentType) {
		this.rentType = rentType;
	}

	public Integer getRentTotal() {
		return rentTotal;
	}

	public void setRentTotal(Integer rentTotal) {
		this.rentTotal = rentTotal;
	}

	public String getCarUseRate() {
		return carUseRate;
	}

	public void setCarUseRate(String carUseRate) {
		this.carUseRate = carUseRate;
	}

}