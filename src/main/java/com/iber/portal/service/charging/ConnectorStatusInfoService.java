package com.iber.portal.service.charging;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.iber.portal.common.Pager;
import com.iber.portal.dao.charging.ConnectorStatusInfoMapper;
import com.iber.portal.model.charging.ConnectorStatusInfo;


@Service("connectorStatusInfoService")
public class ConnectorStatusInfoService{

	private final static Logger log= Logger.getLogger(ConnectorStatusInfoService.class);
	
	@Autowired
    private ConnectorStatusInfoMapper  dao;

		
	public int insert(ConnectorStatusInfo record){
		return dao.insert(record) ;
	}
	
	public int deleteByPrimaryKey(Integer id){
		return dao.deleteByPrimaryKey(id) ;
	}
	
	public int updateByPrimaryKey(ConnectorStatusInfo record){
		return dao.updateByPrimaryKey(record) ;
	}
	
	public int updateByPrimaryKeySelective(ConnectorStatusInfo record){
		return dao.updateByPrimaryKeySelective(record) ;
	}
	
	public ConnectorStatusInfo selectByPrimaryKey(Integer id){
		return dao.selectByPrimaryKey(id) ;
	}
	
	public int getAllNum(Map<String, Object> paramMap){
		return dao.getAllNum(paramMap) ;
	}
	
	public Pager<ConnectorStatusInfo> queryPageList(Map<String, Object> paramMap){
		List<ConnectorStatusInfo> listObj = dao.queryPageList(paramMap);
		Pager<ConnectorStatusInfo> pager = new Pager<ConnectorStatusInfo>();
		pager.setDatas(listObj);
		pager.setTotalCount(getAllNum(paramMap));
		return pager;
	}
	public Pager<ConnectorStatusInfo> queryById(Integer id){
		List<ConnectorStatusInfo> listObj = dao.queryById(id);
		Pager<ConnectorStatusInfo> pager = new Pager<ConnectorStatusInfo>();
		pager.setDatas(listObj);
		pager.setTotalCount(listObj.size());
		return pager;
	}
	
	/**根据设备id删除对应的接口状态信息*/
	public int deleteByEquipmentId(Integer id){
		return dao.deleteByEquipmentId(id);
	}

	public int updateByStatusKey(ConnectorStatusInfo record) {
		return dao.updateByStatusKey(record);
	}

	public int insertSelective(ConnectorStatusInfo record) {
		return dao.insertSelective(record);
		
	}

	public ConnectorStatusInfo queryByconnectorCodeAndId(String connectorCode,
			String connectorId) {
		return dao.queryByconnectorCodeAndId(connectorCode,connectorId);
	}
}
