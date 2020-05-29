package com.iber.portal.controller;

import java.io.IOException;
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
import org.springframework.web.servlet.ModelAndView;

import com.iber.portal.advices.SystemServiceLog;
import com.iber.portal.common.Data2Jsp;
import com.iber.portal.common.Pager;
import com.iber.portal.model.base.MemberLevel;
import com.iber.portal.model.member.MemberLevelRightsRelation;
import com.iber.portal.model.member.MemberRights;
import com.iber.portal.model.sys.SysUser;
import com.iber.portal.service.base.MemberLevelService;
import com.iber.portal.service.member.MemberLevelRightsRelationService;
import com.iber.portal.service.member.MemberRightsService;

/**
 * 会员等级管理
 */ 
@Controller
public class MemberLevelController extends MainController{
	
	private final static Logger log= Logger.getLogger(MemberLevelController.class);
	
	// Servrice start
	@Autowired(required=false) //自动注入，不需要生成set方法了，required=false表示没有实现类，也不会报错。
	private MemberLevelService memberLevelService; 
	
	
	@Autowired(required=false) //
	private MemberRightsService memberRightsService;
	
	@Autowired(required=false)
	MemberLevelRightsRelationService memberLevelRightsRelationService;
	/**
	 * @return
	 * @throws Exception 
	 */
	@SystemServiceLog(description="会员等级")
	@RequestMapping("/member_level_page") 
	public String memberLevel_page(HttpServletRequest request, HttpServletResponse response) {
		log.info("MemberLevel页面");
		/**查询所有的会员权益数据*/
		List<MemberRights> list  = memberRightsService.queryAllMemberRights();
		HttpSession session = request.getSession();  
	    session.setAttribute("memberRightsList",list); 
		return "member/memberLevel" ;
	}
	
	
	/**
	 * 数据
	 * @param page
	 * @param rows
	 * @return
	 * @throws IOException 
	 */
	@SystemServiceLog(description="会员等级数据")
	@RequestMapping("/dataListMemberLevel") 
	public void  datalist(int page, int rows, HttpServletRequest request, HttpServletResponse response) throws IOException{
		log.info("数据列表");
		response.setContentType("text/html;charset=utf-8");
		Map<String,Integer> pageInfo = Data2Jsp.getFristAndPageSize(page, rows);
		int from = pageInfo.get("first_page");
		int to = pageInfo.get("page_size");
		
		//获取其他查询参数
		//String title = request.getParameter("title");
		String name =request.getParameter("name");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("from", from);
		paramMap.put("to", to);
		paramMap.put("name", name);
		Pager<MemberLevel> pager = memberLevelService.queryPageList(paramMap);
		response.getWriter().print(Data2Jsp.Json2Jsp(pager));
	}
	
	/**
	 * 添加或修改数据
	 * @param MemberLevel
	 * @return
	 * @throws IOException 
	 */
	@SystemServiceLog(description="添加或修改会员等级数据")
	@RequestMapping("/saveOrUpdateMemberLevel")
	public void saveOrUpdate(MemberLevel entity,HttpServletRequest request, HttpServletResponse response) throws IOException{
		log.info("新增或更新");
		SysUser user = (SysUser) getUser(request) ;
		if(entity != null && entity.getIntegralUpLimit() == 0){
			entity.setIntegralUpLimit(null);
		}
		if(entity.getId()==null||StringUtils.isBlank(entity.getId().toString())){
			entity.setCreateBy(String.valueOf(user.getId()));
			entity.setCreateTime(new Date());
			memberLevelService.insert(entity);
		}else{
			entity.setUpdateBy(String.valueOf(user.getId())) ;
			entity.setUpdateTime(new Date()) ;
			memberLevelService.updateByPrimaryKeySelective(entity);
		}
		response.getWriter().print("success");
	}
	
	
	/**保存分配权益信息*/
	@SystemServiceLog(description="保存分配权益信息")
	@RequestMapping("/saveMemberRightsInfo")
	public void saveMemberRightsInfo(HttpServletRequest request, HttpServletResponse response) throws IOException{
	 String[] rightsIds = request.getParameterValues("rightsId");
	 String  memberLevelId = request.getParameter("memberLevelId");
	 Integer levelId =Integer.valueOf(memberLevelId);
	 /**每次保存分配会员权益之前先都删除库中 会员等级已有的数据*/
	 memberLevelRightsRelationService.deleteByLevelId(levelId);
	 
	 MemberLevelRightsRelation record = new MemberLevelRightsRelation();
	 if(rightsIds!=null){
		 for(int i=0;i<rightsIds.length;i++){
			 Integer rightsId =Integer.valueOf(rightsIds[i]);
			 record.setRightsId(rightsId);
			 record.setLevelId(levelId);
			 memberLevelRightsRelationService.insert(record); 
		 }
	 } 
		response.getWriter().print("success");
	}
	
	
	
	/**
	 * 删除数据
	 * @param 主键ID
	 * @return
	 * @throws IOException 
	 */
	@SystemServiceLog(description="删除会员等级数据")
	@RequestMapping("/deleteMemberLevelById")
	public void delete(Integer id,HttpServletRequest request,HttpServletResponse response) throws IOException{
		log.info("删除");
		if(null!=id ) {
			memberLevelService.deleteByPrimaryKey(id);
		}
		response.getWriter().print("success");
	}
}
