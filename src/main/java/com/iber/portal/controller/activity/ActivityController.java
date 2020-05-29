package com.iber.portal.controller.activity;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.iber.portal.advices.SystemServiceLog;
import com.iber.portal.common.Data2Jsp;
import com.iber.portal.common.HttpUtils;
import com.iber.portal.common.HttpsClientUtil;
import com.iber.portal.common.OSSClientAPI;
import com.iber.portal.common.Pager;
import com.iber.portal.common.ResultMsg;
import com.iber.portal.controller.MainController;
import com.iber.portal.model.activity.Activity;
import com.iber.portal.model.base.Member;
import com.iber.portal.model.sys.SysParam;
import com.iber.portal.model.sys.SysUser;
import com.iber.portal.service.activity.ActivityService;
import com.iber.portal.service.base.MemberService;
import com.iber.portal.service.sys.SysParamService;
import com.iber.portal.util.SendSMS;

import net.sf.json.JSONObject;

/**
 * 
 * <br>
 * <b>功能：</b>ActivityController<br>
 * <b>作者：</b>www.jeecg.org<br>
 * <b>日期：</b> Feb 2, 2013 <br>
 * <b>版权所有：<b>版权所有(C) 2013，www.jeecg.org<br>
 */ 
@Controller
public class ActivityController extends MainController {
	
	private final static Logger log= Logger.getLogger(ActivityController.class);
	
	// Servrice start
	@Autowired(required=false) //自动注入，不需要生成set方法了，required=false表示没有实现类，也不会报错。
	private ActivityService activityService; 
	
	@Autowired
	private SysParamService sysParamService;
	
//	@Autowired
//	private SysDicService sysDicService;
	
