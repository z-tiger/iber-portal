package com.iber.portal.service.longRent;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iber.portal.common.Pager;
import com.iber.portal.dao.longRent.LongRentOrderMapper;
import com.iber.portal.exception.ServiceException;
import com.iber.portal.model.longRent.LongRentOrder;

@Service
public class LongRentOrderService {

	@Autowired
	private LongRentOrderMapper longRentOrderMapper;
	
	public int insertSelective(LongRentOrder model) throws ServiceException {
		int len;
		try {
			len = longRentOrderMapper.insertSelective(model);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		return len;
	}

	public int deleteByPrimaryKey(int id) throws ServiceException {
		int len;
		try {
			len = longRentOrderMapper.deleteByPrimaryKey(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		return len;
	}

	public int updateByPrimaryKeySelective(LongRentOrder model) throws ServiceException {
		int len;
		try {
			len = longRentOrderMapper.updateByPrimaryKeySelective(model);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		return len;
	}

	public LongRentOrder selectByPrimaryKey(int id) {
		return longRentOrderMapper.selectByPrimaryKey(id);
	}
	
	public Pager<LongRentOrder> getAll(Map<String, Object> paramMap) {
		List<LongRentOrder> listObj = longRentOrderMapper.getAll(paramMap);
		Pager<LongRentOrder> pager = new Pager<LongRentOrder>();
		pager.setDatas(listObj);
		pager.setTotalCount(longRentOrderMapper.getAllNum(paramMap));
		return pager;
	}

	public int bitchUpdateInvoiceStatus(List<String> list, int invoiceStatus) {
		return longRentOrderMapper.bitchUpdateInvoiceStatus(list, invoiceStatus);
	}
}
