package com.iber.portal.service.dayRent;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iber.portal.common.Pager;
import com.iber.portal.dao.dayRent.DayRentPriceFestivalMapper;
import com.iber.portal.exception.ServiceException;
import com.iber.portal.model.dayRent.DayRentPriceFestival;

@Service
public class DayRentPriceFestivalService {

	@Autowired
	private DayRentPriceFestivalMapper dayRentPriceFestivalMapper;
	
	public int insertSelective(DayRentPriceFestival model) throws ServiceException {
		int len;
		try {
			len = dayRentPriceFestivalMapper.insertSelective(model);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		return len;
	}

	public int deleteByPrimaryKey(int id) throws ServiceException {
		int len;
		try {
			len = dayRentPriceFestivalMapper.deleteByPrimaryKey(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		return len;
	}

	public int updateByPrimaryKeySelective(DayRentPriceFestival model) throws ServiceException {
		int len;
		try {
			len = dayRentPriceFestivalMapper.updateByPrimaryKeySelective(model);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		return len;
	}

	public DayRentPriceFestival selectByPrimaryKey(int id) {
		return dayRentPriceFestivalMapper.selectByPrimaryKey(id);
	}
	
	public Pager<DayRentPriceFestival> getAll(Map<String, Object> paramMap) {
		List<DayRentPriceFestival> listObj = dayRentPriceFestivalMapper.getAll(paramMap);
		Pager<DayRentPriceFestival> pager = new Pager<DayRentPriceFestival>();
		pager.setDatas(listObj);
		pager.setTotalCount(dayRentPriceFestivalMapper.getAllNum(paramMap));
		return pager;
	}

	public List<DayRentPriceFestival> queryFestivalDays(
			Map<String, Object> paramMap) {
		return dayRentPriceFestivalMapper.queryFestivalDays(paramMap);
	}

}
