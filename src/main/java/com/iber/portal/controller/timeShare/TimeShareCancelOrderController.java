package com.iber.portal.controller.timeShare;

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
import com.iber.portal.model.sys.SysUser;
import com.iber.portal.service.timeShare.TimeShareCancelService;
import com.iber.portal.vo.timeShare.TimeShareCancelVo;

@Controller
public class TimeShareCancelOrderController extends MainController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private TimeShareCancelService timeShareCancelService;

	/**
	 * @describe 当前订单页面
	 * 
	 */
	@SystemServiceLog(description="当前订单页面")
	@RequestMapping(value = "/member_cancel_order_page", method = { RequestMethod.GET })
	public String member_cancel_order_page(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("当前订单页面跳转");
		return "timeShare/cancelOrderlist";
	}

	/**
	 * @describe 按钮列表
	 */
	@SystemServiceLog(description="按钮列表")
	@RequestMapping(value = "/cancel_order_list", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String cancel_order_list(String memberName, int page, int rows,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("text/html;charset=utf-8");
		Map<String, Integer> pageInfo = Data2Jsp
				.getFristAndPageSize(page, rows);
		SysUser user = (SysUser) request.getSession().getAttribute("user");
		Map<String, Object> record = new HashMap<String, Object>();
		if (user.getCityCode().equals("00")) {
			if (!StringUtils.isBlank(request.getParameter("cityCode"))) {
				if (request.getParameter("cityCode").equals("00")) {
					record.put("cityCode", null);
				} else {
					record.put("cityCode", request.getParameter("cityCode"));
				}
			}
		} else {
			record.put("cityCode", user.getCityCode());
		}
		record.put("memberName", memberName);
		if(!StringUtils.isBlank(request.getParameter("phoneNumber"))){
	        record.put("phoneNumber", request.getParameter("phoneNumber"));
	    }
		record.put("offset", pageInfo.get("first_page"));
		record.put("rows", pageInfo.get("page_size"));
		Pager<TimeShareCancelVo> pager = timeShareCancelService.getPagerAll(record);
		response.getWriter().print(Data2Jsp.Json2Jsp(pager));
		return null;
	}
	/**
	 * 重置会员取消订单次数
	 * @param id
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SystemServiceLog(description="重置会员取消订单次数")
	@RequestMapping(value = "/reset_member_cancel_order", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String reset_member_cancel_order(Integer id , HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("text/html;charset=utf-8");
		/**将会员约车次数重置为0*/
		timeShareCancelService.resetMemberCancelCarOrderCount(id);
		response.getWriter().print("success");
		return null ;
	}
	
	/**
	 * 重置会员取消充电预约次数
	 * @param id
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SystemServiceLog(description="重置会员取消充电订单次数")
	@RequestMapping(value = "/reset_member_cancel_charing_order", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String reset_member_cancel_charing_order(Integer id , HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("text/html;charset=utf-8");
		/**将会员约充电次数重置为0*/
		timeShareCancelService.resetMemberCancelCharingOrderCount(id);
		response.getWriter().print("success");
		return null ;
	}
}
