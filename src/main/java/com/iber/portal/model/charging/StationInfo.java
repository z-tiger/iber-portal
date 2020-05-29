package com.iber.portal.model.charging;


import java.util.Date;
/**
 * 
 * <br>
 * <b>功能：充电站信息<br>
 * <b>作者：xyq<br>
 * <b>日期：2016-10-13 <br>
 */
public class StationInfo {
	
		private Integer id;//   主键id	private String name;//   充电站名称	private Date createTime;//   创建时间	private Integer createId;//   创建人	private Date updateTime;//   更新时间	private Integer updateId;//   更新人	private String operatorId;//   运营商id	private String equipmentOwnerId;//   设备所属方id 	private String countryCode;//   充电站国家代码	private String cityCode;//   充电站省市辖区编码	private String address;//   详细地址	private String stationPhone;//   站点电话	private String servicePhone;//   服务电话	private Integer type;//    站点类型	private Integer status;//   站点状态	private Integer parkNums;//   车位数量	private String longitude;//   经度	private String latitude;//   纬度	private String guide;//   站点引导	private Integer construction;//   建设场所	private String photo;//   站点照片	private String matchCars;//   使用车型描述	private String parkInfo;//   车位楼层及数量描述	private String businessHours;//   营业时间	private String electricityFee;//   充电电费率	private String serviceFee;//   服务费率	private String parkFee;//   停车费	private String payment;//   支付方式	private String isOrder;//   是否支持预约（0 、支持  1、不支持）	private String remark;//   备注	private String equipmentInfos;//   充电设备信息列表
    private String createName;//创建人
	private String updateName;//更新人
	private String operatorName;// 运营商
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Integer getCreateId() {
		return createId;
	}
	public void setCreateId(Integer createId) {
		this.createId = createId;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Integer getUpdateId() {
		return updateId;
	}
	public void setUpdateId(Integer updateId) {
		this.updateId = updateId;
	}
	public String getOperatorId() {
		return operatorId;
	}
	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}
	
	public String getEquipmentOwnerId() {
		return equipmentOwnerId;
	}
	public void setEquipmentOwnerId(String equipmentOwnerId) {
		this.equipmentOwnerId = equipmentOwnerId;
	}
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	
	public String getCityCode() {
		return cityCode;
	}
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getStationPhone() {
		return stationPhone;
	}
	public void setStationPhone(String stationPhone) {
		this.stationPhone = stationPhone;
	}
	public String getServicePhone() {
		return servicePhone;
	}
	public void setServicePhone(String servicePhone) {
		this.servicePhone = servicePhone;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getParkNums() {
		return parkNums;
	}
	public void setParkNums(Integer parkNums) {
		this.parkNums = parkNums;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getGuide() {
		return guide;
	}
	public void setGuide(String guide) {
		this.guide = guide;
	}
	public Integer getConstruction() {
		return construction;
	}
	public void setConstruction(Integer construction) {
		this.construction = construction;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public String getMatchCars() {
		return matchCars;
	}
	public void setMatchCars(String matchCars) {
		this.matchCars = matchCars;
	}
	public String getParkInfo() {
		return parkInfo;
	}
	public void setParkInfo(String parkInfo) {
		this.parkInfo = parkInfo;
	}
	public String getBusinessHours() {
		return businessHours;
	}
	public void setBusinessHours(String businessHours) {
		this.businessHours = businessHours;
	}
	public String getElectricityFee() {
		return electricityFee;
	}
	public void setElectricityFee(String electricityFee) {
		this.electricityFee = electricityFee;
	}
	public String getServiceFee() {
		return serviceFee;
	}
	public void setServiceFee(String serviceFee) {
		this.serviceFee = serviceFee;
	}
	public String getParkFee() {
		return parkFee;
	}
	public void setParkFee(String parkFee) {
		this.parkFee = parkFee;
	}
	public String getPayment() {
		return payment;
	}
	public void setPayment(String payment) {
		this.payment = payment;
	}
	public String getIsOrder() {
		return isOrder;
	}
	public void setIsOrder(String isOrder) {
		this.isOrder = isOrder;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getEquipmentInfos() {
		return equipmentInfos;
	}
	public void setEquipmentInfos(String equipmentInfos) {
		this.equipmentInfos = equipmentInfos;
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
	public String getOperatorName() {
		return operatorName;
	}
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	
	
	
		
}

