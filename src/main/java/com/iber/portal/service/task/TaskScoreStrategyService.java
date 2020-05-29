package com.iber.portal.service.task;

import com.iber.portal.common.Data2Jsp;
import com.iber.portal.common.Pager;
import com.iber.portal.dao.car.CarRunMapper;
import com.iber.portal.dao.task.TaskMapper;
import com.iber.portal.dao.task.TaskPoolMapper;
import com.iber.portal.dao.task.TaskScoreStrategyMapper;
import com.iber.portal.model.car.CarRun;
import com.iber.portal.model.sys.SysUser;
import com.iber.portal.model.task.TaskInfo;
import com.iber.portal.model.task.TaskPool;
import com.iber.portal.model.task.TaskScoreStrategy;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 任务池 servvice
 * @author zengfeiyue
 */
@Service
public class TaskScoreStrategyService {

	@Autowired
	private TaskScoreStrategyMapper taskScoreStrategyMapper;


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


		String type = request.getParameter("type");
		paramMap.put("type", type);

		String maintain = request.getParameter("maintain");
		paramMap.put("maintain", maintain);

		String cityCode = request.getParameter("cityCode");
		paramMap.put("cityCode", cityCode);
		if ("00".equals(cityCode)){
			paramMap.put("cityCode", null);
		}
		List<HashMap> taskPools = taskScoreStrategyMapper.findPage(paramMap);
		Integer totalCount = taskScoreStrategyMapper.findPageTotalCount(paramMap);
		Pager<HashMap> pager = new Pager<HashMap>();
		pager.setDatas(taskPools);
		pager.setTotalCount(totalCount);
		return pager;
	}

	public List<Map> getMaintainType() {
		return taskScoreStrategyMapper.getMaintainType();
	}

	/**
	 * 修改/更新
	 * @param taskScoreStrategy
	 * @param response
	 */
	public void saveOrUpdate(TaskScoreStrategy taskScoreStrategy,HttpServletRequest request, HttpServletResponse response) throws IOException {
		SysUser user = (SysUser) request.getSession().getAttribute("user");
		List<TaskScoreStrategy> count;
		if (taskScoreStrategy.getType()==1){
			count = taskScoreStrategyMapper.findByTypeAndCityCodeMileage(taskScoreStrategy.getType(),taskScoreStrategy.getCityCode(),taskScoreStrategy.getMinMileage(),taskScoreStrategy.getMaxMileage());
		}else{
			count = taskScoreStrategyMapper.findByTypeAndCityCode(taskScoreStrategy.getType(),taskScoreStrategy.getCityCode(),taskScoreStrategy.getMaintainType());
		}
		if (taskScoreStrategy.getId()==null&&count.size()==0){
			taskScoreStrategy.setCreator(user.getName());
			taskScoreStrategy.setCreateTime(new Date());
			taskScoreStrategy.setScore(taskScoreStrategy.getScore()*10);
			taskScoreStrategyMapper.insertSelective(taskScoreStrategy);
		}else if (taskScoreStrategy.getId()!=null){
			if (count==null||(count!=null&&count.size()==1&&count.get(0).getId().equals(taskScoreStrategy.getId()))){
				if (taskScoreStrategy.getType()==1){
					taskScoreStrategy.setMaintainType(null);
				}else{
					taskScoreStrategy.setMaxMileage(null);
					taskScoreStrategy.setMinMileage(null);
				}
				taskScoreStrategy.setModifier(user.getName());
				taskScoreStrategy.setScore(taskScoreStrategy.getScore()*10);
				taskScoreStrategyMapper.updateByPrimaryKeySelective(taskScoreStrategy);
			}else {
				response.getWriter().write("exist");
				return;
			}

		}else{
			response.getWriter().write("exist");
			return;
		}
		response.getWriter().write("ok");

	}
}
