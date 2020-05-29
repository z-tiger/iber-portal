package com.iber.portal.model.base;

import java.math.BigDecimal;
import java.util.Date;

public class MemberRefundWorderUsecar {
    private Integer id;

    private String orderId;

    private String isTrafficCitation;

    private String auditor;

    private Date auditorTime;

    private String lpn;

    private String carVin;

    private String carEngine;

    private Integer trafficCitationCharge;

    private String trafficContent;

    private String auditorAccessoryFilename;

    private String auditorAccessoryFile;
    
    private String engineno; //发动机号
    
    private String classno;//车架号
    
    private BigDecimal wzFee;//违章费用
    
    private int wzPoint;//违章分数
    
    private BigDecimal buyPointFee;//买分总费用
    
    private BigDecimal totalWzFee;//处理违章总费用
    
    private Integer wzPayStatus;//由公司支付或者由会员支付
 
	public Integer getWzPayStatus() {
		return wzPayStatus;
	}

	public void setWzPayStatus(Integer wzPayStatus) {
		this.wzPayStatus = wzPayStatus;
	}

	public BigDecimal getBuyPointFee() {
		return buyPointFee;
	}

	public void setBuyPointFee(BigDecimal buyPointFee) {
		this.buyPointFee = buyPointFee;
	}

	public BigDecimal getTotalWzFee() {
		return totalWzFee;
	}

	public void setTotalWzFee(BigDecimal totalWzFee) {
		this.totalWzFee = totalWzFee;
	}

	public BigDecimal getWzFee() {
		return wzFee;
	}

	public void setWzFee(BigDecimal wzFee) {
		this.wzFee = wzFee;
	}

	public int getWzPoint() {
		return wzPoint;
	}

	public void setWzPoint(int wzPoint) {
		this.wzPoint = wzPoint;
	}

	public String getEngineno() {
		return engineno;
	}

	public void setEngineno(String engineno) {
		this.engineno = engineno;
	}

	public String getClassno() {
		return classno;
	}

	public void setClassno(String classno) {
		this.classno = classno;
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

    public String getIsTrafficCitation() {
        return isTrafficCitation;
    }

    public void setIsTrafficCitation(String isTrafficCitation) {
        this.isTrafficCitation = isTrafficCitation == null ? null : isTrafficCitation.trim();
    }

    public String getAuditor() {
        return auditor;
    }

    public void setAuditor(String auditor) {
        this.auditor = auditor == null ? null : auditor.trim();
    }

    public Date getAuditorTime() {
        return auditorTime;
    }

    public void setAuditorTime(Date auditorTime) {
        this.auditorTime = auditorTime;
    }

    public String getLpn() {
        return lpn;
    }

    public void setLpn(String lpn) {
        this.lpn = lpn == null ? null : lpn.trim();
    }

    public String getCarVin() {
        return carVin;
    }

    public void setCarVin(String carVin) {
        this.carVin = carVin == null ? null : carVin.trim();
    }

    public String getCarEngine() {
        return carEngine;
    }

    public void setCarEngine(String carEngine) {
        this.carEngine = carEngine == null ? null : carEngine.trim();
    }

    public Integer getTrafficCitationCharge() {
        return trafficCitationCharge;
    }

    public void setTrafficCitationCharge(Integer trafficCitationCharge) {
        this.trafficCitationCharge = trafficCitationCharge;
    }

    public String getTrafficContent() {
        return trafficContent;
    }

    public void setTrafficContent(String trafficContent) {
        this.trafficContent = trafficContent == null ? null : trafficContent.trim();
    }

    public String getAuditorAccessoryFilename() {
        return auditorAccessoryFilename;
    }

    public void setAuditorAccessoryFilename(String auditorAccessoryFilename) {
        this.auditorAccessoryFilename = auditorAccessoryFilename == null ? null : auditorAccessoryFilename.trim();
    }

    public String getAuditorAccessoryFile() {
        return auditorAccessoryFile;
    }

    public void setAuditorAccessoryFile(String auditorAccessoryFile) {
        this.auditorAccessoryFile = auditorAccessoryFile == null ? null : auditorAccessoryFile.trim();
    }


    private Integer memberId;


    private Date beginTime;

    private Date endTime;

    private Double totalMileage;

    private Double totalMinute;

    private Integer payMoney;

    private String couponNo;

    private Integer couponBalance;

    private String cityCode;
    
    private String cityCodeName;

    private Integer totalPayMoney;

    private Integer reductionPayMoney;

    private Integer rateId;

    private Integer integralNum;

    private Integer integralMny;

    private Integer totalMileageCost;

    private Integer totalMinuteCost;


    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
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
    
    public void setCityCodeName(String cityCodeName) {
		this.cityCodeName = cityCodeName;
	}
    
    public String getCityCodeName() {
		return cityCodeName;
	}
    
    private String custName;
    private String custPhone;
    
    public void setCustName(String custName) {
		this.custName = custName;
	}
    
    public void setCustPhone(String custPhone) {
		this.custPhone = custPhone;
	}
    
    public String getCustName() {
		return custName;
	}
    
    public String getCustPhone() {
		return custPhone;
	}
}