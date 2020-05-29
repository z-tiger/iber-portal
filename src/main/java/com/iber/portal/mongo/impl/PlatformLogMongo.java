package com.iber.portal.mongo.impl;

import com.google.common.collect.Lists;
import com.iber.portal.mongo.PlatformLogNosql;
import com.iber.portal.mongo.search.MongoPlatformLogSearch;
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
public class PlatformLogMongo extends BaseLogMongo implements PlatformLogNosql {

    private static final String PLATFORM_LOG_COLLECTION = "platform_log";

    public PlatformLogMongo() {
        super();
        super.init(PLATFORM_LOG_COLLECTION);
    }

    @Override
    public long getCount(final MongoPlatformLogSearch search) {
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
        MongoPlatformLogSearch search = (MongoPlatformLogSearch) timeSearch;
        final Integer memberId = search.getMemberId();
        final String name = search.getName();
        final String method = search.getMethod();
        final String methodDescribe = search.getMethodDescribe();
        final String param = search.getParam();
        long startTime = search.getStartTime();
        long endTime = search.getEndTime();
        List<Bson> list = Lists.newArrayList();
        list.add(Filters.lte("createTime", endTime == 0 ? System.currentTimeMillis() : endTime));
        list.add(Filters.gte("createTime", startTime));
        if (memberId != null) {
            list.add(Filters.eq("userId", memberId));
        }
        if (StringUtils.isNotBlank(name)) {
            list.add(Filters.eq("name", name));
        }
        if (StringUtils.isNotBlank(method)) {
            list.add(Filters.eq("methodName", method));
        }
        if (StringUtils.isNotBlank(methodDescribe)) {
            final Pattern compile = Pattern.compile(MongoConst.LIKE_START + methodDescribe + MongoConst.LIKE_END);
            list.add(Filters.regex("methodDescribe", compile));
        }
        if (StringUtils.isNotBlank(param)) {
            final Pattern compile = Pattern.compile(MongoConst.LIKE_START + param + MongoConst.LIKE_END);
            list.add(Filters.regex("inParam", compile));
        }
        return list;
    }
}
