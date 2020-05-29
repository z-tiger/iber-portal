package com.iber.portal.controller.car;

import com.iber.portal.advices.SystemServiceLog;
import com.iber.portal.common.Data2Jsp;
import com.iber.portal.common.ExcelUtil;
import com.iber.portal.common.OSSClientAPI;
import com.iber.portal.common.Picture;
import com.iber.portal.dao.car.CarSelfCheckItemMapper;
import com.iber.portal.dao.car.CarSelfCheckMapper;
import com.iber.portal.model.car.CarSelfCheck;
import com.iber.portal.model.car.CarSelfCheckItem;
import com.iber.portal.model.charging.ChargingPile;
import com.iber.portal.model.sys.SysUser;
import com.iber.portal.service.sys.SysParamService;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author:cuichongc
 * @Version:1.0
 */
@Controller
public class CarSelfCheckController {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private CarSelfCheckItemMapper ItemMapper;
	
	@Autowired
	private CarSelfCheckMapper itemListMapper;

	@Autowired
	private SysParamService sysParamService ;
	
	
	@SystemServiceLog(description="车辆自检配置列表")
	@RequestMapping(value={"/car_self_check_item"},method=RequestMethod.GET)
	public String selfCheckItemList(HttpServletRequest request,HttpServletResponse response)throws Exception{
		log.info("车辆自检项配置");
		return "car/carSelfCheck";
	}
	@SystemServiceLog(description="车辆自检列表")
	@RequestMapping(value={"/car_self_check"},method=RequestMethod.GET)
	public String selfCheckList(HttpServletRequest request,HttpServletResponse response)throws Exception{
		log.info("车辆自检列表");
		return "car/selfCheckList";
	}
	@SystemServiceLog(description="查看图片")
	@RequestMapping(value = "/udCarImgOpen", method = { RequestMethod.GET, RequestMethod.POST})
	public String upDownCarCheck(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		request.setAttribute("img", request.getParameter("img"));
		return "car/udCheckCarImgOpen";		
	}
	
	@SystemServiceLog(description="车辆自检项列表")
	@RequestMapping(value={"/car_self_check_list"},method={RequestMethod.GET,RequestMethod.POST})
	public String carCheckList(int rows,int page,HttpServletRequest request,HttpServletResponse response)throws Exception{
		response.setCharacterEncoding("utf-8");
		Map<String, Integer> pageInfo = Data2Jsp.getFristAndPageSize(page, rows);
		Map<String,Object> map = new HashMap<String, Object>();
		int from = pageInfo.get("first_page");
		int to = pageInfo.get("page_size");
		String itemType = request.getParameter("itemType");
		String status = request.getParameter("status");
		String appType = request.getParameter("appType");
		map.put("itemType", itemType);
		map.put("appType", appType);
		map.put("status", status);
		map.put("from", from);
		map.put("to", to);
		List<CarSelfCheckItem> data = ItemMapper.selectAll(map);
		int total = ItemMapper.selectCount(map);
		String json = Data2Jsp.Json2Jsp(data, total);
		response.getWriter().print(json);
		return null;
	}
	
	/**
	 * 新增 or 修改车辆自检项
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SystemServiceLog(description="车辆自检项增加")
	@RequestMapping(value={"/save_car_self_check"},method={RequestMethod.POST})
	public String save(HttpServletRequest request,HttpServletResponse response,MultipartFile samplePhoto)throws Exception{
		response.setCharacterEncoding("utf-8");
		String id = request.getParameter("id");
		String itemName = request.getParameter("itemName").trim();
		if(itemName.length()>6){
			response.getWriter().print("NameErr");
			return null;
		}
		CarSelfCheckItem items  = null;
		if(StringUtils.isNotBlank(id)){
			items = ItemMapper.selectByPrimaryKey(Integer.valueOf(id));
			items.setUpdateTime(new Date());
		}else{
			items = new CarSelfCheckItem();
			items.setStatus(1);
			items.setCreateTime(new Date());
			items.setUpdateTime(new Date());
		}
		String samplePhotoUri=null;
		if(samplePhoto!=null&& samplePhoto.getSize()!=0){
			OSSClientAPI oss = makeUploadFileOss();
			samplePhotoUri = uploadImg(samplePhoto, oss);
			items.setSamplePhotoUri(samplePhotoUri);
		}
		items.setItemName(itemName);
		
		SysUser user = (SysUser)request.getSession().getAttribute("user");
		items.setCreateUser(user.getId());
		
		String itemType = request.getParameter("itemType");
		if(StringUtils.isNotBlank(itemType)) items.setItemType(Integer.parseInt(itemType));
		
		
		String appType = request.getParameter("appType");
		if(StringUtils.isNotBlank(appType)) items.setAppType(appType);
		
		String exceptionUploadStatus = request.getParameter("exceptionUploadStatus");
		if(StringUtils.isNotBlank(exceptionUploadStatus)) items.setExceptionUploadStatus(Integer.parseInt(exceptionUploadStatus));
		
		String uploadStatus = request.getParameter("uploadStatus");
		if(StringUtils.isNotBlank(uploadStatus)) items.setUploadStatus(Integer.parseInt(uploadStatus));
		
		
		if(StringUtils.isNotBlank(id)){
			ItemMapper.updateByPrimaryKey(items);
		}else {
			ItemMapper.insertSelective(items);
		}
		response.getWriter().print("succ");
		return null;
	}

	/**
	 * @return
	 */
	private OSSClientAPI makeUploadFileOss() {
		String endpoint = sysParamService.selectByKey("endpoint").getValue();
		String accessKeyId = sysParamService.selectByKey("accessKeyId").getValue();
		String accessKeySecret = sysParamService.selectByKey("accessKeySecret").getValue();
		String bucketName = sysParamService.selectByKey("bucketName").getValue();
		String dns = sysParamService.selectByKey("dns").getValue();
		OSSClientAPI oss = new OSSClientAPI(endpoint, accessKeyId, accessKeySecret, bucketName, dns);
		return oss;
	}

