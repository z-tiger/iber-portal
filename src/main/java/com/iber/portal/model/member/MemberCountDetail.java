package com.iber.portal.model.member;

import java.util.Date;

public class MemberCountDetail {
	private Integer id;//   
	private Integer memberId;//   会员id
	private String cityCode;//   城市编码
	private String orderNumber;//   订单号
	private Integer memberLevel;//   会员等级(0-普通会员，1-黄金会员，2-铂金会员，3-钻石会员)
	private Integer memberType;//   会员类型(0-个人会员，1-企业会员)
	private Integer money;//   金额
	private Date countTime;//   统计日期
	private Integer hour;//   时间段
	private Integer countType;//   统计类型（1-注册数，2-正式会员数，3-充值押金数，4-充值余额数，5.会员充值数）
	private Date createTime;//    创建时间
	private Double todayCnt;
	private Double yesterdayCnt;
	private Double thisMonthCnt;
	private Double lastMonthCnt;
	private Double totalCnt;
	private Integer currTime;
	private Integer currDate;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getMemberId() {
		return memberId;
	}
	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}
	public String getCityCode() {
		return cityCode;
	}
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	public String getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	public Integer getMemberLevel() {
		return memberLevel;
	}
	public void setMemberLevel(Integer memberLevel) {
		this.memberLevel = memberLevel;
	}
	public Integer getMemberType() {
		return memberType;
	}
	public void setMemberType(Integer memberType) {
		this.memberType = memberType;
	}
	public Integer getMoney() {
		return money;
	}
	public void setMoney(Integer money) {
		this.money = money;
	}
	public Date getCountTime() {
		return countTime;
	}
	public void setCountTime(Date countTime) {
		this.countTime = countTime;
	}
	public Integer getHour() {
		return hour;
	}
	public void setHour(Integer hour) {
		this.hour = hour;
	}
	public Integer getCountType() {
		return countType;
	}
	public void setCountType(Integer countType) {
		this.countType = countType;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
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
		return "MemberCountDetail [id=" + id + ", memberId=" + memberId
				+ ", cityCode=" + cityCode + ", orderNumber=" + orderNumber
				+ ", memberLevel=" + memberLevel + ", memberType=" + memberType
				+ ", money=" + money + ", countTime=" + countTime + ", hour="
				+ hour + ", countType=" + countType + ", createTime="
				+ createTime + ", todayCnt=" + todayCnt + ", yesterdayCnt="
				+ yesterdayCnt + ", thisMonthCnt=" + thisMonthCnt
				+ ", lastMonthCnt=" + lastMonthCnt + ", totalCnt=" + totalCnt
				+ ", currTime=" + currTime + ", currDate=" + currDate + "]";
	}
	
}
