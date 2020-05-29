package com.iber.portal.service.operationReport;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iber.portal.dao.operationReport.MemberConsumptionMapper;
import com.iber.portal.exception.ServiceException;
import com.iber.portal.model.operationReport.MemberCapital;
import com.iber.portal.model.operationReport.MemberConsumption;



/**
 * @describe 会员消费明细
 * 
 * @author yangguiwu
 * @date 2016年04月05日
 */
@Service
public class MemberConsumptionService {

	@Autowired
	private MemberConsumptionMapper memberConsumptionMapper;


	public List<MemberConsumption> selectByPrimaryKey(Map<String, Object> record)throws ServiceException{
	       try{
				return memberConsumptionMapper.selectByPrimaryKey(record);
			}catch(Exception e){
				e.printStackTrace();
				throw new ServiceException();
			}
		}

	public int selectByPrimaryKeyRecords(Map<String, Object> record)throws ServiceException{
	       try{
				return memberConsumptionMapper.selectByPrimaryKeyRecords(record);
			}catch(Exception e){
				e.printStackTrace();
				throw new ServiceException();
			}
		}

	public List<MemberConsumption> selectByPrimaryKeyExcel(Map<String, Object> map) {

		return memberConsumptionMapper.selectByPrimaryKeyExcel(map);	
	}




}
