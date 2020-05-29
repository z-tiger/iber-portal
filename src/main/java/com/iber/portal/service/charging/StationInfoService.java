package com.iber.portal.service.charging;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



import com.iber.portal.common.Pager;
import com.iber.portal.dao.charging.StationInfoMapper;
import com.iber.portal.model.charging.StationInfo;

/**
 * 
 * <br>
 * <b>功能：</b>StationInfoService<br>
 * <b>作者：</b>www.jeecg.org<br>
 * <b>日期：</b> Feb 2, 2013 <br>
 * <b>版权所有：<b>版权所有(C) 2013，www.jeecg.org<br>
 */
@Service("stationInfoService")
public class StationInfoService{

	private final static Logger log= Logger.getLogger(StationInfoService.class);
	
	@Autowired
    private StationInfoMapper  dao;

		
	public int insert(StationInfo record){
		return dao.insert(record) ;
	}
	
	public int deleteByPrimaryKey(Integer id){
		return dao.deleteByPrimaryKey(id) ;
	}
	
	public int updateByPrimaryKey(StationInfo record){
		return dao.updateByPrimaryKey(record) ;
	}
	
	public int updateByPrimaryKeySelective(StationInfo record){
		return dao.updateByPrimaryKeySelective(record) ;
	}
	
	public StationInfo selectByPrimaryKey(Integer id){
		return dao.selectByPrimaryKey(id) ;
	}
	
	public int getAllNum(Map<String, Object> paramMap){
		return dao.getAllNum(paramMap) ;
	}
	
	public Pager<StationInfo> queryPageList(Map<String, Object> paramMap){
		List<StationInfo> listObj = dao.queryPageList(paramMap);
		Pager<StationInfo> pager = new Pager<StationInfo>();
		pager.setDatas(listObj);
		pager.setTotalCount(getAllNum(paramMap));
		return pager;
	}
	
	public int queryIdByName(String stationName){
		return dao.queryIdByName(stationName);
	}
	
	public List<StationInfo> getAllStationByCode(String cityCode){
		return dao.queryByCode(cityCode);
		
	}
}