	/**
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/activity_page") 
	public String activity_page(HttpServletRequest request, HttpServletResponse response) {
		log.info("Activity页面");
		return "activity/activity" ;
	}
	
	
	/**
	 * 数据
	 * @param page
	 * @param rows
	 * @return
	 * @throws IOException 
	 */
	@SystemServiceLog(description = "活动列表")
	@RequestMapping(value = "/charge_activity_page", method = { RequestMethod.GET, RequestMethod.POST }) 
	public void  datalist(int page, int rows, HttpServletRequest request, HttpServletResponse response) throws IOException{
		log.info("数据列表");
		response.setContentType("text/html;charset=utf-8");
		Map<String,Integer> pageInfo = Data2Jsp.getFristAndPageSize(page, rows);
		Map<String, Object> paramMap = new HashMap<String, Object>();
		int from = pageInfo.get("first_page");
		int to = pageInfo.get("page_size");
		String title = request.getParameter("title");
		String code = request.getParameter("code");
		String status = request.getParameter("status");
		paramMap.put("from", from);
		paramMap.put("to", to);
		paramMap.put("title", title);
		paramMap.put("code", code);
		paramMap.put("status", status);
		
		Pager<Activity> queryPageList = activityService.queryPageList(paramMap);
		response.getWriter().print(Data2Jsp.Json2Jsp(queryPageList));
//		if ("activity_open".equals(code)) {
//			paramMap.put("status", 1);
//		}else if ("activity_close".equals(code)) {
//			paramMap.put("status", 0);
//		}else {
//			paramMap.put("status", -1);
//		}
//		if (request.getParameter("activityName")!=null) {
//			List<Activity> activities = activityService.selectByCode(request.getParameter("activityName"));
//			List<String> remarks = new ArrayList<String>();
//			for (Activity activity : activities) {
//				remarks.add(activity.getRemark());
//			}
//			paramMap.put("remarks", remarks);
//		}
//		
//		paramMap.put("title", request.getParameter("title"));
//		paramMap.put("url", request.getParameter("url"));
//		paramMap.put("startTime", request.getParameter("startTime"));
//		paramMap.put("endTime", request.getParameter("endTime"));
//		Pager<Activity> pager = activityService.queryPageList(paramMap);
//		Pager<ActivityVO> pagerVO = new Pager<ActivityVO>();
//		//获得活动的创建人和更新人
//		List<Activity> datas = pager.getDatas();
//		Map<String, Object> maps = new HashMap<String, Object>();
//		maps.put("dicCode", SysConstant.ACTIVITY_TYPE);
//		List<ActivityVO> activityVOs = new ArrayList<ActivityVO>();
//		for (Activity activity : datas) {
//			maps.put("itemCode", activity.getCode());
//			SysDic sysDic = sysDicService.selectByCode(maps);
//			if(null==sysDic){
//				continue;
//			}
//			ActivityVO newActivity = new ActivityVO();
//			newActivity.setUpdaterName(activity.getUpdateSysUserName());
//			newActivity.setCreaterName(activity.getCreateSysUserName());
//			//根据活动表中的code查询数据字典表中的name
//			newActivity.setActivityName(sysDic.getName());
//			newActivity.setCode(sysDic.getCode());
//			newActivity.setCreateTime(activity.getCreateTime());
//			newActivity.setUpdateTime(activity.getUpdateTime());
//			newActivity.setEndTime(activity.getEndTime());
//			newActivity.setId(activity.getId());
//			newActivity.setImgUrl(activity.getImgUrl());
//			//将remark转为vo对象
//			JSONObject obj = new JSONObject().fromObject(activity.getRemark());//将json字符串转换为json对象
//			ActivityPartVO vo = (ActivityPartVO)JSONObject.toBean(obj,ActivityPartVO.class);//将建json对象转换为Person对象
//			
//			newActivity.setBalance(vo.getBalance()/100);
//			newActivity.setDeadline(vo.getDeadline());
//			newActivity.setNumber(vo.getNumber());
//			newActivity.setStartTime(activity.getStartTime());
//			newActivity.setStatus(activity.getStatus());
//			newActivity.setTitle(activity.getTitle());
//			newActivity.setUrl(activity.getUrl());
//			newActivity.setCityCode(activity.getCityCode());
//			activityVOs.add(newActivity);
//		}
//		pagerVO.setDatas(activityVOs);
//		pagerVO.setPageNumber(pager.getPageNumber());
//		pagerVO.setPageSize(pager.getPageSize());
//		pagerVO.setTotalCount(pager.getTotalCount());
//		response.getWriter().print(Data2Jsp.Json2Jsp(pagerVO));
	}
	
