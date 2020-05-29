package com.iber.portal.controller.car;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.iber.portal.advices.SystemServiceLog;
import com.iber.portal.common.Data2Jsp;
import com.iber.portal.common.ExcelUtil;
import com.iber.portal.common.OSSClientAPI;
import com.iber.portal.common.Pager;
import com.iber.portal.controller.MainController;
import com.iber.portal.model.car.CarRepair;
import com.iber.portal.model.car.CarRescue;
import com.iber.portal.model.car.CarRescueProblem;
import com.iber.portal.model.charging.ChargingPile;
import com.iber.portal.model.member.EvidenceRelation;
import com.iber.portal.model.sys.SysDic;
import com.iber.portal.model.sys.SysUser;
import com.iber.portal.model.task.TaskInfo;
import com.iber.portal.service.car.CarRepairService;
import com.iber.portal.service.car.CarRescueService;
import com.iber.portal.service.sys.SysDicService;
import com.iber.portal.service.sys.SysParamService;
import com.iber.portal.service.task.TaskService;

/**
 * 
 * <br>
 * <b>功能：</b>车辆救援管理<br>
 * <b>作者：</b>xyq<br>
 * <b>日期：</b> 2016.11.10 <br>
 */ 
@Controller
public class CarRescueController extends MainController{
	
	private final static Logger log= Logger.getLogger(CarRescueController.class);
	
	// Servrice start
	@Autowired(required=false) //自动注入，不需要生成set方法了，required=false表示没有实现类，也不会报错。
	private CarRescueService carRescueService; 
	
	@Autowired
	private TaskService taskService;
	
	@Autowired(required=false) 
	private CarRepairService carRepairService; 
	
	@Autowired(required=false)
    private SysParamService sysParamService ;
	
	@Autowired(required=false)
	private SysDicService sysDicService;
	
	/**
	 * @return
	 * @throws Exception 
	 */
	@SystemServiceLog(description="CarRescue页面")
	@RequestMapping("/car_rescue_page") 
	public String carRescue_page(HttpServletRequest request, HttpServletResponse response) {
		log.info("CarRescue页面");
		return "car/carRescue" ;
	}
	
	
	/**
	 * 车数据列表
	 * @param page
	 * @param rows
	 * @return
	 * @throws IOException 
	 */
	@SystemServiceLog(description="车数据列表")
	@RequestMapping("/dataListCarRescue") 
	public void  datalist(int page, int rows, HttpServletRequest request, HttpServletResponse response) throws IOException{
		log.info("车数据列表");
		response.setContentType("text/html;charset=utf-8");
		Map<String,Integer> pageInfo = Data2Jsp.getFristAndPageSize(page, rows);
		int from = pageInfo.get("first_page");
		int to = pageInfo.get("page_size");
		
		//获取其他查询参数
		//String title = request.getParameter("title");
		String lpn = request.getParameter("lpn");
		String rescueStatus =request.getParameter("rescueStatus");

		//添加查询会员姓各 会员联系方式参数
        String memberName = request.getParameter("memberName");
        String memberPhone = request.getParameter("memberPhone");

        Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("from", from);
		paramMap.put("to", to);
		paramMap.put("status",rescueStatus);
		paramMap.put("lpn",lpn);

        paramMap.put("memberName", memberName);
        paramMap.put("memberPhone", memberPhone);
        //城市过滤
		SysUser user = (SysUser) request.getSession().getAttribute("user");
		String cityCode = null ;
		if(!"00".equals(user.getCityCode())){
			cityCode = user.getCityCode() ;
		}
		paramMap.put("cityCode", cityCode);
		Pager<CarRescue> pager = carRescueService.queryPageList(paramMap);
		response.getWriter().print(Data2Jsp.Json2Jsp(pager));
	}
	
