package com.iber.portal.vo.dayRent;

import java.util.Date;

public class DayRentOrderVo {
	    
	    private String  id; 
	    private String  orderId; 
	    private String  cityCode; 
	    private String  cityName; 
	    private String  orderStatus; 
	    private String  memberName; 
	    private String  phone; 
	    private String  lpn; 
	    private Date  appointmenTakeCarTime; 
	    private Date  createTime;
	    private String payStatus ;
	    private String branceName ;
	    private String appointmenTakeCarPark ;
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
		public String getLpn() {
			return lpn;
		}
		public void setLpn(String lpn) {
			this.lpn = lpn;
		}
		
		public Date getAppointmenTakeCarTime() {
			return appointmenTakeCarTime;
		}
		public void setAppointmenTakeCarTime(Date appointmenTakeCarTime) {
			this.appointmenTakeCarTime = appointmenTakeCarTime;
		}
		public Date getCreateTime() {
			return createTime;
		}
		public void setCreateTime(Date createTime) {
			this.createTime = createTime;
		}
		public String getPayStatus() {
			return payStatus;
		}
		public void setPayStatus(String payStatus) {
			this.payStatus = payStatus;
		}
		public String getBranceName() {
			return branceName;
		}
		public void setBranceName(String branceName) {
			this.branceName = branceName;
		}
		public String getAppointmenTakeCarPark() {
			return appointmenTakeCarPark;
		}
		public void setAppointmenTakeCarPark(String appointmenTakeCarPark) {
			this.appointmenTakeCarPark = appointmenTakeCarPark;
		}
	
}