	/**
	 * 添加或修改数据
	 * @param Activity
	 * @return
	 * @throws IOException 
	 */
	@SystemServiceLog(description = "活动更新保存")
	@RequestMapping(value="/saveOrUpdateActivity", method = { RequestMethod.GET , RequestMethod.POST })
	@ResponseBody
	public Map<String, Object> saveOrUpdate(Activity entity,HttpServletRequest request, HttpServletResponse response, MultipartFile activityMultipartFile) throws Exception{
		response.setContentType("text/html;charset=utf-8");
		log.info("新增或更新");
		SysUser user = (SysUser) request.getSession().getAttribute("user");
		Activity activity = activityService.selectByPrimaryKey(entity.getId());
		if(activity != null ){
			activity.setTitle(entity.getTitle());
			activity.setUrl(entity.getUrl());
			if(!entity.getStartTime().equals(activity.getStartTime())||!entity.getEndTime().equals(activity.getEndTime())){
				if(entity.getStartTime().after(entity.getEndTime())){
					return ResultMsg.fialResult("开始时间必须小于结束时间！");
				}
			}
			
			final Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", entity.getId());
			map.put("cityCode", entity.getCityCode());
			List<Activity> list = activityService.selectByIdCode(map);
			if(!list.isEmpty()){
				for (Activity activity2 : list) {
					Date start2 = activity2.getStartTime();
					Date end2 = activity2.getEndTime();
					//当cityCod不变时
					if(entity.getCityCode().equals(activity.getCityCode())){
						//当更改开始时间或者结束时间
						if(!entity.getStartTime().equals(activity.getStartTime())
								||!entity.getEndTime().equals(activity.getEndTime())){
							activity.setStartTime(entity.getStartTime());
							activity.setEndTime(entity.getEndTime());
							if(!(activity.getEndTime().before(start2)||activity.getStartTime().after(end2))){
								return ResultMsg.fialResult("同一城市时间区间只有一个！");
							}
						}
					}else{
						activity.setStartTime(entity.getStartTime());
						activity.setEndTime(entity.getEndTime());
						if(!(activity.getEndTime().before(start2)||activity.getStartTime().after(end2))){
							return ResultMsg.fialResult("同一城市时间区间只有一个！");
						}
					}
					
				}
			}
			activity.setCityCode(entity.getCityCode());
			activity.setStartTime(entity.getStartTime());
			activity.setEndTime(entity.getEndTime());
			String filename = activityMultipartFile.getOriginalFilename();
			if(!"".equals(filename)){
				InputStream is = activityMultipartFile.getInputStream();
				String uploadFile = uploadFile(filename, is);
				activity.setImgUrl(uploadFile);
			}
			activity.setStatus(entity.getStatus());
			activity.setCode(entity.getCode());
			activity.setUpdateTime(new Date());
			activity.setUpdateId(user.getId());
			int count = activityService.updateByPrimaryKeySelective(activity);
			if(count == 0){
				return ResultMsg.fialResult("修改失败！");
			}
			return ResultMsg.succResult("修改成功！");
		}else {
			if(entity.getStartTime().after(entity.getEndTime())){
				return ResultMsg.fialResult("开始时间必须小于结束时间！");
			}
			List<Activity> list = activityService.selecIdenticalCity(entity.getCityCode());
			Date start1 = entity.getStartTime();
			Date end1 = entity.getEndTime();
			if(!list.isEmpty()){
				for (Activity activity2 : list) {
					Date start2 = activity2.getStartTime();
					Date end2 = activity2.getEndTime();
					if((start1.after(start2)&&start1.before(end2))
							||(end1.after(start2)&&end1.before(end2))
							||(start2.after(start1)&&start2.before(end1))
							||(end2.after(start1)&&end2.before(end1))){
						return ResultMsg.fialResult("同一城市时间区间只有一个！");
					}else{
						continue ;
					}
				}
			}
			String filename = activityMultipartFile.getOriginalFilename();
			if(!"".equals(filename)){
				InputStream is = activityMultipartFile.getInputStream();
				String uploadFile = uploadFile(filename, is);
				entity.setImgUrl(uploadFile);
			}
			entity.setCreateId(user.getId());
			entity.setCreateTime(new Date());
			int count = activityService.insertSelective(entity);
			if(count == 0){
				return ResultMsg.fialResult("添加失败！");
			}
			return ResultMsg.succResult("添加成功！"); 
		}
	}
	
	
	/**
	* 文件上传
	*/
	public String uploadFile(String filename, InputStream is) throws Exception {
		String newFileName = UUID.randomUUID().toString() + "."	+ filename.substring(filename.lastIndexOf(".") + 1);
		// 文件上传到CDN
		String endpoint = sysParamService.selectByKey("endpoint").getValue();
		String accessKeyId = sysParamService.selectByKey("accessKeyId").getValue();
		String accessKeySecret = sysParamService.selectByKey("accessKeySecret").getValue();
		String bucketName = sysParamService.selectByKey("bucketName").getValue();
		String dns = sysParamService.selectByKey("dns").getValue();
		OSSClientAPI oss = new OSSClientAPI(endpoint, accessKeyId, accessKeySecret, bucketName, dns);
		String activityFilePhoto = oss.putObject(newFileName, is,"activityFile/");
		return activityFilePhoto;
		
	}
//		long startTimeStamp = entity.getStartTime().getTime();
//		long endTimeStamp = DateTime.getEndTimeOfDay(entity.getEndTime()).getTime();
//		if(startTimeStamp-endTimeStamp>0){
//			//开启时间不能晚于结束时间
//			response.getWriter().print("timeError");
//			return;
//		}
//		Map<String, Object> maps = new HashMap<String, Object>();
//		maps.put("itemCode", activityName);
//		maps.put("dicCode", SysConstant.ACTIVITY_TYPE);
//		SysDic sysDicBean = sysDicService.selectByCode(maps);
//		if(null==sysDicBean){
//			response.getWriter().print("typeError");
//			return;
//		}
//		//获取所有开启的活动
//		Map<String, Object> paramMap = new HashMap<String, Object>();
//		paramMap.put("status", "1");
//		List<Activity> activities = activityService.selectStartingActivity(paramMap);
//		List<ActivityTime> activityTimes = new ArrayList<ActivityTime>();
//		for (Activity activity2 : activities) {
//			ActivityTime time = new ActivityTime();
//			if (entity.getId()!=null && !entity.getId().equals(activity2.getId()) && activity2.getStatus() != null && activity2.getStatus() == 1) {
//				time.setStartTime(activity2.getStartTime());
//				time.setEndTime(activity2.getEndTime());
//				activityTimes.add(time);
//			}
//		}
//		/**
//		 * 添加活动
//		 */
////		if (entity.getId()==null ||StringUtils.isBlank(entity.getId().toString())) {
////			//判断填写的时间是否不在之前所有活动的开始和结束时间之间，或者开始时间小于之前活动的开始时间，结束时间大于之前活动的结束时间
////			if("1".equals(code)){
////				for (Activity act : activities) {
////					if((startTimeStamp-act.getEndTime().getTime() >=0 ) || 
////						    (endTimeStamp - act.getStartTime().getTime()<=0)) {
////	                             continue;
////				           }else{
////					           response.getWriter().print("activityFail");
////					           return;
////				           }
////				}
////			}
//			/**
//			 * 根据cityCode 查找同一个城市,判断时间区间
//			 */
//			if(StringUtils.isNotBlank(entity.getCityCode())){
//				List<Activity> activityList = activityService.selectActivityByCode(entity.getCityCode());
//				for (Activity activity : activityList) {
//					if(entity.getStartTime().after(activity.getStartTime())&&entity.getEndTime().before(activity.getEndTime())){
//						response.getWriter().print("activityFail");
//						return ;
//					}else{
//						continue;
//					}
//				}
//			Activity activity = new Activity();
//			String filename = activityMultipartFile.getOriginalFilename();  
//			//获取上传图片
//			if(!filename.equals("")){
//				InputStream is = activityMultipartFile.getInputStream();  
//		        String newFileName = UUID.randomUUID().toString() + "." + filename.substring(filename.lastIndexOf(".") +1);
//		        
//		        //文件上传到CDN
//		        String endpoint = sysParamService.selectByKey("endpoint").getValue();
//		        String accessKeyId = sysParamService.selectByKey("accessKeyId").getValue();
//		        String accessKeySecret = sysParamService.selectByKey("accessKeySecret").getValue();
//		        String bucketName = sysParamService.selectByKey("bucketName").getValue();
//		        String dns = sysParamService.selectByKey("dns").getValue();
//			    OSSClientAPI oss = new OSSClientAPI(endpoint, accessKeyId, accessKeySecret, bucketName, dns);
//				String imgUrl = oss.putObject(newFileName, is, "activityFile/");
//				activity.setImgUrl(imgUrl);
//			}
//			activity.setCityCode(entity.getCityCode());//城市编码
//			//获得当前登录用户
//			SysUser user = getUser(request);
//			activity.setCreateId(user.getId());
//			activity.setCreateTime(new Date());
//			activity.setUrl(entity.getUrl());
//			activity.setCode(activityName);
//			activity.setStartTime(entity.getStartTime());
//			activity.setEndTime(DateTime.getEndTimeOfDay(entity.getEndTime()));
//			activity.setTitle(entity.getTitle());
//			//判断活动是否开启，如果已经开启，状态设置为1，如果未开启，状态设置为0
//			if ("1".equals(code)) {
//				activity.setStatus(1);
//			}else {
//				activity.setStatus(0);
//			}
//			//将面值，数量，期限，itemCode转成json字符串保存到remark中
//			CouponItemVO vo = new CouponItemVO();
//			vo.setBalance(1*100);
//			vo.setDeadline(10);
//			vo.setItemCode(activityName);
//			vo.setNumber(10);
//			String remark = JSON.toJSONString(vo);
//			activity.setRemark(remark);
//			activityService.insert(activity);
//			
//		}else {
//			/**
//			 *  修改活动
//			 */
//			Activity activity = activityService.selectByPrimaryKey(entity.getId());
////			String code2 = cityMapper.selectCodeByName(entity.getCityCode());
////			if(activity.getCityCode().equals(code2)){
////				activity.setCityCode(code2);
////			}else{
////				
////			}
//			activity.setCityCode(entity.getCityCode());
//			
//			if(StringUtils.isNotBlank(entity.getCityCode())){
//				List<Activity> activityList = activityService.selectActivityByCode(activity.getCityCode());
//				for (Activity activity2 : activityList) {
//					if(entity.getStartTime().after(activity2.getStartTime())&&entity.getEndTime().before(activity2.getEndTime())){
//						response.getWriter().print("activityFail");
//						return ;
//					}else{
//						continue;
//					}
//				}
//			}
//			//获得当前登录用户
//			SysUser user = getUser(request);
//			activity.setUpdateId(user.getId());
//			activity.setUpdateTime(new Date());
//			activity.setStartTime(entity.getStartTime());
//			activity.setEndTime(DateTime.getEndTimeOfDay(entity.getEndTime()));
//			//根据activityName获取数据字典中对应的code
//			activity.setCode(sysDicBean.getCode());
//			activity.setUrl(entity.getUrl());
//			activity.setTitle(entity.getTitle());
//			CouponItemVO vo = new CouponItemVO();
//			vo.setBalance(1*100);
//			vo.setDeadline(10);
//			vo.setItemCode(activityName);
//			vo.setNumber(10);
//			String remark = JSON.toJSONString(vo);
//			activity.setRemark(remark);
//			if ("1".equals(code)) {
//				activity.setStatus(1);
//			}else {
//				activity.setStatus(0);
//			}
//			String filename = activityMultipartFile.getOriginalFilename();  
//			//获取上传图片
//			if(!filename.equals("")){
//				InputStream is = activityMultipartFile.getInputStream();  
//		        String newFileName = UUID.randomUUID().toString() + "." + filename.substring(filename.lastIndexOf(".") +1);
//		        
//		        //文件上传到CDN
//		        String endpoint = sysParamService.selectByKey("endpoint").getValue();
//		        String accessKeyId = sysParamService.selectByKey("accessKeyId").getValue();
//		        String accessKeySecret = sysParamService.selectByKey("accessKeySecret").getValue();
//		        String bucketName = sysParamService.selectByKey("bucketName").getValue();
//		        String dns = sysParamService.selectByKey("dns").getValue();
//			    OSSClientAPI oss = new OSSClientAPI(endpoint, accessKeyId, accessKeySecret, bucketName, dns);
//				String imgUrl = oss.putObject(newFileName, is, "activityFile/");
//				activity.setImgUrl(imgUrl);
//			}
//			activityService.updateByPrimaryKey(activity);
//			
//		}
//		response.getWriter().print("success");
	
