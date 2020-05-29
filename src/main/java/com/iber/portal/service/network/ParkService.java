package com.iber.portal.service.network;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iber.portal.common.Pager;
import com.iber.portal.dao.base.ParkMapper;
import com.iber.portal.model.base.Park;
import com.iber.portal.model.dispatcher.Grid;
import com.iber.portal.vo.base.EasyUiTree;
import com.iber.portal.vo.charging.ChargingPileVo;
import com.iber.portal.vo.enterprise.EnterpriseVo;
import com.iber.portal.vo.park.ParkTotalVo;
import com.iber.portal.vo.park.ParkTreeVo;

@Service
public class ParkService {

	@Autowired
	private ParkMapper parkMapper;
	
	public List<ChargingPileVo> getCooperationParks(String parkName){
	    return parkMapper.getCooperationParks(parkName);
	}
	
	public List<Park> selectAllParks(Map<String, Object> map){
		return parkMapper.selectAllParks(map);
	}
	public int selectAllParksRecords(Map<String, Object> map){
		return parkMapper.selectAllParksRecords(map);
	}
	public int deleteByPrimaryKey(Integer id) {
		return parkMapper.deleteByPrimaryKey(id);
	}

	public int insert(Park record) {
		return parkMapper.insert(record);
	}

	public int insertSelective(Park record) {
		return parkMapper.insertSelective(record);
	}

	public Park selectByPrimaryKey(Integer id) {
		return parkMapper.selectByPrimaryKey(id);
	}

	public int updateByPrimaryKeySelective(Park record) {
		return parkMapper.updateByPrimaryKeySelective(record);
	}

	public int updateByPrimaryKey(Park record) {
		return parkMapper.updateByPrimaryKey(record);
	}

	public List<Park> selectAll(Map<String, Object> map){
		return parkMapper.selectAll(map);
	}

	public int selectAllRecords(Map<String, Object> map){
		return parkMapper.selectAllRecords(map);
	}
	
	public  List<Park> selectAllNotPage(){
		return parkMapper.selectAllNotPage();
	}
	
	public  List<Park> queryParkByCode(Map<String, Object> map){
		return parkMapper.queryParkByCode(map);
	}
	
	
	public List<Park> selectParkByCityCode(String cityCode){
		return parkMapper.selectParkByCityCode(cityCode);
	}
	
	public List<Park> queryParkByCodeAndType(Map<String, Object> map){
		return parkMapper.queryParkByCodeAndType(map) ;
	}
	
	public int queryParkByAreaCode(String areaCode){
		return parkMapper.queryParkByAreaCode(areaCode) ;
	} 
	
	public  List<Park> queryParkStatusByAreaCode(String areaCode){
		return parkMapper.queryParkStatusByAreaCode(areaCode) ;
	}
	
	public Pager<Park> getAll(Map<String, Object> paramMap) {
		List<Park> listObj = parkMapper.getAll(paramMap);
		Pager<Park> pager = new Pager<Park>();
		pager.setDatas(listObj);
		pager.setTotalCount(parkMapper.getAllNum(paramMap));
		return pager;
	}
	
	public Pager<Park> getAllParkName(Map<String, Object> paramMap) {
		List<Park> listObj = parkMapper.getAllParkName(paramMap);
		Pager<Park> pager = new Pager<Park>();
		pager.setDatas(listObj);
		pager.setTotalCount(parkMapper.getAllParkNameNum(paramMap));
		return pager;
	}
	
	public Pager<Park> getDataListPark(Map<String, Object> paramMap) {
		List<Park> listObj = parkMapper.getDataListPark(paramMap);
		Pager<Park> pager = new Pager<Park>();
		pager.setDatas(listObj);
		pager.setTotalCount(parkMapper.getDataListParkNum(paramMap));
		return pager;
	}
	
	public Pager<Park> getAllPark(Map<String, Object> paramMap) {
		List<Park> listObj = parkMapper.getAllPark(paramMap);
		Pager<Park> pager = new Pager<Park>();
		pager.setDatas(listObj);
		pager.setTotalCount(parkMapper.getAllParkNum(paramMap));
		return pager;
	}
	public int updateEnterpriseId(Map<String, Object> paramMap){
		return parkMapper.updateEnterpriseId(paramMap);
	}
	
	public int deleteEnterprise(Map<String, Object> paramMap){
		return parkMapper.deleteEnterprise(paramMap);
	}
	
	/**
	 * 根据cityCode和网点合作类型查询网点
	 * @param cooperationType
	 * @param cityCode
	 * @return
	 * @author ouxx
	 * @date 2016-11-21 下午5:22:25
	 */
	public List<Park> getStaionByCityAndCooperationType(Integer cooperationType, String cityCode){
		return parkMapper.getStaionByCityAndCooperationType(cooperationType, cityCode) ;
	}
	
	/**
	 * 根据cityCode和网点合作类型查询网点
	 * @param cooperationType
	 * @param cityCode
	 * @return
	 * @author ouxx
	 * @date 2016-11-21 下午5:22:25
	 */
	public List<ParkTreeVo> getStaionTreeByCityAndCooperationType(Integer cooperationType, String cityCode){
		return parkMapper.getStaionTreeByCityAndCooperationType(cooperationType, cityCode) ;
	}
	
