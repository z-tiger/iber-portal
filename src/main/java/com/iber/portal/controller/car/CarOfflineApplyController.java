package com.iber.portal.controller.car;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.iber.portal.getui.PushClientEmployee;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


import com.alibaba.fastjson.JSON;
import com.iber.portal.advices.SystemServiceLog;
import com.iber.portal.common.Data2Jsp;

import com.iber.portal.common.ExcelUtil;
import com.iber.portal.common.JsonDateValueProcessor;
import com.iber.portal.common.ResultMsg;
import com.iber.portal.common.SysConstant;
import com.iber.portal.controller.MainController;
import com.iber.portal.dao.car.CarPreOfflineMapper;
import com.iber.portal.dao.car.CarRepairMapper;
import com.iber.portal.getui.PushCommonBean;
import com.iber.portal.model.car.CarOfflineApply;
import com.iber.portal.model.car.CarPreOffline;
import com.iber.portal.model.car.CarRepair;
import com.iber.portal.model.car.CarRun;

import com.iber.portal.model.sys.SysUser;

import com.iber.portal.service.car.CarOfflineApplyService;
import com.iber.portal.service.car.CarRepairService;
import com.iber.portal.service.car.CarRunService;
import com.iber.portal.service.car.CarService;
import com.iber.portal.util.SystemOperationUtil;



/**
 * 车辆下线申请管理
 */
@Controller
public class CarOfflineApplyController extends MainController {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private CarOfflineApplyService carOfflineApplyService;
	
	@Autowired
	private CarRunService carRunService;
	
	@Autowired
	private CarRepairService carRepairService;
	
	@Autowired
	private CarPreOfflineMapper carPreOfflineMapper;
	
	@Autowired
	private CarRepairMapper carRepairMapper;
	
	@Autowired
	private CarService carService;
	
	@SystemServiceLog(description="车辆下线申请管理")
	@RequestMapping(value={"/car_offline_apply"},method=RequestMethod.GET)
	public String carOfflineApply(HttpServletRequest request,HttpServletResponse response)throws Exception{
		log.info("车辆下线管理页面");
		return "car/carOffline";
	}
	
