package com.iber.portal.controller.member;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSON;
import com.iber.portal.advices.SystemServiceLog;
import com.iber.portal.common.Data2Jsp;
import com.iber.portal.common.ExcelUtil;
import com.iber.portal.common.Pager;
import com.iber.portal.model.base.MemberLevel;
import com.iber.portal.model.employee.RescuerEmployeeInfo;
import com.iber.portal.model.member.MemberContributedDetail;
import com.iber.portal.model.sys.SysUser;
import com.iber.portal.model.task.TaskGrade;
import com.iber.portal.service.base.MemberLevelService;
import com.iber.portal.service.member.MemberContributedDetailService;
import com.iber.portal.vo.member.MemberContributedDetailVo;
import com.iber.portal.vo.member.MemberVo;

/**
 * 
 * <br>
 * <b>功能：</b>MemberContributedDetailController<br>
 * <b>作者：</b>www.jeecg.org<br>
 * <b>日期：</b> Feb 2, 2013 <br>
 * <b>版权所有：<b>版权所有(C) 2013，www.jeecg.org<br>
 */ 
@Controller
public class MemberContributedDetailController{
	
	private final static Logger log= Logger.getLogger(MemberContributedDetailController.class);
	
	@Autowired
	private MemberLevelService memberLevelService;
	
	// Servrice start
	@Autowired(required=false) //自动注入，不需要生成set方法了，required=false表示没有实现类，也不会报错。
	private MemberContributedDetailService memberContributedDetailService; 
	
