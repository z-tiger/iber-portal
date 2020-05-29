package com.iber.portal.model.base;

import java.io.Serializable;
import java.util.Date;

public class City implements Serializable {
    private Integer id;

    private String name;

    private String code;

    private String latitude;

    private String longitude;

    private Date createTime;

    private String status;
    //Test p_id
    private Integer pId;
    //上班时间
    private Date startTime;
    //下班时间
    private Date endTime;
    //城市级别
    private Integer layer;
    
	@Override
	public String toString() {
		return "City [id=" + id + ", name=" + name + ", code=" + code
				+ ", latitude=" + latitude + ", longitude=" + longitude
				+ ", createTime=" + createTime + ", status=" + status
				+ ", pId=" + pId + ", startTime=" + startTime + ", endTime="
				+ endTime + "]";
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Integer getpId() {
		return pId;
	}

	public void setpId(Integer pId) {
		this.pId = pId;
	}

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
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
        this.status = status == null ? null : status.trim();
    }

	public Integer getLayer() {
		return layer;
	}

	public void setLayer(Integer layer) {
		this.layer = layer;
	}
}