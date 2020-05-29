package com.iber.portal.controller.member;

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

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.text.StrTokenizer;
import org.apache.commons.lang3.StringUtils;
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
import com.iber.portal.model.employee.RescuerEmployeeInfo;
import com.iber.portal.model.member.MemberBehavior;
import com.iber.portal.model.member.MemberBehaviorType;
import com.iber.portal.model.sys.SysUser;
import com.iber.portal.service.member.MemberBehaviorService;
import com.iber.portal.service.member.MemberBehaviorTypeService;
@Controller
public class MemberBehaviorController extends MainController {
	
	private final static Logger log= Logger.getLogger(MemberBehaviorController.class);
	
	@Autowired(required=false) //自动注入，不需要生成set方法了，required=false表示没有实现类，也不会报错。
	private MemberBehaviorService memberBehaviorService;
	
	@Autowired(required=false) //自动注入，不需要生成set方法了，required=false表示没有实现类，也不会报错。
	private MemberBehaviorTypeService memberBehaviorTypeService; 
	
	/**
	 * 会员信息
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/behavior_page") 
	public String memberBehavior_page(HttpServletRequest request, HttpServletResponse response) {
		log.info("behavior_page页面");
		return "member/behavior" ;
	}
	/**
	 * 会员信息
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/behaviorType_page") 
	public String memberBehaviorTypePage(HttpServletRequest request, HttpServletResponse response) {
		log.info("behaviorType_page页面");
		return "member/behaviorType" ;
	}
	/**
	 * 
	 * @param page
	 * @param rows
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@SystemServiceLog(description="用户行为明细列表")
	 @RequestMapping("/behavior_list") 
		public String behaviorList(int page, int rows,HttpServletRequest request, HttpServletResponse response) throws IOException {
		 response.setContentType("text/html;charset=utf-8");
		 Map<String,Integer> pageInfo = Data2Jsp.getFristAndPageSize(page, rows);
		 int from = pageInfo.get("first_page");
		 int to = pageInfo.get("page_size");
		 Map<String, Object> paramMap = new HashMap<String, Object>();
		 paramMap.put("name", request.getParameter("name"));
		 String maxContriValue = request.getParameter("maxContriValue");
		 if(!StringUtils.isBlank(maxContriValue)){
			 paramMap.put("maxContriValue", Integer.valueOf(maxContriValue));
		 }
		 String minContriValue = request.getParameter("minContriValue");
		 if(!StringUtils.isBlank(minContriValue)){
			 paramMap.put("minContriValue", Integer.valueOf(minContriValue));
		 }
		 paramMap.put("behaviorId", request.getParameter("behaviorId"));
		 paramMap.put("isIncrease", request.getParameter("isIncrease"));
		 paramMap.put("isRatio", request.getParameter("isRatio"));
		 paramMap.put("from", from);
	     paramMap.put("to", to);
	     List<MemberBehavior> data = memberBehaviorService.getMemberBehaviorList(paramMap);
	     int record=memberBehaviorService.getMemberBehaviorNum(paramMap);
	     JSONObject obj = new JSONObject();
		 obj.put("total", record);
		 obj.put("rows", data);
	     response.getWriter().print(obj.toString());
	     return null;
		}
	
	@SystemServiceLog(description="数据导出")
	@RequestMapping(value = "/export_behavior_report", method = { RequestMethod.GET , RequestMethod.POST })
	public List exportExecl(String name,String maxContriValue,String minContriValue,String behaviorId,String isIncrease,String isRatio,
			HttpServletRequest request,HttpServletResponse response) throws Exception {
		String fileName = "BehaviorReport" ;
		//列名充电桩编码	
		String columnNames [] = {"分类","子分类", "贡献值类型", "加/扣分", "贡献值", "关联数值类型","关联数值N","说明" };
		
		String keys[] = {"behaviorType", "name", "isRatio","isIncrease", "contriValue","conditionType","conditionVal", "contriDetal"};
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("name", name);
		if(StringUtils.isNotBlank(maxContriValue)){
			paramMap.put("maxContriValue", Integer.valueOf(maxContriValue));
		}
		 if(!StringUtils.isBlank(minContriValue)){
			 paramMap.put("minContriValue", Integer.valueOf(minContriValue));
		 }
		 paramMap.put("behaviorId", behaviorId);
		 paramMap.put("isIncrease", isIncrease);
		 paramMap.put("isRatio", isRatio);
		 paramMap.put("from", null);
	     paramMap.put("to", null);
		
	    List<MemberBehavior> datas = memberBehaviorService.getMemberBehaviorList(paramMap);
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		Map<String, Object> sheetNameMap = new HashMap<String, Object>();
		sheetNameMap.put("sheetName", "会员行为明细数据报表");
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
			List<MemberBehavior> data) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map;// 避免重复创建对象
		String keys[] = {"behaviorType", "name", "isRatio","isIncrease", "contriValue","conditionType","conditionVal", "contriDetal"};
		for (MemberBehavior  mb: data) {
			map = new HashMap<String, Object>();
			map.put(keys[0], mb.getBehaviorType());
			map.put(keys[1], mb.getName());
			if(mb.getIsRatio() != null){
				if (mb.getIsRatio() == 0) {
					map.put(keys[2], "绝对值");
				}else if(mb.getIsRatio() ==1 ){
					map.put(keys[2], "比例");
				}
			}else{
				map.put(keys[2], mb.getIsRatio());
			}
			if(mb.getIsIncrease() != null){
				if(mb.getIsIncrease() == 0){
					map.put(keys[3], "扣分");
				}else if(mb.getIsIncrease() ==1 ){
					map.put(keys[3], "加分");
				}
			}else{
				map.put(keys[3], mb.getIsIncrease());
			}
			map.put(keys[4], mb.getContriValue());
			String conditionType = mb.getConditionType();
			if(conditionType != null && !conditionType.equals("")){
				if("hour".equals(conditionType)){
					map.put(keys[5], "小时");
				}else if("hour".equals(conditionType)) {
					map.put(keys[5], "");
				}else if("day".equals(conditionType)) {
					map.put(keys[5], "天");
				}else if("month".equals(conditionType)) {
					map.put(keys[5], "月");
				}else if("year".equals(conditionType)) {
					map.put(keys[5], "年");
				}else if("count".equals(conditionType)) {
					map.put(keys[5], "次数");
				}else if("name".equals(conditionType)) {
					map.put(keys[5], "名称");
				}else{
					map.put(keys[5], conditionType);
				}
				
			}else{
				map.put(keys[5], conditionType);
			}
			map.put(keys[6], mb.getConditionVal());
			map.put(keys[7], mb.getContriDetal());

			
			list.add(map);
		}
		return list;
	}
	
	@SystemServiceLog(description="救援行为类型")
	@RequestMapping(value = "/rescueBehaviorType", method = { RequestMethod.GET, RequestMethod.POST})
	public String sysDictType(String dicCode, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("text/html;charset=utf-8");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("behaviorId", request.getParameter("behaviorId"));
		map.put("status", "1");
		List<MemberBehavior> lists = memberBehaviorService.getRescueBehaviorTypeList(map);
//		List<SysDic> dictTypeList = sysDicService.selectListByDicCode(dicCode);		
		JSONArray arr = JSONArray.fromObject(lists);
		response.getWriter().print(arr.toString());
		return null;	
	}	
	    /**
	     * 
	     * @param request
	     * @param response
	     * @return
	     * @throws IOException
	     */
		@SystemServiceLog(description="添加或修改用户行为明细")
	    @RequestMapping("/addOrUpdateBehavior") 
		public String addOrUpdateBehavior(HttpServletRequest request, HttpServletResponse response) throws IOException {
		 response.setContentType("text/html;charset=utf-8");
		 SysUser sysUser = (SysUser) request.getSession().getAttribute("user");	
		 String id=request.getParameter("behavior_id");
		 String behaviorType=request.getParameter("behaviorType");
		 String name=request.getParameter("name");
		 String sort=StringUtils.isNotBlank(request.getParameter("sort"))?request.getParameter("sort"):"0";
		 String isRatio=request.getParameter("isRatio");
		 String isIncrease=request.getParameter("isIncrease");
		 String contriValue=request.getParameter("contriValue");
		 String contriDetail=request.getParameter("contriDetail");
		 String condition=request.getParameter("condition");
		 String conditionType=request.getParameter("conditionType");
		 String memberComplain=request.getParameter("memberComplain")== null ? "0":"1";
		 String employeeComplain=request.getParameter("employeeComplain")== null ? "0":"1";
		 String canAdd=request.getParameter("canAdd");
		
		 if(StringUtils.isNotBlank(id)){
			 MemberBehavior memberBehavior= memberBehaviorService.selectByPrimaryKey(Integer.parseInt(id));
			 memberBehavior.setName(name);
			 memberBehavior.setBehaviorId(Integer.parseInt(behaviorType));
			 memberBehavior.setSort(Integer.parseInt(sort));
			 memberBehavior.setIsRatio(Integer.parseInt(isRatio));
			 memberBehavior.setIsIncrease(Integer.parseInt(isIncrease));
			 memberBehavior.setContriValue(contriValue);
			 memberBehavior.setContriDetal(contriDetail);
			 memberBehavior.setUpdateId(sysUser.getId());
			 memberBehavior.setUpdateTime(new Date());
			 memberBehavior.setConditionVal(condition);
			 memberBehavior.setConditionType(conditionType);
			 memberBehavior.setMemberComplain(memberComplain);
			 memberBehavior.setEmployeeComplain(employeeComplain);
			 memberBehavior.setCanAdd(canAdd);
			 int record=memberBehaviorService.updateByPrimaryKeySelective(memberBehavior);
			 if(record>0){
				 response.getWriter().print("success");
			 }else{
				 response.getWriter().print("fail");
			 }
		 }else{
			 MemberBehavior memberBehavior=new MemberBehavior();
			 memberBehavior.setName(name);
			 memberBehavior.setBehaviorId(Integer.parseInt(behaviorType));
			 memberBehavior.setSort(Integer.parseInt(sort));
			 memberBehavior.setIsRatio(Integer.parseInt(isRatio));
			 memberBehavior.setIsIncrease(Integer.parseInt(isIncrease));
			 memberBehavior.setContriValue(contriValue);
			 memberBehavior.setContriDetal(contriDetail);
			 memberBehavior.setCreateId(sysUser.getId());
			 memberBehavior.setCreateTime(new Date());
			 memberBehavior.setConditionVal(condition);
			 memberBehavior.setConditionType(conditionType);
			 memberBehavior.setMemberComplain(memberComplain);
			 memberBehavior.setEmployeeComplain(employeeComplain);
			 memberBehavior.setCanAdd(canAdd);
			 int record=memberBehaviorService.insertSelective(memberBehavior);
             if(record>0){
            	 response.getWriter().print("success");
			 }else{
				 response.getWriter().print("fail");
			 }
		 }
		return null;
	 }
	    
