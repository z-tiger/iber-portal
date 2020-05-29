package com.iber.portal.service.car;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.iber.portal.model.car.CarRun;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iber.portal.dao.car.CarRepairMapper;
import com.iber.portal.dao.car.CarRunMapper;
import com.iber.portal.model.car.CarRepair;

@Service
public class CarRepairService {

	private final static Logger LOGGER = LoggerFactory.getLogger(CarRepairService.class);

	@Autowired
	private CarRepairMapper carRepairMapper;
	
	@Autowired
	private CarRunMapper carRunMapper;

	public int deleteByPrimaryKey(Integer id) {
		return carRepairMapper.deleteByPrimaryKey(id);
	}

	public int insert(CarRepair record) {
		return carRepairMapper.insert(record);
	}

	public int insertSelective(CarRepair record) {
		return carRepairMapper.insertSelective(record);
	}

	public CarRepair selectByPrimaryKey(Integer id) {
		return carRepairMapper.selectByPrimaryKey(id);
	}

	public int updateByPrimaryKeySelective(CarRepair record) {
		//更新车辆状态
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("lpn", record.getLpn());
		/*if(record.getStatus().equals("1")){
			map.put("status", "repair");
		}
		if(record.getStatus().equals("2")){
			map.put("status", "maintain");
		}
		carRunMapper.updateCarRunStatusByLpn(map);*/
		return carRepairMapper.updateByPrimaryKeySelective(record);
	}

	public int updateByPrimaryKey(CarRepair record) {
		return carRepairMapper.updateByPrimaryKey(record);
	}
	
	@Transactional(rollbackFor=Exception.class)
	public int delCarRepairInfo(int id, String lpn){
		CarRepair carRepair = carRepairMapper.selectByPrimaryKey(id);
		if(!carRepair.getStatus().equals("0")){
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("lpn", lpn);
			map.put("status", "empty");
			int r1 = carRunMapper.updateCarRunStatusByLpn(map);
		}
		int r2 = carRepairMapper.deleteByPrimaryKey(id);
		return r2;
	}
	
	@Transactional(rollbackFor=Exception.class)
	public int saveCarRepairInfo(CarRepair carRepair,String carRunStatus,Double restBattery){
		//更新车辆状态
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("lpn", carRepair.getLpn().replace("•", ""));
		if(carRepair.getStatus().equals("1")){
			map.put("status", "repair");
		}
		if(carRepair.getStatus().equals("2")){
			map.put("status", "maintain");
		}
		if (carRepair.getStatus().equals("3")) {//如果车辆为补电状态
			map.put("status", "charging");
		}
		System.err.println(JSON.toJSONString(map));
		int r1 = carRunMapper.updateCarRunStatusByLpn(map);
		// 如果是非空闲状态的车下线,则需要将x_car_repair的记录结束,再上载新的车辆下线维修信息
		if("repair".equals(carRunStatus)){
			map.put("status", 1);
		}
		if("charging".equals(carRunStatus)){
			map.put("status", 3);
		}
		if("maintain".equals(carRunStatus)){
			map.put("status", 2);
		}
		System.err.println(JSON.toJSONString(map));
		List<CarRepair> lists =carRepairMapper.queryCarRepairByLpnStatus(map);
		for (CarRepair repair : lists) {
			// 恢复运营
			repair.setStatus("0");
			repair.setEndTime(new Date());
			repair.setUpdateTime(new Date());
			repair.setUpdateUser(carRepair.getUpdateUser());
			repair.setEndRestBattery(restBattery);
			carRepairMapper.updateByPrimaryKey(repair);
		}
		// 开始电量
		carRepair.setStartRestBattery(restBattery);
		int r2 = carRepairMapper.insertSelective(carRepair);
		if(r1 > 0 && r2 > 0){
			return 1;
		}else{
			return -1;
		}
	}
	
	@Transactional(rollbackFor=Exception.class)
	public int carResume(int id, String user, String onlineUserName, String userPhone, String onlineReason){
		int r1=1;
		CarRepair carRepair = carRepairMapper.selectByPrimaryKey(id);
		carRepair.setEndTime(new Date());
		carRepair.setUpdateTime(new Date());
		carRepair.setUpdateUser(user);
		carRepair.setStatus("0");
		carRepair.setRecoverReason(onlineReason);
		carRepair.setRecoverUser(onlineUserName);
		carRepair.setRecoverUserPhone(userPhone);
		
		/**根据车牌查询车辆状态 如果不是维修和维护以及补电状态， 恢复运营 就不修改车辆状态*/
		final String lpn = carRepair.getLpn();
		String carStatus =carRunMapper.selectStatusByLpn(lpn);
		if("maintain".equals(carStatus)||"repair".equals(carStatus)||"charging".equals(carStatus)){
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("lpn", lpn);
			map.put("status", "empty");
			map.put("preOffline", "0");
			r1= carRunMapper.updateCarRunStatusByLpn(map);
		}

		final CarRun carRun = carRunMapper.queryCarRun(lpn);
		carRepair.setEndRestBattery(carRun.getRestBattery());
		int r2 = carRepairMapper.updateByPrimaryKeySelective(carRepair);
		if(r1 > 0 && r2 > 0){
			return 1;
		}else{
			return -1;
		}
	}
	public List<CarRepair> selectAll(Map<String, Object> map){
		return carRepairMapper.selectAll(map);
	}
	    
	public   int selectAllRecords(Map<String, Object> map){
		return carRepairMapper.selectAllRecords(map);
	}
	
	public CarRepair queryCarRepairByLpnStatus(String lpn,String status){
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("lpn", lpn);
		map.put("status", status);
		List<CarRepair> carRepairList = carRepairMapper.queryCarRepairByLpnStatus(map) ;
		if(carRepairList.size() > 0){
			return carRepairList.get(0) ;
		}else{
			return null ;
		}
	}
	
	public int saveCarRepairInfoByOther(CarRepair carRepair){
		//插入车辆维修信息
		int r2 = carRepairMapper.insertSelective(carRepair);
		if( r2 > 0){
			return 1;
		}else{
			return -1;
		}
	}
	public String queryCarOrderId(String lpn){
	   return carRunMapper.queryCarOrderId(lpn);	
	}
	
	public List<CarRepair> selectTotalRepairsInfos(Map<String, Object> map){
		return carRepairMapper.selectTotalRepairsInfos(map);
	}
	    
	public int selectTotalRepairsInfoRecords(Map<String, Object> map){
		return carRepairMapper.selectTotalRepairsInfoRecords(map);
	}
	/**
	 * 车辆关闭：对应的维修列表里的记录为已维修,已维护String status,@Param("statusCache") String statusCache
	 */
	public int updateStatusCarBylpn(String lpn, String status, String statusCache){
		return carRepairMapper.updateStatusCarBylpn(lpn,status, statusCache);
	}
}
