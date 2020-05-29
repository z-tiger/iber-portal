package com.iber.portal.service.version;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iber.portal.dao.versions.VersionsEmployeeManagerMapper;
import com.iber.portal.exception.ServiceException;
import com.iber.portal.model.versions.VersionsEmployee;

@Service
public class VersionsEmployeeManagerService {
	@Autowired
	private VersionsEmployeeManagerMapper versionsEmployeeManagerMapper;

	public List<VersionsEmployee> selectAll(HashMap<String, Object> record)
			throws ServiceException {
		try {
			return versionsEmployeeManagerMapper.selectAll(record);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	public VersionsEmployee selectByPrimaryId(int id) throws ServiceException {
		try {
			return versionsEmployeeManagerMapper.selectByPrimaryId(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	public int updateByPrimaryKeySelective(VersionsEmployee currObj)throws ServiceException {
		try {
			return versionsEmployeeManagerMapper
					.updateByPrimaryKeySelective(currObj);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	public int insertSelective(VersionsEmployee obj) throws ServiceException {
		try {
			return versionsEmployeeManagerMapper.insertSelective(obj);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	public int deleteByPrimaryKey(int id) throws ServiceException {
		try {
			return versionsEmployeeManagerMapper.deleteByPrimaryKey(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	public int selectByPrimaryKeyRecords(HashMap<String, Object> record)
			throws ServiceException {
		try {
			return versionsEmployeeManagerMapper
					.selectByPrimaryKeyRecords(record);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
	}

}