	private String uploadImg(MultipartFile file, OSSClientAPI oss) throws Exception{
		String filename = file.getOriginalFilename();
		if(!filename.equals("")){
			CommonsMultipartFile cf= (CommonsMultipartFile)file; //这个myfile是MultipartFile的
			DiskFileItem fi = (DiskFileItem)cf.getFileItem();
			File file1 = fi.getStoreLocation();
			InputStream is =   Picture.resizePngNoCompress(file1, filename.substring(filename.lastIndexOf(".") +1)) ;
//			InputStream is =   Picture.resizePNG(file1, 200, 100, filename.substring(filename.lastIndexOf(".") +1)) ;
			String newFileName = UUID.randomUUID().toString() + "." + filename.substring(filename.lastIndexOf(".") +1);

			return oss.putObject(newFileName, is, "selfItem/");
		}
		return null;
	}
	@SystemServiceLog(description="车辆自检项启用关闭")
	@RequestMapping(value={"/set_check_status"},method={RequestMethod.GET,RequestMethod.POST})
	public String setStatus(Integer id,String status,HttpServletRequest request,HttpServletResponse response)throws Exception{
		response.setCharacterEncoding("utf-8");
		if(id == null){
			response.getWriter().print("NoId");
			return null;
		}
		CarSelfCheckItem ck = ItemMapper.selectByPrimaryKey(id);
		if(StringUtils.equals(status, "1")){
			ck.setStatus(0);
			ItemMapper.updateByPrimaryKeySelective(ck);
			response.getWriter().print("closeSucc");
		}else if(StringUtils.equals(status, "0")){
			ck.setStatus(1);
			ItemMapper.updateByPrimaryKeySelective(ck);
			response.getWriter().print("openSucc");
		}
		return null;
	}
	@SystemServiceLog(description="车辆自检列表")
	@RequestMapping(value={"/car_self_check_info"},method={RequestMethod.GET,RequestMethod.POST})
	public String carCheckInfo(int rows,int page,HttpServletRequest request,HttpServletResponse response)throws Exception{
		response.setCharacterEncoding("utf-8");
		Map<String, Integer> pageInfo = Data2Jsp.getFristAndPageSize(page, rows);
		Map<String,Object> map = new HashMap<String, Object>();
		int from = pageInfo.get("first_page");
		int to = pageInfo.get("page_size");
		String cityCode = request.getParameter("cityCode");
		String phone = request.getParameter("phone");
		String lpn = request.getParameter("lpn");
		String status = request.getParameter("status");
		String exceptionStatus = request.getParameter("exceptionStatus");
		String orderId = request.getParameter("orderId");
		map.put("status", status);
		map.put("exceptionStatus", exceptionStatus);
		map.put("orderId", orderId);
		SysUser user = (SysUser) request.getSession().getAttribute("user");
		if(StringUtils.equals(user.getCityCode(), "00")){
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
		String itemType = request.getParameter("itemType");//自检类型
		map.put("itemType", itemType);
		map.put("from", from);
		map.put("to", to);
		map.put("phone", phone);
		map.put("lpn", lpn);
		List<CarSelfCheck> data = itemListMapper.selectAll(map);
		int total = itemListMapper.selectCount(map);
		String json = Data2Jsp.Json2Jsp(data, total);
		response.getWriter().print(json);
		return null;
	}
	
	@SystemServiceLog(description="车辆自检数据导出")
	@RequestMapping(value = "/export_carSelf_list", method = { RequestMethod.GET , RequestMethod.POST })
	public List<ChargingPile> exportExecl(String lpn,String cityCode,String phone,String status,
			String exceptionStatus,String orderId,String itemType,
			HttpServletRequest request,HttpServletResponse response) throws Exception {
		String fileName = "CarSelfReport" ;
		//列名充电桩编码	
		String columnNames [] = {"订单号", "所属城市",  "车牌号", "姓名", "手机号", "身份", 
				"反馈时间","自检类型","项目","是否异常","处理状态","处理人"};
		
		String keys[] = { "orderId", "cityName","lpn", 
				"name", "phone", "memberType", "createTime",
				"itemType","itemName","","handleStatus","handleUser"};
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", status);
		map.put("exceptionStatus", exceptionStatus);
		map.put("orderId", orderId);
		SysUser user = (SysUser) request.getSession().getAttribute("user");
		if(StringUtils.equals(user.getCityCode(), "00")){
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
		map.put("itemType", itemType);
		map.put("phone", phone);
		map.put("lpn", lpn);
		map.put("from", null);
		map.put("to", null);
		List<CarSelfCheck> datas = itemListMapper.selectAll(map);
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		Map<String, Object> sheetNameMap = new HashMap<String, Object>();
		sheetNameMap.put("sheetName", "车辆自检数据报表");
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
			List<CarSelfCheck> data) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map;// 避免重复创建对象
		String keys[] = { "orderId", "cityName","lpn", 
				"name", "phone", "memberType", "createTime",
				"itemType","itemName","","handleStatus","handleUser"};
		for (CarSelfCheck check : data) {
			map = new HashMap<String, Object>();
			map.put(keys[0], check.getOrderId());
			map.put(keys[1], check.getCityName());
			String lpn = check.getLpn();
			if(lpn != null){
				map.put(keys[2], lpn.indexOf("•")<0 ?lpn.substring(0, 2)+"•"+lpn.substring(2):lpn);
			}
			map.put(keys[3], check.getName());
			map.put(keys[4], check.getPhone());
			if(check.getMemberType() != null){
				if(check.getMemberType().equals("member")){
					map.put(keys[5], "会员");
				}else{
					map.put(keys[5], "员工");
				}
			}else{
				map.put(keys[5], "");
			}
			map.put(keys[6], check.getCreateTime() != null?new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(check.getCreateTime()):"");
			String type = check.getItemType();
			if(type != null){
				if(type.equals("0")){
					map.put(keys[7], "上车自检");
				}else{
					map.put(keys[7], "下车自检");
				}
			}else{
				map.put(keys[7], type);
			}
			map.put(keys[8], check.getItemName());
			if(check.getHandleStatus() != null){
				if(check.getHandleStatus()==2){
					map.put(keys[9], "正常");
				}else{
					map.put(keys[9], "异常");
				}
			}else{
				map.put(keys[9], "异常");
			}
			Integer status = check.getHandleStatus();
			if(status != null){
				if(status == 0){
					map.put(keys[10], "未处理");
				}else if(status ==2 ){
					map.put(keys[10], "无需处理");
				}else{
					map.put(keys[10], "已处理");
				}
			}else{
				map.put(keys[10], "已处理");
			}
			map.put(keys[11], check.getHandleUser());
			
			list.add(map);
		}
		return list;
	}
	
	@SystemServiceLog(description="自检审核处理")
	@RequestMapping(value={"/handle_car_self_check"},method={RequestMethod.GET,RequestMethod.POST})
	public String handleCar(HttpServletRequest request,HttpServletResponse response)throws Exception{
		response.setCharacterEncoding("utf-8");
		String id = request.getParameter("id");
		CarSelfCheck ck = itemListMapper.selectByPrimaryKey(Integer.valueOf(id));
		SysUser user = (SysUser)request.getSession().getAttribute("user");
		if(ck.getHandleStatus()== 1){
			response.getWriter().print("pass");
			return null;
		}else if(ck.getHandleStatus()== 2) {
			response.getWriter().print("succ");
			return null;
		}else{
			ck.setHandleStatus(1);
			ck.setHandleUser(user.getName());
			itemListMapper.updateByPrimaryKeySelective(ck);
			response.getWriter().print("succ");
		}
			return null;
		}
	@SystemServiceLog(description="自检审核批处理")
	@RequestMapping(value={"/batch_handle"},method={RequestMethod.POST})
	@ResponseBody
	public String batchHandle(@RequestParam(value="idList") List<Integer> idList,
			HttpServletRequest request,HttpServletResponse response)throws Exception{
		response.setCharacterEncoding("utf-8");
		SysUser user = (SysUser)request.getSession().getAttribute("user");
		String name = user.getName();
		List<Boolean> statusList = itemListMapper.selecthandleStatus(idList);
		if(hasSame(statusList)==true){
			for (Boolean b : statusList) {
				if(null != b && b==true){
					response.getWriter().print("pass");
					return null;
				}else{
					if(!idList.isEmpty()&&idList.size()>1){
						List<String> orderList = itemListMapper.selectOrderList(idList);
						List<String> names = new ArrayList<String>();
						if(StringUtils.isNotBlank((name))){
							names.add(name);
						}
						if(hasSame(orderList)==true){
							Map map = new HashMap();
							map.put("names", names);
							map.put("idList", idList);
							itemListMapper.batchUpdateHandleStatus(map);
							response.getWriter().print("succ");
							return null;
						}else{
							response.getWriter().print("error");
							return null;
						}
					}else{
						response.getWriter().print("numErr");
						return null;
					}
				}
			}
		}
		return null;
	}
	private boolean hasSame(List<? extends Object> list){
        if(null == list)
            return false;
        return 1 == new HashSet<Object>(list).size();
    }
}
