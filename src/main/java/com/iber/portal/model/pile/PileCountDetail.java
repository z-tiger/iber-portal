package com.iber.portal.model.pile;

import java.util.Date;

public class PileCountDetail {
	private Integer id;//   
	private Integer memberId;//   会员id
	private String cityCode;//   城市编码
	private String orderNumber;//   订单号
	private Integer memberLevel;//   会员等级
	private Integer memberType;//   会员类型(1-个人会员，2-企业会员,3-内部员工)
	private Integer money;//   金额
	private Date countTime;//   统计日期
	private Integer hour;//   时间段
	private Integer countType;//   统计类型（1-充电次数，2-充电人数，3-充电电度数，4-充电收入）
	private String relatedOrderId;//关联订单
	private Date createTime;//    创建时间
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
	public String getCityCode() {
		return cityCode;
	}
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	public String getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	public Integer getMemberLevel() {
		return memberLevel;
	}
	public void setMemberLevel(Integer memberLevel) {
		this.memberLevel = memberLevel;
	}
	public Integer getMemberType() {
		return memberType;
	}
	public void setMemberType(Integer memberType) {
		this.memberType = memberType;
	}
	public Integer getMoney() {
		return money;
	}
	public void setMoney(Integer money) {
		this.money = money;
	}
	public Date getCountTime() {
		return countTime;
	}
	public void setCountTime(Date countTime) {
		this.countTime = countTime;
	}
	public Integer getHour() {
		return hour;
	}
	public void setHour(Integer hour) {
		this.hour = hour;
	}
	public Integer getCountType() {
		return countType;
	}
	public void setCountType(Integer countType) {
		this.countType = countType;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getRelatedOrderId() {
		return relatedOrderId;
	}
	public void setRelatedOrderId(String relatedOrderId) {
		this.relatedOrderId = relatedOrderId;
	}
	
	
}
