package com.iber.portal.service.dayRent;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iber.portal.common.Pager;
import com.iber.portal.dao.dayRent.DayRentPriceMapper;
import com.iber.portal.exception.ServiceException;
import com.iber.portal.model.dayRent.DayRentPrice;
import com.iber.portal.model.timeShare.TimeShareRate;

@Service
public class DayRentPriceService {

	@Autowired
	private DayRentPriceMapper dayRentPriceMapper;
	
	public int insertSelective(DayRentPrice model) throws ServiceException {
		int len;
		try {
			len = dayRentPriceMapper.insertSelective(model);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		return len;
	}

	public int deleteByPrimaryKey(int id) throws ServiceException {
		int len;
		try {
			len = dayRentPriceMapper.deleteByPrimaryKey(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		return len;
	}

	public int updateByPrimaryKeySelective(DayRentPrice model) throws ServiceException {
		int len;
		try {
			len = dayRentPriceMapper.updateByPrimaryKeySelective(model);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		return len;
	}

	public DayRentPrice selectByPrimaryKey(int id) {
		return dayRentPriceMapper.selectByPrimaryKey(id);
	}
	
	public Pager<DayRentPrice> getAll(Map<String, Object> paramMap) {
		List<DayRentPrice> listObj = dayRentPriceMapper.getAll(paramMap);
		Pager<DayRentPrice> pager = new Pager<DayRentPrice>();
		pager.setDatas(listObj);
		pager.setTotalCount(dayRentPriceMapper.getAllNum(paramMap));
		return pager;
	}

}
