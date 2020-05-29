package com.iber.portal.model.overall;

import java.util.Date;

public class OverallAnnualMember {
    private Integer id;

    private Integer memberTotal;

    private Integer memberLose;

    private Integer memberAdd;

    private Integer memberAudit;

    private Integer totalMemberLose;

    private Integer totalMemberNew;

    private Integer totalMemberAudit;

    private Integer silenceUser;

    private Integer loseUser;

    private Integer totalSilenceUser;

    private Integer totalLoseUser;

    private Double totalBalance;

    private Double totalDeposit;

    private Double totalIncome;

    private Double balance;

    private Double deposit;

    private Double income;

    private Integer addUser;

    private Integer totalUser;

    private Integer auditUser;

    private Integer timeNum;

    private String annual;

    private String timeType;

    private String cityCode;

    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMemberTotal() {
        return memberTotal;
    }

    public void setMemberTotal(Integer memberTotal) {
        this.memberTotal = memberTotal;
    }

    public Integer getMemberLose() {
        return memberLose;
    }

    public void setMemberLose(Integer memberLose) {
        this.memberLose = memberLose;
    }

    public Integer getMemberAdd() {
        return memberAdd;
    }

    public void setMemberAdd(Integer memberAdd) {
        this.memberAdd = memberAdd;
    }

    public Integer getMemberAudit() {
        return memberAudit;
    }

    public void setMemberAudit(Integer memberAudit) {
        this.memberAudit = memberAudit;
    }

    public Integer getTotalMemberLose() {
        return totalMemberLose;
    }

    public void setTotalMemberLose(Integer totalMemberLose) {
        this.totalMemberLose = totalMemberLose;
    }

    public Integer getTotalMemberNew() {
        return totalMemberNew;
    }

    public void setTotalMemberNew(Integer totalMemberNew) {
        this.totalMemberNew = totalMemberNew;
    }

    public Integer getTotalMemberAudit() {
        return totalMemberAudit;
    }

    public void setTotalMemberAudit(Integer totalMemberAudit) {
        this.totalMemberAudit = totalMemberAudit;
    }

    public Integer getSilenceUser() {
        return silenceUser;
    }

    public void setSilenceUser(Integer silenceUser) {
        this.silenceUser = silenceUser;
    }

    public Integer getLoseUser() {
        return loseUser;
    }

    public void setLoseUser(Integer loseUser) {
        this.loseUser = loseUser;
    }

    public Integer getTotalSilenceUser() {
        return totalSilenceUser;
    }

    public void setTotalSilenceUser(Integer totalSilenceUser) {
        this.totalSilenceUser = totalSilenceUser;
    }

    public Integer getTotalLoseUser() {
        return totalLoseUser;
    }

    public void setTotalLoseUser(Integer totalLoseUser) {
        this.totalLoseUser = totalLoseUser;
    }

    public Double getTotalBalance() {
        return totalBalance;
    }

    public void setTotalBalance(Double totalBalance) {
        this.totalBalance = totalBalance;
    }

    public Double getTotalDeposit() {
        return totalDeposit;
    }

    public void setTotalDeposit(Double totalDeposit) {
        this.totalDeposit = totalDeposit;
    }

    public Double getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(Double totalIncome) {
        this.totalIncome = totalIncome;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Double getDeposit() {
        return deposit;
    }

    public void setDeposit(Double deposit) {
        this.deposit = deposit;
    }

    public Double getIncome() {
        return income;
    }

    public void setIncome(Double income) {
        this.income = income;
    }

    public Integer getAddUser() {
        return addUser;
    }

    public void setAddUser(Integer addUser) {
        this.addUser = addUser;
    }

    public Integer getTotalUser() {
        return totalUser;
    }

    public void setTotalUser(Integer totalUser) {
        this.totalUser = totalUser;
    }

    public Integer getAuditUser() {
        return auditUser;
    }

    public void setAuditUser(Integer auditUser) {
        this.auditUser = auditUser;
    }

    public Integer getTimeNum() {
        return timeNum;
    }

    public void setTimeNum(Integer timeNum) {
        this.timeNum = timeNum;
    }

    public String getAnnual() {
        return annual;
    }

    public void setAnnual(String annual) {
        this.annual = annual == null ? null : annual.trim();
    }

    public String getTimeType() {
        return timeType;
    }

    public void setTimeType(String timeType) {
        this.timeType = timeType == null ? null : timeType.trim();
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode == null ? null : cityCode.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}