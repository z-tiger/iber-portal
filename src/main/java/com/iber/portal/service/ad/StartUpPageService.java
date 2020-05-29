/**
 * 
 */
package com.iber.portal.service.ad;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iber.portal.common.Pager;
import com.iber.portal.dao.ad.StartUpPageMapper;
import com.iber.portal.exception.ServiceException;
import com.iber.portal.model.ad.StartUpPage;
//import com.sun.tools.doclets.formats.html.resources.standard;

/**
 * @PackageName：com.iber.portal.service.ad
 * @FileName:StartUpPageService.java
 * @Description:TODO
 * @Create:CUICHONG
 * @CreateDate:2017-6-27 下午2:21:08
 * @Update:CUICHONG
 * @UpdateDate:2017-6-27 下午2:21:08
 * @Version:1.0
 */
@Service
public class StartUpPageService {
	
	@Autowired
	private StartUpPageMapper startUpPageMapper;
	
	public int insertSelective(StartUpPage model) throws ServiceException {
		int len;
		try {
			len = startUpPageMapper.insertSelective(model);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		return len;
	}

	public int deleteByPrimaryKey(int id) throws ServiceException {
		int len;
		try {
			len = startUpPageMapper.deleteByPrimaryKey(id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		return len;
	}

	public int updateByPrimaryKeySelective(StartUpPage model) throws ServiceException {
		int len;
		try {
			len = startUpPageMapper.updateByPrimaryKeySelective(model);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		return len;
	}

	public StartUpPage selectByPrimaryKey(int id) {
		return startUpPageMapper.selectByPrimaryKey(id);
	}
	
	public Pager<StartUpPage> getAll(Map<String, Object> map){
		Pager<StartUpPage> pager = new Pager<StartUpPage>();
		List<StartUpPage> list = startUpPageMapper.getAll(map);
		pager.setDatas(list);
		pager.setTotalCount(startUpPageMapper.getAllNum(map));
		return pager;
	}
	public int getStartTime(String from) throws ServiceException{
		int len;
		try {
			len = startUpPageMapper.getStartTime(from);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		return len;
	}
	public int getEndTime(String to) throws ServiceException{
		int len;
		try {
			len = startUpPageMapper.getEndTime(to);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		return len;
	}

}
