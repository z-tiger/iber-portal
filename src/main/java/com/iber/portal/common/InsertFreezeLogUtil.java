package com.iber.portal.common;

import com.iber.portal.dao.member.MemberFreezeLogMapper;
import com.iber.portal.model.member.MemberFreezeLog;
import com.iber.portal.model.sys.SysUser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * Created by 刘晓杰 on 2017/12/26.
 */
public class InsertFreezeLogUtil {

    private MemberFreezeLogMapper memberFreezeLogMapper;

    public InsertFreezeLogUtil(MemberFreezeLogMapper memberFreezeLogMapper) {
        this.memberFreezeLogMapper = memberFreezeLogMapper;
    }

    public void insertFreezeLog(HttpServletRequest request, String memberId, String reason, Integer status) {
        MemberFreezeLog memberFreezeLog = new MemberFreezeLog();
        memberFreezeLog.setCreateTime(new Date());
        memberFreezeLog.setMemberId(Integer.valueOf(memberId));
        if (null != request){
            memberFreezeLog.setCreateId(getUser(request).getId());
        }else{
            memberFreezeLog.setCreateId(1);
        }

        memberFreezeLog.setReason(reason);
        memberFreezeLog.setStatus(status);
        memberFreezeLogMapper.insertSelective(memberFreezeLog);
    }

    public SysUser getUser(HttpServletRequest request){
        HttpSession sess = request.getSession();
        return (SysUser) sess.getAttribute("user");
    }
}
