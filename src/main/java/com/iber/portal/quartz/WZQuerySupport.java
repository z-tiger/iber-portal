package com.iber.portal.quartz;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.iber.portal.common.HttpTool;
import com.iber.portal.common.SendMail;
import com.iber.portal.dao.activity.ActivityMapper;
import com.iber.portal.dao.base.*;
import com.iber.portal.dao.car.CarMapper;
import com.iber.portal.dao.car.CarRescueMapper;
import com.iber.portal.dao.member.MemberBehaviorMapper;
import com.iber.portal.dao.timeShare.TimeShareCancelMapper;
import com.iber.portal.dao.timeShare.TimeSharePayMapper;
import com.iber.portal.enums.MemberBehaviorNameEnum;
import com.iber.portal.getui.PushClient;
import com.iber.portal.getui.PushCommonBean;
import com.iber.portal.model.base.*;
import com.iber.portal.model.car.Car;
import com.iber.portal.model.member.MemberBehavior;
import com.iber.portal.model.timeShare.TimeShareCancel;
import com.iber.portal.model.timeShare.TimeSharePay;
import com.iber.portal.service.member.MemberContributedDetailService;
import com.iber.portal.service.sys.SysParamService;
import com.iber.portal.util.FastJsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

public class WZQuerySupport extends QuartzJobBean{
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private WZQueryLogMapper wzQueryLogMapper; //违章日志查询
	
	@Autowired
	private SysParamService  sysParamService;//系统参数查询
	
	@Autowired
	private WZQueryMapper wzQueryMapper;//违章记录

	@Autowired
	private TimeShareCancelMapper timeShareCancelMapper;//用以查连续用车情况
	
	@Autowired
	private WZCitysQueryMapper wzCitysQueryMapper;//违章城市对应表查询
	
	@Autowired
	private WZCitysMapper wzCitysMapper;//违章城市列表查询
	
	@Autowired
	private TimeSharePayMapper timeSharePayMapper; //查询订单
	
	@Autowired
	private CarMapper carMapper;//查询车辆信息
	
	@Autowired
	private ActivityMapper activityMapper;
	
	@Autowired
	private MemberBehaviorMapper memberBehaviorMapper;	
	
	//救援
	@Autowired
	private CarRescueMapper carRescueMapper;
	
	@Autowired
	private MemberContributedDetailService memberContributedDetailService ;

	@Autowired
	private MemberMapper memberMapper;

	@Autowired
	private SystemMsgMapper systemMsgMapper;

	@Autowired
	private SystemMsgLogMapper systemMsgLogMapper;

	private Map<String,Object> relativeOrderMap = new HashMap<String,Object>();

	private Map<String,Object> noRelativeOrderMap = new HashMap<String,Object>();

	private static final String WZ_ORDER_BEFORE_N_DAY_PARAM  = "wz_order_before_n_day"; //递推N天参数名称
	private static final String WZ_QUERY_CYCLE_PARAM =  "wz_query_cycle";//查询周期参数名称
	private static final String ORDER_TIME_PATTEN = "yyyy-MM-dd";
	private static final String LONG_TIME_PATTEN = "yyyy-MM-dd HH:mm:ss";
	private static final String WZ_QUERY_URL = "wz_query_url";
	
	private static final String WZ_SWITCH = "wz_switch";
	
	private static final String HPHM = "HPHM";
	private static final String ENGINENO = "ENGINENO";
	private static final String CLASSNO = "CLASSNO";
	private static final String CITY = "CITY";
	private static final String ENCODING_UTF8 = "utf-8";
	
	private static final String WZ_SEND_EMAIL_TO= "wz_send_email_to";
	private static final String WZ_SEND_EMAIL_CC= "wz_send_email_cc";
	private static final String WZ_SEND_EMAIL_BCC= "wz_send_email_bcc";
	
	
	private  StringBuffer sendMailContent =new StringBuffer(); //车辆违章信息
	
//	private  String subject="车辆违章明细("+new SimpleDateFormat("yyyy-MM-dd").format(new Date())+")";//邮件标题
	
	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		
		log.info("wz query start...");
		
		/*
		 * 违章查询规则：
		 *    1.  根据用车订单（已经完成支付类型的订单，不包含取消，未完成的订单）查询出车牌号码和车辆所在城市编码，
		 *            查询支付类型订单，根据参数表配置，在当前系统时间基础上往前递推N天进行查询
		 *            根据参照配置，查询订单周期数（订单周期数要与定时器配置触发时间一致）    
		 *          
		 *    2.  获取到车牌号码和所在城市编码后，查询出车辆的车架号和发动机号；
		 *    
		 *    3.  根据违章查询城市列表中要求，处理发动机号和车架号
		 *    
		 *    4. 调取接口，进行查询 
		 *           查询结果保存违章查询日志表
		 *           查询结果code为200保存违章查询表中，
		 *           
		 *    5. 分析违章查询结果code=200的result，落实此违章记录到某订单上       
		 * 
		 */
		
