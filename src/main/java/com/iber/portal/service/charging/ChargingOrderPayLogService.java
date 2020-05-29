package com.iber.portal.service.charging;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iber.portal.common.Pager;
import com.iber.portal.dao.charging.ChargingOrderPayLogMapper;

import com.iber.portal.exception.ServiceException;
import com.iber.portal.model.charging.ChargingOrderPayLog;

@Service
public class ChargingOrderPayLogService {

	@Autowired
	private ChargingOrderPayLogMapper chargingOrderPayLogMapper;
	
	public int insertSelective(ChargingOrderPayLog model) throws ServiceException {
		int len;
		try {
			len = chargingOrderPayLogMapper.insertSelective(model);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		return len;
	}

	public int deleteByPrimaryKey(int id) throws ServiceException {
		int len;
		try {
			len = chargingOrderPayLogMapper.deleteByPrimaryKey(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		return len;
	}

	public int updateByPrimaryKeySelective(ChargingOrderPayLog model) throws ServiceException {
		int len;
		try {
			len = chargingOrderPayLogMapper.updateByPrimaryKeySelective(model);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		return len;
	}

	public ChargingOrderPayLog selectByPrimaryKey(int id) {
		return chargingOrderPayLogMapper.selectByPrimaryKey(id);
	}
	
	public Pager<ChargingOrderPayLog> getAll(Map<String, Object> paramMap) {
		List<ChargingOrderPayLog> listObj = chargingOrderPayLogMapper.getAll(paramMap);
		Pager<ChargingOrderPayLog> pager = new Pager<ChargingOrderPayLog>();
		pager.setDatas(listObj);
		pager.setTotalCount(chargingOrderPayLogMapper.getAllNum(paramMap));
		return pager;
	}
	

}
