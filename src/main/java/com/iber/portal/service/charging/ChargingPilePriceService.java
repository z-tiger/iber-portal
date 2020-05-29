package com.iber.portal.service.charging;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iber.portal.common.Pager;
import com.iber.portal.dao.charging.ChargingPilePriceMapper;
import com.iber.portal.exception.ServiceException;
import com.iber.portal.model.charging.ChargingPilePrice;

@Service
public class ChargingPilePriceService {

	@Autowired
	private ChargingPilePriceMapper chargingPilePriceMapper;
	
	public int insertSelective(ChargingPilePrice model) throws ServiceException {
		int len;
		try {
			len = chargingPilePriceMapper.insertSelective(model);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		return len;
	}

	public int deleteByPrimaryKey(int id) throws ServiceException {
		int len;
		try {
			len = chargingPilePriceMapper.deleteByPrimaryKey(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		return len;
	}

	public int updateByPrimaryKeySelective(ChargingPilePrice model) throws ServiceException {
		int len;
		try {
			len = chargingPilePriceMapper.updateByPrimaryKeySelective(model);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		return len;
	}

	public ChargingPilePrice selectByPrimaryKey(int id) {
		return chargingPilePriceMapper.selectByPrimaryKey(id);
	}
	
	public Pager<ChargingPilePrice> getAll(Map<String, Object> paramMap) {
		List<ChargingPilePrice> listObj = chargingPilePriceMapper.getAll(paramMap);
		Pager<ChargingPilePrice> pager = new Pager<ChargingPilePrice>();
		pager.setDatas(listObj);
		pager.setTotalCount(chargingPilePriceMapper.getAllNum(paramMap));
		return pager;
	}
	

}
