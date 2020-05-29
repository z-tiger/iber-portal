package com.iber.portal.quartz;

import com.iber.portal.common.HttpUtils;
import com.iber.portal.common.HttpsClientUtil;
import com.iber.portal.model.sys.SysParam;
import com.iber.portal.service.sys.SysParamService;
import net.sf.json.JSONObject;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * Created by 刘晓杰 on 2018/1/8.
 */
public class LongRentDelaySupport extends QuartzJobBean {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SysParamService sysParamService ;

    @Override
    protected void executeInternal(JobExecutionContext arg0)
            throws JobExecutionException {
        log.info("日租延期定时任务开始执行") ;
        StringBuffer sb = new StringBuffer("{") ;
        sb.append("\"cId\":\"platForm\",\"memberId\":\"\",\"method\":\"delayLongRent\",") ;
        sb.append("\"param\":\"{}\",") ;
        sb.append("\"phone\":\"\",\"type\":\"platForm\",\"version\":\"1\"") ;
        sb.append("}") ;
        SysParam sysParam = sysParamService.selectByKey("http_url_d") ;
        String json = "" ;
        if(sysParam.getValue().indexOf("https") == 0){ //https接口
            json = HttpsClientUtil.get(sysParam.getValue(), sb.toString()) ;
        }else{
            json = HttpUtils.commonSendUrl(sysParam.getValue(), sb.toString()) ;
        }
        JSONObject jsonObject = JSONObject.fromObject(json) ;
        log.info("日租延期定时任务结束执行" + jsonObject) ;
    }
}
