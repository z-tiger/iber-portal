package com.iber.portal.util;

import org.apache.commons.lang3.time.FastDateFormat;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
public class DateTime {

	private static final String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";
	private static final FastDateFormat DEFAULT_DATE_FORMAT = FastDateFormat.getInstance(DEFAULT_FORMAT);

	public static Timestamp getNowDateTime(){
	 Timestamp ts = new Timestamp(System.currentTimeMillis());		
	 return ts;
	}
	public static Timestamp getDateTime(String time){
		 Timestamp ts = Timestamp.valueOf(time);
		 return ts;
		}
	public static String getString(){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 		  
		Timestamp now = new Timestamp(System.currentTimeMillis());		  
		String str = df.format(now);      
		return str;
	}
	public static String getStringlong(){
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss"); 		  
		Timestamp now = new Timestamp(System.currentTimeMillis());		  
		String str = df.format(now);      
		return str;
	}
	public static String getTimeString(Timestamp now){
		if(now!=null){
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 		  
			String str = df.format(now);      
			return str;
		}else {
			return "";
		}
	}
	public static String getTimesString(Timestamp now){
		if(now!=null){
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss"); 		  
			String str = df.format(now);      
			return str;
		}else {
			return "";
		}
	}
	public static String getDateString(Date date){
		if(date!=null){
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd"); 		  
			String str = df.format(date);      
			return str;
		}else {
			return "";
		}
	}
	public static String getDateTimeString(Date date){
		if(date!=null){
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 		  
			String str = df.format(date);      
			return str;
		}else {
			return "";
		}
	}
	public static Timestamp getNowDate(){
		 SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");  
		 String time = df.format(new Date());   	  
		 Timestamp ts = Timestamp.valueOf(time);
		 return ts;
		}
	public static String getDateString(Timestamp now){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd"); 		  
		String str = df.format(now);      
		return str;
		}
	public static Date getDate(String str1){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date= new Date();
		try {
			date = sdf.parse(str1);
			return date;
		} catch (ParseException e) {
			return  date;
		}     
		
	}
	public static String getNowDateString(){
		Date nowDate = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(nowDate);
		return dateString;
	}
	public static String getNextMonthDateString(){
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //制定日期格式
		Calendar c=Calendar.getInstance();
		Date date=new Date();
		c.setTime(date);
		c.add(Calendar.MONTH,1); //将当前日期加一个月
		String validityDate=df.format(c.getTime()); //返回String型的时间
		return validityDate;
	}
	
	public static int compareDate(String date1, String date2) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
            Date dt1 = df.parse(date1);
            Date dt2 = df.parse(date2);
            if (dt1.getTime() > dt2.getTime()) {
                System.out.println("dt1 在dt2前");
                return 1;
            } else if (dt1.getTime() < dt2.getTime()) {
                System.out.println("dt1在dt2后");
                return -1;
            } else {
                return 0;
            }
		} catch (Exception exception) {
            exception.printStackTrace();
		}
		return 0;
    }
	
	public static Date StringToDate(String date) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			return df.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 取指定时间的周一
	 * 2017年5月26日11:58:03
	 * lf
	 */
	public static Date getfirstWeek (Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_WEEK,2);
		calendar.set(Calendar.HOUR_OF_DAY,0);
		calendar.set(Calendar.MINUTE,0);
		calendar.set(Calendar.SECOND,0);
		return calendar.getTime();
	}
    /**
    * @Title: getStartTimeOfDay
    * @Description: 获取某个时间的当天开始时间,即当天的零整点
    * @date 2017-10-11  上午9:40:43
    * @param date
    * @return    
    * @return Date    返回类型
    * @throws
     */
	public static Date getStartTimeOfDay(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY,0);
		calendar.set(Calendar.MINUTE,0);
		calendar.set(Calendar.SECOND,0);
		calendar.set(Calendar.MILLISECOND, 000);
		return calendar.getTime();
	}
    /**
    * @Title: getEndTimeOfDay
    * @Description: 获取某个时间的当天结束时间,即当天的23点59分59秒
    * @date 2017-10-11  上午9:40:43
    * @param date
    * @return    
    * @return Date    返回类型
    * @throws
     */
	public static Date getEndTimeOfDay(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY,23);
		calendar.set(Calendar.MINUTE,59);
		calendar.set(Calendar.SECOND,59);
		calendar.set(Calendar.MILLISECOND, 000);
		return calendar.getTime();
	}

	/**
	 * 获取当前时间月份的结束时间
	 * @param date
	 * @return
	 */
	public static Date getEndTimeOfMonth(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		Integer lastMonthMaxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), lastMonthMaxDay, 23, 59, 59);
		return calendar.getTime();
	}
	/**
	 * 获取当前时间月份的开始时间
	 * @param date
	 * @return
	 */
	public static Date getStartTimeOfMonth(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		Integer startMonthMinDay = calendar.getActualMinimum(Calendar.DAY_OF_MONTH);
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), startMonthMinDay, 00, 00, 00);
		return calendar.getTime();
	}

	/**
	 * 获取当前时间年份的结束时间
	 * @param date
	 * @return
	 */
	public static Date getEndTimeOfYear(Date date){

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		Integer year = calendar.get(Calendar.YEAR);
		calendar.clear();
		calendar.set(Calendar.YEAR, year);
		calendar.roll(Calendar.DAY_OF_YEAR, -1);
		Date currYearLast = calendar.getTime();
		calendar.setTime(currYearLast);
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 23, 59, 59);
		return calendar.getTime();
	}

	/**
	 * 获取当前时间年份的开始时间
	 * @param date
	 * @return
	 */
	public static Date getStartTimeOfYear(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(calendar.get(Calendar.YEAR), 0, 1, 00, 00, 00);
		return calendar.getTime();
	}

	/**
	 * 解析时间字符串
	 * @param dateTime 时间字符串
	 * @return lf
	 * 2017-5-4 14:32:38
	 */
	public static long getTime(String dateTime){
		try {
			return DEFAULT_DATE_FORMAT.parse(dateTime).getTime();
		} catch (ParseException e) {
			throw new RuntimeException("日期解析失败!",e);
		}
	}

	/**
	 * 計算兩個日期之間相差天數
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int daysBetween(Date date1,Date date2){
		try {
			Calendar cal=Calendar.getInstance();
			cal.setTime(date1);
			long time1=cal.getTimeInMillis();
			cal.setTime(date2);
			long time2=cal.getTimeInMillis();
			long between_days=(time1-time2)/(1000*3600*24);
			return  Integer.parseInt(String.valueOf(between_days));
		}catch (Exception e){

		}
		return  0;
	}

	/**
	 * 計算年齡
	 * @param birthdayString
	 * @param format
	 * @return
	 */
	public  static int getAge(String birthdayString, String format){
		try {
			SimpleDateFormat sd = new SimpleDateFormat(format);
			Date date2=sd.parse(birthdayString);
			int days=daysBetween(new Date(),date2);
			return  days/365;
		}catch (Exception e){

		}
			return  0;
	}

	public static void main(String[] args) {
		System.err.println(getTime("2017-10-19 11:11:15"));
	}
	
}
