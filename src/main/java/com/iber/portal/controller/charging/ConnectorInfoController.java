package com.iber.portal.controller.charging;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;

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
import com.iber.portal.controller.MainController;
import com.iber.portal.model.charging.ChargingPile;
import com.iber.portal.model.charging.ConnectorInfo;
import com.iber.portal.model.charging.ConnectorStatusInfo;
import com.iber.portal.model.sys.SysUser;
import com.iber.portal.service.charging.ConnectorInfoService;
import com.iber.portal.service.charging.ConnectorStatusInfoService;
import com.iber.portal.service.charging.EquipmentInfoService;
import com.iber.portal.vo.charging.ConnectorInfoVo;


@Controller
public class ConnectorInfoController extends MainController{ 
	
	private final static Logger log= Logger.getLogger(ConnectorInfoController.class);
	
	// Servrice start
	@Autowired(required=false) //自动注入，不需要生成set方法了，required=false表示没有实现类，也不会报错。
	private ConnectorInfoService connectorInfoService; 
	
	@Autowired(required=false)
	private EquipmentInfoService equipmentInfoService;
	
	@Autowired(required=false)
	private ConnectorStatusInfoService connectorStatusInfoService;
	/**
	 * @return
	 * @throws Exception 
	 */
	@SystemServiceLog(description="ConnectorInfo页面")
	@RequestMapping("/connectorInfo_page") 
	public String connectorInfo_page(HttpServletRequest request, HttpServletResponse response) {
		log.info("ConnectorInfo页面");
		return "charging/connectorInfo" ;
	}
	
	
	/**
	 * 充电设备接口信息数据列表 
	 */
	@SystemServiceLog(description="充电设备接口信息数据列表")
	@RequestMapping("/dataListConnectorInfo") 
	public void  datalist(int page, int rows, HttpServletRequest request, HttpServletResponse response) throws IOException{
		log.info("充电设备接口信息数据列表");
		SysUser user = (SysUser) getUser(request) ;
		response.setContentType("text/html;charset=utf-8");
		Map<String,Integer> pageInfo = Data2Jsp.getFristAndPageSize(page, rows);
		int from = pageInfo.get("first_page");
		int to = pageInfo.get("page_size");
		
		//获取其他查询参数
		//String title = request.getParameter("title");
		String cityCode = user.getCityCode();
		String connectorName = request.getParameter("connectorName");
		String  equipmentCode  =request.getParameter("equipmentCode");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("from", from);
		paramMap.put("to", to);
		paramMap.put("equipmentCode",equipmentCode);
		paramMap.put("connectorName",connectorName);
		if(cityCode.equals("00")){
	    	paramMap.put("cityCode", null);
        }else{
        	paramMap.put("cityCode", cityCode);
        }
		Pager<ConnectorInfo> pager = connectorInfoService.queryPageList(paramMap);
		response.getWriter().print(Data2Jsp.Json2Jsp(pager));
	}
	
