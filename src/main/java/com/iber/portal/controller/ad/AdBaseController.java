package com.iber.portal.controller.ad;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.iber.portal.advices.SystemServiceLog;
import com.iber.portal.common.Data2Jsp;
import com.iber.portal.common.Pager;
import com.iber.portal.controller.MainController;
import com.iber.portal.model.ad.AdBase;
import com.iber.portal.model.sys.SysUser;
import com.iber.portal.service.ad.AdBaseService;


@Controller
public class AdBaseController extends MainController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
    private AdBaseService adBaseService;
	
	/**
	 * @describe 广告页面
	 * 
	 */
	@SystemServiceLog(description="广告页面")
	@RequestMapping(value = "/ad_base_page", method = { RequestMethod.GET })
	public String adBase(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("广告页面");
		return "ad/adBase";		
	}
	
	/**
	 * @describe 广告列表
	 */
	@SystemServiceLog(description="广告列表")
	@RequestMapping(value = "/ad_base_list", method = { RequestMethod.GET , RequestMethod.POST })
	public String adBaseList(int page, int rows, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("广告列表");
		response.setContentType("text/html;charset=utf-8");
		Map<String,Integer> pageInfo = Data2Jsp.getFristAndPageSize(page, rows);
		int from = pageInfo.get("first_page");
		int to = pageInfo.get("page_size");
		
		String adPid = request.getParameter("adpid");
		String isShow = request.getParameter("is_show");
		String title = request.getParameter("title");
		//String cityCode = request.getParameter("area_code");
		String queryDateFrom = request.getParameter("queryDateFrom");
		String queryDateTo = request.getParameter("queryDateTo");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		SysUser user = (SysUser) request.getSession().getAttribute("user");
		if(user.getCityCode().equals("00")){
	       	 if(!StringUtils.isBlank(request.getParameter("cityCode"))){
	         	if(request.getParameter("cityCode").equals("00")){
	         		paramMap.put("cityCode", null);
	         	}else{
	         		paramMap.put("cityCode", request.getParameter("cityCode"));
	         	}
	         }
       }else{
    	   paramMap.put("cityCode", user.getCityCode());
       }
		paramMap.put("from", from);
		paramMap.put("to", to);
		paramMap.put("adPid", adPid);
		paramMap.put("isShow", isShow);
		paramMap.put("title", title);
		//paramMap.put("cityCode", cityCode);
		paramMap.put("queryDateFrom", queryDateFrom);
		paramMap.put("queryDateTo", queryDateTo);
		Pager<AdBase> pager = adBaseService.getAll(paramMap);
		response.getWriter().print(Data2Jsp.Json2Jsp(pager));
		return null;	
	}
	
	
	/**
	 * @describe 保存更新广告
	 * 
	 */
	@SystemServiceLog(description="保存更新广告")
	@RequestMapping(value = "/ad_base_saveOrUpdate", method = { RequestMethod.GET , RequestMethod.POST })
	public String adBaseSaveOrUpdate(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("保存更新广告");
		response.setContentType("text/html;charset=utf-8");
		String id = request.getParameter("id");
		String title = request.getParameter("title");
		String simpleContext = request.getParameter("simple_context");
		String isShow = request.getParameter("is_show");
		String adpId = request.getParameter("adpid");
		String pointId = request.getParameter("pointid");
		if("".equals(pointId) || null==pointId){
			pointId = "0";
		}
		
		if (id!=null && !id.equals("")) {
			AdBase currObj = adBaseService.selectByPrimaryKey(Integer.parseInt(id));
			if (currObj!=null) {
				currObj.setAdPid(Integer.parseInt(adpId));
				currObj.setCreateTime(new Date());
				currObj.setIsShow(isShow);
				currObj.setPointId(Integer.parseInt(pointId));
				currObj.setSimpleContext(simpleContext);
				currObj.setTitle(title);
				adBaseService.updateByPrimaryKeySelective(currObj);
			}
		}else{	
			AdBase obj = new AdBase();
			obj.setAdPid(Integer.parseInt(adpId));
			obj.setCreateTime(new Date());
			obj.setIsShow(isShow);
			obj.setPointId(Integer.parseInt(pointId));
			obj.setSimpleContext(simpleContext);
			obj.setTitle(title);
			adBaseService.insertSelective(obj);
	
		}
		response.getWriter().print("success");
		return null;	
	}
	
	/**
	 * @describe 删除广告
	 * 
	 */
	@SystemServiceLog(description="删除广告")
	@RequestMapping(value = "/ad_base_del", method = { RequestMethod.GET , RequestMethod.POST })
	public String adBaseDel(String id, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("删除广告");
		response.setContentType("text/html;charset=utf-8");
		if (id!=null && !id.equals("")) {
			adBaseService.deleteByPrimaryKey(Integer.parseInt(id));
		}
		response.getWriter().print("success");
		return null;
	}
	
	/**
	 * @describe 广告位广告列表
	 */
	@SystemServiceLog(description="广告位广告列表")
	@RequestMapping(value = "/ad_base_preview", method = { RequestMethod.GET , RequestMethod.POST })
	public String adBasePreview(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("广告位广告列表");
		response.setContentType("text/html;charset=utf-8");
		String adPid = request.getParameter("id");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("adPid", adPid);
		Pager<AdBase> pager = adBaseService.getPreview(paramMap);
		response.getWriter().print(Data2Jsp.Json2Jsp(pager));
		return null;	
	}
	
}
