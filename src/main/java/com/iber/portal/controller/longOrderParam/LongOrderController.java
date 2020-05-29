package com.iber.portal.controller.longOrderParam;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.iber.portal.advices.SystemServiceLog;
import com.iber.portal.common.Data2Jsp;
import com.iber.portal.common.Pager;
import com.iber.portal.controller.MainController;
import com.iber.portal.model.base.MemberLevel;
import com.iber.portal.model.car.CarType;
import com.iber.portal.model.longOrderParam.LongOrderParam;
import com.iber.portal.model.sys.SysUser;
import com.iber.portal.service.longOrder.LongOrderService;
import com.iber.portal.vo.longOrderParam.LongOrderParamVo;

@Controller
public class LongOrderController extends MainController{

	@Autowired
	private LongOrderService longOrderService;
	
	@SystemServiceLog(description = "超长订单参数设置页面")
	@RequestMapping(value = "longOrderSet_page" , method = { RequestMethod.GET, RequestMethod.POST })
	public String invoiceManagePage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return "longOrder/longOrder";
	}
	
	@SystemServiceLog(description = "超长订单参数设置列表")
	@RequestMapping(value ="longOrder_list", method = { RequestMethod.GET, RequestMethod.POST })
	public void longOrder_list(int page, int rows, HttpServletRequest request, HttpServletResponse response)throws Exception{
		response.setContentType("text/html;charset=utf-8");
		Map<String,Integer> pageInfo = Data2Jsp.getFristAndPageSize(page, rows);
		//获取其他查询参数
		Map<String, Object> paramMap = new HashMap<String, Object>();
		String levelCode = request.getParameter("levelCode");
		String carTypeId = request.getParameter("carTypeId");
		String cityCode = request.getParameter("cityCode");
				
		paramMap.put("cityCode", cityCode);
		paramMap.put("levelCode", levelCode);
		paramMap.put("carTypeId", carTypeId);
		paramMap.put("offset", pageInfo.get("first_page"));
		paramMap.put("rows", pageInfo.get("page_size"));
		Pager<LongOrderParamVo> pager = longOrderService.queryPageList(paramMap);
		response.getWriter().print(Data2Jsp.Json2Jsp(pager));
	}
	
	@ResponseBody
	@SystemServiceLog(description = "保存超长设置参数")
	@RequestMapping(value="/saveOrUpdateLongOrderParam", method= {RequestMethod.GET,RequestMethod.POST})
	public Map<String, Object> saveOrUpdateLongOrderParam(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		SysUser user = getUser(request);
		String id = request.getParameter("id");
		String cityCode = request.getParameter("cityCode");
		String levelCode = request.getParameter("levelCode");
		String carTypeId = request.getParameter("carTypeId");
	 	String moneyLimit = request.getParameter("budgetAmount");
		String timeLimit = request.getParameter("budgetTime");
		LongOrderParam longOrderParam = new LongOrderParam();
		longOrderParam.setCreateId(user.getId()); 
		longOrderParam.setCreateTime(new Date());
		longOrderParam.setValidStatus(1);
		
		if(StringUtils.isNotBlank(id)) {
			longOrderParam = longOrderService.selectById(Integer.parseInt(id));
			if(longOrderParam == null) {
				map.put("status", "fail");
				map.put("msg", "没有找到对应的数据，请刷新后再试");
				return map;
			}
		}
		
		if(StringUtils.isNotBlank(moneyLimit))
			longOrderParam.setBudgetAmount((int)(Double.parseDouble(moneyLimit)*100));
		if(StringUtils.isNotBlank(cityCode))
			longOrderParam.setCityCode(cityCode);
		if(StringUtils.isNotBlank(levelCode))
			longOrderParam.setLevelCode(Integer.parseInt(levelCode));
		if(StringUtils.isNotBlank(carTypeId))
			longOrderParam.setCarTypeId(Integer.parseInt(carTypeId));
		if(StringUtils.isNotBlank(timeLimit))
			longOrderParam.setBudgetTime(Float.parseFloat(timeLimit));
		
		//判断数据库中是否有相同数据
		int records = longOrderService.selectLongOrderParamRecords(longOrderParam.getLevelCode(),
				longOrderParam.getCarTypeId(), longOrderParam.getCityCode(), id);
		if(records > 0) {
			map.put("status", "fail");
			map.put("msg", "已有相同数据，请勿重复添加");
			return map;
		}
		
		int res = 0;
		if(longOrderParam.getId() != null) {
			 res = longOrderService.updateById(longOrderParam);
		}else {
			res = longOrderService.saveLongOrderParam(longOrderParam);
		}
		if(res> 0) {
			map.put("status", "success");
			map.put("msg", "保存成功");
		}else {
			map.put("status", "fail");
			map.put("msg", "网络异常，请稍后再试！");
		}
		return map;
	}
	
	@SystemServiceLog(description = "会员等级")
	@RequestMapping(value = "/sys_optional_memberLevelCombobox", method = { RequestMethod.GET, RequestMethod.POST })
	public void sys_optional_memberLevelCombobox(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("text/html;charset=utf-8");
		StringBuffer tree = new StringBuffer();
		tree.append("[");
		List<MemberLevel> memberLevelList = longOrderService.selectAllMemberLevel();
		if (memberLevelList != null && memberLevelList.size() > 0) {
			for (int i = 0; i < memberLevelList.size(); i++) {
				if(i != 0) {
					tree.append(",");
				}
				MemberLevel memberLevel = memberLevelList.get(i);
				tree.append("{");
				tree.append("\"id\":\"" + memberLevel.getLevelCode() + "\",");
				tree.append("\"text\":\"" + memberLevel.getName() + "\"");
				tree.append("}");
			}
		}
		tree.append("]");
		response.getWriter().print(tree.toString());
	}
	
	@SystemServiceLog(description = "车型选择框")
	@RequestMapping(value = "/sys_optional_carTypeCombobox", method = { RequestMethod.GET, RequestMethod.POST })
	public void sys_optional_carTypeCombobox(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("text/html;charset=utf-8");
		StringBuffer tree = new StringBuffer();
		tree.append("[");
		List<CarType> carTypeList = longOrderService.selectAllCarType();
		if (carTypeList != null && carTypeList.size() > 0) {
			for (int i = 0; i < carTypeList.size(); i++) {
				if(i != 0) {
					tree.append(",");
				}
				CarType carType = carTypeList.get(i);
				tree.append("{");
				tree.append("\"id\":\"" + carType.getId() + "\",");
				tree.append("\"text\":\"" + carType.getBrance()+" "+carType.getType() + "\"");
				tree.append("}");
			}
		}
		tree.append("]");
		response.getWriter().print(tree.toString());
	}
}
