package com.iber.portal.model.timeShare;

import com.iber.portal.enums.DetailItemEnum;

/**
 * 附属订单详情
 */
public class TimeShareOrderAttachedDetail {

    private Integer id;

    /**是否选中,前端传值用**/
    private Boolean ischeck;

    /**附属订单id**/
    private Integer attached_id;

    /**收费项目枚举**/
    private DetailItemEnum item;

    /**数目，元为单位，保存进数据库需要乘以1000**/
    private Float money;

    private String is_show;

    /**每一个收费项目的说明，可以不填**/
    private String explanation;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getIscheck() {
        return ischeck;
    }

    public void setIscheck(Boolean ischeck) {
        this.ischeck = ischeck;
    }

    public Integer getAttached_id() {
        return attached_id;
    }

    public void setAttached_id(Integer attached_id) {
        this.attached_id = attached_id;
    }

    public DetailItemEnum getItem() {
        return item;
    }

    public void setItem(DetailItemEnum item) {
        this.item = item;
    }

    public Float getMoney() {
        return money;
    }

    public void setMoney(Float money) {
        this.money = money;
    }

    public String getIs_show() {
        return is_show;
    }

    public void setIs_show(String is_show) {
        this.is_show = is_show;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }
}