		String wzSwitch = sysParamService.selectByKey(WZ_SWITCH).getValue();
		if(wzSwitch.equalsIgnoreCase("on")){
			//订单时间
			String[] orderTime = getOrderTime();
			String st = orderTime[1] + " 00:00:00";
			String et = orderTime[0] + " 23:59:59";
			//查询车牌号码
			List<TimeSharePay> lpnData = timeSharePayMapper.selectLpnByOrderTime(st, et);
			if(null != lpnData && !lpnData.isEmpty() && lpnData.size() > 0){
				executeLpnData(lpnData);
			}
		}
		
		//连续用车无违章、需要救援的行为
		MemberBehavior noWzAndRescueBehavior = this.memberBehaviorMapper
				.getByType(MemberBehaviorNameEnum.CONTINUOUS_NO_WZ_AND_RESCUE.getName());
		if(null != noWzAndRescueBehavior){
			log.info("no wz");
			//如果没有违章，则从最后一次查询时间开始，到现在，查会员连续用车情况
			Date lastInsertTime = this.wzQueryMapper.getLatestInsertTime();
			Date newTime = new Date();
			if(null != lastInsertTime){
				newTime = lastInsertTime;
				//违章定时查询是每5天查一次，即如果当前时间与上次记录的时间间隔超过5天，则认为上一次定时程序运行时并没有违章记录，
				//则需要把查时租订单的起始时间定为最后一次违章查询时间的5天后，否则会算上重复的订单
				Calendar lastInsertCal = Calendar.getInstance();
				lastInsertCal.setTime(lastInsertTime);
				Calendar now = Calendar.getInstance();
				//上次记录的5天后
				lastInsertCal.add(Calendar.DAY_OF_MONTH, 5);
				lastInsertCal.add(Calendar.HOUR_OF_DAY, 1);
				if(now.after(lastInsertCal)){
					newTime = lastInsertCal.getTime();
				}					
			}
			
			//条件：连续的次数
			int continTimes = (StringUtils.isNotBlank(noWzAndRescueBehavior.getConditionVal()) ? 
					Integer.valueOf(noWzAndRescueBehavior.getConditionVal()).intValue() : 5);
			List<TimeShareCancel> memberList = timeSharePayMapper.queryMemberList(newTime, continTimes, this.wzMemberIdList);
			if(null != memberList && !memberList.isEmpty()){
				for(TimeShareCancel member : memberList){
					Date tmpTime = (member.getLatestNoWzOrdEndTime() != null ? member.getLatestNoWzOrdEndTime() : newTime);
					List<TimeSharePay> ordList = timeSharePayMapper.queryOrdListFromDate(
							member.getMemberId(), tmpTime);
					Date latestNoWzOrdEndTime = handleContinuTsOrder(ordList, member.getMemberId(), noWzAndRescueBehavior, continTimes);
					TimeShareCancel timeShareCancel = this.timeShareCancelMapper
							.queryTimeShareCancelByMemberId(member.getMemberId());
					if(null == timeShareCancel){
						log.info("isNeed2CreateCancel 2, " + latestNoWzOrdEndTime);
						timeShareCancel = new TimeShareCancel() ;
						timeShareCancel.setCreateTime(new Date()) ;
						timeShareCancel.setMemberId(member.getMemberId()) ;
						timeShareCancel.setLatestNoWzOrdEndTime(latestNoWzOrdEndTime);
						//连续无违章、救援的次数清0
//						timeShareCancel.setContinNoWzNum(0);
//						timeShareCancel.setContinNoRescueNum(0);
						timeShareCancelMapper.insertSelective(timeShareCancel) ;
					}else{
						if(null != latestNoWzOrdEndTime){
							log.info("no need2CreateCancel 2, " + latestNoWzOrdEndTime);
							timeShareCancel.setLatestNoWzOrdEndTime(latestNoWzOrdEndTime);
							//连续无违章、救援的次数清0
//						timeShareCancel.setContinNoWzNum(0);
//						timeShareCancel.setContinNoRescueNum(0);
							timeShareCancelMapper.updateByPrimaryKeySelective(timeShareCancel);
							
						}
					}
				}
			}
			
			if(!this.wzMemberIdList.isEmpty()){
				this.wzMemberIdList.clear();
			}
		}
		
