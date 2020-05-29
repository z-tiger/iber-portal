package com.iber.portal.exception;

/**
 * @describe 业务服务模块异常类
 * 
 * @author ruanpeng
 * @date 2014年6月27日
 */
public class ServiceException extends Exception {

	private static final long serialVersionUID = 8766746989649456423L;

	public ServiceException() {
	}

	public ServiceException(String message) {
		super(message);
	}
}
