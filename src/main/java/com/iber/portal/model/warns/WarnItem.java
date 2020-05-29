package com.iber.portal.model.warns;

public class WarnItem {
    private Integer id;

    private String itemCode;

    private String itemName;

    private String warnTpl;

    private Integer toDispatch;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode == null ? null : itemCode.trim();
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName == null ? null : itemName.trim();
    }

    public String getWarnTpl() {
        return warnTpl;
    }

    public void setWarnTpl(String warnTpl) {
        this.warnTpl = warnTpl == null ? null : warnTpl.trim();
    }

    public Integer getToDispatch() {
        return toDispatch;
    }

    public void setToDispatch(Integer toDispatch) {
        this.toDispatch = toDispatch;
    }
}