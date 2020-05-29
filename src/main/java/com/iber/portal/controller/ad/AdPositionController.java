package com.iber.portal.controller.ad;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
import com.iber.portal.getui.PushClient;
import com.iber.portal.getui.PushCommonBean;
import com.iber.portal.model.ad.AdPosition;
import com.iber.portal.model.car.Car;
import com.iber.portal.model.sys.SysUser;
import com.iber.portal.service.ad.AdPositionService;
import com.iber.portal.service.car.CarService;


@Controller
public class AdPositionController extends MainController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
    private AdPositionService adPositionService;
	
	@Autowired
	private CarService carService;
	
	/**
	 * @describe 广告位页面
	 * 
	 */
	@SystemServiceLog(description="广告位页面")
	@RequestMapping(value = "/ad_position_page", method = { RequestMethod.GET })
	public String adPosition(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("广告位页面");
		return "ad/adPosition";		
	}
	
	/**
	 * @describe 广告位列表
	 */
	@SystemServiceLog(description="广告位列表")
	@RequestMapping(value = "/ad_position_list", method = { RequestMethod.GET , RequestMethod.POST })
	public String adPositionList(int page, int rows, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("广告位列表");
		response.setContentType("text/html;charset=utf-8");
		Map<String,Integer> pageInfo = Data2Jsp.getFristAndPageSize(page, rows);
		int from = pageInfo.get("first_page");
		int to = pageInfo.get("page_size");
		
		String name = request.getParameter("adpname");
		//String city_code = request.getParameter("area_code");
		String isShow = request.getParameter("adpstatus");
		String adppage = request.getParameter("adppage");
		String dateFrom = request.getParameter("queryDateFrom");
		String dateTo = request.getParameter("queryDateTo");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		SysUser user = (SysUser) request.getSession().getAttribute("user");
		if(user.getCityCode().equals("00")){
	       	 if(!StringUtils.isBlank(request.getParameter("area_code"))){
	         	if(request.getParameter("cityCode").equals("00")){
	         		paramMap.put("cityCode", null);
	         	}else{
	         		paramMap.put("cityCode", request.getParameter("area_code"));
	         	}
	         }
       }else{
    	   paramMap.put("cityCode", user.getCityCode());
       }
		paramMap.put("from", from);
		paramMap.put("to", to);
		paramMap.put("name", name);
		//paramMap.put("cityCode", city_code);
		paramMap.put("isShow", isShow);
		paramMap.put("page", adppage);
		paramMap.put("dateFrom", dateFrom);
		paramMap.put("dateTo", dateTo);
		Pager<AdPosition> pager = adPositionService.getAll(paramMap);
		response.getWriter().print(Data2Jsp.Json2Jsp(pager));
		return null;	
	}
	
	
	/**
	 * @describe 保存更新广告位
	 * 
	 */
	@SystemServiceLog(description="保存更新广告位")
	@RequestMapping(value = "/ad_position_saveOrUpdate", method = { RequestMethod.GET , RequestMethod.POST })
	public String adPositionSaveOrUpdate(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("保存更新广告位");
		response.setContentType("text/html;charset=utf-8");
		String id = request.getParameter("id");
		String name = request.getParameter("add_adpname");
		String cityCode = request.getParameter("add_adarea");
		String page = request.getParameter("add_adppage");
		String isShow = request.getParameter("add_adstatus");
		String describe = request.getParameter("add_adpdescribe");
		String positionNo = request.getParameter("add_position_no");
		
		if (id!=null && !id.equals("")) {
			AdPosition currObj = adPositionService.selectByPrimaryKey(Integer.parseInt(id));
			if (currObj!=null) {
				currObj.setName(name);
				currObj.setCreateTime(new Date());
				currObj.setCityCode(cityCode);
				currObj.setDescribe(describe);
				currObj.setIsShow(isShow);
				currObj.setPage(page);
				currObj.setPositionNo(Integer.parseInt(positionNo));
				adPositionService.updateByPrimaryKeySelective(currObj);
			}
		}else{	
			AdPosition obj = new AdPosition();
			obj.setName(name);
			obj.setCreateTime(new Date());
			obj.setCityCode(cityCode);
			obj.setDescribe(describe);
			obj.setIsShow(isShow);
			obj.setPage(page);
			obj.setPositionNo(Integer.parseInt(positionNo));
			adPositionService.insertSelective(obj);
		}
		response.getWriter().print("success");
		return null;	
	}
	
	/**
	 * @describe 删除广告位
	 * 
	 */
	@SystemServiceLog(description="删除广告位")
	@RequestMapping(value = "/ad_position_del", method = { RequestMethod.GET , RequestMethod.POST })
	public String adPositionDel(String id, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("删除广告位");
		response.setContentType("text/html;charset=utf-8");
		if (id!=null && !id.equals("")) {
			adPositionService.deleteByPrimaryKey(Integer.parseInt(id));
		}
		response.getWriter().print("success");
		return null;
	}
	/**
	 * @describe 推送车载广告位
	 * 
	 */
	@SystemServiceLog(description="推送车载广告位")
	@RequestMapping(value = "/ad_position_push_car", method = { RequestMethod.GET , RequestMethod.POST })
	public String adPositionPushCar(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("推送车载广告位");
		response.setContentType("text/html;charset=utf-8");
		PushCommonBean push = new PushCommonBean("server_push_car_ad_update","1","车载广告更新","");
		HashMap<String, Object> map = new HashMap<String, Object>();
		String cityCode = request.getParameter("cityCode");
		if("00".equals(cityCode)){
			cityCode = "";
		}
		map.put("cityCode", cityCode);
		List<Car> carList = carService.selectAllPushCar(map);
		if(carList.size()>0){
			for(int i=0;i<carList.size();i++){
				Car car = carList.get(i);
				List<String> alias = PushClient.queryClientId(car.getLpn());
				if(!alias.isEmpty() && alias.size()> 0){
					for(String cid : alias){
						PushClient.push(cid, net.sf.json.JSONObject.fromObject(push));
					}
				}
			}
		}
		response.getWriter().print("success");
		return null;
	}
	
	
	
	
}
