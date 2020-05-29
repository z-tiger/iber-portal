package com.iber.portal.vo.longRent;

import com.iber.portal.model.longRent.LongRentRateCalendar;

import java.io.Serializable;

public class LongRentRateCalendarVo extends LongRentRateCalendar implements Serializable{

    private static final long serialVersionUID = -3896077568663667497L;
    private Double moneyFen;

    public Double getMoneyFen() {
        return moneyFen;
    }

    public void setMoneyFen(Double moneyFen) {
        this.moneyFen = moneyFen;
    }

    @Override
    public String toString() {
        return "LongRentRateCalendarVo{" +
                "moneyFen=" + moneyFen +
                "} " + super.toString();
    }
}
