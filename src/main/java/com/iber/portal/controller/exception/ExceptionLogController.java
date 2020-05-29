package com.iber.portal.controller.exception;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.iber.portal.mongo.search.MongoExceptionLogSearch;
import com.iber.portal.mongo.search.MongoPlatformLogSearch;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.iber.portal.advices.SystemServiceLog;
import com.iber.portal.common.Data2Jsp;
import com.iber.portal.common.Pager;
import com.iber.portal.model.exception.ExceptionLog;
import com.iber.portal.service.exception.ExceptionLogService;

/**
 * 
 * <br>
 * <b>功能：</b>XExceptionLogController<br>
 * <b>作者：</b>www.jeecg.org<br>
 * <b>日期：</b> Feb 2, 2013 <br>
 * <b>版权所有：<b>版权所有(C) 2013，www.jeecg.org<br>
 */ 
@Controller
public class ExceptionLogController{
	
	private final static Logger log= Logger.getLogger(ExceptionLogController.class);
	
	// Servrice start
	@Autowired(required=false) //自动注入，不需要生成set方法了，required=false表示没有实现类，也不会报错。
	private ExceptionLogService exceptionLogService; 
	
	/**
	 * @return
	 * @throws Exception 
	 */
	@SystemServiceLog(description="接口异常管理页面")
	@RequestMapping("/exceptionLog_page") 
	public String exceptionLog_page(HttpServletRequest request, HttpServletResponse response) {
		log.info("XExceptionLog页面");
		return "exception/exceptionLog" ;
	}
	
	
	/**
	 * 数据
	 * @param page
	 * @param rows
	 * @return
	 * @throws IOException 
	 */
	@SystemServiceLog(description="接口异常管理页面数据")
	@RequestMapping("/dataListXExceptionLog") 
	public void  datalist(int page, int rows, HttpServletRequest request, HttpServletResponse response) throws IOException{
		log.info("接口异常数据列表");
		response.setContentType("text/html;charset=utf-8");
//		Map<String,Integer> pageInfo = Data2Jsp.getFristAndPageSize(page, rows);
//		int from = pageInfo.get("first_page");
//		int to = pageInfo.get("page_size");
//		Map<String, Object> paramMap = new HashMap<String, Object>();
//		//获取其他查询参数
//		if(!StringUtils.isBlank(request.getParameter("queryDateFrom"))){
//	       	String startTime = request.getParameter("queryDateFrom");
//	       	paramMap.put("queryDateFrom", startTime);
//	     }
//	       if(!StringUtils.isBlank(request.getParameter("queryDateTo"))){
//	       	String endTime = request.getParameter("queryDateTo");
//	       	paramMap.put("queryDateTo", endTime);
//	     }
//
//
//		paramMap.put("from", from);
//		paramMap.put("to", to);
//
//		Pager<ExceptionLog> pager = exceptionLogService.queryPageList(paramMap);
//		response.getWriter().print(Data2Jsp.Json2Jsp(pager));
		MongoExceptionLogSearch search = new MongoExceptionLogSearch();
		search.setPage(page);
		search.setRows(rows);
		final String start = request.getParameter("start");
		final String end = request.getParameter("end");
		if (NumberUtils.isDigits(start)) {
			search.setStartTime(Long.valueOf(start));
		}
		if (NumberUtils.isDigits(end)) {
			search.setEndTime(Long.valueOf(end));
		}
		search.setMethod(request.getParameter("method"));
		String memberId = request.getParameter("memberId");
		search.setMemberId(StringUtils.isNotBlank(memberId) ? Integer.valueOf(memberId) : null);
		// 查询
		Pager<Map<String, Object>> mapPager = exceptionLogService.searchLogs(search);
		response.getWriter().print(Data2Jsp.Json2Jsp(mapPager));
	}
	
	/**
	 * 添加或修改数据
	 * @param ExceptionLog
	 * @return
	 * @throws IOException 
	 */
	@SystemServiceLog(description="接口异常管理页面添加或修改数据")
	@RequestMapping("/saveOrUpdateXExceptionLog")
	public void saveOrUpdate(ExceptionLog entity,HttpServletRequest request, HttpServletResponse response) throws IOException{
		log.info("新增或更新");
		if(entity.getId()==null||StringUtils.isBlank(entity.getId().toString())){
			exceptionLogService.insert(entity);
		}else{
			exceptionLogService.updateByPrimaryKey(entity);
		}
		response.getWriter().print("success");
	}
	
	/**
	 * 删除数据
	 * @param 主键ID
	 * @return
	 * @throws IOException 
	 */
	@SystemServiceLog(description="接口异常管理页面主键ID")
	@RequestMapping("/deleteXExceptionLogById")
	public void delete(Integer id,HttpServletRequest request,HttpServletResponse response) throws IOException{
		log.info("删除");
		if(null!=id ) {
			exceptionLogService.deleteByPrimaryKey(id);
		}
		response.getWriter().print("success");
	}
}
