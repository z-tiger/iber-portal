package com.iber.portal.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

public class CalculateTime {

	public static int getMinute(int minutes , int timeUnit){
		int minuteCount = minutes / timeUnit;
		int minute = minutes % timeUnit;
		int lastMinuteCount = minuteCount + (minute > 0 ? 1 : 0);
		return lastMinuteCount ;
	}
	
	public static Date getDate(String str) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Date d = null;
		try {
			d = sdf.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return d;
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
		return l.intValue();
	}
	
	/**
	 * 时间差（小时）
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static int getDateHour(Date d1, Date d2) {
		long l1 = d1.getTime();
		long l2 = d2.getTime();
		Integer l = (int)Math.ceil((double)(l2 - l1) / 1000/ 60/60);
		return l.intValue();
	}
	
	public static void main(String[] args) {
//		String startTime = "20160527181812" ;
//		String endTime =   "20160530091424" ;
//		System.err.println(firstDayMinute(startTime, "yyyyMMddHHmmss")) ;
//		
//		Date st = getDate(startTime);
//		Date et = getDate(endTime);
//		// 计算时间长度，并且根据时间单位进行向上取整
//		int minute = getDateMin(st, et);
//		if((minute / 1440) >= 1 ){ //时间有跨天数
//			Integer timeRate = 12; // 时间计费单位
//			Integer milesRate = 1;// 里程计费单位
//			Integer minConsump = 13;// 最低消费
//			Integer maxConsump = 130;// 当天最高消费
//			
//			int timeUnit = 60;
//			Integer mile = 81 ;
//			
//			int firstM = getMinute(firstDayMinute(startTime, "yyyyMMddHHmmss"), timeUnit);
//			Integer firstMoney = firstM * timeRate + mile * milesRate; //第一天花费金额
//			if (firstMoney <= minConsump){
//				firstMoney = minConsump;
//			}else if(firstMoney >= maxConsump){
//				firstMoney = maxConsump ;
//			}
//			System.err.println("第一天消费："+firstMoney );
//			System.err.println(day(startTime, endTime, "yyyyMMddHHmmss")) ;
//			//2.获取中间天数
//			int otherDay = CalculateTime.day(startTime, endTime, "yyyyMMddHHmmss") ;
//			int otherDayMoney = maxConsump * otherDay ;
//			
//			System.err.println("第二天消费："+otherDayMoney );
//			
//			System.err.println(lastDayMinute(endTime, "yyyyMMddHHmmss")) ;
//			
//			int lastDateMinute = CalculateTime.lastDayMinute(endTime, "yyyyMMddHHmmss") ;
//			int lastM = getMinute(lastDateMinute, timeUnit);
//			Integer lastMoney = lastM * timeRate + mile * milesRate; //第一天花费金额
//			if (lastMoney <= minConsump){
//				lastMoney = minConsump;
//			}else if(lastMoney >= maxConsump){
//				lastMoney = maxConsump ;
//			}
//			
//			System.err.println("第三天消费："+firstMoney );
//			
//		}
	}
	
	/**
	 * 计算第一天所用车分钟
	 * @param startTime
	 * @param endTime
	 * @param format
	 * @return
	 */
	public static int firstDayMinute(String startTime, String format) {
		String endTime = startTime.substring(0,8)+"235959" ;
		SimpleDateFormat sd = new SimpleDateFormat(format);
		long nd = 1000*24*60*60;//一天的毫秒数
		long nh = 1000*60*60;//一小时的毫秒数
		long nm = 1000*60;//一分钟的毫秒数
		long ns = 1000;//一秒钟的毫秒数
		long diff;
		try {
			//获得两个时间的毫秒时间差异
			diff = sd.parse(endTime).getTime() - sd.parse(startTime).getTime();
			long day = diff/nd;//计算差多少天
			long hour = diff%nd/nh;//计算差多少小时
			long min = diff%nd%nh/nm;//计算差多少分钟
			long sec = diff%nd%nh%nm/ns;//计算差多少秒//输出结果
//			System.out.println("时间相差："+day+"天"+hour+"小时"+min+"分钟"+sec+"秒。");
			if(sec == 0) {
				return (int) (hour * 60 + min) ;
			}else{
				return (int) (hour * 60 + min + 1) ;
			}
		}catch (Exception e) {
			
		}
		return 0 ;
	}
	
	/**
	 * 计算所用车天数
	 * @param startTime
	 * @param endTime
	 * @param format
	 * @return
	 */
	public static int day(String startTime, String endTime, String format) {
		startTime = addOneday(startTime).substring(0,8)+"000000";
		endTime = endTime.substring(0,8)+"000000" ;
		//按照传入的格式生成一个simpledateformate对象
		SimpleDateFormat sd = new SimpleDateFormat(format);
		long nd = 1000*24*60*60;//一天的毫秒数
		long diff;
		try {
			//获得两个时间的毫秒时间差异
			diff = sd.parse(endTime).getTime() - sd.parse(startTime).getTime();
			long day = diff/nd;//计算差多少天
			return (int) day ;
		}catch (Exception e) {
			
		}
		return 0 ;
	}
	