	/**按网点类型（自有、合作）查询网点总数*/
	public List<ParkTotalVo> queryAllParkByType(String cityCode){
		return parkMapper.queryAllParkByType( cityCode) ;
	}
	
	/**按网点类型（自有、合作）查询网点总数--按省份划分*/
	public List<ParkTotalVo> queryAllProvinceParkByType(String cityCode){
		return parkMapper.queryAllProvinceParkByType( cityCode) ;
	}
	
	/**按网点服务类型（1s、2s...）查询网点总数*/
	public List<ParkTotalVo> queryAllProvinceParkByServiceType(String cityCode){
		return parkMapper.queryAllProvinceParkByServiceType( cityCode) ;
	}
	
	/**按网点服务类型（1s、2s...）按照省份划分查询网点总数*/
	public List<ParkTotalVo> queryAllParkByServiceType(String cityCode){
		return parkMapper.queryAllParkByServiceType( cityCode) ;
	}
	
	/**根据cityCode获取网点车位总数*/
	public List<ParkTotalVo> queryAllParkCarport(String cityCode){
		return parkMapper.queryAllParkCarport( cityCode) ;
	}
	
	/**根据cityCode获取网点车位总数--按照省份划分*/
	public List<ParkTotalVo> queryAllProvinceParkCarport(String cityCode){
		return parkMapper.queryAllProvinceParkCarport( cityCode) ;
	}
	
	/**根据cityCode分别统计快慢充的网点车位数*/
	public List<ParkTotalVo> queryAllParkCarportByConnectorType(String cityCode){
		return parkMapper.queryAllParkCarportByConnectorType( cityCode) ;
	}
	
	/**根据cityCode分别统计快慢充的网点车位数--按照省份划分*/
	public List<ParkTotalVo> queryAllProvinceParkCarportByConnectorType(String cityCode){
		return parkMapper.queryAllProvinceParkCarportByConnectorType( cityCode) ;
	}
	
	/**根据cityCode和车辆类型统计网点车位数*/
	public List<ParkTotalVo> queryAllParkCarportByCarType(String cityCode){
		return parkMapper.queryAllParkCarportByCarType( cityCode) ;
	}
	
	/**根据cityCode和车辆类型统计网点车位数--按照省份划分*/
	public List<ParkTotalVo> queryAllProvinceParkCarportByCarType(String cityCode){
		return parkMapper.queryAllProvinceParkCarportByCarType( cityCode) ;
	}
	
	/**根据cityCode获取网点个数的总计*/
	public List<ParkTotalVo> queryAllParkNums(String cityCode){
		return parkMapper.queryAllParkNums( cityCode) ;
	}
	
	/**根据cityCode获取网点个数的总计*/
	public List<ParkTotalVo> queryAllProvinceParkNums(String cityCode){
		return parkMapper.queryAllProvinceParkNums( cityCode) ;
	}
	
	/**根据cityCode查询省下级城市的网点个数的总计*/
	public List<ParkTotalVo> queryAllCityParkNums(String name){
		return parkMapper.queryAllCityParkNums( name) ;
	}
	
	/**查询城市下级的区县的网点个数的总计*/
	public List<ParkTotalVo> queryAllareaParkNums(String cityCode){
		return parkMapper.queryAllareaParkNums( cityCode) ;
	}
	
	public Pager<Park> queryPageDetail(Map<String, Object> paramMap) {
		List<Park> listObj = parkMapper.getParkDetail(paramMap);
		Pager<Park> pager = new Pager<Park>();
		pager.setDatas(listObj);
		pager.setTotalCount(getMyAllParkNum(paramMap));
		return pager;
	}

	private int getMyAllParkNum(Map<String, Object> paramMap) {
		return parkMapper.getMyAllParkNum(paramMap);
	}

	public List<Park> selectExceptExistRelation(String cityCode) {
		return parkMapper.selectExceptExistRelation(cityCode);
	}

	public List<ChargingPileVo> getAllChargingPileByParkId(String parkName) {
		return parkMapper.getAllChargingPileByParkId(parkName);
	}
    public List<Park> getTotalParks(Map<String, Object> map){
    	return parkMapper.getTotalParks(map);
    }
    
	public List<Park> selectTemporaryParks(String cityCode) {
		return parkMapper.selectTemporaryParks(cityCode);
	}

	public List<Park> queryAllParkExcludeTemporary() {
		return parkMapper.queryAllParkExcludeTemporary();
	}

	public int batchUpdateByParkId(List<Integer> ids) {
		return parkMapper.batchUpdateByParkId(ids);
	}

	public int batchUpdateByRunParkId(List<Integer> runIds) {
		return parkMapper.batchUpdateByRunParkId(runIds);
	}

	public Park selectParkByName(String parkName) {
		return parkMapper.selectParkByName(parkName);
	}
	
	public int queryByPidLpnCount(Integer id){
		
		return parkMapper.queryByPidLpnCount(id);
	}
	
	public int updateStatus(Integer status,Integer id){
		return parkMapper.updateStatus(status, id);
	}

    public Park queryParkByCityCodeAndParkName(String cityCode, String longRentChangeParkName) {
		return parkMapper.queryParkByCityCodeAndParkName(cityCode,longRentChangeParkName);
    }
}

