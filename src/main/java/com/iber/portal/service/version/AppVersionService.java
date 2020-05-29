package com.iber.portal.service.version;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iber.portal.dao.function.AppVersionMapper;
import com.iber.portal.exception.ServiceException;
import com.iber.portal.model.function.AppVersion;

@Service
public class AppVersionService {

	@Autowired
	private AppVersionMapper appVersionMapper;
	
	public AppVersion selectByPrimaryKey(Integer id){
		return appVersionMapper.selectByPrimaryKey(id) ;
	}
	
	public List<AppVersion> selectByAPPVersionList(Map<String, Object> map)throws ServiceException{
       try{
			return appVersionMapper.selectByAPPVersionList(map);
		}catch(Exception e){
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	public int selectByAPPVersionListRecords(Map<String, Object> map)throws ServiceException{
       try{
			return appVersionMapper.selectByAPPVersionListRecords(map);
		}catch(Exception e){
			e.printStackTrace();
			throw new ServiceException();
		}
	}

	public int deleteByPrimaryKey(int id) throws ServiceException {
		int len;
		try {
			len = appVersionMapper.deleteByPrimaryKey(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		return len;
	}

	public int insertSelective(AppVersion model) throws ServiceException {
		int len;
		try {
			len = appVersionMapper.insertSelective(model);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		return len;
	}

	public int updateByPrimaryKeySelective(AppVersion model) throws ServiceException {
		int len;
		try {
			len = appVersionMapper.updateByPrimaryKeySelective(model);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		return len;
	}

	public AppVersion selectByPrimaryId(int id) {
		return appVersionMapper.selectByPrimaryKey(id);
	}
}
