package com.iber.portal.vo.pile;

public class PileTotalVo {
	
	private String cityName ="" ;
	private Integer pileTotal = 0 ;
	
	private Integer pileFastTotal = 0 ;
	
	private Integer pileSlowTotal = 0 ;
	
	
	public PileTotalVo() {
		super();
	}

	public PileTotalVo(String cityName, Integer pileTotal,
			Integer pileFastTotal, Integer pileSlowTotal) {
		super();
		this.cityName = cityName;
		this.pileTotal = pileTotal;
		this.pileFastTotal = pileFastTotal;
		this.pileSlowTotal = pileSlowTotal;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public Integer getPileTotal() {
		return pileTotal;
	}

	public void setPileTotal(Integer pileTotal) {
		this.pileTotal = pileTotal;
	}

	public Integer getPileFastTotal() {
		return pileFastTotal;
	}

	public void setPileFastTotal(Integer pileFastTotal) {
		this.pileFastTotal = pileFastTotal;
	}

	public Integer getPileSlowTotal() {
		return pileSlowTotal;
	}

	public void setPileSlowTotal(Integer pileSlowTotal) {
		this.pileSlowTotal = pileSlowTotal;
	}
}
