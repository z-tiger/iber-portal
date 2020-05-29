package com.iber.portal.vo.base;

import java.util.Date;

import com.iber.portal.model.base.MemberCard;

public class MemberCardVo extends MemberCard {

	private String cityCode;

	private String cityName;

	private String name;

	private String sex;

	private String phone;

	private String idcard;

	private String driverIdcard;

	private String accoutStatus;
	
	private Integer totalNotUsecoupon;//未使用优惠券总额,单位分
	
	private String zhimaScore;
	
	private Integer acpPreAuth;
	
	private String acpPreAuthDate;
	
	private String dayRentOrderId ;
	
	private String timeShareOrderId ;
	
	private Integer levelCode;
	
	private Date driverIdCardTime;
	
	private Integer depositValue;
	
	private Integer requiredDeposit;
	
	private Integer refundMoney;
	
	public Integer getRefundMoney() {
		return refundMoney;
	}

	public void setRefundMoney(Integer refundMoney) {
		this.refundMoney = refundMoney;
	}

	public Integer getRequiredDeposit() {
		return requiredDeposit;
	}

	public void setRequiredDeposit(Integer requiredDeposit) {
		this.requiredDeposit = requiredDeposit;
	}

	public Integer getDepositValue() {
		return depositValue;
	}

	public void setDepositValue(Integer depositValue) {
		this.depositValue = depositValue;
	}

	public Integer getLevelCode() {
		return levelCode;
	}

	public void setLevelCode(Integer levelCode) {
		this.levelCode = levelCode;
	}

	public Date getDriverIdCardTime() {
		return driverIdCardTime;
	}

	public void setDriverIdCardTime(Date driverIdCardTime) {
		this.driverIdCardTime = driverIdCardTime;
	}

	public MemberCardVo(){}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public String getDriverIdcard() {
		return driverIdcard;
	}

	public void setDriverIdcard(String driverIdcard) {
		this.driverIdcard = driverIdcard;
	}

	public String getAccoutStatus() {
		return accoutStatus;
	}

	public void setAccoutStatus(String accoutStatus) {
		this.accoutStatus = accoutStatus;
	}

	public Integer getTotalNotUsecoupon() {
		return totalNotUsecoupon;
	}

	public void setTotalNotUsecoupon(Integer totalNotUsecoupon) {
		this.totalNotUsecoupon = totalNotUsecoupon;
	}

	public String getZhimaScore() {
		return zhimaScore;
	}

	public void setZhimaScore(String zhimaScore) {
		this.zhimaScore = zhimaScore;
	}

	public Integer getAcpPreAuth() {
		return acpPreAuth;
	}

	public void setAcpPreAuth(Integer acpPreAuth) {
		this.acpPreAuth = acpPreAuth;
	}

	public String getAcpPreAuthDate() {
		return acpPreAuthDate;
	}

	public void setAcpPreAuthDate(String acpPreAuthDate) {
		this.acpPreAuthDate = acpPreAuthDate;
	}

	public String getDayRentOrderId() {
		return dayRentOrderId;
	}

	public void setDayRentOrderId(String dayRentOrderId) {
		this.dayRentOrderId = dayRentOrderId;
	}

	public String getTimeShareOrderId() {
		return timeShareOrderId;
	}

	public void setTimeShareOrderId(String timeShareOrderId) {
		this.timeShareOrderId = timeShareOrderId;
	}
	
}
