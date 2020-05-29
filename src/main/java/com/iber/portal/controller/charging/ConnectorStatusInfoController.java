package com.iber.portal.controller.charging;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.iber.portal.advices.SystemServiceLog;
import com.iber.portal.common.Data2Jsp;
import com.iber.portal.common.Pager;
import com.iber.portal.model.charging.ConnectorStatusInfo;
import com.iber.portal.service.charging.ConnectorStatusInfoService;

/**
 * <b>功能：</b>充电设备接口状态<br>
 */ 
@Controller
public class ConnectorStatusInfoController{
	
	private final static Logger log= Logger.getLogger(ConnectorStatusInfoController.class);
	
	// Servrice start
	@Autowired(required=false) //自动注入，不需要生成set方法了，required=false表示没有实现类，也不会报错。
	private ConnectorStatusInfoService connectorStatusInfoService; 
	
	/**
	 * @return
	 * @throws Exception 
	 */
	@SystemServiceLog(description="ConnectorStatusInfo页面")
	@RequestMapping("/connectorStatusInfo_page") 
	public String connectorStatusInfo_page(HttpServletRequest request, HttpServletResponse response) {
		log.info("ConnectorStatusInfo页面");
		return "charging/connectorStatusInfo" ;
	}
	
	
	/**
	 * 充电设备接口状态列表
	 */
	@SystemServiceLog(description="充电设备接口状态列表")
	@RequestMapping("/dataListConnectorStatusInfo") 
	public void  datalist(int page, int rows, HttpServletRequest request, HttpServletResponse response) throws IOException{
		log.info("数据列表");
		response.setContentType("text/html;charset=utf-8");
		Map<String,Integer> pageInfo = Data2Jsp.getFristAndPageSize(page, rows);
		int from = pageInfo.get("first_page");
		int to = pageInfo.get("page_size");
		
		//获取其他查询参数
		//String title = request.getParameter("title");
		String   connectorId = request.getParameter("connectorId");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("from", from);
		paramMap.put("to", to);
		paramMap.put("connectorId",connectorId);
		Pager<ConnectorStatusInfo> pager = connectorStatusInfoService.queryPageList(paramMap);
		response.getWriter().print(Data2Jsp.Json2Jsp(pager));
	}
	
	/**
	 * 添加或修改数据
	 * @param ConnectorStatusInfo
	 * @return
	 * @throws IOException 
	 */
	@SystemServiceLog(description="充电设备新增或更新")
	@RequestMapping("/saveOrUpdateConnectorStatusInfo")
	public void saveOrUpdate(ConnectorStatusInfo entity,HttpServletRequest request, HttpServletResponse response) throws IOException{
		log.info("新增或更新");
		if(entity.getId()==null||StringUtils.isBlank(entity.getId().toString())){
			connectorStatusInfoService.insert(entity);
		}else{
			connectorStatusInfoService.updateByPrimaryKey(entity);
		}
		response.getWriter().print("success");
	}
	
	/**
	 * 删除数据
	 * @param 主键ID
	 * @return
	 * @throws IOException 
	 */
	@SystemServiceLog(description="充电设备删除")
	@RequestMapping("/deleteConnectorStatusInfoById")
	public void delete(Integer id,HttpServletRequest request,HttpServletResponse response) throws IOException{
		log.info("删除");
		if(null!=id ) {
			connectorStatusInfoService.deleteByPrimaryKey(id);
		}
		response.getWriter().print("success");
	}
}
