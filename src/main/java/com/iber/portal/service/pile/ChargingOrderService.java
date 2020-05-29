package com.iber.portal.service.pile;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iber.portal.common.Pager;
import com.iber.portal.dao.pile.ChargingOrderMapper;
import com.iber.portal.exception.ServiceException;
import com.iber.portal.model.pile.ChargingOrder;

@Service
public class ChargingOrderService {

	@Autowired
	private ChargingOrderMapper chargingOrderMapper;
	
	public int insertSelective(ChargingOrder model) throws ServiceException {
		int len;
		try {
			len = chargingOrderMapper.insertSelective(model);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		return len;
	}

	public int deleteByPrimaryKey(int id) throws ServiceException {
		int len;
		try {
			len = chargingOrderMapper.deleteByPrimaryKey(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		return len;
	}

	public int updateByPrimaryKeySelective(ChargingOrder model) throws ServiceException {
		int len;
		try {
			len = chargingOrderMapper.updateByPrimaryKeySelective(model);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		return len;
	}

	public ChargingOrder selectByPrimaryKey(int id) {
		return chargingOrderMapper.selectByPrimaryKey(id);
	}
	
	public Pager<ChargingOrder> getAll(Map<String, Object> paramMap) {
		List<ChargingOrder> listObj = chargingOrderMapper.getAll(paramMap);
		Pager<ChargingOrder> pager = new Pager<ChargingOrder>();
		pager.setDatas(listObj);
		pager.setTotalCount(chargingOrderMapper.getAllNum(paramMap));
		return pager;
	}

}
