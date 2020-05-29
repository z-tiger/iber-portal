package com.iber.portal.controller.version;

import java.io.InputStream;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.iber.portal.common.*;
import com.iber.portal.service.tbox.TboxService;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.iber.portal.advices.SystemServiceLog;
//import com.iber.portal.common.FavFTPUtil;
import com.iber.portal.controller.MainController;
import com.iber.portal.controller.monitorCenter.ServerPushOrder;
import com.iber.portal.getui.PushClient;
import com.iber.portal.getui.PushCommonBean;
import com.iber.portal.model.car.Car;
import com.iber.portal.model.charging.EquipmentInfo;
import com.iber.portal.model.sys.SysParam;
import com.iber.portal.model.sys.SysUser;
import com.iber.portal.model.versions.VersionsBox;
import com.iber.portal.model.versions.VersionsCategory;
import com.iber.portal.model.versions.VersionsPile;
import com.iber.portal.model.versions.VersionsRearview;
import com.iber.portal.model.versions.VersionsUpgradeLog;
import com.iber.portal.service.car.CarService;
import com.iber.portal.service.charging.EquipmentInfoService;
import com.iber.portal.service.sys.SysParamService;
import com.iber.portal.service.version.VersionsBoxService;
import com.iber.portal.service.version.VersionsCategoryService;
import com.iber.portal.service.version.VersionsPileService;
import com.iber.portal.service.version.VersionsRearviewService;
import com.iber.portal.service.version.VersionsUpgradeLogService;
import com.iber.portal.vo.car.CarUpgradeVo;
import com.iber.portal.vo.car.CarVersionVo;
import com.iber.portal.vo.pile.EquipmentInfoVo;


@Controller
public class VersionController extends MainController{

	@Autowired
	private CarService carService;
	
	@Autowired
	private VersionsBoxService versionsBoxService ;

	@Autowired
	private VersionsRearviewService versionsRearviewService ;
	
	@Autowired
	private VersionsCategoryService versionsCategoryService ;
	
	@Autowired
	private VersionsUpgradeLogService versionsUpgradeLogService ;
	
	
	@Autowired
	private SysParamService sysParamService ;
	
	@Autowired
	private VersionsPileService versionsPileService;
	
	@Autowired
	private EquipmentInfoService equipmentInfoService;

	@Autowired
	private TboxService tboxService;
	
	private static final int SUCCESS_STATUC = 0;
	private static final int FAIL_STATUC = 1;
	
