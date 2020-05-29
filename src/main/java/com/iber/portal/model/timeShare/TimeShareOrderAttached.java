package com.iber.portal.model.timeShare;

import com.iber.portal.enums.OrderAttachedTypeEnum;
import com.iber.portal.enums.PayStatusEnum;
import com.iber.portal.enums.PayTypeEnum;

import java.util.Date;
import java.util.List;

/**
 * 附属订单
 */
public class TimeShareOrderAttached {

    private Integer id;

    /*订单id*/
    private String orderId;

    /*创建订单原因*/
    private String createReason;

    /*附属订单枚举*/
    private OrderAttachedTypeEnum type;

    /*创建日期*/
    private Date createTime;

    /*创建人*/
    private String creater;

    /*支付状态*/
    private PayStatusEnum payStatus;

    /*支付类型*/
    private PayTypeEnum payType;

    /*完成支付日期*/
    private Date paymentCompletion;

    /*是否用户责任*/
    private Integer toUser;

    private List<TimeShareOrderAttachedDetail> item;

    /*维修单据链接*/
    private String repairPictureUrl;

    /*车辆损毁照片链接*/
    private String carDamagePictureUrl;

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


    public String getCreateReason() {
        return createReason;
    }

    public void setCreateReason(String createReason) {
        this.createReason = createReason;
    }

    public OrderAttachedTypeEnum getType() {
        return type;
    }

    public void setType(OrderAttachedTypeEnum type) {
        this.type = type;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    public PayStatusEnum getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(PayStatusEnum payStatus) {
        this.payStatus = payStatus;
    }

    public PayTypeEnum getPayType() {
        return payType;
    }

    public void setPayType(PayTypeEnum payType) {
        this.payType = payType;
    }

    public Date getPaymentCompletion() {
        return paymentCompletion;
    }

    public void setPaymentCompletion(Date paymentCompletion) {
        this.paymentCompletion = paymentCompletion;
    }

    public Integer getToUser() {
        return toUser;
    }

    public void setToUser(Integer toUser) {
        this.toUser = toUser;
    }

    public List<TimeShareOrderAttachedDetail> getItem() {
        return item;
    }

    public void setItem(List<TimeShareOrderAttachedDetail> item) {
        this.item = item;
    }

    public String getRepairPictureUrl() {
        return repairPictureUrl;
    }

    public void setRepairPictureUrl(String repairPictureUrl) {
        this.repairPictureUrl = repairPictureUrl;
    }

    public String getCarDamagePictureUrl() {
        return carDamagePictureUrl;
    }

    public void setCarDamagePictureUrl(String carDamagePictureUrl) {
        this.carDamagePictureUrl = carDamagePictureUrl;
    }
}
