package com.iber.portal.service.car;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iber.portal.common.Pager;
import com.iber.portal.dao.car.CarLpnCheckMapper;
import com.iber.portal.exception.ServiceException;
import com.iber.portal.model.car.CarLpnCheck;

@Service
public class CarLpnCheckService {

	@Autowired
	private CarLpnCheckMapper carLpnCheckMapper;
	
	public int insertSelective(CarLpnCheck model) throws ServiceException {
		int len;
		try {
			len = carLpnCheckMapper.insertSelective(model);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		return len;
	}

	public int deleteByPrimaryKey(int id) throws ServiceException {
		int len;
		try {
			len = carLpnCheckMapper.deleteByPrimaryKey(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		return len;
	}

	public int updateByPrimaryKeySelective(CarLpnCheck model) throws ServiceException {
		int len;
		try {
			len = carLpnCheckMapper.updateByPrimaryKeySelective(model);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		return len;
	}

	public CarLpnCheck selectByPrimaryKey(int id) {
		return carLpnCheckMapper.selectByPrimaryKey(id);
	}
	
	public Pager<CarLpnCheck> getAll(Map<String, Object> paramMap) {
		List<CarLpnCheck> listObj = carLpnCheckMapper.getAll(paramMap);
		Pager<CarLpnCheck> pager = new Pager<CarLpnCheck>();
		pager.setDatas(listObj);
		pager.setTotalCount(carLpnCheckMapper.getAllNum(paramMap));
		return pager;
	}
}