	    @SystemServiceLog(description="删除用户行为明细分类")
		@RequestMapping(value = "/delete_behavior", method = { RequestMethod.GET , RequestMethod.POST })
		public String deleteBehavior(String id, HttpServletRequest request, HttpServletResponse response)
				throws Exception {		
			response.setContentType("text/html;charset=utf-8");

			if (id!=null && !id.equals("")) {
				memberBehaviorService.deleteByPrimaryKey(Integer.parseInt(id));
			}
			response.getWriter().print("success");
			return null;
		}
		/**
		 * 
		 * @param page
		 * @param rows
		 * @param request
		 * @param response
		 * @return
		 * @throws IOExceptionaddOrUpdateBehaviorType
		 */
	    @SystemServiceLog(description="行为类型列表")
		@RequestMapping("/behaviorType_list") 
		public String behaviorTypeList(int page, int rows,HttpServletRequest request, HttpServletResponse response) throws IOException {
		 response.setContentType("text/html;charset=utf-8");
		 Map<String,Integer> pageInfo = Data2Jsp.getFristAndPageSize(page, rows);
		 int from = pageInfo.get("first_page");
		 int to = pageInfo.get("page_size");
		 String behaviorName = request.getParameter("behaviorName");
		 Map<String, Object> paramMap = new HashMap<String, Object>();
		 paramMap.put("from", from);
	     paramMap.put("to", to);
	     paramMap.put("behaviorName", behaviorName);
	     List<MemberBehaviorType> data = memberBehaviorTypeService.getMemberBehaviorTypeList(paramMap);
	     int record = memberBehaviorTypeService.getMemberBehaviorTypeNum(paramMap);
		JSONObject obj = new JSONObject();
		obj.put("total", record);
		obj.put("rows", data);
	     response.getWriter().print(obj.toString());
	     return null;
		}
	    
