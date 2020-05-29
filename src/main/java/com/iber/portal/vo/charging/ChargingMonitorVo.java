package com.iber.portal.vo.charging;


public class ChargingMonitorVo {

	private Integer id;//充电接口主键
	private String cityName;//   城市名
	private String parkName;//   网点名
	private String equipmentCode;//  枪编码
	private String parkNo;//   车位号
	private Integer connectorNo;//接口编号
	private Integer status;//枪状态
	private Integer connectorNumber;
	private Integer lockStatus;//地锁状态
	private Integer equipmentType;
    private Integer cooperationType; 
    private Integer parkId;
    private String faultCode; //枪故障编码
	public String getFaultCode() {
		return faultCode;
	}
	public void setFaultCode(String faultCode) {
		this.faultCode = faultCode;
	}
	public Integer getParkId() {
		return parkId;
	}
	public void setParkId(Integer parkId) {
		this.parkId = parkId;
	}
	public Integer getCooperationType() {
		return cooperationType;
	}
	public void setCooperationType(Integer cooperationType) {
		this.cooperationType = cooperationType;
	}
	public Integer getEquipmentType() {
		return equipmentType;
	}
	public void setEquipmentType(Integer equipmentType) {
		this.equipmentType = equipmentType;
	}
	public Integer getLockStatus() {
		return lockStatus;
	}
	public void setLockStatus(Integer lockStatus) {
		this.lockStatus = lockStatus;
	}
	public Integer getConnectorNumber() {
		return connectorNumber;
	}
	public void setConnectorNumber(Integer connectorNumber) {
		this.connectorNumber = connectorNumber;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getParkName() {
		return parkName;
	}
	public void setParkName(String parkName) {
		this.parkName = parkName;
	}
	public String getEquipmentCode() {
		return equipmentCode;
	}
	public void setEquipmentCode(String equipmentCode) {
		this.equipmentCode = equipmentCode;
	}
	public String getParkNo() {
		return parkNo;
	}
	public void setParkNo(String parkNo) {
		this.parkNo = parkNo;
	}
	public Integer getConnectorNo() {
		return connectorNo;
	}
	public void setConnectorNo(Integer connectorNo) {
		this.connectorNo = connectorNo;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
}
