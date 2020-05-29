package com.iber.portal.vo.pile;

public class PileCountDataVo {
	private Double  todayCnt;
	private Double  yesterdayCnt;
	private Double  thisMonthCnt;
	private Double  lastMonthCnt;
	private Double  totalCnt;
	private Integer currTime;
	private Integer currDate;
	
	public Double getTodayCnt() {
		return todayCnt;
	}
	public void setTodayCnt(Double todayCnt) {
		this.todayCnt = todayCnt;
	}
	public Double getYesterdayCnt() {
		return yesterdayCnt;
	}
	public void setYesterdayCnt(Double yesterdayCnt) {
		this.yesterdayCnt = yesterdayCnt;
	}
	public Double getThisMonthCnt() {
		return thisMonthCnt;
	}
	public void setThisMonthCnt(Double thisMonthCnt) {
		this.thisMonthCnt = thisMonthCnt;
	}
	public Double getLastMonthCnt() {
		return lastMonthCnt;
	}
	public void setLastMonthCnt(Double lastMonthCnt) {
		this.lastMonthCnt = lastMonthCnt;
	}
	public Double getTotalCnt() {
		return totalCnt;
	}
	public void setTotalCnt(Double totalCnt) {
		this.totalCnt = totalCnt;
	}
	public Integer getCurrTime() {
		return currTime;
	}
	public void setCurrTime(Integer currTime) {
		this.currTime = currTime;
	}
	public Integer getCurrDate() {
		return currDate;
	}
	public void setCurrDate(Integer currDate) {
		this.currDate = currDate;
	}
	@Override
	public String toString() {
		return "PileCountDataVo [todayCnt=" + todayCnt + ", yesterdayCnt="
				+ yesterdayCnt + ", thisMonthCnt=" + thisMonthCnt
				+ ", lastMonthCnt=" + lastMonthCnt + ", totalCnt=" + totalCnt
				+ ", currTime=" + currTime + ", currDate=" + currDate + "]";
	}
}
