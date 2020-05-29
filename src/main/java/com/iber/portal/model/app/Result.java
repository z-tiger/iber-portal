package com.iber.portal.model.app;

import java.io.Serializable;

public class Result implements Serializable{
	private int status;//0表示成功，其他失败
	private String msg;//消息
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	@Override
	public String toString() {
		return "Result [status=" + status + ", msg=" + msg + "]";
	}
	
	
}