	@SystemServiceLog(description="车辆申请下线未审核数量")
	@RequestMapping(value={"/car_offline_apply_total"},method={RequestMethod.GET,RequestMethod.POST})
	public String carOfflineApplyTotal(HttpServletResponse response,HttpServletRequest request)throws Exception{
		response.setContentType("text/html;charset=utf-8");
		SysUser sysUser = SystemOperationUtil.getSysUser(request);
		if(null==sysUser){
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		if(StringUtils.equals(sysUser.getCityCode(), "00")){
			map.put("cityCode", null);
       }else{
   	       map.put("cityCode", sysUser.getCityCode());
       }
		map.put("auditResult", "0");
		int totalRecords = carOfflineApplyService.carOfflineApplyTotal(map);
		response.getWriter().print(totalRecords);
		return null;
	}
	
	@SystemServiceLog(description="下线申请列表")
	@RequestMapping(value={"/car_offline_apply_list"},method={RequestMethod.GET,RequestMethod.POST})
	public String carOfflineApplyList(int rows,int page,HttpServletRequest request,HttpServletResponse response)throws Exception{
		response.setCharacterEncoding("utf-8");
		log.info("车辆下线申请页面");
		Map<String, Integer> pageInfo = Data2Jsp.getFristAndPageSize(page, rows);
		int from = pageInfo.get("first_page");
		int to = pageInfo.get("page_size");
		String lpn = request.getParameter("lpn");
		String reason = request.getParameter("reason");
		String auditResult = request.getParameter("auditResult");
		String offLineType = request.getParameter("offLineType");
		String applicant = request.getParameter("applicant");
		String cityCode = request.getParameter("cityCode");
		Map<String, Object> map = new HashMap<String, Object>();
		//城市过滤
				SysUser user = (SysUser) request.getSession().getAttribute("user");
				if(user.getCityCode().equals("00")){
			       	 if(!StringUtils.isBlank(cityCode)){
			         	if(cityCode.equals("00")){
			         		map.put("cityCode", null);
			         	}else{
			         		map.put("cityCode", cityCode);
			         	}
			         }
		      }else{
		   	   map.put("cityCode", user.getCityCode());
		      }
		map.put("lpn", lpn);
		map.put("reason", reason);
		map.put("auditResult", auditResult);
		map.put("offLineType", offLineType);
		map.put("applicant", applicant);
		map.put("from", from);
		map.put("to", to);
		List<CarOfflineApply> data = carOfflineApplyService.allCarOfflineApply(map);
		int total = carOfflineApplyService.allCarOfflineApplyRecords(map);
		String json = Data2Jsp.Json2Jsp(data, total);
		response.getWriter().print(json);
		return null;
	}
	
    
	@SystemServiceLog(description="车辆下线数据导出")
	@RequestMapping(value = "/export_carOffline_list", method = { RequestMethod.GET , RequestMethod.POST })
	public List exportExecl(String lpn,String cityCode,String reason,String auditResult,String applicant,String offLineType,
			HttpServletRequest request,HttpServletResponse response) throws Exception {
		String fileName = "CarOfflineReport" ;
		//列名充电桩编码	
		String columnNames [] = {"所属城市", "车牌",  "车辆当前状态", "剩余电量", "小电瓶电压", "设备上报时间", 
				"所属网点","申请下线状态","申请人","手机号","申请时间","下线原因","审核状态","审核人"};
		
		String keys[] = { "cname", "lpn","cstatus", 
				"restBattery", "smallBatteryVoltage", "cupdateTime", "pname",
				"offLineType","applicant","applicantPhone","createTime","reason","auditResult","auditHuman" };
		Map<String, Object> map = new HashMap<String, Object>();
		SysUser user = (SysUser) request.getSession().getAttribute("user");
		if(user.getCityCode().equals("00")){
	       	 if(!StringUtils.isBlank(cityCode)){
	         	if(cityCode.equals("00")){
	         		map.put("cityCode", null);
	         	}else{
	         		map.put("cityCode", cityCode);
	         	}
	         }
		}else{
			map.put("cityCode", user.getCityCode());
		}
		map.put("lpn", lpn);
		map.put("reason", reason);
		map.put("auditResult", auditResult);
		map.put("applicant", applicant);
		map.put("offLineType", offLineType);
		map.put("from", null);
		map.put("to", null);
		List<CarOfflineApply> datas = carOfflineApplyService.allCarOfflineApply(map);
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		Map<String, Object> sheetNameMap = new HashMap<String, Object>();
		sheetNameMap.put("sheetName", "车辆下线数据报表");
		list.add(sheetNameMap);
		list.addAll(createData2(datas));
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try{
			ExcelUtil.createWorkBook(list, keys, columnNames).write(os);
		}catch (Exception e) {
			e.printStackTrace();
		}
		byte[] content = os.toByteArray();
		InputStream is = new ByteArrayInputStream(content);
		// 设置response参数，可以打开下载页面
		response.reset();
		response.setContentType("application/vnd.ms-excel;charset=utf-8");
		response.setHeader("Content-Disposition", "attachment;filename="
				+ new String((fileName + ".xls").getBytes(), "iso-8859-1"));
		ServletOutputStream out = response.getOutputStream();
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			bis = new BufferedInputStream(is);
			bos = new BufferedOutputStream(out);
			byte[] buff = new byte[2048];
			int bytesRead;
			// Simple read/write loop.
			while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
				bos.write(buff, 0, bytesRead);
			}
		} catch (final IOException e) {
			throw e;
		} finally {
			if (bis != null)
				bis.close();
			if (bos != null)
				bos.close();
		}	
		
