package com.iber.portal.dao.charging;

import java.util.List;
import java.util.Map;

import com.iber.portal.model.charging.ConnectorInfo;
import com.iber.portal.model.charging.ConnectorStatusInfo;
import com.iber.portal.vo.charging.ChargingMonitorVo;
import com.iber.portal.vo.charging.ConnectorInfoVo;



public interface ConnectorInfoMapper {
	
	int insert(ConnectorInfo record);
	
	int deleteByPrimaryKey(Integer id);
	
	int updateByPrimaryKey(ConnectorInfo record);
	
	int updateByPrimaryKeySelective(ConnectorInfo record);
	
	ConnectorInfo selectByPrimaryKey(Integer id);
	
	int getAllNum(Map<String, Object> paramMap);
	
	List<ConnectorInfo> queryPageList(Map<String, Object> paramMap);
	
	List<ConnectorInfoVo>queryAttachmentList(Integer id);
	
	//根据充电桩删除充电枪
	int deleteByEquipmentId(Integer id);
	
	/**
	 * 根据cityCode、网点名、枪状态查询充电桩与枪的状态信息
	 * @param paramMap
	 * @return
	 * @author ouxx
	 * @date 2017-3-10 下午8:03:23
	 */
	List<ChargingMonitorVo> queryConnectorStatusByCityParkAndStatus(
			Map<String, Object> paramMap);
	
	/**
	 * 根据cityCode、网点名、枪状态查询充电桩与枪的数量。用于分页
	 * @param paramMap
	 * @return
	 * @author ouxx
	 * @date 2017-3-10 下午8:03:23
	 */
	int queryConnectorRecordsByCityParkAndStatus(
			Map<String, Object> paramMap);
	/**
	 * 根据充电枪id查询该充电枪是否已经被预约
	 * @param connectorId
	 * @return
	 */
	Integer selectOrderedOrderByConnectorId(Integer connectorId);

	int insertSelective(ConnectorInfo entity);
	
	List<ChargingMonitorVo> queryConnectorStatusAndParkInfo(Map<String, Object> map);
	List<ChargingMonitorVo> queryPartnerConnectorAndParkInfo(Map<String, Object> map);
	int queryConnectorStatusAndParkInfoRecords(Map<String, Object> map);
	int queryPartnerConnectorAndParkInfoRecords(Map<String, Object> map);
	
}
