package com.iber.portal.vo.car;

import com.iber.portal.model.car.CarRunLog;

public class CarWarnVo extends CarRunLog{
	
	private String name ;

	public CarWarnVo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CarWarnVo(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	

}
