package com.iber.portal.service.dayRent;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iber.portal.common.Pager;
import com.iber.portal.dao.dayRent.DayRentPriceDetailMapper;
import com.iber.portal.dao.dayRent.DayRentPriceMapper;
import com.iber.portal.exception.ServiceException;
import com.iber.portal.model.dayRent.DayRentPrice;
import com.iber.portal.model.dayRent.DayRentPriceDetail;

@Service
public class DayRentPriceDetailService {

	@Autowired
	private DayRentPriceDetailMapper dayRentPriceDetailMapper;
	
	public int insertSelective(DayRentPriceDetail model) throws ServiceException {
		int len;
		try {
			len = dayRentPriceDetailMapper.insertSelective(model);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		return len;
	}

	public int deleteByPrimaryKey(int id) throws ServiceException {
		int len;
		try {
			len = dayRentPriceDetailMapper.deleteByPrimaryKey(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		return len;
	}

	public int updateByPrimaryKeySelective(DayRentPriceDetail model) throws ServiceException {
		int len;
		try {
			len = dayRentPriceDetailMapper.updateByPrimaryKeySelective(model);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		return len;
	}

	public DayRentPriceDetail selectByPrimaryKey(int id) {
		return dayRentPriceDetailMapper.selectByPrimaryKey(id);
	}
	
	public Pager<DayRentPriceDetail> getAll(Map<String, Object> paramMap) {
		List<DayRentPriceDetail> listObj = dayRentPriceDetailMapper.getAll(paramMap);
		Pager<DayRentPriceDetail> pager = new Pager<DayRentPriceDetail>();
		pager.setDatas(listObj);
		pager.setTotalCount(dayRentPriceDetailMapper.getAllNum(paramMap));
		return pager;
	}

	public DayRentPriceDetail selectBycurrDate(Map<String, Object> currMap) {
		return dayRentPriceDetailMapper.selectBycurrDate(currMap);
	}

	public List<DayRentPriceDetail> queryDayRentPriceDetails(
			Map<String, Object> paramMap) {
		return dayRentPriceDetailMapper.queryDayRentPriceDetails(paramMap);
	}

	public List<DayRentPriceDetail> getByCityCodeAndCarTypeId(String cityCode,
			Integer carTypeId, Long date) {
		return dayRentPriceDetailMapper.getByCityCodeAndCarTypeId(cityCode, carTypeId, date);
	}

}
