package com.iber.portal.model.dispatcher;

import java.util.Date;

public class Grid {
	private Integer id;
	private String name;//网格名称
	private String scope;//范围
	private String cityCode;
	private String createUser;
	private String updateUser;
	private Date createTime;
	private Date updateTime;
	private Integer parkNumber;//网点数
	private Integer dispatcherNumber;//调度员数
	private Integer createId;
	private Integer updateId;
	private String cityName;

	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public Integer getCreateId() {
		return createId;
	}
	public void setCreateId(Integer createId) {
		this.createId = createId;
	}
	public Integer getUpdateId() {
		return updateId;
	}
	public void setUpdateId(Integer updateId) {
		this.updateId = updateId;
	}
	public Integer getParkNumber() {
		return parkNumber;
	}
	public void setParkNumber(Integer parkNumber) {
		this.parkNumber = parkNumber;
	}
	public Integer getDispatcherNumber() {
		return dispatcherNumber;
	}
	public void setDispatcherNumber(Integer dispatcherNumber) {
		this.dispatcherNumber = dispatcherNumber;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public String getUpdateUser() {
		return updateUser;
	}
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
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
		this.name = name;
	}
	public String getScope() {
		return scope;
	}
	public void setScope(String scope) {
		this.scope = scope;
	}
	public String getCityCode() {
		return cityCode;
	}
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	
}
