package com.iber.portal.model.dayRent;

import java.util.Date;

public class DayRentOrderRefundLog {
    private Integer id;

    private String orderId;

    private Integer extendId;

    private String refundType;

    private String refundStatus;

    private Date refundTime;

    private String tradeNo;

    private Date tradeTime;

    private Integer refundMoney;

    private String orderType;

    private Integer memberId;

    private String refundId;

    private String acpTraceNo;

    private String acpSettleDate;

    private Integer acpSettleAmt;

    private Date createTime;

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

    public String getRefundType() {
        return refundType;
    }

    public void setRefundType(String refundType) {
        this.refundType = refundType == null ? null : refundType.trim();
    }

    public String getRefundStatus() {
        return refundStatus;
    }

    public void setRefundStatus(String refundStatus) {
        this.refundStatus = refundStatus == null ? null : refundStatus.trim();
    }

    public Date getRefundTime() {
        return refundTime;
    }

    public void setRefundTime(Date refundTime) {
        this.refundTime = refundTime;
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

    public Integer getRefundMoney() {
        return refundMoney;
    }

    public void setRefundMoney(Integer refundMoney) {
        this.refundMoney = refundMoney;
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

    public String getRefundId() {
        return refundId;
    }

    public void setRefundId(String refundId) {
        this.refundId = refundId == null ? null : refundId.trim();
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
}