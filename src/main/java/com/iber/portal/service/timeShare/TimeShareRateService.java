package com.iber.portal.service.timeShare;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iber.portal.common.Pager;
import com.iber.portal.dao.timeShare.TimeShareRateMapper;
import com.iber.portal.exception.ServiceException;
import com.iber.portal.model.timeShare.TimeShareRate;

@Service
public class TimeShareRateService {

	@Autowired
	private TimeShareRateMapper timeShareRateMapper;
	
	public int insertSelective(TimeShareRate model) throws ServiceException {
		int len;
		try {
			len = timeShareRateMapper.insertSelective(model);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		return len;
	}

	public int deleteByPrimaryKey(int id) throws ServiceException {
		int len;
		try {
			len = timeShareRateMapper.deleteByPrimaryKey(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		return len;
	}

	public int updateByPrimaryKeySelective(TimeShareRate model) throws ServiceException {
		int len;
		try {
			len = timeShareRateMapper.updateByPrimaryKeySelective(model);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		return len;
	}

	public TimeShareRate selectByPrimaryKey(int id) {
		return timeShareRateMapper.selectByPrimaryKey(id);
	}
	
	public List<TimeShareRate> getAllTimeShareRate() {
		return timeShareRateMapper.getAllTimeShareRate();
	}
	
	public Pager<TimeShareRate> getAll(Map<String, Object> paramMap) {
		List<TimeShareRate> listObj = timeShareRateMapper.getAll(paramMap);
		Pager<TimeShareRate> pager = new Pager<TimeShareRate>();
		pager.setDatas(listObj);
		pager.setTotalCount(timeShareRateMapper.getAllNum(paramMap));
		return pager;
	}
	
	/**
	 * 根据cityCode和carTypeId查车的租赁价格信息
	 * @param cityCode
	 * @param carTypeId
	 * @return
	 * @author ouxx
	 * @date 2016-9-27 上午11:29:18
	 */
	public TimeShareRate getByCityCodeAndCarTypeId(String cityCode, Integer carTypeId){
		return timeShareRateMapper.getByCityCodeAndCarTypeId(cityCode, carTypeId);
	}
	
}
