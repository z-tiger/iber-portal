package com.iber.portal.service.dispatcher;

import com.iber.portal.common.Pager;
import com.iber.portal.dao.dispatcher.EmployeeMapper;
import com.iber.portal.dao.employee.EmployeeInfoMapper;
import com.iber.portal.dao.employee.EmployeeOrderMapper;
import com.iber.portal.dao.sys.SysParamMapper;
import com.iber.portal.model.base.Park;
import com.iber.portal.model.dispatcher.Employee;
import com.iber.portal.model.dispatcher.EmployeeVo;
import com.iber.portal.model.employee.EmployeeOrder;
import com.iber.portal.model.sys.SysParam;
import com.iber.portal.vo.car.EmployeeInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.iber.portal.common.MD5;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EmployeeService {
	
	@Autowired
	private EmployeeMapper employeeMapper;
	@Autowired
	private EmployeeInfoMapper employeeInfoMapper;
	@Autowired
	private SysParamMapper sysParamMapper;
	@Autowired
	private EmployeeOrderMapper employeeOrderMapper;
	/**
	 * 获得所有的调度员
	 * @return
	 */
	public List<Employee> selectAllDispatcher(){
		return employeeMapper.selectAllDispatcher();
	}
	/**
	 * 根据网格id以及调度员名称查询调度员
	 * @param paramMap
	 * @return
	 */
	public Pager<Employee> queryPageDetail(Map<String, Object> paramMap) {
		List<Employee> listObj = employeeMapper.getDispatcherDetail(paramMap);
		Pager<Employee> pager = new Pager<Employee>();
		pager.setDatas(listObj);
		pager.setTotalCount(getMyAllParkNum(paramMap));
		return pager;
	}
	private int getMyAllParkNum(Map<String, Object> paramMap) {
		return employeeMapper.getMyAllParkNum(paramMap);
	}
	public List<Employee> selectExceptExistRelation(String cityCode) {
		return employeeMapper.selectExceptExistRelation(cityCode);
	}
	
	public Pager<Employee> queryPageList(Map<String, Object> paramMap) {
		List<Employee> listObj = employeeMapper.queryPageList(paramMap);
		Pager<Employee> pager = new Pager<Employee>();
		pager.setDatas(listObj);
		pager.setTotalCount(getAllNum(paramMap));
		return pager;
	}
	
	private int getAllNum(Map<String, Object> paramMap) {
		return employeeMapper.getAllNum(paramMap);
	}
	public List<Map<String, Object>> selectAllPhone() {
		return employeeMapper.selectAllPhone();
	}
	public void insert(Employee employee) {
		employeeMapper.insert(employee);
	}
	public Employee selectByPrimaryKey(Integer id) {
		return employeeMapper.selectByPrimaryKey(id);
	}
	public int update(Employee employee) {
		//return employeeMapper.update(employee);
		return employeeInfoMapper.updateEmployeeInfo(employee);
	}
	public int freezeDispatcherAccount(Integer id) {
	    return employeeInfoMapper.updateEmployeeAccountToFrozen(id);
	 // return employeeMapper.deleteByPrimaryKey(id);
	    
	}
	public int activeDispatcherAccount(Integer id) {
	    return employeeInfoMapper.updateEmployeeAccountToActived(id);
	}
	public List<EmployeeVo> queryDispatcherList() {
		return employeeMapper.queryDispatcherList();
	}
	public Employee selectByEmployeeId(Integer id) {
		return employeeMapper.selectByPrimaryKey(id);
	}
	public List<EmployeeVo> queryDispatcher(String cityCode) {
		return employeeMapper.queryDispatcher(cityCode);
	}
	public EmployeeOrder queryOrderByEmployeeId(Integer id) {
		return employeeMapper.queryOrderByEmployeeId(id);
	}
	public EmployeeOrder queryOrderByLpn(String lpn) {
		return employeeMapper.queryOrderByLpn(lpn);
	}
	public Integer deleteEmployeeFinger(Integer id) {
		return employeeMapper.deleteEmployeeFinger(id);
	}
	public Employee selectByPhone(String responsiblePersonPhone) {
		return employeeMapper.selectByPhone(responsiblePersonPhone);
	}
	public Pager<EmployeeInfoVo> getAll(Map<String, Object> paramMap) {
		List<EmployeeInfoVo> listObj = employeeMapper.getAll(paramMap);
		Pager<EmployeeInfoVo> pager = new Pager<EmployeeInfoVo>();
		pager.setDatas(listObj);
		pager.setTotalCount(employeeMapper.getAllWorkingNum(paramMap));
		return pager;
	}
	public Pager<Employee> queryEmployeePortInfos(Map<String, Object> paramMap) {
		List<Employee> listObj = employeeMapper.queryEmployeePortInfos(paramMap);
		Pager<Employee> pager = new Pager<Employee>();
		pager.setDatas(listObj);
		pager.setTotalCount(employeeMapper.queryEmployeePortInfosNum(paramMap));
		return pager;
	}
	public List<Employee> queryEmployeesByCityCodeAndTaskType(Map<String, Object> map){
		return employeeMapper.queryEmployeesByCityCode(map);
	}
	public Integer queryEmployeeGrid(String id){
		return employeeMapper.queryEmployeeGrid(id);
	}
	public List<Employee> getAllEmployeeInfo(HashMap<String, Object> map) {
		return employeeMapper.getAllEmployeeInfo(map);
	}
	public int selectAllEmployeesRecords(HashMap<String, Object> map) {
		return employeeMapper.selectAllEmployeesRecords(map);
	}
	
	public List<String> selectPhoneListByParkId(Integer parkId) {
		return employeeMapper.selectPhoneListByParkId(parkId);
	}

	/**
	 * 删除三代指纹
	 * @param id 员工id
	 * @return
	 */
    public int deleteEmployeeTboxFinger(Integer id) {
    	return employeeMapper.deleteEmployeeTboxFinger(id);
    }

    public Pager<Employee> getEmployeeInfo(Map<String, Object> map) {
    	List<Employee> lists = employeeInfoMapper.getEmployeeInfo(map);
		Pager<Employee> pager = new Pager<Employee>();
		pager.setDatas(lists);
		pager.setTotalCount(employeeInfoMapper.getAllEmployeeCount(map));
		return pager;
    }

	@Transactional(rollbackFor=Exception.class)
    public int resetPassword(Integer employeeId){
		SysParam sysParam = sysParamMapper.selectByKey("reset_post_password");
		Employee employee = new Employee();
		employee.setId(employeeId);
		employee.setPassword(MD5.toMD5(sysParam.getValue()));
		return employeeInfoMapper.updateEmployeeInfo(employee);
	}

    public List<Map<String,String>> queryOrderIdsByLpn(String lpn) {
    	return employeeOrderMapper.queryOrderIdsByLpn(lpn);
    }
}
