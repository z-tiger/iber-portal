package com.iber.portal.service.charging;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iber.portal.common.Pager;
import com.iber.portal.dao.charging.ChargingPileTypeMapper;
import com.iber.portal.exception.ServiceException;
import com.iber.portal.model.charging.ChargingPileType;

@Service
public class ChargingPileTypeService {

	@Autowired
	private ChargingPileTypeMapper chargingPileTypeMapper;
	
	public int insertSelective(ChargingPileType model) throws ServiceException {
		int len;
		try {
			len = chargingPileTypeMapper.insertSelective(model);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		return len;
	}

	public int deleteByPrimaryKey(int id) throws ServiceException {
		int len;
		try {
			len = chargingPileTypeMapper.deleteByPrimaryKey(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		return len;
	}

	public int updateByPrimaryKeySelective(ChargingPileType model) throws ServiceException {
		int len;
		try {
			len = chargingPileTypeMapper.updateByPrimaryKeySelective(model);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		return len;
	}

	public ChargingPileType selectByPrimaryKey(int id) {
		return chargingPileTypeMapper.selectByPrimaryKey(id);
	}
	
	public Pager<ChargingPileType> getAll(Map<String, Object> paramMap) {
		List<ChargingPileType> listObj = chargingPileTypeMapper.getAll(paramMap);
		Pager<ChargingPileType> pager = new Pager<ChargingPileType>();
		pager.setDatas(listObj);
		pager.setTotalCount(chargingPileTypeMapper.getAllNum(paramMap));
		return pager;
	}
	

}
