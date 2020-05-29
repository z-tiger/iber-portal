package com.iber.portal.vo.base;

import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 日期vo
 * lf
 * 2018年1月4日
 */
public class DateVo extends PageVo implements Serializable{

    private static final long serialVersionUID = -6863763604145180841L;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date from ;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date to ;

    public Date getFrom() {
        return from;
    }

    public void setFrom(Date from) {
        this.from = from;
    }

    public Date getTo() {
        return to;
    }

    public void setTo(Date to) {
        this.to = to;
    }

    @Override
    public String toString() {
        return "DateVo{" +
                "from=" + from +
                ", to=" + to +
                '}';
    }
}
