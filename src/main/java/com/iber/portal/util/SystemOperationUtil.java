package com.iber.portal.util;

import javax.servlet.http.HttpServletRequest;

import com.iber.portal.model.sys.SysUser;

public class SystemOperationUtil {
    public static SysUser getSysUser(HttpServletRequest request){
    	
    	return (SysUser) request.getSession().getAttribute("user");
    	
    }
}
