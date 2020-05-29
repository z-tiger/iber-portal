package com.iber.portal.model.base;

import java.util.Date;

public class WZCitys {
    private Integer id;

    private String cityName;

    private String cityCode;

    private String abbr;

    private String engine;

    private String engineno;

    private String classa;

    private String classz;

    private String classno;

    private String regist;

    private String registno;

    private Date createTime;
    
    private String province;
    
    private String provinceCode;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName == null ? null : cityName.trim();
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode == null ? null : cityCode.trim();
    }

    public String getAbbr() {
        return abbr;
    }

    public void setAbbr(String abbr) {
        this.abbr = abbr == null ? null : abbr.trim();
    }

    public String getEngine() {
        return engine;
    }

    public void setEngine(String engine) {
        this.engine = engine == null ? null : engine.trim();
    }

    public String getEngineno() {
        return engineno;
    }

    public void setEngineno(String engineno) {
        this.engineno = engineno == null ? null : engineno.trim();
    }

    public String getClassa() {
        return classa;
    }

    public void setClassa(String classa) {
        this.classa = classa == null ? null : classa.trim();
    }
    
    public String getClassz() {
		return classz;
	}
    
    public void setClassz(String classz) {
		this.classz = classz;
	}

    public String getClassno() {
        return classno;
    }

    public void setClassno(String classno) {
        this.classno = classno == null ? null : classno.trim();
    }

    public String getRegist() {
        return regist;
    }

    public void setRegist(String regist) {
        this.regist = regist == null ? null : regist.trim();
    }

    public String getRegistno() {
        return registno;
    }

    public void setRegistno(String registno) {
        this.registno = registno == null ? null : registno.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}
    
    
}