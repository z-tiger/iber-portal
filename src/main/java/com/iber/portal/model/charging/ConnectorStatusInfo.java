package com.iber.portal.model.charging;

import java.util.Date;

public class ConnectorStatusInfo {
	
		private Integer id;//   	private Date createTime;//   	private Integer createId;//   	private Date updateTime;//   	private Integer updateId;//   	private Integer connectorId;//   接口id	private Integer status;//   接口状态	private Integer currentA;//   A相电流	private Integer currentB;//   B相电流	private Integer currentC;//   C相电流	private Integer voltageA;//   A相电压	private Integer voltageB;//   B相电压	private Integer voltageC;//   C相电压	private Integer parkStatus;//   车位状态	private Integer lockStatus;//   地锁状态	private String soc;//   剩余电量
	private String connectorCode;//接口编码
	private String createName;//创建人
	private String updateName;//更新人
	
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
	
	
	public Integer getConnectorId() {
		return connectorId;
	}
	public void setConnectorId(Integer connectorId) {
		this.connectorId = connectorId;
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
	public String getConnectorCode() {
		return connectorCode;
	}
	public void setConnectorCode(String connectorCode) {
		this.connectorCode = connectorCode;
	}
}

