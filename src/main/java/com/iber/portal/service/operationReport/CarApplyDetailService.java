package com.iber.portal.service.operationReport;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iber.portal.dao.operationReport.CarApplyDetailMapper;
import com.iber.portal.exception.ServiceException;
import com.iber.portal.model.operationReport.CarApplyDetail;



/**
 * @describe 车辆使用明细
 * 
 * @author yangguiwu
 * @date 2016年03月31日
 */
@Service
public class CarApplyDetailService {

	@Autowired
	private CarApplyDetailMapper carApplyDetailMapper;


	public List<CarApplyDetail> selectByPrimaryKey(Map<String, Object> record)throws ServiceException{
	       try{
				return carApplyDetailMapper.selectByPrimaryKey(record);
			}catch(Exception e){
				e.printStackTrace();
				throw new ServiceException();
			}
		}

	public int selectByPrimaryKeyRecords(Map<String, Object> record)throws ServiceException{
	       try{
				return carApplyDetailMapper.selectByPrimaryKeyRecords(record);
			}catch(Exception e){
				e.printStackTrace();
				throw new ServiceException();
			}
		}

	public List<CarApplyDetail> selectByPrimaryKeyExcel(Map<String, Object> map) {

		return carApplyDetailMapper.selectByPrimaryKeyExcel(map);	
	}




}
