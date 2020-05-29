package com.iber.portal.dao.overall;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.iber.portal.model.overall.OverallCar;


public interface OverallCarMapper {
	
    int deleteByPrimaryKey(Integer id);

    int insert(OverallCar record);

    int insertSelective(OverallCar record);

    OverallCar selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OverallCar record);

    int updateByPrimaryKey(OverallCar record);

	int insertBatch(List<OverallCar> carList);

	List<OverallCar> selectInitCarNum(@Param("currentTime") String currentTime);

	int getInitRecords();

	List<OverallCar> selectInitPayMoney(@Param("currentTime") String currentTime);

	List<OverallCar> selectInitOrderMoney(@Param("currentTime") String currentTime);

	List<OverallCar> selectInitRentMileage(@Param("currentTime") String currentTime);

	List<OverallCar> selectInitRentTimelong(@Param("currentTime") String currentTime);

	List<OverallCar> selectInitOrderMemberNum(@Param("currentTime") String currentTime);

	List<OverallCar> selectInitOrderCarNum(@Param("currentTime") String currentTime);

	List<OverallCar> selectInitOrderNum(@Param("currentTime") String currentTime);

	List<OverallCar> selectInitRentNum(@Param("currentTime") String currentTime);

	List<OverallCar> selectInitRentMemberNum(@Param("currentTime") String currentTime);

	List<OverallCar> selectInitRentCarNum(@Param("currentTime") String currentTime);

	List<OverallCar> selectInitCarRunNum(@Param("currentTime") String currentTime);

	List<OverallCar> selectInitRepairNum(@Param("currentTime") String currentTime);

	int getCarRecords();

}