	/**
	 * 计算最后一天所用车时间分钟
	 * @param startTime
	 * @param endTime
	 * @param format
	 * @return
	 */
	public static int lastDayMinute(String endTime, String format) {
		String startTime = endTime.substring(0,8)+"000000" ;
		//按照传入的格式生成一个simpledateformate对象
		SimpleDateFormat sd = new SimpleDateFormat(format);
		long nd = 1000*24*60*60;//一天的毫秒数
		long nh = 1000*60*60;//一小时的毫秒数
		long nm = 1000*60;//一分钟的毫秒数
		long ns = 1000;//一秒钟的毫秒数
		long diff;
		try {
			//获得两个时间的毫秒时间差异
			diff = sd.parse(endTime).getTime() - sd.parse(startTime).getTime();
			long day = diff/nd;//计算差多少天
			long hour = diff%nd/nh;//计算差多少小时
			long min = diff%nd%nh/nm;//计算差多少分钟
			long sec = diff%nd%nh%nm/ns;//计算差多少秒//输出结果
			System.out.println("时间相差："+day+"天"+hour+"小时"+min+"分钟"+sec+"秒。");
			if(sec == 0) {
				return (int) (hour * 60 + min) ;
			}else{
				return (int) (hour * 60 + min + 1) ;
			}
		}catch (Exception e) {
			
		}
		return 0 ;
	}
	
	public static String addOneday(String today){   
        SimpleDateFormat f =  new SimpleDateFormat("yyyyMMddHHmmss");   
        try{   
            Date  d  =  new Date(f.parse(today).getTime()+24*3600*1000);     
            return  f.format(d);   
        }catch(Exception ex) {   
            return   "输入格式错误";     
        }   
    }
	
	public static int timeDifference(Date startTime, Date endTime) {
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
				return (int) (day*24*60+hour * 60 + min) ;
			}else{
				return (int) (day*24*60+hour * 60 + min + 1) ;
			}
		}catch (Exception e) {
			
		}
		return 0 ;
	}
	
	public static Map<String, Object> getMoney() {
		String startTime = "20160518194803" ;
		String endTime =   "20160519104400" ;
		Integer mile = ((Double)Math.ceil(Double.parseDouble("0"))).intValue() ;
		
		Date st = getDate(startTime);
		Date et = getDate(endTime);
		// 计算时间长度，并且根据时间单位进行向上取整
		int minute = getDateMin(st, et);

		Integer timeRate = 12; // 时间计费单位
		Integer milesRate = 1;// 里程计费单位
		Integer minConsump = 13;// 最低消费
		Integer maxConsump = 130;// 当天最高消费
		
		int timeUnit = 60;
		Map<String, Object> map = new HashMap<String, Object>();
		
		int j = minute / timeUnit;
		int k = minute % timeUnit;
		int m = j + (k > 0 ? 1 : 0);
		map.put("minute", minute); //时间 分钟
		map.put("timeMny", m * timeRate); //时间花费
		map.put("milesMny", mile * milesRate); //里程花费
		
		if((minute / 1440) >= 1 ){ //时间有跨天数
			//1.获取第一天用车分钟
			int firstDateMinute = CalculateTime.firstDayMinute(startTime, "yyyyMMddHHmmss") ;
			int firstM = getMinute(firstDateMinute, timeUnit);
			Integer firstMoney = firstM * timeRate + mile * milesRate; //第一天花费金额
			if (firstMoney <= minConsump){
				firstMoney = minConsump;
			}else if(firstMoney >= maxConsump){
				firstMoney = maxConsump ;
			}
			
			//2.获取中间天数
			int otherDay = CalculateTime.day(startTime, endTime, "yyyyMMddHHmmss") ;
			int otherDayMoney = maxConsump * otherDay ;
			
			int lastDateMinute = CalculateTime.lastDayMinute(endTime, "yyyyMMddHHmmss") ;
			int lastM = getMinute(lastDateMinute, timeUnit);
			Integer lastMoney = lastM * timeRate + mile * milesRate; //第一天花费金额
			if (lastMoney <= minConsump){
				lastMoney = minConsump;
			}else if(lastMoney >= maxConsump){
				lastMoney = maxConsump ;
			}
			map.put("money", firstMoney + otherDayMoney + lastMoney ); //总花费
			
		}else{
			
			Integer d = m * timeRate + mile * milesRate;
			//查询是否有日期跨度
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
//			if(sdf.format(st).equals(sdf.format(et))){//没有时间跨度 判断是否大于最高消费
				if (d <= minConsump){
					d = minConsump;
				}else if(d >= maxConsump){
					d = maxConsump ;
				}
//			}
			map.put("money", d); //总花费
		}
		return map;
	}
	
	public static String getDateStr(Date date,Integer day,String format){
	     Calendar calendar =  new GregorianCalendar(); 
	     calendar.setTime(date); 
	     calendar.add(Calendar.DATE,day);//把日期往后增加一天.整数往后推,负数往前移动 
	     date=calendar.getTime();
	     return new SimpleDateFormat(format).format(date);
	}
	
	public static Date getDate(Date date,Integer day){
	     Calendar calendar =  new GregorianCalendar(); 
	     calendar.setTime(date); 
	     calendar.add(Calendar.DATE,day);//把日期往后增加一天.整数往后推,负数往前移动 
	     date=calendar.getTime();
	     return date;
	}
	
	public static Date getDatePlus(Date date , int minutes){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MINUTE, minutes) ;
		return cal.getTime() ;
	}
}
