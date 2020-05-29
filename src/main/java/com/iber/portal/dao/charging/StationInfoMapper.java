package com.iber.portal.dao.charging;

import java.util.List;
import java.util.Map;
import com.iber.portal.model.charging.StationInfo;


/**
 */
public interface StationInfoMapper {
	
	int insert(StationInfo record);
	
	int deleteByPrimaryKey(Integer id);
	
	int updateByPrimaryKey(StationInfo record);
	
	int updateByPrimaryKeySelective(StationInfo record);
	
	StationInfo selectByPrimaryKey(Integer id);
	
	int getAllNum(Map<String, Object> paramMap);
	
	List<StationInfo> queryPageList(Map<String, Object> paramMap);
	
	int queryIdByName(String stationName);
	
	List<StationInfo> queryByCode(String cityCode);
}
