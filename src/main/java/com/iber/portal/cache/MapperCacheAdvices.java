/*
 * Copyright 2015 guobang zaixian science technology CO., LTD. All rights reserved.
 * distributed with this file and available online at
 * http://www.gob123.com/
 */
package com.iber.portal.cache;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang3.time.FastDateFormat;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 内存缓存 Advices
 * @author ouxx
 * @since 2016-10-10 下午7:00:25
 *
 */
//@Component
//@Aspect
public class MapperCacheAdvices {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	private static final int CACHE_TIME_LIMIT = 6 * 1;//缓存的存活时间，单位为秒
	private static final String FULL_DATE_FORMAT_PATTERN = "yyyy-MM-dd HH:mm:ss";
	private static final FastDateFormat FULL_DATE_FORMAT = FastDateFormat.getInstance(FULL_DATE_FORMAT_PATTERN);
	
	//<key, ResultVo > ResultVo.calDeadline为此缓存的存活期限
	private Map<String, ResultVo> cacheMap = new HashMap<String, ResultVo>();

	/**
	 * 结果VO
	 * @author ouxx
	 * @since 2016-10-11 下午6:14:05
	 *
	 */
	private class ResultVo{
		//结果
		private Object result;
		//缓存期限
		private Calendar deadline;
		
		public Object getResult() {
			return result;
		}
		public void setResult(Object result) {
			this.result = result;
		}
		public Calendar getDeadline() {
			return deadline;
		}
		public void setDeadline(Calendar calDeadline) {
			this.deadline = calDeadline;
		}
	}
	
	/**
	 * 匹配com.iber.portal.service包、子孙包下的类名以Service结尾的所有public的查询方法
	 * @param point
	 * @author ouxx
	 * @date 2016-10-10 下午9:04:00
	 */
//	@Before("execution(public * com.iber.portal.controller..*.*Controller.*(..))")
//	@Before("execution(public * com.iber.portal.service..*.*Service.select*(..))"
//			+ " || execution(public * com.iber.portal.service..*.*Service.get*(..))"
//			+ " || execution(public * com.iber.portal.service..*.*Service.find*(..))")
//    public void before(JoinPoint point){
//		log.info("@Before：模拟权限检查...");
//        log.info("@Before：目标方法为：" + 
//                point.getSignature().getDeclaringTypeName() + 
//                "." + point.getSignature().getName());
//        log.info("@Before：参数为：" + Arrays.toString(point.getArgs()));
//        log.info("@Before：被织入的目标对象为：" + point.getTarget());
//    }
//    
	/**
	 * 后置增强，用于更新数据库数据(insert、delete、update等操作)后，能清除缓存
	 * @param point
	 * @param result
	 * @author ouxx
	 * @throws IOException 
	 * @date 2016-10-11 上午10:08:44
	 */
    @AfterReturning(pointcut = "execution(public * com.iber.portal.service..*.*Service.insert*(..))"
			+ " || execution(public * com.iber.portal.service..*.*Service.delete*(..))"
			+ " || execution(public * com.iber.portal.service..*.*Service.update*(..))", returning = "result")
    public void cleanCacheAfterUpdate(JoinPoint point, Object result) throws IOException{
        log.info("@AfterReturning：目标方法为：" + 
                point.getSignature().getDeclaringTypeName() + 
                "." + point.getSignature().getName());
        log.info("@AfterReturning：参数为：" + 
                Arrays.toString(point.getArgs()));
        log.info("@AfterReturning：返回值为：" + result);
        log.info("@AfterReturning：被织入的目标对象为：" + point.getTarget());
        
        this.cleanMatchServiceCache(point);
    }
    
    
    private void cleanMatchServiceCache(JoinPoint point) throws IOException{
    	String key = this.getCacheKey(point, false);
    	Iterator<Map.Entry<String, ResultVo>> it = this.cacheMap.entrySet().iterator();
    	while(it.hasNext()){
    		Map.Entry<String, ResultVo> entry = it.next();
    		//如果匹配当前所操作的Service，则清除此缓存
    		if(entry.getKey().startsWith(key)){
    			it.remove();
    		}
    	}
    }
    
	/**
	 * 环绕增强，用于获取和更新缓存
	 * @param point
	 * @return
	 * @throws Throwable
	 * @author ouxx
	 * @date 2016-10-11 下午5:28:38
	 */
    @Around("execution(public * com.iber.portal.service..*.*Service.select*(..))"
			+ " || execution(public * com.iber.portal.service..*.*Service.get*(..))"
			+ " || execution(public * com.iber.portal.service..*.*Service.find*(..))"
			+ " || execution(public * com.iber.portal.service..*.*Service.query*(..))")
    public Object aroundProcess(ProceedingJoinPoint point) throws Throwable {
        log.info("@Around：被织入的目标对象为：" + point.getTarget());
        log.info("@Around：参数为：" + Arrays.toString(point.getArgs()));
        
        return this.getOrUpdateCacheValue(point);//从缓存或调用目标方法，来获取结果
    }
    
