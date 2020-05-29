package com.iber.portal.model.overall;

import java.util.Date;

public class OverallCar {
	private Integer id;

	private Integer carNum;

	private Integer carRunNum;

	private Integer maintainNum;

	private Integer repairNum;

	private Integer rentCarNum;

	private Integer rentNum;

	private Integer rentMemberNum;

	private Integer orderNum;

	private Integer orderCarNum;

	private Integer orderMemberNum;

	private Double rentTimelong;

	private Double rentMileage;

	private Integer payMoney;

	private Integer orderMoney;

	private String cityCode;

	private String carType;

	private Date dateTime;

	private Date createTime;

	public String getCarType() {
		return carType;
	}

	public void setCarType(String carType) {
		this.carType = carType;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCarNum() {
		return carNum;
	}

	public void setCarNum(Integer carNum) {
		this.carNum = carNum;
	}

	public Integer getCarRunNum() {
		return carRunNum;
	}

	public void setCarRunNum(Integer carRunNum) {
		this.carRunNum = carRunNum;
	}

	public Integer getMaintainNum() {
		return maintainNum;
	}

	public void setMaintainNum(Integer maintainNum) {
		this.maintainNum = maintainNum;
	}

	public Integer getRepairNum() {
		return repairNum;
	}

	public void setRepairNum(Integer repairNum) {
		this.repairNum = repairNum;
	}

	public Integer getRentCarNum() {
		return rentCarNum;
	}

	public void setRentCarNum(Integer rentCarNum) {
		this.rentCarNum = rentCarNum;
	}

	public Integer getRentNum() {
		return rentNum;
	}

	public void setRentNum(Integer rentNum) {
		this.rentNum = rentNum;
	}

	public Integer getRentMemberNum() {
		return rentMemberNum;
	}

	public void setRentMemberNum(Integer rentMemberNum) {
		this.rentMemberNum = rentMemberNum;
	}

	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	public Integer getOrderCarNum() {
		return orderCarNum;
	}

	public void setOrderCarNum(Integer orderCarNum) {
		this.orderCarNum = orderCarNum;
	}

	public Integer getOrderMemberNum() {
		return orderMemberNum;
	}

	public void setOrderMemberNum(Integer orderMemberNum) {
		this.orderMemberNum = orderMemberNum;
	}

	public Double getRentTimelong() {
		return rentTimelong;
	}

	public void setRentTimelong(Double rentTimelong) {
		this.rentTimelong = rentTimelong;
	}

	public Double getRentMileage() {
		return rentMileage;
	}

	public void setRentMileage(Double rentMileage) {
		this.rentMileage = rentMileage;
	}

	public Integer getPayMoney() {
		return payMoney;
	}

	public void setPayMoney(Integer payMoney) {
		this.payMoney = payMoney;
	}

	public Integer getOrderMoney() {
		return orderMoney;
	}

	public void setOrderMoney(Integer orderMoney) {
		this.orderMoney = orderMoney;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode == null ? null : cityCode.trim();
	}

	public Date getDateTime() {
		return dateTime;
	}

	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "OverallCar [id=" + id + ", carNum=" + carNum + ", carRunNum="
				+ carRunNum + ", maintainNum=" + maintainNum + ", repairNum="
				+ repairNum + ", rentCarNum=" + rentCarNum + ", rentNum="
				+ rentNum + ", rentMemberNum=" + rentMemberNum + ", orderNum="
				+ orderNum + ", orderCarNum=" + orderCarNum
				+ ", orderMemberNum=" + orderMemberNum + ", rentTimelong="
				+ rentTimelong + ", rentMileage=" + rentMileage + ", payMoney="
				+ payMoney + ", orderMoney=" + orderMoney + ", cityCode="
				+ cityCode + ", carType=" + carType + ", dateTime=" + dateTime
				+ ", createTime=" + createTime + "]";
	}

}