	  /**
	   *  获取所有行为类型类表
	   * @param request
	   * @param response
	   * @return
	   * @throws IOException
	   */
	@SystemServiceLog(description = "行为类型列表")
	@RequestMapping("/getBehaviorType_list")
	public String getBehaviorType_list(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		List<MemberBehaviorType> dataList = memberBehaviorTypeService.getAll();
		StringBuffer tree = new StringBuffer();
		tree.append("[");
		tree.append("{");
		tree.append("\"id\":\"-1\",");
		tree.append("\"text\":\"全部\"");
		tree.append("},");
		if (dataList != null && dataList.size() > 0) {
			for (int i = 0; i < dataList.size(); i++) {
				MemberBehaviorType type = dataList.get(i);
				tree.append("{");
				tree.append("\"id\":\"" + type.getId() + "\",");
				tree.append("\"text\":\"" + type.getBehaviorName() + "\"");
				if (i < dataList.size() - 1) {
					tree.append("},");
				} else {
					tree.append("}");
				}
			}
		}
		tree.append("]");
		response.getWriter().print(tree.toString());
		return null;
	}

		 /**
		  * 会员行为类型添加和修改
		  * @param request
		  * @param response
		  * @return
		  * @throws IOException
		  */
	    	@SystemServiceLog(description="会员行为类型添加和修改")
		   @RequestMapping("/addOrUpdateBehaviorType") 
			public String addOrUpdateBehaviorType(HttpServletRequest request, HttpServletResponse response) throws IOException {
			 response.setContentType("text/html;charset=utf-8");
			 SysUser sysUser = (SysUser) request.getSession().getAttribute("user");	
			 String id=request.getParameter("behaviorType_id");
			 String behaviorTypeName=request.getParameter("behaviorTypeName");
			 String complain=request.getParameter("complain");
			 String complainType=request.getParameter("complainType");
			 String behaviorType=request.getParameter("behaviorType");
			 String behaviorDetail=request.getParameter("behaviorDetail");
			 String canAdd=request.getParameter("canAdd");
			 JSONObject json = new JSONObject();
			 int records = 0;
			 if(StringUtils.isNotBlank(id)){
				 MemberBehaviorType type= memberBehaviorTypeService.selectMemberBehaviorTypeByKey(Integer.parseInt(id));
				 if(type == null){
					json.accumulate("status", "fail");
					json.accumulate("message", "数据已被删除，请刷新后再试");
					 response.getWriter().print(json.toString());
					 return null;
				 }
				 type.setBehaviorName(behaviorTypeName);
				 type.setBehaviorDetail(behaviorDetail);
				 type.setUpdateTime(new Date());
				 type.setUpdateId(sysUser.getId());
				 type.setId(Integer.parseInt(id));
				 if(StringUtils.isNotBlank(complain)) type.setComplain(complain);
				 if(StringUtils.isNotBlank(complainType)) type.setComplainType(complainType);
				 if(StringUtils.isNotBlank(behaviorType))type.setBehaviorType(behaviorType);
				 if(StringUtils.isNotBlank(canAdd))type.setCanAdd(canAdd);
				  records=memberBehaviorTypeService.updateByPrimaryKeySelective(type);
			 }else{
				 MemberBehaviorType type=new MemberBehaviorType();
				 type.setBehaviorName(behaviorTypeName);
				 type.setBehaviorDetail(behaviorDetail);
				 type.setCreateId(sysUser.getId());
				 type.setCreateTime(new Date());
				 if(StringUtils.isNotBlank(complain)) type.setComplain(complain);
				 if(StringUtils.isNotBlank(complainType)) type.setComplainType(complainType);
				 if(StringUtils.isNotBlank(behaviorType))type.setBehaviorType(behaviorType);
				 if(StringUtils.isNotBlank(canAdd))type.setCanAdd(canAdd);
				  records=memberBehaviorTypeService.insertSelective(type);
			 }
			 if(records>0){
				 json.accumulate("status", "success");
			 }else{
				 json.accumulate("status", "fail");
				 json.accumulate("message", "网络繁忙,请稍后再试");
			 }
			 response.getWriter().print(json.toString());
		     return null;
	  }    
	   /**
	    * 
	    * @param id
	    * @param request
	    * @param response
	    * @return
	    * @throws Exception
	    */
	   @SystemServiceLog(description="删除行为分类")
		@RequestMapping(value = "/delete_behaviorType", method = { RequestMethod.GET , RequestMethod.POST })
		public String deleteBehaviorType(String id, HttpServletRequest request, HttpServletResponse response)
				throws Exception {		
			response.setContentType("text/html;charset=utf-8");
			JSONObject json  = new JSONObject();
			if (StringUtils.isNotBlank(id)) {
				//查询该类型下是否有用户行为（如有提示用户不能删除）
				int records = memberBehaviorService.getCountByBehaviorId(Integer.parseInt(id));
				if(records > 0){
					json.accumulate("status", "fail");
					json.accumulate("message", "该类型下有用户行为数据，不能删除");
				}else{
					memberBehaviorTypeService.deleteByPrimaryKey(Integer.parseInt(id));
					json.accumulate("status", "success");
				}
			}
			response.getWriter().print(json.toString());
			return null;
		}
	   
