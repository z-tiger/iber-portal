package com.iber.portal.dao.base;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.iber.portal.model.base.City;
import com.iber.portal.vo.city.CityVo;
import com.iber.portal.vo.city.ExtendCity;

public interface CityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(City record);

    int insertSelective(City record);

    City selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(City record);

    int updateByPrimaryKey(City record);
    
    List<City> selectAllCity();
    //查询所有城市列表
    List<City> selectAllCityList();
    
    List<CityVo> queryCityCarNum() ;
    
    List<CityVo> queryCityParkNum() ;
    
    List<City> queryCityByCode(String cityCode) ;
    
    List<CityVo> queryCityPileParkNum();
    
    List<CityVo> queryCityAreaByCode(String cityCode) ;
    
    /**
     * 查询网点所在城市的上一级城市，及其城市下的网点总数
     * @return
     * @author ouxx
     * @date 2016-11-18 下午6:00:39
     */
    List<CityVo> queryProvinceList();
    
    /**
     * 根据上一级城市ID，查城市列表和网点数
     * @param pid
     * @return
     * @author ouxx
     * @date 2016-11-18 下午6:19:12
     */
    List<CityVo> queryCityList(@Param("pid") Integer pid);
    
    /**
     * 获取已开通的、有网点的城市
     * @return
     * @author ouxx
     * @date 2016-11-21 下午4:25:27
     */
    List<CityVo> getOpenedCityList();
    
    List<CityVo> queryCode();
    /**获取数据库中全国省下级城市信息*/
    List<CityVo> cityAreaMapStatistics(@Param("name") String name);
    
    /**获取数据库中全国省下级城市信息*/
    List<CityVo> provinceAreaMapStatistics(@Param("name") String name);
    
    /**获取数据库中全国地区信息*/
    List<CityVo> areaMapStatistics();
    
    /**获取数据库中全国车辆总数信息*/
    List<CityVo> chinaCarStatistics();
    
    /**通过城市cityCode获取城市名称*/
    String queryNameByCode(String cityCode);
    /**
     * 通过城市编码获取城市
     * @param cityName
     * @return
     */
	List<ExtendCity> selectLocationByCityCode(String cityCode);
	/**
	 * 通过经纬度获得城市
	 * @param latitude
	 * @param longitude
	 * @return
	 */
	List<ExtendCity> selectByLocation(@Param("latitude")Double latitude, @Param("longitude")Double longitude);

	List<City> selectAllCityPagelist(HashMap<String, Object> map);

	int selectAllCityPagelistRecords(HashMap<String, Object> map);
	
	/**
	 * 查询车辆所属城市
	 * @return
	 * @author ouxx
	 * @date 2017-4-11 下午4:15:08
	 */
	List<City> queryAllCarCity();

	List<City> selectCityByLayer(@Param("layer")String layer);

	List<City> queryCityByParentCodeAndLayer(@Param("parentCode")String parentCode, @Param("layer")Integer layer);
    
}