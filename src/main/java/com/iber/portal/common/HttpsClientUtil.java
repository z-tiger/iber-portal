package com.iber.portal.common;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.*;
import java.util.Map.Entry;
  
public class HttpsClientUtil {  
    private static final Logger LOG = LoggerFactory.getLogger(HttpsClientUtil.class);

    private static PoolingHttpClientConnectionManager connMgr;
    private static RequestConfig requestConfig;
    private static final int MAX_TIMEOUT = 15000;

    static {
        // 设置连接池
        connMgr = new PoolingHttpClientConnectionManager();
        // 设置连接池大小
        connMgr.setMaxTotal(100);
        connMgr.setDefaultMaxPerRoute(connMgr.getMaxTotal());

        RequestConfig.Builder configBuilder = RequestConfig.custom();
        // 设置连接超时
        configBuilder.setConnectTimeout(MAX_TIMEOUT);
        // 设置读取超时
        configBuilder.setSocketTimeout(MAX_TIMEOUT);
        // 设置从连接池获取连接实例的超时
        configBuilder.setConnectionRequestTimeout(MAX_TIMEOUT);
        requestConfig = configBuilder.build();
    }

    private static CloseableHttpClient getHttpsClient()
    {  
        return HttpClients.custom().setSSLSocketFactory(SSLConnectionSocketFactory.getSocketFactory()).setConnectionManager(connMgr).setDefaultRequestConfig(requestConfig).build();
    }

    private static CloseableHttpClient getHttpClient()
    {
        return HttpClients.custom().setConnectionManager(connMgr).setDefaultRequestConfig(requestConfig).build();
    }

   /**
    * post方法
    * @param url
    * @param params
    * @return
    */
    public static String post(String url, Map<String, String> params) {
        CloseableHttpClient httpClient = getHttpsClient();
        HttpPost post = new HttpPost(url);  
        HttpEntity entity = map2UrlEncodedFormEntity(params);  
        if(entity != null) {  
            post.setEntity(entity);  
        }  
        CloseableHttpResponse response = null;
        try {  
            response = httpClient.execute(post);
            String result = EntityUtils.toString(response.getEntity());
            LOG.info(result);
            return result;  
        } catch (IOException e) {
            LOG.error("", e);  
        } finally {
            try {
                if (response != null){
                    response.close();
                }
            } catch (IOException e) {
                LOG.error(e.getMessage());
            }
        }  
        return null;  
    }  
      
    /** 
     * 生成post请求时的记录 
     * @param url 
     * @param params 
     * @return 
     * @author asflex 
     * @date  2014-3-28下午7:23:33　 
     * @modify 2014-3-28下午7:23:33 
     */  
    public static String getUrlRequestInfo(String url, Map<String, String> params) {  
          
        StringBuilder paramStr = new StringBuilder();  
        if(MapUtils.isNotEmpty(params)) {  
//            Iterator<Entry<String, String>> iterator = params.entrySet().iterator();  
//            Joiner.on("&").appendTo(paramStr, iterator);  
        }  
        return String.format("curl -d '%s' '%s'", StringUtils.trimToEmpty(paramStr.toString()), StringUtils.trimToEmpty(url));  
    }  

    /** 
     * 参数转换 
     * @param params 
     * @return 
     * @author asflex 
     * @date  2014-3-28下午7:23:05　 
     * @modify 2014-3-28下午7:23:05 
     */
    private static HttpEntity map2UrlEncodedFormEntity(Map<String, String> params) {
        if(MapUtils.isNotEmpty(params)) {  
            Iterator<Entry<String, String>> iterator = params.entrySet().iterator();  
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();  
            while(iterator.hasNext()) {  
                Entry<String, String> entry = iterator.next();  
                nvps.add(new BasicNameValuePair(StringUtils.trimToEmpty(entry.getKey()), StringUtils.trimToEmpty(entry.getValue())));  
            }  
            try {  
                return new UrlEncodedFormEntity(nvps);  
            } catch (UnsupportedEncodingException e) {  
                LOG.error("", e);  
            }  
        }  
        return null;  
    }  
      
