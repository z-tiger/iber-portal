package com.iber.portal.getui;

public class PushCarCommon implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int code ;
	
	private String eventCode = "server_push_order";
	
	private String msg ;
	
	private Object result ;

	
	public PushCarCommon() {
		super();
	}

	public PushCarCommon(int code, String eventCode, String msg, Object result) {
		super();
		this.code = code;
		this.eventCode = eventCode;
		this.msg = msg;
		this.result = result;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getEventCode() {
		return eventCode;
	}

	public void setEventCode(String eventCode) {
		this.eventCode = eventCode;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}
	
	
}
