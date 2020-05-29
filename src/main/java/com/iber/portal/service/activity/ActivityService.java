package com.iber.portal.service.activity;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iber.portal.common.Pager;
import com.iber.portal.dao.activity.ActivityMapper;
import com.iber.portal.model.activity.Activity;

/**
 * 
 * <br>
 * <b>功能：</b>ActivityService<br>
 * <b>作者：</b>www.jeecg.org<br>
 * <b>日期：</b> Feb 2, 2013 <br>
 * <b>版权所有：<b>版权所有(C) 2013，www.jeecg.org<br>
 */
@Service("activityService")
public class ActivityService{

	private final static Logger log= Logger.getLogger(ActivityService.class);
	
	@Autowired
    private ActivityMapper  dao;

		
	public int insert(Activity record){
		
		return dao.insert(record) ;
	}
	public int insertSelective(Activity record){
		return dao.insertSelective(record);
	}
	
	public int deleteByPrimaryKey(Integer id){
		return dao.deleteByPrimaryKey(id) ;
	}
	
	public int updateByPrimaryKey(Activity record){
		return dao.updateByPrimaryKey(record) ;
	}
	
	public int updateByPrimaryKeySelective(Activity record){
		return dao.updateByPrimaryKeySelective(record) ;
	}
	
	public Activity selectByPrimaryKey(Integer id){
		return dao.selectByPrimaryKey(id) ;
	}
	
	public int getAllNum(Map<String, Object> paramMap){
		return dao.getAllNum(paramMap) ;
	}
	
	public Pager<Activity> queryPageList(Map<String, Object> paramMap){
		List<Activity> listObj = dao.queryPageList(paramMap);
		Pager<Activity> pager = new Pager<Activity>();
		pager.setDatas(listObj);
		pager.setTotalCount(getAllNum(paramMap));
		return pager;
	}
	
	public List<Activity> selectAll(){
		return dao.selectAll();
	}

	public List<Activity> selectByCode(String code) {
		return dao.selectByCode(code);
	}
	
	public List<Activity> selectStartingActivity(Map<String, Object> map){
	    return dao.selectStartingActivity(map);
	}
	
	public List<Activity> selectActivityByCode(String cityCode){
		return dao.selectActivityByCode(cityCode);
	}
	
	public List<Activity> selecIdenticalCity(String cityCode){
		return dao.selecIdenticalCity(cityCode);
	}
	public List<Activity> selectByIdCode(Map<String, Object> map) {
		return dao.selectByIdCode(map);
	}
	
}
