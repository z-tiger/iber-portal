package com.iber.portal.vo.dayRent;

import java.util.Date;

public class DayRentRefundVo {
	    
	    private String  id; 
	    private String  orderId; 
	    private String  cityCode; 
	    private String  cityName; 
	    private String  orderStatus; 
	    private String  memberName; 
	    private String  phone; 
	    private String  refundType; 
	    private Date  carBindTime; 
	    private Date  createTime;
	    private String payMoney ;
	    private String refundMoney ;
	    private String refundStatus ;
	    
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getOrderId() {
			return orderId;
		}
		public void setOrderId(String orderId) {
			this.orderId = orderId;
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
		public String getOrderStatus() {
			return orderStatus;
		}
		public void setOrderStatus(String orderStatus) {
			this.orderStatus = orderStatus;
		}
		
		public String getMemberName() {
			return memberName;
		}
		public void setMemberName(String memberName) {
			this.memberName = memberName;
		}
		public String getPhone() {
			return phone;
		}
		public void setPhone(String phone) {
			this.phone = phone;
		}
		 
		public String getRefundType() {
			return refundType;
		}
		public void setRefundType(String refundType) {
			this.refundType = refundType;
		}
		public Date getCarBindTime() {
			return carBindTime;
		}
		public void setCarBindTime(Date carBindTime) {
			this.carBindTime = carBindTime;
		}
		public Date getCreateTime() {
			return createTime;
		}
		public void setCreateTime(Date createTime) {
			this.createTime = createTime;
		}
		
		public String getPayMoney() {
			return payMoney;
		}
		public void setPayMoney(String payMoney) {
			this.payMoney = payMoney;
		}
		public String getRefundMoney() {
			return refundMoney;
		}
		public void setRefundMoney(String refundMoney) {
			this.refundMoney = refundMoney;
		}
		public String getRefundStatus() {
			return refundStatus;
		}
		public void setRefundStatus(String refundStatus) {
			this.refundStatus = refundStatus;
		}
	
}
