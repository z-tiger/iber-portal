package com.iber.portal.model.dispatcherMonitor;

public class ParkLocation extends LoginLocation {
	private Integer id;
	private Double parkLatitude;
	private Double parkLongitude;
	private String parkName;
	private Integer category;//网点类型
	private String cooperationType;//网点合作类型
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
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
	public Double getParkLatitude() {
		return parkLatitude;
	}
	public void setParkLatitude(Double parkLatitude) {
		this.parkLatitude = parkLatitude;
	}
	public Double getParkLongitude() {
		return parkLongitude;
	}
	public void setParkLongitude(Double parkLongitude) {
		this.parkLongitude = parkLongitude;
	}
	
	
}
