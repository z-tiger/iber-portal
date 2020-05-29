package com.iber.portal.mongo.impl;

import com.google.common.collect.Lists;
import com.iber.portal.mongo.BaseLogNosql;
import com.iber.portal.mongo.search.MongoTime;
import com.iber.portal.mongo.util.MongoDBUtils;
import com.iber.portal.mongo.util.SearchOption;
import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @auther lf
 * @date 2017/9/5
 * @description mongodb log 基础操作
 */
@Repository
public class BaseLogMongo extends BaseMongo implements BaseLogNosql {

    @Override
    public List<Map<String, Object>> searchLogs(final MongoTime search) {
        if (search == null) return null;
        SearchOption option = getOption(search);
        FindIterable<Document> documents = MongoDBUtils.find(collection, Filters.and(getBsons(search)), option);
        final List<Map<String, Object>> result = Lists.newArrayListWithCapacity(search.getRows());
        if (documents != null) {
            documents.forEach(new Block<Document>() {
                @Override
                public void apply(final Document document) {
                    result.add(document);
                }
            });
        }
        return result;
    }

    @Override
    public List<Bson> getBsons(final MongoTime search) {
        return new ArrayList<Bson>();
    }

}
