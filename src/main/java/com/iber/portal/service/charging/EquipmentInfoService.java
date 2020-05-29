package com.iber.portal.service.charging;

import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.iber.portal.common.Pager;
import com.iber.portal.dao.charging.EquipmentInfoMapper;
import com.iber.portal.model.charging.EquipmentInfo;
import com.iber.portal.vo.pile.EquipmentInfoVo;


@Service("equipmentInfoService")
public class EquipmentInfoService{

	private final static Logger log= Logger.getLogger(EquipmentInfoService.class);
	
	@Autowired
    private EquipmentInfoMapper  dao;

		
	public int insert(EquipmentInfo record){
		return dao.insert(record) ;
	}
	
	public int deleteByPrimaryKey(Integer id){
		return dao.deleteByPrimaryKey(id) ;
	}
	
	public int updateByPrimaryKey(EquipmentInfo record){
		return dao.updateByPrimaryKey(record) ;
	}
	
	public int updateByPrimaryKeySelective(EquipmentInfo record){
		return dao.updateByPrimaryKeySelective(record) ;
	}
	
	
	
	public int getAllNum(Map<String, Object> paramMap){
		return dao.getAllNum(paramMap) ;
	}
	
	public Pager<EquipmentInfo> queryPageList(Map<String, Object> paramMap){
		List<EquipmentInfo> listObj = dao.queryPageList(paramMap);
		Pager<EquipmentInfo> pager = new Pager<EquipmentInfo>();
		pager.setDatas(listObj);
		pager.setTotalCount(dao.getAllNum(paramMap));
		return pager;
	}
	
	public int queryIdByCode(String equipmentCode){
		return dao.queryIdByCode(equipmentCode);
	}
	
	public List<EquipmentInfo> getAllBycityCode(String cityCode){
		return dao.queryByStationId(cityCode);
	}
	
	public Pager<EquipmentInfo> queryAttachmentList(Integer id){
		List<EquipmentInfo> listObj = dao.selectByPrimaryKey(id);
		Pager<EquipmentInfo> pager = new Pager<EquipmentInfo>();
		pager.setDatas(listObj);
		pager.setTotalCount(listObj.size());
		return pager;
	}
	public Pager<EquipmentInfo> getAllParkEquipment(Map<String, Object> paramMap) {
		List<EquipmentInfo> listObj = dao.getAllParkEquipment(paramMap);
		Pager<EquipmentInfo> pager = new Pager<EquipmentInfo>();
		pager.setDatas(listObj);
		pager.setTotalCount(dao.getAllParkEquipmentNum(paramMap));
		return pager;
	}

	public Pager<EquipmentInfoVo> selectUpgradePageList(
			Map<String, Object> paramMap) {
		List<EquipmentInfoVo> listObj = dao.selectUpgradePageList(paramMap);
		Pager<EquipmentInfoVo> pager = new Pager<EquipmentInfoVo>();
		pager.setDatas(listObj);
		pager.setTotalCount(dao.getAllNum(paramMap));
		return pager;
	}

	public EquipmentInfo queryByCode(String equipmentCode) {
		return dao.selectByCode(equipmentCode);
	}

	public List<EquipmentInfoVo> queryAllUpgrade(Map<String, Object> map) {
		return dao.queryAllUpgrade(map);
	}
}
