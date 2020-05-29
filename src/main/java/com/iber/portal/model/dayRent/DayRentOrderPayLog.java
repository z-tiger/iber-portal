package com.iber.portal.model.dayRent;

import java.util.Date;

public class DayRentOrderPayLog {
    private Integer id;

    private String orderId;

    private Integer extendId;

    private String payType;

    private String payStatus;

    private Date payTime;

    private String tradeNo;

    private Date tradeTime;

    private Integer payMoney;

    private String orderType;

    private Integer memberId;

    private String payId;

    private String acpTraceNo;

    private String acpSettleDate;

    private Integer acpSettleAmt;

    private Date createTime;
    
    private String memberName;
    
    private String cityCode;
    
    private String cityName;
    
    private String lpn;

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

    public Integer getExtendId() {
        return extendId;
    }

    public void setExtendId(Integer extendId) {
        this.extendId = extendId;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType == null ? null : payType.trim();
    }

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus == null ? null : payStatus.trim();
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo == null ? null : tradeNo.trim();
    }

    public Date getTradeTime() {
        return tradeTime;
    }

    public void setTradeTime(Date tradeTime) {
        this.tradeTime = tradeTime;
    }

    public Integer getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(Integer payMoney) {
        this.payMoney = payMoney;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType == null ? null : orderType.trim();
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public String getPayId() {
        return payId;
    }

    public void setPayId(String payId) {
        this.payId = payId == null ? null : payId.trim();
    }

    public String getAcpTraceNo() {
        return acpTraceNo;
    }

    public void setAcpTraceNo(String acpTraceNo) {
        this.acpTraceNo = acpTraceNo == null ? null : acpTraceNo.trim();
    }

    public String getAcpSettleDate() {
        return acpSettleDate;
    }

    public void setAcpSettleDate(String acpSettleDate) {
        this.acpSettleDate = acpSettleDate == null ? null : acpSettleDate.trim();
    }

    public Integer getAcpSettleAmt() {
        return acpSettleAmt;
    }

    public void setAcpSettleAmt(Integer acpSettleAmt) {
        this.acpSettleAmt = acpSettleAmt;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
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

	public String getLpn() {
		return lpn;
	}

	public void setLpn(String lpn) {
		this.lpn = lpn;
	}
    
}