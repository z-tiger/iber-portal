package com.iber.portal.controller.Task;

import com.alibaba.fastjson.JSON;
import com.iber.portal.advices.SystemServiceLog;
import com.iber.portal.common.Data2Jsp;
import com.iber.portal.common.ExcelUtil;
import com.iber.portal.common.JsonDateValueProcessor;
import com.iber.portal.common.Pager;
import com.iber.portal.controller.MainController;
import com.iber.portal.dao.base.ParkMapper;
import com.iber.portal.dao.base.SystemMsgLogMapper;
import com.iber.portal.dao.base.SystemMsgMapper;
import com.iber.portal.dao.task.TaskPoolMapper;
import com.iber.portal.dao.timeShare.TimeShareOrderMapper;
import com.iber.portal.getui.PushClientEmployee;
import com.iber.portal.getui.PushCommonBean;
import com.iber.portal.model.base.Park;
import com.iber.portal.model.base.SystemMsg;
import com.iber.portal.model.base.SystemMsgLog;
import com.iber.portal.model.car.CarChargingRemind;
import com.iber.portal.model.car.CarRescue;
import com.iber.portal.model.car.CarRun;
import com.iber.portal.model.dispatcher.Employee;
import com.iber.portal.model.sys.SysUser;
import com.iber.portal.model.task.*;
import com.iber.portal.model.timeShare.TimeShareOrder;
import com.iber.portal.service.car.CarRescueService;
import com.iber.portal.service.car.CarRunService;
import com.iber.portal.service.dispatcher.EmployeeService;
import com.iber.portal.service.dispatcher.GridService;
import com.iber.portal.service.sys.SysParamService;
import com.iber.portal.service.task.TaskScoreStrategyService;
import com.iber.portal.service.task.TaskService;
import com.iber.portal.service.timeShare.TimeShareOrderService;
import com.iber.portal.util.DateTime;
import com.iber.portal.util.SendSMS;
import net.sf.json.JsonConfig;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 任务积分策略控制器
 * @author Administrator
 *
 */
@Controller
public class TaskScoreStrategyController extends MainController {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
    private TaskScoreStrategyService taskScoreStrategyService;

	/**
	 * 任务积分管理页面
	 */
	@SystemServiceLog(description = "任务积分策略管理页面")
	@RequestMapping(value = "task_score_strategy_management" , method = { RequestMethod.GET, RequestMethod.POST })
	public String gridManagementPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return "/task/taskScoreStrategyManagement";
	}

    /**
     * 任务积分管理列表
     */
    @SystemServiceLog(description = "任务积分策略分页查询")
    @RequestMapping(value = "task_score_strategy_page" , method = { RequestMethod.GET, RequestMethod.POST })
    public String gridTaskScoreStrategyPage(int page, int rows, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=utf-8");

        Pager<HashMap> pager = taskScoreStrategyService.queryPageList(page,rows,request);
        response.getWriter().print(Data2Jsp.Json2Jsp(pager));
        return null;
    }

    /**
     * 维护类目列表
     */
    @SystemServiceLog(description = "维护类目分页查询")
    @RequestMapping(value = "get_maintain_type" , method = { RequestMethod.GET, RequestMethod.POST })
    public String getMaintainType(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=utf-8");

        List<Map> maintainType = taskScoreStrategyService.getMaintainType();
        response.getWriter().print(JSON.toJSON(maintainType));
        return null;
    }

    /**
     * 新增，或修改
     */
    @SystemServiceLog(description = "新增修改任务积分策略")
    @RequestMapping(value = "task_score_strategy_saveorupdate" , method = { RequestMethod.GET, RequestMethod.POST })
    public String saveOrUpdate(TaskScoreStrategy taskScoreStrategy,HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=utf-8");
        taskScoreStrategyService.saveOrUpdate(taskScoreStrategy,request, response);
        return null;
    }

}
