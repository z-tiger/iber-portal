package com.iber.portal.controller.operateLog;

import com.iber.portal.advices.SystemServiceLog;
import com.iber.portal.common.Data2Jsp;
import com.iber.portal.common.Pager;
import com.iber.portal.mongo.search.MongoMemberLogExtendSearch;
import com.iber.portal.mongo.search.MongoPlatformLogSearch;
import com.iber.portal.service.sys.SysOperateLogService;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * <br>
 * <b>功能：</b>操作日志<br>
 * <b>作者：</b>lf<br>
 * <b>日期：</b> 2017-9-6 14:38:44 <br>
 */
@Controller
public class OperateLogController {

    private final static Logger log = Logger.getLogger(OperateLogController.class);

    @Autowired
    private SysOperateLogService sysOperateLogService;


    @SystemServiceLog(description = "平台操作日志页面")
    @RequestMapping("/platformOperateLog_page")
    public String platformOperateLogPage() {
        log.info("platformOperateLogPage页面");
        return "operateLog/platformOperateLog";
    }

    @SystemServiceLog(description = "会员操作日志页面")
    @RequestMapping("/memberOperateLog_page")
    public String memberOperateLogPage() {
        log.info("memberOperateLogPage页面");
        return "operateLog/memberOperateLog";
    }

    /**
     * 数据
     *
     * @param page
     * @param rows
     * @return
     * @throws IOException
     */
    @SystemServiceLog(description = "平台操作日志管理页面数据")
    @RequestMapping("/platformOperateLog")
    public void platformOperateLog(int page, int rows, HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info("平台操作日志数据列表");
        response.setContentType("text/html;charset=utf-8");
        MongoPlatformLogSearch search = new MongoPlatformLogSearch();
        search.setPage(page);
        search.setRows(rows);
        final String start = request.getParameter("start");
        final String end = request.getParameter("end");
        final String param = request.getParameter("param");
        if (NumberUtils.isDigits(start)) {
            search.setStartTime(Long.valueOf(start));
        }
        if (NumberUtils.isDigits(end)) {
            search.setEndTime(Long.valueOf(end));
        }
        search.setMethodDescribe(request.getParameter("methodDescribe"));
        search.setMethod(request.getParameter("method"));
        String memberId = request.getParameter("memberId");
        search.setMemberId(StringUtils.isNotBlank(memberId) ? Integer.valueOf(memberId) : null);
        search.setName(request.getParameter("name"));
        search.setParam(param);
        // 查询
        Pager<Map<String, Object>> mapPager = sysOperateLogService.queryPlatformPageList(search);
        response.getWriter().print(Data2Jsp.Json2Jsp(mapPager));
    }

    /**
     * 数据
     *
     * @param page
     * @param rows
     * @return
     * @throws IOException
     */
    @SystemServiceLog(description = "会员操作日志管理页面数据")
    @RequestMapping("/memberOperateLog")
    public void memberOperateLog(int page, int rows, HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info("会员操作日志数据列表");
        response.setContentType("text/html;charset=utf-8");
        MongoMemberLogExtendSearch search = new MongoMemberLogExtendSearch();
        search.setPage(page);
        search.setRows(rows);
        final String start = request.getParameter("start");
        final String end = request.getParameter("end");
        if (NumberUtils.isDigits(start)) {
            search.setStartTime(Long.valueOf(start));
        }
        if (NumberUtils.isDigits(end)) {
            search.setEndTime(Long.valueOf(end));
        }
        //search.setMethod(request.getParameter("method"));
        search.setName(request.getParameter("name"));//会员姓名
        //String memberId = request.getParameter("memberId");
        //search.setMemberId(StringUtils.isNotBlank(memberId) ? Integer.valueOf(memberId) : null);
        search.setScene(request.getParameter("scene"));//操作描述
        search.setPhone(request.getParameter("phone"));//会员手机号
        Pager<Map<String, Object>> mapPager = sysOperateLogService.queryMemberLogPager(search);
        response.getWriter().print(Data2Jsp.Json2Jsp(mapPager));
    }

}
