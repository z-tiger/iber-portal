package com.iber.portal.controller.enterprise;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.iber.portal.advices.SystemServiceLog;
import com.iber.portal.common.Data2Jsp;
import com.iber.portal.common.Pager;
import com.iber.portal.common.SysConstant;
import com.iber.portal.controller.MainController;
import com.iber.portal.model.enterprise.EnterpriseExtend;
import com.iber.portal.model.sys.SysUser;
import com.iber.portal.service.enterprise.EnterpriseExtendService;


@Controller
public class EnterpriseExtendController extends MainController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
    private EnterpriseExtendService enterpriseExtendService;
	
	/**
	 * @describe 企业用车订单页面
	 * 
	 */
	@SystemServiceLog(description="企业用车订单页面")
	@RequestMapping(value = "/enterprise_extend", method = { RequestMethod.GET })
	public String enterpriseExtend(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("企业用车订单页面");
		return "enterprise/enterpriseExtend";		
	}

    /**
     * 用车申请记录页面
     * @return
     */
    @SystemServiceLog(description="用车申请记录页面")
    @RequestMapping(value = "/enterprise_car_apply_record", method = { RequestMethod.GET })
    public String enterpriseCarApplyRecord() {
        return "enterprise/enterpriseCarApplyRecord";
    }
    /**
     * 用车申请记录页面
     * @return
     */
    @SystemServiceLog(description="用车超额申请记录页面")
    @RequestMapping(value = "/enterprise_over_apply_record", method = { RequestMethod.GET })
    public String enterpriseOverApplyRecord() {
        return "enterprise/enterpriseOverApplyRecord";
    }

    /**
	 * @describe 企业用车订单列表
	 */
	@SystemServiceLog(description="企业用车订单列表")
	@RequestMapping(value = "/enterprise_extend_list", method = { RequestMethod.GET , RequestMethod.POST })
	public String enterpriseExtendList(int page, int rows, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("企业用车订单列表");
		response.setContentType("text/html;charset=utf-8");
		Map<String,Integer> pageInfo = Data2Jsp.getFristAndPageSize(page, rows);
		int from = pageInfo.get("first_page");
		int to = pageInfo.get("page_size");
		String city_code = request.getParameter("city_code");
		String enterpriseName = request.getParameter("enterprise_name");
		String memberName = request.getParameter("member_name");
		String payType = request.getParameter("pay_type");
		String checkStatus = request.getParameter("check_status");
		String queryDateFrom = request.getParameter("queryDateFrom");
		String queryDateTo = request.getParameter("queryDateTo");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		if(StringUtils.isNotBlank(city_code)&&!StringUtils.equals(city_code, "00")){
			paramMap.put("cityCode", city_code);
		}else{
			//城市过滤
			SysUser user = (SysUser) request.getSession().getAttribute("user");
			String cityCode = null;
			if(!user.getCityCode().equals("00")){
				cityCode = user.getCityCode() ;
			}
			paramMap.put("cityCode", cityCode);
		}
		//paramMap.put("cityCode", cityCode);
		paramMap.put("from", from);
		paramMap.put("to", to);
		paramMap.put("enterpriseName", enterpriseName);
		paramMap.put("memberName", memberName);
		paramMap.put("payType", payType);
		paramMap.put("checkStatus", checkStatus);
		paramMap.put("queryDateFrom", queryDateFrom);
		paramMap.put("queryDateTo", queryDateTo);
		Pager<EnterpriseExtend> pager = enterpriseExtendService.getAll(paramMap);
		response.getWriter().print(Data2Jsp.Json2Jsp(pager));
		return null;	
	}
	
	
	/**
	 * @describe 审核通过
	 * 
	 */
	@SystemServiceLog(description="企业用车订单审核通过")
	@RequestMapping(value = "/enterprise_extend_check_success", method = { RequestMethod.GET , RequestMethod.POST })
	public String EnterpriseExtendCheckSuccess(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("text/html;charset=utf-8");
		log.info("企业用车订单审核通过");
		String id = request.getParameter("id");
		
		if (id!=null && !id.equals("")) {
			EnterpriseExtend currObj = enterpriseExtendService.selectByPrimaryKey(Integer.parseInt(id));
			if (currObj!=null) {
				currObj.setCheckStatus(SysConstant.ENTERPRISE_CHECK_STATUS_CHECKSUCC);
				currObj.setPayType(SysConstant.ENTERPRISE_PAY);
				/** 扣除企业帐号内金额 */
				enterpriseExtendService.updateByEnterprise(currObj);
				enterpriseExtendService.updateByPrimaryKeySelective(currObj);
			}
			
		}
		response.getWriter().print("success");
		return null;	
	}
	
	/**
	 * @describe 审核不通过
	 * 
	 */
	@SystemServiceLog(description="企业用车订单审核不通过")
	@RequestMapping(value = "/enterprise_extend_check_fail", method = { RequestMethod.GET , RequestMethod.POST })
	public String EnterpriseExtendCheckFail(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("text/html;charset=utf-8");
		log.info("企业用车订单审核不通过");
		String id = request.getParameter("id");
		
		if (id!=null && !id.equals("")) {
			EnterpriseExtend currObj = enterpriseExtendService.selectByPrimaryKey(Integer.parseInt(id));
			if (currObj!=null) {
				currObj.setCheckStatus(SysConstant.ENTERPRISE_CHECK_STATUS_CHECKFAIL);
				currObj.setPayType(SysConstant.MYSELF_PAY);
				/** 扣除个人帐号内金额 */
				enterpriseExtendService.updateByMember(currObj);
				enterpriseExtendService.updateByPrimaryKeySelective(currObj);
			}
			
		}
		response.getWriter().print("success");
		return null;	
	}
	
	
	
}
