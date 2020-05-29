package com.iber.portal.controller.enterprise;

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
import com.iber.portal.model.enterprise.EnterpriseLevel;
import com.iber.portal.service.enterprise.EnterpriseLevelService;


@Controller
public class EnterpriseLevelController extends MainController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
    private EnterpriseLevelService enterpriseService;
	
	/**
	 * @describe 企业等级页面
	 * 
	 */
	@SystemServiceLog(description="企业等级页面")
	@RequestMapping(value = "/enterprise_level", method = { RequestMethod.GET })
	public String enterpriseLevel(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("企业等级页面");
		return "enterprise/enterpriseLevel";		
	}
	
	/**
	 * @describe 企业等级列表
	 */
	@SystemServiceLog(description="企业等级列表")
	@RequestMapping(value = "/enterprise_level_list", method = { RequestMethod.GET , RequestMethod.POST })
	public String enterpriseLevelList(int page, int rows, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("text/html;charset=utf-8");
		Map<String,Integer> pageInfo = Data2Jsp.getFristAndPageSize(page, rows);
		int from = pageInfo.get("first_page");
		int to = pageInfo.get("page_size");
		String name = request.getParameter("name");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("from", from);
		paramMap.put("to", to);
		paramMap.put("name", name);
		Pager<EnterpriseLevel> pager = enterpriseService.getAll(paramMap);
		response.getWriter().print(Data2Jsp.Json2Jsp(pager));
		return null;	
	}
	
	
	/**
	 * @describe 保存更新企业等级
	 * 
	 */
	@SystemServiceLog(description="保存更新企业等级")
	@RequestMapping(value = "/enterprise_level_saveOrUpdate", method = { RequestMethod.GET , RequestMethod.POST })
	public String enterpriseLevelSaveOrUpdate(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("text/html;charset=utf-8");
		
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		String deposit_limit = request.getParameter("deposit_limit");
		if (deposit_limit.indexOf(".") > 0) {
			deposit_limit = deposit_limit.substring(0, deposit_limit.indexOf("."));
		}
		
		String overdraft = request.getParameter("overdraft");
		if (overdraft.indexOf(".") > 0) {
			overdraft = overdraft.substring(0, overdraft.indexOf("."));
		}
		
		String member_deposit_limit = request.getParameter("member_deposit_limit");
		if (member_deposit_limit.indexOf(".") > 0) {
			member_deposit_limit = member_deposit_limit.substring(0,member_deposit_limit.indexOf("."));
		}
        String depositNumber = request.getParameter("deposit_number");

        if (id!=null && !id.equals("")) {
			EnterpriseLevel currObj = enterpriseService.selectByPrimaryKey(Integer.parseInt(id));
			if (currObj!=null) {
				currObj.setName(name);
                currObj.setDepositNumber(Integer.parseInt(depositNumber));
                currObj.setDepositLimit(Integer.parseInt(deposit_limit)*100);
				currObj.setMemberDepositLimit(Integer.parseInt(member_deposit_limit)*100);
				currObj.setOverdraft(Integer.parseInt(overdraft)*100);
				enterpriseService.updateByPrimaryKeySelective(currObj);
			}
			
		}else{		
			EnterpriseLevel obj = new EnterpriseLevel();
			obj.setName(name);
            obj.setDepositNumber(Integer.parseInt(depositNumber));
            obj.setDepositLimit(Integer.parseInt(deposit_limit)*100);
			obj.setMemberDepositLimit(Integer.parseInt(member_deposit_limit)*100);
			obj.setOverdraft(Integer.parseInt(overdraft)*100);
			enterpriseService.insertSelective(obj);
		}
		
		response.getWriter().print("success");
		return null;	
	}
	
	/**
	 * @describe 删除企业等级
	 * 
	 */
	@SystemServiceLog(description="删除企业等级")
	@RequestMapping(value = "/enterprise_level_del", method = { RequestMethod.GET , RequestMethod.POST })
	public String enterpriseLevelDel(String id, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		response.setContentType("text/html;charset=utf-8");
		
		if (id!=null && !id.equals("")) {
			enterpriseService.deleteByPrimaryKey(Integer.parseInt(id));
		}
		
		response.getWriter().print("success");
		return null;
	}
	
}
