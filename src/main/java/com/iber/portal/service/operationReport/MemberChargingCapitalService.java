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
 * @describe 会员充电消费明细
 * 
 * @author yangguiwu
 * @date 2016年04月05日
 */
@Service
public class MemberChargingCapitalService {

	@Autowired
	private MemberConsumptionMapper memberConsumptionMapper;


	public List<MemberConsumption> selectChargingCapitalByPrimaryKey(Map<String, Object> record)throws ServiceException{
	       try{
				return memberConsumptionMapper.selectChargingCapitalByPrimaryKey(record);
			}catch(Exception e){
				e.printStackTrace();
				throw new ServiceException();
			}
		}

	public int selectChargingCapitalByPrimaryKeyRecords(Map<String, Object> record)throws ServiceException{
	       try{
				return memberConsumptionMapper.selectChargingCapitalByPrimaryKeyRecords(record);
			}catch(Exception e){
				e.printStackTrace();
				throw new ServiceException();
			}
		}

	public List<MemberConsumption> selectChargingCapitalByPrimaryKeyExcel(Map<String, Object> map) {

		return memberConsumptionMapper.selectChargingCapitalByPrimaryKeyExcel(map);	
	}

	public int selectChargingCapitalCountByPrimaryKey(HashMap<String, Object> record) throws ServiceException {
		try{
			return memberConsumptionMapper.selectChargingCapitalCountByPrimaryKey(record);
		}catch(Exception e){
			e.printStackTrace();
			throw new ServiceException();
		}
	}




}
