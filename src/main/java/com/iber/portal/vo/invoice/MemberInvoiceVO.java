package com.iber.portal.vo.invoice;

import java.util.Date;

/**
 * 会员发票申请vo对象
 * 
 * @date create 2017/11/10 by zixb
 */
public class MemberInvoiceVO {
	/** */
	private Integer id;
	/** 会员id */
	private Integer memberId;
	/** 订单id 用英文逗号,隔开 */
	private String orderIds;
	/** 发票类型（电子 纸质）1电子发票 2纸质发票 */
	private Integer invoiceType;
	/** 发票抬头 个人 or 企业 */
	private String invocieHead;
	/** 发票内容（1汽车服务费、2充电服务费） */
	private Integer serverType;
	/** 发票金额 */
	private Integer money;
	/** 发票状态 （0待开票、1开票中、2已开票、3取消） */
	private Integer status;
	/** 发票号 */
	private String invoiceNo;
	/** 发票开具人员 */
	private String operator;
	/** 纳税人识别号 */
	private String taxpayerCode;
	/** 发票地址 */
	private String invoiceAddress;
	/** 发票电话 */
	private String invoicePhone;
	/** 开户银行及账号 */
	private String bankDetail;
	/** 邮费 */
	private Integer postage;
	/** 邮寄地址信息 */
	private Integer mailInfoId;
	/** 邮费支付状态 */
	private Integer payStatus;
	/** 快递公司 */
	private String fastMailCompany;
	/** 快递号 */
	private String fastMailNo;
	/** 电子发票url */
	private String elecInvoiceUrl;
	/** 备注 */
	private String remark;
	/** 创建时间 */
	private Date createTime;
	/** 更新时间 */
	private Date updateTime;
	/** 所属城市 */
	private String cityName;
	/** 会员姓名 */
	private String memberName;
	/** 会员电话 */
	private String memberPhone;
	/** 收件人地址 */
	private String receiverAddress;
	/** 收件人 */
	private String receiver;
	/** 收件人电话 */
	private String receiverPhone;

    private String province;
    
    private String city;
    
    private String layer;
    
    private String area;

    private String email;
    
    private  String payType;
    
    private Integer personType;
    
    private Integer postageSum;
    
    private Double orderMoney;
    
    private Double chargingMoney;
    
	public Double getOrderMoney() {
		return orderMoney;
	}

	public void setOrderMoney(Double orderMoney) {
		this.orderMoney = orderMoney;
	}

	public Double getChargingMoney() {
		return chargingMoney;
	}

	public void setChargingMoney(Double chargingMoney) {
		this.chargingMoney = chargingMoney;
	}

	public Integer getPostageSum() {
		return postageSum;
	}

	public void setPostageSum(Integer postageSum) {
		this.postageSum = postageSum;
	}

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getLayer() {
		return layer;
	}

	public void setLayer(String layer) {
		this.layer = layer;
	}

	public String getReceiverAddress() {
		return receiverAddress;
	}

	public void setReceiverAddress(String receiverAddress) {
		this.receiverAddress = receiverAddress;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getReceiverPhone() {
		return receiverPhone;
	}

	public void setReceiverPhone(String receiverPhone) {
		this.receiverPhone = receiverPhone;
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
		this.invoiceAddress = invoiceAddress == null ? null : invoiceAddress
				.trim();
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
		this.fastMailCompany = fastMailCompany == null ? null : fastMailCompany
				.trim();
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
		this.elecInvoiceUrl = elecInvoiceUrl == null ? null : elecInvoiceUrl
				.trim();
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

}