		return null;
		
	}
	
	private List<Map<String, Object>> createData2(
			List<CarOfflineApply> data) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map;// 避免重复创建对象
		String keys[] = { "cname", "lpn","cstatus", 
				"restBattery", "smallBatteryVoltage", "cupdateTime", "pname",
				"offLineType","applicant","applicantPhone","createTime","reason","auditResult","auditHuman" };
		for (CarOfflineApply apply : data) {
			map = new HashMap<String, Object>();
			map.put(keys[0], apply.getCname());
			String lpn = apply.getLpn();
			if(lpn != null){
				map.put(keys[1], lpn.indexOf("•")<0 ?lpn.substring(0, 2)+"•"+lpn.substring(2):lpn);
			}
			String status = apply.getCstatus();
			if(StringUtils.isNotBlank(status)){
				if(status.equals("empty")||status.equals("")||status==null){
					map.put(keys[2], "闲置中");
				}else if(status.equals("repair")){
					map.put(keys[2], "维修中");
				}else if(status.equals("maintain")){
					map.put(keys[2], "维护中");
				}else if(status.equals("rescue")){
					map.put(keys[2], "救援中");
				}else if(status.equals("accident")){
					map.put(keys[2], "事故处理中");
				}else if(status.equals("charging")){
					map.put(keys[2], "补电中");
				}else{
					if(StringUtils.isNotBlank(apply.getPreoffline())){
						if (apply.getPreoffline().equals("1")) {
							map.put(keys[2], "运营中（预下线）");
						}else{
							map.put(keys[2], "运营中");
						}
					}
				}
			}else{
				map.put(keys[2], "");
			}
			map.put(keys[3], apply.getRestBattery());
			if(StringUtils.isNotBlank(apply.getSmallBatteryVoltage())){
				int small = Integer.parseInt(apply.getSmallBatteryVoltage());
				map.put(keys[4], small/10.0+"V");
			}else{
				map.put(keys[4], "0V");
			}
			map.put(keys[5], apply.getCupdateTime() != null ? new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(apply.getCupdateTime()):"");
			map.put(keys[6], apply.getPname());
			String type = apply.getOffLineType();
			if(type != null){
				if (type.equals("1")) {
					map.put(keys[7],"维修");
				} else if(type.equals("2")) {
					map.put(keys[7],"维护");
				}else if(type.equals("3")){
					map.put(keys[7],"补电");
				}else if(type.equals("4")){
					map.put(keys[7],"空闲");
				}else{
					map.put(keys[7], type);
				}
			}
			map.put(keys[8], apply.getApplicant());
			map.put(keys[9], apply.getApplicantPhone());
			map.put(keys[10], apply.getCreateTime()!=null ? new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(apply.getCreateTime()):"" );
			map.put(keys[11], apply.getReason());
			String result = apply.getAuditResult();
			if (result != null) {
				if(result.equals("1")){
					map.put(keys[12], "审核通过") ;
				}else if(result.equals("2")) {
					map.put(keys[12], "审核不通过") ;
				}else if(result.equals("0")) {
					map.put(keys[12], "未审核") ;
				}else{
					map.put(keys[12], result) ;
				}
			}else{
				map.put(keys[12], result) ;
			}
			map.put(keys[13], apply.getAuditHuman());
			list.add(map);
		}
		return list;
	}
	
	@SystemServiceLog(description="查询车辆申请页面")
	@RequestMapping(value={"/car_offline_apply_info"},method={RequestMethod.GET,RequestMethod.POST})
	public String carOfflineApplyInfo(Integer id,HttpServletRequest request,HttpServletResponse response)throws Exception{
		response.setCharacterEncoding("utf-8");
		CarOfflineApply data = carOfflineApplyService.selectByPrimaryKey(id);
		JsonConfig json = new JsonConfig();
		json.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
		response.getWriter().print(JSONObject.fromObject(data, json));
		return null;
	}
	