	@SystemServiceLog(description="车辆救援数据导出")
	@RequestMapping(value = "/export_carRescue_list", method = { RequestMethod.GET , RequestMethod.POST })
	public List<ChargingPile> exportExecl(String lpn,String rescueStatus,String memberName,String memberPhone,
			HttpServletRequest request,HttpServletResponse response) throws Exception {
		String fileName = "CarRescueReport" ;
		//列名充电桩编码	
		String columnNames [] = {"车牌号码", "车型",  "救援状态", "任务执行人", "执行人号码", "责任类型", 
				"责任判定类型","会员姓名","会员联系方式","会员等级","记录人","救援开始时间","救援结束时间","救援地址","事故经过","救援结果","是否维修"};
		
		String keys[] = { "lpn", "carBranceType","status", 
				"responsiblePerson", "responsiblePersonPhone", "behaviorTypeName", "responsibilityJudgeAdvice",
				"memberName","memberPhone","memberLevel","createUser","startTime","endTime","rescueAddress",
				"reason","result","ifRepair"};
		Map<String, Object> map = new HashMap<String, Object>();

        map.put("memberName", memberName);
        map.put("memberPhone", memberPhone);

        map.put("lpn", lpn);
		map.put("status", rescueStatus);
		map.put("from", null);
		map.put("to", null);
		Pager<CarRescue> pager = carRescueService.queryPageList(map);
		List<CarRescue> datas = pager.getDatas();
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		Map<String, Object> sheetNameMap = new HashMap<String, Object>();
		sheetNameMap.put("sheetName", "车辆救援数据报表");
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
			List<CarRescue> data) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map;// 避免重复创建对象
		String keys[] = { "lpn", "carBranceType","status", 
				"responsiblePerson", "responsiblePersonPhone", "behaviorTypeName", "responsibilityJudgeAdvice",
				"memberName","memberPhone","memberLevel","createUser","startTime","endTime","rescueAddress",
				"reason","result","ifRepair"};
		for (CarRescue rescue : data) {
			map = new HashMap<String, Object>();
			String lpn = rescue.getLpn();
			if(lpn != null){
				map.put(keys[0], lpn.indexOf("•")<0 ?lpn.substring(0, 2)+"•"+lpn.substring(2):lpn);
			}
			map.put(keys[1], rescue.getCarBranceType());
			String status = rescue.getStatus();
			if(status != null){
				if (status.equals("1")) {
					map.put(keys[2], "救援中");
				}else if(status.equals("0")){
					map.put(keys[2], "已结束");
				}else if(status.equals("2")){
					map.put(keys[2], "已取消");
				}
			}
			map.put(keys[3], rescue.getResponsiblePerson());
			map.put(keys[4], rescue.getResponsiblePersonPhone());
			String behaviorTypeName = rescue.getBehaviorTypeName();
			if (behaviorTypeName != null) {
				if(behaviorTypeName.equals("其它(不扣用户贡献值)")){
					map.put(keys[5], "其它(不扣用户贡献值)"+rescue.getResponsibleDescription());
				}else{
					map.put(keys[5], behaviorTypeName);
				}
			} else {
				map.put(keys[5], behaviorTypeName);
			}
			map.put(keys[6], rescue.getResponsibilityJudgeAdvice());
			map.put(keys[7], rescue.getMemberName()); 
			map.put(keys[8], rescue.getMemberPhone());
			map.put(keys[9], rescue.getMemberLevel()!=null?rescue.getMemberLevel():"");
			map.put(keys[10], rescue.getCreateUser());
			map.put(keys[11], rescue.getStartTime()!= null ? new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(rescue.getStartTime()):"" );
			map.put(keys[12], rescue.getEndTime()!= null ? new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(rescue.getEndTime()):"" );
			map.put(keys[13], rescue.getRescueAddress());
			map.put(keys[14], rescue.getReason());
			map.put(keys[15], rescue.getResult());
			String ifRepair = rescue.getIfRepair();
			if(ifRepair != null){
				if(ifRepair.equals("1")){
					map.put(keys[16], "是");
				}else{
					map.put(keys[16], "否");
				}
			}else{
				map.put(keys[16], "否");
			}
			list.add(map);
		}
		return list;
	}
	
	/**
	 * 添加或修改数据
	 * @param CarRescue
	 * @return
	 * @throws IOException 
	 */
	@SystemServiceLog(description="新增或更新车数据")
	@RequestMapping("/saveOrUpdateCarRescue")
	public void saveOrUpdate(CarRescue entity,HttpServletRequest request, HttpServletResponse response) throws IOException{
		log.info("新增或更新");
		SysUser user = (SysUser) getUser(request) ;
		entity.setLpn(entity.getLpn().replace("•", ""));
		if(entity.getId()==null||StringUtils.isBlank(entity.getId().toString())){
			carRescueService.insert(entity);
		}else{
			entity.setUpdateTime(new Date());
			entity.setUpdateUser(user.getAccount());
			carRescueService.updateByPrimaryKey(entity);
		}
		response.getWriter().print("success");
	}
	
	/**
	 * 删除数据
	 * @param 主键ID
	 * @return
	 * @throws IOException 
	 */
	@SystemServiceLog(description="删除数据车数据")
	@RequestMapping("/deleteCarRescueById")
	public void delete(Integer id,HttpServletRequest request,HttpServletResponse response) throws IOException{
		log.info("删除");
		if(null!=id ) {
			CarRescue carRescue = carRescueService.selectByPrimaryKey(id);
			int records = carRescueService.deleteByPrimaryKey(id);
			if(records > 0){
				TaskInfo taskInfo = taskService.selectByTaskTypeAndLpn("5",carRescue.getLpn());
				//taskInfo.setStatus("3");
				taskService.updateStatus(taskInfo.getId(),"3");
			}
		}
		response.getWriter().print("success");
	}
	
	/**车辆恢复运营*/
	@SystemServiceLog(description="车辆恢复运营")
	@RequestMapping(value = "/endRescue", method = { RequestMethod.GET, RequestMethod.POST})
	public String carResume(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		String result = request.getParameter("result");
		String id  = request.getParameter("m_id");
		int r = carRescueService.carResume(Integer.valueOf(id), getUser(request).getAccount(),result);
		if(r > 0){
			response.getWriter().print("succ");
		}else{
			response.getWriter().print("fail");
		}
		return null;		
	}
	
	/**保存车辆转维修的信息*/
	@SystemServiceLog(description="保存车辆转维修的信息")
	@RequestMapping(value = "/rescue_to_repair_save", method = { RequestMethod.GET, RequestMethod.POST })
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
	
	/**车辆結束运营*/
	@SystemServiceLog(description="结束救援")
	@RequestMapping(value = "/endRescueUpdate", method = { RequestMethod.GET, RequestMethod.POST})
	public String carResumeUpdate(CarRescue carRescue,HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		String id  = request.getParameter("m_id");
		String ifRescue=request.getParameter("ifRescue");
		String lpn = request.getParameter("s-lpn-1");
		String matterProess = request.getParameter("e-reason-2");
		// 车辆其它问题的描述
		String carProRemark = request.getParameter("carProRemark");
		// 技术其它问题的描述
		String tecProRemark = request.getParameter("tecProRemark");
		
		String sysDicIds = request.getParameter("sysDicIds");
		String[] arr = sysDicIds.split(",");
		List<String> param = Arrays.asList(arr);
		String responsibleDescription = request.getParameter("responsibleDescription");
		CarRescue cr = carRescueService.selectByPrimaryKey(Integer.parseInt(id));
		if("0".equals(cr.getStatus())){
			response.getWriter().print("finish");
			return null;
		}
		
		List<SysDic> sysDics = sysDicService.querySysDicBySysDicIds(param);
		List<CarRescueProblem> problems = new ArrayList<CarRescueProblem>();
		for (SysDic sysDic : sysDics) {
			CarRescueProblem problem = new CarRescueProblem();
			problem.setCreateTime(new Date());
			problem.setCarRescueId(cr.getId());
			problem.setCreateUser(getUser(request).getAccount());
			problem.setDicId(sysDic.getId());
			if("其它".equals(sysDic.getName())){
        		if("CAR_PROBLEM".equals(sysDic.getDicCode())){
        			problem.setProblemDescription("(其它)".concat(carProRemark));
        		}else{
        			problem.setProblemDescription("(其它)".concat(tecProRemark));
        		}
			}else{
				problem.setProblemDescription(sysDic.getName());
			}
			problems.add(problem);
		}
		carRescueService.insertCarRescueProblems(problems);
		cr.setResponsibleDescription(responsibleDescription);
		lpn = lpn.replace("•", "");
		int r=0;
		List<EvidenceRelation> urlLists = new ArrayList<EvidenceRelation>();
		// count用来控制上传图片的数量,超过4张就不处理了
		int count = 0;
		if(id!=null && !id.equals("")){
			cr.setResult(carRescue.getResult());
			cr.setIfRepair(ifRescue);
			// 图片处理
			MultipartHttpServletRequest mRequest = (MultipartHttpServletRequest) (request);  
	        Iterator<String> files = mRequest.getFileNames(); 
	        while (files.hasNext()&&4>count) { 
	        	MultipartFile muFile = mRequest.getFile(files.next());
	        	String fileName = muFile.getOriginalFilename();
	        	if(!"".equals(fileName)){
	        		EvidenceRelation eR = new EvidenceRelation();
	        		eR.setIsMemberComplain(2);
	        		eR.setReportId(Integer.parseInt(id));
	        	    eR.setPictureEvidenceUrl(uploadCarRescueMultipartFile(muFile,null,"picture"));
	        	    urlLists.add(eR);
	        	    count++;
	        	}
	        }
	//      carRescue.setLpn(lpn);
	//		carRescue.setId(Integer.parseInt(id));
			cr.setResponsibleType(request.getParameter("responsibleType"));
			cr.setMatterProcess(matterProess);
			cr.setUpdateTime(new Date());
			cr.setStatus("0");
			cr.setEndTime(new Date());
			cr.setUpdateUser(getUser(request).getAccount());
		    r=carRescueService.carResumeRepair(request,cr,urlLists);
		}
		if(r > 0){
			response.getWriter().print("succ");
		}else{
			response.getWriter().print("fail");
		}
		return null;		
	}
	private String uploadCarRescueMultipartFile(MultipartFile multipartFile,
			HttpServletRequest request, String useFile) throws Exception {
		String filename = multipartFile.getOriginalFilename();  
	    InputStream is = multipartFile.getInputStream();  
	    String newFileName = UUID.randomUUID().toString() + "." + filename.substring(filename.lastIndexOf(".") +1);
	    String endpoint = sysParamService.selectByKey("endpoint").getValue();
        String accessKeyId = sysParamService.selectByKey("accessKeyId").getValue();
        String accessKeySecret = sysParamService.selectByKey("accessKeySecret").getValue();
        String bucketName = sysParamService.selectByKey("bucketName").getValue();
        String dns = sysParamService.selectByKey("dns").getValue();
	    OSSClientAPI oss = new OSSClientAPI(endpoint, accessKeyId, accessKeySecret, bucketName, dns);
		String fileURL = oss.putObject(newFileName, is, useFile +"/");
		return fileURL;
	}


	@SystemServiceLog(description="车辆救援凭证")
	@RequestMapping(value = "/getRescueEviPicsByReportId", method = { RequestMethod.GET, RequestMethod.POST})
	public String getRescueEviPicsByReportId(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		String reportId = request.getParameter("reportId");
		if(!StringUtils.isBlank(reportId)){
			List<String> photos = carRescueService.getRescueEviPicsByReportId(Integer.valueOf(reportId));
			String str = photos.toString();
			response.getWriter().print(str);
		}else {
			response.getWriter().print("");
		}
		return null;		
	}
	
	@SystemServiceLog(description = "获取救援人员列表")
	@RequestMapping(value = "/getProblemAndRescueTypeInfo",method  = {RequestMethod.GET, RequestMethod.POST })
	public String getProblemAndRescueTypeInfo(int page, int rows, HttpServletRequest request, HttpServletResponse response) throws Exception{
		response.setContentType("text/html;charset=utf-8");
		String tecProblem = request.getParameter("tecProblem");
		String carProblem = request.getParameter("carProblem");
		List<String> param = new ArrayList<String>();
		param.add(tecProblem);
		param.add(carProblem);
		List<SysDic> lists = sysDicService.querySysDicByDicCodeList(param);
		response.getWriter().print(Data2Jsp.listToJson(lists));
		return null;
	}
}
