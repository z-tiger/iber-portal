package com.iber.portal.controller;

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
import org.springframework.web.bind.annotation.RequestMethod;

import com.iber.portal.advices.SystemServiceLog;
import com.iber.portal.common.Data2Jsp;
import com.iber.portal.common.Pager;
import com.iber.portal.model.base.ParkCategory;
import com.iber.portal.model.car.CarType;
import com.iber.portal.service.network.ParkCategoryService;

/**
 * 
 * <br>
 * <b>功能：</b>XParkCategoryController<br>
 * <b>作者：</b>www.jeecg.org<br>
 * <b>日期：</b> Feb 2, 2013 <br>
 * <b>版权所有：<b>版权所有(C) 2013，www.jeecg.org<br>
 */ 
@Controller
public class ParkCategoryController{
	
	private final static Logger log= Logger.getLogger(ParkCategoryController.class);
	
	// Servrice start
	@Autowired(required=false) //自动注入，不需要生成set方法了，required=false表示没有实现类，也不会报错。
	private ParkCategoryService parkCategoryService; 
	
	/**
	 * @return
	 * @throws Exception 
	 */
	@SystemServiceLog(description="网点类型管理页面")
	@RequestMapping("/parkCategory_page") 
	public String xParkCategory_page(HttpServletRequest request, HttpServletResponse response) {
		log.info("XParkCategory页面");
		return "network/parkCategory" ;
	}
	
	
	/**
	 * 数据
	 * @param page
	 * @param rows
	 * @return
	 * @throws IOException 
	 */
	@SystemServiceLog(description="网点类型数据")
	@RequestMapping("/dataListXParkCategory") 
	public void  datalist(int page, int rows, HttpServletRequest request, HttpServletResponse response) throws IOException{
		log.info("数据列表");
		response.setContentType("text/html;charset=utf-8");
		Map<String,Integer> pageInfo = Data2Jsp.getFristAndPageSize(page, rows);
		int from = pageInfo.get("first_page");
		int to = pageInfo.get("page_size");
		
		//获取其他查询参数
		//String title = request.getParameter("title");
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("from", from);
		paramMap.put("to", to);
		 
		Pager<ParkCategory> pager = parkCategoryService.queryPageList(paramMap);
		response.getWriter().print(Data2Jsp.Json2Jsp(pager));
	}
	
	/**
	 * 添加或修改数据
	 * @param ParkCategory
	 * @return
	 * @throws IOException 
	 */
	@SystemServiceLog(description="添加或修改网点数据")
	@RequestMapping("/saveOrUpdateXParkCategory")
	public void saveOrUpdate(ParkCategory entity,HttpServletRequest request, HttpServletResponse response) throws IOException{
		log.info("新增或更新");
		if(entity.getId()==null||StringUtils.isBlank(entity.getId().toString())){
			parkCategoryService.insert(entity);
		}else{
			parkCategoryService.updateByPrimaryKey(entity);
		}
		response.getWriter().print("success");
	}
	
	/**
	 * 删除数据
	 * @param 主键ID
	 * @return
	 * @throws IOException 
	 */
	@SystemServiceLog(description="删除网点数据")
	@RequestMapping("/deleteXParkCategoryById")
	public void delete(Integer id,HttpServletRequest request,HttpServletResponse response) throws IOException{
		log.info("删除");
		if(null!=id ) {
			parkCategoryService.deleteByPrimaryKey(id);
		}
		response.getWriter().print("success");
	}
	
	/**查询全部的网点类型combobox*/
	@SystemServiceLog(description="查询全部的网点类型")
	@RequestMapping(value = "/parkCategoryCombobox", method = { RequestMethod.GET, RequestMethod.POST})
	public String parkCategoryCombobox(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("text/html;charset=utf-8");	
		List<ParkCategory> data = parkCategoryService.queryAllParkCategoryList() ;
		StringBuffer tree=new StringBuffer();		
		tree.append("[");
		if(data!=null && data.size()>0) {				
			for(int i=0;i<data.size();i++ ){
				ParkCategory ptype=data.get(i);
				tree.append("{");
				tree.append("\"id\":\""+ptype.getId()+"\",");
				tree.append("\"text\":\""+ptype.getName()+"\"");
				if(i<data.size()-1){
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
