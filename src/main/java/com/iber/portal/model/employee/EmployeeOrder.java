package com.iber.portal.model.employee;


/**
 * 
 * <br>
 * <b>功能：</b>EmployeeOrderEntity<br>
 * <b>作者：</b>www.jeecg.org<br>
 * <b>日期：</b> Feb 2, 2013 <br>
 * <b>版权所有：<b>版权所有(C) 2013，www.jeecg.org<br>
 */
public class EmployeeOrder {
	
	
	private java.lang.Integer id;//   主键id
	private java.lang.Integer parkId;//   取车网点
	private java.lang.Integer returnParkId;//   还车网点
	private java.util.Date orderTime;//   订单生成时间
	private java.util.Date beginTime;//   开始时间
	private java.util.Date endTime;//   结束时间
	private java.lang.String cityCode;//   
	private java.lang.Integer isReturnCar;//   检查车辆状态是否可还车（1、可还  2、不可还）
	private java.lang.String lpn;//   车牌号
	private java.lang.Integer employeeId;//   员工id
	private java.lang.String orderNo;//   订单号
	private java.lang.Integer taskId;//   任务id
	private java.lang.String status;//订单状态
	private String endLocation;
	private Double longitude;
	private Double latitude;
	private String cityName;
	private String memberName;
	private String memberPhone;
	private String parkName;
	private String returnParkName;

	private Long useTime;//实际使用时长
	private Double planMileage;//预计里程
	private Double actualMileage;//实际里程

/*    private String beginLatitude;//规化起始网点的纬度
    private String beginLongitude;//规化起始网点的经度
    private String endLatitude;//规化结束网点的纬度
    private String endLongitude;//规化结束网点的经度*/




    public Long getUseTime() {
        return useTime;
    }

    public void setUseTime(Long useTime) {
        this.useTime = useTime;
    }

    public Double getPlanMileage() {
        return planMileage;
    }

    public void setPlanMileage(Double planMileage) {
        this.planMileage = planMileage;
    }

    public Double getActualMileage() {
        return actualMileage;
    }

    public void setActualMileage(Double actualMileage) {
        this.actualMileage = actualMileage;
    }

    public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	public String getEndLocation() {
		return endLocation;
	}
	public void setEndLocation(String endLocation) {
		this.endLocation = endLocation;
	}
	public java.lang.Integer getId() {
		return id;
	}
	public void setId(java.lang.Integer id) {
		this.id = id;
	}
	public java.lang.Integer getParkId() {
		return parkId;
	}
	public void setParkId(java.lang.Integer parkId) {
		this.parkId = parkId;
	}
	public java.lang.Integer getReturnParkId() {
		return returnParkId;
	}
	public void setReturnParkId(java.lang.Integer returnParkId) {
		this.returnParkId = returnParkId;
	}
	public java.util.Date getOrderTime() {
		return orderTime;
	}
	public void setOrderTime(java.util.Date orderTime) {
		this.orderTime = orderTime;
	}
	public java.util.Date getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(java.util.Date beginTime) {
		this.beginTime = beginTime;
	}
	public java.util.Date getEndTime() {
		return endTime;
	}
	public void setEndTime(java.util.Date endTime) {
		this.endTime = endTime;
	}
	public java.lang.String getCityCode() {
		return cityCode;
	}
	public void setCityCode(java.lang.String cityCode) {
		this.cityCode = cityCode;
	}
	public java.lang.Integer getIsReturnCar() {
		return isReturnCar;
	}
	public void setIsReturnCar(java.lang.Integer isReturnCar) {
		this.isReturnCar = isReturnCar;
	}
	public java.lang.String getLpn() {
		return lpn;
	}
	public void setLpn(java.lang.String lpn) {
		this.lpn = lpn;
	}
	public java.lang.Integer getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(java.lang.Integer employeeId) {
		this.employeeId = employeeId;
	}
	public java.lang.String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(java.lang.String orderNo) {
		this.orderNo = orderNo;
	}
	public java.lang.Integer getTaskId() {
		return taskId;
	}
	public void setTaskId(java.lang.Integer taskId) {
		this.taskId = taskId;
	}
	public java.lang.String getStatus() {
		return status;
	}
	public void setStatus(java.lang.String status) {
		this.status = status;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public String getMemberPhone() {
		return memberPhone;
	}
	public void setMemberPhone(String memberPhone) {
		this.memberPhone = memberPhone;
	}
	public String getParkName() {
		return parkName;
	}
	public void setParkName(String parkName) {
		this.parkName = parkName;
	}
	public String getReturnParkName() {
		return returnParkName;
	}
	public void setReturnParkName(String returnParkName) {
		this.returnParkName = returnParkName;
	}

    @Override
    public String toString() {
        return "EmployeeOrder{" +
                "id=" + id +
                ", parkId=" + parkId +
                ", returnParkId=" + returnParkId +
                ", orderTime=" + orderTime +
                ", beginTime=" + beginTime +
                ", endTime=" + endTime +
                ", cityCode='" + cityCode + '\'' +
                ", isReturnCar=" + isReturnCar +
                ", lpn='" + lpn + '\'' +
                ", employeeId=" + employeeId +
                ", orderNo='" + orderNo + '\'' +
                ", taskId=" + taskId +
                ", status='" + status + '\'' +
                ", endLocation='" + endLocation + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", cityName='" + cityName + '\'' +
                ", memberName='" + memberName + '\'' +
                ", memberPhone='" + memberPhone + '\'' +
                ", parkName='" + parkName + '\'' +
                ", returnParkName='" + returnParkName + '\'' +
                ", useTime=" + useTime +
                ", planMileage=" + planMileage +
                ", actualMileage=" + actualMileage +
                '}';
    }
}

