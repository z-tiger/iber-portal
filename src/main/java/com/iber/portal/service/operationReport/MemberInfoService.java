package com.iber.portal.service.operationReport;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iber.portal.dao.operationReport.MemberConsumptionMapper;
import com.iber.portal.dao.operationReport.MemberInfoMapper;
import com.iber.portal.exception.ServiceException;
import com.iber.portal.model.operationReport.MemberCapital;
import com.iber.portal.model.operationReport.MemberConsumption;
import com.iber.portal.model.operationReport.MemberInfo;



/**
 * @describe 会员信息表
 * 
 * @author yangguiwu
 * @date 2016年04月05日
 */
@Service
public class MemberInfoService {

	@Autowired
	private MemberInfoMapper memberInfoMapper;


	public List<MemberInfo> selectByPrimaryKey(Map<String, Object> record)throws ServiceException{
	       try{
				return memberInfoMapper.selectByPrimaryKey(record);
			}catch(Exception e){
				e.printStackTrace();
				throw new ServiceException();
			}
		}

	public int selectByPrimaryKeyRecords(Map<String, Object> record)throws ServiceException{
	       try{
				return memberInfoMapper.selectByPrimaryKeyRecords(record);
			}catch(Exception e){
				e.printStackTrace();
				throw new ServiceException();
			}
		}


	public List<MemberInfo> selectByPrimaryKeyExcel(Map<String, Object> record)throws ServiceException{
	       try{
	    	   return memberInfoMapper.selectByPrimaryKeyExcel(record);	
			}catch(Exception e){
				e.printStackTrace();
				throw new ServiceException();
			}
		}

}
