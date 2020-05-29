package com.iber.portal.mongo;

import com.iber.portal.mongo.search.MongoMemberLogExtendSearch;

/**
 * Created by 刘晓杰 on 2018/3/15.
 */
public interface MemberLogNosql extends BaseLogNosql {

    long getCount(MongoMemberLogExtendSearch search);
}
