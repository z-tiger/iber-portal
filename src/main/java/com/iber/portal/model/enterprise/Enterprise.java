package com.iber.portal.model.enterprise;

import java.util.Date;

public class Enterprise {
    private Integer id;

    private String enterpriseName;

    private String legalPerson;

    private String legalPhone;

    private String responsible;

    private String responsiblePhone;

    private String address;

    private String email;

    private String enterpriseTel;

    private String businessLicense;

    private String licenseFileUrl;

    private String organizationCode;

    private String admin;

    private String adminMobile;

    private String cityCode;

    private String level;

    private Date createTime;

    private String createUser;

    private Date updateTime;

    private String updateUser;

    private Integer adminId;

    private String isDelete;
    
    private String cityName;
    
    private String levelName;

    private Integer isContactPark;//是否关联网点 默认否

    private Integer levelId;
    private Integer totalRecharge;
    private Integer totalDeposit;
    private Integer totalConsumption;
    private Integer pId;

    private Integer canOverdraftMoney;
    private Integer depositLimit;
    private Integer totalOverdraftNum;
    private Integer canOverdraftNum;
    private Integer totalRefundSum;
    private Integer accountMoney;
    private boolean bindCars;

    private int enterpriseParkNum;
    private int enterpriseMemberNum;
    private int enterpriseCarNum;

    private int overdraftMoney;
    private int overdraftNum;
    
    private Integer branchOfficeNum;

    public Integer getBranchOfficeNum() {
		return branchOfficeNum;
	}

	public void setBranchOfficeNum(Integer branchOfficeNum) {
		this.branchOfficeNum = branchOfficeNum;
	}

	public int getOverdraftMoney() {
        return overdraftMoney;
    }

    public void setOverdraftMoney(int overdraftMoney) {
        this.overdraftMoney = overdraftMoney;
    }

    public int getOverdraftNum() {
        return overdraftNum;
    }

    public void setOverdraftNum(int overdraftNum) {
        this.overdraftNum = overdraftNum;
    }

    public int getEnterpriseParkNum() {
        return enterpriseParkNum;
    }

    public void setEnterpriseParkNum(int enterpriseParkNum) {
        this.enterpriseParkNum = enterpriseParkNum;
    }

    public int getEnterpriseMemberNum() {
        return enterpriseMemberNum;
    }

    public void setEnterpriseMemberNum(int enterpriseMemberNum) {
        this.enterpriseMemberNum = enterpriseMemberNum;
    }

    public int getEnterpriseCarNum() {
        return enterpriseCarNum;
    }

    public void setEnterpriseCarNum(int enterpriseCarNum) {
        this.enterpriseCarNum = enterpriseCarNum;
    }

    public Enterprise() {
    }

    public boolean isBindCars() {
        return bindCars;
    }

    public void setBindCars(boolean bindCars) {
        this.bindCars = bindCars;
    }

    public Integer getAccountMoney() {
        return accountMoney;
    }

    public void setAccountMoney(Integer accountMoney) {
        this.accountMoney = accountMoney;
    }

    public Integer getCanOverdraftMoney() {
        return canOverdraftMoney;
    }

    public void setCanOverdraftMoney(Integer canOverdraftMoney) {
        this.canOverdraftMoney = canOverdraftMoney;
    }

    public Integer getDepositLimit() {
        return depositLimit;
    }

    public void setDepositLimit(Integer depositLimit) {
        this.depositLimit = depositLimit;
    }

    public Integer getTotalOverdraftNum() {
        return totalOverdraftNum;
    }

    public void setTotalOverdraftNum(Integer totalOverdraftNum) {
        this.totalOverdraftNum = totalOverdraftNum;
    }

    public Integer getCanOverdraftNum() {
        return canOverdraftNum;
    }

    public void setCanOverdraftNum(Integer canOverdraftNum) {
        this.canOverdraftNum = canOverdraftNum;
    }

    public Integer getTotalRefundSum() {
        return totalRefundSum;
    }

    public void setTotalRefundSum(Integer totalRefundSum) {
        this.totalRefundSum = totalRefundSum;
    }

    public Integer getpId() {
        return pId;
    }

    public void setpId(Integer pId) {
        this.pId = pId;
    }

    public Integer getLevelId() {
        return levelId;
    }

    public void setLevelId(Integer levelId) {
        this.levelId = levelId;
    }

    public Integer getTotalRecharge() {
        return totalRecharge;
    }

    public void setTotalRecharge(Integer totalRecharge) {
        this.totalRecharge = totalRecharge;
    }

    public Integer getTotalDeposit() {
        return totalDeposit;
    }

    public void setTotalDeposit(Integer totalDeposit) {
        this.totalDeposit = totalDeposit;
    }

    public Integer getTotalConsumption() {
        return totalConsumption;
    }

    public void setTotalConsumption(Integer totalConsumption) {
        this.totalConsumption = totalConsumption;
    }


    public Integer getIsContactPark() {
        return isContactPark;
    }

    public void setIsContactPark(Integer isContactPark) {
        this.isContactPark = isContactPark;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName == null ? null : enterpriseName.trim();
    }

    public String getLegalPerson() {
        return legalPerson;
    }

    public void setLegalPerson(String legalPerson) {
        this.legalPerson = legalPerson == null ? null : legalPerson.trim();
    }

    public String getLegalPhone() {
        return legalPhone;
    }

    public void setLegalPhone(String legalPhone) {
        this.legalPhone = legalPhone == null ? null : legalPhone.trim();
    }

    public String getResponsible() {
        return responsible;
    }

    public void setResponsible(String responsible) {
        this.responsible = responsible == null ? null : responsible.trim();
    }

    public String getResponsiblePhone() {
        return responsiblePhone;
    }

    public void setResponsiblePhone(String responsiblePhone) {
        this.responsiblePhone = responsiblePhone == null ? null : responsiblePhone.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getEnterpriseTel() {
        return enterpriseTel;
    }

    public void setEnterpriseTel(String enterpriseTel) {
        this.enterpriseTel = enterpriseTel == null ? null : enterpriseTel.trim();
    }

    public String getBusinessLicense() {
        return businessLicense;
    }

    public void setBusinessLicense(String businessLicense) {
        this.businessLicense = businessLicense == null ? null : businessLicense.trim();
    }

    public String getLicenseFileUrl() {
        return licenseFileUrl;
    }

    public void setLicenseFileUrl(String licenseFileUrl) {
        this.licenseFileUrl = licenseFileUrl == null ? null : licenseFileUrl.trim();
    }

    public String getOrganizationCode() {
        return organizationCode;
    }

    public void setOrganizationCode(String organizationCode) {
        this.organizationCode = organizationCode == null ? null : organizationCode.trim();
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin == null ? null : admin.trim();
    }

    public String getAdminMobile() {
        return adminMobile;
    }

    public void setAdminMobile(String adminMobile) {
        this.adminMobile = adminMobile == null ? null : adminMobile.trim();
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode == null ? null : cityCode.trim();
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level == null ? null : level.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser == null ? null : updateUser.trim();
    }

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete == null ? null : isDelete.trim();
    }
    
	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}
	
}