	   /**
	    * 用户行为分类明细开关操作
	    * @param request
	    * @param response
	    * @throws Exception
	    */
	   @SystemServiceLog(description="用户行为分类明细开关操作")
	   @RequestMapping(value="/modifyBehaviorStatus" , method={RequestMethod.GET , RequestMethod.POST})
	   public  void modifyBehaviorStatus(HttpServletRequest request, HttpServletResponse response)throws Exception{
		   response.setContentType("text/html;charset=utf-8");
			JSONObject json  = new JSONObject(); 
			String id = request.getParameter("id");
			String status = request.getParameter("status");
			if(StringUtils.isNotBlank(id)&&StringUtils.isNotBlank(status)){
				MemberBehavior memberBehavior = memberBehaviorService.selectByPrimaryKey(Integer.parseInt(id));
				if(memberBehavior == null){
					json.accumulate("status","fail");
					json.accumulate("message","该数据已被删除，请刷新后再操作");
					return ;
				}
				memberBehavior.setStatus(Integer.parseInt(status));
				int record = memberBehaviorService.updateByPrimaryKeySelective(memberBehavior);
				if(record > 0){
					json.accumulate("status","success");
				}else{
					json.accumulate("status","fail");
					json.accumulate("message","系统繁忙，请稍后再试");
				}
			}else{
				json.accumulate("status","fail");
				json.accumulate("message","系统繁忙，请稍后再试");
			}
			response.getWriter().print(json.toString());
	   }

