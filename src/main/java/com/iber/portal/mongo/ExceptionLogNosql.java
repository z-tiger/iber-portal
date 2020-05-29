package com.iber.portal.mongo;

import com.iber.portal.mongo.search.MongoExceptionLogSearch;

/**
 * @auther lf
 * @date 2017/10/17
 * @description 异常记录查看
 */
public interface ExceptionLogNosql extends BaseLogNosql {

    long getCount(MongoExceptionLogSearch search);
}
