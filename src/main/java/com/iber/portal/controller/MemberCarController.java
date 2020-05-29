package com.iber.portal.controller;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.iber.portal.advices.SystemServiceLog;
import com.iber.portal.common.Data2Jsp;
import com.iber.portal.common.Pager;
import com.iber.portal.model.base.MemberCar;
import com.iber.portal.service.base.MemberCarService;

 
@Controller
public class MemberCarController{
	
	private final static Logger log= Logger.getLogger(MemberCarController.class);
	
	// Servrice start
	@Autowired(required=false) //自动注入，不需要生成set方法了，required=false表示没有实现类，也不会报错。
	private MemberCarService memberCarService; 
	
	/**
	 * @return
	 * @throws Exception 
	 */
	@SystemServiceLog(description="会员车辆管理页面")
	@RequestMapping("/member_car_page") 
	public String memberCar_page(HttpServletRequest request, HttpServletResponse response) {
		log.info("MemberCar页面");
		return "member/memberCar" ;
	}
	
	
	/**
	 * 数据
	 * @param page
	 * @param rows
	 * @return
	 * @throws IOException 
	 */
	@SystemServiceLog(description="会员车辆数据列表")
	@RequestMapping("/dataListMemberCar") 
	public void  datalist(int page, int rows, HttpServletRequest request, HttpServletResponse response) throws IOException{
		log.info("数据列表");
		response.setContentType("text/html;charset=utf-8");
		Map<String,Integer> pageInfo = Data2Jsp.getFristAndPageSize(page, rows);
		int from = pageInfo.get("first_page");
		int to = pageInfo.get("page_size");
		
		//获取其他查询参数
		//String title = request.getParameter("title");
		String lpn = request.getParameter("lpn");
		String checkStatus = request.getParameter("checkStatus");
		String memberName = request.getParameter("memberName");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("from", from);
		paramMap.put("to", to);
		paramMap.put("lpn", lpn);
		paramMap.put("memberName", memberName);
		paramMap.put("checkStatus", checkStatus);
		Pager<MemberCar> pager = memberCarService.queryPageList(paramMap);
		response.getWriter().print(Data2Jsp.Json2Jsp(pager));
	}
	
	/**
	 * 添加或修改数据
	 * @param MemberCar
	 * @return
	 * @throws IOException 
	 */
	@SystemServiceLog(description="会员车辆添加或修改数据")
	@RequestMapping("/memberCar_modify")
	public void saveOrUpdate(MemberCar entity,HttpServletRequest request, HttpServletResponse response) throws IOException{
		log.info("新增或更新");
		if(entity.getId()==null||StringUtils.isBlank(entity.getId().toString())){
			memberCarService.insert(entity);
		}else{
			
			memberCarService.updateByPrimaryKey(entity);
		}
		response.getWriter().print("success");
	}
	
	/**
	 * 删除数据
	 * @param 主键ID
	 * @return
	 * @throws IOException 
	 */
	@SystemServiceLog(description="删除数据")
	@RequestMapping("/deleteMemberCarById")
	public void delete(Integer id,HttpServletRequest request,HttpServletResponse response) throws IOException{
		log.info("删除");
		if(null!=id ) {
			memberCarService.deleteByPrimaryKey(id);
		}
		response.getWriter().print("success");
	}
}
