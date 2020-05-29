package com.iber.portal.model.charging;

import java.util.Date;



public class ConnectorInfo {
		private Integer id;//   充电设备接口主键	private Date createTime;//   	private Integer createId;//   	private Date updateTime;//   	private Integer updateId;//   
	private String connectorCode;//接口编码	private String connectorName;//   接口名称	private Integer connectorType;//   接口类型	private Integer voltageUpperLimits;//   额定电压上限	private Integer voltageLowerLimits;//   额定电压下限	private Integer current;//   额定电流	private String power;//   额定功率	private String parkNo;//   车位号	private Integer equipmentId;//   充电设备id
	private String createName;
	private String updateName;
	private String equipmentCode;//充电设备编码
	private String cityCode;
	private String lockCode;//地锁编码
	private String connectorNo;
	private String lockStatus;
	
	public String getConnectorNo() {
		return connectorNo;
	}
	public void setConnectorNo(String connectorNo) {
		this.connectorNo = connectorNo;
	}
	public String getLockStatus() {
		return lockStatus;
	}
	public void setLockStatus(String lockStatus) {
		this.lockStatus = lockStatus;
	}
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
	public String getConnectorName() {
		return connectorName;
	}
	public void setConnectorName(String connectorName) {
		this.connectorName = connectorName;
	}
	public Integer getConnectorType() {
		return connectorType;
	}
	public void setConnectorType(Integer connectorType) {
		this.connectorType = connectorType;
	}
	public Integer getVoltageUpperLimits() {
		return voltageUpperLimits;
	}
	public void setVoltageUpperLimits(Integer voltageUpperLimits) {
		this.voltageUpperLimits = voltageUpperLimits;
	}
	public Integer getVoltageLowerLimits() {
		return voltageLowerLimits;
	}
	public void setVoltageLowerLimits(Integer voltageLowerLimits) {
		this.voltageLowerLimits = voltageLowerLimits;
	}
	public Integer getCurrent() {
		return current;
	}
	public void setCurrent(Integer current) {
		this.current = current;
	}
	public String getPower() {
		return power;
	}
	public void setPower(String power) {
		this.power = power;
	}
	public String getParkNo() {
		return parkNo;
	}
	public void setParkNo(String parkNo) {
		this.parkNo = parkNo;
	}
	
	public Integer getEquipmentId() {
		return equipmentId;
	}
	public void setEquipmentId(Integer equipmentId) {
		this.equipmentId = equipmentId;
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
	public String getLockCode() {
		return lockCode;
	}
	public void setLockCode(String lockCode) {
		this.lockCode = lockCode;
	}
}

