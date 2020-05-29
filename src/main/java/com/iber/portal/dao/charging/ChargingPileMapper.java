package com.iber.portal.dao.charging;

import java.util.List;
import java.util.Map;

import com.iber.portal.model.charging.ChargingPile;

public interface ChargingPileMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ChargingPile record);

    int insertSelective(ChargingPile record);

    ChargingPile selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ChargingPile record);

    int updateByPrimaryKey(ChargingPile record);

	List<ChargingPile> getAll(Map<String, Object> paramMap);

	int getAllNum(Map<String, Object> paramMap);
	
	List<ChargingPile> queryPileByParkId(Map<String, Object> paramMap);
	
	List<ChargingPile> queryPileByAreaCode(Map<String, Object> paramMap);
	
	int queryParkPileByAreaCode(String areaCode) ;
	
	List<ChargingPile> queryPileStatusByAreaCode(Map<String, Object> paramMap) ;
}