package com.iber.portal.model.base;

import java.util.Date;

public class IntegralDetail {
	private Integer id;
	private Integer memberId;
	private Integer integral;
	private String  integralCategory;
	private Integer integralFormId;
	private String  integralDesc;
	private Date    createTime;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getMemberId() {
		return memberId;
	}
	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}
	public Integer getIntegral() {
		return integral;
	}
	public void setIntegral(Integer integral) {
		this.integral = integral;
	}
	public String getIntegralCategory() {
		return integralCategory;
	}
	public void setIntegralCategory(String integralCategory) {
		this.integralCategory = integralCategory;
	}
	public Integer getIntegralFormId() {
		return integralFormId;
	}
	public void setIntegralFormId(Integer integralFormId) {
		this.integralFormId = integralFormId;
	}
	public String getIntegralDesc() {
		return integralDesc;
	}
	public void setIntegralDesc(String integralDesc) {
		this.integralDesc = integralDesc;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	
}