    /**
     * get方法
     * @param url
     * @param params
     * @return
     * @throws URISyntaxException
     * @throws ClientProtocolException
     * @throws IOException
     */
    public static String get(String url , String params){
        CloseableHttpClient httpClient = getHttpsClient();
        CloseableHttpResponse response = null;
    	try {
            HttpGet request = new HttpGet();
            request.setURI(new URI(url+URLEncoder.encode(params,SysConstant.DEFAULT_CHARSET)));
            response = httpClient.execute(request);
            return EntityUtils.toString(response.getEntity(),SysConstant.DEFAULT_CHARSET);
		} catch (Exception e) {
            LOG.error("",e);
		}finally{
            try {
                if (response != null){
                    response.close();
                }
            } catch (IOException e) {
                LOG.error(e.getMessage());
            }
        }
    	return null ;
	}

    /**
     * 发送 GET 请求（HTTP），不带输入数据
     * @param url
     * @return
     */
    public static String doGet(String url) {
        return doGet(url, new HashMap<String, Object>());
    }

    /**
     * 发送 GET 请求（HTTP），K-V形式
     * @param url
     * @param params
     * @return
     */
    public static String doGet(String url, Map<String, Object> params) {
        String apiUrl = url;
        StringBuffer param = new StringBuffer();
        int i = 0;
        for (String key : params.keySet()) {
            if (i == 0)
                param.append("?");
            else
                param.append("&");
            param.append(key).append("=").append(params.get(key));
            i++;
        }
        apiUrl += param;
        String result = null;
        CloseableHttpClient httpclient = getHttpClient();
        CloseableHttpResponse response = null;
        try {
            HttpGet httpPost = new HttpGet(apiUrl);
            response = httpclient.execute(httpPost);
            result = EntityUtils.toString(response.getEntity(),SysConstant.DEFAULT_CHARSET);
        } catch (IOException e) {
            LOG.error(e.getMessage());
        }finally {
            try {
                if (response != null){
                    response.close();
                }
            } catch (IOException e) {
                LOG.error(e.getMessage());
            }
        }
        return result;
    }

    /**
     * 发送 POST 请求（HTTP），不带输入数据
     * @param apiUrl
     * @return
     */
    public static String doPost(String apiUrl) {
        return doPost(apiUrl, new HashMap<String, Object>());
    }

