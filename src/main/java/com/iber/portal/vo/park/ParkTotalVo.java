package com.iber.portal.vo.park;

public class ParkTotalVo {
	
	private String cityName ="" ;
	private Integer parkTotal = 0 ;
	
	private Integer parkEnabledTotal = 0 ;
	
	private Integer parkUsedTotal = 0 ;
	
	private Integer parkCarportTotal = 0 ;
	
	private Integer parkTotalByType;//网点总数（网点类型划分）
	
	private Integer parkType;//网点类型
	
	private Integer parkServiceType;//网点服务类型
	
	private Integer parkTotalByService;//网点总数(服务类型划分)
	
	private Integer total;//网点车位数总计
	
	private Integer connector_type;//充电枪类型
	
	private Integer connectorTotal;//充电枪个数(代表车位数)
	
	private String brand_name;//车辆品牌名称
	
	private Integer brandTotal;//车辆总计（按品牌划分）

	private Integer websiteTotal;//网点总计
	
	private String name;
	
	private String code;
	
	private Integer parkNum;//各省网点数
	
	private Integer cityTotal;//省下级城市网点总计
	
	private Integer areaTotal;//区县网点总计
	
	public ParkTotalVo() {
		super();
	}

	public ParkTotalVo(String cityName,Integer parkTotal, Integer parkEnabledTotal,
			Integer parkUsedTotal, Integer parkCarportTotal) {
		super();
		this.cityName = cityName ;
		this.parkTotal = parkTotal;
		this.parkEnabledTotal = parkEnabledTotal;
		this.parkUsedTotal = parkUsedTotal;
		this.parkCarportTotal = parkCarportTotal;
	}



	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public Integer getParkTotal() {
		return parkTotal;
	}


	public void setParkTotal(Integer parkTotal) {
		this.parkTotal = parkTotal;
	}


	public Integer getParkEnabledTotal() {
		return parkEnabledTotal;
	}


	public void setParkEnabledTotal(Integer parkEnabledTotal) {
		this.parkEnabledTotal = parkEnabledTotal;
	}


	public Integer getParkUsedTotal() {
		return parkUsedTotal;
	}


	public void setParkUsedTotal(Integer parkUsedTotal) {
		this.parkUsedTotal = parkUsedTotal;
	}


	public Integer getParkCarportTotal() {
		return parkCarportTotal;
	}


	public void setParkCarportTotal(Integer parkCarportTotal) {
		this.parkCarportTotal = parkCarportTotal;
	}

	public Integer getParkTotalByType() {
		return parkTotalByType;
	}

	public void setParkTotalByType(Integer parkTotalByType) {
		this.parkTotalByType = parkTotalByType;
	}

	public Integer getParkType() {
		return parkType;
	}

	public void setParkType(Integer parkType) {
		this.parkType = parkType;
	}

	public Integer getParkServiceType() {
		return parkServiceType;
	}

	public void setParkServiceType(Integer parkServiceType) {
		this.parkServiceType = parkServiceType;
	}

	public Integer getParkTotalByService() {
		return parkTotalByService;
	}

	public void setParkTotalByService(Integer parkTotalByService) {
		this.parkTotalByService = parkTotalByService;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Integer getConnector_type() {
		return connector_type;
	}

	public void setConnector_type(Integer connector_type) {
		this.connector_type = connector_type;
	}

	public Integer getConnectorTotal() {
		return connectorTotal;
	}

	public void setConnectorTotal(Integer connectorTotal) {
		this.connectorTotal = connectorTotal;
	}

	public String getBrand_name() {
		return brand_name;
	}

	public void setBrand_name(String brand_name) {
		this.brand_name = brand_name;
	}

	public Integer getBrandTotal() {
		return brandTotal;
	}

	public void setBrandTotal(Integer brandTotal) {
		this.brandTotal = brandTotal;
	}

	public Integer getWebsiteTotal() {
		return websiteTotal;
	}

	public void setWebsiteTotal(Integer websiteTotal) {
		this.websiteTotal = websiteTotal;
	}

	public Integer getParkNum() {
		return parkNum;
	}

	public void setParkNum(Integer parkNum) {
		this.parkNum = parkNum;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getCityTotal() {
		return cityTotal;
	}

	public void setCityTotal(Integer cityTotal) {
		this.cityTotal = cityTotal;
	}

	public Integer getAreaTotal() {
		return areaTotal;
	}

	public void setAreaTotal(Integer areaTotal) {
		this.areaTotal = areaTotal;
	}
}
