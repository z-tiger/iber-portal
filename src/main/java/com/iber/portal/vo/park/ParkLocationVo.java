package com.iber.portal.vo.park;

import com.iber.portal.model.dispatcherMonitor.LoginLocation;

public class ParkLocationVo extends LoginLocation {
	private Double parkLongitude;//调度员经度
	private Double parkLatitude;//调度员纬度
	private String name;
	
	public Double getParkLongitude() {
		return parkLongitude;
	}
	public void setParkLongitude(Double parkLongitude) {
		this.parkLongitude = parkLongitude;
	}
	public Double getParkLatitude() {
		return parkLatitude;
	}
	public void setParkLatitude(Double parkLatitude) {
		this.parkLatitude = parkLatitude;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
