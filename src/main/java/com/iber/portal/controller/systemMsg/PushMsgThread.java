package com.iber.portal.controller.systemMsg;

import com.iber.portal.common.JsonDateValueProcessor;
import com.iber.portal.getui.PushClient;
import com.iber.portal.getui.PushCommonBean;
import com.iber.portal.model.base.SystemMsgLog;
import com.iber.portal.vo.systemMsg.SystemMsgLogVo;
import net.sf.json.JsonConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;

/**
 * Created by liuxj on 2018/6/20.
 */
public class PushMsgThread implements Runnable {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private ExecutorService service;
    private List<SystemMsgLogVo> list;
    private List<SystemMsgLogVo> successList;
    private List<Map<String,Object>> maps;

    public PushMsgThread(ExecutorService service, List<SystemMsgLogVo> list,
                         List<SystemMsgLogVo> successList, List<Map<String, Object>> maps) {
        this.service = service;
        this.list = list;
        this.successList = successList;
        this.maps = maps;
    }

    @Override
    public void run() {
        for (int i = 0; i < list.size(); i++) {
            log.info("推送消息开始："+maps.get(i).get("phone"));
            PushCommonBean pushCommon = new PushCommonBean("push_msg","1","推送消息",list.get(i)) ;
            List<String> alias = PushClient.queryClientId((String) maps.get(i).get("phone"));
            if(!alias.isEmpty() && alias.size()> 0){
                log.info("推送消息中："+maps.get(i).get("phone"));
                for(String cid : alias){
                    JsonConfig jsonConfig = new JsonConfig();
                    jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
                    PushClient.push(cid, net.sf.json.JSONObject.fromObject(pushCommon, jsonConfig));
                    successList.add(list.get(i));
                }
            }
            log.info("推送消息结束："+maps.get(i).get("phone"));
        }
        //latch.countDown();
    }
}
