package com.iber.portal.quartz;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.iber.portal.common.InsertFreezeLogUtil;
import com.iber.portal.common.SendMail;
import com.iber.portal.common.SysConstant;
import com.iber.portal.dao.base.SystemMsgLogMapper;
import com.iber.portal.dao.base.WZQueryMapper;
import com.iber.portal.dao.car.CarRunMapper;
import com.iber.portal.dao.member.MemberFreezeLogMapper;
import com.iber.portal.dao.sys.SysParamMapper;
import com.iber.portal.getui.PushClient;
import com.iber.portal.getui.PushCommonBean;
import com.iber.portal.model.base.Member;
import com.iber.portal.model.base.SystemMsgLog;
import com.iber.portal.model.car.report.CarRunSmallBatteryDetail;
import com.iber.portal.model.car.report.RunStatusVo;
import com.iber.portal.model.sys.SysParam;
import com.iber.portal.service.sys.SysParamService;
import com.iber.portal.vo.car.CarReportQuery;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 违章长时间未处理，冻结会员
 * @author zengfeiyue
 */
public class CarStatusReportSupport extends QuartzJobBean {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CarRunMapper carRunMapper;

    @Autowired
    private SysParamService sysParamService;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("车辆运营状态分析报告定时任务开始执行");
        SysParam subject= sysParamService.selectByKey("car_report_subject");
        SysParam carReportContent= sysParamService.selectByKey("car_report_content");
        SysParam car_report_to= sysParamService.selectByKey("car_report_to");
        SysParam car_report_cc= sysParamService.selectByKey("car_report_cc");
        SysParam small_battery_loss= sysParamService.selectByKey("small_battery_loss");
        SysParam car_report_query_json= sysParamService.selectByKey("car_report_query_json");
        net.sf.json.JSONArray array = net.sf.json.JSONArray.fromObject(car_report_query_json.getValue());
        List<CarReportQuery> list = net.sf.json.JSONArray.toList(array,CarReportQuery.class);
        RunStatusVo runStatusVo = carRunMapper.selectCarRunStatus();
        List<CarRunSmallBatteryDetail> carRunSmallBatteryDetails = carRunMapper.selectCarRunSmallBatteryLossDetailRun(Integer.parseInt(small_battery_loss.getValue()));
        List<CarRunSmallBatteryDetail> carRunEmptySmallBatteryDetails = carRunMapper.selectCarRunSmallBatteryLossDetailEmpty(Integer.parseInt(small_battery_loss.getValue()));
        List<CarRunSmallBatteryDetail> useCarRestBattery = carRunMapper.selectUseCarRestBattery(list);
        File file = new File(this.getClass().getResource("/").getPath()+"template/车辆状态报告.xls");
        POIFSFileSystem fileSystem = null;
        try {
            fileSystem = new POIFSFileSystem(file);
            Workbook wb = new HSSFWorkbook(fileSystem);
            // 创建第一个sheet（页），并命名
            Sheet sheet = wb.getSheet("sheet1");

            Row row2 = sheet.getRow((short) 1);
            row2.getCell(1).setCellValue(runStatusVo.getUseCar());
            Row row3 = sheet.getRow((short) 2);
            row3.getCell(1).setCellValue(runStatusVo.getRepair());
            Row row4 = sheet.getRow((short) 3);
            row4.getCell(1).setCellValue(runStatusVo.getMaintain());
            Row row5 = sheet.getRow((short) 4);
            row5.getCell(1).setCellValue(runStatusVo.getBatStatus());
            int i=0;
            for (CarRunSmallBatteryDetail carRunSmallBatteryDetail:carRunSmallBatteryDetails){
                Row row = sheet.getRow((short) (5+i));
                if (row==null){
                    row = sheet.createRow((short) (5+i));
                }
                row.createCell(3).setCellValue(carRunSmallBatteryDetail.getMemberName());
                row.createCell(4).setCellValue(carRunSmallBatteryDetail.getMemberPhone());
                row.createCell(5).setCellValue(carRunSmallBatteryDetail.getLpn());
                row.createCell(6).setCellValue(carRunSmallBatteryDetail.getBrandName());
                row.createCell(7).setCellValue(carRunSmallBatteryDetail.getSmallBattery());
                i++;
            }
            i=0;
            for (CarRunSmallBatteryDetail carRunSmallBatteryDetail:carRunEmptySmallBatteryDetails){
                Row row = sheet.getRow((short) (5+i));
                if (row==null){
                    row = sheet.createRow((short) (5+i));
                }
                row.createCell(9).setCellValue(carRunSmallBatteryDetail.getLpn());
                row.createCell(10).setCellValue(carRunSmallBatteryDetail.getStatus());
                row.createCell(11).setCellValue(carRunSmallBatteryDetail.getBrandName());
                row.createCell(12).setCellValue(carRunSmallBatteryDetail.getSmallBattery());
                i++;
            }
            i=0;
            for (CarRunSmallBatteryDetail carRunSmallBatteryDetail:useCarRestBattery){
                Row row = sheet.getRow((short) (5+i));
                if (row==null){
                    row = sheet.createRow((short) (5+i));
                }
                row.createCell(14).setCellValue(carRunSmallBatteryDetail.getMemberName());
                row.createCell(15).setCellValue(carRunSmallBatteryDetail.getMemberPhone());
                row.createCell(16).setCellValue(carRunSmallBatteryDetail.getLpn());
                row.createCell(17).setCellValue(carRunSmallBatteryDetail.getBrandName());
                row.createCell(18).setCellValue(carRunSmallBatteryDetail.getRestBattery());
                i++;
            }

            ByteArrayOutputStream os = new ByteArrayOutputStream();
            wb.write(os);
            //写到文件
            File file1 = new File(this.getClass().getResource("/").getPath()+"template/carReport.xls");
            byte[] content = os.toByteArray();
            InputStream is = new ByteArrayInputStream(content);
            OutputStream ops = new FileOutputStream(file1);
            int bytesRead = 0;
            byte[] buffer = new byte[1024];
            while ((bytesRead = is.read(buffer, 0, 1024)) != -1) {
                ops.write(buffer, 0, bytesRead);
            }
            ops.close();
            is.close();
            //发送文件
            SendMail sendMail = new SendMail(sysParamService);
            System.err.println(file1.getName());
            List<File> files = new ArrayList<File>();
            files.add(file1);
            try {
                sendMail.sendMail(subject.getValue(),carReportContent.getValue(),car_report_to.getValue(),car_report_cc.getValue(),"",files);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