    /**
     * 发送 POST 请求（HTTP），K-V形式
     * @param apiUrl API接口URL
     * @param params 参数map
     * @return
     */
    public static String doPost(String apiUrl, Map<String, Object> params) {
        CloseableHttpClient httpClient = getHttpClient();
        String httpStr = null;
        HttpPost httpPost = new HttpPost(apiUrl);
        CloseableHttpResponse response = null;

        try {
            httpPost.setConfig(requestConfig);
            List<NameValuePair> pairList = new ArrayList<NameValuePair>(params.size());
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                NameValuePair pair = new BasicNameValuePair(entry.getKey(), entry
                        .getValue().toString());
                pairList.add(pair);
            }
            httpPost.setEntity(new UrlEncodedFormEntity(pairList, Charset.forName(SysConstant.DEFAULT_CHARSET)));
            response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            httpStr = EntityUtils.toString(entity, SysConstant.DEFAULT_CHARSET);
        } catch (IOException e) {
            LOG.error(e.getMessage());
        } finally {
            if (response != null) {
                try {
                    EntityUtils.consume(response.getEntity());
                } catch (IOException e) {
                    LOG.error(e.getMessage());
                }
            }
        }
        return httpStr;
    }

    /**
     * 发送 POST 请求（HTTP），JSON形式
     * @param apiUrl
     * @param json json对象
     * @return
     */
    public static String doPost(String apiUrl, String json) {
        CloseableHttpClient httpClient = getHttpClient();
        String httpStr = null;
        HttpPost httpPost = new HttpPost(apiUrl);
        CloseableHttpResponse response = null;

        try {
            httpPost.setConfig(requestConfig);
            StringEntity stringEntity = new StringEntity(json,SysConstant.DEFAULT_CHARSET);//解决中文乱码问题
            stringEntity.setContentEncoding(SysConstant.DEFAULT_CHARSET);
            stringEntity.setContentType("application/json");
//            stringEntity.setContentType("application/x-www-form-urlencoded");
            httpPost.setEntity(stringEntity);
            response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            httpStr = EntityUtils.toString(entity, SysConstant.DEFAULT_CHARSET);
        } catch (IOException e) {
            LOG.error("doPost",e);
        } finally {
            if (response != null) {
                try {
                    EntityUtils.consume(response.getEntity());
                } catch (IOException e) {
                    LOG.error(e.getMessage());
                }
            }
        }
        return httpStr;
    }

    /**
     * 发送 SSL POST 请求（HTTPS），K-V形式
     * @param apiUrl API接口URL
     * @param params 参数map
     * @return
     */
    public static String doPostSSL(String apiUrl, Map<String, Object> params) {
        CloseableHttpClient httpClient = getHttpsClient();
        HttpPost httpPost = new HttpPost(apiUrl);
        CloseableHttpResponse response = null;
        String httpStr = null;

        try {
            httpPost.setConfig(requestConfig);
            List<NameValuePair> pairList = new ArrayList<NameValuePair>(params.size());
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                NameValuePair pair = new BasicNameValuePair(entry.getKey(), entry
                        .getValue().toString());
                pairList.add(pair);
            }
            httpPost.setEntity(new UrlEncodedFormEntity(pairList, Charset.forName(SysConstant.DEFAULT_CHARSET)));
            response = httpClient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                return null;
            }
            HttpEntity entity = response.getEntity();
            if (entity == null) {
                return null;
            }
            httpStr = EntityUtils.toString(entity, SysConstant.DEFAULT_CHARSET);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    EntityUtils.consume(response.getEntity());
                } catch (IOException e) {
                    LOG.error(e.getMessage());
                }
            }
        }
        return httpStr;
    }

    /**
     * 发送 SSL POST 请求（HTTPS），JSON形式
     * @param apiUrl API接口URL
     * @param json JSON对象
     * @return
     */
    public static String doPostSSL(String apiUrl, String json) {
        CloseableHttpClient httpClient = getHttpsClient();
        HttpPost httpPost = new HttpPost(apiUrl);
        CloseableHttpResponse response = null;
        String httpStr = null;

        try {
            httpPost.setConfig(requestConfig);
            StringEntity stringEntity = new StringEntity(json,SysConstant.DEFAULT_CHARSET);//解决中文乱码问题
            stringEntity.setContentEncoding(SysConstant.DEFAULT_CHARSET);
            stringEntity.setContentType("application/json");
            httpPost.setEntity(stringEntity);
            response = httpClient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                return null;
            }
            HttpEntity entity = response.getEntity();
            if (entity == null) {
                return null;
            }
            httpStr = EntityUtils.toString(entity, SysConstant.DEFAULT_CHARSET);
        } catch (Exception e) {
            LOG.error(e.getMessage());
        } finally {
            if (response != null) {
                try {
                    EntityUtils.consume(response.getEntity());
                } catch (IOException e) {
                    LOG.error("",e);
                }
            }
        }
        return httpStr;
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        Map<String, String> params = new HashMap<String, String>();
       /* String tokenUrl="https://aip.baidubce.com/oauth/2.0/token";
        params.put("grant_type", "client_credentials");
        params.put("client_id",SysConstant.BAIDU_API_KEY);
        params.put("client_secret", SysConstant.BAIDU_SECRET_KEY);
//        System.out.println(post(tokenUrl, params));

        String result =post(tokenUrl, params);

        JSONObject jsonResult = JSON.parseObject(result);
        String accessToken = jsonResult.getString("access_token");
        System.out.println(accessToken);*/

        String access_token="24.72f9a263af41cd360d0ac78d6b907e4b.2592000.1525923616.282335-10929845";

        String deleteUrl ="https://aip.baidubce.com/rest/2.0/face/v2/faceset/user/delete";
        String uid ="12312";
        String ContentType="application/x-www-form-urlencoded";
        params.put("access_token",access_token);
        params.put("uid", uid);

        String json = "uid="+uid+"&access_token="+access_token;
//        System.out.println(doPost(deleteUrl, json));
        String result = post(deleteUrl, params);
        System.out.println(JSONObject.parseObject(result).getInteger("error_code"));

        System.out.println();









        /*String param="grant_type=client_credentials&"+
                "client_id="+ SysConstant.BAIDU_ACCESS_KEY+"&client_secret="+SysConstant.BAIDU_SECRET_KEY;*/

       /* Map<String, String> params = new HashMap<String, String>();
        String str = new String("测试".getBytes(SysConstant.DEFAULT_CHARSET),"ISO-8859-1") ;
        params.put("msgContentJson", "{\"telephoneNo\":\"18666666666\",\"ipAddress\":\"\",\"templateId\":\"1673\",\"contentParam\":\""+str+"\"}");
        System.out.println(post("https://ibtest.ibgoing.com/services/i/a", params));*/
    }  
}