    /**
     * 生成key : 包名+类名+方法名+参数(包含值，有多个)
     * @param point
     * @param isNeedArgs 是否需要拼接参数。后置增强只需 "包名+类名"
     * @return
     * @throws IOException
     * @author ouxx
     * @date 2016-10-11 下午5:43:41
     */
    private String getCacheKey(JoinPoint point, boolean isNeedArgs) throws IOException{
    	StringBuffer key = new StringBuffer();
    	//包名+类名
    	String packageName = point.getSignature().getDeclaringTypeName();
    	
    	if(isNeedArgs){
    		//方法名
    		String methodName = point.getSignature().getName();
    		packageName = packageName + "." + methodName;
    		key.append(packageName);
	    	//参数
	    	Object[] args = point.getArgs();
	    	ObjectMapper objMapper = new ObjectMapper();
//	    	objMapper.setSerializationInclusion(Inclusion.NON_NULL);
	    	
	    	for(Object arg : args){
	    		//写流
	    		StringWriter writer = new StringWriter();
	    		//对象转JSON：写过程
	    		try{
	    			objMapper.writeValue(writer, arg);
	    		} catch(Exception e){
	    			log.error("error of MapperCacheAdvices#getCacheKey", e);
	    		} finally{
	    			writer.close();
	    		}
	    		key.append(".").append(writer);
	    	}
    	}else{
    		key.append(packageName);
    	}
    	return key.toString();
    }
    
    
    /**
     * 获取key对应的value，要比对时间，如果获取的时候在缓存创建的时间的deadline以后，则清楚此条缓存，重新查数据库、更新缓存
     * @param key
     * @return
     * @author ouxx
     * @throws Throwable 
     * @date 2016-10-11 下午1:05:26
     */
    private Object getOrUpdateCacheValue(ProceedingJoinPoint point) throws Throwable{
    	String key = this.getCacheKey(point, true);
    	ResultVo resultMap = null;
    	if(this.cacheMap.containsKey(key)){
    		resultMap = this.cacheMap.get(key);
    	}
    	
    	boolean need2UpdateCache = false;//是否需要更新缓存
    	
    	Calendar now = Calendar.getInstance();
    	//如果缓存中已有此key的数据，则从缓存中取
    	if(null != resultMap){
    		Calendar calDeadline = resultMap.getDeadline();//此缓存的存活期限
    		//如果此缓存的期限是否已过，则更新
    		if(now.after(calDeadline)){
    			need2UpdateCache = true;
    		} 
    		
    	} else{//如果缓存中暂无此key的数据，则调用目标方法来取数据，并插入缓存
    		need2UpdateCache = true;
    	}
    	
    	if(need2UpdateCache){
    		//调用目标方法，然后获取方法的返回结果
			Object newResult = getResultFromTarget(point);
			//更新缓存
			updateCacheValue(key, newResult);
    	}
    	
    	return this.cacheMap.get(key).getResult();
    }

	/**
	 * 更新缓存
	 * @param key
	 * @param newResult
	 * @author ouxx
	 * @date 2016-10-11 下午2:34:11
	 */
	private void updateCacheValue(String key, Object newResult) {
		Calendar calDeadline = Calendar.getInstance();
		calDeadline.add(Calendar.SECOND, CACHE_TIME_LIMIT);

		ResultVo newResultVo = new ResultVo();
		newResultVo.setResult(newResult);
		newResultVo.setDeadline(calDeadline);
		this.cacheMap.put(key, newResultVo);
		
		log.info("@updateCacheValue 更新缓存，key = " + key + ", value = " + newResult + ", 期限为: " + FULL_DATE_FORMAT.format(calDeadline));
	}

	/**
	 * 调用目标方法，然后获取方法的返回结果
	 * @param point
	 * @return
	 * @throws Throwable
	 * @author ouxx
	 * @date 2016-10-11 下午2:33:23
	 */
	private Object getResultFromTarget(ProceedingJoinPoint point) throws Throwable {
		
		//目标方法的参数：
		Object[] args = point.getArgs();
		//调用目标方法
		Object newResult = point.proceed(args);
		
		log.info("@getResultFromTarget 从目标方法获取结果: " + newResult);
		return newResult;
	}
    
}
