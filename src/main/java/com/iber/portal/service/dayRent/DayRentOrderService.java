package com.iber.portal.service.dayRent;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iber.portal.common.Pager;
import com.iber.portal.dao.dayRent.DayRentOrderMapper;
import com.iber.portal.exception.ServiceException;
import com.iber.portal.model.dayRent.DayRentOrder;
import com.iber.portal.vo.dayRent.DayRentOrderVo;

@Service
public class DayRentOrderService {

	@Autowired
	private DayRentOrderMapper dayRentOrderMapper;
	
	public int insertSelective(DayRentOrder model) throws ServiceException {
		int len;
		try {
			len = dayRentOrderMapper.insertSelective(model);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		return len;
	}

	public int deleteByPrimaryKey(int id) throws ServiceException {
		int len;
		try {
			len = dayRentOrderMapper.deleteByPrimaryKey(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		return len;
	}

	public int updateByPrimaryKeySelective(DayRentOrder model) throws ServiceException {
		int len;
		try {
			len = dayRentOrderMapper.updateByPrimaryKeySelective(model);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		return len;
	}

	public DayRentOrder selectByPrimaryKey(int id) {
		return dayRentOrderMapper.selectByPrimaryKey(id);
	}
	
	public Pager<DayRentOrderVo> getAll(Map<String, Object> paramMap) {
		List<DayRentOrderVo> listObj = dayRentOrderMapper.getAll(paramMap);
		Pager<DayRentOrderVo> pager = new Pager<DayRentOrderVo>();
		pager.setDatas(listObj);
		pager.setTotalCount(dayRentOrderMapper.getAllNum(paramMap));
		return pager;
	}
	
	public DayRentOrder queryDayRentOrder(String orderId){
		return dayRentOrderMapper.queryDayRentOrder(orderId) ;
	}
	
	public Pager<DayRentOrder> getAllFinish(Map<String, Object> paramMap) {
		List<DayRentOrder> listObj = dayRentOrderMapper.getAllFinish(paramMap);
		Pager<DayRentOrder> pager = new Pager<DayRentOrder>();
		pager.setDatas(listObj);
		pager.setTotalCount(dayRentOrderMapper.getAllFinishNum(paramMap));
		return pager;
	}
	
	/**
	 * 查询已完成的订单
	 * @param orderId
	 * @return
	 * @author ouxx
	 * @date 2016-11-30 下午3:50:20
	 */
	public DayRentOrder queryFinishByOrderId(String orderId){
		return dayRentOrderMapper.queryFinishByOrderId(orderId) ;
	}
}
