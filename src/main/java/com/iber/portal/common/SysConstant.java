package com.iber.portal.common;

public final class SysConstant {
	
	/**活动管理*/
	public static final String ACTIVITY_TYPE = "ACTIVITY_TYPE";

	/**会员状态*/
	public static final String MEMBER_STATUS_READY = "ready";
	
	public static final String MEMBER_STATUS_USECAR = "useCar";
	
	public static final String MEMBER_STATUS_RETURN = "return";
	
	/**运行车辆状态*/
	public static final String CAR_RUN_STATUS_EMPTY = "empty" ;
	
	public static final String CAR_RUN_STATUS_USECAR = "useCar" ;
	
	public static final String CAR_RUN_STATUS_RETURN = "return" ;
	
	public static final String CAR_RUN_STATUS_ORDERED = "ordered" ;
	
	public static final String CAR_RUN_STATUS_CHARING = "charging" ;
	
	public static final String CAR_RUN_STATUS_MAINTAIN = "maintain" ;
	
	public static final String CAR_RUN_STATUS_REPAIR = "repair" ;
	
	/**订单状态*/
	public static final String X_TIME_SHARE_ORDER_STATUS_ORDERED = "ordered" ;
	
	public static final String X_TIME_SHARE_ORDER_STATUS_CANCEL = "cancel" ;
	
	public static final String X_TIME_SHARE_ORDER_STATUS_USECAR = "useCar" ;
	
	public static final String X_TIME_SHARE_ORDER_STATUS_RETURN = "return" ;
	
	public static final String X_TIME_SHARE_ORDER_STATUS_FINISH = "finish" ;
	
	/**订单类型*/
	public static final String X_TIME_SHARE_ORDER_TYPE_ENTERPRISEORDER = "enterpriseOrder" ;
	
	public static final String X_TIME_SHARE_ORDER_TYPE_PERSONORDER = "personOrder" ;
	
	public static final String MEMBER_LEVEL_INSIDE = "inside" ;
	
	public static final String ORDER_TYPE_TIMESHARE = "TS" ;
	
	public static final String ORDER_TYPE_DAYRENT = "DR" ;

	/**企业审核，待审核*/
	public static final String ENTERPRISE_CHECK_STATUS_CHECKWAIT = "checkWait" ;
	/**企业审核，审核通过*/
	public static final String ENTERPRISE_CHECK_STATUS_CHECKSUCC = "checkSucc" ;
	/**企业审核，审核不通过*/
	public static final String ENTERPRISE_CHECK_STATUS_CHECKFAIL = "checkFail" ;
	/**企业审核，无需审核*/
	public static final String ENTERPRISE_CHECK_STATUS_CHECKWITHOUT = "checkWithout" ;
	
	/**可用电量*/
	public static final String USE_CAR_RESTBATTERY = "use_car_restBattery" ;
	
	/**个人最低充值押金线*/
	public static final String PERSON_DEPOSIT = "personDeposit" ;
	
	/***/
	public static final String MYSELF_PAY = "myself_pay";
	public static final String ENTERPRISE_PAY = "enterprise_pay" ;
	
	/**给车载推送订单*/
	public static final String SERVER_PUSH_ORDER = "server_push_order" ;
	/**给车载推送网点gps，进行导航指令*/
	public static final String SERVER_PUSH_CAR_NAVIGATION = "server_push_gps_info" ;
	/**给车载推送取消导航指令*/
	public static final String SERVER_PUSH_CAR_CANCEL_NAVIGATION = "server_push_car_cancel_navigation";
	/**给车载推送熄火指令*/
	public static final String SERVER_PUSH_CAR_FIRE_OFF = "server_push_car_fire_off";
	
	/**企业会员状态,待审核*/
	public static final String ENTERPRISE_MEMBER_CHECK_STATUS_CHECKWAIT = "0" ;
	/**企业会员状态，审核通过*/
	public static final String ENTERPRISE_MEMBER_CHECK_STATUS_CHECKSUCC = "1" ;
	
