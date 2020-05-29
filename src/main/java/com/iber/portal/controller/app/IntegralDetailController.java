package com.iber.portal.controller.app;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.iber.portal.advices.SystemServiceLog;
import com.iber.portal.controller.MainController;
import com.iber.portal.dao.base.IntegralDetailMapper;
import com.iber.portal.vo.member.MemberBehaviorVo;

/**
 * 积分详情页面
 */
@Controller
public class IntegralDetailController extends MainController {
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private IntegralDetailMapper integralDetailMapper;

	/**
	 * 积分详情页面
	 */

	@SystemServiceLog(description = "积分详情页面")
	@RequestMapping(value = "/integralDetail")
	public String integralDetail(HttpServletRequest request,
			HttpServletResponse response) {
		log.info("积分详情页面");
		/** 获取所有加分项 */
		List<MemberBehaviorVo> BehaviorList = integralDetailMapper.getAllAddBehaviorInfo();
		Integer index = 0;
		MemberBehaviorVo vo = new MemberBehaviorVo();
		for (MemberBehaviorVo memberBehaviorVo : BehaviorList) {
			String behaviorName = memberBehaviorVo.getName();
			if(!behaviorName.contains("其它(不扣用户贡献值)")){
				index++;
			}
			else {
				vo = memberBehaviorVo;
			}
			if(behaviorName.contains("事故责任方为对方(不扣用户贡献值)")){
			    behaviorName = behaviorName.replace("(不扣用户贡献值)", "");
			    memberBehaviorVo.setName(behaviorName);
			}
		}
		BehaviorList.remove(vo);
		HttpSession session = request.getSession();
		session.setAttribute("BehaviorList", BehaviorList);
		return "app/jsp/integralDetail";
	}
}
