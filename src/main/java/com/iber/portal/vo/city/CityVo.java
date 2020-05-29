package com.iber.portal.vo.city;

import java.io.Serializable;
import java.util.Date;

public class CityVo implements Serializable {
    private Integer id;
    
    //上一级城市ID
    private Integer pid;

    private String name;

    private String code;

    private String latitude;

    private String longitude;

    private Date createTime;

    private String status;
    
    private Integer num ;
    
    //全国车辆总数
    private Integer carTotal;
    
    //自建网点数
    private Integer selfBuiltParkNum ;
    
    //合作网点数
    private Integer cooperationParkNum ;
    
    //城市车辆数
    private Integer cityCarSum;
    
    //各省车辆数
    private Integer provinceCarSum;
    
    //各地区车辆数
    
    private Integer areaCarSum;
    
    
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public Integer getSelfBuiltParkNum() {
		return selfBuiltParkNum;
	}

	public void setSelfBuiltParkNum(Integer selfBuiltParkNum) {
		this.selfBuiltParkNum = selfBuiltParkNum;
	}

	public Integer getCooperationParkNum() {
		return cooperationParkNum;
	}

	public void setCooperationParkNum(Integer cooperationParkNum) {
		this.cooperationParkNum = cooperationParkNum;
	}

	public Integer getCityCarSum() {
		return cityCarSum;
	}

	public void setCityCarSum(Integer cityCarSum) {
		this.cityCarSum = cityCarSum;
	}

	public Integer getProvinceCarSum() {
		return provinceCarSum;
	}

	public void setProvinceCarSum(Integer provinceCarSum) {
		this.provinceCarSum = provinceCarSum;
	}

	public Integer getAreaCarSum() {
		return areaCarSum;
	}

	public void setAreaCarSum(Integer areaCarSum) {
		this.areaCarSum = areaCarSum;
	}

	public Integer getCarTotal() {
		return carTotal;
	}

	public void setCarTotal(Integer carTotal) {
		this.carTotal = carTotal;
	}
}