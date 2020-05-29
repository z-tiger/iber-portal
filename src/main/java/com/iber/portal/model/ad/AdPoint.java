package com.iber.portal.model.ad;

import java.util.Date;

public class AdPoint {
    private Integer id;

    private String adPointName;

    private String adPointAddress;

    private String gps;

    private Date createTime;

    private String cityCode;
    
    private String cityName;

    private Integer limits;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAdPointName() {
        return adPointName;
    }

    public void setAdPointName(String adPointName) {
        this.adPointName = adPointName == null ? null : adPointName.trim();
    }

    public String getAdPointAddress() {
        return adPointAddress;
    }

    public void setAdPointAddress(String adPointAddress) {
        this.adPointAddress = adPointAddress == null ? null : adPointAddress.trim();
    }

    public String getGps() {
        return gps;
    }

    public void setGps(String gps) {
        this.gps = gps == null ? null : gps.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode == null ? null : cityCode.trim();
    }

    public Integer getLimits() {
        return limits;
    }

    public void setLimits(Integer limits) {
        this.limits = limits;
    }

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
}