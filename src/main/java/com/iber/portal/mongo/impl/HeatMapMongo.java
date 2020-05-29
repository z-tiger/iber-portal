package com.iber.portal.mongo.impl;

import com.google.common.collect.Lists;
import com.iber.portal.mongo.HeatMapNosql;
import com.mongodb.BasicDBObject;
import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import org.bson.BsonDocument;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @auther lf
 * @date 2017/9/5
 * @description 平台操作日志
 */
@Repository
public class HeatMapMongo extends BaseMongo implements HeatMapNosql {

    private static final String HEAT_MAP_COLLECTION = "heatMap";
    private static final BasicDBObject FIELDS = new BasicDBObject("lng",1);

    public HeatMapMongo() {
        super();
        super.init(HEAT_MAP_COLLECTION);
        FIELDS.put("lat",1);
        FIELDS.put("count",1);
        FIELDS.put("_id",0);
    }

    @Override
    public List<Document> searchByDate(long date) {
        FindIterable<Document> documents = collection
                .find(new BasicDBObject("statistic_time", date))
                .projection(FIELDS);
        if (documents == null) return Collections.EMPTY_LIST;
        final List<Document> list = Lists.newArrayList();
        documents.forEach(new Block<Document>() {
            @Override
            public void apply(Document document) {
                list.add(document);
            }
        });
        return list;
    }
}
