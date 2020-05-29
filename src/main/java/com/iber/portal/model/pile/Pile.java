package com.iber.portal.model.pile;

public class Pile {
    private Integer id;

    private String name;

    private Integer parkId;

    private String type;

    private String status;

    private String cityCode;
    
    private String cityName;
    
    private String parkName;
    
    private String parkAddress;
    
    private Integer parkNums;

    private String latitude;

    private String longitude;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getParkId() {
        return parkId;
    }

    public void setParkId(Integer parkId) {
        this.parkId = parkId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode == null ? null : cityCode.trim();
    }
    
    public String getCityName() {
		return cityName;
	}
    
    public void setCityName(String cityName) {
    	 this.cityName = cityName == null ? null : cityName.trim();
	}
    
    public String getParkName() {
		return parkName;
	}

	public void setParkName(String parkName) {
		this.parkName = parkName;
	}

	public String getParkAddress() {
		return parkAddress;
	}

	public void setParkAddress(String parkAddress) {
		this.parkAddress = parkAddress;
	}

	public Integer getParkNums() {
		return parkNums;
	}

	public void setParkNums(Integer parkNums) {
		this.parkNums = parkNums;
	}

	public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude == null ? null : latitude.trim();
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude == null ? null : longitude.trim();
    }
}