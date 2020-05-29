package com.iber.portal.model.coupon;

import java.util.Date;

/**
 * 
 * <br>
 * <b>功能：</b>CouponItemEntity<br>
 * <b>作者：</b>www.jeecg.org<br>
 * <b>日期：</b> Feb 2, 2013 <br>
 * <b>版权所有：<b>版权所有(C) 2013，www.jeecg.org<br>
 */
public class CouponItem {
	
	
	private java.lang.Integer id;//   主键ID
	private java.lang.String itemname;//   项名称
	private java.lang.String itemcode;//   项编码
	private java.lang.Integer number;//   数量
	private java.lang.Integer balance;//   面值
	private java.lang.Integer status;//   1 启用/ 0 关闭
	private java.lang.Integer createid;//   
	private java.util.Date createtime;//   
	private java.lang.Integer updateid;//   
	private java.util.Date updatetime;//   
	private Integer deadline ; //期限 天单位
	private String  createName;//创建人
	private String  updateName;//更新人
	private Integer activityId;//对应的活动id
	private Integer useType;
	private Integer minUseValue;
	private Date startTime;//开始时间
	private Date endTime;//结束时间
	private String cityName;
	private String cityCode;
	private Integer maxDeductionValue;
	
	public Integer getMaxDeductionValue() {
		return maxDeductionValue;
	}
	public void setMaxDeductionValue(Integer maxDeductionValue) {
		this.maxDeductionValue = maxDeductionValue;
	}
	public Integer getUseType() {
		return useType;
	}
	public void setUseType(Integer useType) {
		this.useType = useType;
	}
	public Integer getMinUseValue() {
		return minUseValue;
	}
	public void setMinUseValue(Integer minUseValue) {
		this.minUseValue = minUseValue;
	}
	public Integer getActivityId() {
		return activityId;
	}
	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}
	public String getCreateName() {
		return createName;
	}
	public void setCreateName(String createName) {
		this.createName = createName;
	}
	public String getUpdateName() {
		return updateName;
	}
	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}
	public java.lang.Integer getId() {
	    return this.id;
	}
	public void setId(java.lang.Integer id) {
	    this.id=id;
	}
	public java.lang.String getItemname() {
	    return this.itemname;
	}
	public void setItemname(java.lang.String itemname) {
	    this.itemname=itemname;
	}
	public java.lang.String getItemcode() {
	    return this.itemcode;
	}
	public void setItemcode(java.lang.String itemcode) {
	    this.itemcode=itemcode;
	}
	public java.lang.Integer getNumber() {
	    return this.number;
	}
	public void setNumber(java.lang.Integer number) {
	    this.number=number;
	}
	public java.lang.Integer getBalance() {
	    return this.balance;
	}
	public void setBalance(java.lang.Integer balance) {
	    this.balance=balance;
	}
	public java.lang.Integer getStatus() {
	    return this.status;
	}
	public void setStatus(java.lang.Integer status) {
	    this.status=status;
	}
	public java.lang.Integer getCreateid() {
	    return this.createid;
	}
	public void setCreateid(java.lang.Integer createid) {
	    this.createid=createid;
	}
	public java.util.Date getCreatetime() {
	    return this.createtime;
	}
	public void setCreatetime(java.util.Date createtime) {
	    this.createtime=createtime;
	}
	public java.lang.Integer getUpdateid() {
	    return this.updateid;
	}
	public void setUpdateid(java.lang.Integer updateid) {
	    this.updateid=updateid;
	}
	public java.util.Date getUpdatetime() {
	    return this.updatetime;
	}
	public void setUpdatetime(java.util.Date updatetime) {
	    this.updatetime=updatetime;
	}
	public Integer getDeadline() {
		return deadline;
	}
	public void setDeadline(Integer deadline) {
		this.deadline = deadline;
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

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
}

