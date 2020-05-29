package com.iber.portal.dao.evaluate;

import java.util.List;
import java.util.Map;

import com.iber.portal.model.enterprise.EnterpriseLevel;
import com.iber.portal.model.evaluate.OrderEvaluateLabel;

public interface OrderEvaluateLabelMapper {
	
	List<OrderEvaluateLabel> selectByPrimaryKey(Map<String, Object> record);

	int selectByPrimaryKeyRecords(Map<String, Object> record);
	
	int deleteByPrimaryKey(Integer id);

    int insert(OrderEvaluateLabel record);

    int insertSelective(OrderEvaluateLabel record);

    int updateByPrimaryKeySelective(OrderEvaluateLabel record);

    int updateByPrimaryKey(OrderEvaluateLabel record);

	OrderEvaluateLabel selectByPrimaryId(Integer id);

}