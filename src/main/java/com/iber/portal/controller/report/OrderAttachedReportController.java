package com.iber.portal.controller.report;

import com.iber.portal.advices.SystemServiceLog;
import com.iber.portal.common.Data2Jsp;
import com.iber.portal.common.ExcelUtil;
import com.iber.portal.common.Pager;
import com.iber.portal.service.timeShare.TimeShareOrderAttachedService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zengfeiyue
 * 附属订单收入报表
 */
@Controller
public class OrderAttachedReportController {


    @Autowired
    private TimeShareOrderAttachedService timeShareOrderAttachedService;

    /**
     * @describe 其他订单收入报表查询
     * @throws Exception
     */
    @SystemServiceLog(description = "其他订单收入报表查询")
    @RequestMapping(value = "/orderAttachedReport", method = { RequestMethod.GET })
    public String carIncomeReport(HttpServletRequest request,
                                  HttpServletResponse response) throws Exception {
        return "report/orderAttachedlistReport";
    }


    /**
     * 附属订单列表
     * @param page
     * @param rows
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @SystemServiceLog(description="查询附属订单报表")
    @RequestMapping(value = "/page_orderattached_report", method = { RequestMethod.GET , RequestMethod.POST })
    public String pageOrderAttachedList(Integer page, Integer rows, HttpServletRequest request, HttpServletResponse response) {
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
            record.put("payStatus",request.getParameter("payStatus"));
            record.put("memberName",request.getParameter("memberName"));
            record.put("cityCode",request.getParameter("cityCode"));
            record.put("lpn",request.getParameter("lpn"));
            record.put("phoneNumber",request.getParameter("phoneNumber"));
            record.put("type",request.getParameter("type"));
            Pager<HashMap> pager = timeShareOrderAttachedService.findPageReport(record);
            response.getWriter().print(Data2Jsp.Json2Jsp(pager));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @describe 导出附属订单收入报表
     * @throws Exception
     */
    @SystemServiceLog(description = "导出附属订单收入报表")
    @RequestMapping(value = "/export_orderattached_report_excel", method = {
            RequestMethod.GET, RequestMethod.POST })
    public String exportOrderattachedReportExcel(HttpServletRequest request,
                                                   HttpServletResponse response) throws Exception {
        response.setContentType("text/html;charset=utf-8");

        Map<String,Object> record = new HashMap<String, Object>() ;

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
        record.put("type",request.getParameter("type"));
        record.put("payStatus", request.getParameter("payStatus"));
        List<Map<String, Object>> list = timeShareOrderAttachedService.findPageReportAll(record);

        String columnNames[] = { "所属城市", "约车订单编号", "会员姓名", "手机号码", "车牌号码",
                "车辆品牌","订单类型", "订单创建时间", "付款时间","创建人","支付状态","支付方式","误工费",
                "维修费","救援费","违章处理费","保险上涨费","实际收入"};// 列名
        //结果集的key，需要根据顺序创建
        String keys[] = {  "city", "orderId", "membername", "phone", "lpn", "brandName",
                "type","createtime","completiontime", "creater","paystatus","paytype",
                "lostIncome","maintainCost","rescueCost","illegalProcessingCost","insurancePremiumCost","ordermoney"};// map中的key

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            ExcelUtil.createWorkBookByTemplate(list, keys, "template/attend_order_template.xls",3).write(os);

        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] content = os.toByteArray();
        InputStream is = new ByteArrayInputStream(content);
        // 设置response参数，可以打开下载页面
        response.reset();
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-Disposition", "attachment;filename="
                + new String(("违章事故收入报表.xls").getBytes(), "iso-8859-1"));
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
            if (bis != null){
                bis.close();
            }

            if (bos != null){
                bos.close();
            }

        }
        return null;
    }
}
