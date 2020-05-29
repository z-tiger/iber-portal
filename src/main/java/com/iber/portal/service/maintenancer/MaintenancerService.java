package com.iber.portal.service.maintenancer;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iber.portal.common.Pager;
import com.iber.portal.dao.employee.EmployeeInfoMapper;
import com.iber.portal.model.dispatcher.Employee;
import com.iber.portal.model.employee.MaintenanceEmployeeInfo;

@Service
public class MaintenancerService {
	@Autowired
	private EmployeeInfoMapper employeeInfoMapper;

	public Pager<MaintenanceEmployeeInfo> getMaintenancerInfos(Map<String, Object> map) {

		List<MaintenanceEmployeeInfo> listObj = employeeInfoMapper
				.getMaintenancerInfos(map);
		Pager<MaintenanceEmployeeInfo> pager = new Pager<MaintenanceEmployeeInfo>();
		pager.setDatas(listObj);
		pager.setTotalCount(employeeInfoMapper.getMaintenancerAmount(map));
		return pager;
	}

	public int updateMaintenancerInfo(Employee employee) {
		//return employeeInfoMapper.updateMaintenancerInfo(employee);
		return employeeInfoMapper.updateEmployeeInfo(employee);
	}

	public int addMaintenancerInfo(Map<String, Object> map) {
		return employeeInfoMapper.insertMaintenancerInfo(map);
	}
	public int freezeMaintenceAccount(String id){
		return employeeInfoMapper.updateEmployeeAccountToFrozen(Integer.valueOf(id));
	}
	public int activeMaintenceAccount(String id){
		return employeeInfoMapper.updateEmployeeAccountToActived(Integer.valueOf(id));
	}
}
