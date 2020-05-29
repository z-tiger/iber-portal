package com.iber.portal.service.charging;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iber.portal.common.Pager;
import com.iber.portal.dao.charging.ChargingPileCarMapper;
import com.iber.portal.exception.ServiceException;
import com.iber.portal.model.charging.ChargingPileCar;

@Service
public class ChargingPileCarService {

	@Autowired
	private ChargingPileCarMapper chargingPileCarMapper;
	
	public int insertSelective(ChargingPileCar model) throws ServiceException {
		int len;
		try {
			len = chargingPileCarMapper.insertSelective(model);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		return len;
	}

	public int deleteByPrimaryKey(int id) throws ServiceException {
		int len;
		try {
			len = chargingPileCarMapper.deleteByPrimaryKey(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		return len;
	}

	public int updateByPrimaryKeySelective(ChargingPileCar model) throws ServiceException {
		int len;
		try {
			len = chargingPileCarMapper.updateByPrimaryKeySelective(model);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		return len;
	}

	public ChargingPileCar selectByPrimaryKey(int id) {
		return chargingPileCarMapper.selectByPrimaryKey(id);
	}
	
	public Pager<ChargingPileCar> getAll(Map<String, Object> paramMap) {
		List<ChargingPileCar> listObj = chargingPileCarMapper.getAll(paramMap);
		Pager<ChargingPileCar> pager = new Pager<ChargingPileCar>();
		pager.setDatas(listObj);
		pager.setTotalCount(chargingPileCarMapper.getAllNum(paramMap));
		return pager;
	}

	public int deleteByTypeIdAndBrandId(Map<String, Object> paramMap) throws ServiceException {
		int len;
		try {
			len = chargingPileCarMapper.deleteByTypeIdAndBrandId(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		return len;
	}
	

}
