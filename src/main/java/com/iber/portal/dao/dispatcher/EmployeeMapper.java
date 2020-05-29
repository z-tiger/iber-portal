package com.iber.portal.dao.dispatcher;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.MapKey;

import com.iber.portal.model.dispatcher.Employee;
import com.iber.portal.model.dispatcher.EmployeeVo;
import com.iber.portal.model.employee.EmployeeOrder;
import com.iber.portal.vo.car.EmployeeInfoVo;

public interface EmployeeMapper {
	
	int insert(Employee record);
	
	int deleteByPrimaryKey(Integer id);
	
	int updateByPrimaryKey(Employee record);
	
	int updateByPrimaryKeySelective(Employee record);
	
	Employee selectByPrimaryKey(Integer id);
	
	int getAllNum(Map<String, Object> paramMap);
	
	List<Employee> queryPageList(Map<String, Object> paramMap);

	List<Employee> selectAllDispatcher();

	List<Employee> getDispatcherDetail(Map<String, Object> paramMap);

	int getMyAllParkNum(Map<String, Object> paramMap);

	List<Employee> selectExceptExistRelation(String cityCode);

	List<Map<String, Object>> selectAllPhone();

	int update(Employee employee);

	List<EmployeeVo> queryDispatcherList();

	List<EmployeeVo> queryDispatcher(String cityCode);
	
	/**
	 * 根据员工id获取员工正在用车的订单
	 * @param id
	 * @return
	 */
	EmployeeOrder queryOrderByEmployeeId(Integer id);
	/**
	 * 根据车牌查询员工订单
	 * @param lpn
	 * @return
	 */
	EmployeeOrder queryOrderByLpn(String lpn);
	
	/**删除员工指纹*/
	Integer deleteEmployeeFinger(Integer id);
	
	/**
	 * 根据手机号查询员工
	 * @param responsiblePersonPhone
	 * @return
	 */
	Employee selectByPhone(String responsiblePersonPhone);
	
	/**
	 * 查询所有上班的员工
	 * @param paramMap
	 * @return
	 */
	List<EmployeeInfoVo> getAll(Map<String, Object> paramMap);
	
	/**
	 * 查询所有正在上班的员工数量
	 * @param paramMap
	 * @return
	 */
	int getAllWorkingNum(Map<String, Object> paramMap);
	
	List<Employee> queryEmployeePortInfos(Map<String, Object> paramMap);
	
	int queryEmployeePortInfosNum(Map<String, Object> paramMap);
	
	List<Employee> queryEmployeesByCityCode(Map<String, Object> map);
	
	@MapKey("id")
	Map<String, Object> queryEmployeeLeaders();
	
	Integer queryEmployeeGrid(String id);

	List<Employee> getAllEmployeeInfo(HashMap<String, Object> map);

	int selectAllEmployeesRecords(HashMap<String, Object> map);

	List<String> selectPhoneListByParkId(Integer parkId);

	/**
	 * 删除三代指纹
	 * @param id
	 * @return
	 */
    int deleteEmployeeTboxFinger(Integer id);
}
