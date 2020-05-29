package com.iber.portal.model.timeShare;

import java.util.Date;

public class TimeSharePay {
    private Integer id;

    private String orderId;

    private Integer memberId;

    private String lpn;

    private Date beginTime;

    private Date endTime;

    private Double totalMileage;

    private Double totalMinute;

    private Integer payMoney;

    private String couponNo;

    private Integer couponBalance;

    private String cityCode;

    private Integer totalPayMoney;

    private Integer reductionPayMoney;

    private Integer rateId;

    private Integer integralNum;

    private Integer integralMny;

    private Integer totalMileageCost;

    private Integer totalMinuteCost;
    
    private String payStatus ;
    private String payType ;
    private Double freeCompensationMoney;
    private Integer nightTotalMileage ;
    private Integer nightTotalMinute ;
    private Integer nightMileageCost ;
    private Integer nightMinuteCost ;
    private Integer nightMinuteReductionMoney ;
    
    private String memberPhone;

    private Date payTime; //x_time_share_pay新增的pay_time
    private Date logPayTime;//x_time_share_pay_log 里面的pay_time

    public Date getLogPayTime() {
        return logPayTime;
    }

    public void setLogPayTime(Date logPayTime) {
        this.logPayTime = logPayTime;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public String getMemberPhone() {
		return memberPhone;
	}

	public void setMemberPhone(String memberPhone) {
		this.memberPhone = memberPhone;
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

    public Integer getTotalPayMoney() {
        return totalPayMoney;
    }

    public void setTotalPayMoney(Integer totalPayMoney) {
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

	public String getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public Double getFreeCompensationMoney() {
		return freeCompensationMoney;
	}

	public void setFreeCompensationMoney(Double freeCompensationMoney) {
		this.freeCompensationMoney = freeCompensationMoney;
	}

	public Integer getNightTotalMileage() {
		return nightTotalMileage;
	}

	public void setNightTotalMileage(Integer nightTotalMileage) {
		this.nightTotalMileage = nightTotalMileage;
	}

	public Integer getNightTotalMinute() {
		return nightTotalMinute;
	}

	public void setNightTotalMinute(Integer nightTotalMinute) {
		this.nightTotalMinute = nightTotalMinute;
	}

	public Integer getNightMileageCost() {
		return nightMileageCost;
	}

	public void setNightMileageCost(Integer nightMileageCost) {
		this.nightMileageCost = nightMileageCost;
	}

	public Integer getNightMinuteCost() {
		return nightMinuteCost;
	}

	public void setNightMinuteCost(Integer nightMinuteCost) {
		this.nightMinuteCost = nightMinuteCost;
	}

	public Integer getNightMinuteReductionMoney() {
		return nightMinuteReductionMoney;
	}

	public void setNightMinuteReductionMoney(Integer nightMinuteReductionMoney) {
		this.nightMinuteReductionMoney = nightMinuteReductionMoney;
	}

    @Override
    public String toString() {
        return "TimeSharePay{" +
                "id=" + id +
                ", orderId='" + orderId + '\'' +
                ", memberId=" + memberId +
                ", lpn='" + lpn + '\'' +
                ", beginTime=" + beginTime +
                ", endTime=" + endTime +
                ", totalMileage=" + totalMileage +
                ", totalMinute=" + totalMinute +
                ", payMoney=" + payMoney +
                ", couponNo='" + couponNo + '\'' +
                ", couponBalance=" + couponBalance +
                ", cityCode='" + cityCode + '\'' +
                ", totalPayMoney=" + totalPayMoney +
                ", reductionPayMoney=" + reductionPayMoney +
                ", rateId=" + rateId +
                ", integralNum=" + integralNum +
                ", integralMny=" + integralMny +
                ", totalMileageCost=" + totalMileageCost +
                ", totalMinuteCost=" + totalMinuteCost +
                ", payStatus='" + payStatus + '\'' +
                ", payType='" + payType + '\'' +
                ", freeCompensationMoney=" + freeCompensationMoney +
                ", nightTotalMileage=" + nightTotalMileage +
                ", nightTotalMinute=" + nightTotalMinute +
                ", nightMileageCost=" + nightMileageCost +
                ", nightMinuteCost=" + nightMinuteCost +
                ", nightMinuteReductionMoney=" + nightMinuteReductionMoney +
                ", memberPhone='" + memberPhone + '\'' +
                ", payTime=" + payTime +
                ", logPayTime=" + logPayTime +
                '}';
    }
}