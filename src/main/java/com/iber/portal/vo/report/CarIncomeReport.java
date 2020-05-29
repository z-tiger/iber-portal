package com.iber.portal.vo.report;

public class CarIncomeReport {

	private String cityName ;
	private String lpn ;
	private String brandName ;
	private String type ;
	private String color ;
	private String timeShareMoney ;
	private String totalMoney ;
	private String dayRentMoney;
	
	private Double timeShareMoneySum;//时租总合计
	private Double dayRentMoneySum;//日租总合计
	
	
	@Override
	public String toString() {
		return "CarIncomeReport [cityName=" + cityName + ", lpn=" + lpn
				+ ", brandName=" + brandName + ", type=" + type + ", color="
				+ color + ", timeShareMoney=" + timeShareMoney
				+ ", totalMoney=" + totalMoney + ", dayRentMoney="
				+ dayRentMoney + ", timeShareMoneySum=" + timeShareMoneySum
				+ ", dayRentMoneySum=" + dayRentMoneySum + "]";
	}
	public Double getTimeShareMoneySum() {
		return timeShareMoneySum;
	}
	public void setTimeShareMoneySum(Double timeShareMoneySum) {
		this.timeShareMoneySum = timeShareMoneySum;
	}
	public Double getDayRentMoneySum() {
		return dayRentMoneySum;
	}
	public void setDayRentMoneySum(Double dayRentMoneySum) {
		this.dayRentMoneySum = dayRentMoneySum;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getLpn() {
		return lpn;
	}
	public void setLpn(String lpn) {
		this.lpn = lpn;
	}
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getTimeShareMoney() {
		return timeShareMoney+".00";
	}
	public void setTimeShareMoney(String timeShareMoney) {
		this.timeShareMoney = timeShareMoney;
	}
	public String getTotalMoney() {
		return totalMoney+".00";
	}
	public void setTotalMoney(String totalMoney) {
		this.totalMoney = totalMoney;
	}
	public String getDayRentMoney() {
		return dayRentMoney;
	}
	public void setDayRentMoney(String dayRentMoney) {
		this.dayRentMoney = dayRentMoney;
	}
	
	
}
