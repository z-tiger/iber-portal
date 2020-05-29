package com.iber.portal.vo.systemMsg;

import com.iber.portal.model.base.SystemMsgLog;

/**
 * Created by liuxj on 2018/6/20.
 */
public class SystemMsgLogVo extends SystemMsgLog {
    private Integer isSpecifyUser;
    private Integer isManualSend;
    private String cityCode;
    private Integer createId;

    public Integer getIsSpecifyUser() {
        return isSpecifyUser;
    }

    public void setIsSpecifyUser(Integer isSpecifyUser) {
        this.isSpecifyUser = isSpecifyUser;
    }

    public Integer getIsManualSend() {
        return isManualSend;
    }

    public void setIsManualSend(Integer isManualSend) {
        this.isManualSend = isManualSend;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public Integer getCreateId() {
        return createId;
    }

    public void setCreateId(Integer createId) {
        this.createId = createId;
    }
}
