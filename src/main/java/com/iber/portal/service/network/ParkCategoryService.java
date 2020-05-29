package com.iber.portal.service.network;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iber.portal.common.Pager;
import com.iber.portal.dao.base.ParkCategoryMapper;
import com.iber.portal.model.base.ParkCategory;

/**
 * 
 * <br>
 * <b>功能：</b>XParkCategoryService<br>
 * <b>作者：</b>www.jeecg.org<br>
 * <b>日期：</b> Feb 2, 2013 <br>
 * <b>版权所有：<b>版权所有(C) 2013，www.jeecg.org<br>
 */
@Service("parkCategoryService")
public class ParkCategoryService{

	private final static Logger log= Logger.getLogger(ParkCategoryService.class);
	
	@Autowired
    private ParkCategoryMapper  dao;

		
	public int insert(ParkCategory record){
		return dao.insert(record) ;
	}
	
	public int deleteByPrimaryKey(Integer id){
		return dao.deleteByPrimaryKey(id) ;
	}
	
	public int updateByPrimaryKey(ParkCategory record){
		return dao.updateByPrimaryKey(record) ;
	}
	
	public int updateByPrimaryKeySelective(ParkCategory record){
		return dao.updateByPrimaryKeySelective(record) ;
	}
	
	public ParkCategory selectByPrimaryKey(Integer id){
		return dao.selectByPrimaryKey(id) ;
	}
	
	public int getAllNum(Map<String, Object> paramMap){
		return dao.getAllNum(paramMap) ;
	}
	
	public Pager<ParkCategory> queryPageList(Map<String, Object> paramMap){
		List<ParkCategory> listObj = dao.queryPageList(paramMap);
		Pager<ParkCategory> pager = new Pager<ParkCategory>();
		pager.setDatas(listObj);
		pager.setTotalCount(getAllNum(paramMap));
		return pager;
	}
	
	public List<ParkCategory> queryAllParkCategoryList(){
		return dao.queryAllParkCategoryList() ;
	}
}
