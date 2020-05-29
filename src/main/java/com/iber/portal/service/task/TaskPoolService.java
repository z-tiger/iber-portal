package com.iber.portal.service.task;

import com.iber.portal.common.Data2Jsp;
import com.iber.portal.common.Pager;
import com.iber.portal.dao.car.CarRunMapper;
import com.iber.portal.dao.task.TaskMapper;
import com.iber.portal.dao.task.TaskPoolMapper;
import com.iber.portal.model.car.CarRun;
import com.iber.portal.model.sys.SysUser;
import com.iber.portal.model.task.TaskInfo;
import com.iber.portal.model.task.TaskPool;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * 任务池 servvice
 * @author zengfeiyue
 */
@Service
public class TaskPoolService {

	@Autowired
	private TaskPoolMapper taskPoolMapper;

	@Autowired
	private TaskMapper taskMapper;

	@Autowired
	private CarRunMapper carRunMapper;

	/**
	 * 分页查询定时任务
	 * @param page
	 * @param rows
	 * @param request
	 * @return
	 */
	public Pager<HashMap> queryPageList(int page, int rows, HttpServletRequest request) {
		Map<String,Integer> pageInfo = Data2Jsp.getFristAndPageSize(page, rows);
		int from = pageInfo.get("first_page");
		int to = pageInfo.get("page_size");

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("from", from);
		paramMap.put("to", to);
		SysUser user = (SysUser) request.getSession().getAttribute("user");
		if(user.getCityCode().equals("00")){
			if(!StringUtils.isBlank(request.getParameter("cityCode"))){
				if(request.getParameter("cityCode").equals("00")){
					paramMap.put("cityCode", null);
				}else{
					paramMap.put("cityCode", request.getParameter("cityCode"));
				}
			}
		}else{
			paramMap.put("cityCode", user.getCityCode());
		}
		String lpn = request.getParameter("lpn");
		paramMap.put("lpn", lpn);
		String status = request.getParameter("status");
		String taskType = request.getParameter("taskType");
		String taskStatus = request.getParameter("taskStatus");
		String employeeName = request.getParameter("employeeName");
		String employeePhone = request.getParameter("employeePhone");
		paramMap.put("employeeName", employeeName);
		paramMap.put("employeePhone", employeePhone);
		if(StringUtils.isBlank(status)||"0".equals(status)){
			paramMap.put("status", null);
		}else {
			paramMap.put("status", status);
		}

		if(StringUtils.isBlank(taskStatus)||"0".equals(taskStatus)){
			paramMap.put("taskStatus", null);
		}else {
			paramMap.put("taskStatus", taskStatus);
		}

		if(StringUtils.isEmpty(taskType)||"0".equals(taskType)){
			paramMap.put("taskType", null);
		}else {
			paramMap.put("taskType", taskType);
		}
		List<HashMap> taskPools = taskPoolMapper.findPage(paramMap);
		Integer totalCount = taskPoolMapper.findPageTotalCount(paramMap);
		Pager<HashMap> pager = new Pager<HashMap>();
		pager.setDatas(taskPools);
		pager.setTotalCount(totalCount);
		return pager;
	}

	/**
	 * 保存任务池
	 * @param request
	 * @param response
	 */
	@Transactional(rollbackFor = Exception.class)
	public void save(HttpServletRequest request, HttpServletResponse response) {
		String lpn = request.getParameter("lpn");
		String taskType = request.getParameter("taskType");
		String endParkId = request.getParameter("endParkId");
		Integer count = taskPoolMapper.findByStatusAndLpn(lpn);
		String result ="";
		if (count==0){
			TaskPool taskPool = new TaskPool();
			taskPool.setLpn(lpn);
			taskPool.setCreateTime(new Date());
			taskPool.setType(Integer.parseInt(taskType));
			if (!endParkId.isEmpty()){
				taskPool.setEndParkId(Integer.parseInt(endParkId));
			}
			taskPoolMapper.insertSelective(taskPool);
			result = "ok";
		}else {
			result = "exist";
		}
		try {
			response.getWriter().print(result);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 取消任务池
	 * @param request
	 * @param response
	 */
	@Transactional(rollbackFor = Exception.class)
	public void cancel(HttpServletRequest request, HttpServletResponse response) {
		try {
			String id = request.getParameter("id");
			TaskPool taskPool = taskPoolMapper.selectByPrimaryKey(Integer.parseInt(id));
			CarRun carRun = carRunMapper.queryCarRunByLpn(taskPool.getLpn());
			if (taskPool.getTaskInfoId()==null){
				//取消
				setCancel(taskPool,response);
			}
			else if ("useCar".equals(carRun.getStatus())){
				setCancel(taskPool,response);
			}
			else if ("charging".equals(carRun.getStatus())&&"1".equals(carRun.getBatStatus())){
				setCancel(taskPool,response);
			}
		}catch (Exception e){

		}
	}

	/**
	 * 设置取消
	 * @param taskPool
	 */
	public void setCancel(TaskPool taskPool,HttpServletResponse response){
		//取消
		taskPool.setStatus("4");
		taskPoolMapper.updateByPrimaryKeySelective(taskPool);
		if (taskPool.getTaskInfoId()!=null){
			TaskInfo taskInfo = taskMapper.selectByPrimaryKey(taskPool.getTaskInfoId());
			/*设置任务取消*/
			taskMapper.updateStatus(taskInfo.getId(),"4");
		}

		try {
			response.getWriter().print("ok");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
