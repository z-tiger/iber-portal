package com.iber.portal.vo.park;

import java.util.List;

import com.iber.portal.vo.order.RentStatisticsVo;

public class ParkDetailInfoVo extends RentStatisticsVo {

	//今天/昨天中每个小时对应的数据、本月/上月中每天对应的数据
	//今天或本月
	private List<ParkDetailStatisticsVo> currList;
	//昨天或上月
	private List<ParkDetailStatisticsVo> lastList;
	
	public List<ParkDetailStatisticsVo> getCurrList() {
		return currList;
	}
	public void setCurrList(List<ParkDetailStatisticsVo> currList) {
		this.currList = currList;
	}
	public List<ParkDetailStatisticsVo> getLastList() {
		return lastList;
	}
	public void setLastList(List<ParkDetailStatisticsVo> lastList) {
		this.lastList = lastList;
	}
	
	
}
