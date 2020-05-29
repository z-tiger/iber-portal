package com.iber.portal.dao.charging;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.iber.portal.model.charging.CarBrand;

public interface CarBrandMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CarBrand record);

    int insertSelective(CarBrand record);

    CarBrand selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CarBrand record);

    int updateByPrimaryKey(CarBrand record);

	List<CarBrand> getAll(Map<String, Object> paramMap);

	int getAllNum(Map<String, Object> paramMap);
	
	List<CarBrand> getAllBrand(Map<String, Object> paramMap);
	
	int getAllBrandNum(Map<String, Object> paramMap);
	
	/**充电设备功能：设置支持车辆品牌*/
	int selectAllBrand(@Param("equipmentId")Integer equipmentId,@Param("brandName")String brandName);
	
	List<CarBrand> selectNotSupportBrand(@Param("equipmentId")Integer equipmentId,@Param("from")Integer from,@Param("to")Integer to,@Param("brandName")String brandName);
	
	List<CarBrand>getAllRecords(Map<String, Object> paramMap);
	int getAllRecordsSum();
	
	/**
	 * 查询车辆品牌列表
	 * @return
	 * @author ouxx
	 * @date 2016-12-6 下午4:56:57
	 */
	List<CarBrand> getBrandNameList();
}