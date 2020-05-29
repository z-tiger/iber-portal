package com.iber.portal.vo.dayRent;

public class CarOrderBean implements java.io.Serializable {
	 
	private static final long serialVersionUID = 1L;
	private String	orderNo ; 					 		  //	订单ID
	private String	identifyNo ; 						  //	身份证号码
	private String	fingerprint ;				 		  //	指纹
	private String	ecarno ; 							  //	电子车牌
	private String	bluetoochNo ; 				 		  //	蓝牙号
	private String  orderTime ;
	private String  userName ;
	private String  sex ;
	private String  orderEndTime ;
	
	public CarOrderBean() {
		super();
	}
	
	public CarOrderBean(String orderNo,
			String identifyNo, String fingerprint, String ecarno,
			String bluetoochNo, String orderTime, String userName, String sex,String orderEndTime) {
		super();
		this.orderNo = orderNo;
		this.identifyNo = identifyNo;
		this.fingerprint = fingerprint;
		this.ecarno = ecarno;
		this.bluetoochNo = bluetoochNo;
		this.orderTime = orderTime;
		this.userName = userName;
		this.sex = sex;
		this.orderEndTime = orderEndTime;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getIdentifyNo() {
		return identifyNo;
	}
	public void setIdentifyNo(String identifyNo) {
		this.identifyNo = identifyNo;
	}
	public String getFingerprint() {
		return fingerprint;
	}
	public void setFingerprint(String fingerprint) {
		this.fingerprint = fingerprint;
	}
	public String getEcarno() {
		return ecarno;
	}
	public void setEcarno(String ecarno) {
		this.ecarno = ecarno;
	}
	public String getBluetoochNo() {
		return bluetoochNo;
	}
	public void setBluetoochNo(String bluetoochNo) {
		this.bluetoochNo = bluetoochNo;
	}
	public String getOrderTime() {
		return orderTime;
	}
	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getOrderEndTime() {
		return orderEndTime;
	}

	public void setOrderEndTime(String orderEndTime) {
		this.orderEndTime = orderEndTime;
	} 
	
}
