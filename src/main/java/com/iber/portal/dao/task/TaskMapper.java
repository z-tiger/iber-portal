package com.iber.portal.dao.task;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.iber.portal.model.task.Lpn;
import com.iber.portal.model.task.ParkName;
import com.iber.portal.model.task.TaskGrade;
import com.iber.portal.model.task.TaskInfo;
import com.iber.portal.model.task.TaskType;

public interface TaskMapper {
	int insert(TaskInfo record);
	
	int deleteByPrimaryKey(Integer id);
	
	int updateByPrimaryKey(TaskInfo record);
	
	int updateByPrimaryKeySelective(TaskInfo record);
	
	TaskInfo selectByPrimaryKey(Integer id);
	
	int getAllNum(Map<String, Object> paramMap);
	
	List<TaskInfo> queryPageList(Map<String, Object> paramMap);

	List<TaskType> selectAllTaskType();

	List<Lpn> selectAllLpn();
	
	List<Lpn> queryTotalLpns();

	List<TaskGrade> selectAllTaskGrade();

	List<ParkName> selectAllPark();

	TaskType selectByTaskType(int taskType);
	
	TaskInfo getRecords(String lpn);
	
	int insertChargingRecords(TaskInfo record);
	
	/**
	 * 根据任务类型和车牌查询救援中的任务
	 * @param paramMap
	 * @return
	 */
	Integer selectByTaskTypeAndLpn(Map<String, Object> paramMap);
	Integer getTotalTasksCount(Map<String, Object> paramMap);
	Integer selectCarTaskInfo(@Param("id")String id,@Param("lpn")String lpn,@Param("taskType")String taskType);

	TaskInfo selectNONByTaskTypeAndLpn(@Param("taskType")String taskType, @Param("lpn")String lpn);

	int updateStatus(@Param("id")Integer id, @Param("status")String status);
    int updateTaskStatusToFinish(@Param("taskType") String taskType,@Param("status") String status,@Param("lpn") String lpn,@Param("result") String result);

	TaskInfo getRescueRecordByLpn(@Param("taskType")String string, @Param("lpn")String lpn);

	int selectNonTaskRecords(@Param("empId")String empId);
}
