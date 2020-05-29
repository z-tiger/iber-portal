package com.iber.portal.common;

import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * @author liubiao
 */
public class BaiduTokenUtils {
    private static String token ;

    static {
        Map<String, String> params = new HashMap<String, String>();
        String tokenUrl="https://aip.baidubce.com/oauth/2.0/token";
        params.put("grant_type", "client_credentials");
        params.put("client_id",SysConstant.BAIDU_API_KEY);
        params.put("client_secret", SysConstant.BAIDU_SECRET_KEY);
//        System.out.println(post(tokenUrl, params));

        String result = HttpsClientUtil.post(tokenUrl, params);

        JSONObject jsonResult = JSONObject.parseObject(result);
        token = jsonResult.getString("access_token");
        System.out.println(token);
    }

    public static String getToken() {
        return token;
    }

    public static void setToken(String token) {
        BaiduTokenUtils.token = token;
    }

    public static void main(String[] args) {
        String token;
        Map<String, String> params = new HashMap<String, String>();
        String tokenUrl="https://aip.baidubce.com/oauth/2.0/token";
        params.put("grant_type", "client_credentials");
        params.put("client_id",SysConstant.BAIDU_API_KEY);
        params.put("client_secret", SysConstant.BAIDU_SECRET_KEY);

        String result = HttpsClientUtil.post(tokenUrl, params);

        JSONObject jsonResult = JSONObject.parseObject(result);
        token = jsonResult.getString("access_token");
        System.out.println(token);
    }

}
