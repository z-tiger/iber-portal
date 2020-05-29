package com.iber.portal.dao.base;

import java.util.HashMap;
import java.util.List;

import com.iber.portal.model.base.WZAssociatedCitys;

public interface WZAssociatedCitysMapper {
    Integer insert(WZAssociatedCitys wazssociatedCitys);

	List<WZAssociatedCitys> selectAll(HashMap<String, Object> map);

	int selectAllRecords(HashMap<String, Object> map);

	WZAssociatedCitys selectByPrimaryId(int id);

	int updateByPrimaryKeySelective(WZAssociatedCitys currObj);

	int delete(int id);

	List<WZAssociatedCitys> selectAllAssociatedCitys();
	
	WZAssociatedCitys selectCityByCode(String cityCode);

	int queryIfRecord(String cityCode);

}
