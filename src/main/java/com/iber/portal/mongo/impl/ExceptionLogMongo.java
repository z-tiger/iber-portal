package com.iber.portal.mongo.impl;

import com.google.common.collect.Lists;
import com.iber.portal.mongo.ExceptionLogNosql;
import com.iber.portal.mongo.search.MongoExceptionLogSearch;
import com.iber.portal.mongo.search.MongoTime;
import com.iber.portal.mongo.util.MongoConst;
import com.iber.portal.mongo.util.MongoDBUtils;
import com.mongodb.client.model.Filters;
import org.apache.commons.lang3.StringUtils;
import org.bson.conversions.Bson;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.regex.Pattern;

/**
 * @auther lf
 * @date 2017/9/5
 * @description 平台操作日志
 */
@Repository
public class ExceptionLogMongo extends BaseLogMongo implements ExceptionLogNosql {

    private static final String EXCEPTION_LOG_COLLECTION = "exception_log";

    public ExceptionLogMongo() {
        super();
        super.init(EXCEPTION_LOG_COLLECTION);
    }

    @Override
    public long getCount(final MongoExceptionLogSearch search) {
        if (search == null) return 0;
        return MongoDBUtils.getCount(collection, Filters.and(getBsons(search)));
    }

    /**
     * 查询条件
     * @param timeSearch 搜索条件
     * @return
     */
    @Override
    public List<Bson> getBsons(final MongoTime timeSearch) {
        MongoExceptionLogSearch search = (MongoExceptionLogSearch) timeSearch;
        Integer memberId = search.getMemberId();
        String method = search.getMethod();
        long startTime = search.getStartTime();
        long endTime = search.getEndTime();
        List<Bson> list = Lists.newArrayList();
        list.add(Filters.lte("createtime", endTime == 0 ? System.currentTimeMillis() : endTime));
        list.add(Filters.gte("createtime", startTime));
        if (memberId != null) {
            list.add(Filters.eq("memberid", memberId));
        }
        if (StringUtils.isNotBlank(method)) {
            list.add(Filters.eq("method", method));
        }
        return list;
    }
}