	/**
	 * 升级管理
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SystemServiceLog(description="升级管理")
	@RequestMapping(value = "/upgrade", method = { RequestMethod.GET })
	public String upgrade(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return "version/carUpgrade";		
	}
	/**
	 * 版本更新日志
	 * @param cityCode
	 * @param lpn
	 * @param page
	 * @param rows
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SystemServiceLog(description="版本更新日志")
	@RequestMapping(value = "/upgrade_list", method = { RequestMethod.GET , RequestMethod.POST })
	public String upgrade_list(String cityCode ,String lpn ,int page, int rows,HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		Map<String,Integer> pageInfo = Data2Jsp.getFristAndPageSize(page, rows);	
		SysUser user = (SysUser) request.getSession().getAttribute("user");

		if(!user.getCityCode().equals("00")){
			cityCode = user.getCityCode() ;
		}
		if(cityCode.equals("00")){
			cityCode = null ;
		}
		Map<String,Object> param = new HashMap<String, Object>() ;
		param.put("cityCode", cityCode) ;
		param.put("lpn", lpn) ;
		param.put("offset", pageInfo.get("first_page")) ;
		param.put("rows", pageInfo.get("page_size")) ;
		param.put("status", request.getParameter("status")) ;
		param.put("tboxVersion", request.getParameter("tboxVersion")) ;
		param.put("brandName", request.getParameter("brandName")) ;
		Pager<CarVersionVo> pager= carService.queryCarVersion(param) ;
		response.getWriter().print(Data2Jsp.Json2Jsp(pager));
		return null;		
	}
	/**
	 * 宝盒升级
	 * @param boxIds
	 * @param lpns
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SystemServiceLog(description="宝盒升级")
	@RequestMapping(value = "/box_upgrade", method = { RequestMethod.GET , RequestMethod.POST })
	public String box_upgrade(String[] boxIds ,String[] lpns ,Integer[] tboxVersions, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		if (ArrayUtils.isEmpty(boxIds)){
		    return null;
        }
		for(int i = 0 ; i < lpns.length ; i ++) {
			/** 推送宝盒升级指令给车载 */
			final Integer tboxVersion = tboxVersions[i];
			SysUser user = (SysUser) request.getSession().getAttribute("user");
			if (tboxVersion != null && tboxVersion == 3){
				// 三代升级
				saveTbox3VersionsUpgradeLog(lpns[i],Integer.parseInt(boxIds[i]),user) ;
			}else {
				saveVersionsUpgradeLog(lpns[i],Integer.parseInt(boxIds[i]),user,"box") ;
			}
		}
		response.getWriter().print("succ");
		return null;		
	}
	/**
	 * 后视镜升级
	 * @param rearviewIds
	 * @param lpns
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SystemServiceLog(description="后视镜升级")
	@RequestMapping(value = "/rearview_upgrade", method = { RequestMethod.GET , RequestMethod.POST })
	public String rearview_upgrade(String[] rearviewIds ,String[] lpns ,HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		for(int i = 0 ; i < lpns.length ; i ++) {
			/** 推送宝盒升级指令给车载 */
			SysUser user = (SysUser) request.getSession().getAttribute("user");
			saveVersionsUpgradeLog(lpns[i],Integer.parseInt(rearviewIds[i]),user,"rearview") ;
		}
		response.getWriter().print("succ");
		return null;		
	}
	/**
	 * 全部宝盒升级
	 * @param rearviewIds
	 * @param lpns
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SystemServiceLog(description="全部宝盒升级")	
	@RequestMapping(value = "/all_box_upgrade", method = { RequestMethod.GET , RequestMethod.POST })
	public String all_box_upgrade(String[] rearviewIds ,String[] lpns ,HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		SysUser user = (SysUser) request.getSession().getAttribute("user");
		Map<String,Object> map = new HashMap<String, Object>() ;
		if(!user.getCityCode().equals("00")){
			map.put("cityCode", user.getCityCode()) ;
		}else{
			map.put("cityCode", null) ;
		}
		List<CarUpgradeVo> carUpgradeVoList = carService.queryCarBoxUpgradeVo(map);
		for(CarUpgradeVo carUpgradeVo : carUpgradeVoList) {
			/** 推送宝盒升级指令给车载 */
			saveVersionsUpgradeLog(carUpgradeVo.getLpn(),carUpgradeVo.getId(),user,"box") ;
		}
		response.getWriter().print("succ");
		return null;		
	}
	/**
	 * 全部后视镜升级
	 * @param rearviewIds
	 * @param lpns
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SystemServiceLog(description="全部后视镜升级")		
	@RequestMapping(value = "/all_rearview_upgrade", method = { RequestMethod.GET , RequestMethod.POST })
	public String all_rearview_upgrade(String[] rearviewIds ,String[] lpns ,HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		SysUser user = (SysUser) request.getSession().getAttribute("user");
		Map<String,Object> map = new HashMap<String, Object>() ;
		if(!user.getCityCode().equals("00")){
			map.put("cityCode", user.getCityCode()) ;
		}else{
			map.put("cityCode", null) ;
		}
		List<CarUpgradeVo> carUpgradeVoList = carService.queryCarRearviewUpgradeVo(map);
		for(CarUpgradeVo carUpgradeVo : carUpgradeVoList) {
			/** 推送后视镜升级指令给车载 */
			saveVersionsUpgradeLog(carUpgradeVo.getLpn(),carUpgradeVo.getId(),user,"rearview") ;
		}
		response.getWriter().print("succ");
		return null;		
	}
	
	private void saveVersionsUpgradeLog(String lpn , Integer versionId,SysUser user ,String upgradetype){
		Car car = carService.selectByLpn(lpn) ;
		VersionsUpgradeLog log = new VersionsUpgradeLog() ;
		List<String> cidList = PushClient.queryClientId(lpn);
		PushCommonBean push = null ;
		String upgradeVersionNo = "" ;
		String currentVersionNo = "" ;
		if(upgradetype.equals("box")){
			VersionsBox versionsBox = versionsBoxService.selectByPrimaryKey(versionId) ;
			upgradeVersionNo = versionsBox.getVersionNo() ;
			currentVersionNo = car.getBoxVersionCode() ;
			push = new PushCommonBean(ServerPushOrder.PLATFORM_REQUEST_BOX_UPGRADE,"1","",versionsBox) ;
		}else{
			VersionsRearview versionsRearview = versionsRearviewService.selectByPrimaryKey(versionId) ;
			upgradeVersionNo = versionsRearview.getVersionNo() ;
			currentVersionNo = car.getVersionName() ;
			push = new PushCommonBean(ServerPushOrder.PLATFORM_REQUEST_REARVIEW_UPGRADE,"1","",versionsRearview) ;
		}
		if(cidList.size() > 0) {
			for (String lpnCid : cidList) {
				String result = PushClient.push(lpnCid,push);
				if(result.equals("ok")){
					/**保存下发指令成功信息*/
					log.setStatus(0);
					log.setRemark("指令下发成功");
				}else{
					/**保存下发指令失败信息*/
					log.setStatus(1);
					log.setRemark(result);
				}
			}
		}else{
			log.setStatus(1);
			log.setRemark("车载未连接服务器，无法下发指令");
		}
		log.setLpn(lpn);
		log.setUpgradetype(upgradetype);
		log.setCurrentVersionNo(currentVersionNo);
		log.setUpgradeVersionNo(upgradeVersionNo);
		log.setCreateId(user.getId());
		log.setCreateTime(new Date());
		versionsUpgradeLogService.insertSelective(log) ;
	}

	/**
	 * 三代tbox升级
	 * @param lpn
	 * @param versionId
	 * @param user
	 */
	private void saveTbox3VersionsUpgradeLog(String lpn,Integer versionId,SysUser user){
		Car car = carService.selectByLpn(lpn) ;
		VersionsUpgradeLog log = new VersionsUpgradeLog() ;
		VersionsBox versionsBox = versionsBoxService.selectByPrimaryKey(versionId) ;
		final String upgradeVersionNo = versionsBox.getVersionNo() ;
		final String currentVersionNo = car.getBoxVersionCode() ;
		final String versionUrl = versionsBox.getVersionUrl();
		final Map<String, Object> map = tboxService.boxUpgrade(lpn, versionUrl);
		if (CommonUtil.isSuccess(map)){
			log.setStatus(0);
			log.setRemark("指令下发成功");
		}else{
			/**保存下发指令失败信息*/
			log.setStatus(1);
			log.setRemark(CommonUtil.getFailMsg(map));
		}
		log.setLpn(lpn);
		log.setUpgradetype("tbox3");
		log.setCurrentVersionNo(currentVersionNo);
		log.setUpgradeVersionNo(upgradeVersionNo);
		log.setCreateId(user.getId());
		log.setCreateTime(new Date());
		versionsUpgradeLogService.insertSelective(log) ;
	}

	/**
	 * 版本更新日志
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SystemServiceLog(description="版本更新日志")		
	@RequestMapping(value = "/version_upgrade_list", method = { RequestMethod.GET , RequestMethod.POST })
	public String version_upgrade_list(String lpn ,String upgradeType ,int page, int rows,HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		Map<String,Integer> pageInfo = Data2Jsp.getFristAndPageSize(page, rows);	
		Pager<VersionsUpgradeLog> pager= versionsUpgradeLogService.queryCarVersionsUpgradeLog("", lpn,pageInfo.get("first_page"), pageInfo.get("page_size")) ;
		response.getWriter().print(Data2Jsp.Json2Jsp(pager));
		return null;		
	}
	
	/**
	 * @describe 查询版本类型
	 * 
	 * @auther yangguiwu
	 * @date 2016年04月18日
	 * @throws Exception
	 */	
	@SystemServiceLog(description="查询版本类型")	
	@RequestMapping(value = "/queryCategory", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public List<VersionsCategory> queryCategory(String id,HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
		response.setContentType("text/html;charset=utf-8");	
		List<VersionsCategory> obj =versionsCategoryService.selectByPrimaryCategory(id) ;
		return obj;
	
	}
	

	
	/**
	 * @describe 版本类型管理页面
	 * 
	 * @auther yangguiwu
	 * @date 2016年04月14日
	 * @throws Exception
	 */
	@SystemServiceLog(description="版本类型管理页面")	
	@RequestMapping(value = "/versionsCategory", method = { RequestMethod.GET })
	public String versionsCategory(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return "version/versionsCategory";		
	}
	/**
	 * 版本类型管理
	 * @param page
	 * @param rows
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SystemServiceLog(description="版本类型管理")	
    @RequestMapping(value = "/versions_category_list", method = { RequestMethod.GET, RequestMethod.POST })
	public String versionsCategoryList(int page, int rows, HttpServletRequest request, HttpServletResponse response) throws Exception{
        response.setContentType("text/html;charset=utf-8");
        HashMap<String, Object> record = new  HashMap<String, Object>();
        
			Map<String, Integer> pageInfo = Data2Jsp.getFristAndPageSize(page, rows);
			record.put("page", pageInfo.get("first_page"));
			record.put("size", pageInfo.get("page_size"));
			List<VersionsCategory> data = versionsCategoryService.selectByPrimaryKey(record);
			JSONObject obj = new JSONObject();
			obj.put("total", versionsCategoryService.selectByPrimaryKeyRecords(record));
			obj.put("rows", data);
			response.getWriter().print(obj.toString());
			return null;
	}		
	
	/**
	 * @describe 删除种类类型记录
	 * 
	 * @auther yangguiwu
	 * @date 2016年04月15日
	 * @throws Exception
	 */
	@SystemServiceLog(description="删除种类类型记录")	
	@RequestMapping(value = "/versions_category_del", method = { RequestMethod.GET , RequestMethod.POST })
	public String versionsCategoryDel(String id, HttpServletRequest request, HttpServletResponse response)
			throws Exception {		
		response.setContentType("text/html;charset=utf-8");

		if (id!=null && !id.equals("")) {
			versionsCategoryService.deleteByPrimaryKey(Integer.parseInt(id));
		}
		response.getWriter().print("success");
		return null;
	}	
	
	/**
	 * @describe 添加种类类型记录
	 * 
	 */
	@SystemServiceLog(description="添加种类类型记录")	
	@RequestMapping(value = "/add_versions_category", method = { RequestMethod.GET , RequestMethod.POST })
	public String addVersionsCategory(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("text/html;charset=utf-8");
		SysUser sysUser = (SysUser) request.getSession().getAttribute("user");	
		
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		String code = request.getParameter("code");
		String remark = request.getParameter("remark");
		
		if (id!=null && !id.equals("")) {
			VersionsCategory currObj = versionsCategoryService.selectByPrimaryId(Integer.parseInt(id));
			if (currObj!=null) {
				currObj.setName(name);
				currObj.setCode(code);
				currObj.setUpdateId(sysUser.getId());
				currObj.setUpdateTime(new Date());
				currObj.setRemark(remark);
				versionsCategoryService.updateByPrimaryKeySelective(currObj);
			}
			
		}else{	
			VersionsCategory obj = new VersionsCategory();
			obj.setName(name);
			obj.setCode(code);
			obj.setCreateId(sysUser.getId());
			obj.setCreateTime(new Date());
			obj.setRemark(remark);
			versionsCategoryService.insertSelective(obj);
		}
		
		response.getWriter().print("success");
		return null;	
	}	
    
    
    
    
    /**
	 * @describe 后视镜版本管理页面
	 * 
	 * @auther yangguiwu
	 * @date 2016年04月14日
	 * @throws Exception
	 */
	@SystemServiceLog(description="后视镜版本管理页面")
	@RequestMapping(value = "/versionsRearview", method = { RequestMethod.GET })
	public String versionsRearview(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return "version/versionsRearview";		
	}
	/**
	 * 后视镜版本管理
	 * @param page
	 * @param rows
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SystemServiceLog(description="后视镜版本管理")
    @RequestMapping(value = "/versions_rearview_list", method = { RequestMethod.GET, RequestMethod.POST })
	public String versionsRearviewList(int page, int rows, HttpServletRequest request, HttpServletResponse response) throws Exception{
        response.setContentType("text/html;charset=utf-8");
        HashMap<String, Object> record = new  HashMap<String, Object>();
        
        if(!StringUtils.isBlank(request.getParameter("categoryCode"))){
        	record.put("categoryCode", request.getParameter("categoryCode"));
        }
    
			Map<String, Integer> pageInfo = Data2Jsp.getFristAndPageSize(page, rows);
			record.put("page", pageInfo.get("first_page"));
			record.put("size", pageInfo.get("page_size"));
			List<VersionsRearview> data = versionsRearviewService.selectByPrimaryInfo(record);
			JSONObject obj = new JSONObject();
			obj.put("total", versionsRearviewService.selectByPrimaryKeyRecords(record));
			obj.put("rows", data);
			response.getWriter().print(obj.toString());
			return null;
	}		
	
	/**
	 * @describe 删除后视镜版本记录
	 * 
	 * @auther yangguiwu
	 * @date 2016年04月15日
	 * @throws Exception
	 */
	@SystemServiceLog(description="删除后视镜版本记录")
	@RequestMapping(value = "/versions_rearview_del", method = { RequestMethod.GET , RequestMethod.POST })
	public String versionsRearviewDel(String id, HttpServletRequest request, HttpServletResponse response)
			throws Exception {		
		response.setContentType("text/html;charset=utf-8");

		if (id!=null && !id.equals("")) {
			versionsRearviewService.deleteByPrimaryKey(Integer.parseInt(id));
		}
		response.getWriter().print("success");
		return null;
	}	
	
	/**
	 * @describe 添加后视镜版本记录
	 * 
	 */
	@SystemServiceLog(description="添加后视镜版本记录")
	@RequestMapping(value = "/add_versions_rearview", method = { RequestMethod.GET , RequestMethod.POST })
	public String addVersionsRearview(HttpServletRequest request, HttpServletResponse response,MultipartFile rearviewFile)
			throws Exception {
		response.setContentType("text/html;charset=utf-8");
		SysUser sysUser = (SysUser) request.getSession().getAttribute("user");	
		String id = request.getParameter("id");
		String code = request.getParameter("categoryCode");
		String versionName = request.getParameter("versionName");
		String versionNo = request.getParameter("versionNo");
		String versionRecord = request.getParameter("versionRecord");
		String remark = request.getParameter("remark");
		String isIncrement = request.getParameter("isIncrement");
	
		if (id!=null && !id.equals("")) {
			VersionsRearview currObj = versionsRearviewService.selectByPrimaryId(Integer.parseInt(id));
			if (currObj!=null) {
				currObj.setCategoryCode(code);
				currObj.setVersionName(versionName);
				if(rearviewFile != null) {
					String versionUrl = uploadFileCDN(rearviewFile) ;
					currObj.setVersionUrl(versionUrl);
				}
				currObj.setVersionNo(versionNo);
				currObj.setVersionRecord(Integer.parseInt(versionRecord));
				currObj.setUpdateId(sysUser.getId());
				currObj.setUpdateTime(new Date());
				currObj.setRemark(remark);
				currObj.setIsIncrement(isIncrement);
				versionsRearviewService.updateByPrimaryKeySelective(currObj);
			}
		}else{	
			String versionUrl = uploadFileCDN(rearviewFile) ;
			VersionsRearview obj = new VersionsRearview();
			obj.setCategoryCode(code);
			obj.setVersionName(versionName);
			obj.setVersionUrl(versionUrl);
			obj.setVersionNo(versionNo);
			obj.setVersionRecord(Integer.parseInt(versionRecord));
			obj.setCreateId(sysUser.getId());
			obj.setCreateTime(new Date());
			obj.setRemark(remark);
			obj.setIsIncrement(isIncrement);
			versionsRearviewService.insertSelective(obj);
		}
		
		response.getWriter().print("success");
		return null;	
	}	

	private String uploadFileCDN(MultipartFile file) throws Exception{
        String filename = file.getOriginalFilename();  
        InputStream is = file.getInputStream();  
        String newFileName = UUID.randomUUID().toString() + "." + filename.substring(filename.lastIndexOf(".") +1);
      //文件上传到CDN
        String endpoint = sysParamService.selectByKey("endpoint").getValue();
        String accessKeyId = sysParamService.selectByKey("accessKeyId").getValue();
        String accessKeySecret = sysParamService.selectByKey("accessKeySecret").getValue();
        String bucketName = sysParamService.selectByKey("bucketName").getValue();
        String dns = sysParamService.selectByKey("dns").getValue();
	    OSSClientAPI oss = new OSSClientAPI(endpoint, accessKeyId, accessKeySecret, bucketName, dns);
		String fileURL = oss.putObject(newFileName, is, "upgrade/");
	    return fileURL;
	}

	/**
	 * @describe 宝盒版本管理页面
	 * 
	 * @auther yangguiwu
	 * @date 2016年04月14日
	 * @throws Exception
	 */
	@SystemServiceLog(description="宝盒版本管理页面")
	@RequestMapping(value = "/versionsBox", method = { RequestMethod.GET })
	public String carApplyDetail(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return "version/versionsBox";		
	}
	/**
	 * 宝盒版本管理
	 * @param page
	 * @param rows
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SystemServiceLog(description="宝盒版本管理")
    @RequestMapping(value = "/versions_box_list", method = { RequestMethod.GET, RequestMethod.POST })
	public String versionsBoxList(int page, int rows, HttpServletRequest request, HttpServletResponse response) throws Exception{
        response.setContentType("text/html;charset=utf-8");
        HashMap<String, Object> record = new  HashMap<String, Object>();
        
        if(!StringUtils.isBlank(request.getParameter("categoryCode"))){
        	record.put("categoryCode", request.getParameter("categoryCode"));
        }
    
			Map<String, Integer> pageInfo = Data2Jsp.getFristAndPageSize(page, rows);
			record.put("page", pageInfo.get("first_page"));
			record.put("size", pageInfo.get("page_size"));
			List<VersionsBox> data = versionsBoxService.selectByPrimaryInfo(record);
			JSONObject obj = new JSONObject();
			obj.put("total", versionsBoxService.selectByPrimaryKeyRecords(record));
			obj.put("rows", data);
			response.getWriter().print(obj.toString());
			return null;
	}		
	
	/**
	 * @describe 删除宝盒版本记录
	 * 
	 * @auther yangguiwu
	 * @date 2016年04月15日
	 * @throws Exception
	 */
	@SystemServiceLog(description="删除宝盒版本记录")
	@RequestMapping(value = "/versions_box_del", method = { RequestMethod.GET , RequestMethod.POST })
	public String versionsDoxDel(String id, HttpServletRequest request, HttpServletResponse response)
			throws Exception {		
		response.setContentType("text/html;charset=utf-8");

		if (id!=null && !id.equals("")) {
			versionsBoxService.deleteByPrimaryKey(Integer.parseInt(id));
		}
		response.getWriter().print("success");
		return null;
	}	
	
	/**
	 * @describe 添加宝盒版本记录
	 * 
	 */
	@SystemServiceLog(description="添加宝盒版本记录")
	@RequestMapping(value = "/add_versions_box", method = { RequestMethod.GET , RequestMethod.POST })
	public String addVersionsBox(HttpServletRequest request, HttpServletResponse response,MultipartFile boxFile)
			throws Exception {
		response.setContentType("text/html;charset=utf-8");
		SysUser sysUser = (SysUser) request.getSession().getAttribute("user");	
		
		String id = request.getParameter("id");
		String code = request.getParameter("categoryCode");
		String versionName = request.getParameter("versionName");
		String versionNo = request.getParameter("versionNo");
		String versionRecord = request.getParameter("versionRecord");
		String remark = request.getParameter("remark");
		String isIncrement = request.getParameter("isIncrement");
	
		if (id!=null && !id.equals("")) {
			VersionsBox currObj = versionsBoxService.selectByPrimaryId(Integer.parseInt(id));
			if (currObj!=null) {
				currObj.setCategoryCode(code);
				currObj.setVersionName(versionName);
				if(boxFile != null) {
					String versionUrl = uploadFileCDN(boxFile) ;
					currObj.setVersionUrl(versionUrl);
				}
				currObj.setVersionNo(versionNo);
				currObj.setVersionRecord(Integer.parseInt(versionRecord));
				currObj.setUpdateId(sysUser.getId());
				currObj.setUpdateTime(new Date());
				currObj.setRemark(remark);
				currObj.setIsIncrement(isIncrement);
				versionsBoxService.updateByPrimaryKeySelective(currObj);
			}
			
		}else{	
			String versionUrl = uploadFileCDN(boxFile) ;
			VersionsBox obj = new VersionsBox();
			obj.setCategoryCode(code);
			obj.setVersionName(versionName);
			obj.setVersionUrl(versionUrl);
			obj.setVersionNo(versionNo);
			obj.setVersionRecord(Integer.parseInt(versionRecord));
			obj.setCreateId(sysUser.getId());
			obj.setCreateTime(new Date());
			obj.setRemark(remark);
			obj.setIsIncrement(isIncrement);
			versionsBoxService.insertSelective(obj);
		}
		
		response.getWriter().print("success");
		return null;	
	}
	
	/**
	 * @describe 充电桩版本管理页面
	 * 
	 * @auther zixiaobang
	 * @date 2017年03月22日
	 * @throws Exception
	 */
	@SystemServiceLog(description="充电桩版本管理页面")
	@RequestMapping(value = "versionsPile", method = { RequestMethod.GET })
	public String versionsPileDetail(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return "version/versionsPile";		
	}
	
	/**
	 * 充电桩版本管理
	 * @author zixiaobang
	 * @date 2017.03.22
	 * @throws Exception
	 */
	@SystemServiceLog(description="充电桩版本管理")
	@RequestMapping(value="versions_pile_list",method={ RequestMethod.GET , RequestMethod.POST})
	public String versionsPileList(int page, int rows,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("text/html;charset=utf-8");
		HashMap<String, Object> record = new HashMap<String, Object>();

		if (!StringUtils.isBlank(request.getParameter("categoryCode"))) {
			record.put("categoryCode", request.getParameter("categoryCode"));
		}

		Map<String, Integer> pageInfo = Data2Jsp.getFristAndPageSize(page, rows);
		record.put("page", pageInfo.get("first_page"));
		record.put("size", pageInfo.get("page_size"));
		List<VersionsPile> data = versionsPileService.selectByPrimaryInfo(record);
		JSONObject obj = new JSONObject();
		obj.put("total", versionsPileService.selectByPrimaryKeyRecords(record));
		obj.put("rows", data);
		response.getWriter().print(obj.toString());
		return null;
	}
	
	/**
	 * 添加或修改充电桩版本记录
	 * @author zixiaobang
	 * @date 2017.03.22
	 * @throws Exception
	 */
	@SystemServiceLog(description = "添加充电桩版本记录")
	@RequestMapping(value="add_versions_pile", method = { RequestMethod.GET , RequestMethod.POST})
	public String addVersionsPile(HttpServletRequest request, HttpServletResponse response, MultipartFile boxFile) throws Exception{
		response.setContentType("text/html;charset=utf-8");
		SysUser sysUser = (SysUser) request.getSession().getAttribute("user");	
		String id = request.getParameter("id");
		String code = request.getParameter("categoryCode");
		String versionName = request.getParameter("versionName");
		String versionNo = request.getParameter("versionNo");
		String versionRecord = request.getParameter("versionRecord");
		String remark = request.getParameter("remark");
		String isIncrement = request.getParameter("isIncrement");
		if(StringUtils.isNotBlank(id)){
			//更新操作
			VersionsPile currObj = versionsPileService.selectByPrimaryId(Integer.parseInt(id));
			if (currObj!=null) {
				currObj.setCategoryCode(code);
				currObj.setVersionName(versionName);
				if(!boxFile.isEmpty()) {
					String versionUrl = uploadFileFtp(boxFile) ;
					currObj.setVersionUrl(versionUrl);
				}
				currObj.setVersionNo(versionNo);
				currObj.setVersionRecord(Integer.parseInt(versionRecord));
				currObj.setUpdateId(sysUser.getId());
				currObj.setUpdateTime(new Date());
				currObj.setRemark(remark);
				currObj.setIsIncrement(isIncrement);
				versionsPileService.updateByPrimaryKeySelective(currObj);
			}
		}else{
			//添加操作
			String versionUrl = uploadFileFtp(boxFile) ;
			VersionsPile obj = new VersionsPile();
			obj.setCategoryCode(code);
			obj.setVersionName(versionName);
			obj.setVersionUrl(versionUrl);
			obj.setVersionNo(versionNo);
			obj.setVersionRecord(Integer.parseInt(versionRecord));
			obj.setCreateId(sysUser.getId());
			obj.setCreateTime(new Date());
			obj.setRemark(remark);
			obj.setIsIncrement(isIncrement);
			versionsPileService.insertSelective(obj);
		}
		response.getWriter().print("success");
		return null;
	}
	
	/**
	 * 上传文件到ftp服务器
	 * @author zixiaobang
	 * @date 2017.03.22
	 * @throws Exception
	 * @param file
	 * @return
	 * 
	 */
	private String uploadFileFtp(MultipartFile file) throws Exception {
		InputStream inputStream = file.getInputStream();
		String filename = file.getOriginalFilename();  
		String newFileName = UUID.randomUUID().toString() + "." + filename.substring(filename.lastIndexOf(".") +1);
		String hostname = sysParamService.selectByKey("ftp_host").getValue();
		if(StringUtils.startsWith(hostname, "ftp://")){
			hostname = StringUtils.replace(hostname, "ftp://", "");
		}
		String username = sysParamService.selectByKey("ftp_username").getValue();
		String password = sysParamService.selectByKey("ftp_password").getValue();
		String pathname = sysParamService.selectByKey("ftp_pathname").getValue();
		String fileUrl = "";//FavFTPUtil.uploadFile(hostname, username, password, pathname, newFileName, inputStream);
		return fileUrl;
	}
	
	/**
	 * 删除充电桩版本
	 * @author zixiaobang
	 * @date 2017.03.22
	 * @throws Exception
	 */
	@SystemServiceLog(description="删除充电桩版本")
	@RequestMapping(value="versions_pile_del", method={RequestMethod.GET , RequestMethod.POST})
	public String versionsPileDel(HttpServletRequest request, HttpServletResponse response) throws Exception{
		response.setContentType("text/html;charset=utf-8");
		String id = request.getParameter("id");
		if (StringUtils.isNotBlank(id)) {
			versionsPileService.deleteByPrimaryKey(Integer.parseInt(id));
		}
		response.getWriter().print("success");
		return null;
	}
	
	
	@SystemServiceLog(description="全部电桩升级")
	@RequestMapping(value="all_pile_upgrade", method={RequestMethod.GET , RequestMethod.POST})
	public String allPileUpgrade(HttpServletRequest request, HttpServletResponse response)throws Exception{
		response.setContentType("text/html;charset=utf-8");
		SysUser user = (SysUser) request.getSession().getAttribute("user");
		Map<String,Object> map = new HashMap<String, Object>() ;
		if(!"00".equals(user.getCityCode())){
			map.put("cityCode", user.getCityCode()) ;
		}else{
			map.put("cityCode", null) ;
		}
		List<EquipmentInfoVo> equipmentInfos = equipmentInfoService.queryAllUpgrade(map);
		//各种类型的最新版本号
		List<VersionsPile> VersionsPilelist = versionsPileService.selectNewestVersion();
		/**存放充电桩最新版本 1：直流设备 2：交流设备 3：交直流一体设备*/
		VersionsPile[] versions = new VersionsPile[3];
		String[] equipmentCodes = new String[3]; 
		for (VersionsPile versionsPile : VersionsPilelist) {
			if("1".equals(versionsPile.getCategoryCode())){
				versions[0]=versionsPile;
				continue;
			}
			if("2".equals(versionsPile.getCategoryCode())){
				versions[1]=versionsPile;
				continue;
			}
			if("3".equals(versionsPile.getCategoryCode())){
				versions[2]=versionsPile;
				continue;
			}
		}
		//充电桩设备
		for (EquipmentInfoVo equipmentInfo : equipmentInfos) {
				if("1".equals(equipmentInfo.getEquipmentType().toString())){
					//是否有上传对应的最新版本
					if (versions[0] == null) continue;
					//判断是否需要升级
					if(equipmentInfo.getVersionRecord() == null||Integer.parseInt(equipmentInfo.getVersionRecord()) < versions[0].getVersionRecord()){
						if(equipmentCodes[0] != null) 
							equipmentCodes[0] = equipmentCodes[0]+","+equipmentInfo.getEquipmentCode();
						else
							equipmentCodes[0] = equipmentInfo.getEquipmentCode();
						continue;
					}
				}
				
				if("2".equals(equipmentInfo.getEquipmentType().toString())){
					//是否有上传对应的最新版本
					if (versions[1] == null) continue;
					//判断是否需要升级
					if(equipmentInfo.getVersionRecord() == null||Integer.parseInt(equipmentInfo.getVersionRecord()) < versions[1].getVersionRecord()){
						if(equipmentCodes[1] != null) 
							equipmentCodes[1] = equipmentCodes[1]+","+equipmentInfo.getEquipmentCode();
						else
							equipmentCodes[1] = equipmentInfo.getEquipmentCode();
						continue;
					}
				}
				
				if("3".equals(equipmentInfo.getEquipmentType().toString())){
					//是否有上传对应的最新版本
					if (versions[2] == null) continue;
					//判断是否需要升级
					if(equipmentInfo.getVersionRecord() == null||Integer.parseInt(equipmentInfo.getVersionRecord()) < versions[2].getVersionRecord()){
						if(equipmentCodes[2] != null) 
							equipmentCodes[2] = equipmentCodes[2]+","+equipmentInfo.getEquipmentCode();
						else
							equipmentCodes[2] = equipmentInfo.getEquipmentCode();
						continue;
					}
				}
		}
		boolean flag = savePileVersionsUpgradeLog(equipmentCodes,versions,user);
		if(!flag){
			response.getWriter().print("fail");
			return null;
		}
		response.getWriter().print("succ");
		return null;
	}
	
	/**
	 * 全部充电桩下发升级指令
	 * @param equipmentCodes
	 * @param versions
	 * @param user
	 * @return
	 * @throws Exception
	 */
	private boolean savePileVersionsUpgradeLog(String[] equipmentCodes,
			VersionsPile[] versions, SysUser user) throws Exception{
		boolean flag = false;
		// 下发指令
		String json = "{}";
		String code = "";
		String ftpHost = sysParamService.selectByKey("ftp_host").getValue();
		String ftpUsername = sysParamService.selectByKey("ftp_username")
				.getValue();
		String ftpPassword = sysParamService.selectByKey("ftp_password")
				.getValue();
		String ftpPathname = sysParamService.selectByKey("ftp_pathname")
				.getValue();
		SysParam sysParam = sysParamService.selectByKey("http_url_c");
		int tem = 0;//记录不需要更新的个数
		for (int index = 0 ; index < versions.length;index++) {
			VersionsPile pile = versions[index];
			if(StringUtils.isBlank(equipmentCodes[index])|| (pile== null)){tem++;continue;}
			
			String[] paths = pile.getVersionUrl().split("/");
			String filepath =ftpPathname +"/"+ paths[paths.length-1];
			StringBuffer sb = new StringBuffer("{");
			sb.append("\"cId\":\"platForm\",\"memberId\":\"\",\"method\":\"sendRemoteUpgradePileSoftware\",");
			sb.append("\"param\":\"{'equipmentCodeList':'" + equipmentCodes[index]
					+ "','ip':'" + ftpHost + "','path':'" + URLEncoder.encode(filepath,"utf-8") 
					+ "','ftpAccount':'" + ftpUsername + "','ftpPassword':'" + ftpPassword + "','version':'"
					+ pile.getVersionNo() + "'}\",");
			sb.append("\"phone\":\"" + user.getPhone()+ "\",\"type\":\"platForm\",\"version\":\"1\"");
			sb.append("}");
			if(sysParam.getValue().indexOf("https") == 0){ //https接口
				json = HttpsClientUtil.get(sysParam.getValue(), sb.toString()) ;
			}else{
				json = HttpUtils.commonSendUrl(sysParam.getValue(), sb.toString()) ;
			}
			
			JSONObject jsonObject = JSONObject.fromObject(json);
			if (jsonObject.containsKey("code")) {
				code = jsonObject.getString("code");
			}
			String result =  jsonObject.getString("result");
			String[] pileCodes = equipmentCodes[index].split(",");
			for (String pileCode : pileCodes) {
				EquipmentInfo equipmentInfo =  equipmentInfoService.queryByCode(pileCode);
				VersionsUpgradeLog log = new VersionsUpgradeLog();
				log.setCreateId(user.getId());
				log.setCreateTime(new Date());
				log.setUpgradeVersionNo(pile.getVersionNo());
				log.setCurrentVersionNo(equipmentInfo.getVersion());
				log.setLpn(pileCode);
				log.setUpgradetype("pile");
				if ("00".equals(code)) { // 命令下载成功
					flag = true;
					log.setStatus(SUCCESS_STATUC);
					log.setRemark(result);
				} else {
					flag = false;
					log.setStatus(FAIL_STATUC);
					log.setRemark(result);
				}
				versionsUpgradeLogService.insertSelective(log);
			}
			
		}
		//所有的充电桩都是最新版本
		if(tem == 3){
			flag = true;
		}
		return flag;
	}
	/**
	 * 宝盒升级
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SystemServiceLog(description="电桩升级")
	@RequestMapping(value = "/pile_upgrade", method = { RequestMethod.GET , RequestMethod.POST })
	public String pileUpgrade(String pileCodes, HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		SysUser user = getUser(request);
		String[]  upgradeInfos= pileCodes.split(",");
		for (String upgradeInfo : upgradeInfos) {
			if(!"".equals(upgradeInfo)){
				String[] params = upgradeInfo.split("%-%");
				VersionsPile versionsPile = versionsPileService.selectByPrimaryId(Integer.parseInt(params[0]));
				if(versionsPile != null && StringUtils.isNotBlank(versionsPile.getVersionNo())&&StringUtils.isNotBlank(versionsPile.getVersionUrl())){
					//下发推送命令
					boolean flag =	savePileVersionsUpgradeLog(params[1],versionsPile,user);
					if(!flag){
						response.getWriter().print("fail");
						return null;
					}
				}else{
					//没有对应的版本信息
					response.getWriter().print("notVersionNo");
					return null;
				}
			}
		}
		
		response.getWriter().print("succ");
		return null;		
	}
	
	/**
	 * @author zixiaobang
	 * @date 2017.03.22
	 * @param pileCode 充电桩编码
	 * @param pile 版本号
	 * @param user 更新用户
	 * @return
	 * @throws Exception
	 */
	private boolean savePileVersionsUpgradeLog(String pileCode,
			VersionsPile pile, SysUser user) throws Exception {
		boolean flag = false;
		// 下发指令
		String json = "{}";
		String code = "";
		String ftpHost = sysParamService.selectByKey("ftp_host").getValue();
		String ftpUsername = sysParamService.selectByKey("ftp_username")
				.getValue();
		String ftpPassword = sysParamService.selectByKey("ftp_password")
				.getValue();
		String ftpPathname = sysParamService.selectByKey("ftp_pathname")
				.getValue();
		SysParam sysParam = sysParamService.selectByKey("http_url_c");
		String[] paths = pile.getVersionUrl().split("/");
		String filepath =ftpPathname +"/"+ paths[paths.length-1];
		StringBuffer sb = new StringBuffer("{");
		sb.append("\"cId\":\"platForm\",\"memberId\":\"\",");
		sb.append("\"method\":\"sendRemoteUpgradePileSoftware\",");
		sb.append("\"param\":\"{'equipmentCodeList':'" + pileCode
				+ "','ip':'" + ftpHost + "','path':'" +URLEncoder.encode(filepath,"utf-8") 
				+ "','ftpAccount':'" + ftpUsername
				+ "','ftpPassword':'" + ftpPassword + "','version':'"
				+ pile.getVersionNo() + "'}\",");
		sb.append("\"phone\":\"" + user.getPhone()
				+ "\",\"type\":\"platForm\",\"version\":\"1\"");
		sb.append("}");
		
		if(sysParam.getValue().indexOf("https") == 0){ //https接口
			json = HttpsClientUtil.get(sysParam.getValue(), sb.toString()) ;
		}else{
			json = HttpUtils.commonSendUrl(sysParam.getValue(), sb.toString()) ;
		}
		JSONObject jsonObject = JSONObject.fromObject(json);
		if (jsonObject.containsKey("code")) {
			code = jsonObject.getString("code");
		}
		String result =  jsonObject.getString("result");
		EquipmentInfo equipmentInfo =  equipmentInfoService.queryByCode(pileCode);
		VersionsUpgradeLog log = new VersionsUpgradeLog();
		log.setCreateId(user.getId());
		log.setCreateTime(new Date());
		log.setUpgradeVersionNo(pile.getVersionNo());
		log.setCurrentVersionNo(equipmentInfo.getVersion());
		log.setLpn(pileCode);
		log.setUpgradetype("pile");
		if ("00".equals(code)) { // 命令下载成功
				flag = true;
				log.setStatus(SUCCESS_STATUC);
				log.setRemark(result);
		} else {
				log.setStatus(FAIL_STATUC);
				log.setRemark(result);
		}
		versionsUpgradeLogService.insertSelective(log);
		return flag;
	}
}
