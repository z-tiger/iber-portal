package com.iber.portal.service.employee;

import com.iber.portal.common.Pager;
import com.iber.portal.dao.employee.EmployeeOrderMapper;
import com.iber.portal.model.employee.EmployeeOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class EmployeeOrderService {
	@Autowired
	private EmployeeOrderMapper employeeOrderMapper;
	
	
	public Pager< EmployeeOrder> getPagerAllInfo(Map<String,Object> map) {
		List< EmployeeOrder> listObj = getAllInfo(map);
		Pager< EmployeeOrder> pager = new Pager< EmployeeOrder>();
		pager.setDatas(listObj);
		pager.setTotalCount(getAllInfoNum(map));
		return pager;
	}
	public List<EmployeeOrder> getAllInfo(Map<String,Object> map) {
		List<EmployeeOrder> employeeOrders= employeeOrderMapper.getAllInfo(map);
        return employeeOrders;
	}
	
	public int getAllInfoNum(Map<String,Object> map){
    	return employeeOrderMapper.getAllInfoNum(map);
    }
}
