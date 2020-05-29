package com.iber.portal.common;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 * 
 * ClassName: HttpTool 
 * @Description: http请求工具类
 * @author 程苗 
 * @date 2015-11-24
 */
@SuppressWarnings("deprecation")
public class ApacheHttpTool {  

	private HttpClient hc = new DefaultHttpClient();  
    
	/**
	 * 模拟Get方式的Http请求
	 * @param url 请求地址
	 * @param params 请求参数
	 * @param responseEncoding 请求页面的响应流编码
	 * @param entityEncoding 请求参数的编码
	 * @return
	 */
	public byte[] doGetReturnBytes(String url, List<NameValuePair> params, String responseEncoding, String entityEncoding) {
		byte[] body = null;
		if(responseEncoding==null || responseEncoding.equals(""))
		{
			responseEncoding="UTF-8";
//			responseEncoding="ISO-8859-1";
		}
		
		if(entityEncoding == null || entityEncoding.equals(""))
			entityEncoding = "UTF-8";
		
		if(params == null)
			params = new ArrayList<NameValuePair>();
		
		try {  
			//Get请求
			HttpGet httpget = new HttpGet(url);
			//设置参数  
	        String str = EntityUtils.toString(new UrlEncodedFormEntity(params, entityEncoding));
	        httpget.setURI(new URI(httpget.getURI().toString() + "?" + str));
	        //发送请求  
	        HttpResponse httpresponse = hc.execute(httpget);
	        //获取返回数据 
	        HttpEntity entity = httpresponse.getEntity();  
	        body = EntityUtils.toByteArray(entity);
	
	        if (entity != null) {
	        	entity.consumeContent();  
	        }
	
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {  
			e.printStackTrace();
		} catch (IOException e) {  
			e.printStackTrace();
		} catch (URISyntaxException e) {  
			e.printStackTrace();
		}
		return body;
	}
	
	/**
	 * 模拟Get方式的Http请求
	 * @param url 请求地址
	 * @param params 请求参数
	 * @param responseEncoding 请求页面的响应流编码
	 * @param returnEncoding 最终返回值的编码
	 * @param entityEncoding 请求参数的编码
	 * @return
	 */
	public String doGet(String url, List<NameValuePair> params, String responseEncoding, String returnEncoding, String entityEncoding) {	
		String result = null;
		
		if(returnEncoding == null || returnEncoding.equals(""))
			returnEncoding = "UTF-8";
		
		if(params == null)
			params = new ArrayList<NameValuePair>();
		
		byte[] body = doGetReturnBytes(url, params, responseEncoding, entityEncoding);
		try {
			result = new String(body, returnEncoding);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
//		body = EntityUtils.toString(entity);
//    	body = new String(body.getBytes(responseEncoding), returnEncoding);
		return result;
	}
	/**
	 * 模拟Get方式的Http请求
	 * @param url 请求地址
	 * @param params 请求参数
	 * @return
	 */
	public String doGet(String url, List<NameValuePair> params) {
		return doGet(url, params, "UTF-8", "UTF-8", "UTF-8");
	}
	/**
	 * 模拟Get方式的Http请求
	 * @param url 请求地址
	 * @return
	 */
	public String doGet(String url) {
		return doGet(url, null, "UTF-8", "UTF-8", "UTF-8");
	}
    
	/**
	 * 模拟Post方式的Http请求
	 * @param url 请求地址
	 * @param params 请求参数
	 * @param responseEncoding 请求页面的响应流编码
	 * @param returnEncoding 最终返回值的编码
	 * @param entityEncoding 请求参数的编码
	 * @return
	 */
	public String doPost(String url, List<NameValuePair> params, String responseEncoding, String returnEncoding, String entityEncoding) {
		String body = null;
	
		if(responseEncoding == null || responseEncoding.equals(""))
		{
			responseEncoding = "UTF-8";
//			responseEncoding="ISO-8859-1";
		}
		
		if(returnEncoding == null || returnEncoding.equals(""))
			returnEncoding = "UTF-8";
		
		if(entityEncoding == null || entityEncoding.equals(""))
			entityEncoding = "UTF-8";		
		
		if(params == null)
			params = new ArrayList<NameValuePair>();
		
		try {
			//Post请求  
			HttpPost httppost = new HttpPost(url);  
			//设置参数  
			httppost.setEntity(new UrlEncodedFormEntity(params, entityEncoding)); 
			
			//发送请求  
			HttpResponse httpresponse = hc.execute(httppost);  
			//获取返回数据  
			HttpEntity entity = httpresponse.getEntity();  
			body = EntityUtils.toString(entity);
	    	body = new String(body.getBytes(responseEncoding), returnEncoding);
			
			if (entity != null) {  
				entity.consumeContent();
			}  
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
			return body;
	}
	/**
	 * 模拟Post方式的Http请求
	 * @param url 请求地址
	 * @param params 请求参数
	 * @return
	 */
	public String doPost(String url, List<NameValuePair> params) {
		return doPost(url, params, "UTF-8", "UTF-8", "UTF-8");
	}
	/**
	 * 模拟Post方式的Http请求
	 * @param url 请求地址
	 * @return
	 */
	public String doPost(String url) {
		return doPost(url, null, "UTF-8", "UTF-8", "UTF-8");
	}
	
	
	public static void main(String[] args) {
		test1();
		test2();
	}
	
	private static void test1(){
		String url = "http://blog.sina.com.cn/rss/2245739665.xml";
		String body = new ApacheHttpTool().doGet(url);	
		System.out.println(body);
	}
		
	private static void test2(){
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		
		params.add(new BasicNameValuePair("email", "xxx@gmail.com")); 
		params.add(new BasicNameValuePair("pwd", "xxx"));  
		params.add(new BasicNameValuePair("save_login", "1"));     
	
		String url = "http://www.oschina.net/action/user/login";
		String body = new ApacheHttpTool().doPost(url, params);
	
		System.out.println(body);  
	}
	
}
