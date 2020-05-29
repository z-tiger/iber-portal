package com.iber.portal.dao.charging;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.iber.portal.model.charging.ConnectorStatusInfo;

public interface ConnectorStatusInfoMapper {
	
	int insert(ConnectorStatusInfo record);
	
	int deleteByPrimaryKey(Integer id);
	
	int updateByPrimaryKey(ConnectorStatusInfo record);
	
	int updateByPrimaryKeySelective(ConnectorStatusInfo record);
	
	ConnectorStatusInfo selectByPrimaryKey(Integer id);
	
	int getAllNum(Map<String, Object> paramMap);
	List<ConnectorStatusInfo> queryById(Integer id);
	List<ConnectorStatusInfo> queryPageList(Map<String, Object> paramMap);
	
	
	int deleteByEquipmentId(Integer id);

	int updateByStatusKey(ConnectorStatusInfo record);

	int insertSelective(ConnectorStatusInfo record);

	ConnectorStatusInfo queryByconnectorCodeAndId(@Param("connectorCode")String connectorCode,
			@Param("connectorId")String connectorId);
}
