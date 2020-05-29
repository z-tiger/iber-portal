/*
 * 
 */
package com.iber.portal.enums;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 会员行为的名称的枚举
 * @author ouxx
 * @since 2017-4-14 下午7:47:37
 *
 */
public enum MemberBehaviorNameEnum {
	CAR_SCRATCH("CAR_SCRATCH"),
	CAR_SCRATCH_SUB("CAR_SCRATCH_SUB"),
	CAR_WHEEL_SWELL("CAR_WHEEL_SWELL"),
	CAR_WHEEL_SWELL_SUB("CAR_WHEEL_SWELL_SUB"),
	CAR_IN_THROW_LITTER("CAR_IN_THROW_LITTER"),
	CAR_IN_CARRY_PET("CAR_IN_CARRY_PET"),
	CAR_NOT_DEAL_WELL("CAR_NOT_DEAL_WELL"),
	CHARGING_PILE_DAMAGE("CHARGING_PILE_DAMAGE"),
	OTHER_THINGS("OTHER_THINGS"),
	REGISTER("REGISTER"),//新用户注册
	UPLOAD_CAR_INFO("UPLOAD_CAR_INFO"),//上传车牌
	ZHIMA_SCORE("ZHIMA_SCORE"),//芝麻分
	CHARGE_BALANCE("CHARGE_BALANCE"),//充值余额
	CONSUMPTION("CONSUMPTION"),//消费
	RETURN_CAR_IN_2S_PARK("RETURN_CAR_IN_2S_PARK"),//还车到2S网点
	CHARGING_AFTER_RETURN_CAR("CHARGING_AFTER_RETURN_CAR"),//还车扫码充电
	GOOD_RECEPTION_AND_SHARE("GOOD_RECEPTION_AND_SHARE"),//用车好评，并传播的,增加贡献值+20
	CONTINUOUS_NO_WZ_AND_RESCUE("CONTINUOUS_NO_WZ_AND_RESCUE"),//连续5次用车无违章、救援 +50贡献值
	/**车辆整洁度评分，送20个贡献值*/
	MEMBER_CONTRIBUTED_NEAT_LEVEL_ACCESS("MEMBER_CONTRIBUTED_NEAT_LEVEL_ACCESS"),
	MEMBER_CONTRIBUTED_NEAT_LEVEL_ACCESS_SUB("MEMBER_CONTRIBUTED_NEAT_LEVEL_ACCESS_SUB"),
	
	UNPAID_ORDER("UNPAID_ORDER"),//未支付订单超过7天不支付的 -50
	BREAK_RULES("BREAK_RULES"),//违章 -100
	BREAK_RULES_POINT("BREAK_RULES_POINT"),//违章驾驶导致扣分  扣1分 -50贡献值
	BREAK_RULES_NO_DEALT("BREAK_RULES_NO_DEALT"),//自违章日期起超过30天不作违章处理的  -100贡献值
	ASSIST_CLEANING_CAR("ASSIST_CLEANING_CAR"),//协助清理汽车
	DRIVING_2_YEAR("DRIVING_2_YEAR"),//用户驾龄2年及以上 +500
	DRIVING_1_YEAR("DRIVING_1_YEAR"),//用户驾龄1年及以上 +1500 2017-5-17 14:27:30 新的
	CAR_VIOLATION_PARKING("CAR_VIOLATION_PARKING"),//车辆乱停、违停

	RESCUE_MEMBER_RESPONSIBILITY("RESCUE_MEMBER_RESPONSIBILITY"), //用户责任导致道路救援
	RESCUE_ACCIDENT_ALL("RESCUE_ACCIDENT_ALL"), //车辆出险，用户全责
	RESCUE_ACCIDENT_IMPORT("RESCUE_ACCIDENT_IMPORT"), //车辆出险，用户主责
	RESCUE_ACCIDENT_PART("RESCUE_ACCIDENT_PART") ,  //车辆出险，用户部分责任
	RESCUE_ACCIDENT_NO("RESCUE_ACCIDENT_NO") ,  //车辆出险，用户无责任
	RESCUE_ACCIDENT_CHEAT("RESCUE_ACCIDENT_CHEAT") , //存在骗优惠券行为
	
	BLACK_WZ("BLACK_WZ"),//车辆违章拒不配合处理
	BLACK_ACCIDENT("BLACK_ACCIDENT"),//车辆出险拒不配合处理
	BLACK_FOUL("BLACK_FOUL"),//酒驾、醉驾、嗑药驾驶
	BLACK_NOT_DRIVING_LICENCE("BLACK_NOT_DRIVING_LICENCE"),//车辆交由无驾照人员使用
	
	MEMBER_CULTURE_USECAR("MEMBER_CULTURE_USECAR"),//会员文明用车
	
	CAR_NOT_CLOSE_LIGHT("CAR_NOT_CLOSE_LIGHT"),//未关闭车灯
	
	REFUSED_TO_COOPERATE_WITH("REFUSED_TO_COOPERATE_WITH"),//违章拒绝配合
	
	BLACK_NOT_DRIVING("BLACK_NOT_DRIVING"),//车辆交由无驾照人员使用
	
	BLACK_NOT_PAY("BLACK_NOT_PAY"),//拒不支付订单金额
	
	BLACK_OTHER("BLACK_OTHER"),//其他拉黑项

	CANCEL_BLACKLIST("CANCEL_BLACKLIST"),//撤销黑名单
	CLEAN_CAR("CLEAN_CAR"),//文明用车 清洗车辆 审核通过加50分
	RETURN_CAR_USE_CAR("RETURN_CAR_USE_CAR"), //还车后占用车辆
	CIVILIZED_OTHER_THING("CIVILIZED_OTHER_THING"),//文明用车其他项
	NOT_CIVILIZED_OTHER_THING("NOT_CIVILIZED_OTHER_THING")//不文明用车其他项
	;

	private static final Logger logger = LoggerFactory.getLogger(MemberBehaviorNameEnum.class);
	
	private String name;
	public String getName(){
		return name;
	}
	
	MemberBehaviorNameEnum(String name){
		this.name = name;
	}
	
	/**
	 * 根据key获取枚举
	 * @param name
	 * @return
	 * @author ouxx
	 * @date 2017-1-6 上午10:28:19
	 */
	public static MemberBehaviorNameEnum getByName(String name){
		MemberBehaviorNameEnum tempEnum = null;
		for(MemberBehaviorNameEnum en : MemberBehaviorNameEnum.values()){
			if(name.equals(en.getName())){
				tempEnum = en;
				break;
			}
		}
		if(tempEnum == null){
			logger.error("MemberBehaviorNameEnum-Enum value not exist, key = " + name);
			throw new RuntimeException("Enum value not exist");
		}
		return tempEnum;
	}
}
