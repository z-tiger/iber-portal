//package com.iber.portal.service.cache;
//
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import redis.clients.jedis.Jedis;
//import redis.clients.jedis.JedisPool;
//import redis.clients.jedis.JedisPoolConfig;
//import redis.clients.jedis.ShardedJedis;
//import redis.clients.jedis.ShardedJedisPipeline;
//import redis.clients.jedis.ShardedJedisPool;
//
//@Service
//public class RedisService {
//
//	private final Logger log = LoggerFactory.getLogger(this.getClass());
//	
//	/**
//	 * 分片式集群连接池
//	 */
//	@Autowired
//	private ShardedJedisPool shardedJedisPool;
//
//	private <T> T execute(RedisCallBackFunction<T, ShardedJedis> fun) {
//		ShardedJedis shardedJedis = null;
//		try {
//			// 从连接池中获取到jedis分片对象
//			shardedJedis = shardedJedisPool.getResource();
//			return fun.callback(shardedJedis);
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			if (null != shardedJedis) {
//				// 有效则放回到连接池中，无效则重置状态
//				shardedJedisPool.returnResource(shardedJedis);
//			}
//		}
//		return null;
//	}
//
//	/**
//	 * 执行set操作
//	 * 
//	 * @param key
//	 * @param value
//	 * @return
//	 */
//	public String set(final String key, final String value) {
//		return this.execute(new RedisCallBackFunction<String, ShardedJedis>() {
//			@Override
//			public String callback(ShardedJedis e) {
//				log.info("@RedisService#set 插入redis，key = " + key + " , value = " + value);
//				return e.set(key, value);
//			}
//		});
//	}
//
//	/**
//	 * 设置值并且设置生存时间
//	 * 
//	 * @param key
//	 * @param value
//	 * @param seconds
//	 * @return
//	 */
//	public String set(final String key, final String value, final int seconds) {
//		return this.execute(new RedisCallBackFunction<String, ShardedJedis>() {
//			@Override
//			public String callback(ShardedJedis e) {
//				String result = e.set(key, value);
//				e.expire(key, seconds);
//				log.info("@RedisService#set 插入redis, 存活时间 = " + seconds + " 秒, key = " + key + " , value = " + value);
//				return result;
//			}
//		});
//	}
//	
//	//*************************List 操作
//	/**
//	 * List操作，从左边插入
//	 * @param key
//	 * @param value
//	 * @return
//	 * @author ouxx
//	 * @date 2016-10-12 下午5:51:50
//	 */
//	public Long pushLeft(final String key, final String... values) {
//		return this.execute(new RedisCallBackFunction<Long, ShardedJedis>() {
//			@Override
//			public Long callback(ShardedJedis e) {
//				StringBuffer valBuff = new StringBuffer();
//				for(String s : values){
//		    		valBuff.append(s + " , ");
//		    	}
//				log.info("@RedisService#pushLeft 插入redis，key = " + key 
//						+ " , value = " + valBuff.toString());
//				return e.lpush(key, values);
//			}
//		});
//	}
//	
//	/**
//	 * 左边出列，获取并移除当前的key
//	 * @param key
//	 * @param values
//	 * @return
//	 * @author ouxx
//	 * @date 2016-10-12 下午6:05:26
//	 */
//	public String popLeft(final String key) {
//		return this.execute(new RedisCallBackFunction<String, ShardedJedis>() {
//			@Override
//			public String callback(ShardedJedis e) {
//				String value = e.lpop(key);//出列
//				log.info("@RedisService#popLeft  key = " + key + " , value = " + value);
//				return value;
//			}
//		});
//	}
//	
//	/**
//	 * List操作，从右边插入
//	 * @param key
//	 * @param value
//	 * @return
//	 * @author ouxx
//	 * @date 2016-10-12 下午5:51:50
//	 */
//	public Long pushRight(final String key, final String... values) {
//		return this.execute(new RedisCallBackFunction<Long, ShardedJedis>() {
//			@Override
//			public Long callback(ShardedJedis e) {
//				StringBuffer valBuff = new StringBuffer();
//				for(String s : values){
//					valBuff.append(s + " , ");
//				}
//				log.info("@RedisService#pushRight  key = " + key + " , value = " + valBuff.toString());
//				return e.lpush(key, values);
//			}
//		});
//	}
//	
//	/**
//	 * 右边出列，获取并移除当前的key
//	 * @param key
//	 * @param values
//	 * @return
//	 * @author ouxx
//	 * @date 2016-10-12 下午6:05:26
//	 */
//	public String popRight(final String key) {
//		return this.execute(new RedisCallBackFunction<String, ShardedJedis>() {
//			@Override
//			public String callback(ShardedJedis e) {
//				String value = e.rpop(key);//出列
//				log.info("@RedisService#popRight  key = " + key + " , value = " + value);
//				return value;
//			}
//		});
//	}
//	
//	/**
//	 * 遍历
//	 * @param key
//	 * @param start
//	 * @param end
//	 * @return
//	 * @author ouxx
//	 * @date 2016-10-12 下午6:28:26
//	 */
//	public List<String> rang(final String key, final long start, final long end){
//		return this.execute(new RedisCallBackFunction<List<String>, ShardedJedis>() {
//			@Override
//			public List<String> callback(ShardedJedis e) {
//				List<String> values = e.lrange(key, start, end);//出列
//				log.info("@RedisService#rang  key = " + key + " , value = " + values);
//				return values;
//			}
//		});
//	}
//	
//	/**
//	 * 打印map
//	 * @param dataMap
//	 * @return
//	 * @author ouxx
//	 * @date 2016-10-13 上午9:13:49
//	 */
//	private String map2String(final Map<String, String> dataMap){
//		StringBuffer strBuff = new StringBuffer("{");
//		for(Map.Entry<String, String> entry : dataMap.entrySet()){
//			strBuff.append("{key = " + entry.getKey() + " value = " + entry.getValue() + "}");
//		}
//		strBuff.append("}");
//		return strBuff.toString();
//	}
//	/**
//	 * 打印数组
//	 * @param dataMap
//	 * @return
//	 * @author ouxx
//	 * @date 2016-10-13 上午9:13:49
//	 */
//	private String array2String(final String... values){
//		StringBuffer valBuff = new StringBuffer();
//		for(String s : values){
//			valBuff.append(s + " , ");
//		}
//		return valBuff.toString();
//	}
//	
//	/**
//	 * 遍历全部
//	 * @param key
//	 * @return
//	 * @author ouxx
//	 * @date 2016-10-12 下午6:29:22
//	 */
//	public List<String> rangAll(final String key){
//		return rang(key, 0, -1);
//	}
//	
//	/**
//	 * set hashmap
//	 * @param key
//	 * @param dataMap
//	 * @return
//	 * @author ouxx
//	 * @date 2016-10-13 上午9:17:45
//	 */
//	public String setHashMap(final String key, final Map<String, String> dataMap){
//		return this.execute(new RedisCallBackFunction<String, ShardedJedis>() {
//			@Override
//			public String callback(ShardedJedis e) {
//				log.info("@RedisService#setHashMap  key = " + key + " , value = " + map2String(dataMap));
//				return e.hmset(key, dataMap);
//			}
//		});
//	}
//	
//	/**
//	 * get hashmap
//	 * @param key
//	 * @param fields
//	 * @return
//	 * @author ouxx
//	 * @date 2016-10-13 上午11:09:50
//	 */
//	public List<String> getHashMap(final String key, final String... fields){
//		return this.execute(new RedisCallBackFunction<List<String>, ShardedJedis>() {
//			@Override
//			public List<String> callback(ShardedJedis e) {
//				log.info("@RedisService#getHashMap  key = " + key + " , fields = " + array2String(fields));
//				return e.hmget(key, fields);
//			}
//		});
//	}
//	
//	/**
//	 * 使用管道批量删除key下的 Map<field, value>
//	 * @param key
//	 * @param matchFields 被匹配的field名，一个field名的长度会 <= 数据库中key下需匹配的field
//	 * @return
//	 * @author ouxx
//	 * @date 2016-10-13 下午4:27:10
//	 */
//	public Boolean batchDelMatchFieldMap(final String key, final String... matchFields){
//		return this.execute(new RedisCallBackFunction<Boolean, ShardedJedis>() {
//			@Override
//			public Boolean callback(ShardedJedis e) {
//				log.info("@RedisService#batchDelMatchFieldMap  key = " + key + " , fields = " + array2String(matchFields));
//				try{
//					ShardedJedisPipeline pipeline = e.pipelined();
//					//获取key下的 所有Map<field, value>
//					Map<String, String> map = e.hgetAll(key);
//					for(String match : matchFields){
//						for(String fieldName : map.keySet()){
//							if(fieldName.startsWith(match)){
//								pipeline.hdel(key, fieldName);
//							}
//						}
//					}
//					pipeline.sync();//同步
//					return Boolean.TRUE;
//				}catch(Exception exp){
//					return Boolean.FALSE;
//				}
//			}
//		});
//	}
//	
//	/**
//	 * delete hashmap
//	 * @param key
//	 * @param fields
//	 * @return
//	 * @author ouxx
//	 * @date 2016-10-13 上午11:09:50
//	 */
//	public Long deleteHashMap(final String key, final String... fields){
//		return this.execute(new RedisCallBackFunction<Long, ShardedJedis>() {
//			@Override
//			public Long callback(ShardedJedis e) {
//				log.info("@RedisService#deleteHashMap  key = " + key + " , fields = " + array2String(fields));
//				return e.hdel(key, fields);
//			}
//		});
//	}
//	/**
//	 * 用管道批量删除
//	 * @param key
//	 * @param fields
//	 * @return
//	 * @author ouxx
//	 * @date 2016-10-13 下午4:09:35
//	 */
//	public Boolean delBatchHashMapWithPipeline(final String key, final String... fields){
//		return this.execute(new RedisCallBackFunction<Boolean, ShardedJedis>() {
//			@Override
//			public Boolean callback(ShardedJedis e) {
//				ShardedJedisPipeline pipeline = e.pipelined();
//				try{
//					for(String field : fields){
//						pipeline.hdel(key, field);
//					}
//					pipeline.sync();//同步
//					
//					log.info("@RedisService#delBatchHashMapWithPipeline  key = " + key + " , fields = " + array2String(fields));
//					return Boolean.TRUE;
//				}catch(Exception exp){
//					return Boolean.FALSE;
//				}
//			}
//		});
//	}
//
//	
////	private void printShardInfo(final ShardedJedis e, final String key) {
////		JedisShardInfo shardInfo = e.getShardInfo(key);
////		log.info("@getShardInfo : host = " + shardInfo.getHost()
////				+ "超时时间 ： " + shardInfo.getTimeout());
////	}
//
//	/**
//	 * 指定get操作
//	 * 
//	 * @param key
//	 * @return
//	 */
//	public String get(final String key) {
//		return this.execute(new RedisCallBackFunction<String, ShardedJedis>() {
//			@Override
//			public String callback(ShardedJedis e) {
//				log.info("@RedisService#get  key = " + key + " , value = " + e.get(key));
//				return e.get(key);
//			}
//		});
//	}
//
//	/**
//	 * 删除数据
//	 * 
//	 * @param key
//	 * @return
//	 */
//	public Long del(final String key) {
//		return this.execute(new RedisCallBackFunction<Long, ShardedJedis>() {
//			@Override
//			public Long callback(ShardedJedis e) {e.lpop(key);
//				log.info("@RedisService#del 删除,  key = " + key + " , value = " + e.get(key));
//				return e.del(key);
//			}
//		});
//	}
//
//	/**
//	 * 设置生存时间
//	 * 
//	 * @param key
//	 * @return
//	 */
//	public Long expire(final String key, final int seconds) {
//		return this.execute(new RedisCallBackFunction<Long, ShardedJedis>() {
//			@Override
//			public Long callback(ShardedJedis e) {
//				return e.expire(key, seconds);
//			}
//		});
//	}
//	
//	public static void main(String[] args){
////		ApplicationContext ctx = new ClassPathXmlApplicationContext(
////				"spring/spring-redis.xml");
////		shardedJedisPool = (ShardedJedisPool) ctx.getBean("shardedJedisPool");
////		ShardedJedis jedis = shardedJedisPool.getResource();
//		
//		JedisPool pool = getJedisPool();
//		Jedis jedis = pool.getResource();
//		
////		Jedis jedis = new Jedis("redis://192.168.1.90:6379/db0", 6379);
//		try {  
//            Map<String, String> pairs = new HashMap<String, String>();  
//            pairs.put("name", "Akshi");  
//            pairs.put("age", "2");  
//            pairs.put("sex", "Female");  
//            jedis.hmset("kid", pairs);  
//            List<String> name = jedis.hmget("kid", "name");// 结果是个泛型的LIST  
//            // jedis.hdel("kid","age"); //删除map中的某个键值  
//            
//            System.out.println("name = " + name.get(0)); // 因为删除了，所以返回的是null  
//            System.out.println(jedis.hmget("kid", "pwd")); // 因为删除了，所以返回的是null  
//            System.out.println(jedis.hlen("kid")); // 返回key为user的键中存放的值的个数  
//            System.out.println(jedis.exists("kid"));// 是否存在key为user的记录  
//            System.out.println(jedis.hkeys("kid"));// 返回map对象中的所有key  
//            System.out.println(jedis.hvals("kid"));// 返回map对象中的所有value  
//  
//            Iterator<String> iter = jedis.hkeys("kid").iterator();  
//            while (iter.hasNext()) {  
//                String key = iter.next();  
//                System.out.println(key + ":" + jedis.hmget("kid", key));  
//            }  
//  
//            List<String> values = jedis.lrange("messages", 0, -1);  
//            values = jedis.hmget("kid", new String[] { "name", "age", "sex" });  
//            System.out.println(values);  
//            Set<String> setValues = jedis.zrange("hackers", 0, -1);  
//            setValues = jedis.hkeys("kid");  
//            System.out.println(setValues);  
//            values = jedis.hvals("kid");  
//            System.out.println(values);  
//            pairs = jedis.hgetAll("kid");  
//            System.out.println(pairs);  
//        } catch (Exception e) {  
//            e.printStackTrace();  
//        } finally {  
////        	pool.returnResource(jedis);  
////        	shardedJedisPool.returnResource(jedis);  
//        }  
//	}
//	
//	private static JedisPool  getJedisPool(){
//		JedisPoolConfig config = new JedisPoolConfig();
//		
////		config.setMaxActive(600);
////		 
////		//最大空闲连接数, 默认8个
////		config.setMaxIdle(8);
////		 
////		//逐出连接的最小空闲时间 默认1800000毫秒(30分钟)
////		config.setMinEvictableIdleTimeMillis(1800000);
////		 
////		//最小空闲连接数, 默认0
////		config.setMinIdle(0);
////		 
////		//每次逐出检查时 逐出的最大数目 如果为负数就是 : 1/abs(n), 默认3
////		config.setNumTestsPerEvictionRun(3);
////		 
////		//对象空闲多久后逐出, 当空闲时间>该值 且 空闲连接>最大空闲数 时直接逐出,不再根据MinEvictableIdleTimeMillis判断  (默认逐出策略)   
////		config.setSoftMinEvictableIdleTimeMillis(1800000);
////		 
////		//在获取连接的时候检查有效性, 默认false
////		config.setTestOnBorrow(true);
////		 
////		//在空闲时检查有效性, 默认false
////		config.setTestWhileIdle(false);
////		 
////		//逐出扫描的时间间隔(毫秒) 如果为负数,则不运行逐出线程, 默认-1
////		config.setTimeBetweenEvictionRunsMillis(-1);
////		 
////		JedisConnectionFactory factory = new JedisConnectionFactory(config);
////		factory.get
//		
//		return new JedisPool(config, "192.168.1.90", 6379);
//		 
//	}
//
//}
