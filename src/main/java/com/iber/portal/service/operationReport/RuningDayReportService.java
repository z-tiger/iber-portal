package com.iber.portal.service.operationReport;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.iber.portal.dao.operationReport.MemberRegisterMapper;
import com.iber.portal.dao.operationReport.RuningDayReportMapper;
import com.iber.portal.exception.ServiceException;
import com.iber.portal.model.operationReport.MemberInfo;
import com.iber.portal.model.operationReport.MemberRegister;
import com.iber.portal.model.operationReport.RuningDayReport;



/**
 * @describe 会员注册表
 * 
 * @author yangguiwu
 * @date 2016年04月07日
 */
@Service
public class RuningDayReportService {

	@Autowired
	private RuningDayReportMapper runingDayReportMapper;

	public List<RuningDayReport> selectByPrimaryKey(Map<String, Object> record)throws ServiceException{
	       try{
				return runingDayReportMapper.selectByPrimaryKey(record);
			}catch(Exception e){
				e.printStackTrace();
				throw new ServiceException();
			}
		}

	
	public int selectByPrimaryKeyRecords(Map<String, Object> record)throws ServiceException{
	       try{
				return runingDayReportMapper.selectByPrimaryKeyRecords(record);
			}catch(Exception e){
				e.printStackTrace();
				throw new ServiceException();
			}
		}

}
