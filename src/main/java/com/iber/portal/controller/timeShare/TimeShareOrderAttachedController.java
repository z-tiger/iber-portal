package com.iber.portal.controller.timeShare;

import com.iber.portal.advices.SystemServiceLog;
import com.iber.portal.common.Data2Jsp;
import com.iber.portal.common.ExcelUtil;
import com.iber.portal.common.OSSClientAPI;
import com.iber.portal.common.Pager;
import com.iber.portal.controller.MainController;
import com.iber.portal.enums.PayStatusEnum;
import com.iber.portal.enums.PayTypeEnum;
import com.iber.portal.model.car.CarRescue;
import com.iber.portal.model.charging.ChargingPile;
import com.iber.portal.model.sys.SysUser;
import com.iber.portal.model.timeShare.TimeShareOrder;
import com.iber.portal.model.timeShare.TimeShareOrderAttached;
import com.iber.portal.service.timeShare.TimeShareOrderAttachedService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

@Controller
public class TimeShareOrderAttachedController extends MainController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TimeShareOrderAttachedService timeShareOrderAttachedService;

    /**
     * @describe 当前订单页面
     *
     */
    @SystemServiceLog(description="附属订单页面")
    @RequestMapping(value = "/order_attached_page", method = { RequestMethod.GET })
    public String orderAttachedPage(HttpServletRequest request,
                                           HttpServletResponse response) throws Exception {
        log.info("附属订单页面跳转");
        return "timeShare/orderAttachedlist";
    }

    /**
     * 附属订单列表
     * @param orderId
     * @param page
     * @param rows
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @SystemServiceLog(description="附属订单列表")
    @RequestMapping(value = "/page_orderattached_list", method = { RequestMethod.GET , RequestMethod.POST })
    public String pageOrderAttachedList(String orderId, Integer page, Integer rows, HttpServletRequest request, HttpServletResponse response) {
        try {
            response.setContentType("text/html;charset=utf-8");
            int p = (page == null) ? 1 : page;
            int r = (rows == null) ? 100 : rows;
            Map<String,Integer> pageInfo = Data2Jsp.getFristAndPageSize(p, r);
            Map<String,Object> record = new HashMap<String, Object>() ;
            record.put("offset", pageInfo.get("first_page"));
            record.put("rows", pageInfo.get("page_size"));
            if(!StringUtils.isBlank(request.getParameter("queryDateFrom"))){
                String startTime = request.getParameter("queryDateFrom");
                record.put("queryDateFrom", startTime);
            }
            if(!StringUtils.isBlank(request.getParameter("queryDateTo"))){
                String endTime = request.getParameter("queryDateTo");
                record.put("queryDateTo", endTime);
            }
            record.put("memberName",request.getParameter("memberName"));
            record.put("cityCode",request.getParameter("cityCode"));
            record.put("lpn",request.getParameter("lpn"));
            record.put("phoneNumber",request.getParameter("phoneNumber"));
            record.put("ispay",request.getParameter("ispay"));
            record.put("type",request.getParameter("type"));
            record.put("orderId",request.getParameter("orderId"));
            record.put("toUser",request.getParameter("toUser"));
            Pager<HashMap> pager = timeShareOrderAttachedService.findPage(record);
            response.getWriter().print(Data2Jsp.Json2Jsp(pager));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @SystemServiceLog(description="数据导出")
	@RequestMapping(value = "/export_orderAttached_list", method = { RequestMethod.GET , RequestMethod.POST })
	public List<ChargingPile> exportExecl(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String fileName = "OrderAttachedReport" ;
		//列名充电桩编码	
		String columnNames [] = {"订单编号", "会员姓名",  "手机号码", "责任类型", "创建原因",
				"车牌号","订单类型","创建时间","创建人","城市编码","车辆品牌","应付额（元）","支付类型","支付状态","支付完成时间"};
		
		String keys[] = { "orderId", "membername","phone", 
				"toUser", "reason", "lpn", "type",
				"createtime","creater","city","brandName","ordermoney","paytype","paystatus","completiontime" };
		Map<String, Object> record = new HashMap<String, Object>();
		String orderId = request.getParameter("orderId");
		record.put("memberName",request.getParameter("memberName"));
        record.put("cityCode",request.getParameter("cityCode"));
	     record.put("lpn",request.getParameter("lpn"));
	     record.put("phoneNumber",request.getParameter("phoneNumber"));
	     record.put("ispay",request.getParameter("ispay"));
	     record.put("type",request.getParameter("type"));
	     record.put("orderId",orderId);
	     record.put("toUser",request.getParameter("toUser"));
	     if(!StringUtils.isBlank(request.getParameter("queryDateFrom"))){
             String startTime = request.getParameter("queryDateFrom");
             record.put("queryDateFrom", startTime);
         }
         if(!StringUtils.isBlank(request.getParameter("queryDateTo"))){
             String endTime = request.getParameter("queryDateTo");
             record.put("queryDateTo", endTime);
         }
	     record.put("offset", null);
	     record.put("rows", null);
		 Pager<HashMap> pager = timeShareOrderAttachedService.findPage(record);
		 List<HashMap> datas = pager.getDatas();
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		Map<String, Object> sheetNameMap = new HashMap<String, Object>();
		sheetNameMap.put("sheetName", "其他订单数据报表");
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
			List<HashMap> data) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map;// 避免重复创建对象
		String keys[] = { "orderId", "membername","phone", 
				"toUser", "reason", "lpn", "type",
				"createtime","creater","city","brandName","ordermoney","paytype","paystatus","completiontime" };
		for (HashMap map2 : data) {
			map = new HashMap<String, Object>();
			map.put(keys[0], map2.get("orderId"));
			map.put(keys[1], map2.get("membername"));
			map.put(keys[2], map2.get("phone"));
			map.put(keys[3], map2.get("toUser"));
			map.put(keys[4], map2.get("reason"));
			String lpn = (String) map2.get("lpn");
			if(lpn != null){
				map.put(keys[5], lpn.indexOf("•")<0 ?lpn.substring(0, 2)+"•"+lpn.substring(2):lpn);
			}
			map.put(keys[6], map2.get("type"));
			map.put(keys[7], map2.get("createtime")); 
			map.put(keys[8], map2.get("creater"));
			map.put(keys[9], map2.get("city"));
			map.put(keys[10], map2.get("brandName"));
			map.put(keys[11], map2.get("ordermoney"));
			map.put(keys[12], map2.get("paytype") );
			if(map2.get("paystatus") != null){
				map.put(keys[13], map2.get("paystatus").equals("未支付")?"未支付":map2.get("paystatus") );
			}
			map.put(keys[14], map2.get("completiontime"));
			
			list.add(map);
		}
		return list;
	}
    /**
     * 新增附属订单
     * @param attached
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @SystemServiceLog(description="保存附属订单收费项目")
    @RequestMapping(value = "/timeshare_order_attached_save", method = {  RequestMethod.POST })
    public String addAttachedOrder(TimeShareOrderAttached attached, HttpServletRequest request, HttpServletResponse response,
                                   @RequestParam(required = false) MultipartFile repairUriFile,
                                   @RequestParam(required = false) MultipartFile carDamageUriFile
                                   ) {
        response.setContentType("text/html;charset=utf-8");
        try {
            SysUser user = (SysUser) request.getSession().getAttribute("user");
            attached.setCreater(user.getName());
            attached.setCreateTime(new Date());
            attached.setPayStatus(PayStatusEnum.UNPAY);
            attached.setPayType(PayTypeEnum.BALANCE);
            String result = timeShareOrderAttachedService.saveOrderAttached(attached, repairUriFile, carDamageUriFile);
            response.getWriter().print(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
                return null;
    }

    /**
     * 新增附属订单
     * @param order
     *          订单id
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @SystemServiceLog(description="检查订单是否存在")
    @RequestMapping(value = "/attached_checkorder", method = {  RequestMethod.GET })
    public String checkOrder(@RequestParam("orderId") String order, HttpServletRequest request,Integer page, Integer rows, HttpServletResponse response) {
        response.setContentType("text/html;charset=utf-8");
        try {
            Integer orderCount = timeShareOrderAttachedService.findOrder(order);
            if (orderCount>=1){
                response.getWriter().print("success");
            }else{
                response.getWriter().print("该订单不存在！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 附属订单详情
     * @param id
     * @param request
     * @param response
     * @return
     */
    @SystemServiceLog(description="附属订单详情")
    @RequestMapping(value = "/page_orderattached_list_detail", method = {  RequestMethod.POST })
    public String Orderdetail(@RequestParam("orderId") String id, HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("text/html;charset=utf-8");
        try {
            Pager<HashMap> pager = timeShareOrderAttachedService.queryAttachedOrderDetail(id);
            response.getWriter().print(Data2Jsp.Json2Jsp(pager));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
