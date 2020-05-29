package com.iber.portal.service.insurance;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iber.portal.common.Pager;
import com.iber.portal.dao.insurance.InsuranceRecordMapper;
import com.iber.portal.exception.ServiceException;
import com.iber.portal.model.insurance.InsuranceRecord;

@Service
public class InsuranceRecordService {

	@Autowired
	private InsuranceRecordMapper insuranceRecordMapper;
	
	public int insertSelective(InsuranceRecord model) throws ServiceException {
		int len;
		try {
			len = insuranceRecordMapper.insertSelective(model);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		return len;
	}

	public int deleteByPrimaryKey(int id) throws ServiceException {
		int len;
		try {
			len = insuranceRecordMapper.deleteByPrimaryKey(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		return len;
	}

	public int updateByPrimaryKeySelective(InsuranceRecord model) throws ServiceException {
		int len;
		try {
			len = insuranceRecordMapper.updateByPrimaryKeySelective(model);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		return len;
	}

	public InsuranceRecord selectByPrimaryKey(int id) {
		return insuranceRecordMapper.selectByPrimaryKey(id);
	}
	
	public Pager<InsuranceRecord> getAll(Map<String, Object> paramMap) {
		List<InsuranceRecord> listObj = insuranceRecordMapper.getAll(paramMap);
		Pager<InsuranceRecord> pager = new Pager<InsuranceRecord>();
		pager.setDatas(listObj);
		pager.setTotalCount(insuranceRecordMapper.getAllNum(paramMap));
		return pager;
	}

}
