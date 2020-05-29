package com.iber.portal.vo.report;

import java.util.Date;

public class TimeShareIncomeReport {
	
	private Date endTime ;
	private String cityName ;
	private String lpn ;
	private String brandName ;
	private String type;
	private String name ;
	private String phone ;
	private int timeUnit ;
	private double timeRate ;
	private double milesRate ;
	private double minConsump ;
	private double maxConsump ;
	private Double totalMinute ;
	private Double totalMileage ;
	private Double payMoney ;
	private Double reductionPayMoney ;
	private String couponNo ;
	private double couponBalance ;
	private Double totalMinuteSum;
	private Double payMoneySum;
	private Double totalMileageSum;
	private Double reductionPayMoneySum;
	private Double totalPayMoneySum ;
	private Double totalPayMoney;
	private Double totalInvoiceMoney;
	private Integer invocieStatus;
	private Double invocieMoney;

	private Double freeCompensationMoney;
	private Date payTime;
	private String carModel;
    private Double freeCompensationMoneySum;
    private String payStatus;

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }

    public Double getFreeCompensationMoneySum() {
        return freeCompensationMoneySum;
    }

    public void setFreeCompensationMoneySum(Double freeCompensationMoneySum) {
        this.freeCompensationMoneySum = freeCompensationMoneySum;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public Double getFreeCompensationMoney() {
        return freeCompensationMoney;
    }

    public void setFreeCompensationMoney(Double freeCompensationMoney) {
        this.freeCompensationMoney = freeCompensationMoney;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public Double getTotalPayMoneySum() {
		return totalPayMoneySum;
	}
	public void setTotalPayMoneySum(Double totalPayMoneySum) {
		this.totalPayMoneySum = totalPayMoneySum;
	}
	public Double getTotalInvoiceMoney() {
		return totalInvoiceMoney;
	}
	public void setTotalInvoiceMoney(Double totalInvoiceMoney) {
		this.totalInvoiceMoney = totalInvoiceMoney;
	}
	public Integer getInvocieStatus() {
		return invocieStatus;
	}
	public void setInvocieStatus(Integer invocieStatus) {
		this.invocieStatus = invocieStatus;
	}
	public Double getInvocieMoney() {
		return invocieMoney;
	}
	public void setInvocieMoney(Double invocieMoney) {
		this.invocieMoney = invocieMoney;
	}
	public Double getTotalPayMoney() {
		return totalPayMoney;
	}
	public void setTotalPayMoney(Double totalPayMoney) {
		this.totalPayMoney = totalPayMoney;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getLpn() {
		return lpn;
	}
	public void setLpn(String lpn) {
		this.lpn = lpn;
	}
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
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
	public int getTimeUnit() {
		return timeUnit;
	}
	public void setTimeUnit(int timeUnit) {
		this.timeUnit = timeUnit;
	}
	public double getTimeRate() {
		return timeRate;
	}
	public void setTimeRate(double timeRate) {
		this.timeRate = timeRate;
	}
	public double getMilesRate() {
		return milesRate;
	}
	public void setMilesRate(double milesRate) {
		this.milesRate = milesRate;
	}
	public double getMinConsump() {
		return minConsump;
	}
	public void setMinConsump(double minConsump) {
		this.minConsump = minConsump;
	}
	public double getMaxConsump() {
		return maxConsump;
	}
	public void setMaxConsump(double maxConsump) {
		this.maxConsump = maxConsump;
	}
	public Double getTotalMinute() {
		return totalMinute;
	}
	public void setTotalMinute(Double totalMinute) {
		this.totalMinute = totalMinute;
	}
	public Double getTotalMileage() {
		return totalMileage;
	}
	public void setTotalMileage(Double totalMileage) {
		this.totalMileage = totalMileage;
	}
	public Double getPayMoney() {
		return payMoney;
	}
	public void setPayMoney(Double payMoney) {
		this.payMoney = payMoney;
	}
	public Double getReductionPayMoney() {
		return reductionPayMoney;
	}
	public void setReductionPayMoney(Double reductionPayMoney) {
		this.reductionPayMoney = reductionPayMoney;
	}
	public String getCouponNo() {
		return couponNo;
	}
	public void setCouponNo(String couponNo) {
		this.couponNo = couponNo;
	}
	public double getCouponBalance() {
		return couponBalance;
	}
	public void setCouponBalance(double couponBalance) {
		this.couponBalance = couponBalance;
	}
	public Double getTotalMinuteSum() {
		return totalMinuteSum;
	}
	public void setTotalMinuteSum(Double totalMinuteSum) {
		this.totalMinuteSum = totalMinuteSum;
	}
	public Double getPayMoneySum() {
		return payMoneySum;
	}
	public void setPayMoneySum(Double payMoneySum) {
		this.payMoneySum = payMoneySum;
	}
	public Double getTotalMileageSum() {
		return totalMileageSum;
	}
	public void setTotalMileageSum(Double totalMileageSum) {
		this.totalMileageSum = totalMileageSum;
	}
	public Double getReductionPayMoneySum() {
		return reductionPayMoneySum;
	}
	public void setReductionPayMoneySum(Double reductionPayMoneySum) {
		this.reductionPayMoneySum = reductionPayMoneySum;
	}

    @Override
    public String toString() {
        return "TimeShareIncomeReport{" +
                "endTime=" + endTime +
                ", cityName='" + cityName + '\'' +
                ", lpn='" + lpn + '\'' +
                ", brandName='" + brandName + '\'' +
                ", type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", timeUnit=" + timeUnit +
                ", timeRate=" + timeRate +
                ", milesRate=" + milesRate +
                ", minConsump=" + minConsump +
                ", maxConsump=" + maxConsump +
                ", totalMinute=" + totalMinute +
                ", totalMileage=" + totalMileage +
                ", payMoney=" + payMoney +
                ", reductionPayMoney=" + reductionPayMoney +
                ", couponNo='" + couponNo + '\'' +
                ", couponBalance=" + couponBalance +
                ", totalMinuteSum=" + totalMinuteSum +
                ", payMoneySum=" + payMoneySum +
                ", totalMileageSum=" + totalMileageSum +
                ", reductionPayMoneySum=" + reductionPayMoneySum +
                ", totalPayMoneySum=" + totalPayMoneySum +
                ", totalPayMoney=" + totalPayMoney +
                ", totalInvoiceMoney=" + totalInvoiceMoney +
                ", invocieStatus=" + invocieStatus +
                ", invocieMoney=" + invocieMoney +
                ", freeCompensationMoney=" + freeCompensationMoney +
                ", payTime=" + payTime +
                ", carModel='" + carModel + '\'' +
                '}';
    }
}
