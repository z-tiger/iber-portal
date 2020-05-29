/*
 * 
 */
package com.iber.portal.quartz;

import com.alibaba.fastjson.JSON;
import com.iber.portal.common.HttpUtils;
import com.iber.portal.common.HttpsClientUtil;
import com.iber.portal.common.SysConstant;
import com.iber.portal.dao.base.CityMapper;
import com.iber.portal.dao.base.ParkMapper;
import com.iber.portal.dao.base.SystemMsgLogMapper;
import com.iber.portal.dao.car.CarRunLogMapper;
import com.iber.portal.dao.car.CarRunMapper;
import com.iber.portal.dao.sys.SysWarnItemMapper;
import com.iber.portal.dao.timeShare.TimeShareOrderMapper;
import com.iber.portal.getui.PushClient;
import com.iber.portal.getui.PushCommonBean;
import com.iber.portal.model.base.City;
import com.iber.portal.model.base.Park;
import com.iber.portal.model.base.SystemMsgLog;
import com.iber.portal.model.car.CarRun;
import com.iber.portal.model.car.CarRunLog;
import com.iber.portal.model.sys.SysParam;
import com.iber.portal.model.sys.SysWarnInfo;
import com.iber.portal.model.sys.SysWarnItem;
import com.iber.portal.model.timeShare.TimeShareOrder;
import com.iber.portal.mongo.WarnInfoNosql;
import com.iber.portal.service.base.MemberService;
import com.iber.portal.service.sys.SysParamService;
import com.iber.portal.service.sys.SysWarnInfoService;
import com.iber.portal.service.timeShare.MoneyCalculateService;
import com.iber.portal.service.timeShare.TimeShareOrderService;
import com.iber.portal.util.SendSMS;
import com.iber.portal.vo.timeShare.LongTimeShareOrderVo;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.time.DateUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 超长订单预警
 *
 * @author ouxx
 * @since 2017-5-10 下午4:47:08
 * lf 2017-5-15 09:39:10 修改
 */
public class LongOrderWarnSupport extends QuartzJobBean {

    //预警消息类型分为两种，一种是当订单被认定为超时订单时，会及时发送一级预警消息。
    // 第二种是发送一级预警消息5小时后，客户仍未充值或结束订单，会发二级预警消息。
    //	1）当订单被认定为超时订单时，一级预警消息内容如下：
    //	尊敬的会员，您的当前用车订单已超长，请在6小时内及时充值余额或支付订单重新约车，以免给您带来不必要的麻烦。
    //
    //	2）当距离发送一级预警信息已有5小时，客户却依然未充值或未结束订单，将发送二级预警消息，内容如下：
    //	尊敬的会员，您的当前用车订单已超长，请在1小时内及时充值余额或支付订单重新约车，否则您当前使用的车辆将会被锁定，无法继续使用。

    //	订单金额超过预设金额A后，进行以下判断(订单金额>=余额)，如果条件符合，触发超长订单预警
    //	上面的预设金额A，按车型配置，奇瑞EQ默认为300元；北汽EV160默认为350元，支持后台修改此参数。
    //	根据系统设置的分时租赁计费策略计算当前用车金额＝（当前小时数/24）商*最高消费费用＋（当前小时数%24）余数 *日时间计费

	private final Logger logger = LoggerFactory.getLogger(LongOrderWarnSupport.class);
	
    @Autowired
    private TimeShareOrderService timeShareOrderService;

    @Autowired
    private SysParamService sysParamService;

    @Autowired
    private SysWarnInfoService warnInfoService;

    @Autowired
    private MoneyCalculateService moneyCalculateService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private SysWarnItemMapper sysWarnItemMapper;

    @Autowired
    private CarRunMapper carRunMapper;

    @Autowired
    private CarRunLogMapper carRunLogMapper;

    @Autowired
    private TimeShareOrderMapper timeShareOrderMapper;

    @Autowired
    private SystemMsgLogMapper systemMsgLogMapper;

    @Autowired
    private ParkMapper parkMapper;
    
    @Autowired
    private CityMapper cityMapper;

    @Autowired
    private WarnInfoNosql warnInfoNosql;

