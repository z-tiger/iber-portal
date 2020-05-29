package com.iber.portal.mongo;

import org.bson.Document;

import java.util.List;

/**
 * @auther lf
 * @date 2017/10/17
 * @description 异常记录查看
 */
public interface HeatMapNosql extends BaseNosql {

    List<Document> searchByDate(final long date);
}
