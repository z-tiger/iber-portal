package com.iber.portal.service.task;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.iber.portal.common.Pager;
import com.iber.portal.dao.dispatcher.EmployeeMapper;
import com.iber.portal.dao.task.TaskMapper;
import com.iber.portal.model.task.Lpn;
import com.iber.portal.model.task.ParkName;
import com.iber.portal.model.task.TaskGrade;
import com.iber.portal.model.task.TaskInfo;
import com.iber.portal.model.task.TaskType;

@Service
public class TaskService {
	@Autowired
	private TaskMapper taskMapper;
	@Autowired 
	private EmployeeMapper employeeMapper;
	@Autowired 
	private JdbcTemplate jdbcTemplate;
	
	public Pager<TaskInfo> queryPageList(Map<String, Object> paramMap) {
		List<TaskInfo> listObj = taskMapper.queryPageList(paramMap);
	    Map<String, Object> map = employeeMapper.queryEmployeeLeaders();
		List<TaskInfo> updateObj = new ArrayList<TaskInfo>();
		if(null!=listObj&&0<listObj.size()){
			for (TaskInfo taskInfo : listObj) {
				if(StringUtils.isBlank(taskInfo.getTaskName())){
					String taskType = taskInfo.getTaskType();
					if("1".equals(taskType)){
						taskInfo.setTaskName(taskInfo.getLpn().concat("调度"));
					}else if("2".equals(taskType)){
						taskInfo.setTaskName(taskInfo.getLpn().concat("维护"));
					}else if("3".equals(taskType)){
						taskInfo.setTaskName(taskInfo.getLpn().concat("充电"));
					}else if("4".equals(taskType)){
						taskInfo.setTaskName(taskInfo.getLpn().concat("维修"));
					}else if("5".equals(taskType)){
						taskInfo.setTaskName(taskInfo.getLpn().concat("救援"));
					}else {
						taskInfo.setTaskName(taskInfo.getLpn().concat("其他"));
					}
					updateObj.add(taskInfo);
				}
				JSONObject obj = JSONObject.fromObject(map.get(taskInfo.getId()));
				if(null!=obj){
					taskInfo.setEmployeeLeader(null==obj.get("leader")?"":obj.get("leader").toString());
				}
			}
		}
		if(0<updateObj.size()){
			String updateSql = "update e_task_info set task_name = ? where id = ? ";
			jdbcTemplate.batchUpdate(updateSql, new BatchSetter(updateObj));
		}
		Pager<TaskInfo> pager = new Pager<TaskInfo>();
		pager.setDatas(listObj);
		pager.setTotalCount(getAllNum(paramMap));
		return pager;
	}
	private int getAllNum(Map<String, Object> paramMap) {
		return taskMapper.getTotalTasksCount(paramMap);
	}
	public List<TaskType> selectAllTaskType() {
		return taskMapper.selectAllTaskType();
	}
	public List<Lpn> selectAllLpn() {
		return taskMapper.selectAllLpn();
	}
	public List<Lpn> queryTotalLpns() {
		return taskMapper.queryTotalLpns();
	}
	public List<TaskGrade> selectAllTaskGrade() {
		return taskMapper.selectAllTaskGrade();
	}
	public TaskInfo selectByPrimaryKey(Integer id) {
		return taskMapper.selectByPrimaryKey(id);
	}
	public void updateTask(TaskInfo taskInfo) {
		taskMapper.updateByPrimaryKey(taskInfo);
	}
	public List<ParkName> selectAllPark() {
		return taskMapper.selectAllPark();
	}
	public TaskType selectByTaskType(int taskType) {
		return taskMapper.selectByTaskType(taskType);
	}
	public int insert(TaskInfo taskInfo) {
		return taskMapper.insert(taskInfo);
	}
	public Integer selectByTaskTypeAndLpn(Map<String, Object> paramMap) {
		return taskMapper.selectByTaskTypeAndLpn(paramMap);
	}
	private class BatchSetter implements BatchPreparedStatementSetter{
		final List<TaskInfo> temList; 
		public BatchSetter(List<TaskInfo> temList){
			this.temList = temList;
        }
		public void setValues(PreparedStatement ps, int i) throws SQLException {
			TaskInfo taskInfo = temList.get(i);
			ps.setString(1, taskInfo.getTaskName());
			ps.setInt(2, taskInfo.getId());
		}
		public int getBatchSize() {
			return temList.size();
		}
		
	}
	public Integer selectCarTaskInfo(String id,String lpn,String taskType){
		return taskMapper.selectCarTaskInfo(id,lpn,taskType);
	}
	public TaskInfo selectByTaskTypeAndLpn(String type, String lpn) {
		return taskMapper.selectNONByTaskTypeAndLpn(type,lpn);
	}
	public int updateStatus(Integer id, String status) {
		return taskMapper.updateStatus(id,status);
	}
	public int selectNonTaskRecords(String empId) {
		return taskMapper.selectNonTaskRecords(empId);
	}
}
