package com.iber.portal.service.warns;

import com.iber.portal.common.Pager;
import com.iber.portal.dao.warns.WarnInfoMapper;
import com.iber.portal.exception.ServiceException;
import com.iber.portal.model.warns.WarnInfo;
import com.iber.portal.model.warns.WarnItem;
import com.iber.portal.mongo.WarnInfoNosql;
import com.iber.portal.mongo.search.MongoWarningLogSearch;
import org.apache.commons.lang3.StringUtils;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class WarnInfoService {

	private final static Logger LOGGER = LoggerFactory.getLogger(WarnInfoService.class);

	private static final String MONGO_ID = "_id";

	@Autowired
	private WarnInfoMapper warnInfoMapper;

	@Autowired
	private WarnInfoNosql warnInfoNosql;

	public List<WarnInfo> selectByWarnInfoList(Map<String, Object> map)throws ServiceException{
	       try{
				return warnInfoMapper.selectByWarnInfoList(map);
			}catch(Exception e){
				e.printStackTrace();
				throw new ServiceException();
			}
		}

	public List<WarnInfo> selectByWarnAllList(Map<String, Object> map)throws ServiceException{
	       try{
				return warnInfoMapper.selectByWarnAllList(map);
			}catch(Exception e){
				e.printStackTrace();
				throw new ServiceException();
			}
		}



	public int selectByWarnInfoListRecords(Map<String, Object> map)throws ServiceException{
	       try{
				return warnInfoMapper.selectByWarnInfoListRecords(map);
			}catch(Exception e){
				e.printStackTrace();
				throw new ServiceException();
			}
		}

		public List<WarnInfo> selectByPrimaryKeyExcel(Map<String, String> map) {

			return warnInfoMapper.selectByPrimaryKeyExcel(map);
		}


		public int selectByWarnRemindTotal(Map<String, Object> record)throws ServiceException{
		       try{
		    	   return warnInfoMapper.selectByWarnRemindTotal(record);
				}catch(Exception e){
					e.printStackTrace();
					throw new ServiceException();
				}
			}


    /**
     * 查询预警信息
     * @param search 查询条件
     * @throws ServiceException
     */
	public Pager<Map<String,Object>> selectWarnInfoList(MongoWarningLogSearch search) throws ServiceException {
		try {
			// 查询
			List<Map<String, Object>> maps = selectWarnInfos(search);
			Pager<Map<String, Object>> pager = new Pager<Map<String, Object>>();
			// 判断
			if (CollectionUtils.isEmpty(maps)) return pager;
			for (Map<String, Object> map : maps) {
				map.put(MONGO_ID, Objects.toString(map.get(MONGO_ID)));
			}
			pager.setDatas(maps);
			// 总记录数
			pager.setTotalCount((int)warnInfoNosql.getCount(search));
			return pager;
		} catch (Exception e) {
			LOGGER.error("selectWarnInfoList error！");
			throw new ServiceException();
		}
	}

	/**
	 * 查询预警列表
	 *
	 * @param search
	 * @return
	 * @throws ServiceException
	 */
	public List<Map<String, Object>> selectWarnInfos(MongoWarningLogSearch search) throws ServiceException {
		try {
			// 查询
			return warnInfoNosql.searchLogs(search);
		} catch (Exception e) {
			LOGGER.error("selectWarnInfoList error！");
			throw new ServiceException();
		}
	}

	/**
	 * 查询未读预警记录数
	 *
	 * @param search 查询
	 * @return 记录数
	 * @throws ServiceException
	 */
	public int selectWarnRemindTotal(MongoWarningLogSearch search) throws ServiceException {
		try {
			return (int)warnInfoNosql.getCount(search);
		} catch (Exception e) {
			return 0;
		}
	}

	/**
	 * 更新预警信息已读状态
	 *
	 * @param isRead 修改是否已读
	 * @param id 预警信息id
	 */
	public void updateReadStatus(String id,int isRead) {
		if (StringUtils.isNotBlank(id)) {
			Document document = new Document("isRead",isRead);
			warnInfoNosql.updateById(id,document);
		}
	}

	/**
	 * 更新预警信息已读状态
	 *
	 * @param start 开始时间
	 * @param end 结束时间
	 */
	public void updateAllReadStatus(long start,long end) {
		warnInfoNosql.updateReadByTimeRange(start,end);
	}

	/**
	 * 根据key查询
	 *
	 * @param id k
	 * @return
	 */
	public Map<String,Object> selectByKey(String id) {
		return warnInfoNosql.findById(id);
	}

}
