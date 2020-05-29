package com.iber.portal.model.charging;

import java.util.Date;


public class ChargingOrderInfo {
	
	
	private Integer id;//   
	private Integer memberId;//   会员id
	private Integer equipmentId;//    设备id
	private Integer connectorId;//   接口id
	private Integer stationId;//   网点id
	private Integer chargingAmount;//   充电量
	private Integer chargingTime;//   充电时长
	private Date startTime;//   开始时间
	private Date endTime;//   结束时间
	private Integer orderMoney;//   订单金额
	private String couponCode;//   优惠券编码
	private Integer couponValue;//   优惠券面值
	private Integer payMoney;//   支付金额
	private String payType;//   支付类型
	private String chargingStatus;//   充电状态
	private String chargingBeforeElectric;//   充电前电量
	private String chargingBackElectric;//   充电后电量
	private Integer createId;//   创建人
	private Integer updateId;//   修改人
	private Date createTime;//   创建时间
	private Date updateTime;//   修改时间
	private String createName;
	private String updateName;
	private String memberName;
	private String equipmentCode;
	private String stationName;
	private String chargeSeq;//订单流水号
	private String connectorCode;//接口编码
	private String cityCode;//城市编码
	private String memberStatus;
	private String phone;
	private String isFreeOrder; // 订单是否免单
	private String freeReason;  // 充电订单免单原因
	private String lpn;
	private String userType;
	private String carport;
	private String equipmentType;
	private Integer connectorType;
	private String eastTradeNo;

	public String getEastTradeNo() {
		return eastTradeNo;
	}

	public void setEastTradeNo(String eastTradeNo) {
		this.eastTradeNo = eastTradeNo;
	}

	public Integer getConnectorType() {
		return connectorType;
	}

	public void setConnectorType(Integer connectorType) {
		this.connectorType = connectorType;
	}

	public String getEquipmentType() {
		return equipmentType;
	}
	public void setEquipmentType(String equipmentType) {
		this.equipmentType = equipmentType;
	}
	public String getCarport() {
		return carport;
	}
	public void setCarport(String carport) {
		this.carport = carport;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getLpn() {
		return lpn;
	}
	public void setLpn(String lpn) {
		this.lpn = lpn;
	}
	public String getFreeReason() {
		return freeReason;
	}
	public void setFreeReason(String freeReason) {
		this.freeReason = freeReason;
	}
	public String getIsFreeOrder() {
		return isFreeOrder;
	}
	public void setIsFreeOrder(String isFreeOrder) {
		this.isFreeOrder = isFreeOrder;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getMemberStatus() {
		return memberStatus;
	}
	public void setMemberStatus(String memberStatus) {
		this.memberStatus = memberStatus;
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
	public Integer getEquipmentId() {
		return equipmentId;
	}
	public void setEquipmentId(Integer equipmentId) {
		this.equipmentId = equipmentId;
	}
	
	
	public Integer getConnectorId() {
		return connectorId;
	}
	public void setConnectorId(Integer connectorId) {
		this.connectorId = connectorId;
	}
	public Integer getStationId() {
		return stationId;
	}
	public void setStationId(Integer stationId) {
		this.stationId = stationId;
	}
	public Integer getChargingAmount() {
		return chargingAmount;
	}
	public void setChargingAmount(Integer chargingAmount) {
		this.chargingAmount = chargingAmount;
	}
	public Integer getChargingTime() {
		return chargingTime;
	}
	public void setChargingTime(Integer chargingTime) {
		this.chargingTime = chargingTime;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public Integer getOrderMoney() {
		return orderMoney;
	}
	public void setOrderMoney(Integer orderMoney) {
		this.orderMoney = orderMoney;
	}
	public String getCouponCode() {
		return couponCode;
	}
	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}
	public Integer getCouponValue() {
		return couponValue;
	}
	public void setCouponValue(Integer couponValue) {
		this.couponValue = couponValue;
	}
	public Integer getPayMoney() {
		return payMoney;
	}
	public void setPayMoney(Integer payMoney) {
		this.payMoney = payMoney;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public String getChargingStatus() {
		return chargingStatus;
	}
	public void setChargingStatus(String chargingStatus) {
		this.chargingStatus = chargingStatus;
	}
	public String getChargingBeforeElectric() {
		return chargingBeforeElectric;
	}
	public void setChargingBeforeElectric(String chargingBeforeElectric) {
		this.chargingBeforeElectric = chargingBeforeElectric;
	}
	
	public String getChargingBackElectric() {
		return chargingBackElectric;
	}
	public void setChargingBackElectric(String chargingBackElectric) {
		this.chargingBackElectric = chargingBackElectric;
	}
	public Integer getCreateId() {
		return createId;
	}
	public void setCreateId(Integer createId) {
		this.createId = createId;
	}
	public Integer getUpdateId() {
		return updateId;
	}
	public void setUpdateId(Integer updateId) {
		this.updateId = updateId;
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
	public String getCreateName() {
		return createName;
	}
	public void setCreateName(String createName) {
		this.createName = createName;
	}
	public String getUpdateName() {
		return updateName;
	}
	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public String getEquipmentCode() {
		return equipmentCode;
	}
	public void setEquipmentCode(String equipmentCode) {
		this.equipmentCode = equipmentCode;
	}
	public String getStationName() {
		return stationName;
	}
	public void setStationName(String stationName) {
		this.stationName = stationName;
	}
	public String getChargeSeq() {
		return chargeSeq;
	}
	public void setChargeSeq(String chargeSeq) {
		this.chargeSeq = chargeSeq;
	}
	public String getConnectorCode() {
		return connectorCode;
	}
	public void setConnectorCode(String connectorCode) {
		this.connectorCode = connectorCode;
	}
	public String getCityCode() {
		return cityCode;
	}
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

}

