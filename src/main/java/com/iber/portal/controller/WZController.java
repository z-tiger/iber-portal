package com.iber.portal.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.iber.portal.advices.SystemServiceLog;
import com.iber.portal.common.Data2Jsp;
import com.iber.portal.common.ExcelUtil;
import com.iber.portal.common.HttpTool;
import com.iber.portal.common.Pager;
import com.iber.portal.dao.base.WZCitysMapper;
import com.iber.portal.dao.base.WZCitysQueryMapper;
import com.iber.portal.dao.base.WZQueryMapper;
import com.iber.portal.model.base.Member;
import com.iber.portal.model.base.WZCitys;
import com.iber.portal.model.base.WZCitysQuery;
import com.iber.portal.model.base.WZQuery;
import com.iber.portal.model.dispatcher.Employee;
import com.iber.portal.model.sys.SysUser;
import com.iber.portal.service.WZ.WZManageService;
import com.iber.portal.service.base.MemberService;
import com.iber.portal.service.dispatcher.EmployeeService;
import com.iber.portal.service.sys.SysParamService;
import com.iber.portal.service.timeShare.TimeShareOrderService;
import com.iber.portal.util.ResponseData;
import com.iber.portal.vo.base.WZQueryVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

@Controller
public class WZController extends MainController {
	
	@Autowired
	private WZCitysMapper wzCitysMapper;
	@Autowired
	private WZQueryMapper wzQueryMapper;
	@Autowired
	private WZManageService wzManageService;
	@Autowired
    private SysParamService sysParamService ;
	@Autowired
	private MemberService memberService;
	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private TimeShareOrderService timeShareOrderService;
	@Autowired
	private WZCitysQueryMapper wzCitysQueryMapper;
	
	@SystemServiceLog(description="违章城市页面")
	@RequestMapping(value = "/wz_citys", method = { RequestMethod.GET })
	public String wzCitys(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return "car/wzCitys";		
	}
	
	@SystemServiceLog(description="违章城市列表")
	@RequestMapping(value = "/wz_citys_lists", method = { RequestMethod.GET, RequestMethod.POST })
	public String wzCitysLists(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		List<WZCitys> data = wzCitysMapper.selectAll();
		String json = Data2Jsp.Json2Jsp(data, data.size());
		response.getWriter().print(json);
		return null;
	}
	@SystemServiceLog(description="修改违章城市")
	@RequestMapping(value = "/update_wz_citys", method = { RequestMethod.GET, RequestMethod.POST })
	public String updateWzCitys(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		String url = sysParamService.selectByKey("wz_citys_url").getValue();
		String str = HttpTool.sendGet(url);
		JSONObject jsonObject = JSONObject.parseObject(str);
        if(!StringUtils.isBlank(str) && jsonObject.getString("resultcode").equals("200")){
        	wzCitysMapper.deleteAll();
        	JSONObject resultJSONObject = JSONObject.parseObject(jsonObject.getString("result"));
        	Set<String>  set =  resultJSONObject.keySet();
        	for(String s : set){
        		JSONObject tmpJSONObject = JSONObject.parseObject(resultJSONObject.getString(s));
        		JSONArray  arr =  tmpJSONObject.getJSONArray("citys");
        		for(int i=0; i<arr.size(); i++){
        			JSONObject itemObj = JSONObject.parseObject(String.valueOf( arr.get(i)));
        			WZCitys wzCitys = new WZCitys();
        			wzCitys.setProvince(tmpJSONObject.getString("province"));
        			wzCitys.setProvinceCode(tmpJSONObject.getString("province_code"));
        			wzCitys.setCityName(itemObj.getString("city_name"));
        			wzCitys.setCityCode(itemObj.getString("city_code"));
        			wzCitys.setAbbr(itemObj.getString("abbr"));
        			wzCitys.setEngine(itemObj.getString("engine"));
        			wzCitys.setEngineno(itemObj.getString("engineno"));
        			wzCitys.setClassz(itemObj.getString("class"));
        			wzCitys.setClassno(itemObj.getString("classno"));
        			wzCitys.setClassa(itemObj.getString("classa"));
        			wzCitys.setRegist(itemObj.getString("regist"));
        			wzCitys.setRegistno(itemObj.getString("registno"));
        			wzCitys.setCreateTime(new Date());
        			wzCitysMapper.insertSelective(wzCitys);
        		}
        	}
        	response.getWriter().print("succ");
        }else{
        	response.getWriter().print("fail");
        }
		return "";		
	}
	