	   /**
	    * 修改用户行为明细
	    * @param request
	    * @param response
	    * @throws Exception
	    */
	@SystemServiceLog(description = "修改用户行为明细")
	@RequestMapping(value = "/modifyBehavior", method = { RequestMethod.GET, RequestMethod.POST })
	public void modifyBehavior(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		JSONObject json = new JSONObject();
		String id = request.getParameter("behavior_id");
		String isRatio = request.getParameter("isRatio");
		String isIncrease = request.getParameter("isIncrease");
		String contriValue = request.getParameter("contriValue");
		String contriDetail = request.getParameter("contriDetail");
		if (StringUtils.isNotBlank(id)) {
			MemberBehavior memberBehavior = memberBehaviorService.selectByPrimaryKey(Integer.parseInt(id));
			memberBehavior.setIsRatio(Integer.parseInt(isRatio));
			memberBehavior.setIsIncrease(Integer.parseInt(isIncrease));
			memberBehavior.setContriValue(contriValue);
			memberBehavior.setContriDetal(contriDetail);
			int record = memberBehaviorService.updateByPrimaryKeySelective(memberBehavior);
			if(record > 0){
				json.accumulate("status","success");
			}else{
				json.accumulate("status","fail");
				json.accumulate("message","修改失败，请稍后再试");
			}
		} else {
			json.accumulate("status", "fail");
			json.accumulate("message", "获取行为明细失败，请稍后再试");
		}
		response.getWriter().print(json.toString());
	}
		
