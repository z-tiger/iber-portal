package com.iber.portal.controller.app;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.iber.portal.advices.SystemServiceLog;
import com.iber.portal.controller.MainController;
import com.iber.portal.dao.base.SystemMsgLogMapper;
import com.iber.portal.exception.ServiceException;
import com.iber.portal.getui.PushClient;
import com.iber.portal.getui.PushCommonBean;
import com.iber.portal.model.app.Result;
import com.iber.portal.model.base.Member;
import com.iber.portal.model.base.SystemMsgLog;
import com.iber.portal.model.coupon.Coupon;
import com.iber.portal.model.coupon.CouponLog;
import com.iber.portal.service.base.MemberService;
import com.iber.portal.service.coupon.CouponLogService;
import com.iber.portal.service.coupon.CouponService;

@Controller
public class NumberRobCounponsController extends MainController{
	
private final Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired(required=false) 
	private CouponService couponService; 
	
	@Autowired(required=false) 
	private CouponLogService couponLogService;  
	
	@Autowired
	private SystemMsgLogMapper systemMsgLogService;
	 
	@Autowired(required=false) 
	private MemberService memberService  ; 
	@SystemServiceLog(description="手机领取优惠券页面")
	@RequestMapping("/showNumberRobCounpons") 
	public String counponPage(HttpServletRequest request, HttpServletResponse response){
		log.info("手机领取优惠券页面");
		return "app/jsp/numberRobCounpons";
	}
	@SystemServiceLog(description="领取优惠券")
	@RequestMapping(value = "/goNumberRobCounpons", method = { RequestMethod.GET ,RequestMethod.POST })
	@ResponseBody
	public   synchronized Result boundCounpon(HttpServletRequest request, HttpServletResponse response) throws IOException, ServiceException{
		log.info("领取优惠券");
		response.setContentType("text/html;charset=utf-8");	
		String number = request.getParameter("number");
		String batchNo = request.getParameter("batchNo");
		Result result = new Result();
		//1.判断批次号是否存在
		int index = couponLogService.queryByBatchNo(batchNo);
		if(index==0){
			result.setStatus(1);
			result.setMsg("领取失败，批次号不存在！");
			return result ;
		}
		//2.判断手机号码是否已经领取过
		Member member = memberService.selectDetailByPhone(number);
		if(member==null){
			result.setStatus(2);
			result.setMsg("领取失败，会员不存在！");
			return result ;
		}
		
		//判断该用户是否已经领取了此批次号下的优惠券  批次号 + 会员ID 》 0
		int id = member.getId();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("memberId", id);
		paramMap.put("batchNo", batchNo);
		int sum = couponService.findById(paramMap);
		if(sum>0){
			result.setStatus(3);
			result.setMsg("领取失败，已经领取过了!");
			return result;
		}
		// 3. 绑定优惠券
		//通过批次号查询出该批次号下所有未被领取的优惠券列表  批次号 + 状态未被领取  list . get(0)
		List<Coupon> uncollectedCouponList =  couponService.getUncollectedByBatchNo(batchNo);//未领取的、有效的优惠券
		
		//判断优惠券是否超过限度
		if(uncollectedCouponList.size()==0){
			result.setStatus(4);
			result.setMsg("领取失败，优惠券已领完。");
			return result;
		}
		Coupon coupon =uncollectedCouponList.get(0);
		coupon.setStatus("1");
		coupon.setMemberId(member.getId());
		int rst = 0;
		rst = couponService.updateByPrimaryKeySelective(coupon);
		Date now = new Date();
		//发放优惠券记录
		CouponLog couLog = new CouponLog();
		couLog.setBatchno(batchNo);
		couLog.setCouponno(coupon.getCouponNo());
		couLog.setCreateid(null);
		couLog.setCreatetime(now);
		couLog.setMemberid(member.getId());
		couLog.setStatus(1);
		couponLogService.insert(couLog);
		if(0 != rst){
			result.setStatus(0);
			result.setMsg("领取成功！");
			//将消息保存
			SystemMsgLog systemMsgLog = new SystemMsgLog();
			systemMsgLog.setMsgType("coupon");
			systemMsgLog.setCreateTime(new Date());
			systemMsgLog.setMemberId( member.getId());
			systemMsgLog.setMsgTitle("优惠券");
			systemMsgLog.setMsgContent("尊敬的会员，" + (coupon.getBalance() / 100) + "元(折)面值优惠券已经送到您账户，请注意查收！");
			systemMsgLogService.insertSelective(systemMsgLog);
			//发送推送消息
			PushCommonBean push = new PushCommonBean("server_push_bing_coupon",
					"1", "尊敬的会员，" + (coupon.getBalance() / 100) + "元(折)面值优惠券已经送到您账户，请注意查收！","") ;
			String memberPhone = memberService.getPhoneById(coupon.getMemberId());//获取会员手机号
			List<String> cidList = PushClient.queryClientId(memberPhone);
			for (String memberCid : cidList) {
				PushClient.push(memberCid, JSONObject.fromObject(push));
			}
		}else {
			result.setStatus(5);
			result.setMsg("领取失败！");
		}			
		return result ;
	}
}
