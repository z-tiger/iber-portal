package com.iber.portal.service.cityManager;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iber.portal.common.Pager;
import com.iber.portal.dao.employee.EmployeeInfoMapper;
import com.iber.portal.model.dispatcher.Employee;
import com.iber.portal.model.employee.CityManagerEmployeeInfo;
import com.iber.portal.model.employee.RescuerEmployeeInfo;

@Service
public class CityManagerService {
	@Autowired
	private EmployeeInfoMapper employeeInfoMapper;

	public Pager<CityManagerEmployeeInfo> getCityManagerInfos(Map<String, Object> map) {
		List<CityManagerEmployeeInfo> listObj = employeeInfoMapper
				.getCityManagerInfos(map);
		Pager<CityManagerEmployeeInfo> pager = new Pager<CityManagerEmployeeInfo>();
		pager.setDatas(listObj);
		pager.setTotalCount(employeeInfoMapper.getCityManagerAmount(map));
		return pager;
	}

	public int updateCityManagerInfo(Employee employee) {
		//return employeeInfoMapper.updateCityManagerInfo(employee);
		return employeeInfoMapper.updateEmployeeInfo(employee);
	}

	public int addCityManagerInfo(Map<String, Object> map) {
		return employeeInfoMapper.insertCityManagerInfo(map);
	}
	public int freezeCityManagerAccount(String id){
		return employeeInfoMapper.updateEmployeeAccountToFrozen(Integer.valueOf(id));
	}
	public int activeCityManagerAccount(String id){
		return employeeInfoMapper.updateEmployeeAccountToActived(Integer.valueOf(id));
	}
}
