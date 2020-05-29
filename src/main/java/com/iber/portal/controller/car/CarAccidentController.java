package com.iber.portal.controller.car;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.iber.portal.advices.SystemServiceLog;
import com.iber.portal.common.Data2Jsp;
import com.iber.portal.common.ExcelUtil;
import com.iber.portal.common.Pager;
import com.iber.portal.controller.MainController;
import com.iber.portal.model.car.CarAccident;
import com.iber.portal.model.car.CarRepair;
import com.iber.portal.model.charging.ChargingPile;
import com.iber.portal.model.sys.SysUser;
import com.iber.portal.service.car.CarAccidentService;
import com.iber.portal.service.car.CarRepairService;

/**
 * <br>
 * <b>功能：</b>车辆事故管理<br>
 * <b>作者：</b>xyq<br>
 * <b>日期：</b> 2016.11.11 <br>
 */ 
@Controller
public class CarAccidentController extends MainController{
	
	private final static Logger log= Logger.getLogger(CarAccidentController.class);
	
	// Servrice start
	@Autowired(required=false) //自动注入，不需要生成set方法了，required=false表示没有实现类，也不会报错。
	private CarAccidentService carAccidentService; 
	
	@Autowired(required=false)
	private CarRepairService carRepairService; 

	/**
	 * @return
	 * @throws Exception 
	 */
	@SystemServiceLog(description="CarAccident页面")
	@RequestMapping("/car_accident_page") 
	public String carAccident_page(HttpServletRequest request, HttpServletResponse response) {
		log.info("CarAccident页面");
		return "car/carAccident" ;
	}
	
	
	/**
	 * 数据
	 * @param page
	 * @param rows
	 * @return
	 * @throws IOException 
	 */
	@SystemServiceLog(description="数据列表")
	@RequestMapping(value ="/dataListCarAccident", method = { RequestMethod.GET , RequestMethod.POST }) 
	public void  datalist(int page, int rows, HttpServletRequest request, HttpServletResponse response) throws IOException{
		log.info("数据列表");
		response.setContentType("text/html;charset=utf-8");
		Map<String,Integer> pageInfo = Data2Jsp.getFristAndPageSize(page, rows);
		int from = pageInfo.get("first_page");
		int to = pageInfo.get("page_size");
		
		//获取其他查询参数
		//String title = request.getParameter("title");
		String lpn = request.getParameter("lpn");
		String accidentStatus =request.getParameter("accidentStatus");
		String memberName=request.getParameter("memberName");
		String menberPhome=request.getParameter("memberPhome");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("from", from);
		paramMap.put("to", to);
		paramMap.put("status",accidentStatus);
		paramMap.put("lpn",lpn);
		paramMap.put("memberName",memberName);
		paramMap.put("menberPhome",menberPhome);
		
		SysUser user = (SysUser) request.getSession().getAttribute("user");
		String cityCode = null ;
		if(!"00".equals(user.getCityCode())){
			cityCode = user.getCityCode() ;
		}
		paramMap.put("cityCode", cityCode);
		Pager<CarAccident> pager = carAccidentService.queryPageList(paramMap);
		response.getWriter().print(Data2Jsp.Json2Jsp(pager));
	}
	
