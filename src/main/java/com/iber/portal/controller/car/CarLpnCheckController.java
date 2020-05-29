package com.iber.portal.controller.car;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.iber.portal.getui.PushClient;
import com.iber.portal.getui.PushCommonBean;
import com.iber.portal.model.car.CarLpnCheck;
import com.iber.portal.model.sys.SysUser;
import com.iber.portal.service.car.CarLpnCheckService;
import com.iber.portal.vo.car.CarCheckVo;


@Controller
public class CarLpnCheckController extends MainController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
    private CarLpnCheckService carLpnCheckService;
	
	/**
	 * @describe 车牌审核页面
	 * 
	 */
	@SystemServiceLog(description="车牌审核页面")
	@RequestMapping(value = "/car_lpn_check_page", method = { RequestMethod.GET })
	public String carLpnCheck(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("车牌审核页面");
		return "car/carLpnCheck";		
	}
	
	/**
	 * @describe 车牌审核列表
	 */
	@SystemServiceLog(description="车牌审核列表")
	@RequestMapping(value = "/car_lpn_check_list", method = { RequestMethod.GET , RequestMethod.POST })
	public String carLpnCheckList(int page, int rows, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("车牌审核列表");
		response.setContentType("text/html;charset=utf-8");
		Map<String,Integer> pageInfo = Data2Jsp.getFristAndPageSize(page, rows);
		int from = pageInfo.get("first_page");
		int to = pageInfo.get("page_size");
		String lpn = request.getParameter("lpn");
		String status = request.getParameter("status");
		String queryDateFrom = request.getParameter("queryDateFrom");
		String queryDateTo = request.getParameter("queryDateTo");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("from", from);
		paramMap.put("to", to);
		paramMap.put("lpn", lpn);
		paramMap.put("status", status);
		paramMap.put("queryDateFrom", queryDateFrom);
		paramMap.put("queryDateTo", queryDateTo);
		Pager<CarLpnCheck> pager = carLpnCheckService.getAll(paramMap);
		response.getWriter().print(Data2Jsp.Json2Jsp(pager));
		return null;	
	}
	
	
	/**
	 * @describe 保存更新车牌审核
	 * 
	 */
	@SystemServiceLog(description="保存更新车牌审核")
	@RequestMapping(value = "/car_lpn_check_saveOrUpdate", method = { RequestMethod.GET , RequestMethod.POST })
	public String carLpnCheckSaveOrUpdate(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("保存更新车牌审核");
		response.setContentType("text/html;charset=utf-8");
		String id = request.getParameter("id");
		
		if (id!=null && !id.equals("")) {
			CarLpnCheck currObj = carLpnCheckService.selectByPrimaryKey(Integer.parseInt(id));
			if (currObj!=null) {
				carLpnCheckService.updateByPrimaryKeySelective(currObj);
			}
		}else{	
			CarLpnCheck obj = new CarLpnCheck();
			carLpnCheckService.insertSelective(obj);
	
		}
		response.getWriter().print("success");
		return null;	
	}
	
	/**
	 * @describe 删除车牌审核
	 * 
	 */
	@SystemServiceLog(description="删除车牌审核")
	@RequestMapping(value = "/car_lpn_check_del", method = { RequestMethod.GET , RequestMethod.POST })
	public String carLpnCheckDel(String id, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("删除车牌审核");
		response.setContentType("text/html;charset=utf-8");
		if (id!=null && !id.equals("")) {
			carLpnCheckService.deleteByPrimaryKey(Integer.parseInt(id));
		}
		response.getWriter().print("success");
		return null;
	}
	
	/**
	 * @describe 检查个推是否存在该车牌
	 * 
	 */
	@SystemServiceLog(description="检查个推是否存在该车牌")
	@RequestMapping(value = "/car_lpn_check_is_exist", method = { RequestMethod.GET , RequestMethod.POST })
	public String carLpnCheckDelIsExist(String lpn, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("检查个推是否存在该车牌");
		response.setContentType("text/html;charset=utf-8");
		String msg = "success";
		if (lpn!=null && !lpn.equals("")) {
			//查询个推服务器是否存在该车
			List<String> alias = PushClient.queryClientId(lpn);
			if(!alias.isEmpty() && alias.size()> 0){
				msg = "fail";
			}
		}
		response.getWriter().print(msg);
		return null;
	}
	
	/**
	 * @describe 车牌审核
	 * 
	 */
	@SystemServiceLog(description="车牌审核")
	@RequestMapping(value = "/car_lpn_check_checking", method = { RequestMethod.GET , RequestMethod.POST })
	public String carLpnCheckSuccess(String id, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("车牌审核通过");
		response.setContentType("text/html;charset=utf-8");
		if (id!=null && !id.equals("")) {
			String remark = request.getParameter("remark");
			String status = request.getParameter("status");
			SysUser user = getUser(request);
			CarLpnCheck carLpnCheck	= carLpnCheckService.selectByPrimaryKey(Integer.parseInt(id));
			carLpnCheck.setStatus(status);
			carLpnCheck.setRemark(remark);
			carLpnCheck.setCheckTime(new Date());
			carLpnCheck.setCheckUser(user.getName());
			
			//绑定个推
			PushClient.bindAlias(carLpnCheck.getCid(), carLpnCheck.getLpn().replace("•", "")+"_CHECK");
			
			CarCheckVo carCheckVo = new CarCheckVo();
			carCheckVo.setStatus(status);
			carCheckVo.setRemark(remark);
			carCheckVo.setAlias(carLpnCheck.getLpn().replace("•", "")+"_CHECK");
			carCheckVo.setLpn(carLpnCheck.getLpn().replace("•", ""));
			//推送车载
			if("checkSucc".equals(status)){
				PushCommonBean push = new PushCommonBean("server_push_car_check_lpn","1","审核成功",carCheckVo);
				PushClient.push(carLpnCheck.getCid(), net.sf.json.JSONObject.fromObject(push));
			}else{
				PushCommonBean push = new PushCommonBean("server_push_car_check_lpn","1","车牌失败",carCheckVo);
				PushClient.push(carLpnCheck.getCid(), net.sf.json.JSONObject.fromObject(push));
			}
			carLpnCheckService.updateByPrimaryKeySelective(carLpnCheck);
			
			
		}
		response.getWriter().print("success");
		return null;
	}
	
}
