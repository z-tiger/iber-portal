package com.iber.portal.service.car;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iber.portal.dao.car.CarRunLogMapper;
import com.iber.portal.model.car.CarRunLog;
import com.iber.portal.vo.car.CarWarnVo;

@Service
public class CarRunLogService {

	@Autowired
	private CarRunLogMapper carRunLogMapper;
	
	public CarRunLog queryCarRunLogByLpn(String lpn) {
		Map<String,Object> map = new HashMap<String, Object>() ;
		map.put("lpn", lpn) ;
		return carRunLogMapper.queryCarRunLogByLpn(map) ;
	}
	
	public List<CarRunLog> queryCarRunLogByLpnAndOrderId(String lpn , String queryDateFrom,String queryDateTo,String orderId) {
		Map<String,Object> map = new HashMap<String, Object>() ;
		map.put("lpn", lpn) ;
		if(queryDateFrom.equals("")){
			queryDateFrom = null ;
		}
		if(queryDateTo.equals("")){
			queryDateTo = null ;
		}
		if(orderId.equals("")){
			orderId = null ;
		}
		map.put("queryDateFrom", queryDateFrom) ;
		map.put("queryDateTo", queryDateTo) ;
		map.put("orderId", orderId) ;
		if(queryDateFrom == null && queryDateTo == null && orderId==null){
			return carRunLogMapper.queryCarRunLogByLpnAndLimit(map) ;
		}else{
			return carRunLogMapper.queryCarRunLogByLpnAndOrderId(map) ;
		}
	} 
	
	public List<CarWarnVo> selectAll(Map<String, Object> map){
		return carRunLogMapper.selectAll(map);
	}
	    
	public int selectAllCarWarnVoRecords(Map<String, Object> map){
		return carRunLogMapper.selectAllCarWarnVoRecords(map);
	}
	
	public List<CarRunLog> queryCarRunLogByOrderId(String orderId){
		return carRunLogMapper.queryCarRunLogByOrderId(orderId) ;
	}
}
