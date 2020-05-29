package com.iber.portal.service.warns;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iber.portal.dao.warns.WarnInfoStatusMapper;
import com.iber.portal.exception.ServiceException;
import com.iber.portal.model.warns.WarnInfoStatus;




/**
 * @describe 预警信息状态更改为已读
 * 
 * @author 
 * @date 2016年05月20日
 */
@Service
public class WarnInfoStatusService {

	@Autowired
	private WarnInfoStatusMapper warnInfoStatusMapper;
	
	

	public int insertSelective(WarnInfoStatus model) throws ServiceException {
		int len;
		try {
			len = warnInfoStatusMapper.insertSelective(model);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		return len;
	
	}

	public int updateByPrimaryKeySelective(WarnInfoStatus model) throws ServiceException {
		int len;
		try {
			len = warnInfoStatusMapper.updateByPrimaryKeySelective(model);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException();
		}
		return len;
	
	}

	public WarnInfoStatus selectByPrimaryId(int id) {
		return warnInfoStatusMapper.selectByPrimaryId(id);
		
	}


}
