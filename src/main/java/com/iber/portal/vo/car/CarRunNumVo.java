package com.iber.portal.vo.car;

import com.iber.portal.model.car.CarRunLog;

public class CarRunNumVo extends CarRunLog{
	
	
	private Integer total;
	private String  type;
	private String  brance;
	private Integer emptyTotal;//空闲车辆总数
	private Integer runTotal;//运营车辆总数
	private Integer maintenanceTotal;//维保车辆总数
    private Integer dayRentTotal ; //日租车辆统计（运营车辆数据）
    private Integer timeShareTotal;//时租车辆统计（运营车辆数据）
    private Integer enterpriseTotal;//个人类型车辆统计（运营车辆数据）
    private Integer personTotal;//企业类型车辆统计（运营车辆数据）
    private String code;//省份code
	private Integer repairTotal;//维修车辆总计
	private Integer maintainTotal;//维护 车辆总计
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getBrance() {
		return brance;
	}
	public void setBrance(String brance) {
		this.brance = brance;
	}
	public Integer getEmptyTotal() {
		return emptyTotal;
	}
	public void setEmptyTotal(Integer emptyTotal) {
		this.emptyTotal = emptyTotal;
	}
	public Integer getRunTotal() {
		return runTotal;
	}
	public void setRunTotal(Integer runTotal) {
		this.runTotal = runTotal;
	}
	public Integer getMaintenanceTotal() {
		return maintenanceTotal;
	}
	public void setMaintenanceTotal(Integer maintenanceTotal) {
		this.maintenanceTotal = maintenanceTotal;
	}
	public Integer getDayRentTotal() {
		return dayRentTotal;
	}
	public void setDayRentTotal(Integer dayRentTotal) {
		this.dayRentTotal = dayRentTotal;
	}
	public Integer getTimeShareTotal() {
		return timeShareTotal;
	}
	public void setTimeShareTotal(Integer timeShareTotal) {
		this.timeShareTotal = timeShareTotal;
	}
	public Integer getEnterpriseTotal() {
		return enterpriseTotal;
	}
	public void setEnterpriseTotal(Integer enterpriseTotal) {
		this.enterpriseTotal = enterpriseTotal;
	}
	public Integer getPersonTotal() {
		return personTotal;
	}
	public void setPersonTotal(Integer personTotal) {
		this.personTotal = personTotal;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Integer getRepairTotal() {
		return repairTotal;
	}
	public void setRepairTotal(Integer repairTotal) {
		this.repairTotal = repairTotal;
	}
	public Integer getMaintainTotal() {
		return maintainTotal;
	}
	public void setMaintainTotal(Integer maintainTotal) {
		this.maintainTotal = maintainTotal;
	}
}
