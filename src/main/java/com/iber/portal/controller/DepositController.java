package com.iber.portal.controller;

import com.iber.portal.advices.SystemServiceLog;
import com.iber.portal.common.Data2Jsp;
import com.iber.portal.model.deposit.Deposit;
import com.iber.portal.model.sys.SysUser;
import com.iber.portal.service.deposit.DepositService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class DepositController extends MainController {

	@Autowired
	private DepositService depositService;
	/**查询押金列表
	 *
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SystemServiceLog(description="押金页面")
	@RequestMapping(value = "/deposit_page", method = { RequestMethod.GET })
	public String depositPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return "deposit/deposit";
	}
	/**
	 * 查询押金列表
	 * @param page
	 * @param rows
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SystemServiceLog(description="查询押金列表 ")
	@RequestMapping(value = "/deposit_list", method = { RequestMethod.GET, RequestMethod.POST })
	public String depositList(int page, int rows, HttpServletRequest request, HttpServletResponse response)throws Exception {
		response.setContentType("text/html;charset=utf-8");
		Map<String,Integer> pageInfo = Data2Jsp.getFristAndPageSize(page, rows);
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("offset", pageInfo.get("first_page"));
		map.put("rows", pageInfo.get("page_size"));
		String driverAge=request.getParameter("driverAge");
		String sesameCredit=request.getParameter("sesameCredit");
		String memberLevel=request.getParameter("memberLevel");
		map.put("driverAge", driverAge);
		map.put("sesameCredit", sesameCredit);
		map.put("memberLevel", memberLevel);
		List<Deposit> data = depositService.selectAll(map);
		int totalRecords = depositService.selectAllRecords(map);
		String json = Data2Jsp.Json2Jsp(data, totalRecords);
		response.getWriter().print(json);
		return null;
	}
	/**添加和修改
	 *
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SystemServiceLog(description="添加和修改 ")
	@RequestMapping(value = "/deposit_add_update", method = { RequestMethod.GET, RequestMethod.POST })
	public String depositList(HttpServletRequest request, HttpServletResponse response)throws Exception {
		response.setContentType("text/html;charset=utf-8");
		String id=request.getParameter("id");
		String driverAge=request.getParameter("driverAge");
		String sesameCredit=request.getParameter("sesameCredit");
		String memberLevel=request.getParameter("memberLevel");
		String depositValue=request.getParameter("depositValue");
		String detail=request.getParameter("detail");
		SysUser sysUser = (SysUser) request.getSession().getAttribute("user");
		if(id != null && !id.equals("")){
			Deposit deposit=new Deposit();
			deposit.setId(Integer.parseInt(id));
			deposit.setDriverAge(driverAge);
			deposit.setSesameCredit(sesameCredit);
			deposit.setMemberLevel(memberLevel);
			deposit.setDepositValue(Integer.parseInt(depositValue));
			deposit.setDetail(detail);
			deposit.setUpdateId(sysUser.getId());
			deposit.setUpdateTime(new Date());
			depositService.updateDeposit(deposit);
		}else{
			Deposit deposit=new Deposit();
			//deposit.setId(Integer.parseInt(id));
			deposit.setDriverAge(driverAge);
			deposit.setSesameCredit(sesameCredit);
			deposit.setMemberLevel(memberLevel);
			deposit.setDepositValue(Integer.parseInt(depositValue));
			deposit.setDetail(detail);
			deposit.setCreateId(sysUser.getId());
			deposit.setCreateTime(new Date());
			depositService.addDeposit(deposit);
		}
		response.getWriter().print("success");
		return null;
	}
	/**
	 * 删除记录
	 * @param id
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SystemServiceLog(description="删除记录")
	@RequestMapping(value = "/delete_deposit", method = { RequestMethod.GET , RequestMethod.POST })
	public String deleteDeposit(String id, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("text/html;charset=utf-8");

		if (id!=null && !id.equals("")) {
			depositService.deleteByPrimaryKey(Integer.parseInt(id));
		}
		response.getWriter().print("success");
		return null;
	}
}
