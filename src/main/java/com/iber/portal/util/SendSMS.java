package com.iber.portal.util;

import com.iber.portal.common.HttpsClientUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.iber.portal.util.AES.parseByte2HexStr;

/**
 * @author Created by lf on 2017/5/22.
 * 发送短信 2017-5-22 14:08:53
 */
public class SendSMS {

    private static final Logger LOGGER = LoggerFactory.getLogger(SendSMS.class);
    private static final ExecutorService POOL = Executors.newFixedThreadPool(8);

    private static final String url = PropertyUtil.getString("sms.send.url");
    /**
     * 发送短信
     * @param phone
     * 手机号码
     * @param ip
     * IP地址
     * @param templateId
     * 模板id
     * @param contentParam
     * 发送参数
     */
    public static void send(String phone,String ip,Integer templateId,String contentParam) {
        try {
            String token = encryptBySalt(phone);
            Map<String, String> params = new HashMap<String, String>();
            StringBuilder param = new StringBuilder("{\"telephoneNo\":\"")
                    .append(phone)
                    .append("\",\"ipAddress\":\"")
                    .append(ip)
                    .append("\",")
                    .append("\"templateId\":\"")
                    .append(templateId)
                    .append("\",\"contentParam\":\"")
                    .append(new String(contentParam.getBytes("utf-8"),"ISO-8859-1"))
                    .append("\",\"token\":\"")
                    .append(token).append("\"}");
            params.put("msgContentJson", param.toString());
            HttpsClientUtil.post(url,params) ;
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("发送短信失败!",e);
        }
    }

    /**
     * 发送短信
     * @param msgContentJson
     *      参数json
     */
    public static void send(final String msgContentJson){
        Map<String, String> params = new HashMap<String, String>();
        params.put("msgContentJson",msgContentJson);
        HttpsClientUtil.post(url,params) ;
    }
    /**
     * 发送短信
     * @param params
     *      参数json
     */
    public static void send(Map params){
        HttpsClientUtil.post(url,params) ;
    }

    public static void sendSMS(final String phone,final String ip,final Integer templateId,final String contentParam) {
        POOL.execute(new Runnable() {
            @Override
            public void run() {
                SendSMS.send(phone, ip, templateId, contentParam);
            }
        });
    }

    /**
     * 盐值加密
     * @param saltFigure
     * @return
     */
    public static String encryptBySalt (String saltFigure){
        String saltStr = PropertyUtil.getString("sms.send.token")+"_"+saltFigure;
        byte[] encryptResult = AES.encrypt(saltStr.getBytes(), PropertyUtil.getString("sms.send.key").getBytes());
        String encryptStr = parseByte2HexStr(encryptResult);
        return encryptStr;
    }
}
