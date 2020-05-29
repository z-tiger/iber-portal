//package com.iber.portal.cache;
//
//import java.io.IOException;
//import java.io.StringWriter;
//import java.util.Arrays;
//import java.util.Calendar;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.apache.commons.lang3.time.FastDateFormat;
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.AfterReturning;
//import org.aspectj.lang.annotation.Around;
//import org.codehaus.jackson.map.ObjectMapper;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import com.iber.portal.service.cache.RedisService;
//import com.iber.portal.util.FastJsonUtils;
//
///**
// * 匹配com.iber.portal.service包、子孙包下的类名以Service结尾的所有public的查询方法
// * redis缓存 Advices
// * @author ouxx
// * @since 2016-10-10 下午7:00:25
// *
// */
////@Component
////@Aspect
//public class RedisCacheAdvices {
//
//	private final Logger log = LoggerFactory.getLogger(this.getClass());
//	
//	/**
//	 * 最外层key，例如可分为管理平台的"PLT_KEY"，接口的"INTERFACE_KEY"
//	 */
//	private static final String PLT_KEY = "PLT_KEY";
//	
//	private static final int CACHE_TIME_LIMIT = 60 * 5;//缓存的存活时间，单位为秒
//	private static final String FULL_DATE_FORMAT_PATTERN = "yyyy-MM-dd HH:mm:ss";
//	private static final FastDateFormat FULL_DATE_FORMAT = FastDateFormat.getInstance(FULL_DATE_FORMAT_PATTERN);
//
//	/**
//	 * redis缓存服务
//	 */
//	@Autowired
//	public RedisService redisService;
//	
//	/**
//	 * 后置增强，拦截增删改的service，用于更新数据库数据(insert、delete、update等操作)后，能清除缓存
//	 * @param point
//	 * @param result
//	 * @author ouxx
//	 * @throws IOException 
//	 * @date 2016-10-11 上午10:08:44
//	 */
//    @AfterReturning(pointcut = "execution(public * com.iber.portal.service..*.*Service.insert*(..))"
//			+ " || execution(public * com.iber.portal.service..*.*Service.delete*(..))"
//			+ " || execution(public * com.iber.portal.service..*.*Service.update*(..))", returning = "result")
//    public void cleanCacheAfterUpdate(JoinPoint point, Object result) throws IOException{
//        log.info("@AfterReturning：目标方法为：" + 
//                point.getSignature().getDeclaringTypeName() + 
//                "." + point.getSignature().getName());
//        log.info("@AfterReturning：参数为：" + 
//                Arrays.toString(point.getArgs()));
//        log.info("@AfterReturning：返回值为：" + result);
//        log.info("@AfterReturning：被织入的目标对象为：" + point.getTarget());
//        
//        this.cleanMatchServiceCache(point, PLT_KEY);
//    }
//    
////    /**
////     * 清除匹配的缓存
////     * @param point
////     * @param typeKey 区分是哪种缓存，是管理平台的，还是接口的
////     * @throws IOException
////     * @author ouxx
////     * @date 2016-10-12 下午5:04:03
////     */
////    private void cleanMatchServiceCache(JoinPoint point, final String typeKey) throws IOException{
////    	String key = this.generateCacheKey(point, false);
////    	//获取指定类型typeKey的所有缓存，需要从json转
////    	List<String> cache = null;
////    	cache = this.redisService.getHashMap(typeKey, key);
////    	//如果不存在此类型的缓存，无需清除
////    	if(null == cache || cache.isEmpty()){
////    		return;
////    	}else{
////    		//获取值，要从JSON转  <key, JSON of ResultVo>
////    		Map<String, String> keyRstMap = FastJsonUtils.toMap(cache.get(0));
////    		//存放需要被删除的键为key的缓存
////	    	List<String> keyNeed2BeDeleted = new ArrayList<String>();
////	    	Iterator<Map.Entry<String, String>> it = keyRstMap.entrySet().iterator();
////	    	while(it.hasNext()){
////	    		Map.Entry<String, String> entry = it.next();
////	    		//如果匹配当前所操作的Service，则清除此缓存
////	    		if(entry.getKey().startsWith(key)){
////	    			keyNeed2BeDeleted.add(key);
////	    		}
////	    	}
////	    	
////	    	if(!keyNeed2BeDeleted.isEmpty()){
////	    		int size = keyNeed2BeDeleted.size();
////	    		this.redisService.deleteHashMap(typeKey, keyNeed2BeDeleted.toArray(new String[size]));
////	    	}
////    	}
////    }
//    
//    /**
//     * 清除匹配的缓存
//     * @param point
//     * @param typeKey 区分是哪种缓存，是管理平台的，还是接口的
//     * @throws IOException
//     * @author ouxx
//     * @date 2016-10-12 下午5:04:03
//     */
//    private void cleanMatchServiceCache(JoinPoint point, final String typeKey) throws IOException{
//    	String key = this.generateCacheKey(point, false);
//    	this.redisService.batchDelMatchFieldMap(typeKey, key);
//    }
//    
//	/**
//	 * 环绕增强，用于获取和更新缓存，拦截查询的service
//	 * @param point
//	 * @return
//	 * @throws Throwable
//	 * @author ouxx
//	 * @date 2016-10-11 下午5:28:38
//	 */
//    @Around("execution(public * com.iber.portal.service..*.*Service.select*(..))"
//			+ " || execution(public * com.iber.portal.service..*.*Service.get*(..))"
//			+ " || execution(public * com.iber.portal.service..*.*Service.find*(..))"
//			+ " || execution(public * com.iber.portal.service..*.*Service.query*(..))")
//    public Object aroundProcess(ProceedingJoinPoint point) throws Throwable {
//        log.info("@Around：被织入的目标对象为：" + point.getTarget());
//        log.info("@Around：参数为：" + Arrays.toString(point.getArgs()));
//        
//        return this.getOrUpdateCacheValue(point, PLT_KEY);//从缓存或调用目标方法，来获取结果
//    }
//    
//    /**
//     * 生成key : 包名+类名+方法名+参数(包含值，有多个)
//     * @param point
//     * @param isNeedArgs 是否需要拼接参数。后置增强只需 "包名+类名"
//     * @return
//     * @throws IOException
//     * @author ouxx
//     * @date 2016-10-11 下午5:43:41
//     */
//    private String generateCacheKey(JoinPoint point, boolean isNeedArgs) throws IOException{
//    	StringBuffer key = new StringBuffer();
//    	//包名+类名
//    	String packageName = point.getSignature().getDeclaringTypeName();
//    	
//    	if(isNeedArgs){
//    		//方法名
//    		String methodName = point.getSignature().getName();
//    		packageName = packageName + "." + methodName;
//    		key.append(packageName);
//	    	//参数
//	    	Object[] args = point.getArgs();
//	    	ObjectMapper objMapper = new ObjectMapper();
////	    	objMapper.setSerializationInclusion(Inclusion.NON_NULL);
//	    	
//	    	for(Object arg : args){
//	    		//写流
//	    		StringWriter writer = new StringWriter();
//	    		//对象转JSON：写过程
//	    		try{
//	    			objMapper.writeValue(writer, arg);
//	    		} catch(Exception e){
//	    			log.error("error of MapperCacheAdvices#getCacheKey", e);
//	    		} finally{
//	    			writer.close();
//	    		}
//	    		key.append(".").append(writer);
//	    	}
//    	}else{
//    		key.append(packageName);
//    	}
//    	return key.toString();
//    }
//    
//    
//    /**
//     * 获取typeKey对应下的key对应的value，要比对时间，如果获取的时候在缓存创建的时间的deadline以后，则清楚此条缓存，重新查数据库、更新缓存
//     * @param key
//     * @param typeKey 区分是哪种缓存，是管理平台的，还是接口的
//     * @return
//     * @author ouxx
//     * @throws Throwable 
//     * @date 2016-10-11 下午1:05:26
//     */
//    private Object getOrUpdateCacheValue(ProceedingJoinPoint point, final String typeKey) throws Throwable{
//    	//分辨是否需要调用目标方法并更新缓存
//    	boolean need2UpdateCache = false;
//    	ResultVo resultVo = null;
//    	//生成key
//    	String key = this.generateCacheKey(point, true);
//    	//获取指定类型(typeKey)下的key为所生成的key的缓存，需要从json转
//    	List<String> cache = null;
//    	cache = this.redisService.getHashMap(typeKey, key);
//    	
//    	//如果不存在此类型的缓存，需要更新
//    	if(null == cache || cache.isEmpty()){
//    		need2UpdateCache = true;
//    	}else{
//    		//获取值，要从JSON转  <key, JSON of <result, deadline>>   是 List<ResultVo>
//    		resultVo = FastJsonUtils.toBean(cache.get(0), ResultVo.class);
//        	//如果缓存中已有此数据，再判断是否过期了
//        	if(null != resultVo){
//        		Calendar calDeadline = resultVo.getDeadline();//此缓存的存活期限
//        		//如果此缓存的期限是否已过，则更新
//        		Calendar now = Calendar.getInstance();
//        		if(now.after(calDeadline)){
//        			need2UpdateCache = true;
//        		} 
//        	} else{//如果缓存中暂无此key的数据，则调用目标方法来取数据，并插入缓存
//        		need2UpdateCache = true;
//        	}
//    	}
//    	
//    	if(need2UpdateCache){
//    		//调用目标方法，然后获取方法的返回结果
//			Object newResult = getResultFromTarget(point);
//			//更新缓存
//			updateCacheValue(typeKey, key, newResult);
//			
//			return newResult;
//    	}
//    	
//    	return resultVo.getResult();
//    }
//
//	/**
//	 * 更新缓存
//	 * @param typeKey
//	 * @param key
//	 * @param newResult
//	 * @author ouxx
//	 * @date 2016-10-12 下午4:11:32
//	 */
//	private void updateCacheValue(String typeKey, String key, Object newResult) {
//		Calendar calDeadline = Calendar.getInstance();
//		calDeadline.add(Calendar.SECOND, CACHE_TIME_LIMIT);
//
//		ResultVo newResultVo = new ResultVo();
//		newResultVo.setResult(newResult);
//		newResultVo.setDeadline(calDeadline);
//		//value为FastJsonUtils.toJson(ResultVo)
//		Map<String, String> cache = new HashMap<String, String>();
//		cache.put(key, FastJsonUtils.toJson(newResultVo));
//		//存进redis
//		this.redisService.setHashMap(typeKey, cache);
//		
//		log.info("@updateCacheValue 更新缓存，key = " + key 
//				+ ", value = " + newResult 
//				+ ", 期限为: " + FULL_DATE_FORMAT.format(calDeadline));
//	}
//
//	/**
//	 * 调用目标方法，然后获取方法的返回结果
//	 * @param point
//	 * @return
//	 * @throws Throwable
//	 * @author ouxx
//	 * @date 2016-10-11 下午2:33:23
//	 */
//	private Object getResultFromTarget(ProceedingJoinPoint point) throws Throwable {
//		
//		//目标方法的参数：
//		Object[] args = point.getArgs();
//		//调用目标方法
//		Object newResult = point.proceed(args);
//		
//		log.info("@getResultFromTarget 从目标方法获取结果: " + newResult);
//		return newResult;
//	}
//	
//}
