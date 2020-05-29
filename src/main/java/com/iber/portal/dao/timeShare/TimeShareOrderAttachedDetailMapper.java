package com.iber.portal.dao.timeShare;

import com.iber.portal.model.timeShare.TimeShareOrderAttached;
import com.iber.portal.model.timeShare.TimeShareOrderAttachedDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TimeShareOrderAttachedDetailMapper {

    void saveOrderAttachedDetail(@Param("details") List<TimeShareOrderAttachedDetail> details, @Param("attached")TimeShareOrderAttached timeShareOrderAttached);
}
