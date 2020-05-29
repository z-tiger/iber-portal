package com.iber.portal.model.dayRent;

import java.util.Date;

public class LongRentOrder {
    private Integer id;

    private String orderId;

    private String memberId;

    private String cityCode;

    private Integer modelId;

    private String lpn;

    private Integer unpaidAttachedCnt;

    private Integer changedCarTimes;

    private Integer orderMoney;

    private String couponNo;

    private Integer couponBalance;

    private Integer discountMoney;

    private Integer payMoney;

    private String payType;

    private Integer rentMoney;

    private Integer freeCompensateMoney;

    private String orderStatus;

    private String payStatus;

    private Byte invoiceStatus;

    private Date beginTime;

    private Date endTime;

    private Double mileage;

    private Byte duration;

    private Byte reletNo;

    private String extendTsOrderId;

    private Date takeCarTime;

    private Integer takeParkId;

    private Date returnCarTime;

    private Integer returnCarParkId;

    private Byte remindTimes;

    private Date latestRemind;

    private Date createTime;

    private Integer version;

    private String  cityName  ;
    private String  memberName  ;
    private String  parkName ;
    private String  type;
    private String   phone;
    private Date latestChangedCarTime;
    private Date returnTime;
    private Date orderTime ;
    private Date returnTimes;
    
    /**日租收入报表续租订单金额 、折扣、优惠券面值、不计免赔**/
    private Integer reletCouponBalance ;
    private Integer reletDiscountMoney ;
    private Integer reletFreeMoney ;
    private Integer reletOrderMoney ;
    // 合计
    private String totalOrderMoney;
    private String totalReletOrderMoney;
    private String totalFreeMoney;
    private String totalReletFreeMoney;
    private String totalPayMoney;
    private String totalCouponMoney;
    private String totalReletCouponMoney;
    private String totalDiscountMoney;
    private String totalReletDiscountMoney;
    
    
    
	public String getTotalOrderMoney() {
		return totalOrderMoney;
	}

	public void setTotalOrderMoney(String totalOrderMoney) {
		this.totalOrderMoney = totalOrderMoney;
	}

	public String getTotalReletOrderMoney() {
		return totalReletOrderMoney;
	}

	public void setTotalReletOrderMoney(String totalReletOrderMoney) {
		this.totalReletOrderMoney = totalReletOrderMoney;
	}

	public String getTotalFreeMoney() {
		return totalFreeMoney;
	}

	public void setTotalFreeMoney(String totalFreeMoney) {
		this.totalFreeMoney = totalFreeMoney;
	}

	public String getTotalReletFreeMoney() {
		return totalReletFreeMoney;
	}

	public void setTotalReletFreeMoney(String totalReletFreeMoney) {
		this.totalReletFreeMoney = totalReletFreeMoney;
	}

	public String getTotalPayMoney() {
		return totalPayMoney;
	}

	public void setTotalPayMoney(String totalPayMoney) {
		this.totalPayMoney = totalPayMoney;
	}

	public String getTotalCouponMoney() {
		return totalCouponMoney;
	}

	public void setTotalCouponMoney(String totalCouponMoney) {
		this.totalCouponMoney = totalCouponMoney;
	}

	public String getTotalReletCouponMoney() {
		return totalReletCouponMoney;
	}

	public void setTotalReletCouponMoney(String totalReletCouponMoney) {
		this.totalReletCouponMoney = totalReletCouponMoney;
	}

	public String getTotalDiscountMoney() {
		return totalDiscountMoney;
	}

	public void setTotalDiscountMoney(String totalDiscountMoney) {
		this.totalDiscountMoney = totalDiscountMoney;
	}

	public String getTotalReletDiscountMoney() {
		return totalReletDiscountMoney;
	}

	public void setTotalReletDiscountMoney(String totalReletDiscountMoney) {
		this.totalReletDiscountMoney = totalReletDiscountMoney;
	}

	public Date getReturnTimes() {
		return returnTimes;
	}

	public void setReturnTimes(Date returnTimes) {
		this.returnTimes = returnTimes;
	}

