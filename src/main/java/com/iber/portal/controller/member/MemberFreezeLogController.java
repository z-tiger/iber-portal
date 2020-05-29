package com.iber.portal.controller.member;

import com.iber.portal.advices.SystemServiceLog;
import com.iber.portal.common.Data2Jsp;
import com.iber.portal.common.ExcelUtil;
import com.iber.portal.controller.MainController;
import com.iber.portal.service.member.MemberFreezeLogService;
import com.iber.portal.vo.member.MemberFreezeLogVo;
import org.apache.commons.lang3.time.FastDateFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 刘晓杰 on 2017/12/27.
 */
@Controller
public class MemberFreezeLogController extends MainController {

    @Autowired
    private MemberFreezeLogService memberFreezeLogService;

    /**
     * 会员信息页面
     */
    @SystemServiceLog(description = "会员账户冻结日志页面")
    @RequestMapping(value = "/member_card_freeze_log_page", method = {RequestMethod.GET})
    public String member(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return "member/freezeLog";
    }

    @SystemServiceLog(description = "会员冻结日志")
    @RequestMapping(value = "/member_card_freeze_log_list", method = {RequestMethod.GET, RequestMethod.POST})
    public String getFreezeLogList(int page, int rows, HttpServletRequest request, HttpServletResponse response) throws IOException{
        response.setContentType("text/html;charset=utf-8");
        Map<String, Integer> pageInfo = Data2Jsp.getFristAndPageSize(page, rows);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("offset", pageInfo.get("first_page"));
        map.put("rows", pageInfo.get("page_size"));
        String cityCode = request.getParameter("cityCode");
        String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        String reason = request.getParameter("reason");
        map.put("name",name);
        map.put("phone",phone);
        map.put("reason",reason);
        map.put("cityCode",cityCode);
        List<MemberFreezeLogVo> list = memberFreezeLogService.selectAllFreezeLog(map);
        int totalRecord = memberFreezeLogService.selecAllRecords(map);
        String json = Data2Jsp.Json2Jsp(list, totalRecord);
        response.getWriter().print(json);
        return null;
    }

    @SystemServiceLog(description = "会员冻结日志导出excel")
    @RequestMapping(value = "/export_freeze_log",method = {RequestMethod.POST,RequestMethod.GET})
    public String exportFreezeLog(HttpServletRequest request, HttpServletResponse response) throws Exception{
        String fileName = "freezeLog";
        // 填充reports数据
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("cityCode", request.getParameter("cityCode"));
        map.put("name", request.getParameter("name"));
        map.put("phone", request.getParameter("phone"));
        map.put("reason", request.getParameter("reason"));
        List<MemberFreezeLogVo> reports = createFreezeLogReportData(map);
        List<Map<String, Object>> list = createFreezeLogReportExcelRecord(reports);

        String columnNames[] = {"所属城市", "会员姓名", "手机号码", "原因", "操作人",
                "操作时间"};// 列名
        String keys[] = { "cityName", "name", "phone", "reason", "createName",
                "createTime"};// map中的key
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            ExcelUtil.createTimeShareWorkBook(list, keys, columnNames,null)
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
        return null;
    }

    private List<MemberFreezeLogVo> createFreezeLogReportData(Map<String, Object> map) {
        return memberFreezeLogService.selectAllFreezeLog(map);
    }

    private List<Map<String,Object>> createFreezeLogReportExcelRecord(List<MemberFreezeLogVo> reports){
        List<Map<String, Object>> listmap = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("sheetName", "sheet1");
        listmap.add(map);
        MemberFreezeLogVo report = null;
        FastDateFormat formatter = FastDateFormat
                .getInstance("yyyy-MM-dd HH:mm:ss");
        for (int j = 0; j < reports.size(); j++) {
            report = reports.get(j);
            Map<String, Object> mapValue = new HashMap<String, Object>();
            mapValue.put("createTime", formatter.format(report.getCreateTime()));
            mapValue.put("cityName", report.getCityName());
            mapValue.put("name", report.getName());
            mapValue.put("phone", report.getPhone());
            mapValue.put("reason", report.getReason());
            mapValue.put("name", report.getName());
            mapValue.put("phone", report.getPhone());
            mapValue.put("createName", report.getCreateName());

            listmap.add(mapValue);
        }
        return listmap;
    }
}