/*	@SystemServiceLog(description=("预下线"))
	@RequestMapping(value={"/car_offline_apply_pre"},method={RequestMethod.GET,RequestMethod.POST})
	public String carOfflineApplyPre(HttpServletRequest request,HttpServletResponse response)throws Exception{
		response.setCharacterEncoding("utf-8");
		String lpn = request.getParameter("lpn");
		CarRun data = carRunService.queryCarRunByLpn(lpn.replace("•", ""));
		String status = data.getStatus();
		if(status.equals(SysConstant.CAR_RUN_STATUS_USECAR) || status.equals(SysConstant.CAR_RUN_STATUS_ORDERED)){
			response.getWriter().print("operate");
			JsonConfig json = new JsonConfig();
			json.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
			response.getWriter().print(JSONObject.fromObject(data,json));
			return null;
		}else if(status.equals(SysConstant.CAR_RUN_STATUS_EMPTY)){
			response.getWriter().print("noEmpty");
			return null;
		}else{
			if(status.equals("repair") || status.equals("maintain") || status.equals("charging"))
			response.getWriter().print("noPre");
			return null;
		}
	}*/
	
	@SystemServiceLog(description=("预下线审核"))
	@RequestMapping(value={"/car_offline_pre"},method={RequestMethod.GET,RequestMethod.POST})
	public String carOfflinePre(HttpServletRequest request,HttpServletResponse response)throws Exception{
		String lpn = request.getParameter("lpn").replace("•", "");
		String id = request.getParameter("id");
		CarOfflineApply carOfflineApply = carOfflineApplyService.selectById(Integer.valueOf(id));
		SysUser sysUser = SystemOperationUtil.getSysUser(request);
		CarRun carRun = carRunService.queryCarRun(lpn.replace("•", ""));
		String status = carRun.getStatus();
		String result = carOfflineApply.getAuditResult();
		//车辆以被审核，就不予许重复操作
		if(result.equals("2")||result.equals("1")){
			response.getWriter().print("check");
			return null;
		}else{
			if(status.equals(SysConstant.CAR_RUN_STATUS_USECAR) || status.equals(SysConstant.CAR_RUN_STATUS_ORDERED) || status.equals("return")){
				if(StringUtils.equals(carOfflineApply.getOffLineType(), "4")) {
					response.getWriter().print("noPre");
					return null ;
				}
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("lpn", lpn);
		    	map.put("status", "2");
		    	CarPreOffline offline = carPreOfflineMapper.selectPreOfflineRecordByLpn(map);
	            if(null==offline){
	                offline = new CarPreOffline();
		            offline.setCityCode(sysUser.getCityCode());
		            offline.setPrincipalId(sysUser.getId());
		            offline.setCreateTime(new Date());
		            offline.setLpn(lpn);
		            offline.setOfflineType(Integer.valueOf(carOfflineApply.getOffLineType()));
		            offline.setPredictTime(new Date(System.currentTimeMillis()+60*60*24*1000));
		            offline.setReason("预下线: ".concat(carOfflineApply.getReason()));
		            offline.setCreateUser(sysUser.getName());
		            offline.setPrincipalName(carOfflineApply.getApplicant());
		            offline.setPrincipalPhone(carOfflineApply.getApplicantPhone());
		            offline.setStatus(2);
	            }else{
	            	offline = null;
	            }
				//更改车状态为预下线
				carRun.setPreOffline("1");
			//	carRunService.updateByPrimaryKey(carRun);
				carService.updateCarPreOfflineStatus(carRun,offline);
				//更改审核结果
				carOfflineApply.setAuditResult("1");
				SysUser user = (SysUser)request.getSession().getAttribute("user");
				carOfflineApply.setAuditHuman(user.getName());
				carOfflineApplyService.updateByPrimaryKeySelective(carOfflineApply);

				//推送消息
				PushCommonBean pushCommon = new PushCommonBean("server_car_offline_apply","1","您的车辆下线申请已通过",lpn) ;
				String phone = carOfflineApply.getApplicantPhone();
				List<String> cidList = PushClientEmployee.queryClientId(phone);
				for (String employeeCid : cidList) {
					PushClientEmployee.push(employeeCid,JSON.toJSON(pushCommon));
				}
				response.getWriter().print("succ");
				return null;
			}else{
				response.getWriter().print("noSucc");
			}
		}	
		return null;
}
	
	@SystemServiceLog(description=("下线审核"))
	@RequestMapping(value={"/car_offline_apply_check"},method={RequestMethod.GET,RequestMethod.POST})
	public String checkCarOfflineApply(HttpServletRequest request,HttpServletResponse response)throws Exception{
		response.setCharacterEncoding("utf-8");
		SysUser sysUser = SystemOperationUtil.getSysUser(request);
		String id = request.getParameter("id");
		String lpn = request.getParameter("lpn").replace("•", "");//车牌号
		CarOfflineApply carOfflineApply = carOfflineApplyService.selectById(Integer.valueOf(id));
		CarRun carRun = carRunService.queryCarRun(lpn);
		String status = carRun.getStatus();
		String result = carOfflineApply.getAuditResult();
		//车辆已被审核，就不予许重复操作
		if(result.equals("2")||result.equals("1")){
			response.getWriter().print("check");
			return null;
		}else{
			if (StringUtils.equals(carOfflineApply.getOffLineType(), "4")) {
				response.getWriter().print("typeExcep");
				return null ;
			}
			if(status.equals(SysConstant.CAR_RUN_STATUS_EMPTY)||status.equals(SysConstant.CAR_RUN_STATUS_MAINTAIN)||
					status.equals(SysConstant.CAR_RUN_STATUS_REPAIR)||status.equals(SysConstant.CAR_RUN_STATUS_CHARING)){
				/*carRun 车状态为 维修:repair  维护 :maintain 补电:charging*/
				String stas = carOfflineApply.getOffLineType();// 1,2,3
				if(SysConstant.CAR_RUN_STATUS_MAINTAIN.equals(status)&&"2".equals(stas)){
					response.getWriter().print("sameStatus");
					return null;
				}
				if(SysConstant.CAR_RUN_STATUS_REPAIR.equals(status)&&"1".equals(stas)){
					response.getWriter().print("sameStatus");
					return null;
				}
				if(SysConstant.CAR_RUN_STATUS_CHARING.equals(status)&&"3".equals(stas)){
					response.getWriter().print("sameStatus");
					return null;
				}
					// 1,2,3分别代表维修、维护和补电,如果由1或2切换到3,则拒绝切换
				if(("3".equals(stas))&&(SysConstant.CAR_RUN_STATUS_MAINTAIN.equals(status)||SysConstant.CAR_RUN_STATUS_REPAIR.equals(status))&&!SysConstant.CAR_RUN_STATUS_CHARING.equals(status)){
						response.getWriter().print("modifyFailed");
						return null;
				}
				if(stas.equals("1")){
					carRun.setStatus("repair");
				}else if(stas.equals("2")){
					carRun.setStatus("maintain");
				}else{
					carRun.setStatus("charging");
				}
				carRunService.updateByPrimaryKey(carRun);
				//获取审核人
				SysUser user = (SysUser)request.getSession().getAttribute("user");
				/*把车辆至维修列表*/
				CarRepair carRepair = new CarRepair();
				carRepair.setCityCode(carOfflineApply.getCityCode());//code
				carRepair.setLpn(carRun.getLpn());//车牌
				carRepair.setParkId(carOfflineApply.getParkId());//网点id
				/*x_car_repair 车状态为维修状态， 1维修中，0恢复运营，2维护，3 补电'*/
				carRepair.setStatus(carOfflineApply.getOffLineType());
				carRepair.setStatusCache(carOfflineApply.getOffLineType());
				carRepair.setResponsiblePerson(carOfflineApply.getApplicant());//责任人
				carRepair.setResponsiblePersonPhone(carOfflineApply.getApplicantPhone());
				carRepair.setReason(carOfflineApply.getReason());
				carRepair.setCarId(carRun.getId());
				carRepair.setStartTime(new Date());
				try {
					carRepair.setResponsiblePersonId(Integer.valueOf(carOfflineApply.getCreateUser()));
				} catch (Exception e) {
					carRepair.setResponsiblePersonId(null);
				}
				carRepair.setPredictTime(new Date(System.currentTimeMillis()+60*60*24*1000));
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("lpn", carRun.getLpn());
				if("repair".equals(status)){
					map.put("status", 1);
				}
				if("charging".equals(status)){
					map.put("status", 3);
				}
				if("maintain".equals(status)){
					map.put("status", 2);
				}
				List<CarRepair> lists =carRepairMapper.queryCarRepairByLpnStatus(map);
				final Double restBattery = carRun.getRestBattery();
				for (CarRepair repair : lists) {
					// 把记录结束
					repair.setStatus("0");
					repair.setEndTime(new Date());
					repair.setUpdateTime(new Date());
					repair.setUpdateUser(sysUser.getName());
					repair.setEndRestBattery(restBattery);
					carRepairMapper.updateByPrimaryKey(repair);
				}
				// 开始电量
				carRepair.setStartRestBattery(restBattery);
				carRepairService.insertSelective(carRepair);
				
				/*更改审核状态*/
				carOfflineApply.setAuditResult("1");
				carOfflineApply.setAuditHuman(user.getName());
				carOfflineApplyService.updateByPrimaryKeySelective(carOfflineApply);
				//推送消息
					PushCommonBean pushCommon = new PushCommonBean("server_car_offline_apply","1","您的车辆下线申请已通过",lpn) ;
					String phone = carOfflineApply.getApplicantPhone();
					List<String> cidList = PushClientEmployee.queryClientId(phone);
					for (String employeeCid : cidList) {
						PushClientEmployee.push(employeeCid,JSON.toJSON(pushCommon));
					}
				response.getWriter().print("succ");
				return null;
			}else if(status.equals(SysConstant.CAR_RUN_STATUS_USECAR) || status.equals(SysConstant.CAR_RUN_STATUS_ORDERED)||status.equals(SysConstant.CAR_RUN_STATUS_RETURN)){
				response.getWriter().print("noOperate");
				return null;
			}else{
				response.getWriter().print("noPre");
			}
		}
			
		return null;
	}
	
	@SystemServiceLog(description=("审核拒绝"))
	@RequestMapping(value={"/car_offline_apply_refuse"},method={RequestMethod.GET,RequestMethod.POST})
	public String carOfflineApplyRefuse(HttpServletRequest request,HttpServletResponse response)throws Exception{
		//更改审核状态
		String lpn = request.getParameter("lpn").replace("•", "");;
		String id = request.getParameter("id");
		//CarRun carRun = carRunService.queryCarRun(lpn);
		//String status = carRun.getStatus();
		CarOfflineApply carOfflineApply =   carOfflineApplyService.selectById(Integer.valueOf(id));
		SysUser user =(SysUser) request.getSession().getAttribute("user");
		String result = carOfflineApply.getAuditResult();
		//车辆以被审核，就不予许重复操作
		if(result.equals("2")||result.equals("1")){
			response.getWriter().print("check");
			return null;
		}else{
			carOfflineApply.setAuditResult("2");
			carOfflineApply.setAuditHuman(user.getName());
			carOfflineApplyService.updateByPrimaryKeySelective(carOfflineApply);
			//推送消息
			PushCommonBean pushCommon = new PushCommonBean("server_car_offline_apply_refuse","1","您的车辆下线申请不通过",lpn) ;
			String phone = carOfflineApply.getApplicantPhone();
			List<String> cidList = PushClientEmployee.queryClientId(phone);
			for (String employeeCid : cidList) {
				PushClientEmployee.push(employeeCid,JSON.toJSON(pushCommon));
			}
			response.getWriter().print("succ");
			/*if(status.equals("repair")||status.equals("maintain")||status.equals("charging")){
				
			}else{
				response.getWriter().print("noSucc");
			}*/
		}
		
		return null;
	}
	
	@SystemServiceLog(description=("车辆上线审核"))
	@RequestMapping(value={"/car_online_apply_check"},method={RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public Map<String, Object> carOnlineApplay(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		CarOfflineApply vo = carOfflineApplyService.selectById(Integer.valueOf(id));
		String type = vo.getOffLineType();
		String lpn = request.getParameter("lpn").replace("•", "");
		String carRunStatus = carRunService.selectStatusByLpn(lpn);
		SysUser user = (SysUser) request.getSession().getAttribute("user");
		if (StringUtils.equals(carRunStatus, "repair")
				|| StringUtils.equals(carRunStatus, "maintain")
				|| StringUtils.equals(carRunStatus, "charging")) {
			if (StringUtils.isNotBlank(type) && StringUtils.equals(type, "4")) {
				// 更改carRun车辆状态
				Integer carRunId = carRunService.selectIdByCarApplyStatus(lpn);
				if (carRunId == null) {
					return ResultMsg.fialResult("获取车辆运营id失败！");
				} else {
					carRunService.updateCarRunStatusById(carRunId);
					// 更改对应的车辆维系记录为已维修
					Map<String, Object> paramMap = new HashMap<String, Object>();
					paramMap.put("lpn", lpn);
					if (StringUtils.equals(carRunStatus, "repair")) {
						paramMap.put("status", 1);
					} else if (StringUtils.equals(carRunStatus, "maintain")) {
						paramMap.put("status", 2);
					} else if (StringUtils.equals(carRunStatus, "charging")) {
						paramMap.put("status", 3);
					}
					List<CarRepair> lists =carRepairMapper.queryCarRepairByLpnStatus(paramMap);
					for (CarRepair repair : lists) {
						repair.setStatus("0");
						repair.setEndTime(new Date());
						repair.setParkId(vo.getParkId());
//						repair.setResponsiblePerson(vo.getApplicant());
//						repair.setResponsiblePersonPhone(vo.getApplicantPhone());
//						repair.setReason(vo.getReason());
						repair.setRecoverUser(vo.getApplicant());
						repair.setRecoverUserPhone(vo.getApplicantPhone());
						repair.setRecoverReason(vo.getReason());
						carRepairMapper.updateByPrimaryKey(repair);
					}
					//修改审核操作结果
					vo.setAuditResult("1");
					vo.setAuditHuman(user.getName());
					carOfflineApplyService.updateByPrimaryKeySelective(vo);
					//推送消息
					PushCommonBean pushCommon = new PushCommonBean("server_car_offline_apply","1","您的车辆上线申请已通过",lpn) ;
					String phone = vo.getApplicantPhone();
					List<String> cidList = PushClientEmployee.queryClientId(phone);
					for (String employeeCid : cidList) {
						PushClientEmployee.push(employeeCid,JSON.toJSON(pushCommon));
					}
					return ResultMsg.succResult("车辆上线审核成功！");
				}
			} else {
				return ResultMsg.fialResult("此申请状态不可上线！");
			}
		}else{
			return ResultMsg.fialResult("非维修维护补电不允许操作！");
		}
	}
	
}
