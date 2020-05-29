package com.iber.portal.vo.timeShare;

import java.io.Serializable;
import java.util.Date;

/**
 * 超长订单vo
 * 2017-5-15 11:25:42
 * lf
 */
public class LongTimeShareOrderVo implements Serializable{

    private static final long serialVersionUID = 5397466622469358618L;
    private Integer id; // 订单表的id

    private String orderId; // 订单id

    private Integer memberId; // 会员id

    private Date beginTime; // 订单开始时间

    private Date lockCarTime; // 锁车时间

    private Integer isLockCar; // 是否锁车 0 没有 1 锁车
    
    private Integer isLockTwoWarn; // 是否发生了锁车二次提醒  0 没有 1 锁车

    private Date oneWarnTime; // 一级预警时间

    private Integer isOneWarn; // 是否发送一级预警 0 没有 1 发送

    private Date twoWarnTime; // 二级预警时间

    private Integer isTwoWarn; // 是否发送二级预警 0 没有 1 发送

    private Integer budgetAmount; // 预设金额

    private Integer money; // 会员余额

    private String lpn;// 车牌
    
    private String status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getLockCarTime() {
        return lockCarTime;
    }

    public void setLockCarTime(Date lockCarTime) {
        this.lockCarTime = lockCarTime;
    }

    public Integer getIsLockCar() {
        return isLockCar;
    }

    public void setIsLockCar(Integer isLockCar) {
        this.isLockCar = isLockCar;
    }

    public Date getOneWarnTime() {
        return oneWarnTime;
    }

    public void setOneWarnTime(Date oneWarnTime) {
        this.oneWarnTime = oneWarnTime;
    }

    public Integer getIsOneWarn() {
        return isOneWarn;
    }

    public void setIsOneWarn(Integer isOneWarn) {
        this.isOneWarn = isOneWarn;
    }

    public Date getTwoWarnTime() {
        return twoWarnTime;
    }

    public void setTwoWarnTime(Date twoWarnTime) {
        this.twoWarnTime = twoWarnTime;
    }

    public Integer getIsTwoWarn() {
        return isTwoWarn;
    }

    public void setIsTwoWarn(Integer isTwoWarn) {
        this.isTwoWarn = isTwoWarn;
    }

    public Integer getBudgetAmount() {
        return budgetAmount;
    }

    public void setBudgetAmount(Integer budgetAmount) {
        this.budgetAmount = budgetAmount;
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public String getLpn() {
        return lpn;
    }

    public void setLpn(String lpn) {
        this.lpn = lpn;
    }

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getIsLockTwoWarn() {
		return isLockTwoWarn;
	}

	public void setIsLockTwoWarn(Integer isLockTwoWarn) {
		this.isLockTwoWarn = isLockTwoWarn;
	}
}