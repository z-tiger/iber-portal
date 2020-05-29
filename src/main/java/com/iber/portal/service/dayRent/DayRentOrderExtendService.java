package com.iber.portal.service.dayRent;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iber.portal.dao.dayRent.DayRentOrderExtendMapper;
import com.iber.portal.exception.ServiceException;
import com.iber.portal.model.dayRent.DayRentOrderExtend;
import com.iber.portal.vo.dayRent.DayRentOrderAndExtendVo;

@Service
public class DayRentOrderExtendService {

	@Autowired
	private DayRentOrderExtendMapper dayRentOrderExtendMapper;
	
	public int insertSelective(DayRentOrderExtend model) throws ServiceException {
		int len;
		try {
			len = dayRentOrderExtendMapper.insertSelective(model);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		return len;
	}

	public int deleteByPrimaryKey(int id) throws ServiceException {
		int len;
		try {
			len = dayRentOrderExtendMapper.deleteByPrimaryKey(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		return len;
	}

	public int updateByPrimaryKeySelective(DayRentOrderExtend model) throws ServiceException {
		int len;
		try {
			len = dayRentOrderExtendMapper.updateByPrimaryKeySelective(model);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		return len;
	}

	public DayRentOrderExtend selectByPrimaryKey(int id) {
		return dayRentOrderExtendMapper.selectByPrimaryKey(id);
	}
	
	public List<DayRentOrderExtend> queryDayRentOrderExtendByOrderIdType(Map<String,Object> map){
		return dayRentOrderExtendMapper.queryDayRentOrderExtendByOrderIdType(map) ;
	}
	
	public List<DayRentOrderAndExtendVo> queryFinishByOrderId(String orderId, Date lastCountTime){
		return dayRentOrderExtendMapper.queryFinishByOrderId(orderId, lastCountTime);
	}

	public List<DayRentOrderExtend> selectByOrderId(String objId) {
		return dayRentOrderExtendMapper.selectByOrderId(objId);
	}
}
