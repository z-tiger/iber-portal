package com.iber.portal.service.sys;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iber.portal.dao.sys.SysParamMapper;
import com.iber.portal.model.sys.SysParam;


@Service
public class SysParamService {

	@Autowired
	private SysParamMapper sysParamMapper;
	
	public SysParam selectByKey(String key){
		return sysParamMapper.selectByKey(key) ;
	}
	
	
}
