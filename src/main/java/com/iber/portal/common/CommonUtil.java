package com.iber.portal.common;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.iber.portal.controller.base.BaseController;
import net.sf.json.JSONObject;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.formula.functions.T;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.number.NumberFormatter;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * 基础工具类
 * 
 * @author lf
 * 
 */
public class CommonUtil {

	public static final Set<String> EMPTY_STRING_SET = Sets.newHashSetWithExpectedSize(0);

	/** 结果字符串 */
	private static final String MSG = "message";
	private static final String STATUS = "status";
	private static final String CODE = "code";
	private static final String RESULT = "result";

	/** 成功提示 */
	private static final String STATUS_SUCCESS = "成功";
	private static final int CODE_SUCCESS = 1;

	/** 失败提示 */
	private static final String STATUS_FAIL = "失败";
	private static final int CODE_FAIL = 0;

	/** 错误提示 */
	private static final String STATUS_ERROR = "系统繁忙";
	private static final int CODE_ERROR = -1;

	private static final String PARAM_ERROR_MSG = "请求参数不正确！";

	public static final Map<String,Object> ERROR_RESULT = Maps.newHashMap();

	static {
		ERROR_RESULT.put(STATUS,STATUS_ERROR);
		ERROR_RESULT.put(CODE,CODE_ERROR);
		ERROR_RESULT.put(MSG,STATUS_ERROR);
	}

	/**
	 * 成功返回
	 * lf
	 * 2017-11-13 17:33:28
	 * @param result
	 * @return
	 */
	public static Map<String,Object> success(Object result){
		Map<String,Object> map = Maps.newHashMap();
		map.put(MSG,"");
		map.put(STATUS, STATUS_SUCCESS);
		map.put(CODE,CODE_SUCCESS);
		map.put(RESULT,result);
		return map;
	}

	/**
	 * 成功返回
	 * lf
	 * 2017-11-13 17:33:28
	 * @return
	 */
	public static Map<String,Object> success(){
		Map<String,Object> map = Maps.newHashMap();
		map.put(MSG,"");
		map.put(STATUS, HttpStatus.OK);
		map.put(CODE,CODE_SUCCESS);
		return map;
	}

	/**
	 * 失败返回
	 * lf
	 * 2017-11-13 17:33:38
	 * @param msg
	 * @return
	 */
	public static Map<String,Object> fail(String msg){
		Map<String,Object> map = Maps.newHashMap();
		map.put(MSG,msg);
		map.put(STATUS,STATUS_FAIL);
		map.put(CODE,CODE_FAIL);
		return map;
	}

	/**
	 * 失败返回
	 * lf
	 * 2017-11-13 17:33:38
	 * @return
	 */
	public static Map<String,Object> paramFail(){
		return fail(PARAM_ERROR_MSG);
	}

	/**
	 * 发送错误
	 * lf
	 * 2017-11-13 17:33:54
	 * @return
	 */
	public static Map<String,Object> error(){
		return ERROR_RESULT;
	}

	/**
	 * 结果是否是成功
	 * @param map
	 * @return
	 */
	public static boolean isSuccess(Map<String,Object> map){
		if (CollectionUtils.isEmpty(map)) return false;
		final Object code = map.get(CODE);
		return Objects.equals(code,CODE_SUCCESS);
	}

	/**
	 * 获取失败的信息
	 * lf
	 * 2017年11月17日
	 * @param map
	 * @return
	 */
	public static String getFailMsg(Map<String,Object> map){
		if (CollectionUtils.isEmpty(map)) return "";
		return Objects.toString(map.get(MSG),"");
	}

	/**
	 * 将null不是的数字转化为0
	 * 
	 * @param num
	 * @return
	 */
	public static Integer nullToZero(Integer num) {
		if (num == null) {
			return 0;
		} else {
			return num;
		}
	}

