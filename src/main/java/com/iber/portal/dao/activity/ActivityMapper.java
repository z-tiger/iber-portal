package com.iber.portal.dao.activity;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.iber.portal.model.activity.Activity;


/**
 * 
 * <br>
 * <b>功能：</b>ActivityDao<br>
 * <b>作者：</b>www.jeecg.org<br>
 * <b>日期：</b> Feb 2, 2013 <br>
 * <b>版权所有：<b>版权所有(C) 2013，www.jeecg.org<br>
 */
public interface ActivityMapper {
	
	int insert(Activity record);
	int insertSelective(Activity record);
	
	int deleteByPrimaryKey(Integer id);
	
	int updateByPrimaryKey(Activity record);
	int updateByPrimaryKeySelective(Activity record);
	
//	int updateByPrimaryKeySelective(Activity record);
	
	Activity selectByPrimaryKey(Integer id);
	
	int getAllNum(Map<String, Object> paramMap);
	
	List<Activity> queryPageList(Map<String, Object> paramMap);
	
	List<Activity> selectAll();
	
	List<Activity> selectActivityByCode(@Param("cityCode") String cityCode) ;
	
	List<Activity> selecIdenticalCity(@Param("cityCode") String cityCode);
	/**
	 * 获取最新的有效且未过期的活动
	 * @return
	 * @author ouxx
	 * @date 2016-12-16 下午2:03:30
	 */
	Activity getLatestActivity();
	/**
	 * 根据code获取所有的活动
	 * @param code
	 * @return
	 */
	List<Activity> selectByCode(String code);
	/**
	 * 获取已启动的活动
	 */
	List<Activity> selectStartingActivity(Map<String, Object> paramMap);
	
	List<Activity> selectByIdCode(Map<String, Object> map);
}
