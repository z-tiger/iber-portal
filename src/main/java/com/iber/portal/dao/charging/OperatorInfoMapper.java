package com.iber.portal.dao.charging;

import java.util.List;
import java.util.Map;
import com.iber.portal.model.charging.OperatorInfo;


public interface OperatorInfoMapper {
	
	int insert(OperatorInfo record);
	
	int deleteByPrimaryKey(Integer id);
	
	int updateByPrimaryKey(OperatorInfo record);
	
	int updateByPrimaryKeySelective(OperatorInfo record);
	
	OperatorInfo selectByPrimaryKey(Integer id);
	
	int getAllNum(Map<String, Object> paramMap);
	
	List<OperatorInfo> queryPageList(Map<String, Object> paramMap);
	
	List<OperatorInfo> getAllOperatorInfo();
	List<OperatorInfo> getAllOperatorName();
	
	int selectIdByName(String name);
}
