package com.iber.portal.model.enterprise;

public class EnterpriseLevel {
    private Integer id;

    private String name;

    private Integer depositLimit;

    private Integer overdraft;

    private Integer memberDepositLimit;

    private Integer depositNumber;

    public Integer getDepositNumber() {
        return depositNumber;
    }

    public void setDepositNumber(Integer depositNumber) {
        this.depositNumber = depositNumber;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getDepositLimit() {
        return depositLimit;
    }

    public void setDepositLimit(Integer depositLimit) {
        this.depositLimit = depositLimit;
    }

    public Integer getOverdraft() {
        return overdraft;
    }

    public void setOverdraft(Integer overdraft) {
        this.overdraft = overdraft;
    }

    public Integer getMemberDepositLimit() {
        return memberDepositLimit;
    }

    public void setMemberDepositLimit(Integer memberDepositLimit) {
        this.memberDepositLimit = memberDepositLimit;
    }
}