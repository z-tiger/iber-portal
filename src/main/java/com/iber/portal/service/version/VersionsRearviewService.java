package com.iber.portal.service.version;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iber.portal.dao.versions.VersionsRearviewMapper;
import com.iber.portal.exception.ServiceException;
import com.iber.portal.model.versions.VersionsBox;
import com.iber.portal.model.versions.VersionsRearview;

@Service
public class VersionsRearviewService {

	@Autowired
	private VersionsRearviewMapper versionsRearviewMapper;
	
	public VersionsRearview selectByPrimaryKey(Integer id){
		return versionsRearviewMapper.selectByPrimaryKey(id) ;
	}
	
	public List<VersionsRearview> selectByPrimaryInfo(Map<String, Object> record)throws ServiceException{
	       try{
				return versionsRearviewMapper.selectByPrimaryInfo(record);
			}catch(Exception e){
				e.printStackTrace();
				throw new ServiceException();
			}
		}

	public int selectByPrimaryKeyRecords(Map<String, Object> record)throws ServiceException{
	       try{
				return versionsRearviewMapper.selectByPrimaryKeyRecords(record);
			}catch(Exception e){
				e.printStackTrace();
				throw new ServiceException();
			}
		}

	public int deleteByPrimaryKey(int id) throws ServiceException {
		int len;
		try {
			len = versionsRearviewMapper.deleteByPrimaryKey(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		return len;
	}

	public int insertSelective(VersionsRearview model) throws ServiceException {
		int len;
		try {
			len = versionsRearviewMapper.insertSelective(model);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		return len;
	}

	public int updateByPrimaryKeySelective(VersionsRearview model) throws ServiceException {
		int len;
		try {
			len = versionsRearviewMapper.updateByPrimaryKeySelective(model);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		return len;
	}

	public VersionsRearview  selectByPrimaryId(int id) {
		return versionsRearviewMapper.selectByPrimaryId(id);
	}
	
}
