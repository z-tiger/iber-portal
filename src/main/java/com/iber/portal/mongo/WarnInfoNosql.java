package com.iber.portal.mongo;

import com.iber.portal.mongo.search.MongoWarningLogSearch;

/**
 * @auther lf
 * @date 2017/9/5
 * @description 平台操作日志
 */
public interface WarnInfoNosql extends BaseLogNosql {

    long getCount(MongoWarningLogSearch search);

    /**
     * 更新指定时间范围的阅读状态
     * @param start 开始
     * @param end 结束
     */
    void updateReadByTimeRange(long start, long end);
}
