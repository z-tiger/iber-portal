package com.iber.portal.dao.base;

import java.util.List;
import java.util.Map;

import com.iber.portal.model.base.ParkCategory;

/**
 * 
 * <br>
 * <b>功能：</b>XParkCategoryDao<br>
 * <b>作者：</b>www.jeecg.org<br>
 * <b>日期：</b> Feb 2, 2013 <br>
 * <b>版权所有：<b>版权所有(C) 2013，www.jeecg.org<br>
 */
public interface ParkCategoryMapper {
	
	int insert(ParkCategory record);
	
	int deleteByPrimaryKey(Integer id);
	
	int updateByPrimaryKey(ParkCategory record);
	
	int updateByPrimaryKeySelective(ParkCategory record);
	
	ParkCategory selectByPrimaryKey(Integer id);
	
	int getAllNum(Map<String, Object> paramMap);
	
	List<ParkCategory> queryPageList(Map<String, Object> paramMap);
	
	List<ParkCategory> queryAllParkCategoryList() ;
}
