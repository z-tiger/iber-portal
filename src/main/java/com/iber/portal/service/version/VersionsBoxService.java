package com.iber.portal.service.version;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iber.portal.dao.versions.VersionsBoxMapper;
import com.iber.portal.exception.ServiceException;
import com.iber.portal.model.versions.VersionsBox;
import com.iber.portal.model.versions.VersionsCategory;

@Service
public class VersionsBoxService {

	@Autowired
	private VersionsBoxMapper versionsBoxMapper;
	
	
	public VersionsBox selectByPrimaryKey(Integer id){
		return versionsBoxMapper.selectByPrimaryKey(id) ;
	}
	
	

	public List<VersionsBox> selectByPrimaryInfo(Map<String, Object> record)throws ServiceException{
	       try{
				return versionsBoxMapper.selectByPrimaryInfo(record);
			}catch(Exception e){
				e.printStackTrace();
				throw new ServiceException();
			}
		}

	public int selectByPrimaryKeyRecords(Map<String, Object> record)throws ServiceException{
	       try{
				return versionsBoxMapper.selectByPrimaryKeyRecords(record);
			}catch(Exception e){
				e.printStackTrace();
				throw new ServiceException();
			}
		}

	public int deleteByPrimaryKey(int id) throws ServiceException {
		int len;
		try {
			len = versionsBoxMapper.deleteByPrimaryKey(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		return len;
	}

	public int insertSelective(VersionsBox model) throws ServiceException {
		int len;
		try {
			len = versionsBoxMapper.insertSelective(model);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		return len;
	}

	public int updateByPrimaryKeySelective(VersionsBox model) throws ServiceException {
		int len;
		try {
			len = versionsBoxMapper.updateByPrimaryKeySelective(model);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		return len;
	}

	public VersionsBox  selectByPrimaryId(int id) {
		return versionsBoxMapper.selectByPrimaryId(id);
	}






}