	/**
	 * 删除数据
	 * @param 主键ID
	 * @return
	 * @throws IOException 
	 */
	@SystemServiceLog(description = "活动删除")
	@RequestMapping("/deleteActivityById")
	public void delete(Integer id,HttpServletRequest request,HttpServletResponse response) throws IOException{
		log.info("删除");
//		if (id!=null && !id.equals("")) {
		if(null!=id ) {
			activityService.deleteByPrimaryKey(id);
		}
		response.getWriter().print("success");
	}
	
	@RequestMapping("/test")
	public String test(){
		return "activity/test";
	}
	
	@SystemServiceLog(description="邀请注册发送验证码")
	@ResponseBody
	@RequestMapping(value="/app_invitation_code" , method= {RequestMethod.GET, RequestMethod.POST})
	public Map<String, Object> getInvitationCode(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> map = new HashMap<String, Object>();
		String phone = request.getParameter("phone");
		String imgcode = request.getParameter("imgcode");
		String sImgCode = (String) request.getSession().getAttribute("InvitationCertCode");
		if(!StringUtils.equalsIgnoreCase(sImgCode, imgcode)) {
			map.put("status", "fail");
			map.put("msg", "图形验证码不正确,请重新输入");
			return map;	
		}
		int ints = (int) (Math.random()*9000+1000);
		SendSMS.send(phone, "", 1673, ints+"");
		Cookie cookie = new Cookie("VCODE", ints+"");
		response.addCookie(cookie);
		map.put("status", "success");
		map.put("msg", "success");
		return map;
	}
	
