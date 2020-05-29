package com.iber.portal.service.operationReport;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iber.portal.dao.operationReport.CarMonthlyOperationMapper;
import com.iber.portal.dao.operationReport.MemberConsumptionMapper;
import com.iber.portal.exception.ServiceException;
import com.iber.portal.model.operationReport.CarMonthlyOperation;
import com.iber.portal.model.operationReport.MemberCapital;
import com.iber.portal.model.operationReport.MemberConsumption;



/**
 * @describe 车辆运营月报表
 * 
 * @author yangguiwu
 * @date 2016年04月06日
 */
@Service
public class CarMonthlyOperationService {

	@Autowired
	private CarMonthlyOperationMapper carMonthlyOperationMapper;


	public List<CarMonthlyOperation> selectByPrimaryKey(Map<String, Object> record)throws ServiceException{
	       try{
				return carMonthlyOperationMapper.selectByPrimaryKey(record);
			}catch(Exception e){
				e.printStackTrace();
				throw new ServiceException();
			}
		}

	public int selectByPrimaryKeyRecords(Map<String, Object> record)throws ServiceException{
	       try{
				return carMonthlyOperationMapper.selectByPrimaryKeyRecords(record);
			}catch(Exception e){
				e.printStackTrace();
				throw new ServiceException();
			}
		}

	public List<CarMonthlyOperation> selectByPrimaryKeyExcel(Map<String, String> map) {

		return carMonthlyOperationMapper.selectByPrimaryKeyExcel(map);	
	}




}
