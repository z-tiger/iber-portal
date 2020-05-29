package com.iber.portal.service.version;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iber.portal.dao.versions.VersionsCategoryMapper;
import com.iber.portal.exception.ServiceException;
import com.iber.portal.model.versions.VersionsCategory;





/**
 * @describe 版本类型管理
 * 
 * @author yangguiwu
 * @date 2016年04月15日
 */
@Service
public class VersionsCategoryService {

	@Autowired
	private VersionsCategoryMapper versionsCategoryMapper;
	
	


	public List<VersionsCategory> selectByPrimaryKey(Map<String, Object> record)throws ServiceException{
	       try{
				return versionsCategoryMapper.selectByPrimaryKey(record);
			}catch(Exception e){
				e.printStackTrace();
				throw new ServiceException();
			}
		}

	public int selectByPrimaryKeyRecords(Map<String, Object> record)throws ServiceException{
	       try{
				return versionsCategoryMapper.selectByPrimaryKeyRecords(record);
			}catch(Exception e){
				e.printStackTrace();
				throw new ServiceException();
			}
		}

	public int deleteByPrimaryKey(int id) throws ServiceException {
		int len;
		try {
			len = versionsCategoryMapper.deleteByPrimaryKey(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		return len;
	}

	public int insertSelective(VersionsCategory model) throws ServiceException {
		int len;
		try {
			len = versionsCategoryMapper.insertSelective(model);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		return len;
	}

	public int updateByPrimaryKeySelective(VersionsCategory model) throws ServiceException {
		int len;
		try {
			len = versionsCategoryMapper.updateByPrimaryKeySelective(model);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		return len;
	}

	public VersionsCategory selectByPrimaryId(int id) {
		return versionsCategoryMapper.selectByPrimaryId(id);
	}
	
	public List<VersionsCategory> selectByPrimaryCategory(String id) {
		Map<String,String> map=new HashMap<String,String>();
		map.put("id", id);
		return versionsCategoryMapper.selectByPrimaryCategory(map);
	}	


}
