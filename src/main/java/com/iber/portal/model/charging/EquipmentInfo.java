package com.iber.portal.model.charging;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class EquipmentInfo {

	private Integer id;// 主键id
	private String equipmentCode;// 设备编码
	private Date createTime;//
	private Integer createId;//
	private Date updateTime;//
	private Integer updateId;//
	private String manufacturerId;// 设备生产商组织机构代码
	private String equipmentModel;// 设备型号
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date productionDate;// 设备生产日期
	private Integer equipmentType;// 设备类型
	private String connectorInfos;// 充电设备接口列表
	private String equipmentLng;// 充电设备经度
	private String equipmentLat;// 充电设备纬度
	private Integer stationId;// 网点id
	private String stationName;// 网点名称
	private String operatorId;// 运营商组织机构代码
	private String createName;
	private String updateName;
	private String cityCode;
	private String brandIds;
	private Integer connectorNumber;// 接口个数
	private String affordName;// 设备提供商名称
	private String version;// 设置版本号

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public String getManufacturerId() {
		return manufacturerId;
	}

	public void setManufacturerId(String manufacturerId) {
		this.manufacturerId = manufacturerId;
	}

	public String getEquipmentModel() {
		return equipmentModel;
	}

	public void setEquipmentModel(String equipmentModel) {
		this.equipmentModel = equipmentModel;
	}

	public Date getProductionDate() {
		return productionDate;
	}

	public void setProductionDate(Date productionDate) {
		this.productionDate = productionDate;
	}

	public Integer getEquipmentType() {
		return equipmentType;
	}

	public void setEquipmentType(Integer equipmentType) {
		this.equipmentType = equipmentType;
	}

	public String getConnectorInfos() {
		return connectorInfos;
	}

	public void setConnectorInfos(String connectorInfos) {
		this.connectorInfos = connectorInfos;
	}

	public String getEquipmentLng() {
		return equipmentLng;
	}

	public void setEquipmentLng(String equipmentLng) {
		this.equipmentLng = equipmentLng;
	}

	public String getEquipmentLat() {
		return equipmentLat;
	}

	public void setEquipmentLat(String equipmentLat) {
		this.equipmentLat = equipmentLat;
	}

	public Integer getStationId() {
		return stationId;
	}

	public void setStationId(Integer stationId) {
		this.stationId = stationId;
	}

	public String getStationName() {
		return stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
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

	public String getEquipmentCode() {
		return equipmentCode;
	}

	public void setEquipmentCode(String equipmentCode) {
		this.equipmentCode = equipmentCode;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

	public String getBrandIds() {
		return brandIds;
	}

	public void setBrandIds(String brandIds) {
		this.brandIds = brandIds;
	}

	public Integer getConnectorNumber() {
		return connectorNumber;
	}

	public void setConnectorNumber(Integer connectorNumber) {
		this.connectorNumber = connectorNumber;
	}

	public String getAffordName() {
		return affordName;
	}

	public void setAffordName(String affordName) {
		this.affordName = affordName;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	@Override
	public String toString() {
		return "EquipmentInfo [id=" + id + ", equipmentCode=" + equipmentCode
				+ ", createTime=" + createTime + ", createId=" + createId
				+ ", updateTime=" + updateTime + ", updateId=" + updateId
				+ ", manufacturerId=" + manufacturerId + ", equipmentModel="
				+ equipmentModel + ", productionDate=" + productionDate
				+ ", equipmentType=" + equipmentType + ", connectorInfos="
				+ connectorInfos + ", equipmentLng=" + equipmentLng
				+ ", equipmentLat=" + equipmentLat + ", stationId=" + stationId
				+ ", stationName=" + stationName + ", operatorId=" + operatorId
				+ ", createName=" + createName + ", updateName=" + updateName
				+ ", cityCode=" + cityCode + ", brandIds=" + brandIds
				+ ", connectorNumber=" + connectorNumber + ", affordName="
				+ affordName + ", version=" + version + "]";
	}

}
