package com.iber.portal.dao.sys;

import java.util.List;
import java.util.Map;

import com.iber.portal.model.sys.SysOperateLog;



public interface SysOperateLogMapper {
	
	int insert(SysOperateLog record);
	
	int deleteByPrimaryKey(Integer id);
	
	int updateByPrimaryKey(SysOperateLog record);
	
	int updateByPrimaryKeySelective(SysOperateLog record);
	
	SysOperateLog selectByPrimaryKey(Integer id);
	
	int getAllNum(Map<String, Object> paramMap);
	
	List<SysOperateLog> queryPageList(Map<String, Object> paramMap);
}
