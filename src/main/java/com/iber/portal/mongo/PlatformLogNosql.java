package com.iber.portal.mongo;

import com.iber.portal.mongo.search.MongoPlatformLogSearch;

/**
 * @auther lf
 * @date 2017/9/5
 * @description 平台操作日志
 */
public interface PlatformLogNosql extends BaseLogNosql {

    long getCount(MongoPlatformLogSearch search);
}
