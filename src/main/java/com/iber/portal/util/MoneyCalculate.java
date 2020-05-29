package com.iber.portal.util;

import org.apache.commons.lang.time.FastDateFormat;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.util.Date;

/**
 * 计算时租订单金额
 * 2017-5-15 14:07:10
 * xj
 */

public class MoneyCalculate {

	private static final Logger LOGGER = LoggerFactory.getLogger(MoneyCalculate.class);

	private static final String PARTTERN = "yyyyMMddHHmmss";
	private static final String DEFAULT_PARTTREN = "yyyy-MM-dd HH:mm:ss";
	private static final FastDateFormat DEFAULT_FORMAT= FastDateFormat.getInstance(PARTTERN);
	private static final String[] PARTTERNS = new String[]{PARTTERN,DEFAULT_PARTTREN};

	/**
	 * 解析日期
	 * @param str
	 * @return
	 */
	public static Date getDate(String str) {
		try {
			return DateUtils.parseDate(str, PARTTERNS);
		} catch (ParseException e) {
			LOGGER.error("解析日期失败!",e.getMessage());
			throw new RuntimeException(e);
		}
	}

	/**
	 * 格式日期
	 * lf
	 * 2017-5-15 14:44:37
	 * @param date
	 * @return
	 */
	public static String format(Date date){
		return DEFAULT_FORMAT.format(date);
	}

	
	/**
	 * 时间差（分钟）
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static int getDateMin(Date d1, Date d2) {
		long l1 = d1.getTime();
		long l2 = d2.getTime();
		Long l = (l2 - l1) / 1000 / 60;
		Long l3 = (l2 - l1) /1000 % 60 > 0 ? 1L : 0L;
		return Long.valueOf(l+l3).intValue();
	}
	
	public static String timeDifferenceDay(Date startTime, Date endTime) {
		long nd = 1000*24*60*60;//一天的毫秒数
		long nh = 1000*60*60;//一小时的毫秒数
		long nm = 1000*60;//一分钟的毫秒数
		long ns = 1000;//一秒钟的毫秒数
		long diff;
		try {
			//获得两个时间的毫秒时间差异
			diff = endTime.getTime() - startTime.getTime();
			long day = diff/nd;//计算差多少天
			long hour = diff%nd/nh;//计算差多少小时
			long min = diff%nd%nh/nm;//计算差多少分钟
			long sec = diff%nd%nh%nm/ns;//计算差多少秒//输出结果
			System.out.println("时间相差："+day+"天"+hour+"小时"+min+"分钟"+sec+"秒。");
			if(sec == 0) {
				return day+"#"+(hour * 60 + min) ;
			}else{
				return day+"#"+ (hour * 60 + min + 1) ;
			}
		}catch (Exception e) {
			
		}
		return "0#0" ;
	}
	
}
