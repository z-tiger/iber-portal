package com.iber.portal.model.base;

public class WZAssociatedCitys {
   private Integer id;
   private String cityCode;//城市编码
   private String cityName;//城市名字
   private String associatedCity;//附近城市
   
   
@Override
public String toString() {
	return "WZAssociatedCitys [id=" + id + ", cityCode=" + cityCode
			+ ", cityName=" + cityName + ", associatedCity=" + associatedCity
			+ "]";
}
public Integer getId() {
	return id;
}
public void setId(Integer id) {
	this.id = id;
}
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
public String getAssociatedCity() {
	return associatedCity;
}
public void setAssociatedCity(String associatedCity) {
	this.associatedCity = associatedCity;
}
}
