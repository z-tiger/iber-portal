package com.iber.portal.model.car;

import java.util.Date;

public class CarNavigation {
    private Integer id;

    private String lpn;

    private String orderId;

    private String beginGps;

    private String endGps;

    private String memberName;

    private Date createTime;

    private Integer createId;

    private String createName;
    
    private String address ;
    
    private String status ;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLpn() {
        return lpn;
    }

    public void setLpn(String lpn) {
        this.lpn = lpn == null ? null : lpn.trim();
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    public String getBeginGps() {
		return beginGps;
	}

	public void setBeginGps(String beginGps) {
		this.beginGps = beginGps;
	}

	public String getEndGps() {
		return endGps;
	}

	public void setEndGps(String endGps) {
		this.endGps = endGps;
	}

	public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName == null ? null : memberName.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getCreateId() {
        return createId;
    }

    public void setCreateId(Integer createId) {
        this.createId = createId;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName == null ? null : createName.trim();
    }

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
    
    
}