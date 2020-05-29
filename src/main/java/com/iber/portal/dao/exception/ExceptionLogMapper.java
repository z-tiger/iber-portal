package com.iber.portal.dao.exception;

import java.util.List;
import java.util.Map;

import com.iber.portal.model.exception.ExceptionLog;
/**
 * 
 * <br>
 * <b>功能：</b>XExceptionLogDao<br>
 * <b>作者：</b>www.jeecg.org<br>
 * <b>日期：</b> Feb 2, 2013 <br>
 * <b>版权所有：<b>版权所有(C) 2013，www.jeecg.org<br>
 */
public interface ExceptionLogMapper {
	
	int insert(ExceptionLog record);
	
	int deleteByPrimaryKey(Integer id);
	
	int updateByPrimaryKey(ExceptionLog record);
	
	ExceptionLog selectByPrimaryKey(Integer id);
	
	int getAllNum(Map<String, Object> paramMap);
	
	List<ExceptionLog> queryPageList(Map<String, Object> paramMap);
}
