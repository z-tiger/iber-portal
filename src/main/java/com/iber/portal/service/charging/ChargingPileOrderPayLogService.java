package com.iber.portal.service.charging;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iber.portal.common.Pager;
import com.iber.portal.dao.charging.ChargingPileOrderPayLogMapper;
import com.iber.portal.exception.ServiceException;
import com.iber.portal.model.charging.ChargingPileOrderPayLog;

@Service
public class ChargingPileOrderPayLogService {

	@Autowired
	private ChargingPileOrderPayLogMapper chargingPileOrderPayLogMapper;
	
	public int insertSelective(ChargingPileOrderPayLog model) throws ServiceException {
		int len;
		try {
			len = chargingPileOrderPayLogMapper.insertSelective(model);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		return len;
	}

	public int deleteByPrimaryKey(int id) throws ServiceException {
		int len;
		try {
			len = chargingPileOrderPayLogMapper.deleteByPrimaryKey(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		return len;
	}

	public int updateByPrimaryKeySelective(ChargingPileOrderPayLog model) throws ServiceException {
		int len;
		try {
			len = chargingPileOrderPayLogMapper.updateByPrimaryKeySelective(model);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		return len;
	}

	public ChargingPileOrderPayLog selectByPrimaryKey(int id) {
		return chargingPileOrderPayLogMapper.selectByPrimaryKey(id);
	}
	
	public Pager<ChargingPileOrderPayLog> getAll(Map<String, Object> paramMap) {
		List<ChargingPileOrderPayLog> listObj = chargingPileOrderPayLogMapper.getAll(paramMap);
		Pager<ChargingPileOrderPayLog> pager = new Pager<ChargingPileOrderPayLog>();
		pager.setDatas(listObj);
		pager.setTotalCount(chargingPileOrderPayLogMapper.getAllNum(paramMap));
		return pager;
	}
	

}
