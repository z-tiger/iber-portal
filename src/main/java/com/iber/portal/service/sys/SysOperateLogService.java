package com.iber.portal.service.sys;

import com.iber.portal.common.Pager;
import com.iber.portal.dao.sys.SysOperateLogMapper;
import com.iber.portal.model.sys.SysOperateLog;
import com.iber.portal.mongo.MemberLogNosql;
import com.iber.portal.mongo.PlatformLogNosql;
import com.iber.portal.mongo.search.MongoMemberLogExtendSearch;
import com.iber.portal.mongo.search.MongoPlatformLogSearch;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Service("sysOperateLogService")
public class SysOperateLogService{

	private final static Logger log= Logger.getLogger(SysOperateLogService.class);
	
	@Autowired
    private SysOperateLogMapper  dao;

	@Autowired
	private PlatformLogNosql platformLogNosql;

	@Autowired
	private MemberLogNosql memberLogNosql;

	private static final ExecutorService POOL = Executors.newFixedThreadPool(8);

		
	public int insert(SysOperateLog record){
		return dao.insert(record) ;
	}
	
	public int deleteByPrimaryKey(Integer id){
		return dao.deleteByPrimaryKey(id) ;
	}
	
	public int updateByPrimaryKey(SysOperateLog record){
		return dao.updateByPrimaryKey(record) ;
	}
	
	public int updateByPrimaryKeySelective(SysOperateLog record){
		return dao.updateByPrimaryKeySelective(record) ;
	}
	
	public SysOperateLog selectByPrimaryKey(Integer id){
		return dao.selectByPrimaryKey(id) ;
	}
	
	public int getAllNum(Map<String, Object> paramMap){
		return dao.getAllNum(paramMap) ;
	}
	
	public Pager<SysOperateLog> queryPageList(Map<String, Object> paramMap){
		List<SysOperateLog> listObj = dao.queryPageList(paramMap);
		Pager<SysOperateLog> pager = new Pager<SysOperateLog>();
		pager.setDatas(listObj);
		pager.setTotalCount(getAllNum(paramMap));
		return pager;
	}

	/**
	 * 查询平台操作日志列表
	 *
	 * @param search
	 * @return
	 */
	public Pager<Map<String, Object>> queryPlatformPageList(MongoPlatformLogSearch search) {
		// 查询
		List<Map<String, Object>> maps = platformLogNosql.searchLogs(search);
		Pager<Map<String, Object>> pager = new Pager<Map<String, Object>>();
		// 判断
		if (CollectionUtils.isEmpty(maps)) return pager;
		pager.setDatas(maps);
		// 总记录数
		pager.setTotalCount((int)platformLogNosql.getCount(search));
		return pager;
	}

	/**
	 * 保存平台操作日志
	 * @param map
	 */
	public void savePlatformLog(final Map<String, Object> map) {
		POOL.execute(new Runnable() {
			@Override
			public void run() {
				try {
					platformLogNosql.insert(map);
					map.clear();
				} catch (Exception e) {
					log.error("保存操作日志失败！");
				}
			}
		});
	}


	public Pager<Map<String,Object>> queryMemberLogPager(MongoMemberLogExtendSearch search) {
		Pager<Map<String,Object>> pager = new Pager<Map<String,Object>>();
		List<Map<String, Object>> maps = memberLogNosql.searchLogs(search);
		if (CollectionUtils.isEmpty(maps)) return pager;
		pager.setDatas(maps);
		// 总记录数
		pager.setTotalCount((int)memberLogNosql.getCount(search));
		return pager;
	}
}
