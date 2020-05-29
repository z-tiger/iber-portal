package com.iber.portal.quartz;

import com.iber.portal.common.HttpUtils;
import com.iber.portal.common.HttpsClientUtil;
import com.iber.portal.dao.member.MemberContributedDetailMapper;
import com.iber.portal.enums.MemberBehaviorNameEnum;
import com.iber.portal.model.base.Member;
import com.iber.portal.model.member.MemberContributedDetail;
import com.iber.portal.model.sys.SysParam;
import com.iber.portal.model.timeShare.TimeShareOrder;
import com.iber.portal.service.base.MemberService;
import com.iber.portal.service.car.CarRescueService;
import com.iber.portal.service.member.MemberReportService;
import com.iber.portal.service.sys.SysParamService;
import com.iber.portal.service.timeShare.TimeShareOrderService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.*;

/**
 * 每个自然月，用户使用车辆，每笔订单及时支付，无不文明行为，无事故无剐蹭，奖励贡献值200
 * @author xuyaqiao	
 * @date 2017-05-16
 *
 */
public class MemberContributedIncrease  extends QuartzJobBean{

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SysParamService sysParamService;
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private TimeShareOrderService timeShareOrderService;
	
	@Autowired
	private MemberReportService memberReportService;
	
	@Autowired
	private CarRescueService carRescueService;
	
	@Autowired
	private MemberContributedDetailMapper memberContributedDetailMapper;
	
	
	@Override
	protected void executeInternal(JobExecutionContext context)              
			throws JobExecutionException {
			queryUseNorm();
		
	}
	private void queryUseNorm() {
		log.info("开始查询使用车辆规范");
		//首先查询job是否已经执行(查看数据中是否有这条数据)
		List<MemberContributedDetail> contributedDetaillist = memberContributedDetailMapper.queryIsHave();
		if(contributedDetaillist == null || contributedDetaillist.size()== 0){
			//查询一个月类所有的会员及时支付订单（支付时间在订单完成30分钟内的)
			List<TimeShareOrder> list = timeShareOrderService.getAllOrderInfo();
			//新建一个不符合条件的会员id集合
			Set<Integer> unAccordWith = new HashSet<Integer>();
			//新建一个符合条件的会员id集合
			Set<Integer> memberIdList = new HashSet<Integer>();
			//遍历订单集合查询改订单或者用户是否有违章，是否有不文明用车，救援，事故
			for(TimeShareOrder entity:list){
				if(!unAccordWith.contains(Integer.valueOf(entity.getMemberId()))){
					//查询是否有不文明用车,违章用车
					int  nums = memberReportService.selectByOrderId(entity.getOrderId());
					if(nums==0){
							//查询是否有救援，事故
							Map<String,Object> paramMap = new HashMap<String,Object>();
							paramMap.put("lpn", entity.getLpn());
							paramMap.put("to",entity.getBeginTime());
							paramMap.put("for", entity.getEndTime());
							int number = carRescueService.selectByLpn(paramMap);
							if(number == 0){
								
							}else{
								unAccordWith.add(Integer.valueOf(entity.getMemberId()));
							}
						
					}else{
						unAccordWith.add(Integer.valueOf(entity.getMemberId()));
					}
				}
				memberIdList.add(Integer.valueOf(entity.getMemberId()));
			}
			
			//遍历符合条件的会员id集合，给用户增加贡献值
			Iterator<Integer> it = memberIdList.iterator();
			while (it.hasNext()) {
				Integer memberId = it.next();
				if(!unAccordWith.contains(memberId)){
					SysParam sysParam = sysParamService.selectByKey("http_url");
					String url = "";
					if(sysParam != null)url = sysParam.getValue();
					//根据member_id查询member信息
					Member member = memberService.selectByPrimaryKey(memberId);
				    insertMemberContributedDetailByBehavior(
							url,"{'memberId':'"+memberId+"','objId':'','typeEnum':'"+MemberBehaviorNameEnum.MEMBER_CULTURE_USECAR.getName()+"','multiplicand':'','createId':''}",
							memberId+"", member.getPhone(),"insertMemberContributedDetailByBehavior");
				}
			}
		}
		
		log.info("结束查询使用车辆规范");
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
