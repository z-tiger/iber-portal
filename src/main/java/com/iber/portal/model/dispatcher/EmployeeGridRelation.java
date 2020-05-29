package com.iber.portal.model.dispatcher;


public class EmployeeGridRelation {
		private Integer id;//   	private Integer isManager;//   是否是管理员（1、是  2、不是）	private Integer employeeId;//   员工id	private Integer gridId;//   网格id
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getIsManager() {
		return isManager;
	}
	public void setIsManager(Integer isManager) {
		this.isManager = isManager;
	}
	public Integer getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}
	public Integer getGridId() {
		return gridId;
	}
	public void setGridId(Integer gridId) {
		this.gridId = gridId;
	}	
}

