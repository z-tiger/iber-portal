package com.iber.portal.vo.dayRent;

public class DayRentOrderDetailVo {

	private String appointmenTakeCarTime; //预约取车时间
	private String appointmenOrderMoney;//订单金额
	private String appointmenCarRentMoney;//车辆租赁费
	private String appointmenReturnCarTime;//预约还车时间
	private String appointmenCouponBalance;//优惠券编号（面值）
	private String appointmenInsuranceMoney;//基本保险费
	private String appointmenDayRentTime;//预约租期
	private String appointmenFreeMoney;//优惠金额
	private String appointmenProcedureMoney;//手续费
	private String appointmenTakeCarParkAddress;//预约取车网点
	private String appointmenPayMoney;//支付金额
	private String appointmenRemoteMoney;//异地还车费
	private String appointmenReturnCarParkAddress;//预约还车网点
	private String appointmenPayType;//支付方式
	private String appointmenFreeCompensateMoney;//不计免赔
	
	private String actualTakeCarTime;//实际取车时间
	private String actualTakeCarParkAddress;//实际还车时间
	
	private String delayReturnCarOrderTime;//延期还车下单时间
	private String delayCarRentMoney;//车辆租赁费
	private String delayDayRentTime;//延期天数
	private String delayInsuranceMoney;//基本保险费
	private String delayReturnCarTime;//延期预约还车时间
	private String delayFreeCompensateMoney;//不计免赔
	private String delayPayMoney;//延期支付金额
	private String delayPayType;//支付方式
	private String delayOrderMoney;//订单金额
	
	private String actualReturnCarTime;//时间还车时间
	private String actualReturnCarParkAddress;//时间还车网点
	
	private String timeoutDayTime;//超时时间
	private String timeoutPayMoney;//超时支付金额
	private String timeoutRemoteMoney;//异地还车支付金额
	private String timeoutPayType;//支付方式
	
	private String OrderTotalPayMoney ;
	
