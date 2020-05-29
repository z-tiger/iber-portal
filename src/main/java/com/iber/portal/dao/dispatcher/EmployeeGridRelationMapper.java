package com.iber.portal.dao.dispatcher;

import com.iber.portal.model.dispatcher.EmployeeGridRelation;
import com.iber.portal.model.dispatcher.Grid;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface EmployeeGridRelationMapper {
	
	int insert(EmployeeGridRelation record);
	
	int deleteByPrimaryKey(Integer id);
	
	int updateByPrimaryKey(EmployeeGridRelation record);
	
//	int updateByPrimaryKeySelective(EmployeeGridRelation record);
	
	EmployeeGridRelation selectByPrimaryKey(Integer id);
	
	int getAllNum(Map<String, Object> paramMap);
	
	List<EmployeeGridRelation> queryPageList(Map<String, Object> paramMap);

	List<EmployeeGridRelation> selectEmployeeGridRelationByGridId(Integer gridId);

	int batchDeleteEmployeeByGridId(Integer id);
	
	int deleteEmployeeByEmployeeId(Integer id);
	
	List<Grid>getAllGriddingInfo(@Param("cityCode")String code);
	Integer updateEmpGridRealtion(@Param("id")String id,@Param("isManager")String isManager);
}
