package com.iber.portal.dao.dispatch;

import java.util.List;
import java.util.Map;


import com.iber.portal.model.dispatch.DispatcherMember;

public interface DispatcherMemberMapper {

    List<DispatcherMember> selectByDispatcherMemberType(Map<String, Object> map) ;

}