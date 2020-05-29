package com.iber.portal.model.operationReport;

import java.io.Serializable;
import java.util.Date;

public class MemberRecharge implements Serializable{

	private static final long serialVersionUID = 1L;
	private Integer memberId;    // 会员id号
	private String name;         // 会员姓名
	private String rechargeId;   // 充值单号
	private String rechargeCategory; //充值类型, 押金充值或余额充值
	private Integer rechargeMoney; // 充值金额
	private Date tradeTime;     // 交易成功时间
	private String bankCategory; // 交易的银行类别或者第三方支付机构
	private String remark;    // 备注
	private String tradeStatus;// 交易状态
	private String tradeNo;    // 银行交易流水号
	private Date rechargeCreatTime;
	
 	public Date getRechargeCreatTime() {
		return rechargeCreatTime;
	}
	public void setRechargeCreatTime(Date rechargeCreatTime) {
		this.rechargeCreatTime = rechargeCreatTime;
	}
	public String getTradeNo() {
		return tradeNo;
	}
	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}
	public String getRechargeId() {
		return rechargeId;
	}
	public void setRechargeId(String rechargeId) {
		this.rechargeId = rechargeId;
	}
	public String getRechargeCategory() {
		return rechargeCategory;
	}
	public void setRechargeCategory(String rechargeCategory) {
		this.rechargeCategory = rechargeCategory;
	}
	public String getBankCategory() {
		return bankCategory;
	}
	public void setBankCategory(String bankCategory) {
		this.bankCategory = bankCategory;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getTradeStatus() {
		return tradeStatus;
	}
	public void setTradeStatus(String tradeStatus) {
		this.tradeStatus = tradeStatus;
	}
	public Date getRechargeCreateTime() {
		return rechargeCreateTime;
	}
	public void setRechargeCreateTime(Date rechargeCreateTime) {
		this.rechargeCreateTime = rechargeCreateTime;
	}
	private Date rechargeCreateTime;  // 充值创建时间
	
	public Integer getMemberId() {
		return memberId;
	}
	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getRechargeMoney() {
		return rechargeMoney;
	}
	public void setRechargeMoney(Integer rechargeMoney) {
		this.rechargeMoney = rechargeMoney;
	}
	public Date getTradeTime() {
		return tradeTime;
	}
	public void setTradeTime(Date tradeTime) {
		this.tradeTime = tradeTime;
	}
	

}
