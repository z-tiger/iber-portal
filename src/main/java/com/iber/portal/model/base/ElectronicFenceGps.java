package com.iber.portal.model.base;

public class ElectronicFenceGps {
    private Integer id;

    private Integer fenceId;

    private String longitude;

    private String latitude;

    
    public ElectronicFenceGps() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ElectronicFenceGps(Integer id, Integer fenceId, String longitude,
			String latitude) {
		super();
		this.id = id;
		this.fenceId = fenceId;
		this.longitude = longitude;
		this.latitude = latitude;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFenceId() {
        return fenceId;
    }

    public void setFenceId(Integer fenceId) {
        this.fenceId = fenceId;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude == null ? null : longitude.trim();
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude == null ? null : latitude.trim();
    }
}