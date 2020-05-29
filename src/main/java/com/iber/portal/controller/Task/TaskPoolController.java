package com.iber.portal.controller.Task;

import com.iber.portal.advices.SystemServiceLog;
import com.iber.portal.common.Data2Jsp;
import com.iber.portal.common.Pager;
import com.iber.portal.controller.MainController;
import com.iber.portal.model.sys.SysUser;
import com.iber.portal.model.task.*;
import com.iber.portal.service.task.TaskPoolService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * 任务控制器
 * @author zengfeiyue
 *
 */
@Controller
public class TaskPoolController extends MainController {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private TaskPoolService taskPoolService;

	/**
	 * 任务池管理
	 */
	@SystemServiceLog(description = "任务池管理")
	@RequestMapping(value = "task_pool" , method = { RequestMethod.GET, RequestMethod.POST })
	public String gridManagementPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return "/task/pool";
	}
	
	/**
	 * 任务池分页查询
	 */
	@SystemServiceLog(description = "任务池分页查询")
	@RequestMapping(value = "task_pool_page" , method = { RequestMethod.GET, RequestMethod.POST })
	public String gridTaskPoolList(int page, int rows, HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");

		Pager<HashMap> pager = taskPoolService.queryPageList(page,rows,request);
		response.getWriter().print(Data2Jsp.Json2Jsp(pager));
		return null;
	}

	/**
	 * 创建任务池任务
	 */
	@SystemServiceLog(description = "创建任务池任务")
	@RequestMapping(value = "task_pool_save" , method = { RequestMethod.GET, RequestMethod.POST })
	public String saveTaskPool( HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		taskPoolService.save(request,response);
		return null;
	}

	/**
	 * 取消任务池任务
	 */
	@SystemServiceLog(description = "取消任务池任务")
	@RequestMapping(value = "task_pool_cancel" , method = { RequestMethod.GET, RequestMethod.POST })
	public String cancelTaskPool( HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		taskPoolService.cancel(request,response);
		return null;
	}

}
