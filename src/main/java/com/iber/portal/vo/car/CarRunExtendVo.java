package com.iber.portal.vo.car;

import com.iber.portal.model.car.CarRun;

public class CarRunExtendVo extends CarRun {
	private String parkLatitude;

    private String parkLongitude;
    
    private String parkName;

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

	public String getParkName() {
		return parkName;
	}

	public void setParkName(String parkName) {
		this.parkName = parkName;
	}
}
