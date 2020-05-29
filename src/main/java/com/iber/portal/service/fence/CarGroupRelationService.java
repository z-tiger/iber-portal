package com.iber.portal.service.fence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iber.portal.dao.base.CarGroupRelationMapper;
import com.iber.portal.exception.ServiceException;
import com.iber.portal.model.base.CarGroupRelation;

@Service
public class CarGroupRelationService {
	
	@Autowired
	private CarGroupRelationMapper carGroupRelationMapper ;

	public int insertSelective(CarGroupRelation model) throws ServiceException {
		int len;
		try {
			String lpn = model.getLpn();
			Integer groupId = model.getGroupId();
			//如果已有相同的lpn, groupId记录，则不插入
			int count = carGroupRelationMapper.getCountByGroupIdAndLpn(groupId, lpn);
			if(count > 0){
				return 0;
			}
			len = carGroupRelationMapper.insertSelective(model);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		return len;
	}

	public int deleteByPrimaryKey(int id) throws ServiceException {
		int len;
		try {
			len = carGroupRelationMapper.deleteByPrimaryKey(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		return len;
	}

	public int updateByPrimaryKeySelective(CarGroupRelation model) throws ServiceException {
		int len;
		try {
			len = carGroupRelationMapper.updateByPrimaryKeySelective(model);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		return len;
	}

	public CarGroupRelation selectByPrimaryKey(int id) {
		return carGroupRelationMapper.selectByPrimaryKey(id);
	}
	
	public List<CarGroupRelation> selectAll(Map<String, Object> map){
		return carGroupRelationMapper.selectAll(map);
	}
    
	public int selectAllRecords(Map<String, Object> map){
		return carGroupRelationMapper.selectAllRecords(map);
	}
	
	public List<CarGroupRelation> selectLpnByGroupIdAndLpn(Integer groupId , String lpn){
		Map<String,Object> map = new HashMap<String, Object>() ;
		map.put("groupId", groupId) ;
		map.put("lpn", lpn) ;
		return carGroupRelationMapper.selectLpnByGroupIdAndLpn(map) ;
	}
}