	/**
	 * 将null不是的数字转化为0
	 * 
	 * @param num
	 * @return
	 */
	public static Long nullToZero(Long num) {
		if (num == null) {
			return 0L;
		} else {
			return num;
		}
	}

	/**
	 * 将null不是的数字转化为0
	 * 
	 * @param num
	 * @return
	 */
	public static Double nullToZero(Double num) {
		if (num == null) {
			return 0.0;
		} else {
			return num;
		}
	}

	/**
	 * 将分转换为元
	 * 
	 * @param num
	 *            分
	 * @return
	 */
	public static double fenToYuan(Integer num) {
		return nullToZero(num) / SysConstant.HUNDRED_FLOAT;
	}

	/**
	 * 将分转换为元,并保留两位小数
	 * 
	 * @param num 分
	 * @param decimal 保留小数点位数
	 * @return
	 */
	public static Double fenToYuanDecimal (Integer num,int decimal){
		BigDecimal b = new BigDecimal(fenToYuan(num));
		decimal = decimal < 0 ? 0 : decimal;
		return b.setScale(decimal,BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * 将分转换为元
	 * 
	 * @param num
	 *            分
	 * @return
	 */
	public static double fenToYuan(Long num) {
		return nullToZero(num) / SysConstant.HUNDRED_FLOAT;
	}
	
	
	/**
	 * 将分转换为元,并保留两位小数
	 * 
	 * @param num 分
	 * @param decimal 保留小数点位数
	 * @return
	 */
	public static Double fenToYuanDecimal (Long num,int decimal){
		BigDecimal b = new BigDecimal(fenToYuan(num));
		decimal = decimal < 0 ? 0 : decimal;
		return b.setScale(decimal,BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	public static String toString(Object obj){
		return obj == null ? "":obj.toString();
	}

	/**
	 * 判读对象是否为空
	 * lf
	 * 2017-11-13 16:41:28
	 * @param obj
	 * @return
	 */
	public static boolean isEmpty(Object obj){
		return StringUtils.isBlank(toString(obj));
	}

	/**
	 * 获取int值
	 * lf
	 * 2017-11-13 16:43:52
	 * @param obj
	 * @return
	 */
	public static Integer getInteger(Object obj){
		return isEmpty(obj) ? 0 : Integer.valueOf(obj.toString());
	}

	/**
	 * 获取long值
	 * lf
	 * 2017-11-13 16:43:52
	 * @param obj
	 * @return
	 */
	public static Long getLong(Object obj){
		return isEmpty(obj) ? 0L : Long.valueOf(obj.toString());
	}

	/**
	 * 获取double值
	 * lf
	 * 2017-11-13 16:43:52
	 * @param obj
	 * @return
	 */
	public static double getDouble(Object obj){
		return isEmpty(obj) ? 0.0 : Double.valueOf(obj.toString());
	}

	/**
	 * 检查int值
	 * lf
	 * 2017-11-13 16:43:52
	 * @param obj
	 * @return
	 */
	public static boolean checkInt(Integer obj){
		return obj == null || obj < 0;
	}

	/**
	 * 发送http请求调用接口
	 * lf
	 * 2017年11月16日
	 * @param url
	 * @param params
	 * @return
	 */
	public static Map<String, Object> httpInterfacePost(String url, Map<String, Object> params) {
		String json;
		if (StringUtils.isBlank(url)){
			return CommonUtil.fail("服务接口参数没有配置！");
		}
		if(url.indexOf("https") == 0){ //https接口
			json = HttpsClientUtil.doPostSSL(url, JSON.toJSONString(params)) ;
		}else{
			json = HttpsClientUtil.doPost(url, JSON.toJSONString(params)) ;
		}
		JSONObject jsonObject = JSONObject.fromObject(json) ;
		String code = jsonObject.getString("code") ;
		if(SysConstant.INTERFACE_RESULT_SUCCESS.equals(code)){ // 成功
			return CommonUtil.success();
		}else{
			return CommonUtil.fail(jsonObject.getString("msg"));
		}
	}
}
