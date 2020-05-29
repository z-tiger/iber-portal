package com.iber.portal.vo.car;

public class CarVersionVo {
	
	private String lpn ; //车牌号
	private String cityName ;//城市
	private String carRearviewVersionSoftType ; //当前后视镜版本类型
	private String carRearviewVersionSoftTypeName ;
	private String carVersionCode ;//当前后视镜记录数
	private String carRearviewVersionNo ;//当前后视镜版本号
	private String carBoxVersionCode ;//当前盒子版本号
	private String carBoxSoftTypeName ;
	private String carBoxSoftType ;//当前盒子版本类型
	private Integer rearviewVersionId ;//最大后视镜版本ID
	private String rearviewVersionRecord ;//最大后视镜版本数
	private String rearviewVersionNo ;//最大后视镜版本号
	private String rearviewVersionName ;//最大后视镜版本名称
	private String rearviewBoxIsIncrement ;//最大后视镜版本是否全量升级 0全量 1增量
	private Integer boxVersionId ;//最大盒子版本ID
	private String boxVersionRecord ;//最大盒子版本记录数
	private String boxVersionNo ;//最大盒子版本号
	private String boxVersionName ;//最大盒子版本名称
	private String boxIsIncrement ;//最大盒子版本是否全量升级
	private String lastUploadTime ;//最后数据上传时间
	private String brandName ;//车辆品牌
	private String status ;//车辆状态
	private String tboxVersion ;//车辆tbox版本

	public CarVersionVo() {
		super();
	}

	public CarVersionVo(String lpn, String cityName,
			String carRearviewVersionSoftType, String carVersionCode,
			String carBoxVersionCode, String carBoxSoftType,
			Integer rearviewVersionId, String rearviewVersionRecord,
			String rearviewVersionNo, String rearviewVersionName,
			String rearviewBoxIsIncrement, Integer boxVersionId,
			String boxVersionRecord, String boxVersionNo,
			String boxVersionName, String boxIsIncrement) {
		super();
		this.lpn = lpn;
		this.cityName = cityName;
		this.carRearviewVersionSoftType = carRearviewVersionSoftType;
		this.carVersionCode = carVersionCode;
		this.carBoxVersionCode = carBoxVersionCode;
		this.carBoxSoftType = carBoxSoftType;
		this.rearviewVersionId = rearviewVersionId;
		this.rearviewVersionRecord = rearviewVersionRecord;
		this.rearviewVersionNo = rearviewVersionNo;
		this.rearviewVersionName = rearviewVersionName;
		this.rearviewBoxIsIncrement = rearviewBoxIsIncrement;
		this.boxVersionId = boxVersionId;
		this.boxVersionRecord = boxVersionRecord;
		this.boxVersionNo = boxVersionNo;
		this.boxVersionName = boxVersionName;
		this.boxIsIncrement = boxIsIncrement;
	}

	public String getLpn() {
		return lpn;
	}

	public void setLpn(String lpn) {
		this.lpn = lpn;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCarRearviewVersionSoftType() {
		return carRearviewVersionSoftType;
	}

	public void setCarRearviewVersionSoftType(String carRearviewVersionSoftType) {
		this.carRearviewVersionSoftType = carRearviewVersionSoftType;
	}

	public String getCarVersionCode() {
		return carVersionCode;
	}

	public void setCarVersionCode(String carVersionCode) {
		this.carVersionCode = carVersionCode;
	}

	public String getCarBoxVersionCode() {
		return carBoxVersionCode;
	}

	public void setCarBoxVersionCode(String carBoxVersionCode) {
		this.carBoxVersionCode = carBoxVersionCode;
	}

	public String getCarBoxSoftType() {
		return carBoxSoftType;
	}

	public void setCarBoxSoftType(String carBoxSoftType) {
		this.carBoxSoftType = carBoxSoftType;
	}

	public Integer getRearviewVersionId() {
		return rearviewVersionId;
	}

	public void setRearviewVersionId(Integer rearviewVersionId) {
		this.rearviewVersionId = rearviewVersionId;
	}

	public String getRearviewVersionRecord() {
		return rearviewVersionRecord;
	}

	public void setRearviewVersionRecord(String rearviewVersionRecord) {
		this.rearviewVersionRecord = rearviewVersionRecord;
	}

	public String getRearviewVersionNo() {
		return rearviewVersionNo;
	}

	public void setRearviewVersionNo(String rearviewVersionNo) {
		this.rearviewVersionNo = rearviewVersionNo;
	}

	public String getRearviewVersionName() {
		return rearviewVersionName;
	}

	public void setRearviewVersionName(String rearviewVersionName) {
		this.rearviewVersionName = rearviewVersionName;
	}

	public String getRearviewBoxIsIncrement() {
		return rearviewBoxIsIncrement;
	}

	public void setRearviewBoxIsIncrement(String rearviewBoxIsIncrement) {
		this.rearviewBoxIsIncrement = rearviewBoxIsIncrement;
	}

	public Integer getBoxVersionId() {
		return boxVersionId;
	}

	public void setBoxVersionId(Integer boxVersionId) {
		this.boxVersionId = boxVersionId;
	}

	public String getBoxVersionRecord() {
		return boxVersionRecord;
	}

	public void setBoxVersionRecord(String boxVersionRecord) {
		this.boxVersionRecord = boxVersionRecord;
	}

	public String getBoxVersionNo() {
		return boxVersionNo;
	}

	public void setBoxVersionNo(String boxVersionNo) {
		this.boxVersionNo = boxVersionNo;
	}

	public String getBoxVersionName() {
		return boxVersionName;
	}

	public void setBoxVersionName(String boxVersionName) {
		this.boxVersionName = boxVersionName;
	}

	public String getBoxIsIncrement() {
		return boxIsIncrement;
	}

	public void setBoxIsIncrement(String boxIsIncrement) {
		this.boxIsIncrement = boxIsIncrement;
	}

	public String getCarRearviewVersionSoftTypeName() {
		return carRearviewVersionSoftTypeName;
	}

	public void setCarRearviewVersionSoftTypeName(
			String carRearviewVersionSoftTypeName) {
		this.carRearviewVersionSoftTypeName = carRearviewVersionSoftTypeName;
	}

	public String getCarBoxSoftTypeName() {
		return carBoxSoftTypeName;
	}

	public void setCarBoxSoftTypeName(String carBoxSoftTypeName) {
		this.carBoxSoftTypeName = carBoxSoftTypeName;
	}

	public String getCarRearviewVersionNo() {
		return carRearviewVersionNo;
	}

	public void setCarRearviewVersionNo(String carRearviewVersionNo) {
		this.carRearviewVersionNo = carRearviewVersionNo;
	}

	public String getLastUploadTime() {
		return lastUploadTime;
	}

	public void setLastUploadTime(String lastUploadTime) {
		this.lastUploadTime = lastUploadTime;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTboxVersion() {
		return tboxVersion;
	}

	public void setTboxVersion(String tboxVersion) {
		this.tboxVersion = tboxVersion;
	}
}