	/**
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/memberContributedDetail_page") 
	public String memberContributedDetail_page(HttpServletRequest request, HttpServletResponse response) {
		log.info("MemberContributedDetail页面");
		return "member/memberContributedDetail" ;
	}
	
	
	/**
	 * 数据
	 * @param page
	 * @param rows
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/dataListMemberContributedDetail") 
	public void  datalist(int page, int rows, HttpServletRequest request, HttpServletResponse response) throws IOException{
		log.info("数据列表");
		response.setContentType("text/html;charset=utf-8");
		String citCode = request.getParameter("cityCode");
		String memberLevel = request.getParameter("memberLevel");
		String memberName = request.getParameter("memberName");
		String phone = request.getParameter("phone");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("cityCode", citCode);
		if (!StringUtils.isBlank(memberLevel)) {
			if("0".equals(memberLevel)){
				paramMap.put("memberLevel", memberLevel);
			}else {
				paramMap.put("memberLevel", Integer.parseInt(memberLevel));
			}
		}else {
			paramMap.put("memberLevel", null);
		}
		paramMap.put("memberName", memberName);
		paramMap.put("phone", phone);
		Map<String,Integer> pageInfo = Data2Jsp.getFristAndPageSize(page, rows);
		int from = pageInfo.get("first_page");
		int to = pageInfo.get("page_size");
		paramMap.put("from", from);
		paramMap.put("to", to);
		 
		Pager<MemberVo> pager = memberContributedDetailService.queryPageList(paramMap);
		response.getWriter().print(Data2Jsp.Json2Jsp(pager));
	}
	
	@SystemServiceLog(description="数据导出")
	@RequestMapping(value = "/export_contributed_report", method = { RequestMethod.GET , RequestMethod.POST })
	public List exportExecl(String cityCode,String memberLevel,String memberName,String phone,
			HttpServletRequest request,HttpServletResponse response) throws Exception {
		String fileName = "ContributedReport" ;
		//列名充电桩编码	
		String columnNames [] = {"所属城市","姓名", "手机号码", "会员等级", "贡献值" };
		
		String keys[] = {"cityName", "memberName", "phone","levelCode", "memberContributedVal"};
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("cityCode", cityCode);
		if (!StringUtils.isBlank(memberLevel)) {
			if("0".equals(memberLevel)){
				paramMap.put("memberLevel", memberLevel);
			}else {
				paramMap.put("memberLevel", Integer.parseInt(memberLevel));
			}
		}else {
			paramMap.put("memberLevel", null);
		}
		paramMap.put("memberName", memberName);
		paramMap.put("phone", phone);
		paramMap.put("from", null);
		paramMap.put("to", null);
		
		Pager<MemberVo> pager = memberContributedDetailService.queryPageList(paramMap);		
		List<MemberVo> datas = pager.getDatas();
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		Map<String, Object> sheetNameMap = new HashMap<String, Object>();
		sheetNameMap.put("sheetName", "贡献值明细数据报表");
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
			List<MemberVo> data) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map;// 避免重复创建对象
		String keys[] = {"cityName", "memberName", "phone","levelCode", "memberContributedVal"};
		for (MemberVo vo : data) {
			map = new HashMap<String, Object>();
			map.put(keys[0], vo.getCityName());
			map.put(keys[1], vo.getMemberName());
			map.put(keys[2], vo.getPhone());
			if(vo.getLevelCode() != null){
				switch (vo.getLevelCode()) {
				case 0:
					map.put(keys[3], "黑名单");
					break;
				case 1:
					map.put(keys[3], "一星会员");
					break;
				case 2:
					map.put(keys[3], "二星会员");
					break;
				case 3:
					map.put(keys[3], "三星会员");
					break;
				case 4:
					map.put(keys[3], "四星会员");
					break;
				case 5:
					map.put(keys[3], "五星会员");
					break;

				default:
					break;
				}
			}else{
				map.put(keys[3], vo.getLevelCode());
			}
			map.put(keys[4], vo.getMemberContributedVal());
			
			list.add(map);
		}
		return list;
	}
	
	/**
	 * 查询会员减少的贡献值明细
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping("/getDecreaseContributedDetail") 
	public void getDecreaseContributedDetail(int page, int rows, HttpServletRequest request, HttpServletResponse response) throws IOException {
		log.info("查询贡献值明细");
		response.setContentType("text/html;charset=utf-8");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		Map<String,Integer> pageInfo = Data2Jsp.getFristAndPageSize(page, rows);
		int from = pageInfo.get("first_page");
		int to = pageInfo.get("page_size");
		paramMap.put("from", from);
		paramMap.put("to", to);
		String memberId = request.getParameter("memberId");
		String behaviourName = request.getParameter("behaviourName");
		paramMap.put("memberId", memberId);
		paramMap.put("behaviourName", behaviourName);
		Pager<MemberContributedDetailVo> pager = memberContributedDetailService.queryDecreaseContributedDetail(paramMap);//查询会员减少的贡献值明细
		response.getWriter().print(Data2Jsp.Json2Jsp(pager));
		
	}
	
	/**
	 * 查询贡献值明细
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping("/getContributedDetail") 
	public void getContributedDetail(int page, int rows, HttpServletRequest request, HttpServletResponse response) throws IOException {
		log.info("查询贡献值明细");
		response.setContentType("text/html;charset=utf-8");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		Map<String,Integer> pageInfo = Data2Jsp.getFristAndPageSize(page, rows);
		int from = pageInfo.get("first_page");
		int to = pageInfo.get("page_size");
		paramMap.put("from", from);
		paramMap.put("to", to);
		String memberId = request.getParameter("memberId");
		String behaviourName = request.getParameter("behaviourName");
		paramMap.put("memberId", memberId);
		paramMap.put("behaviourName", behaviourName);
		Pager<MemberContributedDetailVo> pager = memberContributedDetailService.queryContributedDetail(paramMap);//查询会员减少的贡献值明细
		response.getWriter().print(Data2Jsp.Json2Jsp(pager));
		
	}
	
	/**
	 * 查询贡献值明细
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping("/sys_optional_memberLevelCombobox") 
	public String getMemberLevel(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html;charset=utf-8");
		List<MemberLevel> levels =memberLevelService.selectAllMemberLevel();
		List<Map<String,Object>> list  = new ArrayList<Map<String,Object>>();
		Map<String, Object> map = new HashMap<String, Object>();  
		map.put("id", -1); 
        map.put("text","全部");
        list.add(map);
		for (int i = 0; i < levels.size(); i++) {   
	        map = new HashMap<String, Object>();  
	        map.put("id", levels.get(i).getLevelCode()); 
	        map.put("text",levels.get(i).getName()); 
	        if (map != null){
	        	list.add(map);
	        }       
	    }   
		response.getWriter().print(JSON.toJSONString(list));
		return null;
	}
	/**
	 * 添加或修改数据
	 * @param MemberContributedDetail
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/saveOrUpdateMemberContributedDetail")
	public void saveOrUpdate(MemberContributedDetail entity,HttpServletRequest request, HttpServletResponse response) throws IOException{
		log.info("新增或更新");
		if(entity.getId()==null||StringUtils.isBlank(entity.getId().toString())){
			memberContributedDetailService.insert(entity);
		}else{
			memberContributedDetailService.updateByPrimaryKey(entity);
		}
		response.getWriter().print("success");
	}
	
	/**
	 * 删除数据
	 * @param 主键ID
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/deleteMemberContributedDetailById")
	public void delete(Integer id,HttpServletRequest request,HttpServletResponse response) throws IOException{
		log.info("删除");
		if(null!=id ) {
			memberContributedDetailService.deleteByPrimaryKey(id);
		}
		response.getWriter().print("success");
	}
}
