package com.iber.portal.model.ad;

import java.util.Date;

public class AdBase {
    private Integer id;

    private String title;

    private String simpleContext;
    
    private String cityCode;
    
    private String cityName;
    
    private String adpName;
    
    private String pointName;

    private String isShow;

    private Integer integral;

    private Integer adPid;

    private Date createTime;

    private Integer pointId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getSimpleContext() {
        return simpleContext;
    }

    public void setSimpleContext(String simpleContext) {
        this.simpleContext = simpleContext == null ? null : simpleContext.trim();
    }

    public String getIsShow() {
        return isShow;
    }

    public void setIsShow(String isShow) {
        this.isShow = isShow == null ? null : isShow.trim();
    }

    public Integer getIntegral() {
        return integral;
    }

    public void setIntegral(Integer integral) {
        this.integral = integral;
    }

    public Integer getAdPid() {
        return adPid;
    }

    public void setAdPid(Integer adPid) {
        this.adPid = adPid;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getPointId() {
        return pointId;
    }

    public void setPointId(Integer pointId) {
        this.pointId = pointId;
    }

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getAdpName() {
		return adpName;
	}

	public void setAdpName(String adpName) {
		this.adpName = adpName;
	}

	public String getPointName() {
		return pointName;
	}

	public void setPointName(String pointName) {
		this.pointName = pointName;
	}
}