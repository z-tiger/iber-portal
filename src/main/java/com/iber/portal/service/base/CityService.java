package com.iber.portal.service.base;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iber.portal.dao.base.CityMapper;
import com.iber.portal.model.base.City;
import com.iber.portal.vo.city.CityVo;
import com.iber.portal.vo.city.ExtendCity;

@Service
public class CityService {

	@Autowired
	private CityMapper cityMapper;
	
	public List<CityVo> queryCityCarNum() {
		return cityMapper.queryCityCarNum() ;
	}
	    
	public List<CityVo> queryCityParkNum() {
		return cityMapper.queryCityParkNum() ;
	}
	
	public List<City> queryCityByCode(String cityCode){
		return cityMapper.queryCityByCode(cityCode) ;
	}
	
	public List<CityVo> queryCityPileParkNum(){
		return cityMapper.queryCityPileParkNum() ;
	}
	
	public List<CityVo> queryCityAreaByCode(String cityCode){
		return cityMapper.queryCityAreaByCode(cityCode) ;
	} 
	public List<City> selectAllCity(){
		return cityMapper.selectAllCity();
		
	}
	public List<City> selectAllCityList(){
		return cityMapper.selectAllCityList();
		
	}
	//保存城市信息
	public int updateByPrimaryKeySelective(City city){
		return cityMapper.updateByPrimaryKeySelective(city);
	}
	
	//查询城市信息
	
	public City selectByPrimaryKey(int id){
		return cityMapper.selectByPrimaryKey(id) ;
	}
	
	/**
	 * 查询网点所在城市的上一级城市，及其城市下的网点总数
	 * @return
	 * @author ouxx
	 * @date 2016-11-18 下午6:00:54
	 */
	public List<CityVo> queryProvinceList() {
		return cityMapper.queryProvinceList() ;
	}
	
	/**
	 * 根据上一级城市ID，查城市列表和网点数
	 * @param pid
	 * @return
	 * @author ouxx
	 * @date 2016-11-18 下午6:20:01
	 */
	public List<CityVo> queryCityList(Integer pid) {
		return cityMapper.queryCityList(pid) ;
	}
	
	/**
	 * 获取已开通的、有网点的城市
	 * @return
	 * @author ouxx
	 * @date 2016-11-21 下午4:27:03
	 */
	public List<CityVo> getOpenedCityList() {
		return cityMapper.getOpenedCityList() ;
	}
	
	public List<CityVo> queryCode() {
		return cityMapper.queryCode() ;
	}
	/**获取数据库中全国省下级城市信息*/
	public List<CityVo> cityAreaMapStatistics(String name) {
		return cityMapper.cityAreaMapStatistics(name) ;
	}
	
	/**获取数据库中全国省级信息*/
	public List<CityVo> provinceAreaMapStatistics(String name) {
		return cityMapper.provinceAreaMapStatistics(name) ;
	}
	
	/**获取数据库中全国地区车辆信息*/
	public List<CityVo> areaMapStatistics() {
		return cityMapper.areaMapStatistics() ;
	}
	
	/**获取数据库中全国车辆总数信息*/
	public List<CityVo> chinaCarStatistics() {
		return cityMapper.chinaCarStatistics() ;
	}
	
	/**通过城市cityCode获取城市名称*/
	public  String queryNameByCode(String cityCode) {
		return cityMapper.queryNameByCode(cityCode) ;
	}

	public List<ExtendCity> selectLocationByCityCode(String cityCode) {
		return cityMapper.selectLocationByCityCode(cityCode);
	}

	public String selectNameByCode(String cityCode) {
		return cityMapper.queryNameByCode(cityCode);
	}

	public List<ExtendCity> selectByLocation(Double latitude, Double longitude) {
		return cityMapper.selectByLocation(latitude, longitude);
	}

	public List<City> selectAllCityPagelist(HashMap<String, Object> map) {
		return cityMapper.selectAllCityPagelist(map);
	}

	public int selectAllCityPagelistRecords(HashMap<String, Object> map) {
		return cityMapper.selectAllCityPagelistRecords(map);
	}

	public List<City> selectCityByLayer(String layer) {
		return cityMapper.selectCityByLayer(layer);
	}

	public List<City> queryCityByParentCodeAndLayer(String parentCode, Integer layer) {
		return cityMapper.queryCityByParentCodeAndLayer(parentCode,layer);
	}
   
}
