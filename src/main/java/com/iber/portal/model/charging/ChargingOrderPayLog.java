package com.iber.portal.model.charging;

import java.util.Date;

public class ChargingOrderPayLog {
    private Integer id;

    private String orderId;//充电订单号

    private String payType;//支付类型

    private String payStatus;//支付状态 0 未支付 1已支付

    private Date payTime;//支付时间

    private String tradeNo;//交易号

    private Date tradeTime;//交易时间

    private Integer payMoney;//支付金额（单位：分）

    private String orderType;//订单类型（分时 充电桩）

    private Date createTime;

    private Integer memberId;

    private String payId;

    private String acpTraceNo;//收单机构对账时使用，该域由银联系统产生

    private String acpSettleDate;//清算日期

    private Integer acpSettleAmt;//清算金额

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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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
}