package com.iber.portal.cache;

import java.util.Calendar;

/**
 * 结果VO
 * @author ouxx
 * @since 2016-10-11 下午6:14:05
 *
 */
public class ResultVo{
	//结果
	private Object result;
	//缓存期限
	private Calendar deadline;
	
	public Object getResult() {
		return result;
	}
	public void setResult(Object result) {
		this.result = result;
	}
	public Calendar getDeadline() {
		return deadline;
	}
	public void setDeadline(Calendar calDeadline) {
		this.deadline = calDeadline;
	}
}