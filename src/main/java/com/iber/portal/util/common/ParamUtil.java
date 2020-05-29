package com.iber.portal.util.common;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.iber.portal.common.HttpUtils;
import com.iber.portal.common.HttpsClientUtil;
import com.iber.portal.model.sys.SysParam;
import com.iber.portal.service.sys.SysParamService;
import com.iber.portal.util.FastJsonUtils;
import com.iber.portal.zhima.vo.ZhimaRequestVo;

public class ParamUtil {
	
	public static void getParam(String mark, String billStatus,String billType,String payStatus,String orderStatus,SysParamService sysParamService){
		Map<String, Object> methodParamMap = new HashMap<String, Object>();
		methodParamMap.put("zhimaRequestVos", mark);
		methodParamMap.put("billStatus", billStatus);
		methodParamMap.put("billType", billType);
		methodParamMap.put("payStatus", payStatus);
		methodParamMap.put("orderStatus", orderStatus);
		String methodParams = FastJsonUtils.toJson(methodParamMap).replaceAll("\"", "'");
		Map<String, String> httpMap = new HashMap<String, String>();
		httpMap.put("cId", "platForm");
		httpMap.put("memberId", "");
		httpMap.put("method", "zhimaDataFeedbackByPlatform");
		httpMap.put("param", methodParams);
		httpMap.put("phone", "");
		httpMap.put("type", "platForm");
		httpMap.put("version", "1");
		String params = FastJsonUtils.toJson(httpMap);
		SysParam sysParam = sysParamService.selectByKey("http_url") ;
		String json = "";
		if(sysParam.getValue().indexOf("https") == 0){ //https接口
			json = HttpsClientUtil.get(sysParam.getValue(), params) ;
		}else{
			json = HttpUtils.commonSendUrl(sysParam.getValue(), params) ;
		}
		JSONObject jsonObject = JSONObject.fromObject(json);
	}
}
