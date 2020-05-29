package com.iber.portal.model.invoice;

import java.util.Date;

public class MemberInvoice {
    private Integer id;

    private Integer memberId;

    private String orderIds;

    private Integer invoiceType;

    private String invocieHead;

    private Integer serverType;

    private Integer money;

    private Integer status;

    private String invoiceNo;

    private String operator;

    private String taxpayerCode;

    private String invoiceAddress;

    private String invoicePhone;

    private String bankDetail;

    private Integer postage;

    private Integer mailInfoId;

    private Integer payStatus;

    private String fastMailCompany;

    private String fastMailNo;

    private String elecInvoiceUrl;

    private String remark;

    private Date createTime;

    private Date updateTime;
    
    private String payType;
    
    private Integer personType;
    
    private String elecInvoiceSize;
    
    public Integer getPersonType() {
		return personType;
	}

	public void setPersonType(Integer personType) {
		this.personType = personType;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public String getOrderIds() {
        return orderIds;
    }

    public void setOrderIds(String orderIds) {
        this.orderIds = orderIds == null ? null : orderIds.trim();
    }

    public Integer getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(Integer invoiceType) {
        this.invoiceType = invoiceType;
    }

    public String getInvocieHead() {
        return invocieHead;
    }

    public void setInvocieHead(String invocieHead) {
        this.invocieHead = invocieHead == null ? null : invocieHead.trim();
    }

    public Integer getServerType() {
        return serverType;
    }

    public void setServerType(Integer serverType) {
        this.serverType = serverType;
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo == null ? null : invoiceNo.trim();
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator == null ? null : operator.trim();
    }

    public String getTaxpayerCode() {
        return taxpayerCode;
    }

    public void setTaxpayerCode(String taxpayerCode) {
        this.taxpayerCode = taxpayerCode == null ? null : taxpayerCode.trim();
    }

    public String getInvoiceAddress() {
        return invoiceAddress;
    }

    public void setInvoiceAddress(String invoiceAddress) {
        this.invoiceAddress = invoiceAddress == null ? null : invoiceAddress.trim();
    }

    public String getInvoicePhone() {
        return invoicePhone;
    }

    public void setInvoicePhone(String invoicePhone) {
        this.invoicePhone = invoicePhone == null ? null : invoicePhone.trim();
    }

    public String getBankDetail() {
        return bankDetail;
    }

    public void setBankDetail(String bankDetail) {
        this.bankDetail = bankDetail == null ? null : bankDetail.trim();
    }

    public Integer getPostage() {
        return postage;
    }

    public void setPostage(Integer postage) {
        this.postage = postage;
    }

    public Integer getMailInfoId() {
        return mailInfoId;
    }

    public void setMailInfoId(Integer mailInfoId) {
        this.mailInfoId = mailInfoId;
    }

    public Integer getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(Integer payStatus) {
        this.payStatus = payStatus;
    }

    public String getFastMailCompany() {
        return fastMailCompany;
    }

    public void setFastMailCompany(String fastMailCompany) {
        this.fastMailCompany = fastMailCompany == null ? null : fastMailCompany.trim();
    }

    public String getFastMailNo() {
        return fastMailNo;
    }

    public void setFastMailNo(String fastMailNo) {
        this.fastMailNo = fastMailNo == null ? null : fastMailNo.trim();
    }

    public String getElecInvoiceUrl() {
        return elecInvoiceUrl;
    }

    public void setElecInvoiceUrl(String elecInvoiceUrl) {
        this.elecInvoiceUrl = elecInvoiceUrl == null ? null : elecInvoiceUrl.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

	public void setElecInvoiceSize(String elecInvoiceSize) {
		this.elecInvoiceSize = elecInvoiceSize;
	}
	
	public String getElecInvoiceSize(){
		return elecInvoiceSize;
	}
}