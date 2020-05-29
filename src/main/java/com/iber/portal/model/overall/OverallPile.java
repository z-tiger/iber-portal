package com.iber.portal.model.overall;

import java.util.Date;

public class OverallPile {
	private Integer id;

	private Integer pileNum;

	private Integer malfunctionNum;

	private Integer onlineNum;

	private Integer orderMoney;

	private Integer payMoney;

	private Integer orderMemberNum;

	private Integer orderNum;

	private Integer chargingCount;

	private Integer chargingMemberNum;

	private Double chargingAmount;

	private Double chargingTimelong;

	private Date dateTime;

	private String equipmentType;

	private String connectorType;

	private String cityCode;

	private Date createTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPileNum() {
		return pileNum;
	}

	public void setPileNum(Integer pileNum) {
		this.pileNum = pileNum;
	}

	public Integer getMalfunctionNum() {
		return malfunctionNum;
	}

	public void setMalfunctionNum(Integer malfunctionNum) {
		this.malfunctionNum = malfunctionNum;
	}

	public Integer getOnlineNum() {
		return onlineNum;
	}

	public void setOnlineNum(Integer onlineNum) {
		this.onlineNum = onlineNum;
	}

	public Integer getOrderMoney() {
		return orderMoney;
	}

	public void setOrderMoney(Integer orderMoney) {
		this.orderMoney = orderMoney;
	}

	public Integer getPayMoney() {
		return payMoney;
	}

	public void setPayMoney(Integer payMoney) {
		this.payMoney = payMoney;
	}

	public Integer getOrderMemberNum() {
		return orderMemberNum;
	}

	public void setOrderMemberNum(Integer orderMemberNum) {
		this.orderMemberNum = orderMemberNum;
	}

	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	public Integer getChargingCount() {
		return chargingCount;
	}

	public void setChargingCount(Integer chargingCount) {
		this.chargingCount = chargingCount;
	}

	public Integer getChargingMemberNum() {
		return chargingMemberNum;
	}

	public void setChargingMemberNum(Integer chargingMemberNum) {
		this.chargingMemberNum = chargingMemberNum;
	}

	public Double getChargingAmount() {
		return chargingAmount;
	}

	public void setChargingAmount(Double chargingAmount) {
		this.chargingAmount = chargingAmount;
	}

	public Double getChargingTimelong() {
		return chargingTimelong;
	}

	public void setChargingTimelong(Double chargingTimelong) {
		this.chargingTimelong = chargingTimelong;
	}

	public Date getDateTime() {
		return dateTime;
	}

	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}

	public String getEquipmentType() {
		return equipmentType;
	}

	public void setEquipmentType(String equipmentType) {
		this.equipmentType = equipmentType == null ? null : equipmentType
				.trim();
	}

	public String getConnectorType() {
		return connectorType;
	}

	public void setConnectorType(String connectorType) {
		this.connectorType = connectorType == null ? null : connectorType
				.trim();
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode == null ? null : cityCode.trim();
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}