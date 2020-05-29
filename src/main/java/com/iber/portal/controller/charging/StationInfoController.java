package com.iber.portal.controller.charging;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

 
import com.iber.portal.common.Data2Jsp;
import com.iber.portal.common.Pager;
import com.iber.portal.controller.MainController;
import com.iber.portal.model.base.City;
import com.iber.portal.model.charging.EquipmentInfo;
import com.iber.portal.model.charging.StationInfo;
import com.iber.portal.model.sys.SysUser;
import com.iber.portal.service.charging.EquipmentInfoService;
import com.iber.portal.service.charging.OperatorInfoService;
import com.iber.portal.service.charging.StationInfoService;

/**
 * <b>功能：充电站信息
 */ 
@Controller
public class StationInfoController extends MainController{
	
	private final static Logger log= Logger.getLogger(StationInfoController.class);
	
	// Servrice start
	@Autowired(required=false) //自动注入，不需要生成set方法了，required=false表示没有实现类，也不会报错。
	private StationInfoService stationInfoService; 
	
	@Autowired(required=false) //自动注入，不需要生成set方法了，required=false表示没有实现类，也不会报错。
	private EquipmentInfoService equipmentInfoService; 
	
	@Autowired(required=false) 
	private OperatorInfoService operatorInfoService;
	/**
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/stationInfo_page") 
	public String stationInfo_page(HttpServletRequest request, HttpServletResponse response) {
		log.info("充电站页面");
		return "charging/stationInfo" ;
	}
	
	
	/**
	 * 充电站信息列表 
	 */
	@RequestMapping("/dataListStationInfo") 
	public void  datalist(int page, int rows, HttpServletRequest request, HttpServletResponse response) throws IOException{
		log.info("数据列表");
		SysUser user = (SysUser) getUser(request) ;
		response.setContentType("text/html;charset=utf-8");
		Map<String,Integer> pageInfo = Data2Jsp.getFristAndPageSize(page, rows);
		int from = pageInfo.get("first_page");
		int to = pageInfo.get("page_size");
		//获取其他查询参数
		//String title = request.getParameter("title");
		String address  = request.getParameter("address");
		String name = request.getParameter("name");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("from", from);
		paramMap.put("to", to);
		paramMap.put("address",address);
		paramMap.put("name", name);
		paramMap.put("cityCode",user.getCityCode());
		Pager<StationInfo> pager = stationInfoService.queryPageList(paramMap);
		response.getWriter().print(Data2Jsp.Json2Jsp(pager));
	}
	
	/**
	 * 添加或修改数据
	 * @param StationInfo
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/saveOrUpdateStationInfo")
	public void saveOrUpdate(StationInfo entity,HttpServletRequest request, HttpServletResponse response) throws IOException{
		log.info("新增或更新");
		SysUser user = (SysUser) getUser(request) ;
		String operatorName = request.getParameter("operatorId");
		int operatorId = operatorInfoService.selectIdByName(operatorName);
		
		
		if(entity.getId()==null||StringUtils.isBlank(entity.getId().toString())){
			entity.setCreateTime(new Date());
			entity.setCreateId(user.getId());
			entity.setCityCode(user.getCityCode());
			entity.setOperatorId(String.valueOf(operatorId));
			stationInfoService.insert(entity);
		}else{
			entity.setUpdateTime(new Date());
			entity.setUpdateId(user.getId());
			entity.setOperatorId(String.valueOf(operatorId));
			stationInfoService.updateByPrimaryKey(entity);
		}
		response.getWriter().print("success");
	}
	
	/**
	 * 删除数据
	 */
	@RequestMapping("/deleteStationInfoById")
	public void delete(Integer id,HttpServletRequest request,HttpServletResponse response) throws IOException{
		log.info("删除");
		if(null!=id ) {
			stationInfoService.deleteByPrimaryKey(id);
		}
		response.getWriter().print("success");
	}
	
	/**充电站列表*/
	@RequestMapping(value = "/station_Combobox", method = { RequestMethod.GET, RequestMethod.POST})
	public String stationCombobox (HttpServletRequest request,HttpServletResponse response) throws IOException{
		log.info("删除");
		response.setContentType("text/html;charset=utf-8");	
		SysUser user = (SysUser) getUser(request) ;
		HttpSession sess = request.getSession();
		List<StationInfo> stationList = new ArrayList<StationInfo>();
		stationList=stationInfoService.getAllStationByCode(user.getCityCode());
		StringBuffer tree=new StringBuffer();		
		tree.append("[");
		if(stationList!=null && stationList.size()>0) {				
			for(int i=0;i<stationList.size();i++ ){
				StationInfo station=stationList.get(i);
				tree.append("{");
				tree.append("\"id\":\""+station.getId()+"\",");
				tree.append("\"text\":\""+station.getName()+"\"");
				if(i<stationList.size()-1){
					tree.append("},");
				}else{
					tree.append("}");
				}
			}
		}
		tree.append("]");
				
		response.getWriter().print(tree.toString());
		return null;
	}
	
	/**设备附加列表*/
	@RequestMapping(value = "/equipment_attachment_preview", method = { RequestMethod.GET, RequestMethod.POST})
	public void  equipmentlist(HttpServletRequest request, HttpServletResponse response) throws IOException{
		log.info("设备附件数据列表");
		//获取其他查询参数
		//String title = request.getParameter("title");
		String id = request.getParameter("id");
		Pager<EquipmentInfo> pager = equipmentInfoService.queryAttachmentList(Integer.parseInt(id));
		response.getWriter().print(Data2Jsp.Json2Jsp(pager));
	}
	
}
