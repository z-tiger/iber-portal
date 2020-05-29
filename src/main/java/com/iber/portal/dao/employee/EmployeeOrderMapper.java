package com.iber.portal.dao.employee;

import java.util.List;
import java.util.Map;

import com.iber.portal.model.employee.EmployeeOrder;

public interface EmployeeOrderMapper {
	List<EmployeeOrder> getAllInfo(Map<String, Object> paramMap);
	Integer getAllInfoNum(Map<String, Object> paramMap);

    List<Map<String,String>> queryOrderIdsByLpn(String lpn);
}