		log.info("wz query end...");
	}

	
    /**处理每一项车牌号码*/
	private void executeItemLpnData(String lpn) throws Exception{
		Car car = carMapper.selectByLpn(lpn);
		//发动机号和车架号必须存在的情况下才允许启动查询操作
		if(!StringUtils.isBlank(car.getEngineno()) && !StringUtils.isBlank(car.getClassno())){
			//根据城市编码查询出违章城市编码
			WZCitysQuery wzCitysQuery = wzCitysQueryMapper.queryWZCitysQueryByCityCode(car.getCityCode());
			if(null != wzCitysQuery){
				String queryWZCityCode = wzCitysQuery.getQueryCityCode();
				//查询出查询违章必要条件，发动机号和车架号
				WZCitys wzCitys = wzCitysMapper.selectByCityCode(queryWZCityCode);
				//调取聚合违章查询URL,处理和封装查询违章的URL地址
				String wzQueryURL= sysParamService.selectByKey(WZ_QUERY_URL).getValue();
				//处理车牌号码，车牌号码参数必须要URLEncoder，并且采用UTF-8
				wzQueryURL = wzQueryURL.replace(HPHM, URLEncoder.encode(car.getLpn(), ENCODING_UTF8));
				//处理违章查询城市编码
				wzQueryURL = wzQueryURL.replace(CITY, wzCitys.getCityCode());
				//处理发动机号
				if(null != wzCitys){
					//发动机处理
					wzQueryURL = executeEngine(wzQueryURL, car, wzCitys);
					//车架号处理
					wzQueryURL = executeClassa(wzQueryURL, car, wzCitys);
					//当URL地址处理完毕后，进行违章查询操作
					executeQuery(wzQueryURL, car, wzCitys);
				}
				
			}
		}
	}


	/**查询违章信息*/
	private void executeQuery(String wzQueryURL, Car car, WZCitys wzCitys) throws Exception{
		String body = HttpTool.sendGet(wzQueryURL);
		//保存查询日志
		saveWZQueryLog(body, car, wzCitys, wzQueryURL); 
		JSONObject jsonObject = JSONObject.parseObject(body);
		if(jsonObject.getString("resultcode").equals("200")){
			//当返回码=200的时候，进行解析处理
			executeQueryOK(jsonObject.getString("result"), jsonObject.getString("resultcode"), jsonObject.getString("reason"));
		}
	}

	private List<Integer> wzMemberIdList = new ArrayList<Integer>();
	
	private void executeQueryOK(String result, String resultcode, String reason) throws Exception {
		JSONObject jsonObject = JSONObject.parseObject(result);
		JSONArray lists = jsonObject.getJSONArray("lists");
		//连续用车无违章、需要救援的行为
		MemberBehavior noWzAndRescueBehavior = this.memberBehaviorMapper
				.getByType(MemberBehaviorNameEnum.CONTINUOUS_NO_WZ_AND_RESCUE.getName());
		if( null != lists && lists.size() > 0 ){
			
			for(int i=0; i<lists.size(); i++){
				JSONObject itemObj = JSONObject.parseObject(String.valueOf( lists.get(i)));
				WZQuery wzQuery = new WZQuery();
				int sum =wzQueryMapper.queryByCondition(jsonObject.getString("hphm"),jsonObject.getString("hpzl"),itemObj.getString("date"));
				if(sum<=0){//根据车牌号、车辆类型、违章日期 去违章记录表中查询 ，如有记录则不再记录，没有的话则做相应的记录。扣除会员积分处理。
				//保存违章记录
					wzQuery.setResultcode(resultcode);
					wzQuery.setReason(reason);
					wzQuery.setProvince(jsonObject.getString("province"));
					wzQuery.setCity(jsonObject.getString("city"));
					wzQuery.setHphm(jsonObject.getString("hphm"));
					wzQuery.setHpzl(jsonObject.getString("hpzl"));
					wzQuery.setQueryTime(new Date());
					
					//违章明细  
					wzQuery.setDate(itemObj.getString("date"));
					wzQuery.setArea(itemObj.getString("area"));
					wzQuery.setAct(itemObj.getString("act"));
					wzQuery.setCode(itemObj.getString("code"));
					wzQuery.setFen(itemObj.getString("fen"));
					wzQuery.setMoney(itemObj.getString("money"));
					wzQuery.setHandled(itemObj.getString("handled"));

					//根据违章时间和车牌号码。落实到某订单上面
					String[] wzorder = getWZOrder(jsonObject.getString("hphm"),itemObj.getString("date"));
					if(null != wzorder ){
						wzQuery.setMemberId(Integer.valueOf(wzorder[0]));
						wzQuery.setOrderId(wzorder[1]);
						//生成会员积分变化记录(先查询数据库中是否有此条记录 如果有不做处理，没有的话就新增)
	//					String wzKf= sysParamService.selectByKey(WZ_KF).getValue();//获取违章扣分单位
	//					String wzkk= sysParamService.selectByKey(WZ_KK).getValue();//获取违章扣款 扣积分单位
	//					int wzkfIntegral =Integer.valueOf(wzKf)*Integer.valueOf(itemObj.getString("fen"));//扣分对于所扣的积分
	//					int wzkkIntegral =Integer.valueOf(wzkk)*Integer.valueOf(itemObj.getString("money"));//扣款所扣的积分
	//					int  integral = wzkfIntegral+ wzkkIntegral;
						int memberId = Integer.valueOf(wzorder[0]); //会员ID
						/*int integralFormId = Integer.valueOf(wzorder[1]);*/
						/*IntegralDetail integralDetail = new IntegralDetail();
						integralDetail.setCreateTime(new Date());
						integralDetail.setIntegral(-integral);
						integralDetail.setIntegralCategory("violationBuckleIntegral");
						integralDetail.setIntegralFormId(integralFormId);
						integralDetail.setIntegralDesc(itemObj.getString("area"));
						integralDetail.setMemberId(memberId);
						integralDetailMapper.insert(integralDetail);*/
						//根据得到的memberid 由扣除的分数和扣的款数进行相应的会员积分的扣除
	//					MemberCard memberCard = memberCardMapper.selectByMemberId(memberId);
	//					int oldIntegral = memberCard.getIntegral()==null?0:memberCard.getIntegral();//积分如果为Null的话就设置为0
	//					integral =oldIntegral-integral;
	//					memberCardMapper.updateIntegral(memberId, integral);//更新会员积分
						
						//更新会员贡献值的违章扣款部分
						if (!wzorder[1].contains("employee")) {
							wzMemberIdList.add(memberId);
							/****************查会员连续用车是否有违章、需要救援，若连续5次均无违章和需要救援，则增加贡献值***************/
							//查会员最新的一条违章，从上次到现在这段时间，连续用车无需救援的次数，如果没有违章，则直接查会员的连续用车情况
							if(null != noWzAndRescueBehavior && noWzAndRescueBehavior.getStatus().equals(1)){
								log.info("there are wzs");
								Date now = new Date();
								
								WZQuery latestWz = wzQueryMapper.queryLatestWzRecord(memberId);
								//之前有违章记录，则从上次的时间开始算起。如果之前没有违章记录，则查最新的query_time以来，memberId对应的连续用车情况
								Date lastWzTime = null;
								
								TimeShareCancel timeShareCancel = this.timeShareCancelMapper
										.queryTimeShareCancelByMemberId(memberId);
								//没有保存最新一次无违章无救援的订单的结束时间，就需要保存记录
								boolean isNeed2CreateCancel = false;
								if(null == timeShareCancel){
									if(null != latestWz
											&& null != latestWz.getDate()){
										String lastWzTimeStr = latestWz.getDate();//.getQueryTime();
										lastWzTime = DateUtils.parseDate(lastWzTimeStr, "yyyy-MM-dd HH:mm:ss");
									}else{
										lastWzTime = wzQueryMapper.getLatestInsertTime();
									}
									isNeed2CreateCancel = true;
								}else{
									if(null != timeShareCancel.getLatestNoWzOrdEndTime()){
										lastWzTime = timeShareCancel.getLatestNoWzOrdEndTime();						
									}else{
										if(null != latestWz
												&& null != latestWz.getDate()){
											String lastWzTimeStr = latestWz.getDate();//.getQueryTime();
											lastWzTime = DateUtils.parseDate(lastWzTimeStr, "yyyy-MM-dd HH:mm:ss");
										}else{
											lastWzTime = wzQueryMapper.getLatestInsertTime();
										}
									}
									
								}
								
								if(null != lastWzTime){
									int continTimes = (StringUtils.isNotBlank(noWzAndRescueBehavior.getConditionVal()) ? 
											Integer.valueOf(noWzAndRescueBehavior.getConditionVal()).intValue() : 5);
									//查此会员连续用车次数是否有超过条件下限值，若没有则直接跳过，不处理
									List<Integer> memberIdList = timeSharePayMapper.queryMemberIdListOrdCntGreaterThenValFromDate(lastWzTime, continTimes);
									if(null != memberIdList && !memberIdList.isEmpty()){
										if(memberIdList.contains(memberId)){
											List<TimeSharePay> ordList = timeSharePayMapper.queryOrdListFromDate(
													memberId, lastWzTime);
											Date latestNoWzOrdEndTime = handleContinuTsOrder(ordList, memberId, noWzAndRescueBehavior, continTimes);
											if(null != latestNoWzOrdEndTime){
												if(isNeed2CreateCancel){
													log.info("isNeed2CreateCancel 1, " + latestNoWzOrdEndTime);
													timeShareCancel = new TimeShareCancel() ;
													timeShareCancel.setCreateTime(now) ;
													timeShareCancel.setMemberId(memberId) ;
													timeShareCancel.setLatestNoWzOrdEndTime(latestNoWzOrdEndTime);
													//连续无违章、救援的次数清0
//													timeShareCancel.setContinNoWzNum(0);
//													timeShareCancel.setContinNoRescueNum(0);
													timeShareCancelMapper.insertSelective(timeShareCancel) ;
												}else{
													if(null != latestNoWzOrdEndTime){
														log.info("no need2CreateCancel 1, " + latestNoWzOrdEndTime);
														timeShareCancel.setLatestNoWzOrdEndTime(latestNoWzOrdEndTime);
														//连续无违章、救援的次数清0
	//													timeShareCancel.setContinNoWzNum(0);
	//													timeShareCancel.setContinNoRescueNum(0);
														timeShareCancelMapper.updateByPrimaryKeySelective(timeShareCancel);
													}
												}
											}
										}else{
											continue;
										}
									}
								}
							}
							
							//违章 -100
							this.memberContributedDetailService.insertMemberContributedDetail(memberId, 
									wzorder[1], 
									MemberBehaviorNameEnum.BREAK_RULES.getName(), null );
							//违章驾驶导致扣分  扣1分 -100贡献值
							this.memberContributedDetailService.insertMemberContributedDetail(memberId, 
									wzorder[1], 
									MemberBehaviorNameEnum.BREAK_RULES_POINT.getName(), Double.valueOf(wzQuery.getFen()) );
							
						}
						
						
					}
					wzQuery.setStatus("1");
					wzQueryMapper.insertSelective(wzQuery);
					Member member = memberMapper.selectByPrimaryKey(wzQuery.getMemberId());
					sendMsg(member);
				}
				//车辆违章信息生成
//				sendMailContent.append("<b>车牌号码：</b>").append(jsonObject.getString("hphm")).append("<br/>");
//				sendMailContent.append("<b>违章时间：</b>").append(itemObj.getString("date")).append("<br/>");
//				sendMailContent.append("<b>违章地点：</b>").append(itemObj.getString("area")).append("<br/>");
//				sendMailContent.append("<b>违章行为：</b>").append(itemObj.getString("act")).append("<br/>");
//				sendMailContent.append("<b>违章扣分：</b>").append(itemObj.getString("fen")).append("<br/>");
//				sendMailContent.append("<b>违章扣款(单位：元)：</b>").append(itemObj.getString("money")).append("<br/>").append("<br/>");
			}

			for(int i=0; i<lists.size(); i++){
				JSONObject itemObj = JSONObject.parseObject(String.valueOf( lists.get(i)));
				//根据违章时间和车牌号码。落实到某订单上面
				String[] wzorder = getWZOrder(jsonObject.getString("hphm"),itemObj.getString("date"));
				if (null != wzorder){
					itemObj.put("orderId",wzorder[1]);
					relativeOrderMap.put(jsonObject.getString("hphm"),itemObj);
				}else {
					noRelativeOrderMap.put(jsonObject.getString("hphm"),itemObj);
				}
			}
			
		}else{
			
			
			//当没有违章的暂时不处理
//			WZQuery wzQuery = new WZQuery();
//			wzQuery.setResultcode(resultcode);
//			wzQuery.setReason(reason);
//			wzQuery.setProvince(jsonObject.getString("province"));
//			wzQuery.setCity(jsonObject.getString("city"));
//			wzQuery.setHphm(jsonObject.getString("hphm"));
//			wzQuery.setHpzl(jsonObject.getString("hpzl"));
//			wzQuery.setQueryTime(new Date());
//			wzQueryMapper.insertSelective(wzQuery);
		}
	}

	/**
	 * 推送消息给会员
	 * @param member
	 */
	private void sendMsg(Member member) {

		/**保存到系统**/
		SystemMsgLog systemMsgLog = new SystemMsgLog();
		systemMsgLog.setMsgType("refund");
		systemMsgLog.setCreateTime(new Date());
		systemMsgLog.setMemberId(member.getId());
		systemMsgLog.setMsgTitle("违章记录");
		String content = "【宜步出行】尊敬的会员，您有违章记录产生，为不影响您的正常用车，请及时联系违章专员：4000769755转4";
		systemMsgLog.setMsgContent(content);
		systemMsgLogMapper.insertSelective(systemMsgLog);

		/**个推消息**/
		PushCommonBean push = new PushCommonBean("push_server_system_msg_log", "1", "【宜步出行】尊敬的会员，您有违章记录产生，为不影响您的正常用车，请及时联系违章专员：4000769755转4","") ;
		List<String> cidList = PushClient.queryClientId(member.getPhone());
		for (String memberCid : cidList) {
			PushClient.push(memberCid,push);
		}
	}


	/**
	 * 处理连续用车情况
	 * @param ordList
	 * @param memberId
	 * @param noWzAndRescueBehavior
	 * @param continTimes
	 * @return 最后一个订单的结束时间
	 * @author ouxx
	 * @date 2017-5-5 下午11:33:26
	 */
	private Date handleContinuTsOrder(List<TimeSharePay> ordList,
			int memberId, MemberBehavior noWzAndRescueBehavior, int continTimes) {
		
		if(null != ordList && !ordList.isEmpty()){										
			int ordCnt = ordList.size();
			//无违章的连续用车次数 >= 条件的
			if(ordCnt >= continTimes){
				//按订单结束时间升序来排序，查每次时租期间，有无需要救援的
				TsEndTimeComparator compatator = new TsEndTimeComparator();
				Collections.sort(ordList, compatator);
				int loop = ordCnt / continTimes;
				int noRescueTimes = 0;//无需救援的次数
				List<Date> ordTimeList = new ArrayList<Date>();
				for(int i = 0; i < loop; ++i){
					boolean isNoRescue = true;
					for(int n = 0; n < continTimes; ++n){
						TimeSharePay ord = ordList.get(i * continTimes + n);
						if(null != ord){
							Date ordStartTime = ord.getBeginTime();
							Date ordEndTime = ord.getEndTime();
							//lpn在此时间内的救援次数
							int rescueCnt = this.carRescueMapper.queryByLpnBetweenTime(
									ord.getLpn(), ordStartTime, ordEndTime);
							if(rescueCnt > 0){//如果有救援记录，则标记有救援
								isNoRescue = false;
								break;
							}
							//记录本组订单,最后一次的结束时间
							if(n == continTimes - 1){
//								System.err.println((i * continTimes + n) + " , 记录本组订单,最后一次的结束时间 = " + ordEndTime + "ordId = " + ord.getOrderId());
								ordTimeList.add(ordEndTime);
							}
						}
					}
					
					if(isNoRescue){
						++noRescueTimes;//增加无需救援次数
					}
				}
				
				if(0 < noRescueTimes){
					Double val = Double.valueOf(noWzAndRescueBehavior.getContriValue());
					for(int i = 0; i < noRescueTimes; ++i){
						Date creatTime = (ordTimeList != null && ordTimeList.get(i) != null ? 
								ordTimeList.get(i) : (new Date()));
						this.memberContributedDetailService.insertMemberContributedDetail2(creatTime,
								memberId, null, MemberBehaviorNameEnum.CONTINUOUS_NO_WZ_AND_RESCUE.getName(), val);						
					}
				}
				//整数倍以后的订单，就留到下次再统计
				return ordList.get(loop * continTimes - 1).getEndTime();
			}
		}
		
		return null;
	}
	
	/**
	 * 时租订单的结束时间比较器
	 * @author ouxx
	 * @since 2017-4-21 下午7:13:51
	 *
	 */
	public static class  TsEndTimeComparator implements Comparator<TimeSharePay>{

		@Override
		public int compare(TimeSharePay o1, TimeSharePay o2) {
			Long t1 = Long.valueOf(o1.getEndTime().getTime());
			Long t2 = Long.valueOf(o2.getEndTime().getTime());
			return t1.compareTo(t2);
		}
		
	}
	
	public static class TimeSharePayWithoutWz implements Comparable{
		private Date beginTime;//订单开始时间
		private Date endTime;//订单结束时间
		
		@Override
		public int compareTo(Object o) {
			TimeSharePayWithoutWz other = (TimeSharePayWithoutWz) o;
			
			Calendar thisBegin = Calendar.getInstance();
			thisBegin.setTime(this.beginTime);
			Calendar thisEnd = Calendar.getInstance();
			thisEnd.setTime(this.endTime);
			return 0;
		}

		public Date getBeginTime() {
			return beginTime;
		}

		public void setBeginTime(Date beginTime) {
			this.beginTime = beginTime;
		}

		public Date getEndTime() {
			return endTime;
		}

		public void setEndTime(Date endTime) {
			this.endTime = endTime;
		}
		
	}

	public static void main(String[] args) {
		final Integer memberId = 10;
		final String typeEnum = "MEMBER_CONTRIBUTED_BREAK_RULES_PENALTY";
		final String objId = "objIdStr";
		final Double value = 6.6;
		//调用接口的参数
		final Map<String, String> methodParamMap = new HashMap<String, String>(){
			private static final long serialVersionUID = 1L;
			{
				put("objId", objId);
				put("typeEnum", typeEnum);
				put("value", value.toString());
			}
		};
		final String methodParams = FastJsonUtils.toJson(methodParamMap).replaceAll("\"", "'");
		//http请求的参数
		Map<String, String> httpMap = new HashMap<String, String>(){
			private static final long serialVersionUID = 1L;
			{
				put("cId", "platForm");
				put("memberId", memberId.toString());
				put("method", "insertMemberContributedDetail");
				put("param", methodParams);
				put("phone", "");
				put("type", "platForm");
				put("version", "1");
			}
		};
		String params = FastJsonUtils.toJson(httpMap);
		System.out.println(FastJsonUtils.toJson(methodParamMap).replaceAll("\"", "'"));
		System.out.println(params);
	}
	

	private void sendMail(String subject, String content) throws Exception{
		String to = sysParamService.selectByKey(WZ_SEND_EMAIL_TO).getValue();
		String cc = sysParamService.selectByKey(WZ_SEND_EMAIL_CC).getValue();
		String bcc = sysParamService.selectByKey(WZ_SEND_EMAIL_BCC).getValue();
		if(!StringUtils.isBlank(to)){
			SendMail sendMail = new  SendMail(sysParamService);
			//如果发送人包含多个
			if(to.indexOf(",")!=-1){
				String[] tos=to.split(",");
				for(int i=0,len=tos.length;i<len;i++){
					if(i==0){
						if(!StringUtils.isBlank(cc) && !StringUtils.isBlank(bcc)){
							sendMail.sendMail(subject, content, tos[0], cc, bcc);
						}else if(!StringUtils.isBlank(cc) && StringUtils.isBlank(bcc)){
							sendMail.sendMail(subject, content, tos[0], cc);
						}else if(StringUtils.isBlank(cc) && !StringUtils.isBlank(bcc)){
							sendMail.sendMail(subject, content, tos[0], "", bcc);
						}else{
							sendMail.sendMail(subject, content, to);
						}
					}else{
						sendMail.sendMail(subject, content, tos[i]);
					}
				}
			}else{
				if(!StringUtils.isBlank(cc) && !StringUtils.isBlank(bcc)){
					sendMail.sendMail(subject, content, to, cc, bcc);
				}else if(!StringUtils.isBlank(cc) && StringUtils.isBlank(bcc)){
					sendMail.sendMail(subject, content, to, cc);
				}else if(StringUtils.isBlank(cc) && !StringUtils.isBlank(bcc)){
					sendMail.sendMail(subject, content, to, "", bcc);
				}else{
					sendMail.sendMail(subject, content, to);
				}
			}
		}
		
	}


	/**把违章信息落实到某订单上面*/
	private String[] getWZOrder(String lpn, String date) throws Exception {
		String[] orderTime = getOrderTime();
//		String st = orderTime[1] + " 00:00:00";
//		String et = orderTime[0] + " 23:59:59";
//		List<TimeSharePay> orderData = timeSharePayMapper.selectByOrderTimeAndLpn(st, et, lpn);
		
		List<TimeSharePay> orderData = timeSharePayMapper.selectByOrderTimeAndLpn(date,lpn);
		if(null !=orderData && !orderData.isEmpty()){
			for(TimeSharePay tsp  : orderData){
				if(isCheckTime(tsp.getBeginTime(), tsp.getEndTime(), date)){
					orderTime[0] = String.valueOf(tsp.getMemberId());
					orderTime[1] = tsp.getOrderId();
					return orderTime;
				}
			}
		}
		return null;
	}

    /**
     * 校验违章时间是否在某个订单内，如果在返回true， 否则返回false
     * @param st 订单开始时间
     * @param et 订单结束时间
     * @param wzt 违章时间
     * @return
     */
	private boolean isCheckTime(Date st, Date et, String wzt) throws Exception{
		SimpleDateFormat sdf = new SimpleDateFormat(LONG_TIME_PATTEN);
		Long wztLong = sdf.parse(wzt).getTime();
		if(wztLong >= st.getTime() && wztLong <= et.getTime()){
			return true;
		}
		return false;
	}
	
	/**保存查询日志记录*/
	private void saveWZQueryLog(String body, Car car, WZCitys wzCitys, String wzQueryURL) {
		JSONObject jsonObject = JSONObject.parseObject(body);
		WZQueryLog wzQueryLog = new WZQueryLog();
		wzQueryLog.setResultcode(jsonObject.getString("resultcode"));
		wzQueryLog.setReason(jsonObject.getString("reason"));
		wzQueryLog.setResult(jsonObject.getString("result"));
		//wzQueryLog.setErrorCode(jsonObject.getString(""));
		wzQueryLog.setCity(wzCitys.getCityCode());
		wzQueryLog.setHphm(car.getLpn());
		wzQueryLog.setEngineno(car.getEngineno());
		wzQueryLog.setClassno(car.getClassno());
		wzQueryLog.setQueryTime(new Date());
		wzQueryLog.setQueryUrl(wzQueryURL);
		wzQueryLogMapper.insertSelective(wzQueryLog);
	}


	/**车架号处理*/
	private String executeClassa(String wzQueryURL, Car car, WZCitys wzCitys) {
		if(wzCitys.getClassa().equals("1")){//当classa=1时，表示要处理车架号
			int classaNo = Integer.valueOf(wzCitys.getClassno());
			if(classaNo == 0){//当classaNo=0时候，表示需要全部的车架号
				wzQueryURL = wzQueryURL.replace(CLASSNO, car.getClassno());
			}else{
				if(car.getClassno().length() == classaNo){
					wzQueryURL = wzQueryURL.replace(CLASSNO, car.getClassno());
				}else if(car.getClassno().length() > classaNo){
					wzQueryURL = wzQueryURL.replace(CLASSNO, car.getClassno().substring(car.getClassno().length() - classaNo));
				}else{
					throw new IllegalArgumentException("车架号错误...");
				}
			}
		}else{
			 //其他情况下，不需要车架号
			wzQueryURL = wzQueryURL.replace(CLASSNO, "");
		}
		return wzQueryURL;
	}


	/** 发动机URL处理
	 */
	private String executeEngine(String wzQueryURL, Car car, WZCitys wzCitys){
		//当engine=1时，表示需要发动机号码
		 if(wzCitys.getEngine().equals("1")){
			int engineNo = Integer.valueOf(wzCitys.getEngineno()); 
			if(engineNo == 0){//当engineNo=0时候，表示需要全部的发动机号
				wzQueryURL = wzQueryURL.replace(ENGINENO, car.getEngineno());
			}else{
				if(car.getEngineno().length() == engineNo){
					wzQueryURL = wzQueryURL.replace(ENGINENO, car.getEngineno());
				}else if(car.getEngineno().length() > engineNo){
					wzQueryURL = wzQueryURL.replace(ENGINENO, car.getEngineno().substring(car.getEngineno().length() - engineNo));
				}else {
					throw new IllegalArgumentException("发动机号错误...");
				}
			}
		 }else{
			 //其他情况下，不需要发送机号
			 wzQueryURL = wzQueryURL.replace(ENGINENO, "");
		 }
		 
		 return wzQueryURL;
	}
	
	/**处理车牌数据
	 * @throws Exception */
	private void executeLpnData(List<TimeSharePay> lpnData)  {
		
		
		//根据车牌号码，查询出车辆的详细信息	
		for(TimeSharePay timeSharePay : lpnData){
			//处理每一项车牌号码
		    try {
		    	executeItemLpnData(timeSharePay.getLpn());
			} catch (Exception e) {
				continue;
			}
		}

		//生成sendMailContent
		Set<Map.Entry<String, Object>> noRelativeEntry = noRelativeOrderMap.entrySet();
		Iterator<Map.Entry<String, Object>> noRelativeIterator = noRelativeEntry.iterator();
		while (noRelativeIterator.hasNext()){
			Map.Entry<String, Object> next = noRelativeIterator.next();
			JSONObject itemObj = (JSONObject) next.getValue();
			sendMailContent.append("<b>车牌号码：</b>").append(next.getKey()).append("<br/>");
			sendMailContent.append("<b>违章时间：</b>").append(itemObj.getString("date")).append("<br/>");
			sendMailContent.append("<b>违章地点：</b>").append(itemObj.getString("area")).append("<br/>");
			sendMailContent.append("<b>违章行为：</b>").append(itemObj.getString("act")).append("<br/>");
			sendMailContent.append("<b>违章扣分：</b>").append(itemObj.getString("fen")).append("<br/>");
			sendMailContent.append("<b>违章扣款(单位：元)：</b>").append(itemObj.getString("money")).append("<br/>").append("<br/>");
		}
		Set<Map.Entry<String, Object>> relativeEntry = relativeOrderMap.entrySet();
		Iterator<Map.Entry<String, Object>> relativeIterator = relativeEntry.iterator();
		while (relativeIterator.hasNext()){
			Map.Entry<String, Object> next = relativeIterator.next();
			JSONObject itemObj = (JSONObject) next.getValue();
			sendMailContent.append("<b>车牌号码：</b>").append(next.getKey()).append("<br/>");
			sendMailContent.append("<b>订单号：</b>").append(itemObj.getString("orderId")).append("<br/>");
			sendMailContent.append("<b>违章时间：</b>").append(itemObj.getString("date")).append("<br/>");
			sendMailContent.append("<b>违章地点：</b>").append(itemObj.getString("area")).append("<br/>");
			sendMailContent.append("<b>违章行为：</b>").append(itemObj.getString("act")).append("<br/>");
			sendMailContent.append("<b>违章扣分：</b>").append(itemObj.getString("fen")).append("<br/>");
			sendMailContent.append("<b>违章扣款(单位：元)：</b>").append(itemObj.getString("money")).append("<br/>").append("<br/>");
		}
		// 发送邮件
		if(!sendMailContent.toString().equals("")){
			try {
				sendMail("车辆违章明细("+new SimpleDateFormat("yyyy-MM-dd").format(new Date())+")", sendMailContent.toString());
			} catch (Exception e) {
				log.error("车辆违章查询邮件发送失败！") ;
				e.printStackTrace();
			}
		}
		
	
	}
	
	/**获取订单时间 【0】订单开始时间，【1】订单结束时间*/
	private String[] getOrderTime(){
		String[] orderTime = new String[2];
		
		int nDay = -Integer.valueOf(sysParamService.selectByKey(WZ_ORDER_BEFORE_N_DAY_PARAM).getValue());
		int cycleDay = -Integer.valueOf(sysParamService.selectByKey(WZ_QUERY_CYCLE_PARAM).getValue());
		
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(ORDER_TIME_PATTEN);
		
		//订单开始时间
		calendar.add(Calendar.DATE, nDay);
		orderTime[0] = sdf.format(calendar.getTime());
		//订单结束时间
		calendar.add(Calendar.DATE, cycleDay);
		orderTime[1] = sdf.format(calendar.getTime());
		return orderTime;
	}
	
	
}
