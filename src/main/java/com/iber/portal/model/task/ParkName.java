package com.iber.portal.model.task;

public class ParkName {
	private String name;
	private Integer id;
	private Boolean status;
	private Boolean isRun;
	private Integer enterpriseId;
	public Integer getEnterpriseId() {
		return enterpriseId;
	}
	public void setEnterpriseId(Integer enterpriseId) {
		this.enterpriseId = enterpriseId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
	public Boolean getIsRun() {
		return isRun;
	}
	public void setIsRun(Boolean isRun) {
		this.isRun = isRun;
	}
	
}