	public String getAppointmenTakeCarTime() {
		return appointmenTakeCarTime;
	}
	public void setAppointmenTakeCarTime(String appointmenTakeCarTime) {
		this.appointmenTakeCarTime = appointmenTakeCarTime;
	}
	public String getAppointmenOrderMoney() {
		return appointmenOrderMoney;
	}
	public void setAppointmenOrderMoney(String appointmenOrderMoney) {
		this.appointmenOrderMoney = appointmenOrderMoney;
	}
	public String getAppointmenCarRentMoney() {
		return appointmenCarRentMoney;
	}
	public void setAppointmenCarRentMoney(String appointmenCarRentMoney) {
		this.appointmenCarRentMoney = appointmenCarRentMoney;
	}
	public String getAppointmenReturnCarTime() {
		return appointmenReturnCarTime;
	}
	public void setAppointmenReturnCarTime(String appointmenReturnCarTime) {
		this.appointmenReturnCarTime = appointmenReturnCarTime;
	}
	public String getAppointmenCouponBalance() {
		return appointmenCouponBalance;
	}
	public void setAppointmenCouponBalance(String appointmenCouponBalance) {
		this.appointmenCouponBalance = appointmenCouponBalance;
	}
	public String getAppointmenInsuranceMoney() {
		return appointmenInsuranceMoney;
	}
	public void setAppointmenInsuranceMoney(String appointmenInsuranceMoney) {
		this.appointmenInsuranceMoney = appointmenInsuranceMoney;
	}
	public String getAppointmenDayRentTime() {
		return appointmenDayRentTime;
	}
	public void setAppointmenDayRentTime(String appointmenDayRentTime) {
		this.appointmenDayRentTime = appointmenDayRentTime;
	}
	public String getAppointmenFreeMoney() {
		return appointmenFreeMoney;
	}
	public void setAppointmenFreeMoney(String appointmenFreeMoney) {
		this.appointmenFreeMoney = appointmenFreeMoney;
	}
	public String getAppointmenProcedureMoney() {
		return appointmenProcedureMoney;
	}
	public void setAppointmenProcedureMoney(String appointmenProcedureMoney) {
		this.appointmenProcedureMoney = appointmenProcedureMoney;
	}
	public String getAppointmenTakeCarParkAddress() {
		return appointmenTakeCarParkAddress;
	}
	public void setAppointmenTakeCarParkAddress(String appointmenTakeCarParkAddress) {
		this.appointmenTakeCarParkAddress = appointmenTakeCarParkAddress;
	}
	public String getAppointmenPayMoney() {
		return appointmenPayMoney;
	}
	public void setAppointmenPayMoney(String appointmenPayMoney) {
		this.appointmenPayMoney = appointmenPayMoney;
	}
	public String getAppointmenRemoteMoney() {
		return appointmenRemoteMoney;
	}
	public void setAppointmenRemoteMoney(String appointmenRemoteMoney) {
		this.appointmenRemoteMoney = appointmenRemoteMoney;
	}
	public String getAppointmenReturnCarParkAddress() {
		return appointmenReturnCarParkAddress;
	}
	public void setAppointmenReturnCarParkAddress(
			String appointmenReturnCarParkAddress) {
		this.appointmenReturnCarParkAddress = appointmenReturnCarParkAddress;
	}
	public String getAppointmenPayType() {
		return appointmenPayType;
	}
	public void setAppointmenPayType(String appointmenPayType) {
		this.appointmenPayType = appointmenPayType;
	}
	public String getAppointmenFreeCompensateMoney() {
		return appointmenFreeCompensateMoney;
	}
	public void setAppointmenFreeCompensateMoney(
			String appointmenFreeCompensateMoney) {
		this.appointmenFreeCompensateMoney = appointmenFreeCompensateMoney;
	}
	public String getActualTakeCarTime() {
		return actualTakeCarTime;
	}
	public void setActualTakeCarTime(String actualTakeCarTime) {
		this.actualTakeCarTime = actualTakeCarTime;
	}
	public String getActualTakeCarParkAddress() {
		return actualTakeCarParkAddress;
	}
	public void setActualTakeCarParkAddress(String actualTakeCarParkAddress) {
		this.actualTakeCarParkAddress = actualTakeCarParkAddress;
	}
	public String getDelayReturnCarOrderTime() {
		return delayReturnCarOrderTime;
	}
	public void setDelayReturnCarOrderTime(String delayReturnCarOrderTime) {
		this.delayReturnCarOrderTime = delayReturnCarOrderTime;
	}
	public String getDelayCarRentMoney() {
		return delayCarRentMoney;
	}
	public void setDelayCarRentMoney(String delayCarRentMoney) {
		this.delayCarRentMoney = delayCarRentMoney;
	}
	public String getDelayDayRentTime() {
		return delayDayRentTime;
	}
	public void setDelayDayRentTime(String delayDayRentTime) {
		this.delayDayRentTime = delayDayRentTime;
	}
	public String getDelayInsuranceMoney() {
		return delayInsuranceMoney;
	}
	public void setDelayInsuranceMoney(String delayInsuranceMoney) {
		this.delayInsuranceMoney = delayInsuranceMoney;
	}
	public String getDelayReturnCarTime() {
		return delayReturnCarTime;
	}
	public void setDelayReturnCarTime(String delayReturnCarTime) {
		this.delayReturnCarTime = delayReturnCarTime;
	}
	public String getDelayFreeCompensateMoney() {
		return delayFreeCompensateMoney;
	}
	public void setDelayFreeCompensateMoney(String delayFreeCompensateMoney) {
		this.delayFreeCompensateMoney = delayFreeCompensateMoney;
	}
	public String getDelayPayMoney() {
		return delayPayMoney;
	}
	public void setDelayPayMoney(String delayPayMoney) {
		this.delayPayMoney = delayPayMoney;
	}
	public String getDelayPayType() {
		return delayPayType;
	}
	public void setDelayPayType(String delayPayType) {
		this.delayPayType = delayPayType;
	}
	public String getActualReturnCarTime() {
		return actualReturnCarTime;
	}
	public void setActualReturnCarTime(String actualReturnCarTime) {
		this.actualReturnCarTime = actualReturnCarTime;
	}
	public String getActualReturnCarParkAddress() {
		return actualReturnCarParkAddress;
	}
	public void setActualReturnCarParkAddress(String actualReturnCarParkAddress) {
		this.actualReturnCarParkAddress = actualReturnCarParkAddress;
	}
	public String getTimeoutDayTime() {
		return timeoutDayTime;
	}
	public void setTimeoutDayTime(String timeoutDayTime) {
		this.timeoutDayTime = timeoutDayTime;
	}
	public String getTimeoutPayMoney() {
		return timeoutPayMoney;
	}
	public void setTimeoutPayMoney(String timeoutPayMoney) {
		this.timeoutPayMoney = timeoutPayMoney;
	}
	public String getTimeoutRemoteMoney() {
		return timeoutRemoteMoney;
	}
	public void setTimeoutRemoteMoney(String timeoutRemoteMoney) {
		this.timeoutRemoteMoney = timeoutRemoteMoney;
	}
	public String getTimeoutPayType() {
		return timeoutPayType;
	}
	public void setTimeoutPayType(String timeoutPayType) {
		this.timeoutPayType = timeoutPayType;
	}
	public String getDelayOrderMoney() {
		return delayOrderMoney;
	}
	public void setDelayOrderMoney(String delayOrderMoney) {
		this.delayOrderMoney = delayOrderMoney;
	}
	public String getOrderTotalPayMoney() {
		return OrderTotalPayMoney;
	}
	public void setOrderTotalPayMoney(String orderTotalPayMoney) {
		OrderTotalPayMoney = orderTotalPayMoney;
	}
	 
	
}