	@SystemServiceLog(description="充电枪报表导出")
	@RequestMapping(value = "/export_connector_report", method = { RequestMethod.GET , RequestMethod.POST })
	public List<ChargingPile> exportPileReportExecl(String connectorName,String equipmentCode,
			HttpServletRequest request,HttpServletResponse response) throws Exception {
		String fileName = "ConnectorInfoReport" ;
		//列名充电桩编码	
		String columnNames [] = { "充电桩编码", "充电枪编码", "充电枪编号",  "充电枪名称", "充电枪类型",
				"额定电压上限(V)", "额定电压下限(V)", "额定电流(A)", "额定功率(KW)", "车位号","地锁编码","是否有地锁"};
		
		String keys[] = { "equipmentCode", "connectorCode", "connectorNo",
				"connectorName", "connectorType", "voltageUpperLimits", "voltageLowerLimits", "current",
				"power", "parkNo","lockCode","lockStatus" };
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("connectorName", connectorName);
		map.put("equipmentCode", equipmentCode);
		map.put("from", null);
		map.put("to", null);
		Pager<ConnectorInfo> pager = connectorInfoService.queryPageList(map);
		List<ConnectorInfo> datas = pager.getDatas();
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		Map<String, Object> sheetNameMap = new HashMap<String, Object>();
		sheetNameMap.put("sheetName", "充电枪报表");
		list.add(sheetNameMap);
		list.addAll(createData(datas));
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
	
	private List<Map<String, Object>> createData(
			List<ConnectorInfo> data) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map;// 避免重复创建对象
		String keys[] = { "equipmentCode", "connectorCode", "connectorNo",
				"connectorName", "connectorType", "voltageUpperLimits", "voltageLowerLimits", "current",
				"power", "parkNo","lockCode","lockStatus" };
		for (ConnectorInfo connectorInfo : data) {
			map = new HashMap<String, Object>();
			map.put(keys[0], connectorInfo.getEquipmentCode());
			map.put(keys[1], connectorInfo.getConnectorCode());
			if(connectorInfo.getConnectorNo().equals("1")){
				map.put(keys[2], "A");
			}else if(connectorInfo.getConnectorNo().equals("2")){
				map.put(keys[2], "B");
			}
			map.put(keys[3], connectorInfo.getConnectorName());
			Integer connectorType = connectorInfo.getConnectorType();
			switch (connectorType) {
			case 1:
				map.put(keys[4], "家用插座");
				break;
			case 2:
				map.put(keys[4], "交流接口插座");
				break;
			case 3:
				map.put(keys[4], "交流接口插头");
				break;
			case 4:
				map.put(keys[4], "直流接口枪头");
				break;
			}
			map.put(keys[5], connectorInfo.getVoltageUpperLimits());
			map.put(keys[6], connectorInfo.getVoltageLowerLimits());
			map.put(keys[7], connectorInfo.getCurrent());
			map.put(keys[8], connectorInfo.getPower());
			map.put(keys[9], connectorInfo.getParkNo());
			map.put(keys[10], connectorInfo.getLockCode());
			int lockStatus = Integer.parseInt(connectorInfo.getLockStatus());
			switch (lockStatus) {
			case 0:
				map.put(keys[11], "否");
				break;
			case 1:
				map.put(keys[11], "是");
				break;
			}
			list.add(map);
		}
		return list;
	}
	/**
	 * 添加或修改数据
	 */
	@SystemServiceLog(description="充电接口新增或更新")
	@RequestMapping("/saveOrUpdateConnectorInfo")
	public void saveOrUpdate(ConnectorInfo entity,HttpServletRequest request, HttpServletResponse response) throws IOException{
		log.info("充电接口新增或更新");
		//Integer lockStatus=Integer.parseInt(request.getParameter("lockStatus"));
		String lockCode =request.getParameter("lockCode");
		String lockStatus =request.getParameter("lockStatus");
		SysUser user = (SysUser) getUser(request) ;
		if(entity.getId()==null||StringUtils.isBlank(entity.getId().toString())){
			entity.setCreateTime(new Date());
			entity.setCreateId(user.getId());
			entity.setUpdateId(user.getId());
			entity.setUpdateTime(new Date());
			//EquipmentCode参数是combogrid选中的传递的是equipementid主键值
			entity.setEquipmentId(Integer.valueOf(entity.getEquipmentCode()));
			entity.setLockCode(lockCode);
			connectorInfoService.insertSelective(entity);
			//新建接口时默认生成 接口状态初始值
			int id = entity.getId();
			//equipementid
			Date now = new Date();
			ConnectorStatusInfo record = new ConnectorStatusInfo();
			record.setCreateTime(now);
			record.setCreateId(user.getId());
			record.setUpdateId(user.getId());
			record.setUpdateTime(now);
			record.setConnectorId(id);
			record.setStatus(0);
			record.setParkStatus(0);
			Integer lockStatus2 = Integer.valueOf(lockStatus);
			record.setLockStatus(lockStatus2);
			record.setConnectorCode(entity.getConnectorCode());
			connectorStatusInfoService.insertSelective(record);
			
		}else{
			entity.setCreateTime(new Date());
			entity.setCreateId(user.getId());
			entity.setUpdateTime(new Date());
			entity.setUpdateId(user.getId());
			entity.setLockCode(lockCode);
			connectorInfoService.updateByPrimaryKeySelective(entity);
			ConnectorStatusInfo record = new ConnectorStatusInfo();
			int id = entity.getId();
			Date now = new Date();
			record.setConnectorId(id);
			entity.setCreateTime(new Date());
			entity.setCreateId(user.getId());
			record.setUpdateTime(now);
			record.setUpdateId(user.getId());
			record.setStatus(0);
			record.setParkStatus(0);
			record.setLockStatus(Integer.parseInt(lockStatus));
			record.setConnectorCode(entity.getConnectorCode());
			connectorStatusInfoService.updateByStatusKey(record);
		}
		response.getWriter().print("success");
	}
	
	/**
	 * 删除数据
	 * @param 主键ID
	 * @return
	 * @throws IOException 
	 */
	@SystemServiceLog(description="充电接口删除数据")
	@RequestMapping("/deleteConnectorInfoById")
	public void delete(Integer id,HttpServletRequest request,HttpServletResponse response) throws IOException{
		log.info("删除");
		if(null!=id ) {
			int sum=connectorInfoService.deleteByPrimaryKey(id);
			if(sum>0){//删除接口以后 删除对于的接口状态信息
				connectorStatusInfoService.deleteByPrimaryKey(id);
			};
		}
		response.getWriter().print("success");
	}
	
	
	/**接口状态附加列表*/
	@SystemServiceLog(description="接口状态附加列表")
	@RequestMapping(value = "/connectorStatus_attachment_preview", method = { RequestMethod.GET, RequestMethod.POST})
	public void  equipmentlist(HttpServletRequest request, HttpServletResponse response) throws IOException{
		log.info("接口状态数据列表");
		//获取其他查询参数
		//String title = request.getParameter("title");
		response.setContentType("text/html;charset=utf-8");	
		String id = request.getParameter("id");
		Pager<ConnectorStatusInfo> pager = connectorStatusInfoService.queryById(Integer.parseInt(id));
		response.getWriter().print(Data2Jsp.Json2Jsp(pager));
	}
}
