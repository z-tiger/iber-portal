package com.iber.portal.mongo;


import com.iber.portal.mongo.search.MongoTime;
import org.bson.conversions.Bson;

import java.util.List;
import java.util.Map;

/**
 * @auther lf
 * @date 2017/9/5
 * @description nosql 基础日志操作接口
 */
public interface BaseLogNosql extends BaseNosql {

    List<Map<String, Object>> searchLogs(final MongoTime search);

    List<Bson> getBsons(final MongoTime search);
}
