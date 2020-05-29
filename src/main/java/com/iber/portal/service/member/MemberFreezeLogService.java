package com.iber.portal.service.member;

import com.iber.portal.dao.member.MemberFreezeLogMapper;
import com.iber.portal.vo.member.MemberFreezeLogVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by 刘晓杰 on 2017/12/27.
 */
@Service
public class MemberFreezeLogService {

    @Autowired
    private MemberFreezeLogMapper memberFreezeLogMapper;

    public List<MemberFreezeLogVo> selectAllFreezeLog(Map<String, Object> map) {
        return memberFreezeLogMapper.selectAllFreezeLog(map);
    }

    public int selecAllRecords(Map<String, Object> map) {
        return memberFreezeLogMapper.selecAllRecords(map);
    }
}
