package com.iber.portal.model.task;

import java.util.List;

import com.iber.portal.model.dispatcher.Employee;

/**
 * 网格下的所有员工
 * @author Administrator
 *
 */
public class EmployeeOnGrid {
	private Integer id;//网格id
	private String name;//网格名称
	private String cityCode;
	private List<Employee> employees;//网格下所有员工
	
	public String getCityCode() {
		return cityCode;
	}
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
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
	public List<Employee> getEmployees() {
		return employees;
	}
	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}
	
}
