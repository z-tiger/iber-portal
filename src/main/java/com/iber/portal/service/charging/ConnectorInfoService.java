package com.iber.portal.service.charging;

import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.iber.portal.common.Pager;
import com.iber.portal.dao.charging.ConnectorInfoMapper;
import com.iber.portal.model.charging.ConnectorInfo;
import com.iber.portal.model.charging.ConnectorStatusInfo;
import com.iber.portal.vo.charging.ChargingMonitorVo;
import com.iber.portal.vo.charging.ConnectorInfoVo;
import com.sun.org.apache.regexp.internal.recompile;

@Service("connectorInfoService")
public class ConnectorInfoService {

	private final static Logger log = Logger.getLogger(ConnectorInfoService.class);

	@Autowired
	private ConnectorInfoMapper connectorInfoMapper;
	public List<ChargingMonitorVo> queryPartnerConnectorAndParkInfo(Map<String, Object> paramMap){
		return connectorInfoMapper.queryPartnerConnectorAndParkInfo(paramMap);
	}
	public int queryPartnerConnectorAndParkInfoRecords(Map<String, Object> paramMap){
		return connectorInfoMapper.queryPartnerConnectorAndParkInfoRecords(paramMap);
	}
	
    public List<ChargingMonitorVo> queryConnectorStatusAndParkInfo(Map<String, Object> paramMap){
    	return connectorInfoMapper.queryConnectorStatusAndParkInfo(paramMap);
    }
	
    public int queryParkAndEquipConnRecords(Map<String, Object> paramMap){
    	return connectorInfoMapper.queryConnectorStatusAndParkInfoRecords(paramMap);
    }
    
	public int insert(ConnectorInfo record) {
		return connectorInfoMapper.insert(record);
	}

	public int deleteByPrimaryKey(Integer id) {
		return connectorInfoMapper.deleteByPrimaryKey(id);
	}

	public int updateByPrimaryKey(ConnectorInfo record) {
		return connectorInfoMapper.updateByPrimaryKey(record);
	}

	public int updateByPrimaryKeySelective(ConnectorInfo record) {
		return connectorInfoMapper.updateByPrimaryKeySelective(record);
	}

	public ConnectorInfo selectByPrimaryKey(Integer id) {
		return connectorInfoMapper.selectByPrimaryKey(id);
	}

	public int getAllNum(Map<String, Object> paramMap) {
		return connectorInfoMapper.getAllNum(paramMap);
	}

	public Pager<ConnectorInfo> queryPageList(Map<String, Object> paramMap) {
		List<ConnectorInfo> listObj = connectorInfoMapper.queryPageList(paramMap);
		Pager<ConnectorInfo> pager = new Pager<ConnectorInfo>();
		pager.setDatas(listObj);
		pager.setTotalCount(connectorInfoMapper.getAllNum(paramMap));
		return pager;
	}

	/** 设备管理页面。接口信息及状态附件列表 */
	public Pager<ConnectorInfoVo> queryAttachmentList(Integer id) {
		List<ConnectorInfoVo> listObj = connectorInfoMapper.queryAttachmentList(id);
		Pager<ConnectorInfoVo> pager = new Pager<ConnectorInfoVo>();
		pager.setDatas(listObj);
		pager.setTotalCount(listObj.size());
		return pager;
	}

	/** 根据充电桩id删除对应的接口信息 */
	public int deleteByEquipmentId(Integer id) {
		return connectorInfoMapper.deleteByEquipmentId(id);
	}

	/**
	 * 根据cityCode、网点名、枪状态查询充电桩与枪的状态信息
	 * 
	 * @param paramMap
	 * @return
	 * @author ouxx
	 * @date 2017-3-10 下午8:05:34
	 */
	public List<ChargingMonitorVo> queryConnectorStatusByCityParkAndStatus(Map<String, Object> paramMap) {
		return connectorInfoMapper.queryConnectorStatusByCityParkAndStatus(paramMap);
	}

	public int queryConnectorRecordsByCityParkAndStatus(
			Map<String, Object> paramMap){
		return connectorInfoMapper.queryConnectorRecordsByCityParkAndStatus(paramMap);
	}

	public Integer selectOrderedOrderByConnectorId(Integer connectorId) {
		return connectorInfoMapper.selectOrderedOrderByConnectorId(connectorId);
	}

	public int insertSelective(ConnectorInfo entity) {
		return connectorInfoMapper.insertSelective(entity);
	}
}
