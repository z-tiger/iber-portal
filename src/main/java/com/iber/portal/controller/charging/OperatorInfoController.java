package com.iber.portal.controller.charging;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


 
import com.iber.portal.advices.SystemServiceLog;
import com.iber.portal.common.Data2Jsp;
import com.iber.portal.common.Pager;
import com.iber.portal.controller.MainController;
import com.iber.portal.model.charging.OperatorInfo;
import com.iber.portal.model.charging.StationInfo;
import com.iber.portal.model.sys.SysUser;
import com.iber.portal.service.charging.OperatorInfoService;


/**
 * 
 * <br>
 * <b>功能：充电桩运营商信息
 * <b>作者： xyq
 * <b>日期：2016.10.12
 * <b>版权所有：
 */ 
@Controller
public class OperatorInfoController extends MainController{
	
	private final static Logger log= Logger.getLogger(OperatorInfoController.class);
	
	// Servrice start
	@Autowired(required=false) //自动注入，不需要生成set方法了，required=false表示没有实现类，也不会报错。
	private OperatorInfoService operatorInfoService; 
	
	/** 运营商数据页面*/
	@SystemServiceLog(description="运营商数据页面")
	@RequestMapping("/charging_operator_page") 
	public String operatorinfo_page(HttpServletRequest request, HttpServletResponse response) {
		log.info("OperatorInfo页面");
		return "charging/operatorInfo" ;
	}
	
	
	/**
	 * 运营商数据列表
	 */
	@SystemServiceLog(description="运营商数据列表")
	@RequestMapping("/dataListOperatorinfo") 
	public void  datalist(int page, int rows, HttpServletRequest request, HttpServletResponse response) throws IOException{
		log.info("数据列表");
		response.setContentType("text/html;charset=utf-8");
		Map<String,Integer> pageInfo = Data2Jsp.getFristAndPageSize(page, rows);
		int from = pageInfo.get("first_page");
		int to = pageInfo.get("page_size");
		
		//获取其他查询参数
		//String title = request.getParameter("title");
		String operatorId = request.getParameter("operatorId");
		String name = request.getParameter("operatorName");
		String registerAddress = request.getParameter("registerAddress");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("from", from);
		paramMap.put("to", to);
		paramMap.put("operatorId",operatorId);
		paramMap.put("name",name);
		paramMap.put("registerAddress",registerAddress);
		Pager<OperatorInfo> pager = operatorInfoService.queryPageList(paramMap);
		response.getWriter().print(Data2Jsp.Json2Jsp(pager));
	}
	
	/**
	 * 添加或修改数据
	 */
	@SystemServiceLog(description="添加或修改运营商数据")
	@RequestMapping("/saveOrUpdateOperatorinfo")
	public void saveOrUpdate(OperatorInfo entity,HttpServletRequest request, HttpServletResponse response) throws IOException{
		log.info("新增或更新");
		SysUser user = (SysUser) getUser(request) ;
		if(entity.getId()==null||StringUtils.isBlank(entity.getId().toString())){
			entity.setCreateTime(new Date());
			entity.setCreateId(user.getId());
			operatorInfoService.insert(entity);	
		}else{
			entity.setUpdateTime(new Date());
			entity.setUpdateId(user.getId());
			operatorInfoService.updateByPrimaryKey(entity);
		}
		response.getWriter().print("success");
	}
	
	/**
	 * 删除数据
	 */
	@SystemServiceLog(description="删除运营商数据")
	@RequestMapping("/deleteOperatorinfoById")
	public void delete(Integer id,HttpServletRequest request,HttpServletResponse response) throws IOException{
		log.info("删除");
		if(null!=id ) {
			operatorInfoService.deleteByPrimaryKey(id);
		}
		response.getWriter().print("success");
	}
	
	/**运营商组织机构代码combobox*/
	@SystemServiceLog(description="运营商组织机构代码combobox")
	@RequestMapping(value = "/operatorId_Combobox", method = { RequestMethod.GET, RequestMethod.POST})
	public String stationCombobox (HttpServletRequest request,HttpServletResponse response) throws IOException{
		log.info("删除");
		response.setContentType("text/html;charset=utf-8");	
		List<OperatorInfo> operatorList = new ArrayList<OperatorInfo>();
		operatorList=operatorInfoService.getAllOperatorInfo();
		StringBuffer tree=new StringBuffer();		
		tree.append("[");
		if(operatorList!=null && operatorList.size()>0) {				
			for(int i=0;i<operatorList.size();i++ ){
				OperatorInfo operatorInfo=operatorList.get(i);
				tree.append("{");
				tree.append("\"id\":\""+operatorInfo.getId()+"\",");
				tree.append("\"text\":\""+operatorInfo.getOperatorId()+"\"");
				if(i<operatorList.size()-1){
					tree.append("},");
				}else{
					tree.append("}");
				}
			}
		}
		tree.append("]");
				
		response.getWriter().print(tree.toString());
		return null;
	}
	
	/**运营商名称combobox*/
	@SystemServiceLog(description="运营商名称combobox")
	@RequestMapping(value = "/operatorName_Combobox", method = { RequestMethod.GET, RequestMethod.POST})
	public String operatorNameCombobox (HttpServletRequest request,HttpServletResponse response) throws IOException{
		log.info("删除");
		response.setContentType("text/html;charset=utf-8");	
		List<OperatorInfo> operatorList = new ArrayList<OperatorInfo>();
		operatorList=operatorInfoService.getAllOperatorName();
		StringBuffer tree=new StringBuffer();		
		tree.append("[");
		if(operatorList!=null && operatorList.size()>0) {				
			for(int i=0;i<operatorList.size();i++ ){
				OperatorInfo operatorInfo=operatorList.get(i);
				tree.append("{");
				tree.append("\"id\":\""+operatorInfo.getId()+"\",");
				tree.append("\"text\":\""+operatorInfo.getName()+"\"");
				if(i<operatorList.size()-1){
					tree.append("},");
				}else{
					tree.append("}");
				}
			}
		}
		tree.append("]");
				
		response.getWriter().print(tree.toString());
		return null;
	}
	
}
