package com.iber.portal.controller.member;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
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
import com.iber.portal.model.member.MemberLevelRightsRelation;
import com.iber.portal.service.member.MemberLevelRightsRelationService;

/**
 * 
 * <br>
 * <b>功能：</b>会员等级权益<br>
 * <b>作者：</b>xyq<br>
 * <b>日期：</b> 2016.12.30 <br>
 */ 
@Controller
public class MemberLevelRightsRelationController{
	
	private final static Logger log= Logger.getLogger(MemberLevelRightsRelationController.class);
	
	// Servrice start
	@Autowired(required=false) //自动注入，不需要生成set方法了，required=false表示没有实现类，也不会报错。
	private MemberLevelRightsRelationService memberLevelRightsRelationService; 
	
	/**
	 * @return
	 * @throws Exception 
	 */
	@SystemServiceLog(description="会员等级权益页面")
	@RequestMapping("/memberLevelRightsRelation_page") 
	public String memberLevelRightsRelation_page(HttpServletRequest request, HttpServletResponse response) {
		log.info("MemberLevelRightsRelation页面");
		return "member/memberLevelRightsRelation" ;
	}
	
	
	/**
	 * 数据列表
	 * @param page
	 * @param rows
	 * @return
	 * @throws IOException 
	 */
	@SystemServiceLog(description="会员等级权益数据")
	@RequestMapping("/dataListMemberLevelRightsRelation") 
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
		 
		Pager<MemberLevelRightsRelation> pager = memberLevelRightsRelationService.queryPageList(paramMap);
		response.getWriter().print(Data2Jsp.Json2Jsp(pager));
	}
	
	/**
	 * 添加或修改数据
	 * @param MemberLevelRightsRelation
	 * @return
	 * @throws IOException 
	 */
	@SystemServiceLog(description="会员等级权益添加或修改数据")
	@RequestMapping("/saveOrUpdateMemberLevelRightsRelation")
	public void saveOrUpdate(MemberLevelRightsRelation entity,HttpServletRequest request, HttpServletResponse response) throws IOException{
		log.info("新增或更新");
		if(entity.getId()==null||StringUtils.isBlank(entity.getId().toString())){
			memberLevelRightsRelationService.insert(entity);
		}else{
			memberLevelRightsRelationService.updateByPrimaryKey(entity);
		}
		response.getWriter().print("success");
	}
	
	/**
	 * 删除数据
	 * @param 主键ID
	 * @return
	 * @throws IOException 
	 */
	@SystemServiceLog(description="会员等级权益删除数据")
	@RequestMapping("/deleteMemberLevelRightsRelationById")
	public void delete(Integer id,HttpServletRequest request,HttpServletResponse response) throws IOException{
		log.info("删除");
		if(null!=id ) {
			memberLevelRightsRelationService.deleteByPrimaryKey(id);
		}
		response.getWriter().print("success");
	}
}