	/**优惠券使用状态*/
	public static final String COUPON_USE_STATUS_USABLE = "0" ;//可用状态 0可用  1不可使用
	public static final String COUPON_USE_STATUS_INVALID = "1" ;
	/**推送绑定优惠券信息给会员*/
	public static final String SERVER_PUSH_BING_COUPON = "server_push_bing_coupon" ;
	/**日租推送指令 - 车载*/
	public static final String SERVER_PUSH_DAY_ORDER = "server_push_day_order" ;
	/**日租推送绑定车辆成功指令 - 会员*/
	public static final String SERVER_PUSH_DAY_ORDER_BOUND_LPN = "server_push_day_order_bound_lpn" ;
	
    /**日租支付订单状态*/
    public static final String DAY_RENT_ORDER_PAY_STATUS_NO_PAY = "noPay";//未支付
    public static final String DAY_RENT_ORDER_PAY_STATUS_FINISH = "finish";//已支付
    
    /**日租扩展表订单类型*/
    public static final String DAY_RENT_TYPE_DAY_RENT = "dayRent";//日租
    public static final String DAY_RENT_TYPE_CONTINUE_RENT = "continueRent";//续租
    public static final String DAY_RENT_TYPE_TIMEOUT_RENT = "timeoutRent";//超时
	
    /**日租订单状态（预约、已绑定、用车、完成、取消）*/
	public static final String X_DAY_RENT_ORDER_STATUS_ORDERED = "ordered" ;
	
	public static final String X_DAY_RENT_ORDER_STATUS_BOUND = "bound" ;
	
	public static final String X_DAY_RENT_ORDER_STATUS_USECAR = "useCar" ;
	
	public static final String X_DAY_RENT_ORDER_STATUS_FINISH = "finish" ;
	
	public static final String X_DAY_RENT_ORDER_STATUS_CANCEL = "cancel" ;
	
	public static final String SYS_WARN_CAR_AND_PARK_DISTANCE = "sys_warn_car_and_park_distance";
	// 超长订单预警
	public static final String SYS_WARN_LONG_TIME_ORDER = "sys_warn_long_time_order";
	public static final String SYS_WARN_LONG_TIME_ORDER_LOCKCAR = "sys_warn_long_time_order_lockCar";

	public static final String BALANCE_PAY_TYPE = "B";//余额
	public static final String DEPOSIT_PAY_TYPE = "D";//押金
    public static final String ALI_PAY_TYPE = "A";//支付宝
    public static final String CAIFU_PAY_TYPE = "T";//财付通
    public static final String WEIXIN_PAY_TYPE = "WX";//微信
    public static final String YINLIAN_PAY_TYPE = "U";//银联
    public static final String APPLE_PAY_TYPE = "AP";//applePay
    
    public static final String PILE_TYPE_FAST = "fast";//快充
    public static final String PILE_TYPE_SLOW = "slow";//慢充
    
    public static final String PILE_STATUS_EMPTY = "empty";//空闲
    public static final String PILE_STATUS_HANDSHAKE = "handshake";//握手
    public static final String PILE_STATUS_CHARGING = "charging";//充电
    public static final String PILE_STATUS_REPAIR = "repair";//维修
    
    public static final String CHARGING_ORDER_STATUS_ORDERED = "ordered";//预约
    public static final String CHARGING_ORDER_STATUS_CHARGING = "charging";//充电
    public static final String CHARGING_ORDER_STATUS_NOPAY = "noPay";//待支付
    public static final String CHARGING_ORDER_STATUS_FINISH = "finish";//完成
    public static final String SERVER_PUSH_CAPITAL_BLOCK = "server_push_capital_block" ;//资金冻结
    
