package com.iber.portal.service.charging;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iber.portal.common.Pager;
import com.iber.portal.dao.charging.ChargingPileOrderMapper;
import com.iber.portal.exception.ServiceException;
import com.iber.portal.model.charging.ChargingPileOrder;

@Service
public class ChargingPileOrderService {

	@Autowired
	private ChargingPileOrderMapper chargingPileOrderMapper;
	
	public int insertSelective(ChargingPileOrder model) throws ServiceException {
		int len;
		try {
			len = chargingPileOrderMapper.insertSelective(model);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		return len;
	}

	public int deleteByPrimaryKey(int id) throws ServiceException {
		int len;
		try {
			len = chargingPileOrderMapper.deleteByPrimaryKey(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		return len;
	}

	public int updateByPrimaryKeySelective(ChargingPileOrder model) throws ServiceException {
		int len;
		try {
			len = chargingPileOrderMapper.updateByPrimaryKeySelective(model);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		return len;
	}

	public ChargingPileOrder selectByPrimaryKey(int id) {
		return chargingPileOrderMapper.selectByPrimaryKey(id);
	}
	
	public Pager<ChargingPileOrder> getAll(Map<String, Object> paramMap) {
		List<ChargingPileOrder> listObj = chargingPileOrderMapper.getAll(paramMap);
		Pager<ChargingPileOrder> pager = new Pager<ChargingPileOrder>();
		pager.setDatas(listObj);
		pager.setTotalCount(chargingPileOrderMapper.getAllNum(paramMap));
		return pager;
	}
	

}
