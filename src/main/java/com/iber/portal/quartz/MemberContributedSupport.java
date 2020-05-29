package com.iber.portal.quartz;

import java.util.List;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.iber.portal.common.HttpUtils;
import com.iber.portal.common.HttpsClientUtil;
import com.iber.portal.enums.MemberBehaviorNameEnum;
import com.iber.portal.model.base.Member;
import com.iber.portal.model.sys.SysParam;
import com.iber.portal.service.base.MemberService;
import com.iber.portal.service.sys.SysParamService;

/**
 * 查询用户驾龄 当驾龄大于2年是 增加500贡献值
 * @author zixiaobang
 * @date 2017-04-24
 *
 */
public class MemberContributedSupport  extends QuartzJobBean{

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SysParamService sysParamService;
	
	@Autowired
	private MemberService memberService;
	
	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
		//统计驾龄大于一年的用户驾 龄≥365，贡献值=1500；驾龄<365，贡献值=0
		queryMemberDriver();
		
	}
	private void queryMemberDriver() {
		log.info("定时器开始查询驾龄大于1年的用户 ，并添加相应的贡献值");
		List<Member> members = memberService.queryMemberDriving2Year();
		for (Member member : members) {
			SysParam sysParam = sysParamService.selectByKey("http_url");
			String url = "";
			if(sysParam != null)url = sysParam.getValue();
			
			insertMemberContributedDetailByBehavior(
					url,"{'memberId':'"+member.getId()+"','objId':'','typeEnum':'"+MemberBehaviorNameEnum.DRIVING_1_YEAR.getName()+"','multiplicand':'','createId':''}",
					member.getId()+"", member.getPhone(),"insertMemberContributedDetailByBehavior");
		}
		log.info("定时器结束查询驾龄大于1年的用户 ，并添加相应的贡献值");
	}
	
	public String insertMemberContributedDetailByBehavior(String url,String jsonParam,String memberId,String phone,String method){
		log.info("调用接口更改用户("+memberId+")贡献值");
		String json = "{}";
		StringBuffer sb = new StringBuffer("{") ;
		sb.append("\"cId\":\"platForm\",\"memberId\":\""+memberId+"\",") ;
		sb.append("\"method\":\""+method+"\",") ;
		sb.append("\"param\":\""+jsonParam+"\",") ;
		sb.append("\"phone\":\""+phone+"\",\"type\":\"platForm\",\"version\":\"1\"") ;
		sb.append("}") ;
		if(url.indexOf("https") == 0){ //https接口
			json = HttpsClientUtil.get(url, sb.toString()) ;
		}else{
			json = HttpUtils.commonSendUrl(url, sb.toString()) ;
		}
		log.info("用户("+memberId+")调用结果:"+json);
		return json;
	}

}
