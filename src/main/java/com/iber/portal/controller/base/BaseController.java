package com.iber.portal.controller.base;

import com.iber.portal.common.CommonUtil;
import com.iber.portal.common.Page;
import com.iber.portal.model.sys.SysUser;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @auther Administrator
 * @date 2017/11/13
 * @description 基础controller
 */
@Controller
public class BaseController {

    protected static final Logger LOGGER = LoggerFactory.getLogger(BaseController.class);

    @Autowired
    protected HttpServletRequest request;

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
     * 获取page
     * lf
     * 2017-11-13 17:33:02
     */
    protected Page getPage(){
        final Integer page = getInteger(request,"page");
        final Integer pageSize = getInteger(request,"rows");
        return new Page(page,pageSize);
    }

    /**
     * 从请求参数中获取int值
     * lf
     * 2017-11-13 17:32:50
     * @param request
     * @param param
     * @return
     */
    private Integer getInteger(HttpServletRequest request,String param){
        final String parameter = request.getParameter(param);
        if (NumberUtils.isDigits(parameter)){
            return Integer.valueOf(parameter);
        }
        return 0;
    }

    /**
     * 从请求参数中获取int值
     * lf
     * 2017-11-13 17:32:50
     * @param param
     * @return
     */
    protected String getParam(String param){
        return request.getParameter(param);
    }

    /**
     * 从请求参数中获取int值
     * lf
     * 2017-11-13 17:32:50
     * @param param
     * @return
     */
    protected Integer getParamInt(String param){
        final String parameter = request.getParameter(param);
        if (NumberUtils.isDigits(parameter)){
            return Integer.valueOf(parameter);
        }
        return 0;
    }

    /**
     * 从请求参数中获取int值
     * lf
     * 2017-11-13 17:32:50
     * @param param
     * @return
     */
    protected Long getParamLong(String param){
        final String parameter = request.getParameter(param);
        if (NumberUtils.isDigits(parameter)){
            return Long.valueOf(parameter);
        }
        return 0L;
    }

    /**
     * 从请求参数中获取int值
     * lf
     * 2017-11-13 17:32:50
     * @param param
     * @return
     */
    protected Double getParamDoule(String param){
        final String parameter = request.getParameter(param);
        return CommonUtil.getDouble(parameter);
    }

    /**
     * 获取session中的登陆用户信息
     * @return
     */
    public SysUser getUser(){
        HttpSession session = request.getSession();
        return (SysUser) session.getAttribute("user");
    }

    /**
     * 获取session中的登陆用户信息
     * @return
     */
    public Integer getUserId(){
        return getUser().getId();
    }


}
