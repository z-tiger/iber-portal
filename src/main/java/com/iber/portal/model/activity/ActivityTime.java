package com.iber.portal.model.activity;

import java.util.Date;

import javax.xml.crypto.Data;

public class ActivityTime {
	private Date startTime;//开始时间
	private Date endTime;//结束时间
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
}
