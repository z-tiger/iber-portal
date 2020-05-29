package com.iber.portal.controller.insurance;

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
import java.util.UUID;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.iber.portal.advices.SystemServiceLog;
import com.iber.portal.common.Data2Jsp;
import com.iber.portal.common.ExcelUtil;
import com.iber.portal.common.OSSClientAPI;
import com.iber.portal.common.Pager;
import com.iber.portal.controller.MainController;
import com.iber.portal.model.car.CarRescue;
import com.iber.portal.model.charging.ChargingPile;
import com.iber.portal.model.insurance.Insurance;
import com.iber.portal.model.insurance.InsuranceAttachment;
import com.iber.portal.model.sys.SysUser;
import com.iber.portal.service.insurance.InsuranceAttachmentService;
import com.iber.portal.service.insurance.InsuranceService;
import com.iber.portal.service.sys.SysParamService;
import com.iber.portal.util.DateTime;

@Controller
public class InsuranceController extends MainController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
    private InsuranceService insuranceService;
	
	@Autowired
	private InsuranceAttachmentService insuranceAttachmentService;
	
	@Autowired
    private SysParamService sysParamService ;
	
	/**
	 * @describe 保险页面
	 * 
	 */
	@SystemServiceLog(description="保险页面")
	@RequestMapping(value = "/insurance_page", method = { RequestMethod.GET })
	public String insurance(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("保险页面");
		return "insurance/insurance";		
	}
	
	/**
	 * @describe 图片多文件上传附件页面
	 * 
	 */
	@SystemServiceLog(description="图片多文件上传附件页面")
	@RequestMapping(value = "/insurance_uploader_page", method = { RequestMethod.GET })
	public String insuranceUploader(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("图片多文件上传附件页面");
		return "insurance/uploader";		
	}
	
	/**
	 * @describe 图片多文件上传
	 * 
	 */
	@SystemServiceLog(description="图片多文件上传")
	@RequestMapping(value = "/insurance_uploader_upload", method = { RequestMethod.GET , RequestMethod.POST })
	public String insuranceUploaderUpload(HttpServletRequest request, HttpServletResponse response,MultipartFile file)
			throws Exception {
		log.info("图片多文件上传");
		response.setContentType("application/json;charset=utf-8");
		
        String filename = file.getOriginalFilename(); 
        long fileSize = file.getSize();
        InputStream is = file.getInputStream();  
        String newFileName2 = UUID.randomUUID().toString() + "." + filename.substring(filename.lastIndexOf(".") +1);
//        
//        //文件上传到CDN
        String endpoint = sysParamService.selectByKey("endpoint").getValue();
        String accessKeyId = sysParamService.selectByKey("accessKeyId").getValue();
        String accessKeySecret = sysParamService.selectByKey("accessKeySecret").getValue();
        String bucketName = sysParamService.selectByKey("bucketName").getValue();
        String dns = sysParamService.selectByKey("dns").getValue();
	    OSSClientAPI oss = new OSSClientAPI(endpoint, accessKeyId, accessKeySecret, bucketName, dns);
		String fileUrl = oss.putObject(newFileName2, is, "insurance/");
		
//		CommonsMultipartFile cf= (CommonsMultipartFile)file; 
//        DiskFileItem fi = (DiskFileItem)cf.getFileItem(); 
//        File f = fi.getStoreLocation();
//		String newFileName = "";
//    	if (f != null && f.exists()) {
//			String fileType = filename.substring(filename.lastIndexOf(".")).toLowerCase();
//			String path = "uploadfiles/";
//			String realPath  = request.getRealPath(path)+"/";
//			File dir = new File(realPath);
//			if(!dir.exists()){//判断文件目录是否存在
//				dir.mkdirs();
//			}
//			String realName = UUID.randomUUID().toString();
//			newFileName = UUID.randomUUID().toString() + "." + filename.substring(filename.lastIndexOf(".") +1);
//			File target = new File(realPath, newFileName);
//			FileUtils.copyFile(f, target);
//    	}
    	JSONObject obj = new JSONObject();
    	obj.put("status", true);
    	obj.put("fileUrl", fileUrl);
    	obj.put("fileSize", fileSize);
    	response.getWriter().print(obj);
		return null;		
	}
	
	/**
	 * @describe 保险列表
	 */
	@SystemServiceLog(description="保险列表")
	@RequestMapping(value = "/insurance_list", method = { RequestMethod.GET , RequestMethod.POST })
	public String insuranceList(int page, int rows, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("保险列表");
		response.setContentType("text/html;charset=utf-8");
		Map<String,Integer> pageInfo = Data2Jsp.getFristAndPageSize(page, rows);
		int from = pageInfo.get("first_page");
		int to = pageInfo.get("page_size");
		
		String insuranceNo = request.getParameter("insurance_no");
		String lpn = request.getParameter("lpn");
		String insuranceCompany = request.getParameter("insurance_company");
		String insuranceHolder = request.getParameter("insurance_holder");
		String insuranceType = request.getParameter("insurance_type");
		String status = "";
		String queryDateFrom = request.getParameter("queryDateFrom");
		String queryDateTo = request.getParameter("queryDateTo");
		String check = request.getParameter("check");
		
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("from", from);
		paramMap.put("to", to);
		paramMap.put("insuranceNo", insuranceNo);
		paramMap.put("lpn", lpn);
		paramMap.put("insuranceCompany", insuranceCompany);
		paramMap.put("insuranceHolder", insuranceHolder);
		paramMap.put("insuranceType", insuranceType);
		paramMap.put("status", status);
		paramMap.put("queryDateFrom", queryDateFrom);
		paramMap.put("queryDateTo", queryDateTo);
		if("check".equals(check)){
			String checkDateFrom = DateTime.getNowDateString();
			String checkDateTo = DateTime.getNextMonthDateString();
			paramMap.put("checkDateFrom", checkDateFrom);
			paramMap.put("checkDateTo", checkDateTo);
		}
		
		//城市过滤
		SysUser user = (SysUser) request.getSession().getAttribute("user");
		String cityCode = null ;
		if(!user.getCityCode().equals("00")){
			cityCode = user.getCityCode() ;
		}
		paramMap.put("cityCode", cityCode);
		Pager<Insurance> pager = insuranceService.getAll(paramMap);
		response.getWriter().print(Data2Jsp.Json2Jsp(pager));
		return null;	
	}
	
	@SystemServiceLog(description="车辆保险信息数据导出")
	@RequestMapping(value = "/export_insurance_list", method = { RequestMethod.GET , RequestMethod.POST })
	public List<ChargingPile> exportExecl(String insuranceNo,String lpn,String insuranceCompany,String insuranceHolder,
			String insuranceType,String queryDateFrom,String queryDateTo,
			HttpServletRequest request,HttpServletResponse response) throws Exception {
		String fileName = "CarInsuranceReport" ;
		//列名充电桩编码	
		String columnNames [] = {"车牌号码", "保险公司",  "保险类型", "保险单", "保费（元）", "投保人", 
				"开始时间","结束时间","保险范围","创建时间" };
		
		String keys[] = { "lpn", "insuranceCompany","insuranceType", 
				"insuranceNo", "insuranceMoney", "insuranceHolder", "startTime",
				"endTime","insuranceRange","createTime"};
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("from", null);
		paramMap.put("to", null);
		paramMap.put("insuranceNo", insuranceNo);
		paramMap.put("lpn", lpn);
		paramMap.put("insuranceCompany", insuranceCompany);
		paramMap.put("insuranceHolder", insuranceHolder);
		paramMap.put("insuranceType", insuranceType);
		paramMap.put("queryDateFrom", queryDateFrom);
		paramMap.put("queryDateTo", queryDateTo);
		
		Pager<Insurance> pager = insuranceService.getAll(paramMap);
		List<Insurance> datas = pager.getDatas();
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		Map<String, Object> sheetNameMap = new HashMap<String, Object>();
		sheetNameMap.put("sheetName", "车辆保险信息数据报表");
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
			List<Insurance> data) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map;// 避免重复创建对象
		String keys[] = { "lpn", "insuranceCompany","insuranceType", 
				"insuranceNo", "insuranceMoney", "insuranceHolder", "startTime",
				"endTime","insuranceRange","createTime"};
		for (Insurance rance : data) {
			map = new HashMap<String, Object>();
			String lpn = rance.getLpn();
			if(lpn != null){
				map.put(keys[0], lpn.indexOf("•")<0 ?lpn.substring(0, 2)+"•"+lpn.substring(2):lpn);
			}
			map.put(keys[1], rance.getInsuranceCompany());
			map.put(keys[2], rance.getInsuranceType());
			map.put(keys[3], rance.getInsuranceNo());
			map.put(keys[4], rance.getInsuranceMoney()!=null?new DecimalFormat("0.00").format(rance.getInsuranceMoney()/100.0):0.00);
			map.put(keys[5], rance.getInsuranceHolder());
			map.put(keys[6], rance.getStartTime()!=null?new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(rance.getStartTime()):"" );
			map.put(keys[7], rance.getEndTime()!=null?new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(rance.getEndTime()):""); 
			map.put(keys[8], rance.getInsuranceRange());
			map.put(keys[9], rance.getCreateTime()!=null?new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(rance.getCreateTime()):"");

			list.add(map);
		}
		return list;
	}
	
	/**
	 * @describe 保存更新保险
	 * 
	 */
	@SystemServiceLog(description="保存更新保险")
	@RequestMapping(value = "/insurance_saveOrUpdate", method = { RequestMethod.GET , RequestMethod.POST })
	public String insuranceSaveOrUpdate(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String msg = "success";
		log.info("保存更新保险");
		response.setContentType("text/html;charset=utf-8");
		String id = request.getParameter("id");
		String lpn = request.getParameter("lpn").replace("•", "");
		String insuranceCompany = request.getParameter("insurance_company");
		String insuranceType = request.getParameter("insurance_type");
		String insuranceNo = request.getParameter("insurance_no");
		String insuranceMoney = request.getParameter("insurance_money");
		Double insuranceMoneyD = Double.parseDouble(insuranceMoney);
		int insuranceMoneyI = (int) (insuranceMoneyD*100) ;
		String insuranceHolder = request.getParameter("insurance_holder");
		String startTime = request.getParameter("start_time")+" 00:00:00";
		String endTime = request.getParameter("end_time")+" 23:59:59";
		String insuranceRange = request.getParameter("insurance_range");
		String uploud_url_arr = request.getParameter("uploud_url_arr");
		SysUser user = getUser(request);
//		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		Date dt1 = df.parse(startTime);
//        Date dt2 = df.parse(endTime);
//		if(dt1.getTime()>dt2.getTime()){
//			String a = startTime ;
//			startTime = endTime;
//			endTime = a;
//		}
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("lpn", lpn);
		paramMap.put("insuranceType", insuranceType);
		paramMap.put("startTime", startTime);
		paramMap.put("endTime", endTime);
		if (id!=null && !id.equals("")) {
		paramMap.put("myId", Integer.parseInt(id));
		}
		List<Insurance> insuranceList = insuranceService.selectByLpnAndType(paramMap);
		if(insuranceList.size()>0){
			msg = "fail";
		}else{
			
			if (id!=null && !id.equals("")) {
				Insurance currObj = insuranceService.selectByPrimaryKey(Integer.parseInt(id));
				if (currObj!=null) {
					currObj.setEndTime(DateTime.getDateTime(endTime));
					currObj.setInsuranceCompany(insuranceCompany);
					currObj.setInsuranceHolder(insuranceHolder);
//				currObj.setInsuranceHolderIdcard(insuranceHolderIdcard);
					currObj.setInsuranceMoney(insuranceMoneyI);
					currObj.setInsuranceNo(insuranceNo);
					currObj.setInsuranceRange(insuranceRange);
					currObj.setInsuranceType(insuranceType);
					if (uploud_url_arr!=null && !uploud_url_arr.equals("")) {
						currObj.setIsAttachment(currObj.getIsAttachment()+uploud_url_arr.split("@#@#@#@#@").length);//有附件
					}
					currObj.setLpn(lpn);
//				currObj.setRemark(remark);
					currObj.setStartTime(DateTime.getDateTime(startTime));
					currObj.setUpdateTime(new Date());
					currObj.setUpdateUser(user.getName());
					insuranceService.updateByPrimaryKeySelective(currObj);
				}
			}else{	
				Insurance obj = new Insurance();
				obj.setCreateTime(new Date());
				obj.setCreateUser(user.getName());
				obj.setEndTime(DateTime.getDateTime(endTime));
				obj.setInsuranceCompany(insuranceCompany);
				obj.setInsuranceHolder(insuranceHolder);
//			obj.setInsuranceHolderIdcard(insuranceHolderIdcard);
				obj.setInsuranceMoney(insuranceMoneyI);
				obj.setInsuranceNo(insuranceNo);
				obj.setInsuranceRange(insuranceRange);
				obj.setInsuranceType(insuranceType);
				if (uploud_url_arr!=null && !uploud_url_arr.equals("")) {
					obj.setIsAttachment(uploud_url_arr.split("@#@#@#@#@").length);//附件数
				}else{
					obj.setIsAttachment(0);//没有附件
				}
				obj.setLpn(lpn);
//			obj.setRemark(remark);
				obj.setStartTime(DateTime.getDateTime(startTime));
				obj.setStatus(0);
				
				insuranceService.insertSelective(obj);
				id = String.valueOf(obj.getId());
				
			}
			
			if (uploud_url_arr!=null && !uploud_url_arr.equals("")) {
				String urlArr[] = uploud_url_arr.split("@#@#@#@#@");
				for (int i = 0; i < urlArr.length; i++) {
					String attachName =  urlArr[i].split("::")[0];
					String attachUrl = urlArr[i].split("::")[1];
					String attachSize = urlArr[i].split("::")[2];
					InsuranceAttachment obj = new InsuranceAttachment();
					obj.setAttachName(attachName);
					obj.setAttachSize(attachSize);
					obj.setAttachType("insurance");
					obj.setAttachUrl(attachUrl);
					obj.setCreateTime(new Date());
					obj.setInsuranceId(Integer.parseInt(id));
					insuranceAttachmentService.insertSelective(obj);
				}
			}
		}
		
		response.getWriter().print(msg);
		return null;	
	}
	
	/**
	 * @describe 图片预览
	 * 
	 */
	@SystemServiceLog(description="图片预览")
	@RequestMapping(value = "/insurance_show_attachment", method = { RequestMethod.GET , RequestMethod.POST })
	public String insuranceShowAttachment(String id, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("图片预览");
		response.setContentType("text/html;charset=utf-8");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("insuranceId", id);
		paramMap.put("attachType", "insurance");
		List<InsuranceAttachment> insuranceAttachment = insuranceAttachmentService.getAllAttachment(paramMap);
		JSONArray obj = new JSONArray();
		obj.add(insuranceAttachment);
		response.getWriter().print(obj);
		return null;
	}
	/**
	 * @describe 删除保险
	 * 
	 */
	@SystemServiceLog(description="删除保险")
	@RequestMapping(value = "/insurance_del", method = { RequestMethod.GET , RequestMethod.POST })
	public String insuranceDel(String id, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("删除保险");
		response.setContentType("text/html;charset=utf-8");
		if (id!=null && !id.equals("")) {
			insuranceService.deleteByPrimaryKey(Integer.parseInt(id));
		}
		response.getWriter().print("success");
		return null;
	}
	
}
