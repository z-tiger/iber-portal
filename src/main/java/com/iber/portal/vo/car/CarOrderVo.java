package com.iber.portal.vo.car;

public class CarOrderVo {
	
	private String orderNo ;
	
	public CarOrderVo(String orderNo) {
		super();
		this.orderNo = orderNo;
	}

	public CarOrderVo() {
		super();
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
}
