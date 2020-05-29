package com.iber.portal.mongo.impl;

import com.google.common.collect.Lists;
import com.iber.portal.mongo.MemberLogNosql;
import com.iber.portal.mongo.search.MongoMemberLogExtendSearch;
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
 * Created by 刘晓杰 on 2018/3/15.
 */
@Repository
public class MemberLogMongo extends BaseLogMongo implements MemberLogNosql {

    private static final String MEMBER_LOG_COLLECTION = "member_up_log";

    public MemberLogMongo(){
        super();
        super.init(MEMBER_LOG_COLLECTION);
    }

    @Override
    public long getCount(MongoMemberLogExtendSearch search) {
        if (search == null) return 0;
        return MongoDBUtils.getCount(collection, Filters.and(getBsons(search)));
    }

    @Override
    public List<Bson> getBsons(MongoTime search) {
        MongoMemberLogExtendSearch extendSearch = (MongoMemberLogExtendSearch) search;
        final Integer memberId = extendSearch.getMemberId();
        final String name = extendSearch.getName();
        final String phone = extendSearch.getPhone();
        final String method = extendSearch.getMethod();
        final String scene = extendSearch.getScene();
        long startTime = search.getStartTime();
        long endTime = search.getEndTime();
        List<Bson> list = Lists.newArrayList();
        list.add(Filters.lte("createTime",endTime == 0 ? System.currentTimeMillis() : endTime));
        list.add(Filters.gte("createTime",startTime));
        if (memberId != null){
            list.add(Filters.eq("memberId",memberId));
        }
        if (StringUtils.isNotBlank(name)){
            list.add(Filters.eq("name",name));
        }
        if (StringUtils.isNotBlank(phone)){
            list.add(Filters.eq("phone",phone));
        }
        if (StringUtils.isNotBlank(method)) {
            list.add(Filters.eq("methodName", method));
        }
        if (StringUtils.isNotBlank(scene)) {
            list.add(Filters.eq("scene", scene));
        }
        return list;
    }
}
