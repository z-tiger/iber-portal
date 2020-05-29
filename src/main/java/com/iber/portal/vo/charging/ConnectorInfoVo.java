package com.iber.portal.vo.charging;

import java.util.Date;



public class ConnectorInfoVo {
	private Integer id;//   充电设备接口主键
	private Date createTime;//   
	private Integer createId;//   
	private Date updateTime;//   
	private Integer updateId;//   
	private String connectorCode;//接口编码
	private int connectorNo;//接口编码
	private String connectorName;//   接口名称
	private Integer connectorType;//   接口类型
	private Integer voltageUpperLimits;//   额定电压上限
	private Integer voltageLowerLimits;//   额定电压下限
	private Integer current;//   额定电流
	private String power;//   额定功率
	private String parkNo;//   车位号
	private String createName;
	private String updateName;
	private Integer status;//   接口状态
	private Integer currentA;//   A相电流
	private Integer currentB;//   B相电流
	private Integer currentC;//   C相电流
	private Integer voltageA;//   A相电压
	private Integer voltageB;//   B相电压
	private Integer voltageC;//   C相电压
	private Integer parkStatus;//   车位状态
	private Integer lockStatus;//   地锁状态
	private String soc;//   剩余电量
	private Integer equipmentId;//设备id
	
	
	
	public int getConnectorNo() {
		return connectorNo;
	}
	public void setConnectorNo(int connectorNo) {
		this.connectorNo = connectorNo;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getCurrentA() {
		return currentA;
	}
	public void setCurrentA(Integer currentA) {
		this.currentA = currentA;
	}
	public Integer getCurrentB() {
		return currentB;
	}
	public void setCurrentB(Integer currentB) {
		this.currentB = currentB;
	}
	public Integer getCurrentC() {
		return currentC;
	}
	public void setCurrentC(Integer currentC) {
		this.currentC = currentC;
	}
	public Integer getVoltageA() {
		return voltageA;
	}
	public void setVoltageA(Integer voltageA) {
		this.voltageA = voltageA;
	}
	public Integer getVoltageB() {
		return voltageB;
	}
	public void setVoltageB(Integer voltageB) {
		this.voltageB = voltageB;
	}
	public Integer getVoltageC() {
		return voltageC;
	}
	public void setVoltageC(Integer voltageC) {
		this.voltageC = voltageC;
	}
	public Integer getParkStatus() {
		return parkStatus;
	}
	public void setParkStatus(Integer parkStatus) {
		this.parkStatus = parkStatus;
	}
	public Integer getLockStatus() {
		return lockStatus;
	}
	public void setLockStatus(Integer lockStatus) {
		this.lockStatus = lockStatus;
	}
	public String getSoc() {
		return soc;
	}
	public void setSoc(String soc) {
		this.soc = soc;
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
	
	public String getConnectorCode() {
		return connectorCode;
	}
	public void setConnectorCode(String connectorCode) {
		this.connectorCode = connectorCode;
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
	public Integer getEquipmentId() {
		return equipmentId;
	}
	public void setEquipmentId(Integer equipmentId) {
		this.equipmentId = equipmentId;
	}
	
	
}