    //超长订单一级预警时长key
    private static final String LONG_ORDER_ONE_WARN_TIME_HOUR_KEY = "long_order_one_warn_time_hour";
    //超长订单二级预警时长key
    private static final String LONG_ORDER_TWO_WARN_TIME_HOUR_KEY = "long_order_two_warn_time_hour";
    //超长订单锁车时长key
    private static final String LONG_ORDER_LOCK_TIME_HOUR_KEY = "long_order_lock_time_hour";
    //超长订单锁车二次提醒时长key
    private static final String LONG_ORDER_LOCK_TWO_TIP_TIME_HOUR_KEY = "long_order_lock_two_tip_time_hour";
    //锁车条件熄火分钟数key
    private static final String LOCK_CAR_CONDITION_ENGINE_STOP_MINUTE_KEY = "lock_car_condition_engine_stop_minute";
    //锁车条件充电分钟数key
    private static final String LOCK_CAR_CONDITION_CHARGING_MINUTE_KEY = "lock_car_condition_charging_minute";


    // 发送了一级预警
    private static final int ONE_WARN_YES = 1;
    // 发送了二级预警
    private static final int TWO_WARN_YES = 1;
    // 已经锁车
    private static final int LOCK_YES = 1;

    /**
     * 预警信息
     */
    //2684
    private static final Integer WARN_ONE_ID = 2689;
    private static final String WARN_ONE_INFO = "尊敬的会员，您当前的订单已超长，请在6小时内及时充值，否则车辆{0}将被锁定且无法继续使用，给您造成的不便敬请谅解！";
    //2685
    private static final Integer WARN_TWO_ID = 2690;
    private static final String WARN_TWO_INFO = "尊敬的会员，您当前的订单已超长，请在1小时内及时充值，否则车辆{0}将被锁定且无法继续使用，给您造成的不便敬请谅解！";
    //2687
    private static final Integer LOCK_CAR_ID = 2691;
    private static final String LOCK_CAR_INFO = "尊敬的会员，由于未及时充值余额，您当前使用的车辆{0}已经锁定。如需继续用车，请及时充值。如有疑问，请致电客户服务热线4000769755。";
    //2686
    private static final Integer WARN_OVER_ID = 2692;
    private static final String WARN_OVER_INFO = "尊敬的会员，您当前使用的的车辆{0}已解除锁定，祝您用车愉快。";
    
    private static final Integer ORDER_FINISH_ID = 2693;
    private static final String ORDER_FINISH_INFO = "尊敬的会员，由于未及时充值余额，您当前使用的车辆订单已自动结束。如有疑问，请致电客户服务热线4000769755。";

    @Override
    protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
    	logger.info("超长订单查询");
        // 一级预警时长
        SysParam oneWarnTimeParam = sysParamService.selectByKey(LONG_ORDER_ONE_WARN_TIME_HOUR_KEY);
        final int oneWarnTime = oneWarnTimeParam == null ? 5 : Integer.valueOf(oneWarnTimeParam.getValue());
        // 二级预警时长
        SysParam twoWarnTimeParam = sysParamService.selectByKey(LONG_ORDER_TWO_WARN_TIME_HOUR_KEY);
        final int twoWarnTime = twoWarnTimeParam == null ? 1 : Integer.valueOf(twoWarnTimeParam.getValue());

        // 锁车时长
        SysParam lockTimeParam = sysParamService.selectByKey(LONG_ORDER_LOCK_TIME_HOUR_KEY);
        final int lockTime = lockTimeParam == null ? 6 : Integer.valueOf(lockTimeParam.getValue());
        // 锁车第二次预警时长
        SysParam lockTwoTipTimeParam = sysParamService.selectByKey(LONG_ORDER_LOCK_TWO_TIP_TIME_HOUR_KEY);
        final int lockTwoTipTime = lockTwoTipTimeParam == null ? 5 : Integer.valueOf(lockTwoTipTimeParam.getValue());

        // 锁车策略 策略熄灭分钟数
        SysParam engineStopMinuteParam = sysParamService.selectByKey(LOCK_CAR_CONDITION_ENGINE_STOP_MINUTE_KEY);
        final int engineStopMinute = engineStopMinuteParam == null ? 15 : Integer.valueOf(engineStopMinuteParam.getValue());
        // 锁车策略 车辆充电分钟数
        SysParam chargingMinuteParam = sysParamService.selectByKey(LOCK_CAR_CONDITION_CHARGING_MINUTE_KEY);
        final int chargingMinute = chargingMinuteParam == null ? 10 : Integer.valueOf(chargingMinuteParam.getValue());

        // 查询所有userCar 和 return的订单
        TimeShareOrder param = new TimeShareOrder();
        final List<LongTimeShareOrderVo> orders = timeShareOrderService.queryAllUserAndReturnOrders(param);

