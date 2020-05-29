package com.iber.portal.mongo.impl;

import com.google.common.collect.Lists;
import com.iber.portal.mongo.WarnInfoNosql;
import com.iber.portal.mongo.search.MongoTime;
import com.iber.portal.mongo.search.MongoWarningLogSearch;
import com.iber.portal.mongo.util.MongoConst;
import com.iber.portal.mongo.util.MongoDBUtils;
import com.mongodb.client.model.Filters;
import org.apache.commons.lang3.StringUtils;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @auther lf
 * @date 2017/9/5
 * @description 平台操作日志
 */
@Repository
public class WarnInfoMongo extends BaseLogMongo implements WarnInfoNosql {

    private static final String WARNING_LOG_COLLECTION = "warning_log";

    public WarnInfoMongo() {
        super();
        super.init(WARNING_LOG_COLLECTION);
    }

    @Override
    public long getCount(final MongoWarningLogSearch search) {
        if (search == null) return 0;
        return MongoDBUtils.getCount(collection, Filters.and(getBsons(search)));
    }

    /**
     * 更新指定时间范围的阅读状态
     *
     * @param start 开始
     * @param end   结束
     */
    @Override
    public void updateReadByTimeRange(long start, long end) {
        MongoDBUtils.update(collection,Filters.and(Filters.gte(MongoConst.CREATE_TIME,start),
                Filters.lte(MongoConst.CREATE_TIME,end)),new Document("isRead",1));
    }

    /**
     * 查询条件
     * @param timeSearch 搜索条件
     * @return
     */
    @Override
    public List<Bson> getBsons(final MongoTime timeSearch) {
        MongoWarningLogSearch search = (MongoWarningLogSearch) timeSearch;
        String warnItemCode = search.getWarnItemCode();
        String lpn=search.getLpn();
        long startTime = search.getStartTime();
        long endTime = search.getEndTime();
        final int isRead = search.getIsRead();
        List<Bson> list = Lists.newArrayList();
        list.add(Filters.lte("createTime", endTime == 0 ? System.currentTimeMillis() : endTime));
        list.add(Filters.gte("createTime", startTime));
        if (StringUtils.isNotBlank(warnItemCode)) {
            list.add(Filters.eq("warnItemCode", warnItemCode));
        }
        if (StringUtils.isNotBlank(lpn)) {
            list.add(Filters.regex("lpn", ".*?" +lpn+ ".*"));
        }
        if (isRead >= 0) {
            list.add(Filters.eq("isRead", isRead));
        }
        return list;
    }
}
