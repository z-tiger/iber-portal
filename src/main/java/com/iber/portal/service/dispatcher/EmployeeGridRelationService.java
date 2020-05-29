package com.iber.portal.service.dispatcher;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iber.portal.dao.dispatcher.EmployeeGridRelationMapper;
import com.iber.portal.model.dispatcher.EmployeeGridRelation;
import com.iber.portal.model.dispatcher.Grid;

@Service
public class EmployeeGridRelationService {

	@Autowired
	private EmployeeGridRelationMapper employeeGridRelationMapper;
	
	public List<EmployeeGridRelation> selectEmployeeGridRelationByGridId(Integer gridId){
		return employeeGridRelationMapper.selectEmployeeGridRelationByGridId(gridId);
	}
	
	public int insert(EmployeeGridRelation relation){
		return employeeGridRelationMapper.insert(relation);
	}

	public int batchDeleteEmployeeByGridId(Integer id) {
		return employeeGridRelationMapper.batchDeleteEmployeeByGridId(id);
	}

	public int deleteEmployeeByEmployeeId(Integer id) {
		return employeeGridRelationMapper.deleteEmployeeByEmployeeId(id);
	}
	
	public List<Grid> getAllGriddingInfo(String cityCode){
		return  employeeGridRelationMapper.getAllGriddingInfo(cityCode);
	}
	public Integer updateEmpGridRealtion(String id,String isManager){
		employeeGridRelationMapper.updateEmpGridRealtion(id,isManager);
		return null;
	}
}