	public Date getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}

	public Date getReturnTime() {
		return returnTime;
	}

	public void setReturnTime(Date returnTime) {
		this.returnTime = returnTime;
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

	public String getParkName() {
		return parkName;
	}

	public void setParkName(String parkName) {
		this.parkName = parkName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
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

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId == null ? null : memberId.trim();
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode == null ? null : cityCode.trim();
    }

    public Integer getModelId() {
        return modelId;
    }

    public void setModelId(Integer modelId) {
        this.modelId = modelId;
    }

    public String getLpn() {
        return lpn;
    }

    public void setLpn(String lpn) {
        this.lpn = lpn == null ? null : lpn.trim();
    }

    public Integer getUnpaidAttachedCnt() {
        return unpaidAttachedCnt;
    }

    public void setUnpaidAttachedCnt(Integer unpaidAttachedCnt) {
        this.unpaidAttachedCnt = unpaidAttachedCnt;
    }

    public Integer getChangedCarTimes() {
        return changedCarTimes;
    }

    public void setChangedCarTimes(Integer changedCarTimes) {
        this.changedCarTimes = changedCarTimes;
    }

    public Integer getOrderMoney() {
        return orderMoney;
    }

    public void setOrderMoney(Integer orderMoney) {
        this.orderMoney = orderMoney;
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

    public Integer getDiscountMoney() {
        return discountMoney;
    }

    public void setDiscountMoney(Integer discountMoney) {
        this.discountMoney = discountMoney;
    }

    public Integer getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(Integer payMoney) {
        this.payMoney = payMoney;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType == null ? null : payType.trim();
    }

    public Integer getRentMoney() {
        return rentMoney;
    }

    public void setRentMoney(Integer rentMoney) {
        this.rentMoney = rentMoney;
    }

    public Integer getFreeCompensateMoney() {
        return freeCompensateMoney;
    }

    public void setFreeCompensateMoney(Integer freeCompensateMoney) {
        this.freeCompensateMoney = freeCompensateMoney;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus == null ? null : orderStatus.trim();
    }

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus == null ? null : payStatus.trim();
    }

    public Byte getInvoiceStatus() {
        return invoiceStatus;
    }

    public void setInvoiceStatus(Byte invoiceStatus) {
        this.invoiceStatus = invoiceStatus;
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

    public Double getMileage() {
        return mileage;
    }

    public void setMileage(Double mileage) {
        this.mileage = mileage;
    }

    public Byte getDuration() {
        return duration;
    }

    public void setDuration(Byte duration) {
        this.duration = duration;
    }

    public Byte getReletNo() {
        return reletNo;
    }

    public void setReletNo(Byte reletNo) {
        this.reletNo = reletNo;
    }

    public String getExtendTsOrderId() {
        return extendTsOrderId;
    }

    public void setExtendTsOrderId(String extendTsOrderId) {
        this.extendTsOrderId = extendTsOrderId == null ? null : extendTsOrderId.trim();
    }

    public Date getTakeCarTime() {
        return takeCarTime;
    }

    public void setTakeCarTime(Date takeCarTime) {
        this.takeCarTime = takeCarTime;
    }

    public Integer getTakeParkId() {
        return takeParkId;
    }

    public void setTakeParkId(Integer takeParkId) {
        this.takeParkId = takeParkId;
    }

    public Date getReturnCarTime() {
        return returnCarTime;
    }

    public void setReturnCarTime(Date returnCarTime) {
        this.returnCarTime = returnCarTime;
    }

    public Integer getReturnCarParkId() {
        return returnCarParkId;
    }

    public void setReturnCarParkId(Integer returnCarParkId) {
        this.returnCarParkId = returnCarParkId;
    }

    public Byte getRemindTimes() {
        return remindTimes;
    }

    public void setRemindTimes(Byte remindTimes) {
        this.remindTimes = remindTimes;
    }

    public Date getLatestRemind() {
        return latestRemind;
    }

    public void setLatestRemind(Date latestRemind) {
        this.latestRemind = latestRemind;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Date getLatestChangedCarTime() {
        return latestChangedCarTime;
    }

    public void setLatestChangedCarTime(Date latestChangedCarTime) {
        this.latestChangedCarTime = latestChangedCarTime;
    }

	public Integer getReletCouponBalance() {
		return reletCouponBalance;
	}

	public void setReletCouponBalance(Integer reletCouponBalance) {
		this.reletCouponBalance = reletCouponBalance;
	}

	public Integer getReletDiscountMoney() {
		return reletDiscountMoney;
	}

	public void setReletDiscountMoney(Integer reletDiscountMoney) {
		this.reletDiscountMoney = reletDiscountMoney;
	}

	public Integer getReletFreeMoney() {
		return reletFreeMoney;
	}

	public void setReletFreeMoney(Integer reletFreeMoney) {
		this.reletFreeMoney = reletFreeMoney;
	}

	public Integer getReletOrderMoney() {
		return reletOrderMoney;
	}

	public void setReletOrderMoney(Integer reletOrderMoney) {
		this.reletOrderMoney = reletOrderMoney;
	}
    
}