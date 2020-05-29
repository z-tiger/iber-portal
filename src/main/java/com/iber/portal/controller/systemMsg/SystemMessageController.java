package com.iber.portal.controller.systemMsg;

import com.alibaba.fastjson.JSON;
import com.iber.portal.advices.SystemServiceLog;
import com.iber.portal.common.Data2Jsp;
import com.iber.portal.common.JsonDateValueProcessor;
import com.iber.portal.controller.MainController;
import com.iber.portal.getui.PushClient;
import com.iber.portal.getui.PushClientEmployee;
import com.iber.portal.getui.PushCommonBean;
import com.iber.portal.service.base.MemberService;
import com.iber.portal.service.base.SystemMsgLogService;
import com.iber.portal.vo.systemMsg.SystemMsgLogVo;
import net.sf.json.JsonConfig;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import sun.security.x509.AttributeNameEnumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by liuxj on 2018/6/15.
 */
@Controller
public class SystemMessageController extends MainController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SystemMsgLogService systemMsgLogService;

    @Autowired
    private MemberService memberService;


    @RequestMapping(value = "push_msg_page",method = {RequestMethod.GET})
    @SystemServiceLog(description = "消息推送页面")
    public String pushMsgPage(){
        return "systemMsg/systemMsg";
    }

    @RequestMapping(value = "/push_msg_list",method = {RequestMethod.POST})
    @SystemServiceLog(description = "消息推送列表")
    public void pushMsgList(Integer page, Integer rows, String type, String title, HttpServletResponse response) throws Exception{
        response.setContentType("text/html;charset=utf-8");
        Map<String, Integer> pageInfo = Data2Jsp.getFristAndPageSize(page, rows);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("offset", pageInfo.get("first_page"));
        map.put("rows", pageInfo.get("page_size"));
        map.put("type",type);
        map.put("title",title);
        List<Map<String,Object>> list = systemMsgLogService.selectAllSystemMsgByHand(map);
        Integer totalRecord = systemMsgLogService.selecAllRecords(map);
        String json = Data2Jsp.Json2JspFormaDate(list, totalRecord);
        response.getWriter().print(json);
    }

    @SystemServiceLog(description="附件样表下载")
    @RequestMapping(value = "/member_phone_template_file_download", method = { RequestMethod.GET, RequestMethod.POST  })
    public String carInportTemplateFileDownload(HttpServletRequest request, HttpServletResponse response)throws Exception{
        response.setContentType("text/html;charset=utf-8");
        String path = request.getRealPath("systemMsg/template.xls");
        try {
            // path是指欲下载的文件的路径。
            File file = new File(path);
            // 取得文件名。
            String filename = file.getName();
            // 以流的形式下载文件。
            InputStream fis = new BufferedInputStream(new FileInputStream(path));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            // 清空response
            response.reset();
            // 设置response的Header
            response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes()));
            response.addHeader("Content-Length", "" + file.length());
            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            toClient.write(buffer);
            toClient.flush();
            toClient.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @SystemServiceLog(description = "推送消息")
    @RequestMapping(value = "/save_system_msg",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public Map<String,Object> saveSystemMsg(HttpServletResponse response, MultipartFile importUrl, String msgType,
                                            String cityCode, Integer targetUser, final String msgContent, String msgTitle) throws Exception{
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        response.setContentType("text/html;charset=utf-8");
        final List<SystemMsgLogVo> list = new ArrayList<SystemMsgLogVo>();
        if (targetUser.equals(1)){//如果指定用户
            InputStream is = importUrl.getInputStream();
            HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
            HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(0);
            List<String> phones = new ArrayList<String>();
            for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
                HSSFRow hssfRow = hssfSheet.getRow(rowNum);
                if (hssfRow != null) {
                    HSSFCell phone = hssfRow.getCell(0);
                    phones.add(String.valueOf(phone));
                }
            }
            //根据手机号查询要推送消息的用户
            List<Map<String,Object>> maps = memberService.selectByPhoneList(phones);
            pushMsg(msgType, cityCode, msgContent, msgTitle, list, maps,1,executorService);
        }else {//如果不指定用户
            List<Map<String,Object>> maps = memberService.selectByCityCode(cityCode);
            pushMsg(msgType, cityCode, msgContent, msgTitle, list, maps,0,executorService);
        }

        return success("操作成功");
    }

    private void pushMsg(String msgType, String cityCode, String msgContent, String msgTitle, final List<SystemMsgLogVo> list,
                         final List<Map<String, Object>> maps,Integer isSpecifyUser,ExecutorService executorService) throws Exception{
        for (Map map : maps) {
            SystemMsgLogVo vo = new SystemMsgLogVo();
            vo.setCityCode(cityCode);
            vo.setCreateId(getUser(request).getId());
            vo.setCreateTime(new Date());
            vo.setIsManualSend(1);
            vo.setIsSpecifyUser(isSpecifyUser);
            vo.setMsgContent(msgContent);
            vo.setMsgTitle(msgTitle);
            vo.setMemberId((Integer) map.get("id"));
            vo.setMsgType(msgType);
            list.add(vo);
        }
        //推送消息
        List<SystemMsgLogVo> successList = new ArrayList<SystemMsgLogVo>();
        executorService.submit(new PushMsgThread(executorService,list,successList,maps));
//        latch.await();
        //插入数据库
        systemMsgLogService.insertBatch(list);

    }
}
