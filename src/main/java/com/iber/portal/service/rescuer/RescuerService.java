package com.iber.portal.service.rescuer;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iber.portal.common.Pager;
import com.iber.portal.dao.employee.EmployeeInfoMapper;
import com.iber.portal.model.dispatcher.Employee;
import com.iber.portal.model.employee.RescuerEmployeeInfo;

@Service
public class RescuerService {
	@Autowired
	private EmployeeInfoMapper employeeInfoMapper;

	public Pager<RescuerEmployeeInfo> getRescuerInfos(Map<String, Object> map) {

		List<RescuerEmployeeInfo> listObj = employeeInfoMapper
				.getRescuerInfos(map);
		Pager<RescuerEmployeeInfo> pager = new Pager<RescuerEmployeeInfo>();
		pager.setDatas(listObj);
		pager.setTotalCount(employeeInfoMapper.getRescuerAmount(map));
		return pager;
	}

	public int updateRescuerInfo(Employee employee) {
		//return employeeInfoMapper.updateRescuerInfo(employee);
		return employeeInfoMapper.updateEmployeeInfo(employee);
	}

	public int addRescuerInfo(Map<String, Object> map) {
		return employeeInfoMapper.insertRescuerInfo(map);
	}
	public int freezeRescuerAccount(String id){
		return employeeInfoMapper.updateEmployeeAccountToFrozen(Integer.valueOf(id));
	}
	public int activeRescuerAccount(String id){
		return employeeInfoMapper.updateEmployeeAccountToActived(Integer.valueOf(id));
		
	}
}
