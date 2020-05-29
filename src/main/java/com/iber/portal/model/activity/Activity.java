package com.iber.portal.model.activity;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * 活动
 * <br>
 * <b>功能：</b>ActivityEntity<br>
 * <b>作者：</b>www.jeecg.org<br>
 * <b>日期：</b> Feb 2, 2013 <br>
 * <b>版权所有：<b>版权所有(C) 2013，www.jeecg.org<br>
 */
public class Activity {
		private Integer id;//   	private String cityCode;
	private String title;//   标题	private String remark;//   备注	private String url;//   活动的URL	private String imgUrl;//   活动图片URL
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")	private Date startTime;//   活动生效日期时间	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")	private Date endTime;//   活动结束日期时间	private Integer status;//   状态	private Integer createId;//   	private Date createTime;//   	private Integer updateId;//   
	private Date updateTime;// 
	private String code;//code类型
	private String createSysUserName;//活动项添加人
	private String updateSysUserName;//活动项更改人
	private String sdName ; 
	private String cityName ;
	
	
	public String getSdName() {
		return sdName;
	}
	public void setSdName(String sdName) {
		this.sdName = sdName;
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
	public String getCreateSysUserName() {
		return createSysUserName;
	}
	public void setCreateSysUserName(String createSysUserName) {
		this.createSysUserName = createSysUserName;
	}
	public String getUpdateSysUserName() {
		return updateSysUserName;
	}
	public void setUpdateSysUserName(String updateSysUserName) {
		this.updateSysUserName = updateSysUserName;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
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
		this.title = title;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
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
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getCreateId() {
		return createId;
	}
	public void setCreateId(Integer createId) {
		this.createId = createId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Integer getUpdateId() {
		return updateId;
	}
	public void setUpdateId(Integer updateId) {
		this.updateId = updateId;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
}

