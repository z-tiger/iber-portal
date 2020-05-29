package com.iber.portal.service.dayRent;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iber.portal.common.Pager;
import com.iber.portal.dao.dayRent.DayRentOrderPayLogMapper;
import com.iber.portal.exception.ServiceException;
import com.iber.portal.model.dayRent.DayRentOrderPayLog;

@Service
public class DayRentOrderPayLogService {

	@Autowired
	private DayRentOrderPayLogMapper dayRentOrderPayLogMapper;
	
	public int insertSelective(DayRentOrderPayLog model) throws ServiceException {
		int len;
		try {
			len = dayRentOrderPayLogMapper.insertSelective(model);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		return len;
	}

	public int deleteByPrimaryKey(int id) throws ServiceException {
		int len;
		try {
			len = dayRentOrderPayLogMapper.deleteByPrimaryKey(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		return len;
	}

	public int updateByPrimaryKeySelective(DayRentOrderPayLog model) throws ServiceException {
		int len;
		try {
			len = dayRentOrderPayLogMapper.updateByPrimaryKeySelective(model);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		return len;
	}

	public DayRentOrderPayLog selectByPrimaryKey(int id) {
		return dayRentOrderPayLogMapper.selectByPrimaryKey(id);
	}
	
	public Pager<DayRentOrderPayLog> getAll(Map<String, Object> paramMap) {
		List<DayRentOrderPayLog> listObj = dayRentOrderPayLogMapper.getAll(paramMap);
		Pager<DayRentOrderPayLog> pager = new Pager<DayRentOrderPayLog>();
		pager.setDatas(listObj);
		pager.setTotalCount(dayRentOrderPayLogMapper.getAllNum(paramMap));
		return pager;
	}
	
	public List<DayRentOrderPayLog> getAllExcel(Map<String, Object> paramMap) {
		List<DayRentOrderPayLog> listObj = dayRentOrderPayLogMapper.getAllExcel(paramMap);
		return listObj;
	}
	
}
