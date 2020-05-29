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

import org.apache.commons.lang.StringUtils;

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
import com.iber.portal.model.employee.RescuerEmployeeInfo;
import com.iber.portal.model.member.MemberBlacklistLog;
import com.iber.portal.model.sys.SysUser;
import com.iber.portal.service.member.MemberBlacklistLogService;

/**
 * 
 * <br>
 * <b>功能：</b>MemberBlacklistLogController<br>
 * <b>作者：</b>www.jeecg.org<br>
 * <b>日期：</b> Feb 2, 2013 <br>
 * <b>版权所有：<b>版权所有(C) 2013，www.jeecg.org<br>
 */ 
@Controller
public class MemberBlacklistLogController{
	
	private final static Logger log= Logger.getLogger(MemberBlacklistLogController.class);
	
	// Servrice start
	@Autowired(required=false) //自动注入，不需要生成set方法了，required=false表示没有实现类，也不会报错。
	private MemberBlacklistLogService memberBlacklistLogService; 
	
	/**
	 * @return
	 * @throws Exception 
	 */
	@SystemServiceLog(description=" x 页面")
	@RequestMapping("/memberBlacklistLog_page") 
	public String memberBlacklistLog_page(HttpServletRequest request, HttpServletResponse response) {
		log.info("MemberBlacklistLog页面");
		return "member/memberBlacklistLog" ;
	}
	
	
	/**
	 * 数据
	 * @param page
	 * @param rows
	 * @return
	 * @throws IOException 
	 */
	@SystemServiceLog(description=" x 数据")
	@RequestMapping("/dataListMemberBlacklistLog") 
	public void  datalist(int page, int rows, HttpServletRequest request, HttpServletResponse response) throws IOException{
		log.info("数据列表");
		response.setContentType("text/html;charset=utf-8");
		Map<String,Integer> pageInfo = Data2Jsp.getFristAndPageSize(page, rows);
		int from = pageInfo.get("first_page");
		int to = pageInfo.get("page_size");
		
		//获取其他查询参数
		String name = request.getParameter("name");
		String phone = request.getParameter("phone");
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("from", from);
		paramMap.put("to", to);
		paramMap.put("name", name);
		paramMap.put("phone", phone);
		 
		Pager<MemberBlacklistLog> pager = memberBlacklistLogService.queryPageList(paramMap);
		response.getWriter().print(Data2Jsp.Json2Jsp(pager));
	}
	
	
	@SystemServiceLog(description="数据导出")
	@RequestMapping(value = "/export_blockListLog_report", method = { RequestMethod.GET , RequestMethod.POST })
	public List exportExecl(String name,String phone,
			HttpServletRequest request,HttpServletResponse response) throws Exception {
		String fileName = "BlockListLogReport" ;
		//列名充电桩编码	
		String columnNames [] = {"姓名","手机号码", "操作", "理由", "创建人", "创建时间" };
		
		String keys[] = {"memberName", "memberPhone", "operate","reason", "createName","createTime"};
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("from", null);
		paramMap.put("to", null);
		paramMap.put("name", name);
		paramMap.put("phone", phone);
		
		Pager<MemberBlacklistLog> pager = memberBlacklistLogService.queryPageList(paramMap);
		
		List<MemberBlacklistLog> datas = pager.getDatas();
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		Map<String, Object> sheetNameMap = new HashMap<String, Object>();
		sheetNameMap.put("sheetName", "黑名单日志数据报表");
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
			List<MemberBlacklistLog> data) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map;// 避免重复创建对象
		String keys[] = {"memberName", "memberPhone", "operate","reason", "createName","createTime"};
		for (MemberBlacklistLog memberLog : data) {
			map = new HashMap<String, Object>();
			map.put(keys[0], memberLog.getMemberName());
			map.put(keys[1], memberLog.getMemberPhone());
			if(memberLog.getOperate() != null){
				if(memberLog.getOperate() == 1){
					 map.put(keys[2], "撤销黑名单");
				}else{
					map.put(keys[2], "列入黑名单");
				} 
 			}else{
 				map.put(keys[2], memberLog.getOperate());
 			}
			map.put(keys[3], memberLog.getReason());
			map.put(keys[4], memberLog.getCreateName());
			map.put(keys[5], memberLog.getCreateTime()!=null?new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(memberLog.getCreateTime()):"");
			list.add(map);
		}
		return list;
	}
	/**
	 * 添加或修改数据
	 * @param MemberBlacklistLog
	 * @return
	 * @throws IOException 
	 */
	@SystemServiceLog(description=" x 添加或修改数据")
	@RequestMapping("/saveOrUpdateMemberBlacklistLog")
	public void saveOrUpdate(MemberBlacklistLog entity,HttpServletRequest request, HttpServletResponse response) throws IOException{
		log.info("新增或更新");
		if(entity.getId()==null||StringUtils.isBlank(entity.getId().toString())){
			memberBlacklistLogService.insert(entity);
		}else{
			memberBlacklistLogService.updateByPrimaryKey(entity);
		}
		response.getWriter().print("success");
	}
	
	/**
	 * 删除数据
	 * @param 主键ID
	 * @return
	 * @throws IOException 
	 */
	@SystemServiceLog(description=" x 删除数据")
	@RequestMapping("/deleteMemberBlacklistLogById")
	public void delete(Integer id,HttpServletRequest request,HttpServletResponse response) throws IOException{
		log.info("删除");
		if(null!=id ) {
			memberBlacklistLogService.deleteByPrimaryKey(id);
		}
		response.getWriter().print("success");
	}
}
