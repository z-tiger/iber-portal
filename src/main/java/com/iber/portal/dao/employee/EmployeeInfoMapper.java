package com.iber.portal.dao.employee;

import com.iber.portal.model.dispatcher.Employee;
import com.iber.portal.model.employee.CityManagerEmployeeInfo;
import com.iber.portal.model.employee.EmployeeInfo;
import com.iber.portal.model.employee.MaintenanceEmployeeInfo;
import com.iber.portal.model.employee.RescuerEmployeeInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
/**
 * @author xuyq
 */
public interface EmployeeInfoMapper {
	EmployeeInfo getRecordsById(Integer parkId);
	// 救援人员信息处理接口
	List<RescuerEmployeeInfo> getRescuerInfos(Map<String, Object> map);
	int updateRescuerInfo(Employee employee);
	int insertRescuerInfo(Map<String, Object> map);
	int getRescuerAmount(Map<String, Object> map);
	// 维保员信息处理接口
	List<MaintenanceEmployeeInfo> getMaintenancerInfos(Map<String, Object> map);
	int getMaintenancerAmount(Map<String, Object> map);
	int updateMaintenancerInfo(Employee employee);
	int insertMaintenancerInfo(Map<String, Object> map);
	int updateEmployeeAccountToFrozen(@Param("id")Integer id);
	int updateEmployeeAccountToActived(@Param("id")Integer id);
	// 救援人员信息处理接口
	List<CityManagerEmployeeInfo> getCityManagerInfos(Map<String, Object> map);
	int getCityManagerAmount(Map<String, Object> map);
	int updateCityManagerInfo(Employee employee);
	int insertCityManagerInfo(Map<String, Object> map);
	
	int updateEmployeeInfo(Employee employee);

    List<Employee> getEmployeeInfo(Map<String, Object> map);
    Integer getAllEmployeeCount(Map<String, Object> map);
}