	@SystemServiceLog(description="违章记录查询")
	@RequestMapping(value = "/wz_records_page", method = { RequestMethod.GET })
	public String wzRecordsPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return "car/wzRecords";		
	}
	
	/**
	 * 违章记录数据列表
	 */

	@SystemServiceLog(description = "违章记录数据列表")
	@RequestMapping(value = "/wz_records_city_member", method = {
			RequestMethod.GET, RequestMethod.POST })
	public String wzRecordsCityMember(int page, int rows,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("text/html;charset=utf-8");
		Map<String, Integer> pageInfo = Data2Jsp
				.getFristAndPageSize(page, rows);
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("offset", pageInfo.get("first_page"));
		map.put("rows", pageInfo.get("page_size"));
		String type = null== request.getParameter("type")?"":request.getParameter("type").trim();
		//所属城市
		String code = request.getParameter("code");
		String lpn = request.getParameter("lpn");
		String custName = request.getParameter("custName");
		String custPhone = request.getParameter("custPhone");
		String status = request.getParameter("status");
		String orderId= request.getParameter("orderId");
		String handle_type= request.getParameter("handle_type");
		SysUser user = (SysUser) request.getSession().getAttribute("user");
//		if (StringUtils.isNotBlank(custName)){
//			List<Member> members = memberService.selectByName(custName);
//			if (members.size() > 0){
//				map.put("isMemberName",1);
//			}else {
//				map.put()
//			}
//		}
		if(user.getCityCode().equals("00")){
	       	   if(!StringUtils.isBlank(code)){
	         	 if(code.equals("00")){
	         		map.put("cityCode", null);
	         	 }else{
	         	 	map.put("cityCode", code);
	          	}
	          }
         } else{
   	       map.put("cityCode", user.getCityCode());
      	}
        map.put("lpn", lpn);
        map.put("phone", custPhone);
        map.put("name", custName);
        map.put("status", status);
		map.put("type", type);
		map.put("orderId",orderId);
		map.put("handle_type",handle_type);
		Pager<WZQueryVo> pager = new Pager<WZQueryVo>();
		List<WZQueryVo> vos = wzManageService.queryWzInfo(map);
		Integer count = wzManageService.queryWzInfoRecord(map);
		pager.setDatas(vos);
		pager.setTotalCount(count);
		//初始行数
		int i=0;
		// 1 代表员工类型用车违章
//		if("".equals(type)){
//
//			for(WZQueryVo vo:wzManageService.queryWZEmployeeInfos(map)){
//				vo.setId(i++);
//				vo.setType("1");
//				lists.add(vo);
//			}
//			total = total + wzManageService.queryWZEmployeeInfosRecords(map);
//
//			for (WZQueryVo wzQueryVo : wzManageService.queryWZMemberInfos(map)) {
//
//				wzQueryVo.setType("2");
//				wzQueryVo.setId(i++);
//				lists.add(wzQueryVo);
//			}
//			total = total+wzManageService.queryWZMemberInfosRecords(map);
//
//			for (WZQueryVo wzQueryVo : wzManageService.queryWZLongRentInfos(map)) {
//				wzQueryVo.setType("2");
//				wzQueryVo.setId(i++);
//				lists.add(wzQueryVo);
//			}
//			total = total+wzManageService.queryWZLongRentInfosRecords(map);
//
//		}else {
//     		if("1".equals(type)){
//    			for(WZQueryVo vo:wzManageService.queryWZEmployeeInfos(map)){
//					vo.setId(i++);
//    				vo.setType("1");
//    				lists.add(vo);
//    			}
//    			total = wzManageService.queryWZEmployeeInfosRecords(map);
//	    	}else {
//			    for (WZQueryVo wzQueryVo : wzManageService.queryWZMemberInfos(map)) {
//					wzQueryVo.setId(i++);
//					wzQueryVo.setType("2");
//					lists.add(wzQueryVo);
//				}
//			    total = wzManageService.queryWZMemberInfosRecords(map);
//
//				for (WZQueryVo wzQueryVo : wzManageService.queryWZLongRentInfos(map)) {
//					wzQueryVo.setType("2");
//					wzQueryVo.setId(i++);
//					lists.add(wzQueryVo);
//				}
//				total = total+wzManageService.queryWZLongRentInfosRecords(map);
//		    }
//		}
//		//将未关联到订单的违章信息查询出来
//		if (StringUtils.isBlank(custPhone) || StringUtils.isBlank(custName) ||
//				StringUtils.isBlank(orderId) ||StringUtils.isNotBlank(lpn) ||
//				StringUtils.isNotBlank(type) || StringUtils.isNotBlank(handle_type)
//				|| StringUtils.isNotBlank(code) || StringUtils.isNotBlank(status)){
//			List<WZQueryVo> vos = wzQueryMapper.selectWZButNoRelativeOrder(map);
//			lists.addAll(vos);
//			total += wzQueryMapper.selectWZButNoRelativeOrderRecord(map);
//		}
//		// 把查到车辆违章信息按照违章时间最近发生的排序
//        Collections.sort(lists, new Comparator<WZQueryVo>() {
//			public int compare(WZQueryVo o1, WZQueryVo o2) {
//				return o2.getDate().compareTo(o1.getDate());
//			}
//		});
		String json = Data2Jsp.Json2Jsp(pager);
		response.getWriter().print(json);
		return null;
	}
	
	@SystemServiceLog(description="车辆违章处理")
	@RequestMapping(value = "/wz_records_ex", method = { RequestMethod.GET, RequestMethod.POST })
	public String wzRecordsExecute(int id, String status,String handleType, String remark, HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");
		int r = wzQueryMapper.updateWZStatus(id, status,handleType, remark, new Date(), getUser(request).getName());
		//
		if(r > 0){
			response.getWriter().print("succ");
		}else{
			response.getWriter().print("fail");
		}
		return null;
	}
	
    /**
     * 车辆违章数据导出excel链接
	 * @Date   2017-05-17 16:00:00
	 * @author zhaocj
 	 * @param request
	 * @param response
	 * @return 
	 * @throws Exception
     */
	@SystemServiceLog(description = "车辆违章数据导出excel链接")
	@RequestMapping(value = "/export_WZ_Record_excel", method = {
			RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public void exportWZCarExcel(HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		String fileName = "CarWZRecordReport";
		// 列名
		String columnNames[] = {  "所属城市", "类型", "违章人姓名", "违章人手机号码", "违章订单号",
				"车牌号码", "违章时间", "违章地点", "违章行为", "违章罚款(元)","违章扣分","处理状态","备注" };
		String keys[] = { "WZCity", "WZType", "WZName", "WZPhone",
				"WZOrder", "lpn", "WZTime", "WZSpot", "WZBehavior",
				"WZFine", "WZScore","handleStatus","remark" };
		Map<String, Object> map = new HashMap<String, Object>();
		SysUser user = (SysUser) request.getSession().getAttribute("user");
		if(user.getCityCode().equals("00")){
	       	 if(!StringUtils.isBlank(request.getParameter("cityCode"))){
	         	if(request.getParameter("cityCode").equals("00")){
	         		map.put("cityCode", null);
	         	}else{
	         		map.put("cityCode", request.getParameter("cityCode"));
	         	}
	         }
        }else{
  	       map.put("cityCode", user.getCityCode());
       }
		map.put("name", request.getParameter("custName"));
		map.put("phone", request.getParameter("custPhone"));
		map.put("status", request.getParameter("status"));
		map.put("order_id", request.getParameter("orderId"));
		map.put("hphm", request.getParameter("lpn"));
        String type = request.getParameter("type");
        List<WZQueryVo> list = new ArrayList<WZQueryVo>();
        // 根据类型分别把员工用车违章和会员用车违章查询出来
        if(null!=type && !"".equals(type)){
        	if("1".equals(type)){
        		list.addAll(wzQueryMapper.selectEmployeeWZRecord(map));
        	}else {
				list.addAll(wzQueryMapper.selectMemberWZRecord(map));
			}
        }else {
        	Pager<WZQueryVo> pager = new Pager<WZQueryVo>();
    		Integer total = 0;
    		List<WZQueryVo> lists = new ArrayList<WZQueryVo>();
			lists = wzManageService.queryWzInfo((HashMap<String, Object>) map);
    		//初始行数
//    		int i=0;
    		// 1 代表员工类型用车违章
//    		if("".equals(type)){

//    			for(WZQueryVo vo:wzManageService.queryWZEmployeeInfos(map)){
//    				vo.setId(i++);
//    				vo.setType(1);
//    				lists.add(vo);
//    			}
//    			for (WZQueryVo wzQueryVo : wzManageService.queryWzInfo((HashMap<String, Object>) map)) {
//
//    				wzQueryVo.setType(2);
//    				wzQueryVo.setId(i++);
//    				lists.add(wzQueryVo);
//    			}
//    			for (WZQueryVo wzQueryVo : wzManageService.queryWZLongRentInfos(map)) {
//    				wzQueryVo.setType(2);
//    				wzQueryVo.setId(i++);
//    				lists.add(wzQueryVo);
//    			}
//    		}else {
//         		if("1".equals(type)){
//        			for(WZQueryVo vo:wzManageService.queryWZEmployeeInfos(map)){
//    					vo.setId(i++);
//        				vo.setType(1);
//        				lists.add(vo);
//        			}
//    	    	}else {
//    			    for (WZQueryVo wzQueryVo : wzManageService.queryWZMemberInfos(map)) {
//    					wzQueryVo.setId(i++);
//    					wzQueryVo.setType(2);
//    					lists.add(wzQueryVo);
//    				}
//
//    				for (WZQueryVo wzQueryVo : wzManageService.queryWZLongRentInfos(map)) {
//    					wzQueryVo.setType(2);
//    					wzQueryVo.setId(i++);
//    					lists.add(wzQueryVo);
//    				}
//    		    }
//    		}
    		// 把查到车辆违章信息按照违章时间最近发生的排序
            Collections.sort(lists, new Comparator<WZQueryVo>() {
    			public int compare(WZQueryVo o1, WZQueryVo o2) {
    				return o2.getDate().compareTo(o1.getDate());
    			}
    		});  
    		pager.setDatas(lists);
    		pager.setTotalCount(total);
    		List<WZQueryVo> datas = pager.getDatas();
//			list.addAll(wzQueryMapper.selectEmployeeWZRecord(map));
// 			list.addAll(wzQueryMapper.selectMemberWZRecord(map));
    		list.addAll(datas);
        	
// 	        // 把最新违章数据列在前面
// 	        Collections.sort(list, new Comparator<WZQueryVo>() {
// 				public int compare(WZQueryVo o1, WZQueryVo o2) {
// 					return o2.getDate().compareTo(o1.getDate());
// 				}
// 			});  
		}
        //recordMaps 用来存放生成excel报表的单元格数据
        List<Map<String, Object>> recordMaps = new ArrayList<Map<String,Object>>();
        //sheetMaps 用来存放生成excel报表的sheet名称
		Map<String, Object> sheetNameMap = new HashMap<String, Object>();
		sheetNameMap.put("sheetName", "车辆违章数据");
		recordMaps.add(sheetNameMap);
		recordMaps.addAll(createWZRecordExcel(list));
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try {
			ExcelUtil.createTimeShareWorkBook(recordMaps, keys, columnNames,null)
					.write(os);
		} catch (IOException e) {
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

	}
	private List<Map<String, Object>> createWZRecordExcel(List<WZQueryVo> WZQueryVos){
		List<Map<String, Object>> listmap = new ArrayList<Map<String, Object>>();
		for (WZQueryVo wzQueryVo :WZQueryVos) {
				Map<String, Object> mapValue = new HashMap<String, Object>();
				mapValue.put("WZCity", null == wzQueryVo.getCityName() ? "" : wzQueryVo.getCityName());
				// 员工用车订单号与会员用车订单号的最大区别就是: 员工用车订单号中嵌套了一个 employee 字符串
				if (wzQueryVo.getType() == 2) {
					mapValue.put("WZType", "会员");
				} else if(wzQueryVo.getType() == 1){
					mapValue.put("WZType", "员工");
				}else{
					mapValue.put("WZType", "");
				}
				mapValue.put("WZName", null == wzQueryVo.getCustName() ? "" : wzQueryVo.getCustName());
				mapValue.put("WZPhone", null == wzQueryVo.getCustPhone() ? "" : wzQueryVo.getCustPhone());
				mapValue.put("WZOrder", null == wzQueryVo.getOrderId() ? "" : wzQueryVo.getOrderId());
				mapValue.put("lpn", null == wzQueryVo.getHphm() ? "" : wzQueryVo.getHphm());
				mapValue.put("WZTime", null == wzQueryVo.getDate() ? "" : wzQueryVo.getDate());
				mapValue.put("WZSpot", null == wzQueryVo.getArea() ? "" : wzQueryVo.getArea());
				mapValue.put("WZBehavior", null == wzQueryVo.getAct() ? "" : wzQueryVo.getAct());
				mapValue.put("WZFine", null == wzQueryVo.getMoney() ? "" : wzQueryVo.getMoney());
				mapValue.put("WZScore", null == wzQueryVo.getFen() ? "" : wzQueryVo.getFen());
				// 违章处理状态, 1 代表未处理
				if ("1".equals(wzQueryVo.getStatus())) {
					mapValue.put("handleStatus", "未处理");
				} else {
					mapValue.put("handleStatus", "已处理");
				}
				mapValue.put("remark", null == wzQueryVo.getRemark() ? "" : wzQueryVo.getRemark());
				listmap.add(mapValue);
		}
		return listmap;
	}

	@RequestMapping(value = "/getNameByPhone",method = {RequestMethod.GET})
	@SystemServiceLog(description = "根据手机号获取姓名")
	@ResponseBody
	public ResponseData getNameByPhone(Integer type, String phone){
		Map<String,Object> data = new HashMap<String,Object>();
		if (StringUtils.isNotBlank(phone)){
			if (null != type){
				if (type == 2){
					Member member = memberService.selectDetailByPhone(phone);
					if (null != member){
						data.put("name",member.getName());
					}

				}else {
					Employee employee = employeeService.selectByPhone(phone);
					if (null != employee){
						data.put("name",employee.getName());
					}
				}
			}
		}
		return new ResponseData(200,"ok",data);
	}

	@RequestMapping(value = "/getOrderIdByLpn",method = {RequestMethod.GET})
	@SystemServiceLog(description = "根据车牌号获取订单")
	public void getOrderIdByPhone(Integer type,String lpn,HttpServletResponse response) throws Exception{
		response.setContentType("text/html;charset=utf-8");
		Map<String,Object> data = new HashMap<String,Object>();
		StringBuffer tree=new StringBuffer();
		if (StringUtils.isNotBlank(lpn)){
			List<Map<String,String>> orderIds = new ArrayList<Map<String,String>>();
			if (null != type){
				if (type == 2){
					 orderIds = timeShareOrderService.queryOrderIdsByLpn(lpn);
				}else {
					orderIds = employeeService.queryOrderIdsByLpn(lpn);
				}
			}

			tree.append("[");
			for(int i=0;i<orderIds.size();i++ ){
				Map<String,String> map =orderIds.get(i);
				Collection<String> values = map.values();
				String[] arrays = new String[2];
				String[] strings = values.toArray(arrays);
				tree.append("{");
				tree.append("\"phone_orderId\":\""+strings[0]+"_"+strings[1]+"\",");
				tree.append("\"orderId\":\""+strings[1]+"\"");
				if(i<orderIds.size()-1){
					tree.append("},");
				}else{
					tree.append("}");
				}
			}
			tree.append("]");
		}
		response.getWriter().print(tree.toString());
	}

	@RequestMapping(value = "wz_save",method = {RequestMethod.POST})
	@SystemServiceLog(description = "添加违章信息")
	@ResponseBody
	public Map<String,Object> wzSave(String lpn_orderId, String phone, String archiveno, WZQuery wzQuery,Integer type) throws Exception{
		Integer index = lpn_orderId.indexOf("_");
		String orderId = lpn_orderId.substring(index+1);
		String lpn = lpn_orderId.substring(0,index);
		String date = wzQuery.getDate();
		Integer id = wzQuery.getId();
		Integer count = wzQueryMapper.selectRecordByLpnAndDateAndArchiveno(lpn,date,wzQuery.getAct(),id);
		if (count > 0){
			return fail("已经存在该违章信息，请勿重复添加");
		}
		if (null != type && type.equals(2)){
			Member member = memberService.selectDetailByPhone(phone);
			wzQuery.setMemberId(member.getId());
		}else if (null != type && type.equals(1)){
			Employee employee = employeeService.selectByPhone(phone);
			wzQuery.setMemberId(employee.getId());
		}
		String cityCode = wzQuery.getCityCode();
		WZCitysQuery wzCitysQuery = wzCitysQueryMapper.queryWZCitysQueryByCityCode(cityCode);
		wzQuery.setCity(wzCitysQuery.getQueryCityCode());
		wzQuery.setStatus("1");
		wzQuery.setOrderId(orderId);
		if (null == wzQuery.getId()){
			wzQuery.setCreateTime(new Date());
			wzQuery.setCreateUser(getUser(request).getName());
			wzQueryMapper.insertSelective(wzQuery);
		}else {
			wzQuery.setUpdateTime(new Date());
			wzQuery.setUpdateUser(getUser(request).getName());
			wzQueryMapper.update(wzQuery);
		}

		return success("操作成功");
	}
}