	@Autowired
	private MemberService memberService;
	
	@SystemServiceLog(description="邀请注册")
	@ResponseBody
	@RequestMapping(value="/appInvitationRegister", method= {RequestMethod.GET, RequestMethod.POST})
	public Map<String, Object> doInvitationRegister(HttpServletRequest request,HttpServletResponse response){
		response.setContentType("text/html;charset=utf-8");
		Map<String, Object> map = new HashMap<String, Object>();
		String phone = request.getParameter("phone");
		String vcode = request.getParameter("vcode");
		String imgcode = request.getParameter("imgcode");
		String invitationCode = request.getParameter("invitationCode");
		Member register = memberService.selectDetailByPhone(phone);
		if(register != null) {
			map.put("status", "fail");
			map.put("msg", "手机号码已经注册");
			return map;
		}
		String sImgCode = (String) request.getSession().getAttribute("InvitationCertCode");
		if(!StringUtils.equalsIgnoreCase(sImgCode, imgcode)) {
			map.put("status", "fail");
			map.put("msg", "图形验证码不正确,请重新输入");
			return map;	
		}
		//Member member = memberService.selectDetailByPhone(Long.parseLong(invitationCode, 16)+"");
		Cookie[] cookies = request.getCookies();
		String code = "";
		if(cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("VCODE")) {
					code = cookie.getValue();
					break;
				 }
			}
		}
		if(StringUtils.isBlank(code)) {
			map.put("status", "fail");
			map.put("msg", "短信验证码已过期，请重新获取");
			return map;
		}
		if(!StringUtils.equals(vcode, code)) {
			map.put("status", "fail");
			map.put("msg", "短信验证码错误，请重新输入");
			return map;
		}
		//调用接口完成注册
		//调用接口完成优惠
	    SysParam sysParam = sysParamService.selectByKey("http_url");
        String url = "";//"http://192.168.1.83:8889/services/i/e/";
        if (sysParam != null) url = sysParam.getValue();
		String json = "{}";
		StringBuffer sb = new StringBuffer("{") ;
		sb.append("\"cId\":\"platForm\",\"memberId\":\"\",") ;
		sb.append("\"method\":\"bindInvitationCode\",") ;
		sb.append("\"param\":{'invitationCode':'"+invitationCode+"','phone':'"+phone+"'},") ;
		sb.append("\"phone\":\"\",\"type\":\"platForm\",\"version\":\"1\"") ;
		sb.append("}") ;
		if(url.indexOf("https") == 0){ //https接口
			json = HttpsClientUtil.get(url, sb.toString()) ;
		}else{
			json = HttpUtils.commonSendUrl(url, sb.toString()) ;
		}
		JSONObject jsonObject = JSONObject.fromObject(json) ;
		String rcode = jsonObject.getString("code") ;
		if(rcode.equals("00")){ 
			map.put("status", "success");
			map.put("msg", "success");
		}else{
			map.put("status", "fail");
			map.put("msg", "网络繁忙，请稍后再试");
		}
		return map;
	}
}