        final SysWarnItem sysWarnItem = sysWarnItemMapper.selectByCode(SysConstant.SYS_WARN_LONG_TIME_ORDER);
        // 循环查询订单金额是否大于预设金额
        if (CollectionUtils.isEmpty(orders)) return;
        for (LongTimeShareOrderVo order : orders) {
            // 获取订单金额
            final Map map = moneyCalculateService.getMoneyByOrderId(order.getOrderId());
            if (CollectionUtils.isEmpty(map)) continue;
            final Object object = map.get("money");
            if (object == null) continue;
            // 比较金额
            final int orderMoney = (Integer) object;
            // 大于预设金额 and 会员余额
            if (orderMoney > order.getBudgetAmount() && orderMoney > order.getMoney()) {
                // 查询车辆的位置
                final CarRun carRun = carRunMapper.queryCarRun(order.getLpn());
                // 获取现在的时间
                final Date now = new Date();

                // 需要预警,获取是否预警
                if (!order.getIsOneWarn().equals(ONE_WARN_YES)) {
                    // 需要发送一级预警
                    saveWarnInfo(sysWarnItem, order, carRun, now);

                    // 更新订单已经发送一级预警
                    TimeShareOrder timeShareOrder = new TimeShareOrder();
                    timeShareOrder.setIsOneWarn(1);
                    timeShareOrder.setOneWarnTime(now);
                    timeShareOrder.setId(order.getId());
                    timeShareOrderMapper.updateByPrimaryKeySelective(timeShareOrder);

                    // 查询会员电话号码
                    final String phone = memberService.getPhoneById(order.getMemberId());
                    // 发送短信 个推
                    String oneMsg = WARN_ONE_INFO.replace("{0}", order.getLpn());
                    PushCommonBean push = new PushCommonBean(SysConstant.SYS_WARN_LONG_TIME_ORDER, "1", "系统预警消息", oneMsg);
                    push(phone, push, WARN_ONE_ID, oneMsg, order, true);
                } else if (!order.getIsTwoWarn().equals(TWO_WARN_YES)) {
                    // 查看一级预警时间有没有过去5个小时
                    handleTwoWarn(oneWarnTime, sysWarnItem, order, carRun, now);
                } else if (!order.getIsLockCar().equals(LOCK_YES)) {
                    // 还没有锁车 看二级预警是否过去了1个小时需要锁车
                    handleLockCar(twoWarnTime, engineStopMinute, chargingMinute, order, carRun, now);
                } else if (order.getIsLockCar().equals(LOCK_YES)) {
                    // 已经锁车了 查看锁车多久了 需要发送二级提示
                    final Date lockCarTime = order.getLockCarTime();
                    if (lockCarTime != null && now.compareTo(DateUtils.addHours(lockCarTime, lockTime)) > 0) {
                        // 大于锁车时长6小时 结束订单 还车到临时网点
                        overOrder(order, carRun);
                    } else if (lockCarTime != null && now.compareTo(DateUtils.addHours(lockCarTime, lockTwoTipTime)) > 0
                    		&& !order.getIsLockTwoWarn().equals(LOCK_YES)) {
                    	//更新锁车二次提醒标志
                    	TimeShareOrder timeShareOrder = new TimeShareOrder();
                        timeShareOrder.setIsLockTwoWarn(LOCK_YES);
                        timeShareOrder.setId(order.getId());
                        timeShareOrderMapper.updateByPrimaryKeySelective(timeShareOrder);
                        
                        // 锁车5小时以上 发送二次锁车提醒
                        final String phone = memberService.getPhoneById(order.getMemberId());
                        // 发送短信 个推
                        String lockMsg = LOCK_CAR_INFO.replace("{0}", order.getLpn());
                        PushCommonBean push = new PushCommonBean(SysConstant.SYS_WARN_LONG_TIME_ORDER_LOCKCAR, "1", "系统锁车预警消息", lockMsg);
                        push(phone, push, LOCK_CAR_ID, lockMsg, order, true);
                    }
                }
            } else if (orderMoney <= order.getMoney() 
            		&& order.getIsOneWarn().equals(ONE_WARN_YES)) {
                // 查看会员余额是否已经大于订单金额,且已有预警,若是,则解除预警
                handleOverWarn(order);
            }
        }
    }

    /**
     * 自动结束订单
     * @param order 订单
     * @param carRun 车辆
     */
    private void overOrder(LongTimeShareOrderVo order, CarRun carRun) {
    	if(SysConstant.X_TIME_SHARE_ORDER_STATUS_FINISH.equals( order.getStatus() )){
    		return;
    	}
    	
    	List<City> cityList = cityMapper.queryCityByCode(carRun.getCityCode());
    	if(null == cityList || cityList.isEmpty()){
    		return;
    	}
    	final City city = cityList.get(0);
    	String cityCode = city.getCode();
        // 查询临时网点
        Integer parkId = parkMapper.queryParkIdIsTemporary(cityCode);
        // 创建超长订单的临时网点
    	Park tempPark = new Park();
        if (parkId == null){
    		tempPark.setLatitude(city.getLatitude());
        	tempPark.setLongitude(city.getLongitude());
        	tempPark.setName(city.getName() + "超长订单临时还车网点");
        	tempPark.setAddress(city.getName() + "超长订单临时还车网点");
        	tempPark.setCityCode(cityCode);
        	tempPark.setIsTemporary(0);//是临时网点
        	
        	tempPark.setCategory(0);//超长订单的临时网点
        	tempPark.setCooperationType(0);//自有
        	parkMapper.insertSelective(tempPark);
        	parkId = tempPark.getId();
        }
        // 更新订单还车状态
        TimeShareOrder timeShareOrder = new TimeShareOrder();
        timeShareOrder.setStatus(SysConstant.X_TIME_SHARE_ORDER_STATUS_RETURN);
        timeShareOrder.setReturnParkId(parkId);
        timeShareOrder.setId(order.getId());
        timeShareOrderMapper.updateByPrimaryKeySelective(timeShareOrder);

        /** 更新会员信息 */
//        Member member = new Member();
//        member.setStatus(SysConstant.MEMBER_STATUS_RETURN);
//        member.setId(order.getMemberId());
//        memberService.updateByPrimaryKeySelective(member);

        /** 更新车辆运行表状态 */
//        carRun.setStatus(SysConstant.CAR_RUN_STATUS_RETURN);
//        carRunMapper.updateByPrimaryKeySelective(carRun);

        // 查询会员电话号码
        final String phone = memberService.getPhoneById(order.getMemberId());
        StringBuffer sb = new StringBuffer("{");
        sb.append("\"cId\":\"platForm\",\"memberId\":\"" + order.getMemberId() + "\",\"method\":\"memberReturnCarByTemplatePark\",");
        sb.append("\"param\":\"{'orderNo':'" + order.getOrderId() + "','longitude':'" +
                carRun.getLongitude() + "','latitude':'" + carRun.getLatitude() + "','orderMileage':'0','endLocation':'platForm','parkId':'" + parkId + "'}\",");
        sb.append("\"phone\":\"" + phone + "\",\"type\":\"platForm\",\"version\":\"1\"");
        sb.append("}");
        SysParam sysParam = sysParamService.selectByKey("http_url");
        String json ;
        if (sysParam.getValue().indexOf("https") == 0) { //https接口
            json = HttpsClientUtil.get(sysParam.getValue(), sb.toString());
        } else {
            json = HttpUtils.commonSendUrl(sysParam.getValue(), sb.toString());
        }
//        String httpUrl = "http://192.168.1.84:8888/services/i/e/"; 
//        String json = HttpUtils.commonSendUrl(httpUrl, sb.toString());
        JSONObject jsonObject = JSONObject.fromObject(json);
        String code = jsonObject.getString("code");
        if (code.equals("00")) {
            /** 给会员推送取消订单信息 */
//            SystemMsgLog systemMsgLog = new SystemMsgLog();
//            systemMsgLog.setMsgType("timeShareReturnCar");
//            systemMsgLog.setCreateTime(new Date());
//            systemMsgLog.setMemberId(order.getMemberId());
//            systemMsgLog.setMsgTitle("时租还车");
//            systemMsgLog.setMsgContent("【宜步出行】尊敬的会员，您的订单" + carRun.getOrderId() + "于" + DateTime.getString() + ",系统自动进行还车!");
//            systemMsgLogMapper.insertSelective(systemMsgLog);
//            PushCommonBean pushCommonBean = new PushCommonBean(
//                    SysConstant.SERVER_PUSH_TIME_SHARE_RETURN_CAR, "1", "", carRun.getOrderId());
//            List<String> cidMemberList = PushClient.queryClientId(phone);
//            for (String memberCid : cidMemberList) {
//                PushClient.push(memberCid,JSONObject.fromObject(pushCommonBean));
//            }
        	PushCommonBean pushCommonBean = new PushCommonBean(
                  SysConstant.SERVER_PUSH_TIME_SHARE_RETURN_CAR, "1", "", carRun.getOrderId());
            push(phone, pushCommonBean, ORDER_FINISH_ID, ORDER_FINISH_INFO, order, false);
        }
    }

    /**
     * 解除预警处理
     *
     * @param order 订单
     */
    private void handleOverWarn(LongTimeShareOrderVo order) {
//        TimeShareOrder timeShareOrder = timeShareOrderMapper.selectByPrimaryKey(order.getId());
//        if (timeShareOrder != null &&  timeShareOrder.getIsOneWarn().equals(ONE_WARN_YES)
//                &&  !timeShareOrder.getIsLockCar().equals(LOCK_YES)) {
    	if(null != order){
            // 解除预警
            timeShareOrderMapper.updateOverWarn(order.getOrderId());

            // 发送解除预警通知
            final String phone = memberService.getPhoneById(order.getMemberId());
            // 发送短信 个推
            String overMsg = WARN_OVER_INFO.replace("{0}", order.getLpn());
            PushCommonBean push = new PushCommonBean("sys_warn_long_time_order_over", "1", "系统提示消息", overMsg);
            push(phone, push, WARN_OVER_ID, overMsg, order, true);
        }
    }

    /**
     * 处理二级预警
     *
     * @param oneWarnTime
     * @param sysWarnItem
     * @param order
     * @param carRun
     * @param now
     */
    private void handleTwoWarn(int oneWarnTime, SysWarnItem sysWarnItem, LongTimeShareOrderVo order, CarRun carRun, Date now) {
        final Date warnTime = order.getOneWarnTime();
        if (warnTime != null && now.compareTo(DateUtils.addHours(warnTime, oneWarnTime)) > 0) {
            // 已经过去了5小时 发送二级预警
            saveWarnInfo(sysWarnItem, order, carRun, now);

            // 更新订单已经发送二级预警
            TimeShareOrder timeShareOrder = new TimeShareOrder();
            timeShareOrder.setIsTwoWarn(1);
            timeShareOrder.setTwoWarnTime(now);
            timeShareOrder.setId(order.getId());
            timeShareOrderMapper.updateByPrimaryKeySelective(timeShareOrder);
            
            // 查询会员电话号码
            final String phone = memberService.getPhoneById(order.getMemberId());
            // 发送短信 个推
            String twoMsg = WARN_TWO_INFO.replace("{0}", order.getLpn());
            PushCommonBean push = new PushCommonBean(SysConstant.SYS_WARN_LONG_TIME_ORDER, "1", "系统预警消息", twoMsg);
            push(phone, push, WARN_TWO_ID, twoMsg, order, true);
        }
    }

    /**
     * 锁定车辆逻辑处理
     *
     * @param twoWarnTime
     * @param engineStopMinute
     * @param chargingMinute
     * @param order
     * @param carRun
     * @param now
     */
    private void handleLockCar(int twoWarnTime, int engineStopMinute, int chargingMinute, LongTimeShareOrderVo order, CarRun carRun, Date now) {
        final Date warnTime = order.getTwoWarnTime();
        if (warnTime != null && now.compareTo(DateUtils.addHours(warnTime, twoWarnTime)) > 0) {
            // 一个小时过去了 需要准备锁车
            // 1.车辆在熄火状态下，车速为0，gps未发生变化，熄火时长达15分钟（可配置），当满足这几种状态时，将对车进行锁定。
            // 2.熄火停车状态，车辆动力电池充电时长超过10分钟，也进行锁定。
            final String readyStatus = carRun.getCarSignal();
            if (SysConstant.READY_STOP.equals(readyStatus) && "0".equals(carRun.getSpeed())) {
//            	Date latestTime = (null != carRun.getUpdateTime() ? carRun.getUpdateTime() : now);
                // 查询该车载熄火给定时间的车辆上报信息
                Map<String, Object> paramMap = new HashMap<String, Object>();
                paramMap.put("lpn", order.getLpn());
                paramMap.put("createTime", DateUtils.addMinutes(now, -engineStopMinute));
                List<CarRunLog> carRunLogs = carRunLogMapper.queryCarRunLogByLpnAndDate(paramMap);	
                boolean flag1 = true; // 条件一
                // 循环处理
                if (CollectionUtils.isEmpty(carRunLogs)) return;
                for (CarRunLog carRunLog : carRunLogs) {
                    if (!"0".equals(carRunLog.getSpeed()) || !SysConstant.READY_STOP.equals(carRunLog.getCarSignal())) {
                        flag1 = false;
                    }
                }

                if (flag1) {
                    // 锁车
                    lockCar(order, now);
                    return;
                }

                paramMap.put("createTime", DateUtils.addMinutes(now, -chargingMinute));
                carRunLogs = carRunLogMapper.queryCarRunLogByLpnAndDate(paramMap);
                boolean flag2 = true; // 条件二
                // 循环处理
                if (CollectionUtils.isEmpty(carRunLogs)) return;
                for (CarRunLog carRunLog : carRunLogs) {
                    if (!"0".equals(carRunLog.getBatStatus()) || !SysConstant.READY_STOP.equals(carRunLog.getCarSignal())) {
                        flag2 = false;
                    }
                }
                if (flag2) {
                    // 锁车
                    lockCar(order, now);
                }
            }
        }
    }

    /**
     * 锁车操作
     *
     * @param order 订单
     * @param now   当前时间
     */
    private void lockCar(LongTimeShareOrderVo order, Date now) {
        TimeShareOrder timeShareOrder = new TimeShareOrder();
        timeShareOrder.setIsLockCar(1);
        timeShareOrder.setLockCarTime(now);
        timeShareOrder.setId(order.getId());
        timeShareOrderMapper.updateByPrimaryKeySelective(timeShareOrder);

        // 查询会员电话号码
        final String phone = memberService.getPhoneById(order.getMemberId());
        // 发送短信 个推
        String lockMsg = LOCK_CAR_INFO.replace("{0}", order.getLpn());
        PushCommonBean push = new PushCommonBean(SysConstant.SYS_WARN_LONG_TIME_ORDER_LOCKCAR, "1", "系统锁车预警消息", lockMsg);
        push(phone, push, LOCK_CAR_ID, lockMsg, order, true);
    }

    /**
     * 保存预警信息到数据库
     *
     * @param sysWarnItem 预警项
     * @param order       超长订单
     * @param carRun      carRun
     * @param now         现在的时间
     */
    private void saveWarnInfo(SysWarnItem sysWarnItem, LongTimeShareOrderVo order, CarRun carRun, Date now) {
        SysWarnInfo sysWarnInfo = new SysWarnInfo();
        sysWarnInfo.setCreateTime(now);
        sysWarnInfo.setMemberId(order.getMemberId());
        sysWarnInfo.setOrderId(order.getOrderId());
        sysWarnInfo.setToDispatch(sysWarnItem.getToDispatch());
        sysWarnInfo.setWarnItemCode(SysConstant.SYS_WARN_LONG_TIME_ORDER);
        sysWarnInfo.setLpn(order.getLpn());
        sysWarnInfo.setParkId(carRun.getParkId());
        sysWarnInfo.setWarnContent(sysWarnItem.getWarnTpl().replace("{0}", order.getLpn())
                .replace("{1}", carRun.getAddress()));
//        warnInfoService.insert(sysWarnInfo);
        warnInfoNosql.insert(JSON.toJSONString(sysWarnInfo));
    }

    /**
     * 推送消息
     *
     * @param phone 电话
     * @param push  内容
     */
    private void push(String phone, PushCommonBean push, Integer templateId, String content, LongTimeShareOrderVo order, boolean hasParam) {
    	//系统消息
    	SystemMsgLog systemMsgLog = new SystemMsgLog();
        systemMsgLog.setMsgType("longOrder");
        systemMsgLog.setCreateTime(new Date());
        systemMsgLog.setMemberId(order.getMemberId());
        systemMsgLog.setMsgTitle("超长订单");
        systemMsgLog.setMsgContent(content);
        systemMsgLogMapper.insertSelective(systemMsgLog);
        
        List<String> alias = PushClient.queryClientId(phone);
        if (!alias.isEmpty() && alias.size() > 0) {
            for (String cid : alias) {
                PushClient.push(cid, net.sf.json.JSONObject.fromObject(push));
            }
        }
        // 发送短信
        if(hasParam){
        	SendSMS.sendSMS(phone, "", templateId, order.getLpn());      	
        }else{
        	SendSMS.sendSMS(phone, "", templateId, "");  
        }
    }
}
