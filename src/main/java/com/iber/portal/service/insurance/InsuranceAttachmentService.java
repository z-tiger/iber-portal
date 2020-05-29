package com.iber.portal.service.insurance;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iber.portal.dao.insurance.InsuranceAttachmentMapper;
import com.iber.portal.exception.ServiceException;
import com.iber.portal.model.insurance.InsuranceAttachment;

@Service
public class InsuranceAttachmentService {

	@Autowired
	private InsuranceAttachmentMapper insuranceAttachmentMapper;
	
	public int insertSelective(InsuranceAttachment model) throws ServiceException {
		int len;
		try {
			len = insuranceAttachmentMapper.insertSelective(model);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		return len;
	}

	public int deleteByPrimaryKey(int id) throws ServiceException {
		int len;
		try {
			len = insuranceAttachmentMapper.deleteByPrimaryKey(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		return len;
	}

	public int updateByPrimaryKeySelective(InsuranceAttachment model) throws ServiceException {
		int len;
		try {
			len = insuranceAttachmentMapper.updateByPrimaryKeySelective(model);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		return len;
	}

	public InsuranceAttachment selectByPrimaryKey(int id) {
		return insuranceAttachmentMapper.selectByPrimaryKey(id);
	}

	public List<InsuranceAttachment> getAllAttachment(Map<String, Object> paramMap) {
		return insuranceAttachmentMapper.getAllAttachment(paramMap);
	}

}
