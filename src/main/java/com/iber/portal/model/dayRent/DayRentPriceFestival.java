package com.iber.portal.model.dayRent;

import java.util.Date;

public class DayRentPriceFestival {
    private Integer id;

    private Date festivalDate;

    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFestivalDate() {
        return festivalDate;
    }

    public void setFestivalDate(Date festivalDate) {
        this.festivalDate = festivalDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }
}