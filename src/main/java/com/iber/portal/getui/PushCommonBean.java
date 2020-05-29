package com.iber.portal.getui;

public class PushCommonBean implements java.io.Serializable {
	 
	private static final long serialVersionUID = 1L;
	
	private String eventCode ;  //  事件码 
	private String code ; 		//	事件完成结果	
	private String msg ; 		//	返回信息
	private Object result ;
	
	public PushCommonBean(){} 
	
	public PushCommonBean(String eventCode, String code, String msg,
			Object result) {
		super();
		this.eventCode = eventCode;
		this.code = code;
		this.msg = msg;
		this.result = result;
	}


	public String getEventCode() {
		return eventCode;
	}
	public void setEventCode(String eventCode) {
		this.eventCode = eventCode;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
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
