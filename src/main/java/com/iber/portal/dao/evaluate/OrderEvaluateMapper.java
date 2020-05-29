package com.iber.portal.dao.evaluate;

import java.util.List;
import java.util.Map;

import com.iber.portal.model.evaluate.OrderEvaluate;

public interface OrderEvaluateMapper {
   
	List<OrderEvaluate> selectByPrimaryKey(Map<String, Object> record);

	int selectByPrimaryKeyRecords(Map<String, Object> record);
	
	int deleteByPrimaryKey(Integer id);

    int insert(OrderEvaluate record);

    int insertSelective(OrderEvaluate record);

    int updateByPrimaryKeySelective(OrderEvaluate record);

    int updateByPrimaryKey(OrderEvaluate record);
    
    OrderEvaluate selectByPrimaryId(Integer id);
}