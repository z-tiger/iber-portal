package com.iber.portal.service.enterprise;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iber.portal.common.Pager;
import com.iber.portal.dao.enterprise.EnterpriseLevelMapper;
import com.iber.portal.exception.ServiceException;
import com.iber.portal.model.enterprise.EnterpriseLevel;

@Service
public class EnterpriseLevelService {

	@Autowired
	private EnterpriseLevelMapper enterpriseLevelMapper;
	
	public int insertSelective(EnterpriseLevel model) throws ServiceException {
		int len;
		try {
			len = enterpriseLevelMapper.insertSelective(model);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		return len;
	}

	public int deleteByPrimaryKey(int id) throws ServiceException {
		int len;
		try {
			len = enterpriseLevelMapper.deleteByPrimaryKey(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		return len;
	}

	public int updateByPrimaryKeySelective(EnterpriseLevel model) throws ServiceException {
		int len;
		try {
			len = enterpriseLevelMapper.updateByPrimaryKeySelective(model);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		return len;
	}

	public EnterpriseLevel selectByPrimaryKey(int id) {
		return enterpriseLevelMapper.selectByPrimaryKey(id);
	}
	
	public List<EnterpriseLevel> getAllEnterpriseLevel() {
		return enterpriseLevelMapper.getAllEnterpriseLevel();
	}
	
	public Pager<EnterpriseLevel> getAll(Map<String, Object> paramMap) {
		List<EnterpriseLevel> listObj = enterpriseLevelMapper.getAll(paramMap);
		Pager<EnterpriseLevel> pager = new Pager<EnterpriseLevel>();
		pager.setDatas(listObj);
		pager.setTotalCount(enterpriseLevelMapper.getAllNum(paramMap));
		return pager;
	}
}
