package com.iber.portal.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

public class HttpUtils {


	/**
	 * 向指定URL发送GET方法的请求
	 * 
	 * @param url
	 *            发送请求的URL
	 * @param param
	 *            请求参数，请求参数应该是name1=value1&name2=value2的形式。
	 * @return URL所代表远程资源的响应
	 */

	public static String sendGet(String url,String param) {
		String result = ""; //$NON-NLS-1$
		BufferedReader in = null;
		try {
//			String urlName = url + "?" + param; //$NON-NLS-1$
			String urlName = url + URLEncoder.encode(param,"utf-8") ; //$NON-NLS-1$
			URL realUrl = new URL(urlName);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*"); //$NON-NLS-1$ //$NON-NLS-2$
			conn.setRequestProperty("connection", "Keep-Alive"); //$NON-NLS-1$ //$NON-NLS-2$
			conn.setRequestProperty("user-agent", //$NON-NLS-1$
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)"); //$NON-NLS-1$
			// 建立实际的连接
			conn.connect();
			// 获取所有响应头字段
			Map<String, List<String>> map = conn.getHeaderFields();
			// 遍历所有的响应头字段
//			for (String key : map.keySet()) {
//				System.out.println(key + "--->" + map.get(key)); //$NON-NLS-1$
//			}
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(
					new InputStreamReader(conn.getInputStream(),"utf-8"));
			String line;
			while ((line = in.readLine()) != null) {
				result += "\n" + line; //$NON-NLS-1$
			}
		} catch (Exception e) {
			System.out.println("发送GET请求出现异常！" + e); //$NON-NLS-1$
			e.printStackTrace();
		}
		// 使用finally块来关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 向指定URL发送POST方法的请求
	 * 
	 * @param url
	 *            发送请求的URL
	 * @param param
	 *            请求参数，请求参数应该是name1=value1&name2=value2的形式。
	 * @return URL所代表远程资源的响应
	 */
	public static String sendPost(String url, String param) {
		OutputStreamWriter out = null;
		BufferedReader in = null;
		String result = ""; //$NON-NLS-1$
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*"); //$NON-NLS-1$ //$NON-NLS-2$
			conn.setRequestProperty("connection", "Keep-Alive"); //$NON-NLS-1$ //$NON-NLS-2$
			conn.setRequestProperty("user-agent", //$NON-NLS-1$
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)"); //$NON-NLS-1$
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8"); //$NON-NLS-1$
			// 发送请求参数
			out.write(param);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(
					conn.getInputStream(), "utf-8")); //$NON-NLS-1$
			String line;
			while ((line = in.readLine()) != null) {
				result += line; //$NON-NLS-1$
			}
		} catch (Exception e) {
			System.out.println("发送POST请求出现异常！" + e); //$NON-NLS-1$
			e.printStackTrace();
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}

	public String post(String strURL, String params) {
		System.out.println(strURL);
		System.out.println(params);
		try {
			URL url = new URL(strURL);// 创建连接
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection.setRequestMethod("POST"); // 设置请求方式 //$NON-NLS-1$
			connection.setRequestProperty("Accept", "application/json"); // 设置接收数据的格式 //$NON-NLS-1$ //$NON-NLS-2$
			connection.setRequestProperty("Content-Type", "application/json"); // 设置发送数据的格式 //$NON-NLS-1$ //$NON-NLS-2$
			connection.connect();
			OutputStreamWriter out = new OutputStreamWriter(
					connection.getOutputStream(), "UTF-8"); // utf-8编码 //$NON-NLS-1$
			out.append(params);
			out.flush();
			out.close();
			// 读取响应
			InputStream in = connection.getInputStream();
			StringBuffer bf = new StringBuffer();
			byte[] data = null;
			data = new byte[in.available()];
			int b = 0;
			while ((b = in.read(data)) != -1) {
				String tzt = new String(data, "utf-8"); //$NON-NLS-1$
				bf.append(tzt);
			}
			in.close();
			connection.disconnect();
			System.out.println(bf.toString());
			return bf.toString();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "error"; // 自定义错误信息 //$NON-NLS-1$
	}
	
	/**
	 * @Description: 公共提交代码
	 *  @param Url 路径
	 *  @param datas    数据
	 * @return void  
	 * @throws
	 * @author 
	 * @date 2015-7-17
	 */
	public static String commonSendUrl(String Url,String param){
	   return sendGet(Url,param) ;
	}
	
	public static void main(String[] args) {
		System.err.println(commonSendUrl("http://192.168.10.33/services/i/e/","{\"cId\":\"1\",\"memberId\":\"1\",\"method\":\"queryTimeShareUsableCar\",\"param\":\"{'cityCode':'440300'}\",\"phone\":\"1\",\"type\":\"1\",\"version\":\"1\"}"));
	}
}
