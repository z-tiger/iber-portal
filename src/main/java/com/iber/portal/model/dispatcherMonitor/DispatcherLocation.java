package com.iber.portal.model.dispatcherMonitor;

public class DispatcherLocation extends LoginLocation {
	private Double dispatcherLongitude;//调度员经度
	private Double dispatcherLatitude;//调度员纬度
	private String photoUrl;
	private String name;
	
	public String getPhotoUrl() {
		return photoUrl;
	}
	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Double getDispatcherLongitude() {
		return dispatcherLongitude;
	}
	public void setDispatcherLongitude(Double dispatcherLongitude) {
		this.dispatcherLongitude = dispatcherLongitude;
	}
	public Double getDispatcherLatitude() {
		return dispatcherLatitude;
	}
	public void setDispatcherLatitude(Double dispatcherLatitude) {
		this.dispatcherLatitude = dispatcherLatitude;
	}
	
}
