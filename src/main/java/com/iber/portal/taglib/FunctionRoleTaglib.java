package com.iber.portal.taglib;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.iber.portal.model.sys.SysUser;
import com.iber.portal.service.sys.SysUserModuService;

/**
 * 用户所拥有的功能权限taglib
 * @author tc
 *
 */
public class FunctionRoleTaglib extends BodyTagSupport{
	
    private static Logger logger = Logger.getLogger(FunctionRoleTaglib.class);
	private static final long serialVersionUID = 6848692680914307104L;
	private String functionRoleId;
	private String notRoleDisplayStr;//无权限的时候，显示的字符串文字
	
	
	
	public void setFunctionRoleId(String functionRoleId) {
		this.functionRoleId = functionRoleId;
	}
	
	public String getFunctionRoleId() {
		return functionRoleId;
	}
	
	public String getNotRoleDisplayStr() {
		return notRoleDisplayStr;
	}

	public void setNotRoleDisplayStr(String notRoleDisplayStr) {
		this.notRoleDisplayStr = notRoleDisplayStr;
	}

	@SuppressWarnings("all")
	@Override	
	public int doStartTag() throws JspException {
		// 获取原来的bodyn内容
		BodyContent  bodyTxt = getBodyContent();
		//获取登录用户信息
		SysUser user = (SysUser) this.pageContext.getSession().getAttribute("user"); //获取用户登陆信息
		List<String> funcList = (List<String>) this.pageContext.getSession().getAttribute("funcList");
		Set<String> functionSet = new HashSet<String>();
		//判断用户是否有操作上的功能
		if (funcList!=null&&funcList.size() > 0) {
			String[] functionStrings = funcList.toString().split(",");
			for (String funString : functionStrings) {
				String[] splitStrings = funString.split(":");
				if (splitStrings.length == 2) {
					functionSet.add(splitStrings[1].substring(0, splitStrings[1].indexOf("}")));
				}
			}
		}
		
		List<String> roleFunctions = (List<String>) this.pageContext.getSession().getAttribute("roleFunctions");
		//判断用户是否有角色
		if (roleFunctions.size() > 0) {
			String[] splitRoleFunction = roleFunctions.toString().split(",");
			for (String roleFunction : splitRoleFunction) {
				String[] splitStrings = roleFunction.split(":");
				if (splitStrings.length == 2) {
					functionSet.add(splitStrings[1].substring(0, splitStrings[1].indexOf("}")));
				}
				
			}
		}
		
		boolean result = false;
		for (String function : functionSet) {
				if (function.equals('"'+functionRoleId+'"')) {
					result = true;
				}
		}
		return result? EVAL_BODY_INCLUDE : SKIP_BODY;
	}
}