	@SystemServiceLog(description="车辆事故数据导出")
	@RequestMapping(value = "/export_carAccident_list", method = { RequestMethod.GET , RequestMethod.POST })
	public List<ChargingPile> exportExecl(String lpn,String accidentStatus,String memberName,String memberPhome,
			HttpServletRequest request,HttpServletResponse response) throws Exception {
		String fileName = "CarAccidentReport" ;
		//列名充电桩编码	
		String columnNames [] = {"车牌号码", "事故处理状态",  "责任人", "手机号码", "会员姓名","会员手机号码", "关联订单号",
				"赔偿金额","是否由会员处理","处理开始时间","处理结束时间","事故原因","处理结果","是否需要保险","保险单号","预计上线时间","定损时间","定损金额","责任判定"};
		
		String keys[] = { "lpn", "status","responsiblePerson", 
				"responsiblePersonPhone","memberName", "memberPhone", "orderId", "money",
				"handleByCustomer","startTime","endTime","reason","result","isInsurance","insuranceCode",
				"predictTime","assessmentTime","assessmentMoney","responsibility"};
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("lpn", lpn);
		map.put("status", accidentStatus);
		map.put("memberName",memberName);
		map.put("memberPhome",memberPhome);
		map.put("from", null);
		map.put("to", null);

		Pager<CarAccident> pager = carAccidentService.queryPageList(map);
		List<CarAccident> datas = pager.getDatas();
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		Map<String, Object> sheetNameMap = new HashMap<String, Object>();
		sheetNameMap.put("sheetName", "车辆事故数据报表");
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
			List<CarAccident> data) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map;// 避免重复创建对象
		String keys[] = { "lpn", "status","responsiblePerson", 
				"responsiblePersonPhone", "memberName","memberPhone", "orderId", "money",
				"handleByCustomer","startTime","endTime","reason","result","isInsurance","insuranceCode",
				"predictTime","assessmentTime","assessmentMoney","responsibility"};
		for (CarAccident accident : data) {
			map = new HashMap<String, Object>();
			map.put(keys[0], accident.getLpn().indexOf("•")<0 ?accident.getLpn().substring(0, 2)+"•"+accident.getLpn().substring(2):accident.getLpn());
			if(StringUtils.isNotBlank(accident.getStatus())){
				if(accident.getStatus().equals("1")){
					map.put(keys[1], "处理中");
				}else if(accident.getStatus().equals("0")){
					map.put(keys[1], "处理完毕");
				}
			}
			map.put(keys[2], accident.getResponsiblePerson());
			map.put(keys[3], accident.getResponsiblePersonPhone());
			map.put(keys[4], accident.getMemberPhone());
			map.put(keys[5], accident.getOrderId());
			map.put(keys[6], accident.getMoney() != null ? new DecimalFormat("0.00").format(accident.getMoney()/100.0):0.00);
			Integer customer = accident.getHandleByCustomer();
			if(customer != null){
				if(customer==0){
					map.put(keys[7], "由公司处理"); 
				}else{
					map.put(keys[7], "由会员处理");
				}
			}else{
				map.put(keys[7], "由会员处理");
			}
			map.put(keys[8], accident.getStartTime()!=null ? new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(accident.getStartTime()):"");
			map.put(keys[9], accident.getEndTime()!= null ? new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(accident.getEndTime()):"" );
			map.put(keys[10], accident.getReason());
			map.put(keys[11], accident.getResult());
			String insurance = accident.getIsInsurance();
			if(insurance != null){
				if(insurance.equals("0")){
					map.put(keys[12], "不需要");
				}else if(insurance.equals("1")){
					map.put(keys[12], "需要");
				}
			}
			map.put(keys[13], accident.getInsuranceCode());
			map.put(keys[14], accident.getPredictTime());
			map.put(keys[15], accident.getAssessmentTime());
			map.put(keys[16], accident.getAssessmentMoney() != null?accident.getAssessmentMoney():"0.00");
			map.put(keys[17], accident.getResponsibility());
			list.add(map);
		}
		return list;
	}
	/**
	 * 添加或修改数据
	 * @param CarAccident
	 * @return
	 * @throws IOException 
	 */
	@SystemServiceLog(description="添加或修改数据")
	@RequestMapping("/saveOrUpdateCarAccident")
	public String saveOrUpdate(CarAccident entity,HttpServletRequest request, HttpServletResponse response) throws IOException{
		log.info("新增或更新");
		SysUser user = (SysUser) getUser(request) ;
		entity.setLpn(entity.getLpn().replace("•", ""));
		entity.setHandleByCustomer(Integer.parseInt(request.getParameter("memberCompensate")));
		if(!StringUtils.isBlank(request.getParameter("memberPhone"))){
			entity.setMemberPhone(request.getParameter("memberPhone"));
			entity.setOrderId(request.getParameter("relativeOrderId"));
		}
		String accidentMoney = request.getParameter("accident-money");
		if(!StringUtils.isBlank(accidentMoney)){
			accidentMoney = accidentMoney.trim();
			if(accidentMoney.matches("[0-9]+(.[0-9]+)?")){
				// 不包含小数点
				if(!accidentMoney.contains(".")){
					entity.setMoney(Integer.valueOf(accidentMoney.concat("00")));
				}else{
					// 字符串转为双精度数,尽量不让精度丢失
					double d = Double.valueOf(accidentMoney);
					// d乘以100,把 元单位 转为 分单位,money为长整形也是为了尽量不让精度丢失
					long money = (long) (d*100);
					entity.setMoney(Integer.valueOf(String.valueOf(money)));
				}
			}else{
				response.getWriter().print("num-wrong");
				return null;
			}
		}else {
			// 默认置0
			entity.setMoney(0);
		}
		if(entity.getId()==null||StringUtils.isBlank(entity.getId().toString())){
			carAccidentService.insert(entity);
		}else{
			entity.setUpdateTime(new Date());
			entity.setUpdateUser(user.getAccount());
			carAccidentService.updateByPrimaryKeySelective(entity);
		}
		response.getWriter().print("success");
		return null;
	}
	
	/**
	 * 删除数据
	 * @param 主键ID 
	 * @return
	 * @throws IOException 
	 */
	@SystemServiceLog(description="删除数据")
	@RequestMapping("/deleteCarAccidentById")
	public void delete(Integer id,HttpServletRequest request,HttpServletResponse response) throws IOException{
		log.info("删除");
		if(null!=id ) {
			carAccidentService.deleteByPrimaryKey(id);
		}
		response.getWriter().print("success");
	}
	
	/**事故处理完毕更新表格状态*/
	@SystemServiceLog(description="事故处理完毕更新表格状态")
	@RequestMapping("/endAccident")
	public String endAccident(HttpServletRequest request,HttpServletResponse response) throws IOException{
		SysUser user = (SysUser) getUser(request) ;
		String result = request.getParameter("result");
		String id  = request.getParameter("m_id");
		String isInsurance = request.getParameter("is_insurance");
		String insuranceCode = request.getParameter("insurance_code");
		String responsibility = request.getParameter("responsibility");
		String assessment_time = request.getParameter("assessment_time");
		String predictTime = request.getParameter("predictTime");
		String assessment_money = request.getParameter("assessment_money");
		CarAccident carAccident = new CarAccident();
		carAccident.setId(Integer.valueOf(id));
		carAccident.setInsuranceCode(insuranceCode);
		carAccident.setResponsibility(responsibility);
		carAccident.setAssessmentTime(assessment_time);
		carAccident.setAssessmentMoney(Float.parseFloat(assessment_money)*100+"");
		carAccident.setPredictTime(predictTime);
		carAccident.setIsInsurance(isInsurance);
		carAccident.setEndTime(new Date());
		carAccident.setUpdateTime(new Date());
		carAccident.setUpdateUser(user.getAccount());
		carAccident.setStatus("0");
		carAccident.setResult(result);
		int r = carAccidentService.updateByPrimaryKeySelective(carAccident);
		if(r > 0){
			response.getWriter().print("succ");
		}else{
			response.getWriter().print("fail");
		}
		return null;	
	}
	
	/**保存车辆转维修的信息*/
	@SystemServiceLog(description="保存车辆转维修的信息")
	@RequestMapping(value = "/accident_to_repair_save", method = { RequestMethod.GET, RequestMethod.POST })
	public String saveCarRepair( HttpServletRequest request, HttpServletResponse response) throws Exception{
		response.setContentType("text/html;charset=utf-8");
		SysUser user = (SysUser) getUser(request) ;
		CarRepair carRepair = new CarRepair();
		String carId = request.getParameter("r-carId");
		String lpn = request.getParameter("r-lpn-1").replace("•", "");
		String responsiblePerson = request.getParameter("r-responsiblePerson");
		String responsiblePersonPhone =request.getParameter("r-responsiblePersonPhone");
		String reason = request.getParameter("r-reason");
		carRepair.setCarId(Integer.valueOf(carId));
		carRepair.setLpn(lpn);
		carRepair.setResponsiblePerson(responsiblePerson);
		carRepair.setResponsiblePersonPhone(responsiblePersonPhone);
		carRepair.setReason(reason);
		carRepair.setStartTime(new Date());
		carRepair.setStatus("1");
		carRepair.setStatusCache("1");
		carRepair.setCreateUser(user.getAccount());
		carRepair.setCreateTime(new Date());
		String predictTime = request.getParameter("r-predictTime");
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date date = sdf.parse(predictTime);
		carRepair.setPredictTime(date);
		int r =carRepairService.saveCarRepairInfoByOther(carRepair);
		if(r > 0){
			response.getWriter().print("succ");
		}else{
			response.getWriter().print("fail");
		}
		return null;
	}
	
}
