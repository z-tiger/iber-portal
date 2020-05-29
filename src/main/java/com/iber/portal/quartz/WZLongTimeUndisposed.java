package com.iber.portal.quartz;

import com.iber.portal.common.InsertFreezeLogUtil;
import com.iber.portal.common.SysConstant;
import com.iber.portal.dao.base.SystemMsgLogMapper;
import com.iber.portal.dao.base.WZQueryMapper;
import com.iber.portal.dao.member.MemberFreezeLogMapper;
import com.iber.portal.dao.sys.SysParamMapper;
import com.iber.portal.getui.PushClient;
import com.iber.portal.getui.PushCommonBean;
import com.iber.portal.model.base.Member;
import com.iber.portal.model.base.SystemMsgLog;
import com.iber.portal.model.sys.SysParam;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.Date;
import java.util.List;

/**
 * 违章长时间未处理，冻结会员
 * @author zengfeiyue
 */
public class WZLongTimeUndisposed extends QuartzJobBean {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private WZQueryMapper wzQueryMapper;

    @Autowired
    private SysParamMapper sysParamMapper;

    @Autowired
    private SystemMsgLogMapper systemMsgLogMapper;

    @Autowired
    private MemberFreezeLogMapper memberFreezeLogMapper;
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("长时间未处理违章查询开始.....");
        SysParam sysParam = sysParamMapper.selectByKey(SysConstant.WZ_LONG_TIME_UNDISPOSED);
        SysParam sysParam2 = sysParamMapper.selectByKey(SysConstant.WZ_LONG_TIME_UNDISPOSED_NUMBER);
        wzQueryMapper.lockLongTimeUndisposedMember(sysParam.getValue(),sysParam2.getValue());
        List<Member> members = wzQueryMapper.queryLongTimeMember(sysParam.getValue(),sysParam2.getValue());
        for (Member member:members){
            /**保存到系统**/
            SystemMsgLog systemMsgLog = new SystemMsgLog();
            systemMsgLog.setMsgType("member");
            systemMsgLog.setCreateTime(new Date());
            systemMsgLog.setMemberId(member.getId());
            systemMsgLog.setMsgTitle("资金冻结");
            String content = "【宜步出行】尊敬的会员，您有长时间未处理的违章，系统已自动冻结您的资金，如有疑问，请联系客服4000769755";
            systemMsgLog.setMsgContent(content);
            systemMsgLogMapper.insertSelective(systemMsgLog);
            //保存会员账户冻结日志
            String reason = "会员存在违章超时未处理数据";
            new InsertFreezeLogUtil(memberFreezeLogMapper).insertFreezeLog(null,String.valueOf(member.getId()),reason,0);
            /**个推消息**/
            PushCommonBean push = new PushCommonBean("push_server_system_msg_log", "1", "您有违章记录未处理，资金已被冻结",systemMsgLog) ;
            List<String> cidList = PushClient.queryClientId(member.getPhone());
            for (String memberCid : cidList) {
                PushClient.push(memberCid,push);
            }
        }
    }
}
