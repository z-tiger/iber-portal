package com.iber.portal.vo.car;

public class CarStatusVo {
	
	private String status ;
	
	private Integer total ;

	
	public CarStatusVo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CarStatusVo(String status, Integer total) {
		super();
		this.status = status;
		this.total = total;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	
}