	/**
	 * 查询客服可以添加用户信用的行为类型
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@SystemServiceLog(description = "查询客服可以添加信用的行为类型")
	@RequestMapping(value = "getCanAddBehaviorType_list", method = {RequestMethod.GET, RequestMethod.POST })
	public void getCanAddBehaviorTypeList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		List<MemberBehaviorType> dataList = memberBehaviorTypeService.getCanAddBehaviorTypeList();
		StringBuffer tree = new StringBuffer();
		tree.append("[");
		if (dataList != null && dataList.size() > 0) {
			for (int i = 0; i < dataList.size(); i++) {
				MemberBehaviorType type = dataList.get(i);
				tree.append("{");
				tree.append("\"id\":\"" + type.getId() + "\",");
				tree.append("\"text\":\"" + type.getBehaviorName() + "\"");
				if (i < dataList.size() - 1) {
					tree.append("},");
				} else {
					tree.append("}");
				}
			}
		}
		tree.append("]");
		response.getWriter().print(tree.toString());
	}
	
	@SystemServiceLog(description = "查询客服可以添加信用的行为类型")
	@RequestMapping(value = "getValidBehaviorType_list", method = {RequestMethod.GET, RequestMethod.POST })
	public void getValidBehaviorType_list(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		String isMemberComplain = request.getParameter("isMemberComplain");
		List<MemberBehaviorType> dataList = memberBehaviorTypeService.getValidBehaviorType(isMemberComplain);
		StringBuffer tree = new StringBuffer();
		tree.append("[");
		if (dataList != null && dataList.size() > 0) {
			for (int i = 0; i < dataList.size(); i++) {
				MemberBehaviorType type = dataList.get(i);
				tree.append("{");
				tree.append("\"id\":\"" + type.getId() + "\",");
				tree.append("\"text\":\"" + type.getBehaviorName() + "\"");
				if (i < dataList.size() - 1) {
					tree.append("},");
				} else {
					tree.append("}");
				}
			}
		}
		tree.append("]");
		response.getWriter().print(tree.toString());
	}
	
	@SystemServiceLog(description = "查询客服可以添加信用的行为类型")
	@RequestMapping(value = "/getVaildBehaviorByTypeId", method = {RequestMethod.GET, RequestMethod.POST })
	public void getVaildBehaviorByTypeId(HttpServletRequest request,HttpServletResponse response)throws Exception{
		response.setContentType("text/html;charset=utf-8");
		String parentId = request.getParameter("parentId");
		String isMemberComplain = request.getParameter("isMemberComplain");
		if(StringUtils.isBlank(parentId)){
			return ;
		}
		List<MemberBehavior> dataList = memberBehaviorService.getVaildBehaviorByTypeId(parentId,isMemberComplain);
		StringBuffer tree = new StringBuffer();
		tree.append("[");
		if (dataList != null && dataList.size() > 0) {
			for (int i = 0; i < dataList.size(); i++) {
				MemberBehavior type = dataList.get(i);
				tree.append("{");
				tree.append("\"id\":\"" + type.getId() + "\",");
				tree.append("\"text\":\"" + type.getName() + "\"");
				if (i < dataList.size() - 1) {
					tree.append("},");
				} else {
					tree.append("}");
				}
			}
		}
		tree.append("]");
		response.getWriter().print(tree.toString());
	}
	@SystemServiceLog(description = "查询客服可以添加信用的行为类型")
	@RequestMapping(value = "/getCanAddBehaviorByTypeId", method = {RequestMethod.GET, RequestMethod.POST })
	public void getBehaviorByTypeId(HttpServletRequest request,HttpServletResponse response)throws Exception{
		response.setContentType("text/html;charset=utf-8");
		String parentId = request.getParameter("parentId");
		if(StringUtils.isBlank(parentId)){
			return ;
		}
		List<MemberBehavior> dataList = memberBehaviorService.getByBehaviorIdAndCanAdd(Integer.parseInt(parentId),1);
		StringBuffer tree = new StringBuffer();
		tree.append("[");
		if (dataList != null && dataList.size() > 0) {
			for (int i = 0; i < dataList.size(); i++) {
				MemberBehavior type = dataList.get(i);
				tree.append("{");
				tree.append("\"id\":\"" + type.getId() + "\",");
				tree.append("\"text\":\"" + type.getName() + "\"");
				if (i < dataList.size() - 1) {
					tree.append("},");
				} else {
					tree.append("}");
				}
			}
		}
		tree.append("]");
		response.getWriter().print(tree.toString());
	}
	@SystemServiceLog(description = "查询严重行为类型")
	@RequestMapping(value = "/getSeveritiyBehavior", method = {RequestMethod.GET, RequestMethod.POST })
	public void getSeveritiyBehavior(HttpServletRequest request,HttpServletResponse response)throws Exception{
		response.setContentType("text/html;charset=utf-8");
		String id = request.getParameter("parentId");
		String result ="";
		if(StringUtils.isNotBlank(id)){
			MemberBehaviorType type = memberBehaviorTypeService.selectMemberBehaviorTypeByKey(Integer.parseInt(id));
			if(StringUtils.equals("2", type.getBehaviorType())){
				result="success";
			};
		}
		response.getWriter().print(result);
	}
}
