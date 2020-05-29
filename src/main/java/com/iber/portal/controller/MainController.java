package com.iber.portal.controller;

import com.iber.portal.common.CommonUtil;
import com.iber.portal.common.HttpUtils;
import com.iber.portal.common.HttpsClientUtil;
import com.iber.portal.controller.base.BaseController;
import com.iber.portal.model.base.City;
import com.iber.portal.model.sys.SysUser;
import com.iber.portal.util.DateTime;
import com.iber.portal.util.SendSMS;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

public class MainController extends BaseController {

	/**
	 * @describe 异常错误消息
	 * 
	 * @author ruanpeng
	 * @date 2014年6月30日
	 * @param
	 * @return
	 */
	public void addError(String errMsg, Exception e, HttpServletRequest request) {
		e.printStackTrace();
		LOGGER.error(errMsg, e);
		request.setAttribute("err", errMsg);
	}

	/**
	 * @describe 对用JS escape的URL进行解码
	 * 
	 * @author ruanpeng
	 * @date 2014年6月30日
	 * @param
	 * @return
	 */
	public String urlDecode(String url) throws Exception {
		// return URLDecoder.decode(url, "UTF-8");
		return url;
	}


	/**
	 * @describe 获取调用端IP地址
	 * 
	 * @auther ruanpeng
	 * @date 2014年8月14日
	 * @param request
	 * @return
	 */
	public String getRemortIP(HttpServletRequest request) {
		if (request.getHeader("x-forwarded-for") == null) {
			return request.getRemoteAddr();
		}
		return request.getHeader("x-forwarded-for");
	}
	
	/**
	 * 获取session中的登陆用户信息
	 * @param request
	 * @return
	 */
	public SysUser getUser(HttpServletRequest request){
		HttpSession sess = request.getSession();
		return (SysUser) sess.getAttribute("user");
	}
	
	/**
	 * 获取城市信息
	 * @param request
	 * @param cityCode
	 * @return
	 */
	public City getCity(HttpServletRequest request, String cityCode){
		HttpSession sess = request.getSession();
		List<City> data = (List<City>) sess.getAttribute("city");
		City city = null;
		if(!data.isEmpty()){
			for(City c : data){
				if(c.getCode().equals(cityCode)){
					city = c;
					break;
				}
			}
		}
		return city;
	}
	
	public String insertMemberContributedDetailByBehavior(String url,String jsonParam,String memberId,String phone,String method){
		String json = "{}";
		StringBuffer sb = new StringBuffer("{") ;
		sb.append("\"cId\":\"platForm\",\"memberId\":\""+memberId+"\",") ;
		sb.append("\"method\":\""+method+"\",") ;
		sb.append("\"param\":\""+jsonParam+"\",") ;
		sb.append("\"phone\":\""+phone+"\",\"type\":\"platForm\",\"version\":\"1\"") ;
		sb.append("}") ;
		LOGGER.info("参数:"+sb);
		if(url.indexOf("https") == 0){ //https接口
			json = HttpsClientUtil.get(url, sb.toString()) ;
		}else{
			json = HttpUtils.commonSendUrl(url, sb.toString()) ;
		}
		LOGGER.info("返回结果:"+json);
		return json;
	}
	
	public String sendSMS(String url,String telephoneNo,String ipAddress,String templateId,String contentParam) throws Exception{
		//获取加密token
		/*String encryptToken = SendSMS.encryptBySalt(telephoneNo);
		Map<String, String> paramss = new HashMap<String, String>();
		String param = "{\"telephoneNo\":\""+ telephoneNo+ "\",\"ipAddress\":\""+ipAddress+"\",\"templateId\":\""+templateId+"\",\"contentParam\":\""+ new String(contentParam.getBytes("utf-8"),"ISO-8859-1") + "\",\"token\":\""+encryptToken+"\"}";
		paramss.put("msgContentJson", param);*/

		SendSMS.send(telephoneNo,ipAddress,Integer.parseInt(templateId),contentParam);

		return "";
	}

	/**
	 * 成功返回
	 * lf
	 * 2017-11-13 17:33:28
	 * @param result
	 * @return
	 */
	protected Map<String,Object> success(Object result){
		return CommonUtil.success(result);
	}

	/**
	 * 成功返回
	 * lf
	 * 2017-11-13 17:33:28
	 * @return
	 */
	protected Map<String,Object> success(){
		return CommonUtil.success();
	}

	/**
	 * 失败返回
	 * lf
	 * 2017-11-13 17:33:38
	 * @param msg
	 * @return
	 */
	protected Map<String,Object> fail(String msg){
		return CommonUtil.fail(msg);
	}

	/**
	 * 失败返回
	 * lf
	 * 2017-11-13 17:33:38
	 * @return
	 */
	protected Map<String,Object> paramFail(){
		return CommonUtil.paramFail();
	}

	/**
	 * 发送错误
	 * lf
	 * 2017-11-13 17:33:54
	 * @return
	 */
	protected Map<String,Object> error(){
		return CommonUtil.ERROR_RESULT;
	}

    /**
     * 判断是否是空
     */
    protected boolean isEmpty(Object obj){
        return CommonUtil.isEmpty(obj);
    }
    /**
     * 判断不是空
     */
    protected boolean isNotEmpty(Object obj){
        return !CommonUtil.isEmpty(obj);
    }

    /**
     * 从HttpServletRequest获取参数的int值
     * @param request
     * @param paramName
     * @return
     * @author ouxx
     * @date 2016-11-23 上午9:12:04
     */
    public Integer getIntReqParam(HttpServletRequest request, String paramName){
        String str = request.getParameter(paramName);
        Integer intRst = null;
        if(StringUtils.isNotBlank(str)){
            intRst = Integer.valueOf(str);
        }
        return intRst;
    }

    public Timestamp getTimestampReqParam(HttpServletRequest request, String paramName){
        Timestamp time = null;
        String timeStr = request.getParameter(paramName);
        if (StringUtils.isNotBlank(timeStr)) {
            time = DateTime.getDateTime(timeStr);
        }
        return time;
    }
}
