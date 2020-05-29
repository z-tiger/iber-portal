package com.iber.portal.exception;

/**
 * @describe 数据库操作异常类
 * 
 * @author ruanpeng
 * @date 2014年6月27日
 */
public class DaoException extends Exception {

	private static final long serialVersionUID = -4436347330441070060L;

	public DaoException() {
	}

	public DaoException(String message) {
		super(message);
	}
}
