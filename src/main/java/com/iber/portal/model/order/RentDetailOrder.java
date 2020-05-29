package com.iber.portal.model.order;

/**
 * 
 * <br>
 * <b>功能：</b>RentDetailOrderEntity<br>
 * <b>作者：</b>www.jeecg.org<br>
 * <b>日期：</b> Feb 2, 2013 <br>
 * <b>版权所有：<b>版权所有(C) 2013，www.jeecg.org<br>
 */
public class RentDetailOrder {
		private java.lang.Integer id;//   	private java.lang.String lpn;//   车牌号	private java.lang.String brandName;//   车品牌名	private java.lang.String cityCode;//   城市编码	private java.lang.String orderId;//   订单号	private java.lang.String orderType;//   订单类型，TS:时租、DR:日租、长租	private java.lang.Integer memberId;//   会员ID	private java.lang.Integer memberType;//   会员类型，0:普通、1:政企	private java.lang.Integer rentTime;//   租赁时长（日租 + 续租 + 超时）	private java.lang.Double mileage;//   里程，单位：公里	private java.lang.Integer orderMoney;//   订单金额	private java.lang.Integer payMoney;//   支付金额	private java.util.Date beginTime;//   订单创建时间	private java.util.Date endTime;//   订单结束时间	private java.lang.Integer orderHour;//   订单小时，即订单创建时的小时值	private java.lang.Integer orderDay;//   订单日期，即订单创建时的日期值，1-31	private java.util.Date countTime;//   统计日期，YYYY-MM-DD	private java.lang.Integer countType;//   统计类型: 0:次数, 1:人数、2:时长、3:里程、4:收入	private java.util.Date createTime;//   
	private String status;// 订单状态(ordered finish cancel)
		public java.lang.Integer getId() {	    return this.id;	}	public void setId(java.lang.Integer id) {	    this.id=id;	}	public java.lang.String getLpn() {	    return this.lpn;	}	public void setLpn(java.lang.String lpn) {	    this.lpn=lpn;	}	public java.lang.String getCityCode() {	    return this.cityCode;	}	public void setCityCode(java.lang.String cityCode) {	    this.cityCode=cityCode;	}	public java.lang.String getOrderId() {	    return this.orderId;	}	public void setOrderId(java.lang.String orderId) {	    this.orderId=orderId;	}	public java.lang.String getOrderType() {	    return this.orderType;	}	public void setOrderType(java.lang.String orderType) {	    this.orderType=orderType;	}	public java.lang.Integer getMemberId() {	    return this.memberId;	}	public void setMemberId(java.lang.Integer memberId) {	    this.memberId=memberId;	}	public java.lang.Integer getMemberType() {	    return this.memberType;	}	public void setMemberType(java.lang.Integer memberType) {	    this.memberType=memberType;	}	public java.lang.Integer getRentTime() {	    return this.rentTime;	}	public void setRentTime(java.lang.Integer rentTime) {	    this.rentTime=rentTime;	}	public java.lang.Double getMileage() {	    return this.mileage;	}	public void setMileage(java.lang.Double mileage) {	    this.mileage=mileage;	}	public java.lang.Integer getOrderMoney() {	    return this.orderMoney;	}	public void setOrderMoney(java.lang.Integer orderMoney) {	    this.orderMoney=orderMoney;	}	public java.lang.Integer getPayMoney() {	    return this.payMoney;	}	public void setPayMoney(java.lang.Integer payMoney) {	    this.payMoney=payMoney;	}	public java.util.Date getEndTime() {	    return this.endTime;	}	public void setEndTime(java.util.Date endTime) {	    this.endTime=endTime;	}	public java.lang.Integer getOrderHour() {	    return this.orderHour;	}	public void setOrderHour(java.lang.Integer orderHour) {	    this.orderHour=orderHour;	}	public java.util.Date getCountTime() {	    return this.countTime;	}	public void setCountTime(java.util.Date countTime) {	    this.countTime=countTime;	}	public java.lang.Integer getCountType() {	    return this.countType;	}	public void setCountType(java.lang.Integer countType) {	    this.countType=countType;	}	public java.util.Date getCreateTime() {	    return this.createTime;	}	public void setCreateTime(java.util.Date createTime) {	    this.createTime=createTime;	}
	public java.lang.String getBrandName() {
		return brandName;
	}
	public void setBrandName(java.lang.String brandName) {
		this.brandName = brandName;
	}
	public java.util.Date getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(java.util.Date beginTime) {
		this.beginTime = beginTime;
	}
	public java.lang.Integer getOrderDay() {
		return orderDay;
	}
	public void setOrderDay(java.lang.Integer orderDay) {
		this.orderDay = orderDay;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}

