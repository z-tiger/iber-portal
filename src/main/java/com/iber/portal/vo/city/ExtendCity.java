package com.iber.portal.vo.city;

import com.iber.portal.model.base.City;

public class ExtendCity extends City {
	private String parkLatitude;//网点纬度
	private String parkLongitude;//网点经度
	private String parkName;//网点名称
	private Integer category;//网点类型
	private String cooperationType;//网点合作类型
	
	public String getCooperationType() {
		return cooperationType;
	}
	public void setCooperationType(String cooperationType) {
		this.cooperationType = cooperationType;
	}
	public Integer getCategory() {
		return category;
	}
	public void setCategory(Integer category) {
		this.category = category;
	}
	public String getParkName() {
		return parkName;
	}
	public void setParkName(String parkName) {
		this.parkName = parkName;
	}
	public String getParkLatitude() {
		return parkLatitude;
	}
	public void setParkLatitude(String parkLatitude) {
		this.parkLatitude = parkLatitude;
	}
	public String getParkLongitude() {
		return parkLongitude;
	}
	public void setParkLongitude(String parkLongitude) {
		this.parkLongitude = parkLongitude;
	}
	
	
}
