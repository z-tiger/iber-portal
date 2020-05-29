package com.iber.portal.service.insurance;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iber.portal.common.Pager;
import com.iber.portal.dao.insurance.InsuranceMapper;
import com.iber.portal.exception.ServiceException;
import com.iber.portal.model.insurance.Insurance;

@Service
public class InsuranceService {

	@Autowired
	private InsuranceMapper insuranceMapper;
	
	public int insertSelective(Insurance model) throws ServiceException {
		int len;
		try {
			len = insuranceMapper.insertSelective(model);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		return len;
	}

	public int deleteByPrimaryKey(int id) throws ServiceException {
		int len;
		try {
			len = insuranceMapper.deleteByPrimaryKey(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		return len;
	}

	public int updateByPrimaryKeySelective(Insurance model) throws ServiceException {
		int len;
		try {
			len = insuranceMapper.updateByPrimaryKeySelective(model);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		return len;
	}

	public Insurance selectByPrimaryKey(int id) {
		return insuranceMapper.selectByPrimaryKey(id);
	}
	
	public Pager<Insurance> getAll(Map<String, Object> paramMap) {
		List<Insurance> listObj = insuranceMapper.getAll(paramMap);
		Pager<Insurance> pager = new Pager<Insurance>();
		pager.setDatas(listObj);
		pager.setTotalCount(insuranceMapper.getAllNum(paramMap));
		return pager;
	}

	public List<Insurance> selectByNextMonthDate(Map<String, Object> paramMap) {
		return insuranceMapper.selectByNextMonthDate(paramMap);
	}

	public List<Insurance> selectByLpnAndType(Map<String, Object> paramMap) {
		return insuranceMapper.selectByLpnAndType(paramMap);
	}

	public List<Insurance> selectByLpn(Map<String, Object> paramMap) {
		return insuranceMapper.selectByLpn(paramMap);
	}

	public List<Insurance> selectLpn() {
		return insuranceMapper.selectLpn();
	}

}
