package com.iber.portal.model.timeShare;

import java.util.Date;

public class TimeShareCancel {
    private Integer id;

    private Integer memberId;

    private Integer cancelNum;

    private Date createTime;
    
    /**查违章时，统计到最新一次无违章无救援的订单的结束时间*/
    private Date latestNoWzOrdEndTime;
    
    //会员主动取消预约充电次数
    private Integer chargingCancelNum;
    //系统自动取消预约充电次数
    private Integer sysChargingCancelNum;
    
    //连续无违章的用车次数(出现违章时置0)
    private Integer continNoWzNum;
    //连续无救援的用车次数(出现需要救援时置0)
    private Integer continNoRescueNum;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public Integer getCancelNum() {
        return cancelNum;
    }

    public void setCancelNum(Integer cancelNum) {
        this.cancelNum = cancelNum;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

	public Integer getChargingCancelNum() {
		return chargingCancelNum;
	}

	public void setChargingCancelNum(Integer chargingCancelNum) {
		this.chargingCancelNum = chargingCancelNum;
	}

	public Integer getSysChargingCancelNum() {
		return sysChargingCancelNum;
	}

	public void setSysChargingCancelNum(Integer sysChargingCancelNum) {
		this.sysChargingCancelNum = sysChargingCancelNum;
	}

	public Integer getContinNoWzNum() {
		return continNoWzNum;
	}

	public void setContinNoWzNum(Integer continNoWzNum) {
		this.continNoWzNum = continNoWzNum;
	}

	public Integer getContinNoRescueNum() {
		return continNoRescueNum;
	}

	public void setContinNoRescueNum(Integer continNoRescueNum) {
		this.continNoRescueNum = continNoRescueNum;
	}

	public Date getLatestNoWzOrdEndTime() {
		return latestNoWzOrdEndTime;
	}

	public void setLatestNoWzOrdEndTime(Date latestNoWzOrdEndTime) {
		this.latestNoWzOrdEndTime = latestNoWzOrdEndTime;
	}


}