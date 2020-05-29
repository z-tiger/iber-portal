package com.iber.portal.common;

import com.iber.portal.dao.sys.SysUserMapper;
import com.iber.portal.model.sys.SysUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

public class SessionTimeOutInterceptor extends HandlerInterceptorAdapter {
	@Autowired
	SysUserMapper sysUserMapper;

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

//		String logStr = "";
		//不被拦截的url
		String[] noFilters = new String[] { "index.jsp", "makeCertPic.jsp", "SessionTimeOut.html",
				"ui_lib","app", "images", "login", "prices", "showFAQ", "showNetworkIntroduced", "showRentGuide","showNumberRobCounpons","goNumberRobCounpons","guide.html"
				,"dailyRentalDetail","parkReservationDetail","parkReservationDetail","memberRight","discount","dailyRentalPrices","showUserGuide","userGuide.html","parkLock","addLpn"
				,"memberBirthdayCoupon","memberDiscountGiftBag","integralDetail","depositRule","serviceTerms","chooseCar","lottery.html","open","PasswordChanged.html","FrozenAccount.html","LoginElsewhere.html","modifyPwd.jsp","rechargeAgreement.jsp"};
		String uri = request.getRequestURI();
		HttpSession session = request.getSession();


		if(uri.indexOf("pmsg") != -1){
			return super.preHandle(request, response, handler);
		}

		boolean beFilter = true;
		for (String s : noFilters) {
			if (uri.indexOf(s) != -1) {
				beFilter = false;
				break;
			}
		}
		if (beFilter) {

			Object obj = session.getAttribute("login_status");

//			logStr += (String)obj;

//			logStr += " , x-requested-with = " + request.getHeader("x-requested-with");

//			log.info(logStr);

			if (null == obj) { // 未登录
					redirect(request, response, "/SessionTimeOut.html", "timeout");
				return false;
			}else{
				SysUser user = (SysUser) request.getSession().getAttribute("user");
				SysUser userDb = sysUserMapper.selectByPrimaryKey(user.getId());
				if(!user.getPassword().equals(userDb.getPassword())){
					redirect(request, response, "/PasswordChanged.html", "passwordChanged");
					return false;
				}
				if(!"1".equals(userDb.getStatus())){
//					response.sendRedirect(request.getContextPath() + "/modifyPwd.jsp");
					redirect(request, response, "/FrozenAccount.html", "frozen");
					return false;
				}

				String sessionChange = (String) session.getAttribute("sessionChange");
				if(!sessionChange.equals(userDb.getSessionChange())){
					redirect(request, response, "/LoginElsewhere.html", "loginElsewhere");
					return false;
				}

				boolean updateFilter = true;
				//不被拦截的url
				String[] updatePwdNoFilters = new String[] {"sys_modify_pwd","frozen_account"};
				for (String s : updatePwdNoFilters) {
					if (uri.indexOf(s) != -1) {
						updateFilter = false;
						break;
					}
				}

				Calendar cal = Calendar.getInstance();
				cal.setTime(new Date());
				cal.add(Calendar.MONTH, -1);
				boolean isMoreThanThreeMonth = (cal.getTime().getTime() >= userDb.getPasswordUpdateTime().getTime());
				if (isMoreThanThreeMonth && updateFilter ){
					redirect(request, response, "/modifyPwd.jsp", "passwordExpired");
					return false;
				}
			}
		}
		return super.preHandle(request, response, handler);
	}

	private void redirect(HttpServletRequest request, HttpServletResponse response, String path, String sessionStatus) throws IOException {
		if (request.getHeader("x-requested-with") != null
				&& request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")) { // 如果是ajax请求响应头会有，x-requested-with
			response.setHeader("sessionstatus", sessionStatus);// 在响应头设置session状态
		} else {
			response.sendRedirect(request.getContextPath() + path);
		}
	}
}