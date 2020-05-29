package com.iber.portal.controller;


import com.iber.portal.advices.SystemServiceLog;
import com.iber.portal.common.Data2Jsp;
import com.iber.portal.common.ExcelUtil;
import com.iber.portal.common.Pager;
import com.iber.portal.model.dispatch.Dispatcher;
import com.iber.portal.model.dispatch.DispatcherMember;
import com.iber.portal.model.dispatch.DispatcherTask;
import com.iber.portal.model.sys.SysUser;
import com.iber.portal.model.warns.WarnInfo;
import com.iber.portal.model.warns.WarnItem;
import com.iber.portal.mongo.search.MongoWarningLogSearch;
import com.iber.portal.service.dispatch.DispatchService;
import com.iber.portal.service.dispatch.DispatcherService;
import com.iber.portal.service.warns.WarnInfoService;
import com.iber.portal.service.warns.WarnInfoStatusService;
import com.iber.portal.service.warns.WarnItemService;
import com.iber.portal.util.DateTime;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @describe 
 * 
 * @author yangguiwu
 * @date 2016年03月31日
 */
@Controller
public class WarnInfoController extends MainController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
		

	@Autowired
	private WarnInfoService warnInfoService;	

	@Autowired
	private WarnInfoStatusService warnInfoStatusService;	

	@Autowired
	private DispatchService dispatchService;
	
	@Autowired
	private DispatcherService dispatcherService;
	
	@Autowired
	private WarnItemService warnItemService ;
	
	/**
	 * @describe 预警信息页面
	 * 
	 * @auther yangguiwu
	 * @date 2016年03月30日
	 * @throws Exception
	 */
	@SystemServiceLog(description="预警信息页面")
	@RequestMapping(value = "/warning_info", method = { RequestMethod.GET })
	public String warninginfo(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return "warns/warningInfo";		
	}
	@SystemServiceLog(description="预警信息列表")
	@RequestMapping(value = "/warning_info_list", method = { RequestMethod.GET, RequestMethod.POST })
	public String warningInfoList(int page, int rows, HttpServletRequest request, HttpServletResponse response) throws Exception{
		log.info("预警信息列表");
		response.setContentType("text/html;charset=utf-8");
		final String beginTime = request.getParameter("beginTime");
		final String endTime = request.getParameter("endTime");
		final String code = request.getParameter("item");
		final String lpn=request.getParameter("lpn");
		MongoWarningLogSearch search = new MongoWarningLogSearch();
		if (StringUtils.isNotBlank(beginTime)){
			search.setStartTime(DateTime.getTime(beginTime));
		}
		if (StringUtils.isNotBlank(endTime)){
			search.setEndTime(DateTime.getTime(endTime));
		}
		search.setWarnItemCode(code);
		search.setLpn(lpn);
		search.setPage(page);
		search.setRows(rows);
		search.setOrderField("createTime");
		search.setAsc(-1);
		List<WarnItem> warnItemList = warnItemService.selectAll() ;
		Pager<Map<String, Object>> pager = warnInfoService.selectWarnInfoList(search);
		List<Map<String,Object>> datas=pager.getDatas();
		for(int i=0;i<datas.size();i++){
			 Map map=datas.get(i);
			for(WarnItem wItem:warnItemList){
				 if(wItem.getItemCode().equals(map.get("warnItemCode"))){
					 map.put("warnItemCode",wItem.getItemName());
				 }
			}
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("total",pager.getTotalCount());
		jsonObject.put("rows",pager.getDatas());
		response.getWriter().print(jsonObject.toString());
		return null;
	}
    

	/**
	 * @describe 把预警信息转为调度任务
	 * 
	 */
	@SystemServiceLog(description="预警信息转为调度任务")
	@RequestMapping(value = "/change_dispatcher_task", method = { RequestMethod.GET , RequestMethod.POST })
	public String changeDispatcherTask(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("text/html;charset=utf-8");
		
		SysUser user = (SysUser) request.getSession().getAttribute("user");		
		
		String warnContent = request.getParameter("warnContent");
		String srcAlertId = request.getParameter("id");	
		DispatcherTask obj = new DispatcherTask();
		obj.setTaskDesc(warnContent);
		obj.setStatus("0");
		obj.setCreateTime(new Date());
		obj.setSrcAlertId(Integer.parseInt(srcAlertId));
		obj.setCreateBy(user.getAccount());
		dispatchService.insertSelective(obj);
		
		
		
		response.getWriter().print("success");
		return null;	
	}
	
	@SystemServiceLog(description="预警信息导出excel链接")
	@RequestMapping(value = "/warning_info_excel", method = RequestMethod.GET)
	public void exportWarnInfoExcel(String beginTime, String endTime,String item,String lpn,Integer total, HttpServletResponse response)
	throws Exception {
		String fileName = "warningInfoList";
		//填充reports数据

		MongoWarningLogSearch search = new MongoWarningLogSearch();
		if (StringUtils.isNotBlank(beginTime)){
			search.setStartTime(DateTime.getTime(beginTime));
		}
		if (StringUtils.isNotBlank(endTime)){
			search.setEndTime(DateTime.getTime(endTime));
		}
		search.setPage(0);
		search.setRows(total);
		if (item != null && item.length() <= 2){
			item = "";
		}
		search.setWarnItemCode(item);
		search.setLpn(lpn);
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		Map<String, Object> sheetNameMap = new HashMap<String, Object>();
		sheetNameMap.put("sheetName", "预警信息报表");
		list.add(sheetNameMap);
        List<Map<String, Object>> listWanrn=warnInfoService.selectWarnInfos(search);

		List<WarnItem> warnItemList = warnItemService.selectAll() ;
		for(int i=0;i<listWanrn.size();i++){
			Map map=listWanrn.get(i);
			map.put("createTime",convertTimeMillis(map.get("createTime").toString()));
			for(WarnItem wItem:warnItemList){
				if(wItem.getItemCode().equals(map.get("warnItemCode"))){
					System.out.println(map.get("warnItemCode"));
					map.put("warnItemCode",wItem.getItemName());
				}
			}
		}
        list.addAll(listWanrn);
		if (CollectionUtils.isEmpty(list)) return ;
		String columnNames[] = {"预警信息", "类型", "时间", "状态"};//列名
		String keys[] = {"warnContent", "warnItemCode", "createTime", "isRead"};//map中的key
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try {
			ExcelUtil.createWorkBook(list, keys, columnNames).write(os);
		} catch (IOException e) {
			e.printStackTrace();
		}
		byte[] content = os.toByteArray();
		InputStream is = new ByteArrayInputStream(content);
		// 设置response参数，可以打开下载页面
		response.reset();
		response.setContentType("application/vnd.ms-excel;charset=utf-8");
		response.setHeader("Content-Disposition", "attachment;filename=" + new String((fileName + ".xls").getBytes(), "iso-8859-1"));
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

    //时间戳转日期
	static Pattern pattern = Pattern.compile("[0-9]{1,}");
    private  static String  convertTimeMillis(String timeMillis){
		
		Matcher matcher = pattern.matcher((CharSequence) timeMillis);
		boolean bool=matcher.matches();
		String str ="";
		if(bool){
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//这个是你要转成后的时间的格式
			 str= sdf.format(new Date(Long.parseLong(String.valueOf(timeMillis))));   // 时间戳转换成时间
		}
		return str;
	}



	private List<WarnInfo> createWarnInfoData(Map<String,String> map) {
    	List<WarnInfo> obj =warnInfoService.selectByPrimaryKeyExcel(map);
		return obj;
    }
    private List<Map<String, Object>> createWarnInfoExcelRecord(List<WarnInfo> reports) {
        List<Map<String, Object>> listmap = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("sheetName", "sheet1");
        listmap.add(map);
        WarnInfo report=null;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (int j = 0; j < reports.size(); j++) {
        	report=reports.get(j);
            Map<String, Object> mapValue = new HashMap<String, Object>();
            mapValue.put("warnContent", report.getWarnContent());
            mapValue.put("itemName", report.getItemName());
            mapValue.put("createTime", formatter.format(report.getCreateTime()));
            if(report.getStatus()==null){
            	  mapValue.put("status", "未读");
            }else{
            	 mapValue.put("status", "已读");	
            }
            
            listmap.add(mapValue);
        }
        return listmap;
    }  	
		


	/**
	 * @describe 预警信息状态更改为已读
	 * 
	 */
    @SystemServiceLog(description="预警信息状态更改为已读")
	@RequestMapping(value = "/warning_mark_read", method = { RequestMethod.GET , RequestMethod.POST })
	public String addEvaluateLabel(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("text/html;charset=utf-8");
		String[] ids = request.getParameterValues("id");
		if (ArrayUtils.isNotEmpty(ids)) {
			for (int i = 0, len = ids.length; i < len; i++) {
				// 重新插入(更新)
				warnInfoService.updateReadStatus(ids[i],1);
			}
		}
		response.getWriter().print("success");
		return null;
	}

    @SystemServiceLog(description="预警信息总数")
	@RequestMapping(value = "/warn_remind_total", method = { RequestMethod.GET, RequestMethod.POST })
	public String warnRemindTotal(HttpServletResponse response) throws Exception{
        response.setContentType("text/html;charset=utf-8");
        long now = System.currentTimeMillis();
        long start = now - 600 * 1000;
        MongoWarningLogSearch search = new MongoWarningLogSearch();
        search.setStartTime(start);
        search.setEndTime(now);
        search.setIsRead(0);// 未读
        response.getWriter().print(warnInfoService.selectWarnRemindTotal(search));
        return null;
	}


	/**
	 * @describe 10分钟内全部状态更改为已读
	 * 
	 */
    @SystemServiceLog(description="10分钟内全部状态更改为已读")
	@RequestMapping(value = "/all_mark_read", method = { RequestMethod.GET , RequestMethod.POST })
	public String allMarkRead(HttpServletResponse response)
			throws Exception {
		response.setContentType("text/html;charset=utf-8");
		long now = System.currentTimeMillis();
		warnInfoService.updateAllReadStatus(now - 600000,now);
		response.getWriter().print("success");
		return null;
	}	

	/**
	 * @describe 调度任务页面
	 * 
	 * @date 2016年03月30日
	 * @throws Exception
	 */
    @SystemServiceLog(description="调度任务页面")
	@RequestMapping(value = "/dispatch_task", method = { RequestMethod.GET })
	public String dispatchTask(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return "dispatch/dispatchTask";		
	}


    @SystemServiceLog(description="调度任务列表")
	@RequestMapping(value = "/dispatch_task_list", method = { RequestMethod.GET, RequestMethod.POST })
	public String dispatchTaskList(int page, int rows, HttpServletRequest request, HttpServletResponse response) throws Exception{
        response.setContentType("text/html;charset=utf-8");
 
        HashMap<String, Object> record = new  HashMap<String, Object>();
 
        Date now=new Date();
        Date date = new Date(now.getTime() - 24*3600 * 1000);
        DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
       
        String nowBeginTime=format.format(date);   
        
        String nowEndTime=format.format(now);      
        
        if(!StringUtils.isBlank(request.getParameter("beginTime"))){
        	
        	record.put("beginTime", request.getParameter("beginTime"));
        }else{
        	
        	record.put("beginTime", nowBeginTime);
        }
        
        if(!StringUtils.isBlank(request.getParameter("endTime"))){

        	record.put("endTime", request.getParameter("endTime"));
        
        }else{
        	
        	record.put("endTime", nowEndTime);
        }
        
        if(!StringUtils.isBlank(request.getParameter("status"))){
        	
        	record.put("status", request.getParameter("status"));
        
        }
            
        Map<String,Integer> pageInfo = Data2Jsp.getFristAndPageSize(page, rows);
        record.put("page", pageInfo.get("first_page"));
        record.put("size", pageInfo.get("page_size"));
        List<DispatcherTask> data = dispatchService.selectByDispatcherTaskList(record);
        JSONObject obj = new JSONObject();
        obj.put("total", dispatchService.selectByWarnInfoListRecords(record));
		obj.put("rows", data);
		response.getWriter().print(obj.toString());
		
		return null;

	}





    @SystemServiceLog(description="调度任务列表导出excel链接")
	@RequestMapping(value = "/dispatcher_task_excel", method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public List<DispatcherTask> exportDispatcherTaskExcel(String beginTime,String endTime,String status,HttpServletRequest request, HttpServletResponse response)
	throws Exception {
        String fileName="dispatcherTaskList";
        //填充reports数据
        	
        Map<String,String> map=new HashMap<String,String>();
		
        map.put("beginTime", beginTime);	
		map.put("endTime", endTime);
		map.put("status", status);
        List<DispatcherTask> reports=createDispatcherTaskData(map);
        List<Map<String,Object>> list=createDispatcherTaskExcelRecord(reports);
        String columnNames[]={"调度信息","执行人员","时间","状态"};//列名
        String keys[]    =     {"taskDesc","dispatcherName","createTime","status"};//map中的key
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            ExcelUtil.createWorkBook(list,keys,columnNames).write(os);
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] content = os.toByteArray();
        InputStream is = new ByteArrayInputStream(content);
        // 设置response参数，可以打开下载页面
        response.reset();
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename="+ new String((fileName + ".xls").getBytes(), "iso-8859-1"));
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

    private List<DispatcherTask> createDispatcherTaskData(Map<String,String> map) {
    	List<DispatcherTask> obj =dispatchService.selectByPrimaryKeyExcel(map);
		return obj;
    }
    private List<Map<String, Object>> createDispatcherTaskExcelRecord(List<DispatcherTask> reports) {
        List<Map<String, Object>> listmap = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("sheetName", "sheet1");
        listmap.add(map);
        DispatcherTask report=null;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (int j = 0; j < reports.size(); j++) {
        	report=reports.get(j);
            Map<String, Object> mapValue = new HashMap<String, Object>();
            mapValue.put("taskDesc", report.getTaskDesc());
            mapValue.put("dispatcherName", report.getDispatcherName());
            mapValue.put("createTime", formatter.format(report.getCreateTime()));
            if("0".equals(report.getStatus())){
            	mapValue.put("status", "新建");
            	}else if ("1".equals(report.getStatus())){
            	 	mapValue.put("status", "已指派进行中");	
            	}else if("2".equals(report.getStatus())){
            	 	mapValue.put("status", "完成");		
            	}else{
            	mapValue.put("status", " ");	
            }  
            
            listmap.add(mapValue);
        }
        return listmap;
    }
	
	
	
	/**
	 * @describe 添加修改调度任务
	 * 
	 */
    @SystemServiceLog(description="添加修改调度任务")
	@RequestMapping(value = "/add_dispatcher_task", method = { RequestMethod.GET , RequestMethod.POST })
	public String addDispatcherTask(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("text/html;charset=utf-8");
		
		SysUser user = (SysUser) request.getSession().getAttribute("user");		
		
		String id = request.getParameter("id");
		String dispatcherId = request.getParameter("dispatcherId");
		String taskDesc = request.getParameter("taskDesc");
		String status = request.getParameter("modStatus");
		String orderId = request.getParameter("orderId");
		
		if (id!=null && !id.equals("")) {
			DispatcherTask currObj = dispatchService.selectByPrimaryId(Integer.parseInt(id));
			if (currObj!=null) {
				currObj.setDispatcherId(Integer.parseInt(dispatcherId));
				currObj.setTaskDesc(taskDesc);
				currObj.setStatus(status);
				currObj.setCreateTime(new Date());
				currObj.setOrderId(orderId);
				currObj.setCreateBy(user.getAccount());
				dispatchService.updateByPrimaryKeySelective(currObj);
			}
			
		}else{	
			DispatcherTask obj = new DispatcherTask();
			obj.setDispatcherId(Integer.parseInt(dispatcherId));
			obj.setTaskDesc(taskDesc);
			obj.setStatus(status);
			obj.setCreateTime(new Date());
			obj.setOrderId(orderId);
			obj.setCreateBy(user.getAccount());
			dispatchService.insertSelective(obj);
		}
		
		response.getWriter().print("success");
		return null;	
	}   
    

    /**
	 * @describe 获取执行人员列表
	 * 
	 * @date 2016年05月24日
	 * @throws Exception
	 */
    @SystemServiceLog(description="获取执行人员列表")
	@RequestMapping(value = "/dispatcher_list", method = { RequestMethod.GET, RequestMethod.POST})
	public String dispatcherList(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("text/html;charset=utf-8");
		HashMap<String, Object> record = new  HashMap<String, Object>();	
		
		List<Dispatcher> dispatcherList = dispatcherService.selectByDispatcherType(record);		
		JSONArray dispatcherListJson = JSONArray.fromObject(dispatcherList);
		response.getWriter().print(dispatcherListJson.toString());
		return null;	
	}
	

	/**
	 * @describe 调度员管理页面
	 * 
	 * @date 2016年03月30日
	 * @throws Exception
	 */
    @SystemServiceLog(description="调度员管理页面")
	@RequestMapping(value = "/dispatcher_page", method = { RequestMethod.GET })
	public String dispatcherPage(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return "dispatch/dispatcherPage";		
	}


    @SystemServiceLog(description="调度员管理列表")
	@RequestMapping(value = "/dispatcher_page_list", method = { RequestMethod.GET, RequestMethod.POST })
	public String dispatcherPageList(int page, int rows, HttpServletRequest request, HttpServletResponse response) throws Exception{
        response.setContentType("text/html;charset=utf-8");
 
        HashMap<String, Object> record = new  HashMap<String, Object>();
    
        
        if(!StringUtils.isBlank(request.getParameter("memberName"))){

        	record.put("memberName", request.getParameter("memberName"));
        
        }
       
        Map<String,Integer> pageInfo = Data2Jsp.getFristAndPageSize(page, rows);
        record.put("page", pageInfo.get("first_page"));
        record.put("size", pageInfo.get("page_size"));
        List<Dispatcher> data = dispatcherService.selectByDispatcherPageList(record);
        JSONObject obj = new JSONObject();
        obj.put("total", dispatcherService.selectByDispatcherPageListRecords(record));
		obj.put("rows", data);
		response.getWriter().print(obj.toString());
		
		return null;

	}



    /**
	 * @describe 获取通过审核会员列表
	 * 
	 * @date 2016年05月24日
	 * @throws Exception
	 */
    @SystemServiceLog(description="获取通过审核会员列表")
	@RequestMapping(value = "/dispatcher_member_type", method = { RequestMethod.GET, RequestMethod.POST})
	public String memberPassList(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("text/html;charset=utf-8");
		HashMap<String, Object> record = new  HashMap<String, Object>();	
		
		List<DispatcherMember> dispatcherMemberList = dispatcherService.selectByDispatcherMemberType(record);		
		JSONArray dispatcherMemberListJson = JSONArray.fromObject(dispatcherMemberList);
		response.getWriter().print(dispatcherMemberListJson.toString());
		return null;	
	}


	/**
	 * @describe 添加修改调度员
	 * 
	 */
    @SystemServiceLog(description="添加修改调度员")
	@RequestMapping(value = "/add_dispatcher", method = { RequestMethod.GET , RequestMethod.POST })
	public String addDispatcher(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("text/html;charset=utf-8");
			
		
		String id = request.getParameter("id");
		String memberId = request.getParameter("memberId");
		String isEnable = request.getParameter("isEnable");
		String comment = request.getParameter("comment");
		
		if (id!=null && !id.equals("")) {
			Dispatcher currObj = dispatcherService.selectByPrimaryId(Integer.parseInt(id));
			if (currObj!=null) {
				currObj.setMemberId(Integer.parseInt(memberId));
				currObj.setIsEnable(Integer.parseInt(isEnable));
				currObj.setComment(comment);
				currObj.setCreateTime(new Date());
				dispatcherService.updateByPrimaryKeySelective(currObj);
			}
			
		}else{	
			Dispatcher obj = new Dispatcher();
			obj.setMemberId(Integer.parseInt(memberId));
			obj.setIsEnable(Integer.parseInt(isEnable));
			obj.setComment(comment);
			obj.setCreateTime(new Date());
			dispatcherService.insertSelective(obj);
		}
		
		response.getWriter().print("success");
		return null;	
	} 
    @SystemServiceLog(description="警报类型")
	@RequestMapping(value = "/warn_item", method = { RequestMethod.GET, RequestMethod.POST})
	public String warn_item(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("text/html;charset=utf-8");	
		List<WarnItem> warnItemList = warnItemService.selectAll() ;
		StringBuffer tree=new StringBuffer();		
		tree.append("[");
		if(warnItemList!=null && warnItemList.size()>0) {				
			for(int i=0;i<warnItemList.size();i++ ){
				WarnItem warnItem=warnItemList.get(i);
				tree.append("{");
				tree.append("\"id\":\""+warnItem.getItemCode()+"\",");
				tree.append("\"text\":\""+warnItem.getItemName()+"\"");
				if(i<warnItemList.size()-1){
					tree.append("},");
				}else{
					tree.append("}");
				}
			}
		}
		tree.append("]");
		response.getWriter().print(tree.toString());
		return null;	
	}
}






