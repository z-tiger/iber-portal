package com.iber.portal.service.sys;

import com.iber.portal.dao.sys.SysWarnInfoMapper;
import com.iber.portal.model.sys.SysWarnInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 系统预警信息service
 * lf
 * 2017-5-15 09:49:04
 */

@Service
public class SysWarnInfoService {

	@Autowired
    private SysWarnInfoMapper warnInfoMapper;

	public int insert(SysWarnInfo record){
		return warnInfoMapper.insert(record) ;
	}
	
}
