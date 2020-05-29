package com.iber.portal.vo.order;

import java.util.List;

public class RentCountDetailInfoVo extends RentStatisticsVo {
	
	//今天/昨天中每个小时对应的数据、本月/上月中每天对应的数据
	//今天或本月
	private List<RentCountDetailVo> currList;
	//昨天或上月
	private List<RentCountDetailVo> lastList;


	public List<RentCountDetailVo> getCurrList() {
		return currList;
	}

	public void setCurrList(List<RentCountDetailVo> currList) {
		this.currList = currList;
	}

	public List<RentCountDetailVo> getLastList() {
		return lastList;
	}

	public void setLastList(List<RentCountDetailVo> lastList) {
		this.lastList = lastList;
	}

}
