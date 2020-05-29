package com.iber.portal.model.base;

/**
 * 网点使用明细
 * <br>
 * <b>功能：</b>ParkUseDetailEntity<br>
 * <b>作者：</b>www.jeecg.org<br>
 * <b>日期：</b> Feb 2, 2013 <br>
 * <b>版权所有：<b>版权所有(C) 2013，www.jeecg.org<br>
 */
public class ParkUseDetail {
	
		private java.lang.Integer id;//   	private java.lang.String cityCode;//   	private java.lang.String orderId;//   订单号	private java.lang.String orderType;//   订单类型，TS:时租、DR:日租、长租、充电	private java.lang.Integer memberId;//   会员ID	private java.lang.Integer memberType;//   会员类型，0:普通、1:政企	private java.lang.Integer parkId;//   网点ID	private java.lang.String parkName;//   网点名称	private java.util.Date countTime;//   统计日期，YYYY-MM-DD	private java.lang.Integer countType;//   统计类型：0: 约车、1: 还车、2: 充电	private java.util.Date createTime;//   创建时间	private java.lang.Integer countHour;//   订单小时，即订单创建时的小时值
		public java.lang.Integer getId() {	    return this.id;	}	public void setId(java.lang.Integer id) {	    this.id=id;	}	public java.lang.String getCityCode() {	    return this.cityCode;	}	public void setCityCode(java.lang.String cityCode) {	    this.cityCode=cityCode;	}	public java.lang.String getOrderId() {	    return this.orderId;	}	public void setOrderId(java.lang.String orderId) {	    this.orderId=orderId;	}	public java.lang.String getOrderType() {	    return this.orderType;	}	public void setOrderType(java.lang.String orderType) {	    this.orderType=orderType;	}	public java.lang.Integer getMemberId() {	    return this.memberId;	}	public void setMemberId(java.lang.Integer memberId) {	    this.memberId=memberId;	}	public java.lang.Integer getMemberType() {	    return this.memberType;	}	public void setMemberType(java.lang.Integer memberType) {	    this.memberType=memberType;	}	public java.lang.Integer getParkId() {	    return this.parkId;	}	public void setParkId(java.lang.Integer parkId) {	    this.parkId=parkId;	}	public java.lang.String getParkName() {	    return this.parkName;	}	public void setParkName(java.lang.String parkName) {	    this.parkName=parkName;	}	public java.util.Date getCountTime() {	    return this.countTime;	}	public void setCountTime(java.util.Date countTime) {	    this.countTime=countTime;	}	public java.lang.Integer getCountType() {	    return this.countType;	}	public void setCountType(java.lang.Integer countType) {	    this.countType=countType;	}	public java.util.Date getCreateTime() {	    return this.createTime;	}	public void setCreateTime(java.util.Date createTime) {	    this.createTime=createTime;	}
	public java.lang.Integer getCountHour() {
		return countHour;
	}
	public void setCountHour(java.lang.Integer countHour) {
		this.countHour = countHour;
	}
}

