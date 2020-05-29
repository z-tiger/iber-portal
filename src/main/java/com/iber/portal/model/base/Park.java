package com.iber.portal.model.base;

import java.util.Date;

public class Park {
    private Integer id;

    private String name;

    private String address;

    private Integer parkNums;

    private String liablePerson;

    private String cityCode;
    
    private String cityName;

    private String latitude;

    private String longitude;
    
    private String parkType;
    
    private String fenceName ;
    
    private String areaName ;
    
    private Integer fenceId;
    
    private String areaCode ;
    
    private Integer category ;
    
    private String remark ;
    
    private String categoryName ;
    
    private String operatorId;//运营商组织机构代码
    
    private Integer enterpriseId;//企业id
    
    private String enterpriseName;//企业名称
    
    private Integer  parkFee;//停车费
    
    private String parkFeeDesc;//停车费描述
    
    //网点合作类型(0、自有网点  1、合作网点)
    private Integer  cooperationType;
    
    //所属物业公司(自有网点类型才有)
    private String propertyManagementCompany;
    
    private Integer isTemporary;
    
    private String runStartTime;//每天运营开始时间
    
    private String runEndTime;//每天运营结束时间

	//true 车位满的时候不能停车
	private Boolean fullNoParking;
	
	//网点状态
    private Integer status;
    
    private Integer isRun;
    
    private Integer isCoexist;
    
    private String parkPanoramaLink ; // 全景链接字段

	public String getParkPanoramaLink() {
		return parkPanoramaLink;
	}

	public void setParkPanoramaLink(String parkPanoramaLink) {
		this.parkPanoramaLink = parkPanoramaLink;
	}

	public Integer getIsCoexist() {
		return isCoexist;
	}

	public void setIsCoexist(Integer isCoexist) {
		this.isCoexist = isCoexist;
	}

	public Integer getIsRun() {
		return isRun;
	}

	public void setIsRun(Integer isRun) {
		this.isRun = isRun;
	}

	public String getRunStartTime() {
		return runStartTime;
	}

	public void setRunStartTime(String runStartTime) {
		this.runStartTime = runStartTime;
	}

	public String getRunEndTime() {
		return runEndTime;
	}

	public void setRunEndTime(String runEndTime) {
		this.runEndTime = runEndTime;
	}

	public Integer getIsTemporary() {
		return isTemporary;
	}

	public void setIsTemporary(Integer isTemporary) {
		this.isTemporary = isTemporary;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public Integer getParkNums() {
        return parkNums;
    }

    public void setParkNums(Integer parkNums) {
        this.parkNums = parkNums;
    }

    public String getLiablePerson() {
        return liablePerson;
    }

    public void setLiablePerson(String liablePerson) {
        this.liablePerson = liablePerson == null ? null : liablePerson.trim();
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
    
    public void setParkType(String parkType) {
		this.parkType = parkType;
	}
    
    public String getParkType() {
		return parkType;
	}

	public String getFenceName() {
		return fenceName;
	}

	public void setFenceName(String fenceName) {
		this.fenceName = fenceName;
	}
    
    public void setFenceId(Integer fenceId) {
		this.fenceId = fenceId;
	}
    
    public Integer getFenceId() {
		return fenceId;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public Integer getCategory() {
		return category;
	}

	public void setCategory(Integer category) {
		this.category = category;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

	public Integer getEnterpriseId() {
		return enterpriseId;
	}

	public void setEnterpriseId(Integer enterpriseId) {
		this.enterpriseId = enterpriseId;
	}

	public String getEnterpriseName() {
		return enterpriseName;
	}

	public void setEnterpriseName(String enterpriseName) {
		this.enterpriseName = enterpriseName;
	}
	
	public Integer getParkFee() {
		return parkFee;
	}

	public void setParkFee(Integer parkFee) {
		this.parkFee = parkFee;
	}

	public String getParkFeeDesc() {
		return parkFeeDesc;
	}

	public void setParkFeeDesc(String parkFeeDesc) {
		this.parkFeeDesc = parkFeeDesc;
	}

	public Integer getCooperationType() {
		return cooperationType;
	}

	public void setCooperationType(Integer cooperationType) {
		this.cooperationType = cooperationType;
	}

	public String getPropertyManagementCompany() {
		return propertyManagementCompany;
	}

	public void setPropertyManagementCompany(String propertyManagementCompany) {
		this.propertyManagementCompany = propertyManagementCompany;
	}

	public Boolean getFullNoParking() {
		return fullNoParking;
	}

	public void setFullNoParking(Boolean fullNoParking) {
		this.fullNoParking = fullNoParking;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}