package com.iber.portal.vo.base;

import com.iber.portal.model.base.WZQuery;

public class WZQueryVo extends WZQuery{

	private String custName;
	private String  custPhone;
	private Integer type;
	private Integer wzId;

	public Integer getWzId() {
		return wzId;
	}

	public void setWzId(Integer wzId) {
		this.wzId = wzId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public WZQueryVo(){}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getCustPhone() {
		return custPhone;
	}

	public void setCustPhone(String custPhone) {
		this.custPhone = custPhone;
	}

}
