package com.iber.portal.vo.car;

public class CarTotalVo {
	private String cityName = "全国" ; 
	
	private Integer operateTotal = 0 ;
	
	private Integer repairTotal = 0 ;
	
	private Integer emptyTotal = 0 ;
	
	private Integer total = 0 ;

	
	public CarTotalVo() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public CarTotalVo(String cityName, Integer operateTotal,
			Integer repairTotal, Integer emptyTotal, Integer total) {
		super();
		this.cityName = cityName;
		this.operateTotal = operateTotal;
		this.repairTotal = repairTotal;
		this.emptyTotal = emptyTotal;
		this.total = total;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public Integer getOperateTotal() {
		return operateTotal;
	}

	public void setOperateTotal(Integer operateTotal) {
		this.operateTotal = operateTotal;
	}

	public Integer getRepairTotal() {
		return repairTotal;
	}

	public void setRepairTotal(Integer repairTotal) {
		this.repairTotal = repairTotal;
	}

	public Integer getEmptyTotal() {
		return emptyTotal;
	}

	public void setEmptyTotal(Integer emptyTotal) {
		this.emptyTotal = emptyTotal;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	
}