    public static final String SERVER_PUSH_TIME_SHARE_RETURN_CAR = "server_push_time_share_return_car" ; //还车推送指令
    
    
    /**监控中心的统计类型：0:次数, 1:人数、2:时长、3:里程、4:收入*/
    public static final Integer MONITOR_COUNT_TYPE_CNT = 0;
    public static final Integer MONITOR_COUNT_TYPE_MEMBER_CNT = 1;
    public static final Integer MONITOR_COUNT_TYPE_DURATION = 2;
    public static final Integer MONITOR_COUNT_TYPE_MILEAGE = 3;
    public static final Integer MONITOR_COUNT_TYPE_INCOME = 4;
    
    // 100，元和分的转换
    public static final float HUNDRED_FLOAT = 100f;
    
    /**推送重启后视镜*/
	public static final String SERVER_PUSH_REBOOT_SYSTEM = "server_push_reboot_system";
	
	/**推送设置盒子类型*/
	public static final String SERVER_PUSH_SET_BOX_TYPE = "server_push_set_box_type";
	
	/**服务器推送重启盒子*/
	public static final String SERVER_PUSH_REBOOT_BOX = "server_push_reboot_box";
	
	/**车辆一键启动*/
	public static final String SERVER_PUSH_START_CAR = "server_push_start_car";

	/**日租通过管理平台还车*/
	public static final String SERVER_PUSH_LONT_RENT_RETURN_CAR = "server_push_lont_rent_return_car";
	/**
	 * 车辆熄火状态
	 */
	public static final String READY_STOP = "0";

	public  static final Integer ATTACHEDORDER = 27; //附属订单的消息类型id

	public static final String SERVER_PUSH_OPEN_DOOR = "server_push_open_door";

	public static final String DEFAULT_CHARSET = "UTF-8";

	/**
	 * 长时间违章未处理天数
	 */
    public static final String WZ_LONG_TIME_UNDISPOSED = "wz_long_time_undisposed";

	/**
	 * 长时间违章未处理次数限制
	 */
	public static final String WZ_LONG_TIME_UNDISPOSED_NUMBER = "wz_long_time_undisposed_number";

    /**优惠券coupon itemCode*/
	public static final String COUPON_ITEM_CODE_CHARGE = "chargeResult" ;//会员充值赠送

	/**接口返回成功*/
	public static final String INTERFACE_RESULT_SUCCESS = "00";

	public static final Integer FREEZE_CARD = 0;

	public static final Integer UN_FREEZE_CARD = 1;
	
	/**
	 * 日租到期前提醒
	 */
	public static final Integer LONG_RENT_BEFORE_EXPIRE_24 = 31 ;//日租到期第一次提醒
	public static final Integer LONG_RENT_BEFORE_EXPIRE_4 = 32 ;//日租到期第二次提醒
	public static final Integer RENEW_ORDER_BEFORE_EXPIRE_24 = 33 ;//续租到期第一次提醒
	public static final Integer RENEW_ORDER_BEFORE_EXPIRE_4 = 34 ;//续租到期第二次提醒
	
    public static final String LONG_RENT_CHANGE_PARKNAME = "日租换车临时网点";
    
    public static final String CAR_PROBLEM = "CAR_PROBLEM" ;
    public static final String TECHNOLOGY_PROBLEM = "TECHNOLOGY_PROBLEM" ;

	/**
	 * 小电瓶亏电阈值 发送短信提醒用户启动车辆
	 */
	public final static String SMALL_BATTERY_SEND_MSG = "small_battery_send_msg";

    /**
     *百度access_key
     */
	public final static String BAIDU_ACCESS_KEY="91aeaa7bdaa54323a6ec0338565443a9";
    /**
     *宜步出行api key
     */
	public final static String BAIDU_API_KEY="FuIejOVcgbMhEjkhwkQfNKhP";

    /**
     *百度secret_key
     */
    public final static String BAIDU_SECRET_KEY="bT0oxh0guqCDj121HArnGQSTEKiv4BLI";
    /**
     *百度ACCESS_TOKEN
     */
    public final static String BAIDU_ACCESS_TOKEN="24.72f9a263af41cd360d0ac78d6b907e4b.2592000.1525923616.282335-10929845";
}
